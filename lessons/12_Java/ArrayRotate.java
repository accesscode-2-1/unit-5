package nyc.c4q.jrod.current;

import java.util.Arrays;
import java.util.Stack;

public class ArrayRotate {
  private static final boolean DEBUG = false;

  public static void main(String[] args) {
    testArray(new int[]{1, 2, 3}, 1);
    testArray(new int[]{1, 2, 3}, 2);
    testArray(new int[]{1, 2, 3}, 3);
    testArray(new int[]{1, 2, 3, 4, 5}, 1);
    testArray(new int[]{1, 2, 3, 4, 5}, 3);
  }

  private static void testArray(int[] a, int n) {
    System.out.print(Arrays.toString(a));
    //rotateArrayRight(a, n);
    a = rotateArrayRightHans(a, n);
    System.out.println(" => " + Arrays.toString(a));
  }

  // alternative approach using a 2nd array
  private static int[] rotateArrayRightHans(int[] a, int n) {
    if(!isRotateNecessary(a, n)) {
      return a;
    }

    int length = a.length;

    // trade-off = fewer lines of code vs extra allocated memory
    // for large n, the in-place algorithm might perform better
    int[] a2 = new int[length];

    // copy rightmost of a into leftmost of a2
    System.arraycopy(a, length - n, a2, 0, n);
    // copy leftmost of a into rightmost of a2
    System.arraycopy(a, 0, a2, n, length - n);

    return a2;
  }

  private static void rotateArrayRight(int[] a, int n) {
    if(!isRotateNecessary(a, n)) {
      return;
    }

    int length = a.length;
    int end = length - 1;
    int start = end - n + 1; // OR ... = length - n

    if(DEBUG) {
      System.out.println("start: " + start);
      System.out.println("end: " + end);
    }

    // remember last n elements
    Stack<Integer> stack = new Stack<>();
    for (int i = end; i >= start; i--) {
      stack.push(a[i]);
    }

    if(DEBUG) {
      System.out.println("stack: " + stack);
    }

    // move each element from beginning of array up to (not incl. start)
    for (int i = start - 1; i >= 0; i--) {
      a[i + n] = a[i];
    }

    if(DEBUG) {
      System.out.println(Arrays.toString(a));
    }

    // restore stored elements
    for (int i = 0; i < n; i++) {
      a[i] = stack.pop();
    }
  }

  // check and see if the given a & n allow us to short-circuit rotation
  private static boolean isRotateNecessary(int[] a, int n) {
    if (n < 0) {
      throw new IllegalArgumentException("n is negative");
    }

    // if a is empty or contains 1 element, then a rotate is pointless
    if (a.length < 2) {
      return false;
    }

    // suppose a = {1,2,3}
    // then rot(a,3) == rot(a,6),
    //      rot(a,4) == rot(a,7),
    //      etc.
    n = n % a.length;
    if (n == 0) {
      return false;
    }

    return true;
  }
}
