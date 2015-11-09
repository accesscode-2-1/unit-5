# Static

*Instructor: Alex Samuel*  

Date: 10 November 2015

FIXME

## Static fields

Generally, the value of a field is specific to each instance of the class; _i.e._ each instance may have its own value of the field.  A field marked `static`, however, is not part of an instance, but rather of the class.  As such, it takes a single value no matter which instance it's used from.

```java
public abstract class Product {
    Product() {
        this.id = nextId;
        nextId++;
    }

    // ...

    private static long nextId = 0;
    private final long id;
}
```

## Constant fields

A field may be both `static` and `final`; this usually used to store a **constant**.  A constant field conventionally has an UPPERCASE\_WITH\_UNDERSCORES name.  

```java
public class Album extends Product {
    static final double STANDARD_PRICE = 8.99;

    @Override
    public double getPrice() {
        return STANDARD_PRICE;
    }

    // ...
}
```

> :star: **Hint:** As much as possible, place "special" values such as numbers and character strings in constant fields rather than directly into your code.  This prevents duplication and makes it easier to find and change the values in the future.

You can access a static field inside an instance of a class, through a instance, or directly from the class itself.

```java
Store store = new Store();
System.out.println(store.URL);
System.out.println(Store.URL);
```

> :star: **Hint:** A non-final static field should almost never be `public`.  If necessary, provide a static accessor method.

## Static methods

A method may also be marked `static`.  A **static method** executes independent of any instance of its class.  As such,

1. `this` is not available.
2. Non-static fields are not accessible.  However, static fields are.
3. Non-static methods may not be called.  However, other static methods may be.

> :dart: **Exercise:** Write a class called <code>Dice</code>.  Its constructor takes a number of dice, and it has a <code>roll()</code> that simulates rolling that many decide, and returns the total.  Also keep count of how many times the dice have been rolled, and provide a static accessor for this count.

The `main()` method must always be static.  That's because Java runs it before creating any instances of any classes.

> :star: **Hint:** Most programming languages allow you to define a function outside of a class; however, Java does not.  Many Java programmers group such functions together into a class or classes containing only static methods, which behave essentially as functions outside of a class.  There's no point in instantiating such a class, as all of its methods are directly accessible without an instance  The `Math` class is an example of this.

## Static initializers

An initialer block may be marked `static`, called a **static initializer**.  This is useful to perform complex initialization of static fields.

```java
public class Store {
    // ...

    public static final URL STORE_URL;

    static {
        String country = Locale.getDefault().getCountry();
        System.out.println(country);
        String url;
        if (country == "US")
            url = "http://play.google.com";
        else if (country == "UK")
            url = "http://play.google.co.uk";
        else
            throw new RuntimeException("no Play in " + country);

        try {
            STORE_URL = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("wonky Play URL");
        }
    }
}
```

A static initializer is run right before the first time a class is used in any way.

> :dart: **Exercise:** The <code>Math.sqrt()</code> method is relatively slow compared to many other math functions.  Write a class that precomputes the square roots of the numbers 0 through 100 in a static list.  Using this array, provide a <code>fastSqrt()</code> method that takes an integer and uses the list to provide a fast square root result.  If the paramter is not between 0 and 100, fall back to <code>Math.sqrt()</code>.

## Static and dynamic type

Every variable in Java has a **type**.  There are two kinds of types:

- There are eight **primitive types**: `boolean`, `byte`, `char`, `short`, `int`, `long`,  `float`, `double`.  Note that their names are all lower-case.

- All other types are **class types**, and refer to direct or indirect subclasses of `Object`, _i.e._ to class names.  Conventionally, their names are captialized.

The declared type of a variable is its **static type**.  In general, we use the word "static" to refer to anything that is known to the compiler.  Since a variable's type appears in the Java source code, the compiler is aware of it.

However, _in a running program_, the situation is more complicated.

- A variable of a primitive type may contain only a value of that type.  That's straightforward.

- A variable of a class type (an **object variable**) may refer to an instance of that class, _or_ to an instance of any direct or indirect subclass.  

That means at any point in a program, an object variable has _two_ types: the declared static type, and the type of the object it refers to.  This latter type is called its **dynamic type** or **runtime type**.  The dynamic type must always be either the static type or a direct or indirect subclass of the static type; Java makes sure this is _always_ true.

Here's an example of why a dynamic type can't be known to the compiler.

```java
public class Parent {}
public class Child extends Parent {}

// ...

public Parent getInstance() {
    Scanner scanner = new Scanner(System.in);
    if (scanner.nextInt() == 0)
        return new Parent();
    else
        return new Child();
}

// ...
Code to run during instantiation doesn't have to go in constructors. Instead, it can be placed in initializer blocks. These appear simply as curly brace blocks within a class body.

The
Parent obj = getInstance();
// Is it a Parent or a Child?
```

This property of object variables enables **polymorphism** in Java: a single variable can contain a variety of "shapes" (_i.e._ classes), as long as they are subclasses of the variable's static type.  

Similarly, a container can contain instances in a mixture of classes, as long as they are all instances of subclasses of the container's item type.  For example, a `List<Parent>` may contain instances of `Parent` and `Child` mixed together in the same list.

Since that `Object` is the superclass of _all_ other classes, a variable of type `Object` can refer to an instance of _any_ class.  It can't refer to a primitive type, though.  

To get around this, Java provides **boxed types**, which are eight object types corresponding to the eight primitive types.  For example, the boxed type for `long` is [`java.lang.Long`](http://docs.oracle.com/javase/7/docs/api/java/lang/Long.html).  A boxed type is less efficient than the corresponding primitive type, but is a full-fledged class, so that object variables can refer to boxed instances.


## Dynamic dispatch

What if a subclass overrides a method from its superclass?

```java
public class Product {
    // ...

    public String getDisplayName() {
        return getName();
    }
}
```

```java
public class Album extends Product {
    // ...

    @Override
    public String getDisplayName() {
        return artist + " -- " + name;
    }
}
```

When a subclass overrides a superclass method, the overriding method will be used for each _instance_ of the subclass, _regardless of the type for which its called_.

```java
Product product = new Album("Prism", "Katy Perry", "pop");
System.out.println(product.getDisplayName());
```

This is called **dynamic dispatch**.  When it sees `getDisplayName()`, Java will dispatch to the version of this method based on the _dynamic type_ of the object, not the static type of the variable or expression.

> :star: **Hint:** This is the #1 most important concept in Java object oriented programming.  Almost all OO design patterns depend on this.
