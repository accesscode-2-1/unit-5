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

When should you use static methods? This is a complex question, and answers are sometimes contentious. See, for example, [this StackOverflow question](http://stackoverflow.com/questions/2671496/java-when-to-use-static-methods).

## Static initializers

The value of a dynamic field can be initialized in the class's constructor. This allows the constructor to compute the initial value, if it needs to. But how about the value of a static field? You wouldn't want to initialize it in a constructor, since a static field is shared among instances-- you'd be "initializing" the same static field every time you created a new instance. Instead, you need a way to compute the static field's value just once, at some point before the class is first used.

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

## Nested classes

## Inner classes
