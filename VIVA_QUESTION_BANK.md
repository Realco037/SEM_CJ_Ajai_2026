# 🎓 Java Programming — Complete Viva Question Bank
### CSE310: Programming in Java | All Units Covered

> **How to use this:** Read the question, think of your answer, then reveal the answer below it.  
> Questions marked 🔥 are most frequently asked in vivas.

---

## 📚 Unit I — Java Fundamentals

### Introduction to Java

**Q1. 🔥 What is Java? Name its key features.**
> Java is a high-level, object-oriented, platform-independent programming language created by James Gosling at Sun Microsystems in 1991. Key features: Simple, Object-Oriented, Platform Independent, Secure, Robust, Multithreaded, High-Performance, Distributed, Dynamic.

**Q2. 🔥 What does "Write Once, Run Anywhere" mean in Java?**
> Java source code is compiled into **bytecode** (not machine code). This bytecode runs on any machine that has a JVM installed, regardless of the underlying OS or hardware. The JVM translates bytecode to native machine code at runtime — so the same `.class` file runs on Windows, Linux, and macOS without recompilation.

**Q3. 🔥 What is the difference between JDK, JRE, and JVM?**
> - **JVM (Java Virtual Machine):** Executes bytecode; provides platform independence. Platform-specific.
> - **JRE (Java Runtime Environment):** JVM + Java standard class libraries. Used to *run* Java programs.
> - **JDK (Java Development Kit):** JRE + development tools (javac, javadoc, jar, jdb). Used to *develop* Java programs.
> - Relationship: JDK ⊃ JRE ⊃ JVM.

**Q4. What is bytecode? Who produces it and who consumes it?**
> Bytecode is the intermediate, platform-neutral representation of Java code (stored in `.class` files). It is produced by the **Java compiler (`javac`)** and consumed/executed by the **JVM**.

**Q5. What is the role of the `main()` method in Java?**
> `public static void main(String[] args)` is the entry point of every standalone Java application. The JVM looks for this exact signature to start execution. `static` means it can be called without creating an object; `String[] args` receives command-line arguments.

**Q6. 🔥 What are command-line arguments? How are they accessed?**
> Command-line arguments are values passed to a Java program when it is run from the terminal: `java MyApp Alice 25`. They are received as a `String` array via `args` in the `main` method. `args[0]` = "Alice", `args[1]` = "25". Always received as Strings — convert with `Integer.parseInt()` if needed.

**Q7. Why must the filename match the public class name in Java?**
> The Java compiler requires this as a rule. When you compile `MyClass.java`, it produces `MyClass.class`. The JVM uses the filename to locate the correct class. Having mismatched names would make it impossible to locate and load the class reliably.

---

### Data Types, Variables & Operators

**Q8. 🔥 List all 8 primitive data types in Java with their sizes.**
> `byte` (1B), `short` (2B), `int` (4B), `long` (8B), `float` (4B), `double` (8B), `char` (2B), `boolean` (~1 bit). Note: `char` is 2 bytes in Java (Unicode), unlike C/C++ where it's 1 byte.

**Q9. 🔥 What is the difference between widening and narrowing type conversion?**
> - **Widening (implicit):** Smaller type → larger type automatically. No data loss. E.g., `int` → `double`.
> - **Narrowing (explicit):** Larger type → smaller type; requires a cast. May lose data. E.g., `double d = 9.99; int i = (int) d;` → `i = 9` (decimal truncated).

**Q10. What is a Wrapper class? Why is it needed?**
> Wrapper classes (`Integer`, `Double`, `Character`, etc.) wrap primitive types as objects. They are needed because Java collections (like `ArrayList`) only work with objects, not primitives. They also provide utility methods like `Integer.parseInt()`, `Integer.toBinaryString()`, `Character.isDigit()`, etc.

**Q11. 🔥 What is autoboxing and unboxing?**
> - **Autoboxing:** Automatic conversion of a primitive to its wrapper object. E.g., `Integer x = 5;` (int → Integer).
> - **Unboxing:** Automatic conversion of a wrapper object to its primitive. E.g., `int y = x;` (Integer → int).
> Introduced in Java 5.

**Q12. What is the difference between `==` and `.equals()` for String comparison?**
> `==` compares **references** (memory addresses). `.equals()` compares **content**. Two String objects with the same text may be different objects in memory, so `==` can return false even when the content is identical. Always use `.equals()` for String content comparison.

**Q13. 🔥 What is the ternary operator? Give an example.**
> The ternary operator is a shorthand for `if-else`: `condition ? valueIfTrue : valueIfFalse`.
> Example: `String grade = (marks >= 50) ? "Pass" : "Fail";`

**Q14. What is operator precedence? What is the result of `2 + 3 * 4`?**
> Operator precedence determines the order in which operators are evaluated. `*` has higher precedence than `+`, so `3 * 4 = 12` first, then `2 + 12 = 14`. Use parentheses `()` to override precedence.

**Q15. What is the difference between `i++` (post-increment) and `++i` (pre-increment)?**
> - `i++` (post): uses the current value first, then increments. `int a = 5; int b = a++;` → b=5, a=6.
> - `++i` (pre): increments first, then uses the new value. `int a = 5; int b = ++a;` → b=6, a=6.

**Q16. What are access modifiers in Java?**
> Access modifiers control visibility: `private` (same class only), default/package-private (same package), `protected` (same package + subclasses), `public` (everywhere). They implement encapsulation.

---

### Conditional Statements

**Q17. 🔥 What is the difference between `if-else if` and `switch`? When to use each?**
> - Use `if-else if` for **range checks** (`> 90`, `< 50`) and boolean conditions.
> - Use `switch` for **exact value matching** against constants (menu items, days, grades). Switch is cleaner for many discrete options. Switch works with `int`, `char`, `String`, `enum` — NOT `double`, `float`, `long`.

**Q18. What happens if you forget the `break` statement in a switch case?**
> **Fall-through** occurs — execution continues into the next case block without checking its condition, until a `break` or end of switch is reached. This can be intentional (grouping cases) or a bug.

---

## 📚 Unit II — Control Flow, OOP Basics & Strings

### Loops & Arrays

**Q19. 🔥 What is the difference between `while` and `do-while` loops?**
> - `while`: **entry-controlled** — condition checked before execution. Body may never execute if condition is initially false.
> - `do-while`: **exit-controlled** — body executes first, then condition checked. Body executes **at least once**.

**Q20. When would you use a `for-each` loop instead of a regular `for` loop?**
> Use `for-each` when you need to iterate through all elements of an array or collection without needing the index, and without modifying the collection. It's cleaner and less error-prone. Use regular `for` when you need the index or need to iterate a specific range.

**Q21. 🔥 What is the difference between `break` and `continue`?**
> - `break`: immediately **exits** the loop entirely.
> - `continue`: **skips** the current iteration and jumps to the next iteration.

**Q22. What is an array in Java? How is it different from a variable?**
> An array is a **fixed-size, ordered collection** of elements of the **same data type** stored in contiguous memory. A variable holds a single value; an array holds multiple values accessed by index (starting at 0). Size is fixed at creation and cannot be changed.

**Q23. What is a multi-dimensional array? How do you access its elements?**
> A multi-dimensional array is an array of arrays. A 2D array is declared as `int[][] grid = new int[3][4]`. Elements are accessed as `grid[row][column]` (both 0-indexed). `grid.length` gives row count; `grid[0].length` gives column count.

**Q24. 🔥 What are varargs? What are the rules for using them?**
> Varargs (`type... paramName`) allow a method to accept a variable number of arguments of the same type. Internally treated as an array. Rules: (1) must be the **last parameter** in the method signature, (2) only **one varargs** per method. Example: `int sum(int... nums)` can be called as `sum(1,2)`, `sum(1,2,3,4)`, or `sum()`.

**Q25. What is an enum in Java? Why is it better than integer constants?**
> An `enum` defines a **fixed set of named constants** (e.g., `enum Day { MON, TUE, WED }`). Better than int constants because: type-safe (can't pass invalid value), self-documenting, can have methods and fields, works in switch, and `values()` gives all constants.

---

### OOP Concepts

**Q26. 🔥 What is the difference between a class and an object?**
> A **class** is a blueprint or template defining attributes (fields) and behaviour (methods). An **object** is a specific instance of a class, created with `new`, with its own copy of instance variables. Example: `Car` is the class; `myCar = new Car()` is an object.

**Q27. 🔥 What is a constructor? How is it different from a method?**
> A constructor is a special block that **initializes an object** when it's created with `new`. Differences: constructor has the same name as the class, has no return type (not even void), is called automatically on `new`, cannot be called again explicitly. A method can be called multiple times and has a return type.

**Q28. What is constructor overloading?**
> Having multiple constructors in the same class with different parameter lists. The compiler chooses the correct one based on arguments passed. Allows creating objects in multiple ways. Example: `Rectangle()`, `Rectangle(double side)`, `Rectangle(double w, double h)`.

**Q29. 🔥 What is `this` keyword? Give three uses.**
> `this` refers to the current object inside an instance method or constructor.
> 1. Disambiguate field from parameter: `this.name = name;`
> 2. Call another constructor in same class (constructor chaining): `this(0, 0);`
> 3. Pass the current object to a method: `show(this);`

**Q30. What is an initializer block? When is it executed?**
> An **instance initializer block** `{ ... }` runs every time an object is created, **before the constructor**. A **static initializer block** `static { ... }` runs once when the class is first loaded by the JVM. Used to share initialization code across multiple constructors.

**Q31. 🔥 What is method overloading?**
> Method overloading is having multiple methods with the **same name but different parameter lists** (different number, type, or order of parameters) in the same class. The correct method is chosen at **compile time** based on arguments — this is also called **static/compile-time polymorphism**. Return type alone is NOT sufficient to overload.

---

### String Class

**Q32. 🔥 What does "String is immutable" mean in Java?**
> Once a `String` object is created, its content **cannot be changed**. Any operation that appears to modify a String (like `+`, `replace()`, `toUpperCase()`) actually creates a **new String object**. The original remains unchanged. Immutability makes Strings thread-safe and allows String pooling.

**Q33. What is the String Pool?**
> The String Pool (part of the heap) stores **unique String literals**. When you write `String s = "Hello"`, Java checks if "Hello" already exists in the pool; if yes, it reuses that object. `new String("Hello")` always creates a new object in the heap, bypassing the pool.

**Q34. 🔥 What is the difference between `String`, `StringBuilder`, and `StringBuffer`?**
> - `String`: immutable; thread-safe; slow for frequent modification.
> - `StringBuilder`: mutable; **not thread-safe**; fast; use in single-threaded contexts.
> - `StringBuffer`: mutable; **thread-safe** (synchronized); slightly slower than StringBuilder.
> Use `StringBuilder` for building strings in loops; `String` for fixed text.

**Q35. What is the output of `"Hello".substring(1, 4)`?**
> `"ell"` — `substring(start, end)` returns characters from index `start` to `end - 1` (exclusive).

---

## 📚 Unit III — Inheritance, Polymorphism & Abstraction

### Inheritance

**Q36. 🔥 What is inheritance? What keyword is used?**
> Inheritance allows a **child class** to acquire the fields and methods of a **parent class** using the `extends` keyword. It models an IS-A relationship, promotes code reuse, and enables polymorphism. Example: `class Dog extends Animal`.

**Q37. What is the difference between single, multilevel, and hierarchical inheritance?**
> - **Single:** One parent, one child (A → B).
> - **Multilevel:** A chain — A → B → C (B inherits A, C inherits B).
> - **Hierarchical:** One parent, multiple children (A → B, A → C).
> Java does NOT support multiple inheritance with classes (A,B → C) to avoid the Diamond Problem. It supports it through interfaces.

**Q38. 🔥 What members are inherited? What are not?**
> **Inherited:** `public` and `protected` fields and methods; default members within same package.
> **NOT inherited:** `private` members; constructors (though they are called via `super()`).

**Q39. What is the order of constructor calls in inheritance?**
> **Parent constructor runs first, then child constructor.** If you don't explicitly call `super()`, Java calls the no-arg parent constructor automatically. `super(args)` must be the first statement in the child constructor.

---

### Method Overriding & Polymorphism

**Q40. 🔥 What is method overriding? What are its rules?**
> Method overriding occurs when a child class provides its own implementation of a method already defined in the parent class.
> Rules: (1) Same method name, parameters, and return type. (2) Cannot reduce access level. (3) Cannot override `static`, `final`, or `private` methods. (4) Use `@Override` annotation.

**Q41. 🔥 What is the difference between method overloading and method overriding?**
> | | Overloading | Overriding |
> |---|---|---|
> | Where | Same class | Parent → Child |
> | Parameters | Must differ | Must match |
> | Resolved at | Compile time | Runtime |
> | Also called | Static polymorphism | Dynamic polymorphism |

**Q42. What is runtime polymorphism? Give an example.**
> Runtime polymorphism (dynamic dispatch) means the JVM decides which method version to call based on the **actual object type** at runtime, not the reference type.
> ```java
> Animal a = new Dog();
> a.sound();  // calls Dog's sound(), not Animal's — decided at runtime
> ```

**Q43. 🔥 What are the three uses of the `super` keyword?**
> 1. Call parent constructor: `super(name, age);` (must be first line)
> 2. Call parent's overridden method: `super.display();`
> 3. Access parent's shadowed field: `super.x`

**Q44. What does `final` mean when applied to a variable, method, and class?**
> - `final` variable: value cannot be changed (constant).
> - `final` method: cannot be overridden in subclasses.
> - `final` class: cannot be subclassed (e.g., `String`, `Integer` are final classes).

**Q45. 🔥 What does the `instanceof` operator do? What does it return for `null`?**
> `instanceof` checks if an object is an instance of a given class or interface. Returns `true` if so, `false` otherwise. For `null`, it always returns **false** (no exception thrown).

**Q46. What methods does every Java class inherit from `Object`?**
> `toString()`, `equals()`, `hashCode()`, `getClass()`, `clone()`, `finalize()`, `wait()`, `notify()`, `notifyAll()`.

**Q47. 🔥 Why should you override `hashCode()` whenever you override `equals()`?**
> Java's contract states: if `a.equals(b)` is true, then `a.hashCode() == b.hashCode()` must also be true. Collections like `HashMap` and `HashSet` use `hashCode()` to locate buckets and `equals()` to verify identity. Breaking this contract causes these collections to malfunction.

---

### Abstract Classes & Interfaces

**Q48. 🔥 What is an abstract class? Can it be instantiated?**
> An abstract class is declared with the `abstract` keyword and **cannot be instantiated directly**. It may contain abstract methods (no body) and concrete methods (with body). Subclasses must implement all abstract methods, or they must also be abstract.

**Q49. 🔥 What is the difference between an abstract class and an interface?**
> | Feature | Abstract Class | Interface |
> |---|---|---|
> | Instantiatable | No | No |
> | Abstract methods | Optional | All (by default) |
> | Concrete methods | Yes | Only `default`/`static` |
> | Fields | Yes (any) | `public static final` only |
> | Constructor | Yes | No |
> | Multiple inheritance | No | Yes |
> Use abstract class for "IS-A + shared code"; use interface for "CAN-DO capabilities".

**Q50. 🔥 What are `default` methods in interfaces? Why were they introduced?**
> `default` methods (Java 8+) are concrete methods in interfaces. They were introduced to add new methods to existing interfaces **without breaking all existing implementations**. Implementing classes inherit the default method and can optionally override it.

**Q51. What is the difference between `extends` and `implements`?**
> - `extends`: used when a class inherits from another class, OR when an interface extends another interface.
> - `implements`: used when a class adopts an interface contract. A class can implement multiple interfaces but can only extend one class.

---

## 📚 Unit IV — Nested Classes, Lambdas, Dates & Exceptions

### Nested Classes

**Q52. 🔥 What are the four types of nested classes in Java?**
> 1. **Static nested class** — declared with `static`; no access to outer instance members.
> 2. **Inner class (non-static)** — has access to all outer class members; needs outer instance.
> 3. **Local class** — defined inside a method; only accessible within that method.
> 4. **Anonymous class** — no name; declared and instantiated together; used for one-time implementations.

**Q53. What is the difference between a static nested class and an inner class?**
> A **static nested class** belongs to the outer class (like a static member) and does NOT have access to instance members of the outer class. It can be created without an outer instance: `Outer.Nested obj = new Outer.Nested()`.
> An **inner class** has access to ALL members (including private) of the outer class but requires an outer instance: `Outer.Inner obj = outer.new Inner()`.

**Q54. 🔥 What is an anonymous class? When is it used?**
> An anonymous class is a class with no name, declared and instantiated in a single expression. It extends a class or implements an interface on the spot. Used for one-time implementations (before lambdas). In modern Java, lambdas have largely replaced anonymous classes for functional interfaces.

---

### Lambda Expressions

**Q55. 🔥 What is a functional interface? What annotation marks it?**
> A functional interface has **exactly one abstract method**. It may have `default` and `static` methods. Marked with `@FunctionalInterface` (not mandatory but recommended — enforces the constraint at compile time). Examples: `Runnable`, `Comparable`, `Predicate`, `Function`, `Consumer`.

**Q56. 🔥 What is a lambda expression? Write the syntax.**
> A lambda expression is a concise way to implement a functional interface — essentially an anonymous function.
> Syntax: `(parameters) -> expression` or `(parameters) -> { statements; }`
> Examples: `x -> x * x`, `(a, b) -> a + b`, `() -> System.out.println("Hello")`

**Q57. What is the difference between `Predicate`, `Function`, `Consumer`, and `Supplier`?**
> - `Predicate<T>`: `boolean test(T t)` — tests a condition. E.g., `n -> n > 0`
> - `Function<T,R>`: `R apply(T t)` — transforms T to R. E.g., `s -> s.length()`
> - `Consumer<T>`: `void accept(T t)` — consumes, no return. E.g., `System.out::println`
> - `Supplier<T>`: `T get()` — provides a value, no input. E.g., `() -> new ArrayList<>()`

**Q58. What is a method reference? Give the four types.**
> Method reference is a shorthand for a lambda that simply calls an existing method: `Class::method`
> 1. Static: `Math::abs`
> 2. Instance method on object: `System.out::println`
> 3. Instance method on type: `String::toUpperCase`
> 4. Constructor: `ArrayList::new`

---

### Dates

**Q59. Why was `java.time` introduced? What was wrong with the old API?**
> The old `java.util.Date` and `Calendar` classes were mutable (not thread-safe), poorly designed, confusing (months 0-indexed), and had many deprecated methods. Java 8 introduced `java.time` (inspired by Joda-Time) which is immutable, thread-safe, clear, and comprehensive.

**Q60. 🔥 What is the difference between `LocalDate`, `LocalTime`, `LocalDateTime`, and `ZonedDateTime`?**
> - `LocalDate`: date only, no time, no timezone. E.g., `2024-08-15`
> - `LocalTime`: time only, no date, no timezone. E.g., `14:30:00`
> - `LocalDateTime`: date + time, no timezone. E.g., `2024-08-15T14:30:00`
> - `ZonedDateTime`: date + time + timezone. E.g., `2024-08-15T14:30:00+05:30[Asia/Kolkata]`

**Q61. What is the difference between `Period` and `Duration`?**
> - `Period`: date-based amount (years, months, days). Used with `LocalDate`. E.g., "1 year, 2 months, 5 days".
> - `Duration`: time-based amount (hours, minutes, seconds, nanoseconds). Used with `LocalTime`/`Instant`. E.g., "2 hours 30 minutes".

---

### Exceptions

**Q62. 🔥 What is the exception class hierarchy in Java?**
> `Throwable` → `Exception` → `RuntimeException` (unchecked) and `IOException` (checked). Also: `Throwable` → `Error` (don't catch these).

**Q63. 🔥 What is the difference between checked and unchecked exceptions?**
> - **Checked exceptions:** Compiler forces you to handle them (try-catch or declare with `throws`). Represent anticipated conditions. E.g., `IOException`, `SQLException`.
> - **Unchecked (RuntimeException):** Compiler does NOT force handling. Represent programming bugs. E.g., `NullPointerException`, `ArrayIndexOutOfBoundsException`.

**Q64. 🔥 What is the difference between `throw` and `throws`?**
> - `throw`: used inside a method body to **manually throw** an exception. E.g., `throw new IllegalArgumentException("bad input");`
> - `throws`: used in the method **signature** to **declare** that the method might throw a checked exception. E.g., `void read() throws IOException`

**Q65. Does the `finally` block always execute? When does it NOT execute?**
> The `finally` block almost always executes — even if an exception is thrown and not caught, or if `return` is hit in the try block. The rare case where it does NOT execute: if `System.exit()` is called, or if the JVM crashes, or if the thread is killed.

**Q66. 🔥 What is try-with-resources? What interface must a resource implement?**
> Try-with-resources (`try (Resource r = new Resource()) { ... }`) automatically closes resources after the try block, even if an exception occurs. The resource must implement `java.lang.AutoCloseable` (or `java.io.Closeable`). Eliminates the need for `finally` blocks for resource cleanup.

**Q67. How do you create a custom exception?**
> Extend `Exception` for a checked exception, or `RuntimeException` for unchecked. Provide a constructor that calls `super(message)`:
> ```java
> class InsufficientFundsException extends Exception {
>     InsufficientFundsException(double amount) {
>         super("Short by: " + amount);
>     }
> }
> ```

**Q68. What is an assertion? How do you enable assertions at runtime?**
> An assertion `assert condition : "message"` tests a condition you believe is always true. If false, `AssertionError` is thrown. Assertions are **disabled by default**. Enable with: `java -ea ClassName`. Used for debugging invariants and preconditions — not for user input validation.

---

## 📚 Unit V — I/O, Multithreading & Generics

### I/O

**Q69. 🔥 What is the difference between byte streams and character streams?**
> - **Byte streams** (`InputStream`/`OutputStream`): process data as 8-bit bytes. For binary files (images, audio, serialized objects).
> - **Character streams** (`Reader`/`Writer`): process data as 16-bit Unicode characters. For text files. Automatically handle character encoding.

**Q70. Why is `BufferedReader` preferred over `FileReader` for reading text?**
> `FileReader` reads one character at a time from disk (expensive disk I/O). `BufferedReader` wraps `FileReader` and reads a large chunk at a time into memory, then serves characters from the buffer. This dramatically reduces the number of disk reads and improves performance. Also provides the convenient `readLine()` method.

**Q71. 🔥 What is serialization in Java? What interface is required?**
> Serialization converts a Java object into a **byte stream** so it can be saved to a file or sent over a network. Deserialization is the reverse. The class must implement `java.io.Serializable` (a marker interface with no methods). Use `ObjectOutputStream` to serialize and `ObjectInputStream` to deserialize.

**Q72. What is the `transient` keyword? Give a use case.**
> Fields marked `transient` are **excluded from serialization** — they are not saved to the byte stream. Use case: passwords, database connections, cached computations — values that should not or cannot be persisted. After deserialization, transient fields have their default values (`null`, `0`, `false`).

**Q73. What is `serialVersionUID`? Why should you declare it explicitly?**
> `serialVersionUID` is a version identifier for a serializable class. Java uses it to verify that a serialized object is compatible with the current class definition during deserialization. If you don't declare it, Java auto-generates one based on class structure — if you change the class later, the auto-generated UID changes, causing `InvalidClassException` when reading old files.

---

### Multithreading

**Q74. 🔥 What is a thread? How is multithreading beneficial?**
> A thread is the smallest unit of execution within a process. Multithreading allows a program to execute multiple threads **concurrently**, leading to: better CPU utilization, improved responsiveness (UI remains active), and faster execution on multi-core processors.

**Q75. 🔥 What are the states in a thread's lifecycle?**
> **NEW** → `start()` → **RUNNABLE** → CPU scheduled → **RUNNING** → `sleep()`/`wait()` → **TIMED_WAITING / WAITING** → `sleep` ends / `notify()` → **RUNNABLE** → execution finishes → **TERMINATED**. Also: **BLOCKED** when waiting to acquire a monitor lock.

**Q76. 🔥 What is the difference between extending `Thread` and implementing `Runnable`? Which is preferred?**
> Extending `Thread` makes your class a thread — you lose the ability to extend another class. Implementing `Runnable` separates the task from the thread mechanism, allows the class to extend another class, and the same `Runnable` can be submitted to thread pools. **`Runnable` is preferred** for better design.

**Q77. 🔥 What happens if you call `run()` directly instead of `start()`?**
> Calling `run()` directly does NOT create a new thread — it executes the `run()` method in the **current thread**, like a normal method call. To actually start a new thread, you must call `start()`, which internally calls `run()` in the new thread.

**Q78. What is a race condition? How is it fixed?**
> A race condition occurs when multiple threads access and modify shared data simultaneously, leading to inconsistent or incorrect results. Fixed using **synchronization** — the `synchronized` keyword ensures only one thread at a time executes the critical section.

**Q79. 🔥 What is synchronization? What does `synchronized` do?**
> Synchronization ensures only **one thread** at a time can execute a synchronized block or method. It works using an intrinsic **monitor lock** on an object. When a thread enters a synchronized block, it acquires the lock; other threads trying to enter are blocked until the lock is released.

**Q80. What is the difference between `wait()` and `sleep()`?**
> | | `wait()` | `sleep()` |
> |---|---|---|
> | Class | `Object` | `Thread` (static) |
> | Lock | Releases the lock | Holds the lock |
> | Must be in `synchronized`? | Yes | No |
> | Woken by | `notify()` / `notifyAll()` | Timer expiry or interrupt |
> | Purpose | Inter-thread communication | Pause execution |

**Q81. 🔥 What are `wait()`, `notify()`, and `notifyAll()`? Where must they be called?**
> These `Object` methods enable inter-thread communication. They **must be called inside a `synchronized` block** (otherwise `IllegalMonitorStateException`).
> - `wait()`: releases the lock and waits until another thread calls `notify()`.
> - `notify()`: wakes up one waiting thread.
> - `notifyAll()`: wakes up all waiting threads. Use `notifyAll()` when multiple threads may be waiting.

**Q82. What is the `volatile` keyword?**
> `volatile` ensures that a variable is always read from and written to **main memory** rather than a thread's local cache. Guarantees visibility of changes across threads. Lighter than `synchronized` — suitable for simple flags like `boolean running = true`.

---

### Generics

**Q83. 🔥 What are generics in Java? What problem do they solve?**
> Generics allow classes, interfaces, and methods to work with **any type** while providing **compile-time type safety**. Without generics, you'd use `Object` and cast manually — risking `ClassCastException` at runtime. With generics, type errors are caught at compile time.

**Q84. What is the diamond operator `<>`? When was it introduced?**
> The diamond operator allows the compiler to **infer type arguments** from the left side of the declaration, avoiding repetition. `List<String> list = new ArrayList<>();` instead of `new ArrayList<String>()`. Introduced in **Java 7**.

**Q85. 🔥 What is a bounded type parameter? Give an example.**
> Bounded type parameters restrict what types can be used. `<T extends Number>` means T can only be `Number` or its subclasses (Integer, Double, etc.). This allows calling methods of `Number` inside the generic code. Example: `<T extends Comparable<T>>` allows calling `compareTo()`.

**Q86. 🔥 What is a wildcard `?` in generics? What are the three types?**
> A wildcard represents an unknown type.
> - `<?>` **Unbounded:** any type. Used when only `Object` methods are needed.
> - `<? extends T>` **Upper-bounded:** T or subclass. Used for **reading** (producing) from a structure.
> - `<? super T>` **Lower-bounded:** T or superclass. Used for **writing** (consuming) to a structure.

**Q87. What is the PECS principle?**
> **P**roducer **E**xtends, **C**onsumer **S**upers.
> - If a structure **produces** data for you (you read from it) → `<? extends T>`
> - If a structure **consumes** data from you (you write to it) → `<? super T>`
> Example: `Collections.copy(List<? super T> dest, List<? extends T> src)` — dest consumes, src produces.

**Q88. Can you use primitive types as generic type arguments?**
> No. Generics only work with **reference types** (objects). You cannot write `List<int>`. Use the wrapper class instead: `List<Integer>`. Autoboxing handles the conversion automatically.

---

## 📚 Unit VI — Collections & JDBC

### Collections Framework

**Q89. 🔥 What is the Java Collections Framework? Name its three components.**
> The JCF is a unified architecture for storing and manipulating groups of objects. Three components:
> 1. **Interfaces** — abstract types (List, Set, Map, Queue, Deque)
> 2. **Implementations** — concrete classes (ArrayList, TreeSet, HashMap, ArrayDeque)
> 3. **Algorithms** — utility methods in `Collections` class (sort, shuffle, reverse, etc.)

**Q90. 🔥 What is the difference between `ArrayList` and `LinkedList`?**
> - `ArrayList`: backed by a resizable array. O(1) random access by index. Slow insertion/deletion in the middle (shifts elements).
> - `LinkedList`: doubly-linked list. O(n) access by index. O(1) insertion/deletion at front/back. Also implements `Deque`.
> Use `ArrayList` for frequent reads; `LinkedList` for frequent insertions/deletions at ends.

**Q91. 🔥 What is the difference between `HashSet`, `LinkedHashSet`, and `TreeSet`?**
> - `HashSet`: no order, O(1) add/contains. No duplicates.
> - `LinkedHashSet`: maintains **insertion order**. Slightly slower than HashSet.
> - `TreeSet`: **sorted order** (natural or custom). O(log n) operations. Elements must be `Comparable` or a `Comparator` must be provided.

**Q92. 🔥 What is the difference between `Comparable` and `Comparator`?**
> - `Comparable`: implemented **inside** the class. Defines natural order via `compareTo(T o)`. One fixed order.
> - `Comparator`: implemented **outside** the class (or as lambda). Defines custom order via `compare(T o1, T o2)`. Multiple orders possible without modifying the class.

**Q93. 🔥 What is `HashMap`? Can it have null keys or values?**
> `HashMap` stores key-value pairs with O(1) average get/put using hashing. Keys are unique. It allows **one null key** and **multiple null values**. It does NOT guarantee any order. For sorted keys use `TreeMap`; for insertion order use `LinkedHashMap`.

**Q94. What is a `Deque`? How is `ArrayDeque` used as both a stack and a queue?**
> `Deque` (Double-Ended Queue) allows insertion and removal from both ends. As a **stack (LIFO):** use `push()` (addFirst) and `pop()` (removeFirst). As a **queue (FIFO):** use `offer()` (addLast) and `poll()` (removeFirst). `ArrayDeque` is faster than `Stack` and `LinkedList` for these use cases.

**Q95. What does `Collections.unmodifiableList()` return?**
> It returns a **read-only view** of the list. Any attempt to modify it (add, remove, set) throws `UnsupportedOperationException`. The underlying list can still be modified directly. Used to safely share a list without allowing modification.

**Q96. What is the time complexity of common operations on ArrayList, TreeSet, and HashMap?**
> - `ArrayList`: get by index O(1), add at end O(1) amortized, add in middle O(n), contains O(n).
> - `TreeSet`: add/remove/contains O(log n) — Red-Black tree.
> - `HashMap`: get/put/containsKey O(1) average, O(n) worst case (hash collision).

---

### JDBC

**Q97. 🔥 What is JDBC? What package is it in?**
> JDBC (Java Database Connectivity) is a Java API that enables Java programs to connect to and execute SQL queries on relational databases. It's in the `java.sql` package. It provides a standard interface so the same code works with different databases (MySQL, PostgreSQL, SQLite, etc.) by changing only the driver.

**Q98. 🔥 What are the 6 standard steps to work with a database in JDBC?**
> 1. Load/register the JDBC driver (`Class.forName(...)`)
> 2. Establish a connection (`DriverManager.getConnection(url, user, pass)`)
> 3. Create a statement (`conn.createStatement()` or `conn.prepareStatement(sql)`)
> 4. Execute the query (`stmt.executeQuery()` or `stmt.executeUpdate()`)
> 5. Process the results (`ResultSet rs; while(rs.next()) { ... }`)
> 6. Close all resources (ResultSet → Statement → Connection)

**Q99. 🔥 What is the difference between `Statement` and `PreparedStatement`?**
> - `Statement`: executes static SQL. Vulnerable to **SQL injection**. Compiled on every execution.
> - `PreparedStatement`: uses parameterized SQL with `?` placeholders. **Prevents SQL injection**. Compiled once and reused — better performance for repeated queries. Always prefer `PreparedStatement`.

**Q100. 🔥 What is SQL injection? How does `PreparedStatement` prevent it?**
> SQL injection is an attack where malicious SQL is inserted into input fields to manipulate queries. Example: entering `' OR '1'='1` as a password to bypass login.
> `PreparedStatement` prevents this by treating all parameters as **data, not SQL code** — special characters are automatically escaped.

**Q101. What do `executeQuery()` and `executeUpdate()` return?**
> - `executeQuery()`: used for `SELECT` statements; returns a `ResultSet` containing the rows.
> - `executeUpdate()`: used for `INSERT`, `UPDATE`, `DELETE`, `CREATE`; returns an `int` indicating the number of rows affected.

**Q102. How do you navigate a `ResultSet`?**
> `rs.next()` moves the cursor forward by one row. Returns `true` if the row exists, `false` at end. Column values are read using `rs.getInt("colName")`, `rs.getString("colName")`, `rs.getDouble(index)`, etc. Column indices are 1-based.

**Q103. 🔥 What is a transaction in JDBC? Why is it important?**
> A transaction is a group of SQL operations that must all succeed or all fail together (atomicity). Important for operations like bank transfers where debit and credit must both succeed. By default JDBC uses auto-commit. Disable with `conn.setAutoCommit(false)`, then `conn.commit()` on success or `conn.rollback()` on failure.

**Q104. What are the four types of JDBC drivers? Which is most commonly used today?**
> Type 1: JDBC-ODBC Bridge (deprecated). Type 2: Native-API Driver. Type 3: Network Protocol Driver. Type 4: **Pure Java / Thin Driver** — communicates directly with the database using the database's native protocol. **Type 4 is the most commonly used** — no native libraries needed, just add the driver JAR.

**Q105. What is `ResultSetMetaData`? When is it useful?**
> `ResultSetMetaData` describes the structure of a `ResultSet` — number of columns, column names, column types. Obtained via `rs.getMetaData()`. Useful for writing **generic code** that can print or process any query result without knowing the column names in advance.

---

## 🔥 Top 20 Most Important Viva Questions (Quick Recap)

| # | Question |
|---|---|
| 1 | Difference between JDK, JRE, and JVM |
| 2 | What is "Write Once, Run Anywhere"? |
| 3 | What is autoboxing and unboxing? |
| 4 | Difference between `==` and `.equals()` for Strings |
| 5 | What is method overloading vs method overriding? |
| 6 | What is runtime polymorphism? |
| 7 | Three uses of the `super` keyword |
| 8 | Difference between abstract class and interface |
| 9 | What is a functional interface? What are `Predicate`, `Function`, `Consumer`, `Supplier`? |
| 10 | What is a lambda expression? |
| 11 | Difference between `throw` and `throws` |
| 12 | What is try-with-resources? |
| 13 | What is serialization? What is `transient`? |
| 14 | `start()` vs `run()` in threads |
| 15 | What is synchronization? What is a race condition? |
| 16 | Difference between `wait()` and `sleep()` |
| 17 | What are wildcards in generics? Explain `<? extends T>` vs `<? super T>` |
| 18 | Difference between `Comparable` and `Comparator` |
| 19 | What is `PreparedStatement`? How does it prevent SQL injection? |
| 20 | What is a transaction in JDBC? What is `rollback()`? |

---

## 💡 Tips for a Successful Viva

1. **Know your code** — be prepared to explain any line of your practical programs.
2. **Understand, don't memorize** — examiners often rephrase questions; understand concepts deeply.
3. **Use examples** — always back up definitions with a small code snippet or real-world analogy.
4. **Know the "why"** — not just what something is, but why it exists and when to use it.
5. **Common follow-ups:** After any answer, expect "Can you give an example?", "What happens if...?", or "What's the difference between X and Y?".

---

*All the best for your viva! 🎉*
