# UniAccounts - Work 1 (Design Patterns: Creational)

Student accounts management system. Patterns used: **Builder**, **Singleton**, **Prototype**.

## Project structure
```
UniAccounts/
├── src/com/uniaccounts/
│   ├── Main.java                         (demo)
│   ├── model/StudentAccount.java         (Builder + Prototype)
│   ├── prototype/AccountPrototype.java   (Prototype interface)
│   ├── prototype/TemplateRegistry.java   (Prototype registry)
│   ├── repository/AccountRepository.java (Singleton)
│   └── audit/AuditLogger.java            (Singleton)
└── docs/                                 (this file, run output, report)
```

## How to run
Requirements: JDK 17 or later.

```bash
# from the project root (UniAccounts/)
mkdir out
javac -encoding UTF-8 -d out $(find src -name "*.java")     # Linux / macOS
java -cp out com.uniaccounts.Main
```
Windows (PowerShell):
```powershell
mkdir out
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp out com.uniaccounts.Main
```
Or open the folder in IntelliJ / Eclipse, mark `src` as Sources Root and run `Main`.

## Where is each pattern?
| Pattern   | Class(es) |
|-----------|-----------|
| Builder   | `StudentAccount.Builder` (builds `StudentAccount`) |
| Singleton | `AccountRepository`, `AuditLogger` |
| Prototype | `AccountPrototype`, `StudentAccount.copy()`, `TemplateRegistry` |
