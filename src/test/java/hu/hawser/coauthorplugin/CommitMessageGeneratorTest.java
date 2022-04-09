package hu.hawser.coauthorplugin;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CommitMessageGeneratorTest {

    static Stream<Arguments> addCoAuthors() {
        return Stream.of(
                arguments(
                        "empty list",
                        "commit message",
                        Collections.emptyList(),
                        "commit message"
                ),
                arguments(
                        "non-empty list",
                        "commit message",
                        Arrays.asList("name <name@example.com>", "another-name <another-name@example.com>"),
                        "commit message\n\n\nCo-authored-by: name <name@example.com>\nCo-authored-by: another-name <another-name@example.com>"
                ),
                arguments(
                        "base commit message ends with new line",
                        "commit message\n",
                        Arrays.asList("name <name@example.com>", "another-name <another-name@example.com>"),
                        "commit message\n\n\nCo-authored-by: name <name@example.com>\nCo-authored-by: another-name <another-name@example.com>"
                ),
                arguments(
                        "base commit message already has co-author",
                        "commit message\n\n\nCo-authored-by: another-name <another-name@example.com>",
                        Collections.singletonList("name <name@example.com>"),
                        "commit message\n\n\nCo-authored-by: name <name@example.com>"
                ),
                arguments(
                        "base commit message only contains a co-author",
                        "Co-authored-by: another-name <another-name@example.com>",
                        Collections.singletonList("name <name@example.com>"),
                        "Co-authored-by: name <name@example.com>"
                ),
                arguments(
                        "base commit message already has co-author and list is empty",
                        "commit message\n\n\nCo-authored-by: another-name <another-name@example.com>",
                        Collections.emptyList(),
                        "commit message"
                ),
                arguments(
                        "base commit message already has more co-authors",
                        "commit message\n\n\nCo-authored-by: another-name <another-name@example.com>\nCo-authored-by: another-name <another-name@example.com>",
                        Collections.singletonList("name <name@example.com>"),
                        "commit message\n\n\nCo-authored-by: name <name@example.com>"
                ),
                arguments(
                        "base commit message mentions 'Co-authored-by'",
                        "commit message\n\nmentions this in the body: Co-authored-by: another-name <another-name@example.com>",
                        Collections.singletonList("name <name@example.com>"),
                        "commit message\n\nmentions this in the body: Co-authored-by: another-name <another-name@example.com>\n\n\nCo-authored-by: name <name@example.com>"
                )
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource
    void addCoAuthors(String testCase, String baseMessage, List<String> authors, String expectedMessage) {
        String actualMessage = CommitMessageGenerator.addCoAuthors(baseMessage, authors);
        assertEquals(expectedMessage, actualMessage, testCase);
    }
}