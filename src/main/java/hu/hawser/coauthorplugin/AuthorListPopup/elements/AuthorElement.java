package hu.hawser.coauthorplugin.AuthorListPopup.elements;

import org.jetbrains.annotations.NotNull;

public class AuthorElement extends AuthorListElement {

    @NotNull
    private final String author;

    public AuthorElement(@NotNull String author) {
        this.author = author;
    }

    @Override
    public String getText() {
        return author;
    }
}
