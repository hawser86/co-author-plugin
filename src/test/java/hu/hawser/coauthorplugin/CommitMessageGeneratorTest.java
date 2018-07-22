package hu.hawser.coauthorplugin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CommitMessageGeneratorTest {

    private String testCase;
    private final String baseMessage;
    private final List<String> authors;
    private final String expectedMessage;


    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {
                        "empty list",
                        "commit message",
                        Collections.emptyList(),
                        "commit message"
                },
                {
                        "non-empty list",
                        "commit message",
                        Arrays.asList("name <name@example.com>", "another-name <another-name@example.com>"),
                        "commit message\n\n\nCo-authored-by: name <name@example.com>\nCo-authored-by: another-name <another-name@example.com>"
                },
                {
                        "base commit message ends with new line",
                        "commit message\n",
                        Arrays.asList("name <name@example.com>", "another-name <another-name@example.com>"),
                        "commit message\n\n\nCo-authored-by: name <name@example.com>\nCo-authored-by: another-name <another-name@example.com>"
                },
                {
                        "base commit message already has co-author",
                        "commit message\n\n\nCo-authored-by: another-name <another-name@example.com>",
                        Collections.singletonList("name <name@example.com>"),
                        "commit message\n\n\nCo-authored-by: name <name@example.com>"
                },
                {
                        "base commit message only contains a co-author",
                        "Co-authored-by: another-name <another-name@example.com>",
                        Collections.singletonList("name <name@example.com>"),
                        "Co-authored-by: name <name@example.com>"
                },
                {
                        "base commit message already has co-author and list is empty",
                        "commit message\n\n\nCo-authored-by: another-name <another-name@example.com>",
                        Collections.emptyList(),
                        "commit message"
                },
                {
                        "base commit message already has more co-authors",
                        "commit message\n\n\nCo-authored-by: another-name <another-name@example.com>\nCo-authored-by: another-name <another-name@example.com>",
                        Collections.singletonList("name <name@example.com>"),
                        "commit message\n\n\nCo-authored-by: name <name@example.com>"
                },
                {
                        "base commit message mentions 'Co-authored-by'",
                        "commit message\n\nmentions this in the body: Co-authored-by: another-name <another-name@example.com>",
                        Collections.singletonList("name <name@example.com>"),
                        "commit message\n\nmentions this in the body: Co-authored-by: another-name <another-name@example.com>\n\n\nCo-authored-by: name <name@example.com>"
                }
        });
    }


    public CommitMessageGeneratorTest(String testCase, String baseMessage, List<String> authors, String expectedMessage) {
        this.testCase = testCase;
        this.baseMessage = baseMessage;
        this.authors = authors;
        this.expectedMessage = expectedMessage;
    }


    @Test
    public void addCoAuthors() {
        String actualMessage = CommitMessageGenerator.addCoAuthors(baseMessage, authors);
        assertEquals(testCase, expectedMessage, actualMessage);
    }

}