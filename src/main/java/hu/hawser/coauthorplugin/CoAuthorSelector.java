package hu.hawser.coauthorplugin;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;

public class CoAuthorSelector extends DialogWrapper {

    private AuthorTableModel tableModel;


    CoAuthorSelector(@Nullable Project project, List<String> authorList) {
        super(project);
        tableModel = new AuthorTableModel(authorList);

        init();
        setTitle("Co-Authors");
    }


    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JBTable table = createTable();
        mainPanel.add(table, BorderLayout.CENTER);
        mainPanel.add(createToolbar(table), BorderLayout.SOUTH);

        return mainPanel;
    }


    private JBTable createTable() {
        JBTable table = new JBTable(tableModel);

        resizeColumnWidthToFitContent(table);
        preventResizingFirstColumn(table);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        return table;
    }


    private JPanel createToolbar(JBTable table) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        DefaultActionGroup actionGroup = new DefaultActionGroup();
        actionGroup.add(new AnAction("Add", "Add new author to the list", AllIcons.ToolbarDecorator.Add) {
            @Override
            public void actionPerformed(AnActionEvent e) {
                tableModel.addRow();
            }
        });
        actionGroup.add(new AnAction("Remove", "Remove selected authors from the list", AllIcons.ToolbarDecorator.Remove) {
            @Override
            public void actionPerformed(AnActionEvent e) {
                tableModel.removeRows(table.getSelectedRows());
            }
        });

        ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar("MyToolbar", actionGroup, true);
        panel.add(toolbar.getComponent());

        return panel;
    }


    private void preventResizingFirstColumn(JBTable table) {
        TableColumn firstColumn = table.getColumnModel().getColumn(0);
        firstColumn.setMaxWidth(firstColumn.getWidth());
    }


    private void resizeColumnWidthToFitContent(JTable table) {
        int minimumWidth = 15;
        int maxWidth = 300;

        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = minimumWidth;
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }

            width = Math.min(maxWidth, width);
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }


    List<String> getSelectedAuthors() {
        return tableModel.getSelectedAuthors();
    }


    List<String> getAllAuthor() {
        return tableModel.getAllAuthor();
    }

}

