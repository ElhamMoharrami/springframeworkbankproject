package org.elham.bankLoader.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elham.bankLoader.model.Account;
import org.elham.bankLoader.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    private static final Logger logger = LogManager.getLogger(AccountService.class);

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public void run(File file) throws Exception {
        long startAccountLoadTimeMillis = System.currentTimeMillis();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        boolean isFirstRow = true;
        while ((line = reader.readLine()) != null) {
            if (isFirstRow) {
                isFirstRow = false;
                continue;
            }
            String[] columns = line.split(",");
            Account csvData = new Account();
            csvData.setCustomerId(columns[0]);
            csvData.setAccountId(columns[1]);
            accountRepository.save(csvData);
        }
        reader.close();
        long endAccountLoadTimeMillis = System.currentTimeMillis();
        long timeToLoadAccounts = endAccountLoadTimeMillis - startAccountLoadTimeMillis;
        logger.info("accounts loaded in database. took " + timeToLoadAccounts + " milli seconds.");
    }
}