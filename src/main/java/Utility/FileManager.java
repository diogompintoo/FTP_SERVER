package Utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FileManager {

    private final Path rootPath;

    public FileManager() {
        this.rootPath = Paths.get(Constants.SERVER_ROOT);
        createRootDirectory();
    }

    private void createRootDirectory() {
        try {
            Files.createDirectories(rootPath);
        } catch (IOException e) {
        }
    }

    public Path getRootPath() {
        return rootPath;
    }

    public boolean createDirectory(String dirName) {
        if (dirName == null || dirName.trim().isEmpty()) return false;
        Path newDir = rootPath.resolve(dirName.trim());
        return newDir.toFile().mkdir();
    }

    public File[] listFiles() {
        return rootPath.toFile().listFiles();
    }

    public boolean fileExists(String fileName) {
        return Files.exists(rootPath.resolve(fileName));
    }

    public Path getFilePath(String fileName) {
        return rootPath.resolve(fileName);
    }

}
