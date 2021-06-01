package org.university.io;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import org.university.exceptions.NoFileException;
import lombok.NonNull;

@Component
public class FileReader {

    public List<String> read(@NonNull String fileName) {
        URL fileURL = getClass().getClassLoader().getResource(fileName);
        try (Stream<String> fileLines = Files.lines(Paths.get(fileURL.toURI()))) {
            return fileLines.collect(Collectors.toList());
        } catch (URISyntaxException | IOException | NullPointerException e) {
            throw new NoFileException("File \"" + fileName + "\" not found!", e);
        }
    }
}
