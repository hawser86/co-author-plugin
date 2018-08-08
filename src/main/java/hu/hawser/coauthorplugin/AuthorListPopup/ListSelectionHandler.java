package hu.hawser.coauthorplugin.AuthorListPopup;

import org.jetbrains.annotations.NotNull;
import java.util.List;

public interface ListSelectionHandler<T> {

    void onItemsSelected(@NotNull List<T> selectedItems);
}
