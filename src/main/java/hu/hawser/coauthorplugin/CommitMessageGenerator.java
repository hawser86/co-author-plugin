package hu.hawser.coauthorplugin;

import java.util.Arrays;
import java.util.List;

public class CommitMessageGenerator {

    static String addCoAuthors(String baseMessage, List<String> authors) {
        String coAuthorLines = authors.stream()
                .map(author -> "Co-authored-by: " + author)
                .reduce((acc, line) -> acc + "\n" + line)
                .orElse("");


        String newMessage = removePreviousCoAuthors(baseMessage).trim() +
                "\n\n\n" +
                coAuthorLines;
        return newMessage.trim();
    }


    private static String removePreviousCoAuthors(String message) {
        return Arrays.stream(message.split("\n"))
                .filter(line -> !line.startsWith("Co-authored-by:"))
                .reduce((acc, line) -> acc + "\n" + line)
                .orElse("");
    }

}
