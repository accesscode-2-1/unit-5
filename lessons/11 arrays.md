# Arrays

## Properties

An **array** is a special kind of collection in Java. An array has the following properties:

- **ordered**: The items in an array have a definite order, as with a `List`.

- **one-dimensional**: The items are arranged in a one-dimensional sequence.

- **indexed**: The items in an array are indexed from 0 through one less than the length, and each can be accessed by index.

- **fixed length**: The length of an array must be specified when the array is created, and cannot be changed afterward.

- **mutable**: The items in an array (but not the length of the array) may individually be changed at any time.

- **homogeneous**: The type of an array must be specified when the array is created. All items in in the array must be of this type. The type may be a primitive type, a class, or even an array type.  (But see below regarding subclasses and class arrays.)

## Types 

An array _type_ is written by the base type followed by square brackets.  For example, an array of `boolean` is written `boolean[]`, and an array of `String` is written `String[]`.

## Creating

An array variable is a reference variable, like a class variable.  As with a class variable, an array variable may have the value `null`, indicating that it does not refer to an array at all.

```java
int[] arr;          // Initialized to null.
int[] arr = null;
int arr[];          // Alternate syntax.
```

Arrays themselves are allocated on the Java heap, like objects, using the `new` keyword, and specifying the array length in the square brackets.

```java
int[] arr = new int[4];
```

Like static variables, the items of an array are initially set to safe default values: 0 for numeric types, false for booleans, `null` for object types. You can also initialize the array to given values, using either equivalent syntax:

```java
int[] arr = new int[4] { 4, 8, 0, 10 };
int[] arr = { 4, 8, 0, 10 };
```

Like objects, arrays are cleaned up by the garbage collector once they are no longer referenced.

## Accessing

Square brackets are used to access an element in an array, given its index.  The index value must be at least zero, and must be less than the array length; this is checked at run time.

```java
arr[0]  // First element; evaluates to 4.
arr[2]  // Third element; evaluates to 0.

arr[4]  // Raises ArrayIndexOutOfBoundsException
```

On the left side of an assignment (`=`), this sets the item rather than getting it.

```java
arr[3] = 42;
```

An array variable may be assigned (unless it is `final`) a different array by assigning it without square brackets.

> :star: **Note:** If you assign an array variable without specifying an index in square brackets, you are changing the array variable to point to a different array.  This does not change the values in either the old or the new array!
>
> ```java
int[] arr0 = { 1, 3, 5, 7 };
int[] arr1 = { 2, 4, 6, 8 };
> ```
> 
> Initially, `arr0` and `arr1` refer to different arrays.  This line
> changes one element in the array `arr0` refers to.
> 
> ```java
arr0[1] = 100;
> ```
> 
> `arr0` now contains { 1, 100, 5, 7 }.
> 
> This assignment now changes the array _variable_ `arr0` to refer to the _same array_ as `arr1`.
> 
> ```java
arr0 = arr1;
> ```
> 
> The array that `arr0` previously referred to is now a candidate for garbage collection.  Since `arr0` and `arr1` both refer to the same array, changes to one will be visible through the other.
>
> ```java
System.out.println(arr0[1]);
arr1[1] = 200;
System.out.println(arr0[1]);
> ```
> This prints out "4" followed by "200".

An array also has a `length` field, which contains its length.  

To iterate over the contents of an array, you may either iterate over indexes explicitly,

```java
for (int i = 0; i < arr.length; ++i)
    System.out.println(arr[i]);
```

or using an iterator 'for' loop.

```java
for (int item : arr)
    System.out.println(item);
```

Remember, an array may not be resized; its length is set by `new`.  It may be reassigned to a different array object, though; it's up to you to make sure the new array has the same contents.  

## Copying

`System.arraycopy()` copies a range of elements of an array either to another location in the same array or to another array.  It even handles correctly a copy from one range to another overlapping range in the same array.

Its arguments are,

1. The source array.
2. The start index in the source array.
3. The target array (which may be the same as the source array).
4. The start index in the target array.
5. The number of items to copy.

For example, `System.arraycopy(arr0, 2, arr1, 0, 3)` will copy 3 items starting from the third position (index 2) of `arr0`, to the beginning (index 0) of `arr1`.

> :star: **Hint:**  Using `arraycopy()`, we can write a simple method that "expands" an array by creating a larger array and copying elements from the old array into it.
> 
> ```
static int[] expandArray(int[] array, int newSize) {
    int[] newArray = new int[newSize];
    System.arraycopy(array, 0, newArray, 0, array.length);
    return newArray;
}
```

## Other operations

The [`java.util.Arrays`](http://docs.oracle.com/javase/7/docs/api/java/util/Arrays.html) class contains a numer of static methods that operate on arrays.  Since Java generics cannot be used with primitive types, this class provides overloads for each primitive array type for most of its methods.

## Arrays and inheritance

> :star: **Note:** This is an advanced topic!

Java allows you to cast a class array to an array of the class's base. For example, with this class hierarchy,

```java
public class C {}
public class D extends C{}
```

Java allows you to cast a `D[]` array to `C[]`.  This leads to a dangerous situation: Java allows you to insert a `C` instance into a `C[]` array, but also promises you that you receive a `D` instance when you index a `D[]` array.  If both types can refer to the same array, there's a contradiction.  Can you see it?

Consider this code:

```java
D[] dArr = new D[4];   // Create a D array.
dArr[0] = new D();     // Add a D instance to it.
C[] cArr = dArr;       // Cast the array to the base class array.
cArr[1] = new C();     // Add a C instance to the same array.
D d = dArr[1];         // Extract the C instance as a D.  Yikes!
```

To avoid this situation, a Java array carries with it the type of objects it can hold, which is specified when the array is created with `new`.  Any insertion of an item to the array is checked against this (dynamic) type, even if the insertion is via an array variable with a base class array type. So, in the code above, the fourth line will raise an `ArrayStoreException`.


## Multidimentional arrays

Java does _not_ support two- or higher-dimensional arrays!  However, you can emulate them by one of two methods:

1. **Arrays of arrays**: Since Java allows you to create an array of arrays, you can use nested arrays for higher dimensions.  

  Note that _each_ inner array must separately be allocated: Java does not create them for you!  This code demonstrates how to do this for an array of `numRows` rows and `numCols` columns.
  
  ```java
  int[][] arr = new int[numRows][];     // Note syntax!
  for (int i = 0; i < arr.length; ++i)
      arr[i] = new int[numCols];
  ```  

  Because each inner array is allocated separately, you are at liberty to make them different lengths.  This is called a **ragged array**.
  
  The element in row `r` and column `c` is `arr[r][c]`.

1. **Index arithmetic**: A two- or higher-dimensional array may be "flattened" into a one-dimensional array by performing index manipulations.  For example, a two-dimensional array of `numRows` and `numCols` can be stored in a one-dimensional array of length `numRows * numCols`.  

  The element with row `r` and column `c` is stored at index `r * numCols + c` in the one-dimensional array, _i.e._ `arr[r * numCols + c]`.

# Exercises

> :dart: **Exercise:** Reversing an array:
> 
> 1. Write a method that accepts an `int[]` and returns a new, reversed array rather than modifying its argument in place.
> 1. Write a method that accepts an `int[]` and reverses the elements _in place_.
> 1. Write equivalent _generic_ methods, _e.g._ `reverse<T>(T[] arr)` and `reverseInPlace<T>(T[] arr)`.
> 
> Collect your methods (which should be static) into a single class.

<div></div>

> :dart: **Exercise:** A **bit vector** behaves like a list of booleans; however, the booleans are encoded as individual bits in a larger integer type such as `int` or `long`.  This is eight times as efficient as an array of `boolean`, each of which occupies one byte.
> 
> Write a `BitVector` class.  It should accept a length, in bits, at construction, and provide `set(index, bit)` and `get(index)` methods, where the individual bits are represented by booleans.  Your class should use about `length / 32` integer values or `length / 64` long values to store the bits.

<div></div>

> :dart: **Exercise:** Write a `Matrix` class that stores a two-dimensional mathematical matrix of `double` values.  Accept the number of rows and columns in the constructor.  Provide a `get(row, col)` accessor and a corresponding `set(row, col, value)`.  Provide a `toString()` that prints matrix out in the traditional format, with "(" and ")" characters on the left and right, respectively.

<div></div>

> :dart: **Exercise:** Implement your own `ArrayList` from scratch.  It should be functionally identical to the original.
> 
> 1. Write unit tests for `ArrayList<Double>`.  
>   - Make sure you test each method.
>   - Make sure you test cases with empty and non-empty lists.
>   - Make sure you test error conditions as well as correct usage.
> 1. Verify that your tests work as expected on the standard `ArrayList` class.
> 1. Create your own `ArrayList<T>` that implements `List<T>`.  Use IntelliJ to fill in all the methods you need.
> 1. Implement the methods, running tests as you go.

<div></div>

> :dart: **Exercise:** A **native collection** is a collection class specific to a single native type.  Java developers often use a native collection instead of a generic collection to avoid boxing/unboxing operations, reduce memory use, and improve performance.
> 
> Write a `DoubleArrayList` class that accepts and returns `double` values instead of `Double` objects.
> 
> Write some simple benchmarks for the basic operations, and compare performance of your class versus `ArrayList<Double>`.


