package org.elham.bankLoader.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler {
    private static final Logger logger = LogManager.getLogger(FileHandler.class);

    public void moveFileToBackup(File file, String destPath) {
        try {
            Path sourcePath = file.toPath();
            Path backupPath = Paths.get(destPath, file.getName());
            Files.move(sourcePath, backupPath);
            logger.info("Moved file " + file.getName() + " to backup directory.");
        } catch (IOException e) {
            logger.warn("IOException in move file to backup.");
            e.printStackTrace();
        }
    }

    public File renameFile(File file) {
        String originalName = file.getName();
        String newName = originalName.substring(0, originalName.lastIndexOf(".")) + "threaded.csv";
        File renamedFile = new File(file.getParent(), newName);
        file.renameTo(renamedFile);
        return renamedFile;
    }
}
