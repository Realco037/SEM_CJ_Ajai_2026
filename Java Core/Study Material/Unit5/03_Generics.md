# 03 — Generics: Custom Generic Classes, Type Inference, Bounded Types & Wildcards

---

## 📖 Theory

### What Are Generics?

**Generics** allow you to write classes, interfaces, and methods that work with **any type**, while providing **compile-time type safety**. Instead of writing separate classes for `IntBox`, `StringBox`, `DoubleBox`, you write one `Box<T>` that works for all types.

**Without generics (old way — unsafe):**
```java
List list = new ArrayList();
list.add("Hello");
list.add(42);                   // allowed! No type check
String s = (String) list.get(1); // ClassCastException at runtime!
```

**With generics (type-safe):**
```java
List<String> list = new ArrayList<>();
list.add("Hello");
// list.add(42);  // COMPILE ERROR — caught early
String s = list.get(0);   // no cast needed
```

---

### Type Parameters — Naming Conventions

| Letter | Stands For | Common Use |
|---|---|---|
| `T` | Type | General purpose type |
| `E` | Element | Collections (List<E>, Set<E>) |
| `K` | Key | Maps (Map<K, V>) |
| `V` | Value | Maps (Map<K, V>) |
| `N` | Number | Numeric types |
| `R` | Return | Function return type |

---

### Custom Generic Class

```java
public class Box<T> {
    private T content;

    public Box(T content) {
        this.content = content;
    }

    public T getContent() { return content; }
    public void setContent(T content) { this.content = content; }

    @Override
    public String toString() {
        return "Box[" + content + " (" + content.getClass().getSimpleName() + ")]";
    }
}

// Usage
Box<String>  strBox = new Box<>("Hello");
Box<Integer> intBox = new Box<>(42);
Box<Double>  dblBox = new Box<>(3.14);

System.out.println(strBox);  // Box[Hello (String)]
System.out.println(intBox);  // Box[42 (Integer)]
```

**Multiple type parameters:**
```java
public class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey()   { return key; }
    public V getValue() { return value; }

    @Override
    public String toString() { return "(" + key + " → " + value + ")"; }
}

Pair<String, Integer> score = new Pair<>("Alice", 95);
Pair<String, String>  city  = new Pair<>("India", "New Delhi");
```

---

### Generic Methods

A method can be generic even if its class is not:

```java
public class ArrayUtils {
    // Generic method — T is declared before return type
    public static <T> void printArray(T[] array) {
        System.out.print("[");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i < array.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }

    public static <T extends Comparable<T>> T max(T a, T b) {
        return a.compareTo(b) >= 0 ? a : b;
    }
}

Integer[] nums = {1, 2, 3};
String[]  strs = {"Alice", "Bob"};
ArrayUtils.printArray(nums);     // [1, 2, 3]
ArrayUtils.printArray(strs);     // [Alice, Bob]
System.out.println(ArrayUtils.max(10, 25));   // 25
System.out.println(ArrayUtils.max("cat", "dog")); // dog
```

---

### Type Inference Diamond `<>`

Since Java 7, you don't need to repeat the type on the right side of the declaration — the compiler **infers** it from the left:

```java
// Before Java 7 (verbose)
List<Map<String, List<Integer>>> old = new ArrayList<Map<String, List<Integer>>>();

// Java 7+ — diamond operator infers the type
List<Map<String, List<Integer>>> modern = new ArrayList<>();

Box<String> box = new Box<>("Hello");   // compiler infers <String>
Pair<String, Integer> p = new Pair<>("Score", 100);  // infers <String, Integer>
```

---

### Bounded Type Parameters

You can **restrict** what types can be used as type arguments:

#### Upper Bound — `<T extends SomeClass>`

Only allows `SomeClass` and its subclasses:

```java
// T must be Number or a subclass (Integer, Double, Float...)
public static <T extends Number> double sum(T[] array) {
    double total = 0;
    for (T item : array) {
        total += item.doubleValue();   // can call Number methods
    }
    return total;
}

Integer[] ints = {1, 2, 3, 4, 5};
Double[]  dbls = {1.1, 2.2, 3.3};
System.out.println(sum(ints));   // 15.0
System.out.println(sum(dbls));   // 6.6
// sum(new String[]{"a","b"});  // COMPILE ERROR: String doesn't extend Number
```

#### Multiple Bounds — `<T extends ClassA & InterfaceB>`

```java
// T must extend Comparable AND implement Serializable
public static <T extends Comparable<T> & java.io.Serializable> T findMax(T[] arr) {
    T max = arr[0];
    for (T item : arr) if (item.compareTo(max) > 0) max = item;
    return max;
}
```

---

### Wildcards

Wildcards (`?`) are used when you want to work with a **generic type but don't know or care exactly what it is**.

#### 1. Unbounded Wildcard `<?>`

Accepts any type. Used when you only need `Object` methods:

```java
public static void printList(List<?> list) {
    for (Object item : list) {
        System.out.print(item + " ");
    }
    System.out.println();
}

printList(List.of(1, 2, 3));           // works
printList(List.of("a", "b", "c"));    // works
printList(List.of(1.1, 2.2));         // works
```

#### 2. Upper-Bounded Wildcard `<? extends T>`

Accepts `T` or any subclass. Used when **reading** from a structure:

```java
// Read from any list of Numbers (Integer, Double, Float...)
public static double total(List<? extends Number> list) {
    double sum = 0;
    for (Number n : list) sum += n.doubleValue();
    return sum;
}

total(List.of(1, 2, 3));        // List<Integer> — works
total(List.of(1.5, 2.5));       // List<Double>  — works
// total(List.of("a", "b"));    // List<String>  — COMPILE ERROR
```

#### 3. Lower-Bounded Wildcard `<? super T>`

Accepts `T` or any superclass. Used when **writing** to a structure:

```java
// Can add Integer (or subtypes) to any List of Integer or its supertypes
public static void addIntegers(List<? super Integer> list) {
    list.add(1);
    list.add(2);
    list.add(3);
}

List<Integer> intList    = new ArrayList<>();
List<Number>  numList    = new ArrayList<>();
List<Object>  objList    = new ArrayList<>();

addIntegers(intList);   // works
addIntegers(numList);   // works
addIntegers(objList);   // works
```

#### PECS Principle

> **P**roducer **E**xtends, **C**onsumer **S**upers

- If you're **reading** from a structure → use `<? extends T>` (it *produces* data for you)
- If you're **writing** to a structure → use `<? super T>` (it *consumes* data from you)

---

### Generic Interface

```java
public interface Repository<T, ID> {
    void save(T entity);
    T findById(ID id);
    List<T> findAll();
    void delete(ID id);
}
```

---

## 🧪 Practice Questions

1. What problem do generics solve? What is "type erasure"?
2. What does `Box<T>` mean? What is `T` called?
3. What is the diamond operator `<>`? In which Java version was it introduced?
4. What is the difference between `<T extends Number>` and `<? extends Number>`?
5. What does `<? super Integer>` mean? What types does it accept?
6. Explain the PECS principle with an example.
7. Can you use primitives as type arguments (e.g., `Box<int>`)? Why or why not?
8. Write a generic method `<T extends Comparable<T>>` that finds the minimum in an array.
9. What is an unbounded wildcard `<?>`? When would you use it?
10. Can a generic class have multiple type parameters? Give an example.

---

## 💻 Examples

### Example 1 – Custom Generic Class

```java
import java.util.ArrayList;
import java.util.List;

public class GenericStack<T> {
    private List<T> elements = new ArrayList<>();

    public void push(T item) {
        elements.add(item);
        System.out.println("Pushed: " + item);
    }

    public T pop() {
        if (isEmpty()) throw new RuntimeException("Stack is empty!");
        T item = elements.remove(elements.size() - 1);
        System.out.println("Popped: " + item);
        return item;
    }

    public T peek() {
        if (isEmpty()) throw new RuntimeException("Stack is empty!");
        return elements.get(elements.size() - 1);
    }

    public boolean isEmpty() { return elements.isEmpty(); }
    public int size()        { return elements.size(); }

    @Override
    public String toString() { return "Stack" + elements; }

    public static void main(String[] args) {
        System.out.println("=== Integer Stack ===");
        GenericStack<Integer> intStack = new GenericStack<>();
        intStack.push(10);
        intStack.push(20);
        intStack.push(30);
        System.out.println("Peek: " + intStack.peek());
        intStack.pop();
        System.out.println(intStack);

        System.out.println("\n=== String Stack ===");
        GenericStack<String> strStack = new GenericStack<>();
        strStack.push("Java");
        strStack.push("Generics");
        strStack.push("Rock");
        strStack.pop();
        System.out.println(strStack);
    }
}
```

**Output:**
```
=== Integer Stack ===
Pushed: 10
Pushed: 20
Pushed: 30
Peek: 30
Popped: 30
Stack[10, 20]

=== String Stack ===
Pushed: Java
Pushed: Generics
Pushed: Rock
Popped: Rock
Stack[Java, Generics]
```

---

### Example 2 – Generic Pair with Diamond Inference

```java
import java.util.*;

public class PairDemo {

    static class Pair<K, V> {
        private K key;
        private V value;

        Pair(K key, V value) { this.key = key; this.value = value; }

        K getKey()   { return key; }
        V getValue() { return value; }

        @Override
        public String toString() { return key + " → " + value; }
    }

    public static void main(String[] args) {
        // Diamond inference — no need to repeat types on right side
        Pair<String, Integer>  score   = new Pair<>("Alice", 95);
        Pair<String, Double>   price   = new Pair<>("Laptop", 55000.0);
        Pair<Integer, Boolean> lookup  = new Pair<>(404, false);

        System.out.println(score);
        System.out.println(price);
        System.out.println(lookup);

        // List of pairs with diamond
        List<Pair<String, Integer>> leaderboard = new ArrayList<>();
        leaderboard.add(new Pair<>("Alice", 100));
        leaderboard.add(new Pair<>("Bob", 85));
        leaderboard.add(new Pair<>("Charlie", 92));

        System.out.println("\nLeaderboard:");
        leaderboard.stream()
            .sorted((a, b) -> b.getValue() - a.getValue())
            .forEach(p -> System.out.println("  " + p));
    }
}
```

**Output:**
```
Alice → 95
Laptop → 55000.0
404 → false

Leaderboard:
  Alice → 100
  Charlie → 92
  Bob → 85
```

---

### Example 3 – Bounded Types

```java
public class BoundedDemo {

    // Upper bound: T must extend Number
    static <T extends Number> double average(T[] arr) {
        double sum = 0;
        for (T n : arr) sum += n.doubleValue();
        return sum / arr.length;
    }

    // T must be Comparable
    static <T extends Comparable<T>> T findMin(T[] arr) {
        T min = arr[0];
        for (T item : arr) if (item.compareTo(min) < 0) min = item;
        return min;
    }

    static <T extends Comparable<T>> T findMax(T[] arr) {
        T max = arr[0];
        for (T item : arr) if (item.compareTo(max) > 0) max = item;
        return max;
    }

    public static void main(String[] args) {
        Integer[] ints    = {5, 2, 9, 1, 7};
        Double[]  doubles = {3.5, 1.2, 8.8, 4.4};
        String[]  strs    = {"Banana", "Apple", "Cherry", "Mango"};

        System.out.println("Integer average: " + average(ints));
        System.out.println("Double average:  " + average(doubles));

        System.out.println("\nInteger min: " + findMin(ints) + "  max: " + findMax(ints));
        System.out.println("String min:  " + findMin(strs) + "  max: " + findMax(strs));
    }
}
```

**Output:**
```
Integer average: 4.8
Double average:  4.475

Integer min: 1  max: 9
String min:  Apple  max: Mango
```

---

### Example 4 – Wildcards (PECS)

```java
import java.util.*;

public class WildcardDemo {

    // Upper-bounded: READ from list (producer)
    static double sumList(List<? extends Number> list) {
        double total = 0;
        for (Number n : list) total += n.doubleValue();
        return total;
    }

    // Lower-bounded: WRITE to list (consumer)
    static void addNumbers(List<? super Integer> list, int count) {
        for (int i = 1; i <= count; i++) list.add(i * 10);
    }

    // Unbounded: just print anything
    static void printAll(List<?> list) {
        System.out.print("  [");
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i));
            if (i < list.size() - 1) System.out.print(", ");
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        List<Integer> ints    = Arrays.asList(1, 2, 3, 4, 5);
        List<Double>  doubles = Arrays.asList(1.5, 2.5, 3.5);
        List<String>  strs    = Arrays.asList("A", "B", "C");

        // Upper-bounded: sumList works with any Number list
        System.out.println("Sum of ints:    " + sumList(ints));
        System.out.println("Sum of doubles: " + sumList(doubles));

        // Unbounded: printAll works with ANY type
        System.out.println("Integers:"); printAll(ints);
        System.out.println("Doubles:");  printAll(doubles);
        System.out.println("Strings:");  printAll(strs);

        // Lower-bounded: addNumbers
        List<Number> numList = new ArrayList<>();
        List<Object> objList = new ArrayList<>();
        addNumbers(numList, 3);
        addNumbers(objList, 3);
        System.out.println("numList:"); printAll(numList);
        System.out.println("objList:"); printAll(objList);
    }
}
```

**Output:**
```
Sum of ints:    15.0
Sum of doubles: 7.5
Integers:
  [1, 2, 3, 4, 5]
Doubles:
  [1.5, 2.5, 3.5]
Strings:
  [A, B, C]
numList:
  [10, 20, 30]
objList:
  [10, 20, 30]
```

---

### Example 5 – Generic Repository Interface

```java
import java.util.*;

public class GenericRepositoryDemo {

    // Generic entity base
    interface Entity {
        int getId();
    }

    // Generic repository interface
    interface Repository<T extends Entity> {
        void save(T entity);
        Optional<T> findById(int id);
        List<T> findAll();
        void delete(int id);
    }

    // Concrete entity
    static class Student implements Entity {
        int id;
        String name;
        double gpa;

        Student(int id, String name, double gpa) {
            this.id = id; this.name = name; this.gpa = gpa;
        }
        @Override public int getId() { return id; }
        @Override public String toString() {
            return String.format("Student[%d: %-10s GPA: %.1f]", id, name, gpa);
        }
    }

    // Concrete repository
    static class StudentRepository implements Repository<Student> {
        private Map<Integer, Student> store = new HashMap<>();

        @Override public void save(Student s)          { store.put(s.getId(), s); }
        @Override public Optional<Student> findById(int id) { return Optional.ofNullable(store.get(id)); }
        @Override public List<Student> findAll()        { return new ArrayList<>(store.values()); }
        @Override public void delete(int id)            { store.remove(id); }
    }

    public static void main(String[] args) {
        StudentRepository repo = new StudentRepository();

        // Save students
        repo.save(new Student(1, "Alice", 3.9));
        repo.save(new Student(2, "Bob", 3.5));
        repo.save(new Student(3, "Charlie", 3.7));

        // Find by ID
        repo.findById(2).ifPresent(s -> System.out.println("Found: " + s));
        repo.findById(99).ifPresentOrElse(
            s -> System.out.println("Found: " + s),
            () -> System.out.println("Student #99 not found.")
        );

        // All students
        System.out.println("\nAll students:");
        repo.findAll().forEach(s -> System.out.println("  " + s));

        // Delete
        repo.delete(2);
        System.out.println("\nAfter deleting Bob:");
        repo.findAll().forEach(s -> System.out.println("  " + s));
    }
}
```

**Output:**
```
Found: Student[2: Bob        GPA: 3.5]
Student #99 not found.

All students:
  Student[1: Alice      GPA: 3.9]
  Student[2: Bob        GPA: 3.5]
  Student[3: Charlie    GPA: 3.7]

After deleting Bob:
  Student[1: Alice      GPA: 3.9]
  Student[3: Charlie    GPA: 3.7]
```

---

## 📝 Summary

- **Generics** enable type-safe, reusable code by parameterizing classes, interfaces, and methods with type variables (`T`, `E`, `K`, `V`, etc.).
- **Type inference diamond `<>`** (Java 7+) lets the compiler infer type arguments — reduces verbosity.
- **Bounded types** restrict what types can be substituted — `<T extends Number>` allows only Number and its subclasses.
- **Wildcards** (`?`) represent an unknown type — useful in method parameters:
  - `<?>` — any type (unbounded)
  - `<? extends T>` — T or subclass (upper bound — for reading/producing)
  - `<? super T>` — T or superclass (lower bound — for writing/consuming)
- **PECS:** Producer Extends, Consumer Supers — the golden rule for wildcard usage.
- Generics use **type erasure** — type information is removed at compile time; at runtime, all generic types become `Object`.

---

*Previous → [02 – Multithreading](./02_Multithreading.md)*  
*End of Unit 5 — All Units Complete!*
