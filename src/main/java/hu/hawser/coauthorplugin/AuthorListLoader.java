package hu.hawser.coauthorplugin;

import com.intellij.ide.plugins.PluginManager;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorListLoader {

    private static final String FILE_NAME = ".git_coauthors";


    static List<String> load() {
        try {
            Charset charset = Charset.defaultCharset()
                    .newDecoder()
                    .onMalformedInput(CodingErrorAction.REPLACE)
                    .onUnmappableCharacter(CodingErrorAction.REPLACE)
                    .charset();

            return Files.readAllLines(configFilePath(), charset).stream()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            PluginManager.getLogger().error(e);
            return Collections.emptyList();
        }
    }


    static Path configFilePath() {
        return Paths.get(System.getProperty("user.home"), FILE_NAME);
    }

}
