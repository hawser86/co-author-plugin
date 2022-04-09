package hu.hawser.coauthorplugin;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorTableModelTest {

    @Test
    void getValueAtReturnsSelectedValueForColumn1() {
        AuthorTableModel sut = new AuthorTableModel(Collections.singletonList("name <name@example.com>"));

        Boolean value = (boolean) sut.getValueAt(0, 0);

        assertThat(value).isFalse();
    }

    @Test
    void getValueAtReturnsAuthorForColumn2() {
        List<String> authors = Collections.singletonList("name <name@example.com>");

        AuthorTableModel sut = new AuthorTableModel(authors);

        String value = (String) sut.getValueAt(0, 1);

        assertThat(value).isEqualTo(authors.get(0));
    }

    @Test
    void getRowCountReturnsCountOfAuthors() {

        List<String> authors = Collections.singletonList("name <name@example.com>");

        AuthorTableModel sut = new AuthorTableModel(authors);

        assertThat(sut.getRowCount()).isEqualTo(authors.size());

    }

    @Test
    void getColumnCountReturnsAlways2() {
        AuthorTableModel sut = new AuthorTableModel(Collections.singletonList("name <name@example.com>"));

        assertThat(sut.getColumnCount()).isEqualTo(2);
    }

    @Test
    void setValueAtSetsSelected() {
        AuthorTableModel sut = new AuthorTableModel(Collections.singletonList("name <name@example.com>"));

        sut.setValueAt(true, 0, 0);

        assertThat(sut.getValueAt(0, 0)).isEqualTo(true);
    }

    @Test
    void setValueAtSetsName() {
        AuthorTableModel sut = new AuthorTableModel(Collections.singletonList("name <name@example.com>"));

        sut.setValueAt("another-name <name@example.com>", 0, 1);

        assertThat(sut.getValueAt(0, 1))
                .isEqualTo("another-name <name@example.com>");
    }

    @Test
    void addRowAddsDefaultData() {
        AuthorTableModel sut = new AuthorTableModel(Collections.emptyList());

        sut.addRow();

        Boolean selected = (boolean) sut.getValueAt(0, 0);
        String author = (String) sut.getValueAt(0, 1);

        assertThat(selected).isFalse();
        assertThat(author).isEqualTo("");
    }

    @Test
    void removeRows() {
        AuthorTableModel sut = new AuthorTableModel(Collections.singletonList("name <name@example.com>"));

        sut.removeRows(new int[]{0});

        assertThat(sut.getAllAuthor()).isEmpty();
    }

    @Test
    void isCellEditableAlwaysReturnsTrue() {
        AuthorTableModel sut = new AuthorTableModel(Collections.emptyList());

        assertThat(sut.isCellEditable(0, 0)).isTrue();
    }

    @Test
    void getColumnClass() {
        AuthorTableModel sut = new AuthorTableModel(Collections.singletonList("name <name@example.com>"));

        assertThat(sut.getColumnClass(0).getTypeName()).isEqualTo("java.lang.Boolean");
        assertThat(sut.getColumnClass(1).getTypeName()).isEqualTo("java.lang.String");
    }

    @Test
    void getSelectedAuthors() {
        AuthorTableModel sut = new AuthorTableModel(Arrays.asList(
                "selected <name@example.com>",
                "not-selected <name@example.com>"
        ));

        sut.setValueAt(true, 0, 0);

        assertThat(sut.getSelectedAuthors()).containsExactly("selected <name@example.com>");
    }

    @Test
    void getAllAuthors() {
        AuthorTableModel sut = new AuthorTableModel(Arrays.asList(
                "selected <name@example.com>",
                "not-selected <name@example.com>"
        ));

        assertThat(sut.getAllAuthor()).containsExactlyInAnyOrder(
                "selected <name@example.com>",
                "not-selected <name@example.com>");
    }
}