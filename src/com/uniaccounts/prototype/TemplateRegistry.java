package com.uniaccounts.prototype;

import com.uniaccounts.model.StudentAccount;

import java.util.HashMap;
import java.util.Map;

/**
 * Pattern PROTOTYPE - role "Registry" (prototype manager).
 * Keeps one ready-made template per cohort (e.g. "L2-INFO") and gives
 * a fresh copy of it each time a new student joins that cohort.
 */
public class TemplateRegistry {

    private final Map<String, StudentAccount> templates = new HashMap<>();

    public void register(String cohort, StudentAccount template) {
        templates.put(cohort, template);
    }

    /** Creates a new account for the cohort: copy of the template + new identity. */
    public StudentAccount createFrom(String cohort, String id, String fullName, String email) {
        StudentAccount template = templates.get(cohort);
        if (template == null)
            throw new IllegalArgumentException("No template for cohort " + cohort);
        return template.copy().withIdentity(id, fullName, email);
    }
}
