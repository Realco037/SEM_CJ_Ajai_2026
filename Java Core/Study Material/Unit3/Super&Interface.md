JAVA – SUPER KEYWORD AND INTERFACE (THEORY NOTES)

---

## SUPER KEYWORD

Definition:
The "super" keyword in Java is used to refer to the immediate parent class object.

Main Uses of super:

1. Access Parent Class Variable
   If a child class has the same variable name as the parent class, we can use super to access the parent variable.

Example:
class Animal {
int age = 10;
}

class Dog extends Animal {
int age = 5;

```
void showAge() {
    System.out.println(super.age);
}
```

}

Explanation:
super.age refers to the variable in the parent class.

---

2. Call Parent Class Method

If a child class overrides a method, we can call the parent class method using super.

Example:

class Animal {
void sound() {
System.out.println("Animal makes sound");
}
}

class Dog extends Animal {
void sound() {
System.out.println("Dog barks");
}

```
void display() {
    super.sound();
}
```

}

Explanation:
super.sound() calls the parent class method.

---

3. Call Parent Constructor

super() is used to call the parent class constructor.

Example:

class Animal {
Animal() {
System.out.println("Animal Constructor");
}
}

class Dog extends Animal {
Dog() {
super();
System.out.println("Dog Constructor");
}
}

Output:
Animal Constructor
Dog Constructor

---

## INTERFACE

Definition:
An interface in Java is a blueprint of a class.

It contains:
• Abstract methods
• Constants

A class uses the keyword "implements" to use an interface.

Syntax:

interface Animal {
void sound();
}

class Dog implements Animal {
public void sound() {
System.out.println("Dog barks");
}
}

---

Important Points About Interface

1. Interface methods are public and abstract by default.
2. Variables inside an interface are public static final.
3. A class can implement multiple interfaces.

Example:

interface Camera {
void takePhoto();
}

interface MusicPlayer {
void playMusic();
}

class Smartphone implements Camera, MusicPlayer {

```
public void takePhoto() {
    System.out.println("Taking photo");
}

public void playMusic() {
    System.out.println("Playing music");
}
```

}

---

Difference Between Class and Interface

Class:
• Can have methods with body
• Uses "extends"

Interface:
• Contains abstract methods
• Uses "implements"
• Supports multiple inheritance
