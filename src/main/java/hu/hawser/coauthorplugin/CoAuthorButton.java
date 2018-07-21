package hu.hawser.coauthorplugin;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import java.util.List;

public class CoAuthorButton extends AnAction {

    public CoAuthorButton() {
        super(AllIcons.Actions.Edit);
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        List<String> authorList = AuthorListLoader.load();
        // TODO: do not open selector if there are no authors

        CoAuthorSelector selector = new CoAuthorSelector(event.getProject(), authorList);
        boolean closedWithOK = selector.showAndGet();

        if (closedWithOK) {
            System.out.println("OK");
            selector.getSelectedAuthors().forEach(System.out::println);
        } else {
            System.out.println("Cancel");
        }
    }
}
