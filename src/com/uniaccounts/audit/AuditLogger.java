package com.uniaccounts.audit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Pattern SINGLETON - the audit log exists only once in the whole program.
 * Every part of the system writes to the same journal.
 */
public class AuditLogger {

    // The single instance (created on first use).
    private static AuditLogger instance;

    private final List<String> entries = new ArrayList<>();
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Private constructor: nobody can write "new AuditLogger()".
    private AuditLogger() { }

    // Global access point. synchronized = safe if several threads call it.
    public static synchronized AuditLogger getInstance() {
        if (instance == null) {
            instance = new AuditLogger();
        }
        return instance;
    }

    public void log(String action, String details) {
        entries.add("[" + LocalDateTime.now().format(FORMAT) + "] " + action + " - " + details);
    }

    public List<String> getEntries() { return Collections.unmodifiableList(entries); }

    public void printAll() {
        entries.forEach(System.out::println);
    }
}
