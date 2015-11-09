# Static

*Instructor: Alex Samuel*  

Date: 10 November 2015

In this lesson, we'll examine **static** fields, methods, and initializers and how they relate to each other.  We'll consider some common idioms for using them, and also some anti-patterns.

If time permits, we'll look at the two ways Java provides to put a class inside another class: **nested classes** and **inner classes**.

## Static fields

Generally, the value of a field is specific to each instance of the class; _i.e._ each instance may have its own value of the field.  A field marked `static`, however, is not part of an instance, but rather of the class.  As such, it takes a single value no matter which instance it's used from.

```java
public class Value {
    public Value(int val) {
        if (val > max)
            max = val;
        this.val = val;
    }

    private int val;
    public static int max;
}
```

## Constant fields

A field may be both `static` and `final`; this usually used to store a **constant**.  A constant field conventionally has an UPPERCASE\_WITH\_UNDERSCORES name.  

```java
public class Application {
    public static final String NAME = "Angry Birds";
    public static final Date RELEASE_DATE = new Date(2009, 12, 5);
}
```

> :star: **Hint:** As much as possible, place "special" values such as numbers and character strings in constant fields rather than directly into your code.  This prevents duplication and makes it easier to find and change the values in the future.

You can access a static field inside an instance of a class, through a instance, or directly from the class itself.

```java
System.out.println(Application.NAME);

Application app = new App();
System.out.println(app.NAME);
```

## Static methods

A method may also be marked `static`.  A **static method** executes independent of any instance of its class.  As such,

1. `this` is not available.
2. Non-static fields are not accessible.  However, static fields are.
3. Non-static methods may not be called.  However, other static methods may be.

The `main()` method must always be static.  That's because Java runs it before creating any instances of any classes.

## Using static

When should you use static methods? This is a complex question, and answers are sometimes contentious: see, for example, [this StackOverflow question](http://stackoverflow.com/questions/2671496/java-when-to-use-static-methods).

> :star: **Hint:** Just as you use accessors to control access to instance fields, it's a good idea to use static accessors to control access to static fields. An exception to this is static final fields, _i.e._ constants, which are usually safe to access directly.

```java
public class Value {
    public Value(int val) {
        if (val > max)
            max = val;
        this.val = val;
    }

    public static int getMax() { return max; }

    private int val;
    private static int max;
}
```

### Unique objects

Often, static methods are used to model a unique item.  For example, if your application accesses the Google Play Store, you might model the Store with an object in a static field.

But be careful! What's unique today might not be unique tomorrow.

- If you store your database connection in a static field, what happens if you want to connect to two databases in the future?

- If you represent your user's screen in a static field, what happens if the user runs on a decide with two screens?

- If you store the player of your game in a static field, what if you decide to make your game multiplayer in the future?

### Counters and unique IDs

A static field and static accessor method is a good way to generate a counter value or other ID unique in your code.

* :dart: **Exercise:** Write a static method that returns a social security number.  Make sure your method won't return the same number twice.

### Pure functions

One common use is for a **pure function**. A pure function's behavior depends entirely on its arguments, not on any other state&mdash;including the state of any class instance. Since it doesn't need an instance, it can be made static.

Here's an example you've already seen: our old friend, the Fibonacci series.

```java
public static BigDecimal fibonacci(int n) {
    if (n < 1)
        throw new IllegalArgumentException("n must be positive");
    else if (n <= 2)
        return BigDecimal.ONE;
    else
        return fibonacci(n - 2).add(fibonacci(n - 1));
}
```

As you can see, a static method can call other _static_ methods, including itself&mdash;so it can be recursive.

> :star: **Hint:** Most programming languages allow you to define a function outside of a class; however, Java does not.  Many Java programmers group such functions together into a class or classes containing only static methods, which behave essentially as functions outside of a class.  There's no point in instantiating such a class, as all of its methods are directly accessible without an instance  The [`java.lang.Math`](http://docs.oracle.com/javase/7/docs/api/java/lang/Math.html) class is an example of this.

### Caching

A common use for static fields is for caching.  A **cache** is a result stored so that it can be accessed quickly in the future, rather than recomputed.

```java
public static BigDecimal fibonacci(int n) {
    if (n < 1)
        throw new IllegalArgumentException("n must be positive");
    else if (n <= 2)
        return BigDecimal.ONE;
    else {
        BigDecimal result = cache.get(n);
        if (result == null) {
            result = fibonacci(n - 2).add(fibonacci(n - 1));
            cache.put(n, result);
        }
        return result;
    }
}

private static Map<Integer, BigDecimal> cache = new HashMap<Integer, BigDecimal>();
```

### Metrics

Another common use for static fields and methods is for storing counters and other measurements of a program's behavior.

> :dart: **Exercise:** Write a class called <code>Dice</code>.  Its constructor takes a number of dice, and it has a <code>roll()</code> that simulates rolling that many decide, and returns the total.  Also keep count of how many times the dice have been rolled, and provide a static accessor for this count.


## Static initializers

The value of a dynamic field can be initialized in the class's constructor. This allows the constructor to compute the initial value, if it needs to. But how about the value of a static field? You wouldn't want to initialize it in a constructor, since a static field is shared among instances-- you'd be "initializing" the same static field every time you created a new instance. Instead, you need a way to compute the static field's value just once, at some point before the class is first used.

An initialer block may be marked `static`, called a **static initializer**.  This is useful to perform complex initialization of static fields.

```java
private static Vector<BigDecimal> cache = new Vector<BigDecimal>();

static {
    cache.add(0, null);
    cache.add(1, BigDecimal.ONE);
    cache.add(2, BigDecimal.ONE);
}

public static BigDecimal fibonacci(int n) {
    if (n < 1)
        throw new IllegalArgumentException("n must be positive");
    else if (cache.size() <= n) {
        final BigDecimal result = fibonacci(n - 2).add(fibonacci(n - 1));
        cache.add(n, result);
        return result;
    }
    else
        return cache.get(n);
}
```

A static initializer is run right before the first time a class is used in any way.

> :dart: **Exercise:** The <code>Math.sqrt()</code> method is relatively slow compared to many other math functions.  Write a class that precomputes the square roots of the numbers 0 through 100 in a static list.  Using this array, provide a <code>fastSqrt()</code> method that takes an integer and uses the list to provide a fast square root result.  If the parameter is not between 0 and 100, fall back to <code>Math.sqrt()</code>.

## Nested classes



## Inner classes
