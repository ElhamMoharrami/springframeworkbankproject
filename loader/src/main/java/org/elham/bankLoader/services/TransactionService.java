package org.elham.bankLoader.services;

import org.elham.bankLoader.model.TransactType;
import org.elham.bankLoader.model.Transaction;
import org.elham.bankLoader.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    @Value("${files.destination}")
    private String fileDestination;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void run() throws Exception {
        int counter = 1000;
        boolean quitFlag = true;
        while (quitFlag) {
            File file = new File(fileDestination + "/transaction" + counter + ".csv");
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(ResourceUtils.getFile
                        (fileDestination + "/transaction" + counter + ".csv")));
                String line;
                boolean isFirstRow = true;
                while ((line = reader.readLine()) != null) {
                    if (isFirstRow) {
                        isFirstRow = false;
                        continue;
                    }
                    String[] columns = line.split(",");
                    Transaction csvData = new Transaction();
                    csvData.setId(Long.valueOf(columns[0]));
                    csvData.setTime(Long.valueOf(columns[1]));
                    csvData.setAmount(Double.parseDouble(columns[2]));
                    csvData.setAccAId(Long.valueOf(columns[3]));
                    csvData.setAccBId(Long.valueOf(columns[4]));
                    csvData.setType(TransactType.valueOf(columns[5]));
                    transactionRepository.save(csvData);
                }
                reader.close();
                counter += 1000;
            } else {
                quitFlag = false;
            }
        }
    }
}

