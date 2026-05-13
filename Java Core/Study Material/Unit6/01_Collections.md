# 01 — Collections Framework: ArrayList, TreeSet, HashMap & Deque

---

## 📖 Theory

### What Is the Collections Framework?

The **Java Collections Framework (JCF)** is a unified architecture for storing and manipulating groups of objects. It provides:
- **Interfaces** — abstract data types (List, Set, Map, Queue, Deque)
- **Implementations** — concrete classes (ArrayList, TreeSet, HashMap, ArrayDeque)
- **Algorithms** — utility methods in `Collections` class (sort, shuffle, reverse, etc.)

**Why use Collections over arrays?**
| Feature | Array | Collection |
|---|---|---|
| Size | Fixed | Dynamic |
| Type safety | Primitive + Object | Objects only (generics) |
| Built-in methods | None | Rich API |
| Null values | Allowed | Depends on type |

---

### Collections Framework Hierarchy

```
                    Iterable
                       │
                   Collection
              ┌────────┼────────┐
            List      Set     Queue
              │         │        │
         ArrayList  HashSet   Deque
         LinkedList TreeSet  ArrayDeque
         Vector    LinkedHashSet PriorityQueue

         Map (separate hierarchy)
           │
       HashMap
       TreeMap
       LinkedHashMap
```

---

### 1. ArrayList

`ArrayList` is a **resizable array** implementation of the `List` interface. Elements are ordered by insertion and allow duplicates.

**When to use:** When you need fast random access by index and frequent reads.

```java
import java.util.ArrayList;
import java.util.Collections;

ArrayList<String> names = new ArrayList<>();

// Add
names.add("Alice");
names.add("Bob");
names.add("Charlie");
names.add(1, "Zara");      // insert at index 1

// Access
System.out.println(names.get(0));      // Alice
System.out.println(names.size());      // 4

// Update
names.set(2, "Diana");

// Remove
names.remove("Bob");                   // by value
names.remove(0);                       // by index

// Search
System.out.println(names.contains("Diana"));   // true
System.out.println(names.indexOf("Diana"));    // index

// Iteration
for (String name : names) System.out.println(name);

// Sort
Collections.sort(names);
names.sort((a, b) -> a.compareTo(b));   // with lambda

// Other utilities
Collections.reverse(names);
Collections.shuffle(names);
System.out.println(Collections.min(names));
System.out.println(Collections.max(names));
```

---

### 2. TreeSet with Comparable and Comparator

`TreeSet` is a `Set` that stores **unique elements in sorted order** using a **Red-Black tree**. No duplicates allowed.

**When to use:** When you need unique elements that are always sorted.

#### Natural Ordering — `Comparable`

A class implements `Comparable<T>` to define its **natural sort order**:

```java
class Student implements Comparable<Student> {
    String name;
    double gpa;

    Student(String name, double gpa) {
        this.name = name; this.gpa = gpa;
    }

    @Override
    public int compareTo(Student other) {
        return Double.compare(other.gpa, this.gpa);  // descending GPA
    }

    @Override
    public String toString() { return name + "(" + gpa + ")"; }
}

TreeSet<Student> set = new TreeSet<>();
set.add(new Student("Alice", 3.9));
set.add(new Student("Bob", 3.5));
set.add(new Student("Charlie", 3.7));
// Automatically sorted by GPA descending
```

#### Custom Ordering — `Comparator`

Use `Comparator` when you want **different sort orders** or can't modify the class:

```java
// Sort by name alphabetically
Comparator<Student> byName = Comparator.comparing(s -> s.name);

// Sort by GPA ascending
Comparator<Student> byGpa = Comparator.comparingDouble(s -> s.gpa);

// Sort by GPA desc, then by name
Comparator<Student> byGpaDesc = Comparator
    .comparingDouble((Student s) -> s.gpa).reversed()
    .thenComparing(s -> s.name);

TreeSet<Student> sortedSet = new TreeSet<>(byName);
```

**Comparable vs Comparator:**

| Feature | Comparable | Comparator |
|---|---|---|
| Where defined | Inside the class | Separate class / lambda |
| Method | `compareTo(T o)` | `compare(T o1, T o2)` |
| Sort orders | One (natural) | Many (flexible) |
| Modifies class? | Yes | No |

---

### 3. HashMap

`HashMap` stores **key-value pairs** with O(1) average lookup time. Keys are unique; values can be duplicated.

**When to use:** When you need fast lookup by a unique key.

```java
import java.util.HashMap;
import java.util.Map;

HashMap<String, Integer> scores = new HashMap<>();

// Put
scores.put("Alice", 95);
scores.put("Bob", 82);
scores.put("Charlie", 91);
scores.put("Alice", 98);     // updates existing key

// Get
System.out.println(scores.get("Alice"));           // 98
System.out.println(scores.getOrDefault("Zara", 0)); // 0 if not found

// Check
System.out.println(scores.containsKey("Bob"));     // true
System.out.println(scores.containsValue(91));      // true

// Remove
scores.remove("Bob");

// Iterate
for (Map.Entry<String, Integer> entry : scores.entrySet()) {
    System.out.println(entry.getKey() + " → " + entry.getValue());
}

// Keys and values
scores.keySet().forEach(System.out::println);
scores.values().forEach(System.out::println);

// Compute
scores.put("Diana", scores.getOrDefault("Diana", 0) + 10);
scores.computeIfAbsent("Eve", k -> 75);
scores.merge("Alice", 5, Integer::sum);   // add 5 to Alice's score
```

---

### 4. Deque (Double-Ended Queue)

`Deque` (pronounced "deck") is a **double-ended queue** — you can add and remove from both front and back. `ArrayDeque` is the most common implementation.

**When to use:** As a stack (LIFO), queue (FIFO), or both.

```java
import java.util.ArrayDeque;
import java.util.Deque;

Deque<String> deque = new ArrayDeque<>();

// Add to front and back
deque.addFirst("B");
deque.addFirst("A");
deque.addLast("C");
deque.addLast("D");
// deque: [A, B, C, D]

// Peek (look without removing)
System.out.println(deque.peekFirst());  // A
System.out.println(deque.peekLast());   // D

// Remove from front and back
System.out.println(deque.removeFirst()); // A
System.out.println(deque.removeLast());  // D
// deque: [B, C]

// Use as Stack (LIFO)
Deque<Integer> stack = new ArrayDeque<>();
stack.push(1); stack.push(2); stack.push(3);
System.out.println(stack.pop());   // 3 (last in, first out)

// Use as Queue (FIFO)
Deque<Integer> queue = new ArrayDeque<>();
queue.offer(1); queue.offer(2); queue.offer(3);
System.out.println(queue.poll());  // 1 (first in, first out)
```

**Deque method summary:**

| Operation | Front | Back |
|---|---|---|
| Add | `addFirst()` / `offerFirst()` | `addLast()` / `offerLast()` |
| Remove | `removeFirst()` / `pollFirst()` | `removeLast()` / `pollLast()` |
| Peek | `peekFirst()` / `getFirst()` | `peekLast()` / `getLast()` |

---

### Collections Utility Class

```java
import java.util.Collections;

List<Integer> nums = new ArrayList<>(Arrays.asList(5, 2, 8, 1, 9));

Collections.sort(nums);                        // [1, 2, 5, 8, 9]
Collections.sort(nums, Collections.reverseOrder()); // [9, 8, 5, 2, 1]
Collections.reverse(nums);
Collections.shuffle(nums);
Collections.swap(nums, 0, 1);
System.out.println(Collections.min(nums));
System.out.println(Collections.max(nums));
System.out.println(Collections.frequency(nums, 5));  // count occurrences
Collections.fill(nums, 0);                     // fill all with 0
List<Integer> unmodifiable = Collections.unmodifiableList(nums);
```

---

## 🧪 Practice Questions

1. What is the Java Collections Framework? Name its three main components.
2. What is the difference between `ArrayList` and an array?
3. What does `TreeSet` guarantee about its elements? What interface must elements implement?
4. What is the difference between `Comparable` and `Comparator`?
5. What does `HashMap.getOrDefault("key", defaultValue)` do?
6. What is a `Deque`? How is it used as both a stack and a queue?
7. Can `HashMap` have duplicate keys? Duplicate values?
8. What is the time complexity of `get()` in `HashMap` and `TreeSet`?
9. What does `Collections.unmodifiableList()` return?
10. How do you iterate over a `HashMap`? Give two ways.

---

## 💻 Examples

### Example 1 – ArrayList CRUD Operations

```java
import java.util.*;

public class ArrayListDemo {
    public static void main(String[] args) {
        ArrayList<String> cities = new ArrayList<>();

        // Add
        cities.add("Mumbai");
        cities.add("Delhi");
        cities.add("Bengaluru");
        cities.add("Chennai");
        cities.add(1, "Kolkata");   // insert at index 1

        System.out.println("Cities: " + cities);
        System.out.println("Size: " + cities.size());

        // Access
        System.out.println("First: " + cities.get(0));
        System.out.println("Last:  " + cities.get(cities.size() - 1));

        // Update
        cities.set(2, "Hyderabad");
        System.out.println("After update: " + cities);

        // Remove
        cities.remove("Delhi");
        cities.remove(0);
        System.out.println("After remove: " + cities);

        // Search
        System.out.println("Contains Hyderabad: " + cities.contains("Hyderabad"));

        // Sort
        Collections.sort(cities);
        System.out.println("Sorted: " + cities);

        // Iterate with index
        System.out.println("\nIndexed:");
        for (int i = 0; i < cities.size(); i++) {
            System.out.printf("  [%d] %s%n", i, cities.get(i));
        }
    }
}
```

**Output:**
```
Cities: [Mumbai, Kolkata, Delhi, Bengaluru, Chennai]
Size: 5
First: Mumbai
Last:  Chennai
After update: [Mumbai, Kolkata, Hyderabad, Bengaluru, Chennai]
After remove: [Hyderabad, Bengaluru, Chennai]
Contains Hyderabad: true
Sorted: [Bengaluru, Chennai, Hyderabad]

Indexed:
  [0] Bengaluru
  [1] Chennai
  [2] Hyderabad
```

---

### Example 2 – TreeSet with Comparable and Comparator

```java
import java.util.*;

public class TreeSetDemo {

    static class Employee implements Comparable<Employee> {
        int id;
        String name;
        double salary;

        Employee(int id, String name, double salary) {
            this.id = id; this.name = name; this.salary = salary;
        }

        @Override
        public int compareTo(Employee other) {
            return Integer.compare(this.id, other.id);  // natural order: by ID
        }

        @Override
        public String toString() {
            return String.format("EMP%03d %-12s ₹%.0f", id, name, salary);
        }
    }

    public static void main(String[] args) {
        // Natural order (by ID via Comparable)
        TreeSet<Employee> byId = new TreeSet<>();
        byId.add(new Employee(103, "Charlie", 60000));
        byId.add(new Employee(101, "Alice",   75000));
        byId.add(new Employee(105, "Eve",     55000));
        byId.add(new Employee(102, "Bob",     80000));
        byId.add(new Employee(101, "Alice",   75000));  // duplicate ID — not added

        System.out.println("=== Sorted by ID (Comparable) ===");
        byId.forEach(System.out::println);

        // Custom order by salary descending (Comparator)
        TreeSet<Employee> bySalary = new TreeSet<>(
            Comparator.comparingDouble((Employee e) -> e.salary).reversed()
                      .thenComparingInt(e -> e.id)
        );
        bySalary.addAll(byId);

        System.out.println("\n=== Sorted by Salary Desc (Comparator) ===");
        bySalary.forEach(System.out::println);

        // TreeSet navigation methods
        System.out.println("\nFirst: " + byId.first());
        System.out.println("Last:  " + byId.last());
    }
}
```

**Output:**
```
=== Sorted by ID (Comparable) ===
EMP101 Alice        ₹75000
EMP102 Bob          ₹80000
EMP103 Charlie      ₹60000
EMP105 Eve          ₹55000

=== Sorted by Salary Desc (Comparator) ===
EMP102 Bob          ₹80000
EMP101 Alice        ₹75000
EMP103 Charlie      ₹60000
EMP105 Eve          ₹55000

First: EMP101 Alice        ₹75000
Last:  EMP105 Eve          ₹55000
```

---

### Example 3 – HashMap (Word Frequency Counter)

```java
import java.util.*;

public class HashMapDemo {
    public static void main(String[] args) {
        // Word frequency counter
        String text = "java is great java is powerful java collections are great";
        String[] words = text.split(" ");

        HashMap<String, Integer> freq = new HashMap<>();
        for (String word : words) {
            freq.merge(word, 1, Integer::sum);  // add 1 or update
        }

        System.out.println("=== Word Frequencies ===");
        freq.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .forEach(e -> System.out.printf("  %-15s : %d%n", e.getKey(), e.getValue()));

        // Student grades map
        HashMap<String, List<Integer>> grades = new HashMap<>();
        grades.put("Alice",   Arrays.asList(85, 92, 78, 95));
        grades.put("Bob",     Arrays.asList(70, 75, 80, 73));
        grades.put("Charlie", Arrays.asList(90, 88, 95, 92));

        System.out.println("\n=== Student Averages ===");
        grades.forEach((name, marks) -> {
            double avg = marks.stream().mapToInt(Integer::intValue).average().orElse(0);
            System.out.printf("  %-10s : %.1f%n", name, avg);
        });
    }
}
```

**Output:**
```
=== Word Frequencies ===
  java            : 3
  is              : 2
  great           : 2
  powerful        : 1
  collections     : 1
  are             : 1

=== Student Averages ===
  Alice      : 87.5
  Bob        : 74.5
  Charlie    : 91.3
```

---

### Example 4 – Deque as Stack and Queue

```java
import java.util.*;

public class DequeDemo {
    public static void main(String[] args) {
        // --- USE AS STACK (LIFO) ---
        System.out.println("=== Stack (LIFO) — Undo History ===");
        Deque<String> undoStack = new ArrayDeque<>();
        undoStack.push("Type 'Hello'");
        undoStack.push("Bold text");
        undoStack.push("Insert image");
        undoStack.push("Change font");

        System.out.println("Actions performed: " + undoStack);
        System.out.println("Undoing: " + undoStack.pop());
        System.out.println("Undoing: " + undoStack.pop());
        System.out.println("Remaining: " + undoStack);

        // --- USE AS QUEUE (FIFO) ---
        System.out.println("\n=== Queue (FIFO) — Print Spooler ===");
        Deque<String> printQueue = new ArrayDeque<>();
        printQueue.offer("Document1.pdf");
        printQueue.offer("Report.docx");
        printQueue.offer("Photo.jpg");

        System.out.println("Print queue: " + printQueue);
        while (!printQueue.isEmpty()) {
            System.out.println("Printing: " + printQueue.poll());
        }

        // --- USE AS DEQUE (both ends) ---
        System.out.println("\n=== Palindrome Checker (Deque) ===");
        String word = "racecar";
        Deque<Character> dq = new ArrayDeque<>();
        for (char c : word.toCharArray()) dq.addLast(c);

        boolean isPalindrome = true;
        while (dq.size() > 1) {
            if (dq.pollFirst() != dq.pollLast()) {
                isPalindrome = false;
                break;
            }
        }
        System.out.println("'" + word + "' is palindrome: " + isPalindrome);
    }
}
```

**Output:**
```
=== Stack (LIFO) — Undo History ===
Actions performed: [Change font, Insert image, Bold text, Type 'Hello']
Undoing: Change font
Undoing: Insert image
Remaining: [Bold text, Type 'Hello']

=== Queue (FIFO) — Print Spooler ===
Print queue: [Document1.pdf, Report.docx, Photo.jpg]
Printing: Document1.pdf
Printing: Report.docx
Printing: Photo.jpg

=== Palindrome Checker (Deque) ===
'racecar' is palindrome: true
```

---

### Example 5 – Generics with Collections (Full Inventory System)

```java
import java.util.*;

public class InventorySystem {

    static class Product implements Comparable<Product> {
        String id, name, category;
        double price;
        int stock;

        Product(String id, String name, String category, double price, int stock) {
            this.id = id; this.name = name; this.category = category;
            this.price = price; this.stock = stock;
        }

        @Override
        public int compareTo(Product o) { return this.name.compareTo(o.name); }

        @Override
        public String toString() {
            return String.format("%-8s %-15s %-12s ₹%8.2f  Qty:%3d",
                id, name, category, price, stock);
        }
    }

    public static void main(String[] args) {
        // HashMap: ID → Product
        HashMap<String, Product> catalog = new HashMap<>();
        List<Product> products = Arrays.asList(
            new Product("P001", "Laptop",     "Electronics", 55000, 15),
            new Product("P002", "Mouse",      "Electronics",   799, 200),
            new Product("P003", "Desk Chair", "Furniture",   8500, 30),
            new Product("P004", "Notebook",   "Stationery",    49, 500),
            new Product("P005", "Headphones", "Electronics", 2999, 75)
        );
        products.forEach(p -> catalog.put(p.id, p));

        // ArrayList: low stock alert
        ArrayList<Product> lowStock = new ArrayList<>();
        for (Product p : catalog.values())
            if (p.stock < 50) lowStock.add(p);
        Collections.sort(lowStock);

        // TreeSet: sorted by price
        TreeSet<Product> byPrice = new TreeSet<>(
            Comparator.comparingDouble((Product p) -> p.price));
        byPrice.addAll(catalog.values());

        // Deque: recent lookups cache
        Deque<String> recentLookups = new ArrayDeque<>();

        System.out.println("=== Full Catalog ===");
        System.out.printf("%-8s %-15s %-12s %10s  %5s%n","ID","Name","Category","Price","Qty");
        System.out.println("-".repeat(57));
        products.forEach(System.out::println);

        System.out.println("\n=== Low Stock Alert (< 50 units) ===");
        lowStock.forEach(p -> System.out.println("  ⚠ " + p));

        System.out.println("\n=== Products by Price (Cheapest First) ===");
        byPrice.forEach(p -> System.out.printf("  ₹%8.2f  %s%n", p.price, p.name));

        System.out.println("\n=== Lookup by ID ===");
        String[] lookups = {"P003", "P001", "P999"};
        for (String id : lookups) {
            Product found = catalog.get(id);
            if (found != null) {
                recentLookups.addFirst(id);
                if (recentLookups.size() > 3) recentLookups.removeLast();
                System.out.println("  Found: " + found);
            } else {
                System.out.println("  " + id + " → NOT FOUND");
            }
        }
        System.out.println("Recent lookups: " + recentLookups);
    }
}
```

**Output:**
```
=== Full Catalog ===
ID       Name            Category        Price  Qty
---------------------------------------------------------
P001     Laptop          Electronics  ₹55000.00  Qty: 15
P002     Mouse           Electronics  ₹  799.00  Qty:200
P003     Desk Chair      Furniture    ₹ 8500.00  Qty: 30
P004     Notebook        Stationery   ₹   49.00  Qty:500
P005     Headphones      Electronics  ₹ 2999.00  Qty: 75

=== Low Stock Alert (< 50 units) ===
  ⚠ P003     Desk Chair      Furniture    ₹ 8500.00  Qty: 30
  ⚠ P001     Laptop          Electronics  ₹55000.00  Qty: 15

=== Products by Price (Cheapest First) ===
  ₹    49.00  Notebook
  ₹   799.00  Mouse
  ₹  2999.00  Headphones
  ₹  8500.00  Desk Chair
  ₹ 55000.00  Laptop

=== Lookup by ID ===
  Found: P003     Desk Chair      Furniture    ₹ 8500.00  Qty: 30
  Found: P001     Laptop          Electronics  ₹55000.00  Qty: 15
  P999 → NOT FOUND
Recent lookups: [P001, P003]
```

---

## 📝 Summary

- **Collections Framework** provides ready-made data structures with type-safe generics.
- **`ArrayList`** — ordered, resizable, allows duplicates, O(1) access by index.
- **`TreeSet`** — unique elements, always sorted; uses `Comparable` (natural order) or `Comparator` (custom order).
- **`HashMap`** — key-value pairs, unique keys, O(1) average get/put, no ordering guaranteed.
- **`ArrayDeque`** — double-ended queue; use as stack (`push`/`pop`) or queue (`offer`/`poll`).
- **`Comparable`** defines the class's natural order (`compareTo`); **`Comparator`** defines external, flexible orders.
- Use `Collections` utility class for sort, reverse, shuffle, min, max, frequency.

---

*Next → [02 – JDBC: Java Database Programming](./02_JDBC.md)*
