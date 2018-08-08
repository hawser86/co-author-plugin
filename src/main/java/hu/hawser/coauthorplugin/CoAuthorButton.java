package hu.hawser.coauthorplugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.ui.Refreshable;
import hu.hawser.coauthorplugin.AuthorListPopup.CoAuthorListPopup;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class CoAuthorButton extends AnAction {

    private static final List<String> DEFAULT_AUTHORS = Arrays.asList(
            "name <name@example.com>",
            "change-me <change-me@example.com>"
    );


    @Override
    public void actionPerformed(AnActionEvent event) {
        CheckinProjectPanel checkinPanel = getCheckinPanel(event);
        if (checkinPanel == null) {
            return;
        }

        List<String> loadedAuthorList = AuthorListLoader.load();

        final List<String> authorList = loadedAuthorList.isEmpty() ? DEFAULT_AUTHORS : loadedAuthorList;

        CoAuthorListPopup popupStep = new CoAuthorListPopup(event.getProject(),"Select Co-Author",
                authorList,
                selectedItems -> updateCommitMessage(checkinPanel, selectedItems));

        ListPopup listPopup = JBPopupFactory.getInstance().createListPopup(popupStep);
        listPopup.showInBestPositionFor(event.getDataContext());
    }


    @Nullable
    private static CheckinProjectPanel getCheckinPanel(@Nullable AnActionEvent e) {
        if (e == null) {
            return null;
        }
        Refreshable data = Refreshable.PANEL_KEY.getData(e.getDataContext());
        if (data instanceof CheckinProjectPanel) {
            return (CheckinProjectPanel) data;
        }
        return null;
    }


    private void updateCommitMessage(CheckinProjectPanel checkinPanel, List<String> authors) {
        String message = CommitMessageGenerator.addCoAuthors(
                checkinPanel.getCommitMessage(),
                authors
        );
        checkinPanel.setCommitMessage(message);
    }

}
