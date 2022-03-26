package hu.hawser.coauthorplugin.AuthorListPopup;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.PopupStep;
import com.intellij.openapi.ui.popup.util.BaseListPopupStep;
import hu.hawser.coauthorplugin.AuthorListLoader;
import hu.hawser.coauthorplugin.AuthorListPopup.elements.AuthorElement;
import hu.hawser.coauthorplugin.AuthorListPopup.elements.AuthorListElement;
import hu.hawser.coauthorplugin.AuthorListPopup.elements.MoreOptionsElement;
import hu.hawser.coauthorplugin.CoAuthorSelector;
import icons.Icons;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

public class CoAuthorListPopup extends BaseListPopupStep<AuthorListElement> {

    @NotNull
    private final Project project;

    @NotNull
    private final List<String> authorList;

    @NotNull
    private final ListSelectionHandler<String> selectionHandler;

    public CoAuthorListPopup(@NotNull Project project, @NotNull String title, @NotNull List<String> authorList, @NotNull ListSelectionHandler<String> selectionHandler) {
        super(title, Stream.concat(
                Stream.of(new MoreOptionsElement()),
                authorList.stream().map(AuthorElement::new)
            ).collect(toList()),
            singletonList(Icons.USERS)
        );
        this.project = project;
        this.authorList = authorList;
        this.selectionHandler = selectionHandler;
    }

    @Override
    public PopupStep onChosen(AuthorListElement selectedValue, boolean finalChoice) {
        if (selectedValue instanceof MoreOptionsElement) {
            doFinalStep(() -> {
                CoAuthorSelector selector = new CoAuthorSelector(project, authorList);
                if (!selector.showAndGet()) {
                    return;
                }

                List<String> modifiedAuthorList = selector.getAllAuthor();
                if (!modifiedAuthorList.equals(authorList)) {
                    AuthorListLoader.save(modifiedAuthorList);
                }
                selectionHandler.onItemsSelected(selector.getSelectedAuthors());
            });
            return super.onChosen(selectedValue, finalChoice);
        } else {
            selectionHandler.onItemsSelected(Arrays.asList(selectedValue.getText()));
            return super.onChosen(selectedValue, finalChoice);
        }
    }

    @NotNull
    @Override
    public String getTextFor(AuthorListElement value) {
        return value.getText();

    }
}
