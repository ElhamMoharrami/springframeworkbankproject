package org.elham.bankSearcher.services;

import org.elham.bankSearcher.common.CsvWriter;
import org.elham.bankSearcher.model.TransactionProjection;
import org.elham.bankSearcher.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class CliSearcher implements CommandLineRunner {
    private final TransactionRepository transactionRepository;
    private final CsvWriter<TransactionProjection> writer;
    @Value("${searcher.customerTransactionsFileDestination}")
    private String customerTransactionsFileDestination;

    public CliSearcher(TransactionRepository transactionRepository, CsvWriter<TransactionProjection> writer) {
        this.transactionRepository = transactionRepository;
        this.writer = writer;
    }

    private final static String HEADER = "CustomerName,CustomerId,AccountId," +
            "TransactionId,SourceAcc,DestinationAcc,Amount,Type";

    @Override
    public void run(String... args) throws Exception {
        int counter = 0;
        while (true) {
            System.out.println("enter a name");
            Scanner input = new Scanner(System.in);
            String keyword = input.nextLine().toLowerCase();
            String[] keywordParts = keyword.split(" ");
            String capitalizedName = "";
            for (String namePart : keywordParts) {
                capitalizedName += namePart.substring(0, 1).toUpperCase() +
                        namePart.substring(1).toLowerCase() + " ";
            }
            capitalizedName = capitalizedName.trim();
            if (keyword.equals("q")) {
                return;
            } else {
                List<TransactionProjection> transactions = transactionRepository.findByCustomer(capitalizedName);
                if (transactions.size() > 0) {
                    writer.writeToFile(HEADER, transactions, "customerTransact" + counter
                            + ".csv", customerTransactionsFileDestination);
                } else {
                    System.out.println("there is no transaction.customer doesnt exist.");
                }
            }
            counter++;
        }
    }
}
