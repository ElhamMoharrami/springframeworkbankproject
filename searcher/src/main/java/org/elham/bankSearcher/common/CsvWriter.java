package org.elham.bankSearcher.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

@Component
public class CsvWriter<T> {
    private static final Logger logger = LogManager.getLogger(CsvWriter.class);

    public void writeToFile(String header, List<T> list, String fileName, String fileLocation) {
        Path filePath = FileSystems.getDefault().getPath(fileLocation, fileName);
        try (BufferedWriter bufferedList = Files.newBufferedWriter(filePath)) {
            BasicFileAttributes attributes = Files.readAttributes(filePath, BasicFileAttributes.class);
            long currentTime = System.currentTimeMillis();
            long creationTime = attributes.creationTime().toMillis();
            long duration = currentTime - creationTime;
            bufferedList.write(header + "\n");
            for (T element : list) {
                bufferedList.write(element.toString() + "\n");
            }
            logger.info(fileName + " generated successfully in " + fileLocation + " with " + list.size()
                    + " items; took  " + duration + " milliseconds");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            logger.error("Something went wrong,could not create file.");
        }
    }
}