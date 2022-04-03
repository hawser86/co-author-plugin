package hu.hawser.coauthorplugin;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorListLoaderTest {

    @Test
    void loadsCoAuthors(@TempDir Path tempDir) throws Exception {

        System.setProperty("user.home", tempDir.toString());
        File coAuthors = tempDir.resolve(".git_coauthors").toFile();

        FileUtils.writeLines(coAuthors, Collections.singletonList("name <name@example.com>"));

        List<String> actual = AuthorListLoader.load();

        assertThat(actual).containsExactly("name <name@example.com>");
    }

    @Test
    void loadSkipsEmptyLines(@TempDir Path tempDir) throws Exception {

        System.setProperty("user.home", tempDir.toString());
        File coAuthors = tempDir.resolve(".git_coauthors").toFile();

        FileUtils.write(coAuthors, "\n\n\n");

        List<String> actual = AuthorListLoader.load();

        assertThat(actual).isEmpty();
    }

    @Test
    void loadsTrimsLine(@TempDir Path tempDir) throws Exception {

        System.setProperty("user.home", tempDir.toString());
        File coAuthors = tempDir.resolve(".git_coauthors").toFile();

        FileUtils.writeLines(coAuthors, Collections.singletonList("name <name@example.com>      "));

        List<String> actual = AuthorListLoader.load();

        assertThat(actual).containsExactly("name <name@example.com>");
    }

    @Test
    void loadsReturnsEmptyListWhenFileNotFound(@TempDir Path tempDir) {

        System.setProperty("user.home", tempDir.toString());

        List<String> actual = AuthorListLoader.load();

        assertThat(actual).isEmpty();
    }

    @Test
    void save(@TempDir Path tempDir) {

        System.setProperty("user.home", tempDir.toString());

        List<String> list = Collections.singletonList("name <name@example.com>");

        AuthorListLoader.save(list);

        assertThat(tempDir.resolve(".git_coauthors").toFile())
                .hasContent("name <name@example.com>");
    }
}