# 02 — Multithreading: Thread Lifecycle, Priorities, Synchronization & Inter-thread Communication

---

## 📖 Theory

### What Is Multithreading?

A **thread** is the smallest unit of execution within a program. **Multithreading** allows a Java program to run multiple threads concurrently — doing several tasks simultaneously within a single process.

**Why multithreading?**
- Better CPU utilization (use idle time on one task while another waits for I/O)
- Improved responsiveness (UI stays responsive while background work runs)
- Faster execution on multi-core processors

---

### Thread Lifecycle

A thread goes through five states during its lifetime:

```
NEW ──► RUNNABLE ──► RUNNING ──► BLOCKED / WAITING / TIMED_WAITING
                                       │
                                       ▼
                                  TERMINATED
```

| State | Description |
|---|---|
| **NEW** | Thread created but not yet started (`new Thread()`) |
| **RUNNABLE** | Thread is ready to run; waiting for CPU time |
| **RUNNING** | Thread is actively executing |
| **BLOCKED** | Waiting to acquire a monitor lock |
| **WAITING** | Waiting indefinitely for another thread (`wait()`, `join()`) |
| **TIMED_WAITING** | Waiting for a specified time (`sleep()`, `wait(ms)`) |
| **TERMINATED** | Thread has finished execution |

---

### Creating Threads — Two Ways

#### Method 1: Extend `Thread` class

```java
class MyThread extends Thread {
    @Override
    public void run() {
        // code to execute in this thread
        System.out.println("Thread running: " + getName());
    }
}

MyThread t = new MyThread();
t.start();    // starts the thread; calls run() internally
```

#### Method 2: Implement `Runnable` interface (preferred)

```java
class MyTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Runnable running: " + Thread.currentThread().getName());
    }
}

Thread t = new Thread(new MyTask());
t.start();

// With lambda (most concise — Runnable is a functional interface)
Thread t2 = new Thread(() -> System.out.println("Lambda thread!"));
t2.start();
```

**Why prefer `Runnable`?**
- Java doesn't support multiple class inheritance — by extending `Thread`, you lose the ability to extend another class
- `Runnable` separates the *task* from the *thread mechanism* (better design)
- Same `Runnable` can be submitted to thread pools

---

### Important Thread Methods

| Method | Description |
|---|---|
| `start()` | Starts the thread; JVM calls `run()` |
| `run()` | Contains the thread's task — don't call directly |
| `sleep(ms)` | Pauses thread for ms milliseconds (static) |
| `join()` | Waits for another thread to finish |
| `join(ms)` | Wait at most ms milliseconds |
| `getName()` | Returns thread name |
| `setName(name)` | Sets thread name |
| `getId()` | Returns thread ID |
| `isAlive()` | Returns true if thread is running |
| `interrupt()` | Interrupts a sleeping/waiting thread |
| `currentThread()` | Returns reference to currently running thread (static) |
| `yield()` | Hints to scheduler to let other threads run |

---

### Thread Priorities

Each thread has a priority between 1 (lowest) and 10 (highest). Higher-priority threads are more likely to be scheduled first, but this is JVM/OS dependent — it's a *hint*, not a guarantee.

```java
Thread t1 = new Thread(() -> System.out.println("Low priority"));
Thread t2 = new Thread(() -> System.out.println("High priority"));

t1.setPriority(Thread.MIN_PRIORITY);    // 1
t2.setPriority(Thread.MAX_PRIORITY);    // 10
// Thread.NORM_PRIORITY = 5 (default)

t1.start();
t2.start();
```

---

### Synchronization

When multiple threads access **shared data** simultaneously, data can become inconsistent — this is called a **race condition**. **Synchronization** prevents this by allowing only one thread at a time to execute a critical section.

#### `synchronized` method

```java
class Counter {
    private int count = 0;

    // Only one thread can execute this at a time
    public synchronized void increment() {
        count++;
    }

    public synchronized int getCount() {
        return count;
    }
}
```

#### `synchronized` block (finer control)

```java
class Counter {
    private int count = 0;
    private final Object lock = new Object();

    public void increment() {
        synchronized (lock) {   // only this block is locked
            count++;
        }
        // rest of method runs unsynchronized
    }
}
```

**How it works:** Each object has an intrinsic **monitor lock**. When a thread enters a `synchronized` block, it acquires the lock. Other threads trying to enter are **blocked** until the lock is released.

---

### Inter-thread Communication

Threads often need to coordinate with each other. Java provides three `Object` methods for this:

| Method | Description |
|---|---|
| `wait()` | Releases the lock and waits until notified |
| `notify()` | Wakes up one waiting thread |
| `notifyAll()` | Wakes up all waiting threads |

> **Must be called inside a `synchronized` block/method** — otherwise `IllegalMonitorStateException` is thrown.

**Producer-Consumer pattern:**
```java
// Producer adds data, Consumer reads it
// Producer calls notify() after adding
// Consumer calls wait() when buffer is empty
```

---

### The `volatile` Keyword

Marks a variable as always read from main memory (not thread-local cache). Lighter than `synchronized` — useful for simple flags:

```java
class Server {
    private volatile boolean running = true;

    void stop() { running = false; }

    void serve() {
        while (running) {
            // handle requests
        }
    }
}
```

---

## 🧪 Practice Questions

1. What is a thread? How is multithreading different from a single-threaded program?
2. List all 5 thread states and explain each briefly.
3. What is the difference between `start()` and `run()`? What happens if you call `run()` directly?
4. What are the two ways to create a thread in Java? Which is preferred and why?
5. What is a race condition? Give a simple example.
6. What does `synchronized` do? What is the difference between a synchronized method and a synchronized block?
7. What is the difference between `wait()` and `sleep()`?
8. What does `join()` do? Give a use case.
9. What is `volatile`? When is it sufficient instead of `synchronized`?
10. What are `notify()` and `notifyAll()`? When would you use `notifyAll()` instead of `notify()`?

---

## 💻 Examples

### Example 1 – Creating Threads (All Three Ways)

```java
public class ThreadCreation {

    // Method 1: Extend Thread
    static class PrinterThread extends Thread {
        String message;
        PrinterThread(String name, String message) {
            super(name);
            this.message = message;
        }
        @Override
        public void run() {
            for (int i = 1; i <= 3; i++) {
                System.out.println(getName() + ": " + message + " (" + i + ")");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
        }
    }

    // Method 2: Implement Runnable
    static class CounterTask implements Runnable {
        String name;
        CounterTask(String name) { this.name = name; }
        @Override
        public void run() {
            for (int i = 1; i <= 3; i++) {
                System.out.println(name + " count: " + i);
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Method 1
        PrinterThread t1 = new PrinterThread("Thread-A", "Hello");

        // Method 2
        Thread t2 = new Thread(new CounterTask("Thread-B"));

        // Method 3: Lambda
        Thread t3 = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                System.out.println("Lambda-Thread: step " + i);
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
        }, "Thread-C");

        t1.start();
        t2.start();
        t3.start();

        t1.join();   // wait for all to finish before printing done
        t2.join();
        t3.join();
        System.out.println("All threads done.");
    }
}
```

**Output (order may vary — threads run concurrently):**
```
Thread-A: Hello (1)
Thread-B count: 1
Lambda-Thread: step 1
Thread-A: Hello (2)
Thread-B count: 2
Lambda-Thread: step 2
Thread-A: Hello (3)
Thread-B count: 3
Lambda-Thread: step 3
All threads done.
```

---

### Example 2 – Race Condition and Synchronization

```java
public class SyncDemo {

    static class UnsafeCounter {
        int count = 0;
        void increment() { count++; }   // NOT synchronized — race condition!
    }

    static class SafeCounter {
        int count = 0;
        synchronized void increment() { count++; }   // thread-safe
    }

    static void runWithCounter(Object counter, boolean safe) throws InterruptedException {
        int ITERATIONS = 1000;
        Runnable task;

        if (safe) {
            SafeCounter sc = (SafeCounter) counter;
            task = () -> { for (int i = 0; i < ITERATIONS; i++) sc.increment(); };
        } else {
            UnsafeCounter uc = (UnsafeCounter) counter;
            task = () -> { for (int i = 0; i < ITERATIONS; i++) uc.increment(); };
        }

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start(); t2.start();
        t1.join();  t2.join();

        if (safe) System.out.println("Safe count:   " + ((SafeCounter) counter).count + " (expected 2000)");
        else       System.out.println("Unsafe count: " + ((UnsafeCounter) counter).count + " (expected 2000, may differ!)");
    }

    public static void main(String[] args) throws InterruptedException {
        runWithCounter(new UnsafeCounter(), false);
        runWithCounter(new SafeCounter(), true);
    }
}
```

**Output:**
```
Unsafe count: 1743 (expected 2000, may differ!)
Safe count:   2000 (expected 2000)
```

*(Unsafe count varies each run due to race condition)*

---

### Example 3 – Thread Lifecycle with sleep() and join()

```java
public class LifecycleDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            System.out.println("Worker: Starting task...");
            try {
                Thread.sleep(500);   // TIMED_WAITING
                System.out.println("Worker: Halfway done...");
                Thread.sleep(500);
                System.out.println("Worker: Task complete!");
            } catch (InterruptedException e) {
                System.out.println("Worker: Interrupted!");
            }
        }, "WorkerThread");

        System.out.println("State before start: " + worker.getState());   // NEW
        worker.start();
        System.out.println("State after start:  " + worker.getState());   // RUNNABLE or TIMED_WAITING

        Thread.sleep(600);
        System.out.println("State midway:       " + worker.getState());   // TIMED_WAITING

        worker.join();   // main thread waits for worker to finish
        System.out.println("State after join:   " + worker.getState());   // TERMINATED
        System.out.println("Main: All done.");
    }
}
```

**Output:**
```
State before start: NEW
State after start:  RUNNABLE
Worker: Starting task...
Worker: Halfway done...
State midway:       TIMED_WAITING
Worker: Task complete!
State after join:   TERMINATED
Main: All done.
```

---

### Example 4 – Inter-thread Communication (Producer-Consumer)

```java
public class ProducerConsumer {

    static class SharedBuffer {
        private int data;
        private boolean hasData = false;

        synchronized void produce(int value) throws InterruptedException {
            while (hasData) {
                wait();   // wait until consumer reads
            }
            data = value;
            hasData = true;
            System.out.println("Produced: " + value);
            notify();   // wake up consumer
        }

        synchronized int consume() throws InterruptedException {
            while (!hasData) {
                wait();   // wait until producer writes
            }
            hasData = false;
            System.out.println("Consumed: " + data);
            notify();   // wake up producer
            return data;
        }
    }

    public static void main(String[] args) {
        SharedBuffer buffer = new SharedBuffer();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    buffer.produce(i * 10);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) { e.printStackTrace(); }
        }, "Producer");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    buffer.consume();
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) { e.printStackTrace(); }
        }, "Consumer");

        producer.start();
        consumer.start();
    }
}
```

**Output:**
```
Produced: 10
Consumed: 10
Produced: 20
Consumed: 20
Produced: 30
Consumed: 30
Produced: 40
Consumed: 40
Produced: 50
Consumed: 50
```

---

### Example 5 – Thread Priorities

```java
public class PriorityDemo {
    public static void main(String[] args) throws InterruptedException {
        int[] counts = new int[3];

        Thread low = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) counts[0]++;
        }, "LowPriority");

        Thread norm = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) counts[1]++;
        }, "NormPriority");

        Thread high = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) counts[2]++;
        }, "HighPriority");

        low.setPriority(Thread.MIN_PRIORITY);    // 1
        norm.setPriority(Thread.NORM_PRIORITY);  // 5
        high.setPriority(Thread.MAX_PRIORITY);   // 10

        long start = System.currentTimeMillis();
        low.start(); norm.start(); high.start();
        low.join(); norm.join(); high.join();
        long elapsed = System.currentTimeMillis() - start;

        System.out.println("Low  priority thread finished.  Count: " + counts[0]);
        System.out.println("Norm priority thread finished.  Count: " + counts[1]);
        System.out.println("High priority thread finished.  Count: " + counts[2]);
        System.out.println("Total time: " + elapsed + "ms");
        System.out.println("(Priority is a hint — behavior varies by OS/JVM)");
    }
}
```

---

## 📝 Summary

- A **thread** is a lightweight execution unit; multithreading runs multiple threads concurrently.
- Thread states: **NEW → RUNNABLE → RUNNING → BLOCKED/WAITING → TERMINATED**.
- Create threads via `extends Thread` or `implements Runnable` (preferred) or lambda.
- Call `start()` — never `run()` directly — to actually start a new thread.
- **`sleep(ms)`** pauses the current thread; **`join()`** waits for another thread to finish.
- **Race conditions** occur when threads share data without synchronization; fix with `synchronized`.
- **`wait()`/`notify()`/`notifyAll()`** enable inter-thread communication — must be inside `synchronized`.
- **`volatile`** ensures visibility of variable changes across threads (simpler than `synchronized`).

---

*Previous → [01 – I/O Fundamentals](./01_IO_Fundamentals.md)*  
*Next → [03 – Generics](./03_Generics.md)*
