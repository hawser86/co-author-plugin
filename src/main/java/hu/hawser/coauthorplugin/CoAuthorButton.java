package hu.hawser.coauthorplugin;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

public class CoAuthorButton extends AnAction {

    public CoAuthorButton() {
        super(AllIcons.Actions.Edit);
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        Messages.showMessageDialog("Hello", "World", Messages.getInformationIcon());
    }
}
