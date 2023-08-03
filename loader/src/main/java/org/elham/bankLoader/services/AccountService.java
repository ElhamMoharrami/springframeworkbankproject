package org.elham.bankLoader.services;

import org.elham.bankLoader.model.Account;
import org.elham.bankLoader.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.FileReader;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Value("${files.destination}")
    private String fileDestination;

    public void run() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(ResourceUtils.getFile(fileDestination+"/accounts.csv")));
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
    }
}
