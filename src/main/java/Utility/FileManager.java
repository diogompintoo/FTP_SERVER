package Utility;

import java.io.*;
import java.nio.file.*;


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
            System.out.println("Error creating root directory");
        }
    }

    public Path getPath(String name) {
        if (name == null || name.trim().isEmpty()){
            return rootPath;
        }
        return rootPath.resolve(name);
    }

    public boolean createDirectory(String dirName) {
        if (dirName == null || dirName.trim().isEmpty()) {
            return false;
        }
        Path newDir = getPath(dirName);
        try {
            Files.createDirectories(newDir);
            return true;
        } catch (IOException e) {
            System.out.println("Error creating directory");
            return false;
        }
    }

    public File[] listFiles() {
        return rootPath.toFile().listFiles();
    }

    public String[] listFileNames() {
        File[] files = listFiles();

        if (files == null || files.length == 0) {
            return new String[0];
        }
        String[] names = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            names[i] = files[i].getName();
        }
        return names;
    }

    public boolean exists(String name) {
        return Files.exists(getPath(name));
    }

    public boolean isFile(String name) {
        return Files.isRegularFile(getPath(name));
    }

    public File getFile(String fileName) {
        return getPath(fileName).toFile();
    }

    public FileInputStream readFile(String fileName) throws FileNotFoundException {
        return new FileInputStream(getFile(fileName));
    }
}