package com.elham.bankLoader.services;

import com.elham.bank.common.model.TransactType;
import com.elham.bank.common.model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.elham.bankLoader.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
public class TransactionService extends FileHandler {
    private final TransactionRepository transactionRepository;

    private static final Logger logger = LogManager.getLogger(TransactionService.class);

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void run(File file, String destPath) {
        File renamedFile = renameFile(file);
        try (BufferedReader reader = new BufferedReader(new FileReader(renamedFile))) {
            long startTransactionLoadTimeMillis = System.currentTimeMillis();
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
            long endTransactionLoadTimeMillis = System.currentTimeMillis();
            long timeToLoadTransactions = endTransactionLoadTimeMillis - startTransactionLoadTimeMillis;
            logger.info(renamedFile.getName() + " loaded in database. took " + timeToLoadTransactions + " milli seconds.");
            moveFileToBackup(renamedFile, destPath);
        } catch (IOException e) {
            logger.warn("i/o exception happened in TransactionService");
        }
    }


}

