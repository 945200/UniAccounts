package com.uniaccounts;

import com.uniaccounts.audit.AuditLogger;
import com.uniaccounts.model.StudentAccount;
import com.uniaccounts.prototype.TemplateRegistry;
import com.uniaccounts.repository.AccountRepository;

/** Demo of Work 1: Builder + Singleton + Prototype. */
public class Main {

    public static void main(String[] args) {
        AccountRepository repo = AccountRepository.getInstance();

        // ---------- 1) BUILDER ----------
        System.out.println("===== 1) BUILDER =====");
        StudentAccount s1 = new StudentAccount.Builder("20240001", "Amine Haddad", "amine.haddad@univ.dz")
                .build();                                  // only the mandatory fields
        StudentAccount s2 = new StudentAccount.Builder("20240002", "Sara Meziane", "sara.meziane@univ.dz")
                .phone("0550123456").level("M1").major("Computer Science")
                .scholarship(true).addCourse("Design Patterns").addCourse("Databases")
                .build();                                  // all the fields
        repo.add(s1);
        repo.add(s2);
        System.out.println(s1);
        System.out.println(s2);

        try {
            new StudentAccount.Builder("20240003", "Test Student", "email-without-at").build();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid account refused: " + e.getMessage());
        }

        // ---------- 2) SINGLETON ----------
        System.out.println("\n===== 2) SINGLETON =====");
        AccountRepository repo2 = AccountRepository.getInstance();
        AuditLogger log1 = AuditLogger.getInstance();
        AuditLogger log2 = AuditLogger.getInstance();
        System.out.println("repo == repo2 ? " + (repo == repo2));
        System.out.println("log1 == log2  ? " + (log1 == log2));
        try {
            repo2.add(new StudentAccount.Builder("20240001", "Duplicate", "dup@univ.dz").build());
        } catch (IllegalArgumentException e) {
            System.out.println("Duplicate refused: " + e.getMessage());
        }

        // ---------- 3) PROTOTYPE ----------
        System.out.println("\n===== 3) PROTOTYPE =====");
        StudentAccount template = new StudentAccount.Builder("TEMPLATE", "Template L2", "template@univ.dz")
                .level("L2").major("Computer Science")
                .addCourse("Algorithms").addCourse("Java OOP").addCourse("Databases")
                .build();
        TemplateRegistry registry = new TemplateRegistry();
        registry.register("L2-INFO", template);

        StudentAccount a = registry.createFrom("L2-INFO", "20240101", "Yacine Bensalem", "yacine@univ.dz");
        StudentAccount b = registry.createFrom("L2-INFO", "20240102", "Nadia Ouali", "nadia@univ.dz");
        StudentAccount c = registry.createFrom("L2-INFO", "20240103", "Lina Kaci", "lina@univ.dz");
        repo.add(a);
        repo.add(b);
        repo.add(c);
        System.out.println(a);
        System.out.println(b);

        b.addCourse("English");   // we change only b
        System.out.println("After adding a course to b only:");
        System.out.println("  template courses = " + template.getCourses());
        System.out.println("  a courses        = " + a.getCourses());
        System.out.println("  b courses        = " + b.getCourses());

        // ---------- Final state ----------
        System.out.println("\n===== Repository: " + repo.count() + " accounts =====");
        repo.findAll().forEach(acc -> System.out.println(acc.getId() + " | " + acc.getFullName()
                + " | " + acc.getLevel() + " | " + acc.getMajor()));

        System.out.println("\n===== Audit log =====");
        AuditLogger.getInstance().printAll();
    }
}
