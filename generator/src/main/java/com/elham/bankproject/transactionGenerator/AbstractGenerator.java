package com.elham.bankproject.transactionGenerator;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public abstract class AbstractGenerator<T> {
    @Value("${files.destination}")
    private String fileLocation;

    public abstract List<T> generate();

    public String getFileLocation() {
        return fileLocation;
    }
}
