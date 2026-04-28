# 01 — I/O Fundamentals: Streams, File I/O & Serialization

---

## 📖 Theory

### What Is I/O in Java?

**I/O (Input/Output)** refers to the process of reading data *into* a program and writing data *out* of a program. Sources and destinations can be:
- Files on disk
- Keyboard / Console
- Network sockets
- In-memory byte arrays

Java organizes all I/O through the concept of **streams**.

---

### What Is a Stream?

A **stream** is a sequential flow of data. Think of it as a pipeline:

```
Source ──► [InputStream / Reader] ──► Program ──► [OutputStream / Writer] ──► Destination
```

**Two families of streams:**

| Family | Base Classes | Data Unit | Use For |
|---|---|---|---|
| **Byte Streams** | `InputStream`, `OutputStream` | 8-bit bytes | Binary files (images, audio, objects) |
| **Character Streams** | `Reader`, `Writer` | 16-bit Unicode chars | Text files |

---

### Stream Class Hierarchy

```
InputStream (abstract)
├── FileInputStream
├── BufferedInputStream
├── ByteArrayInputStream
└── ObjectInputStream          ← for deserialization

OutputStream (abstract)
├── FileOutputStream
├── BufferedOutputStream
├── ByteArrayOutputStream
└── ObjectOutputStream         ← for serialization

Reader (abstract)
├── FileReader
├── BufferedReader             ← most common for text files
├── InputStreamReader
└── StringReader

Writer (abstract)
├── FileWriter
├── BufferedWriter             ← most common for text files
├── PrintWriter
└── StringWriter
```

---

### Reading from Console

```java
import java.util.Scanner;

Scanner sc = new Scanner(System.in);

String name  = sc.nextLine();    // read entire line
int age      = sc.nextInt();     // read integer
double price = sc.nextDouble();  // read double
```

---

### Writing to Files — FileWriter / BufferedWriter

```java
import java.io.*;

// FileWriter — simple, but unbuffered
try (FileWriter fw = new FileWriter("output.txt")) {
    fw.write("Hello, File!\n");
    fw.write("Second line\n");
}

// BufferedWriter — faster (wraps FileWriter, uses buffer)
try (BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"))) {
    bw.write("Hello, Buffered!");
    bw.newLine();
    bw.write("Second line");
}

// Append mode — pass true as second argument
try (FileWriter fw = new FileWriter("output.txt", true)) {
    fw.write("This is appended.\n");
}
```

---

### Reading from Files — FileReader / BufferedReader

```java
// FileReader — reads character by character (slow)
try (FileReader fr = new FileReader("input.txt")) {
    int ch;
    while ((ch = fr.read()) != -1) {
        System.out.print((char) ch);
    }
}

// BufferedReader — reads line by line (preferred)
try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
    String line;
    while ((line = br.readLine()) != null) {
        System.out.println(line);
    }
}
```

---

### Byte Streams — FileInputStream / FileOutputStream

Used for binary data (images, audio, compiled files, etc.):

```java
// Write bytes
try (FileOutputStream fos = new FileOutputStream("data.bin")) {
    fos.write(65);              // writes byte 65 (ASCII 'A')
    fos.write(new byte[]{66, 67, 68});   // writes B, C, D
}

// Read bytes
try (FileInputStream fis = new FileInputStream("data.bin")) {
    int b;
    while ((b = fis.read()) != -1) {
        System.out.print((char) b);   // A B C D
    }
}
```

---

### PrintWriter — Convenient Writing

`PrintWriter` wraps other writers and provides `print()`, `println()`, `printf()`:

```java
try (PrintWriter pw = new PrintWriter(new FileWriter("report.txt"))) {
    pw.println("Student Report");
    pw.println("=============");
    pw.printf("%-15s %5s %6s%n", "Name", "Age", "GPA");
    pw.printf("%-15s %5d %6.2f%n", "Alice", 21, 3.9);
}
```

---

### Serialization — Writing Objects to Files

**Serialization** converts a Java object into a byte stream so it can be saved to a file or sent over a network.

**Deserialization** is the reverse — reconstructing the object from the byte stream.

**Requirements:**
- The class must implement `java.io.Serializable` (marker interface — no methods to implement)
- All fields must also be serializable (primitives and Strings are automatically)
- Fields marked `transient` are NOT serialized

```java
import java.io.Serializable;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;  // version control
    String name;
    int age;
    transient String password;   // NOT saved to file

    Student(String name, int age, String password) {
        this.name = name;
        this.age = age;
        this.password = password;
    }
}
```

**Writing (Serialization):**
```java
try (ObjectOutputStream oos = new ObjectOutputStream(
        new FileOutputStream("student.ser"))) {
    Student s = new Student("Alice", 21, "secret123");
    oos.writeObject(s);
    System.out.println("Object saved.");
}
```

**Reading (Deserialization):**
```java
try (ObjectInputStream ois = new ObjectInputStream(
        new FileInputStream("student.ser"))) {
    Student s = (Student) ois.readObject();
    System.out.println("Name: " + s.name);
    System.out.println("Age: " + s.age);
    System.out.println("Password: " + s.password);  // null — transient!
}
```

---

### The `serialVersionUID`

```java
private static final long serialVersionUID = 1L;
```

This is a version identifier. If you modify the class structure after serializing objects, Java uses this to verify compatibility. Always declare it explicitly to avoid `InvalidClassException`.

---

## 🧪 Practice Questions

1. What is a stream in Java? What are the two families of streams?
2. What is the difference between byte streams and character streams?
3. Why should you use `BufferedReader` instead of `FileReader` directly for reading text files?
4. What does "append mode" mean in `FileWriter`? How do you enable it?
5. What is serialization? What interface must a class implement to be serializable?
6. What is the `transient` keyword? Give a practical use case.
7. What is `serialVersionUID` and why should you always declare it explicitly?
8. What happens if you try to serialize an object whose field is not serializable?
9. What does `readLine()` return when it reaches the end of a file?

---

## 💻 Examples

### Example 1 – Write and Read a Text File

```java
import java.io.*;

public class TextFileDemo {
    public static void main(String[] args) throws IOException {
        String filename = "students.txt";

        // --- WRITE ---
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write("Alice,21,3.9");
            bw.newLine();
            bw.write("Bob,22,3.5");
            bw.newLine();
            bw.write("Charlie,20,3.7");
            bw.newLine();
        }
        System.out.println("File written successfully.");

        // --- READ ---
        System.out.println("\nFile contents:");
        System.out.println("-".repeat(30));
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNum = 1;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                System.out.printf("%-3d %-10s Age: %s  GPA: %s%n",
                    lineNum++, parts[0], parts[1], parts[2]);
            }
        }
    }
}
```

**Output:**
```
File written successfully.

File contents:
------------------------------
1   Alice      Age: 21  GPA: 3.9
2   Bob        Age: 22  GPA: 3.5
3   Charlie    Age: 20  GPA: 3.7
```

---

### Example 2 – Append to File

```java
import java.io.*;

public class AppendDemo {
    public static void main(String[] args) throws IOException {
        String log = "activity.log";

        // Write initial entries
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(log))) {
            bw.write("[INFO] Server started");
            bw.newLine();
        }

        // Append more entries
        String[] events = {"[INFO] User Alice logged in", "[WARN] Disk usage at 80%", "[INFO] Backup completed"};
        for (String event : events) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(log, true))) {
                bw.write(event);
                bw.newLine();
            }
        }

        // Read and display
        System.out.println("Log file contents:");
        try (BufferedReader br = new BufferedReader(new FileReader(log))) {
            String line;
            while ((line = br.readLine()) != null) System.out.println("  " + line);
        }
    }
}
```

**Output:**
```
Log file contents:
  [INFO] Server started
  [INFO] User Alice logged in
  [WARN] Disk usage at 80%
  [INFO] Backup completed
```

---

### Example 3 – Serialization and Deserialization

```java
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializationDemo {

    static class Product implements Serializable {
        private static final long serialVersionUID = 1L;
        String name;
        double price;
        int stock;
        transient String internalCode;  // not saved

        Product(String name, double price, int stock, String code) {
            this.name = name;
            this.price = price;
            this.stock = stock;
            this.internalCode = code;
        }

        @Override
        public String toString() {
            return String.format("%-15s ₹%8.2f  Stock: %3d  Code: %s",
                name, price, stock, internalCode);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String file = "products.ser";

        // Create products
        List<Product> inventory = new ArrayList<>();
        inventory.add(new Product("Laptop", 55000, 15, "LAP-001"));
        inventory.add(new Product("Mouse", 799, 200, "MOU-002"));
        inventory.add(new Product("Keyboard", 1299, 150, "KEY-003"));

        // Serialize
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(inventory);
            System.out.println("Inventory saved (" + inventory.size() + " products).");
        }

        // Deserialize
        System.out.println("\nLoaded from file:");
        System.out.printf("%-15s %10s  %10s  %s%n", "Product", "Price", "Stock", "Code");
        System.out.println("-".repeat(55));

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            @SuppressWarnings("unchecked")
            List<Product> loaded = (List<Product>) ois.readObject();
            for (Product p : loaded) {
                System.out.println(p);  // internalCode will be null (transient)
            }
        }
    }
}
```

**Output:**
```
Inventory saved (3 products).

Loaded from file:
Product          Price        Stock  Code
-------------------------------------------------------
Laptop           ₹55000.00  Stock:  15  Code: null
Mouse            ₹  799.00  Stock: 200  Code: null
Keyboard         ₹ 1299.00  Stock: 150  Code: null
```

*(Code is `null` because `internalCode` is `transient` — not serialized)*

---

### Example 4 – Copy a File (Byte Stream)

```java
import java.io.*;

public class FileCopyDemo {
    public static void main(String[] args) throws IOException {
        // Create source file
        try (FileWriter fw = new FileWriter("source.txt")) {
            fw.write("This is the original file content.\nLine 2.\nLine 3.");
        }

        // Copy using byte streams
        try (
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream("source.txt"));
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("copy.txt"))
        ) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
        }

        // Verify copy
        System.out.println("Copy contents:");
        try (BufferedReader br = new BufferedReader(new FileReader("copy.txt"))) {
            String line;
            while ((line = br.readLine()) != null) System.out.println("  " + line);
        }
        System.out.println("File copied successfully.");
    }
}
```

**Output:**
```
Copy contents:
  This is the original file content.
  Line 2.
  Line 3.
File copied successfully.
```

---

## 📝 Summary

- Java I/O uses **streams** — byte streams (`InputStream`/`OutputStream`) for binary data, character streams (`Reader`/`Writer`) for text.
- Always use **try-with-resources** to ensure streams are closed automatically.
- **`BufferedReader`/`BufferedWriter`** are preferred for text files — they reduce disk I/O via buffering.
- **`FileWriter(path, true)`** opens in append mode — existing content is preserved.
- **Serialization** saves Java objects to byte streams using `ObjectOutputStream`; requires `implements Serializable`.
- **`transient`** fields are skipped during serialization (use for passwords, caches, connections).
- Always declare `serialVersionUID` explicitly for version stability.

---

*Next → [02 – Multithreading](./02_Multithreading.md)*
