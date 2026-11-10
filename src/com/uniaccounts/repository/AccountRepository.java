package com.uniaccounts.repository;

import com.uniaccounts.audit.AuditLogger;
import com.uniaccounts.model.StudentAccount;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Pattern SINGLETON - the central repository exists only once.
 * If each part of the program had its own repository, accounts would be lost between copies.
 */
public class AccountRepository {

    private static AccountRepository instance;

    private final Map<String, StudentAccount> accounts = new LinkedHashMap<>();

    private AccountRepository() { }

    public static synchronized AccountRepository getInstance() {
        if (instance == null) {
            instance = new AccountRepository();
        }
        return instance;
    }

    /** Adds an account. The id must be unique in the repository. */
    public void add(StudentAccount account) {
        if (accounts.containsKey(account.getId())) {
            AuditLogger.getInstance().log("REJECTED", "duplicate id " + account.getId());
            throw new IllegalArgumentException("id already exists: " + account.getId());
        }
        accounts.put(account.getId(), account);
        AuditLogger.getInstance().log("CREATED", "account " + account.getId() + " (" + account.getFullName() + ")");
    }

    public StudentAccount findById(String id) { return accounts.get(id); }

    public List<StudentAccount> findAll() { return new ArrayList<>(accounts.values()); }

    public int count() { return accounts.size(); }
}
