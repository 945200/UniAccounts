package com.uniaccounts.model;

import com.uniaccounts.prototype.AccountPrototype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Student account of the UniAccounts system.
 *
 * Pattern BUILDER   : this class is the "Product". Its constructor is private,
 *                     the only way to create it is StudentAccount.Builder.
 * Pattern PROTOTYPE : this class is the "ConcretePrototype" (implements AccountPrototype).
 */
public class StudentAccount implements AccountPrototype<StudentAccount> {

    // ----- mandatory fields -----
    private String id;
    private String fullName;
    private String email;

    // ----- optional fields -----
    private final String phone;          // default: null
    private final String level;          // default: "L1"
    private final String major;
    private final boolean scholarship;   // default: false
    private final List<String> courses;  // default: empty list

    // Private constructor: only the Builder can call it.
    private StudentAccount(Builder b) {
        this.id = b.id;
        this.fullName = b.fullName;
        this.email = b.email;
        this.phone = b.phone;
        this.level = b.level;
        this.major = b.major;
        this.scholarship = b.scholarship;
        this.courses = new ArrayList<>(b.courses);
    }

    // ================= PROTOTYPE =================
    /**
     * Pattern PROTOTYPE - method copy(): builds a new account by COPYING this one
     * (deep copy of the courses list, so the copy and the original stay independent).
     * The phone number is personal, so it is not copied (it stays null).
     */
    @Override
    public StudentAccount copy() {
        Builder b = new Builder(id, fullName, email)
                .level(level)
                .major(major)
                .scholarship(scholarship);
        for (String c : courses) {
            b.addCourse(c);
        }
        return b.build();
    }

    /** Changes only the identity of a copied account (id, name, email). */
    public StudentAccount withIdentity(String newId, String newName, String newEmail) {
        validateIdentity(newId, newName, newEmail);
        this.id = newId;
        this.fullName = newName;
        this.email = newEmail;
        return this;
    }

    // ================= Getters =================
    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getLevel() { return level; }
    public String getMajor() { return major; }
    public boolean isScholarship() { return scholarship; }
    public List<String> getCourses() { return Collections.unmodifiableList(courses); }

    /** Used by the demo to show that a copy has its own courses list. */
    public void addCourse(String course) { courses.add(course); }

    @Override
    public String toString() {
        return "StudentAccount{id='" + id + "', name='" + fullName + "', email='" + email
                + "', phone=" + phone + ", level=" + level + ", major=" + major
                + ", scholarship=" + scholarship + ", courses=" + courses + "}";
    }

    // Validation of the mandatory fields (shared by build() and withIdentity()).
    private static void validateIdentity(String id, String fullName, String email) {
        if (id == null || id.trim().isEmpty())
            throw new IllegalArgumentException("id is mandatory and must not be empty");
        if (fullName == null || fullName.trim().isEmpty())
            throw new IllegalArgumentException("fullName is mandatory");
        if (email == null || !email.contains("@"))
            throw new IllegalArgumentException("email must contain the @ symbol");
    }

    // ================= BUILDER =================
    /**
     * Pattern BUILDER - role "Builder" (static nested class).
     * The 3 mandatory fields are given in the Builder constructor, the optional
     * fields are set by chained methods, and build() checks everything.
     */
    public static class Builder {
        private static final List<String> LEVELS = List.of("L1", "L2", "L3", "M1", "M2");

        // mandatory
        private final String id;
        private final String fullName;
        private final String email;
        // optional, with default values
        private String phone = null;
        private String level = "L1";
        private String major = null;
        private boolean scholarship = false;
        private final List<String> courses = new ArrayList<>();

        public Builder(String id, String fullName, String email) {
            this.id = id;
            this.fullName = fullName;
            this.email = email;
        }

        public Builder phone(String phone) { this.phone = phone; return this; }

        public Builder level(String level) {
            if (!LEVELS.contains(level))
                throw new IllegalArgumentException("level must be one of " + LEVELS);
            this.level = level;
            return this;
        }

        public Builder major(String major) { this.major = major; return this; }

        public Builder scholarship(boolean scholarship) { this.scholarship = scholarship; return this; }

        public Builder addCourse(String course) { this.courses.add(course); return this; }

        /** Validates the data and creates the StudentAccount. */
        public StudentAccount build() {
            validateIdentity(id, fullName, email);
            return new StudentAccount(this);
        }
    }
}
