package com.batrom.ing;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.util.Optional.ofNullable;

public class FileResourcesUtil {

    public static List<File> allFilesFromResource(final String folder) {
        return ofNullable(Thread.currentThread().getContextClassLoader().getResource(folder))
                .map(FileResourcesUtil::findFiles)
                .orElse(List.of());
    }

    public static String readFile(File file) {
        try {
            return Files.readString(file.toPath());
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private static List<File> findFiles(final URL resource) {
        try (final var stream = Files.walk(Paths.get(resource.toURI()))) {
            return stream.filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .toList();
        } catch (final URISyntaxException | IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
