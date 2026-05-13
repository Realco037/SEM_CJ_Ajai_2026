# 02 — JDBC: Java Database Programming

---

## 📖 Theory

### What Is JDBC?

**JDBC (Java Database Connectivity)** is a Java API that allows Java programs to connect to and interact with relational databases. It provides a standard interface so the same Java code can work with different databases (MySQL, PostgreSQL, SQLite, Oracle, etc.) by simply switching the **driver**.

**JDBC is part of** `java.sql` package.

---

### JDBC Architecture

```
Java Application
      │
   JDBC API (java.sql)
      │
   JDBC Driver Manager
      │
   ┌──────────────────────────────────────┐
   │  JDBC Driver (vendor-specific .jar)  │
   └──────────────────────────────────────┘
      │
   Database (MySQL / PostgreSQL / SQLite...)
```

---

### JDBC Driver Types

| Type | Name | Description |
|---|---|---|
| Type 1 | JDBC-ODBC Bridge | Translates JDBC to ODBC; deprecated |
| Type 2 | Native-API Driver | Uses native client libraries |
| Type 3 | Network Protocol Driver | Uses middleware server |
| Type 4 | **Thin Driver (Pure Java)** | Direct DB connection; **most commonly used** |

> **Always use Type 4** in modern Java applications. Each database provides its own Type 4 driver as a `.jar` file.

---

### Key JDBC Interfaces

| Interface | Role |
|---|---|
| `DriverManager` | Manages drivers; creates connections |
| `Connection` | Represents a session with the database |
| `Statement` | Executes static SQL queries |
| `PreparedStatement` | Executes parameterized SQL queries (safer, faster) |
| `CallableStatement` | Calls stored procedures |
| `ResultSet` | Holds query results; navigate row by row |
| `ResultSetMetaData` | Describes the columns of a ResultSet |

---

### JDBC Steps (Standard Flow)

```
1. Load/Register driver      Class.forName("com.mysql.cj.jdbc.Driver")
         ↓
2. Create Connection         DriverManager.getConnection(url, user, pass)
         ↓
3. Create Statement          conn.createStatement()  or  conn.prepareStatement(sql)
         ↓
4. Execute Query/Update      stmt.executeQuery(sql)  or  stmt.executeUpdate(sql)
         ↓
5. Process Results           ResultSet rs = stmt.executeQuery(...)
                             while (rs.next()) { rs.getString(...) }
         ↓
6. Close Resources           rs.close(); stmt.close(); conn.close();
```

---

### Connection URL Format

```java
// MySQL
String url = "jdbc:mysql://localhost:3306/dbname";

// PostgreSQL
String url = "jdbc:postgresql://localhost:5432/dbname";

// SQLite (file-based — great for practice, no server needed)
String url = "jdbc:sqlite:mydb.db";

// H2 (in-memory — perfect for demos and testing)
String url = "jdbc:h2:mem:testdb";
```

---

### CRUD with JDBC

#### CREATE (INSERT)
```java
String sql = "INSERT INTO students (name, age, gpa) VALUES (?, ?, ?)";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setString(1, "Alice");
ps.setInt(2, 21);
ps.setDouble(3, 3.9);
int rowsAffected = ps.executeUpdate();
```

#### READ (SELECT)
```java
String sql = "SELECT * FROM students WHERE gpa > ?";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setDouble(1, 3.5);
ResultSet rs = ps.executeQuery();
while (rs.next()) {
    int id      = rs.getInt("id");
    String name = rs.getString("name");
    double gpa  = rs.getDouble("gpa");
    System.out.println(id + " | " + name + " | " + gpa);
}
```

#### UPDATE
```java
String sql = "UPDATE students SET gpa = ? WHERE name = ?";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setDouble(1, 4.0);
ps.setString(2, "Alice");
ps.executeUpdate();
```

#### DELETE
```java
String sql = "DELETE FROM students WHERE id = ?";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setInt(1, 5);
ps.executeUpdate();
```

---

### Statement vs PreparedStatement

| Feature | Statement | PreparedStatement |
|---|---|---|
| SQL | Hardcoded | Parameterized (`?`) |
| SQL Injection | Vulnerable | **Safe** |
| Reuse | New compile each time | Compiled once, reused |
| Performance | Slower for repeated queries | Faster |
| Recommended? | Only for no-parameter queries | **Always prefer** |

---

### ResultSet Navigation

```java
ResultSet rs = stmt.executeQuery("SELECT * FROM students");

// Move forward
while (rs.next()) { ... }        // row by row

// Get column values
rs.getInt("id")                  // by column name
rs.getInt(1)                     // by column index (1-based)
rs.getString("name")
rs.getDouble("gpa")
rs.getDate("enrollment_date")
rs.wasNull()                     // check if last column was NULL
```

---

### Transaction Management

By default, JDBC uses **auto-commit** (every statement is committed immediately). For operations that must succeed or fail together, use transactions:

```java
conn.setAutoCommit(false);   // disable auto-commit
try {
    // multiple operations
    ps1.executeUpdate();
    ps2.executeUpdate();
    conn.commit();           // commit all
} catch (SQLException e) {
    conn.rollback();         // undo all on error
} finally {
    conn.setAutoCommit(true);
}
```

---

### Connecting to Non-Conventional Databases

Java's JDBC can connect to many non-standard databases:

| Database | Driver JAR | URL Format |
|---|---|---|
| **SQLite** | `sqlite-jdbc-x.x.jar` | `jdbc:sqlite:path/to/db.db` |
| **H2 (in-memory)** | `h2-x.x.jar` | `jdbc:h2:mem:dbname` |
| **MongoDB** | `mongo-java-driver` | (uses MongoDB API, not JDBC) |
| **MS SQL Server** | `mssql-jdbc.jar` | `jdbc:sqlserver://server:1433;databaseName=db` |
| **Oracle** | `ojdbc8.jar` | `jdbc:oracle:thin:@localhost:1521:orcl` |

---

## 🧪 Practice Questions

1. What is JDBC? Why is it useful?
2. What are the 6 standard steps to interact with a database using JDBC?
3. What is the difference between `Statement` and `PreparedStatement`?
4. What is SQL injection? How does `PreparedStatement` prevent it?
5. What does `ResultSet.next()` return when it reaches the end of results?
6. What is `auto-commit` in JDBC? When should you disable it?
7. What is the purpose of `conn.rollback()`?
8. Name the four types of JDBC drivers. Which is preferred today?
9. What does `DriverManager.getConnection()` return?
10. What JDBC class would you use to call a stored procedure?

---

## 💻 Examples

### Example 1 – Full CRUD with SQLite (No Server Required)

> **Setup:** Add `sqlite-jdbc` to your classpath. For Maven: `<dependency>groupId: org.xerial, artifactId: sqlite-jdbc</dependency>`
> Or for demo purposes, this shows the complete structure — swap the URL for any database.

```java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDB {

    static final String DB_URL = "jdbc:sqlite:students.db";

    // Create table
    static void createTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS students (
                id      INTEGER PRIMARY KEY AUTOINCREMENT,
                name    TEXT    NOT NULL,
                age     INTEGER NOT NULL,
                gpa     REAL    NOT NULL,
                course  TEXT    NOT NULL
            )
        """;
        conn.createStatement().execute(sql);
        System.out.println("Table ready.");
    }

    // INSERT
    static int addStudent(Connection conn, String name, int age, double gpa, String course)
            throws SQLException {
        String sql = "INSERT INTO students (name, age, gpa, course) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setDouble(3, gpa);
            ps.setString(4, course);
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            return keys.next() ? keys.getInt(1) : -1;
        }
    }

    // SELECT ALL
    static List<String> getAllStudents(Connection conn) throws SQLException {
        List<String> results = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY gpa DESC";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                results.add(String.format("  [%d] %-12s Age:%-3d GPA:%.1f  %s",
                    rs.getInt("id"), rs.getString("name"),
                    rs.getInt("age"), rs.getDouble("gpa"),
                    rs.getString("course")));
            }
        }
        return results;
    }

    // SELECT with filter
    static void getByMinGpa(Connection conn, double minGpa) throws SQLException {
        String sql = "SELECT name, gpa FROM students WHERE gpa >= ? ORDER BY gpa DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, minGpa);
            ResultSet rs = ps.executeQuery();
            System.out.println("  Students with GPA >= " + minGpa + ":");
            while (rs.next()) {
                System.out.printf("    %-12s %.1f%n", rs.getString("name"), rs.getDouble("gpa"));
            }
        }
    }

    // UPDATE
    static void updateGpa(Connection conn, int id, double newGpa) throws SQLException {
        String sql = "UPDATE students SET gpa = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, newGpa);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();
            System.out.println("  Updated " + rows + " row(s).");
        }
    }

    // DELETE
    static void deleteStudent(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            System.out.println("  Deleted " + rows + " row(s).");
        }
    }

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            System.out.println("Connected to SQLite database.\n");

            createTable(conn);

            // INSERT
            System.out.println("--- INSERT ---");
            int id1 = addStudent(conn, "Alice",   21, 3.9, "CSE");
            int id2 = addStudent(conn, "Bob",     22, 3.5, "ECE");
            int id3 = addStudent(conn, "Charlie", 20, 3.7, "CSE");
            int id4 = addStudent(conn, "Diana",   23, 3.8, "IT");
            System.out.println("Inserted 4 students. Last IDs: " + id1 + "," + id2 + "," + id3 + "," + id4);

            // SELECT ALL
            System.out.println("\n--- SELECT ALL (sorted by GPA) ---");
            getAllStudents(conn).forEach(System.out::println);

            // SELECT with filter
            System.out.println("\n--- SELECT WHERE GPA >= 3.7 ---");
            getByMinGpa(conn, 3.7);

            // UPDATE
            System.out.println("\n--- UPDATE Alice's GPA to 4.0 ---");
            updateGpa(conn, id1, 4.0);
            getAllStudents(conn).forEach(System.out::println);

            // DELETE
            System.out.println("\n--- DELETE Bob ---");
            deleteStudent(conn, id2);
            getAllStudents(conn).forEach(System.out::println);

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
}
```

**Output:**
```
Connected to SQLite database.

Table ready.
--- INSERT ---
Inserted 4 students. Last IDs: 1,2,3,4

--- SELECT ALL (sorted by GPA) ---
  [1] Alice        Age:21  GPA:3.9  CSE
  [4] Diana        Age:23  GPA:3.8  IT
  [3] Charlie      Age:20  GPA:3.7  CSE
  [2] Bob          Age:22  GPA:3.5  ECE

--- SELECT WHERE GPA >= 3.7 ---
  Students with GPA >= 3.7:
    Alice        3.9
    Diana        3.8
    Charlie      3.7

--- UPDATE Alice's GPA to 4.0 ---
  Updated 1 row(s).
  [1] Alice        Age:21  GPA:4.0  CSE
  [4] Diana        Age:23  GPA:3.8  IT
  [3] Charlie      Age:20  GPA:3.7  CSE
  [2] Bob          Age:22  GPA:3.5  ECE

--- DELETE Bob ---
  Deleted 1 row(s).
  [1] Alice        Age:21  GPA:4.0  CSE
  [4] Diana        Age:23  GPA:3.8  IT
  [3] Charlie      Age:20  GPA:3.7  CSE
```

---

### Example 2 – Transaction (Bank Transfer)

```java
import java.sql.*;

public class BankTransferJDBC {

    static void transfer(Connection conn, int fromId, int toId, double amount)
            throws SQLException {
        conn.setAutoCommit(false);   // START TRANSACTION
        try {
            // Debit
            PreparedStatement debit = conn.prepareStatement(
                "UPDATE accounts SET balance = balance - ? WHERE id = ? AND balance >= ?");
            debit.setDouble(1, amount);
            debit.setInt(2, fromId);
            debit.setDouble(3, amount);
            int rows = debit.executeUpdate();

            if (rows == 0) throw new SQLException("Insufficient balance in account " + fromId);

            // Credit
            PreparedStatement credit = conn.prepareStatement(
                "UPDATE accounts SET balance = balance + ? WHERE id = ?");
            credit.setDouble(1, amount);
            credit.setInt(2, toId);
            credit.executeUpdate();

            conn.commit();   // SUCCESS — commit both
            System.out.printf("Transferred ₹%.2f from Account#%d to Account#%d%n",
                amount, fromId, toId);

        } catch (SQLException e) {
            conn.rollback();   // FAILURE — undo both
            System.out.println("Transfer failed, rolled back: " + e.getMessage());
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public static void main(String[] args) throws SQLException {
        // Setup in-memory H2 or SQLite for demo
        // This shows the pattern — actual DB connection needed to run
        System.out.println("Transaction pattern demonstrated above.");
        System.out.println("Key points:");
        System.out.println("  setAutoCommit(false) — begin transaction");
        System.out.println("  commit()             — save all changes");
        System.out.println("  rollback()           — undo all changes");
    }
}
```

---

### Example 3 – ResultSetMetaData (Generic Table Printer)

```java
import java.sql.*;

public class GenericQueryPrinter {

    // Prints ANY query result without knowing the columns
    static void printResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        int cols = meta.getColumnCount();

        // Print header
        for (int i = 1; i <= cols; i++) {
            System.out.printf("%-15s", meta.getColumnName(i));
        }
        System.out.println();
        System.out.println("-".repeat(cols * 15));

        // Print rows
        while (rs.next()) {
            for (int i = 1; i <= cols; i++) {
                System.out.printf("%-15s", rs.getString(i));
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // Pattern — use with any active Connection and query
        System.out.println("GenericQueryPrinter uses ResultSetMetaData to:");
        System.out.println("  - Read column count: meta.getColumnCount()");
        System.out.println("  - Read column names: meta.getColumnName(i)");
        System.out.println("  - Read column types: meta.getColumnTypeName(i)");
        System.out.println("Works for ANY SELECT query dynamically.");
    }
}
```

---

## 📝 Summary

- **JDBC** is the standard Java API for relational database access using `java.sql`.
- The 6 JDBC steps: **Load driver → Get Connection → Create Statement → Execute → Process ResultSet → Close**.
- Always use **`PreparedStatement`** over `Statement` to prevent SQL injection and improve performance.
- **`ResultSet`** is navigated with `rs.next()` and values read with `rs.getString()`, `rs.getInt()`, etc.
- **Transactions** group multiple operations — use `setAutoCommit(false)`, `commit()`, and `rollback()`.
- **Type 4 (thin/pure Java) drivers** are the standard for modern applications.
- JDBC can connect to SQLite, H2, MySQL, PostgreSQL, Oracle, and more — only the URL and driver JAR changes.

---

*Previous → [01 – Collections](./01_Collections.md)*  
*End of Unit 6 — Course Complete!*
