package hu.hawser.coauthorplugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.ui.Refreshable;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CoAuthorButton extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        CheckinProjectPanel checkinPanel = getCheckinPanel(event);
        if (checkinPanel == null) {
            return;
        }

        List<String> authorList = AuthorListLoader.load();

        if (authorList.isEmpty()) {
            Messages.showMessageDialog(
                    event.getProject(),
                    "No authors found in " + AuthorListLoader.configFilePath(),
                    "Error",
                    Messages.getErrorIcon());

            return;
        }

        CoAuthorSelector selector = new CoAuthorSelector(event.getProject(), authorList);
        if (!selector.showAndGet()) {
            return;
        }

        List<String> modifiedAuthorList = selector.getAllAuthor();
        if (!modifiedAuthorList.equals(authorList)) {
            AuthorListLoader.save(modifiedAuthorList);
        }

        updateCommitMessage(checkinPanel, selector.getSelectedAuthors());
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
