public class BitVector {

    public BitVector(int length) {
        arr = new byte[(length + 7) / 8];
        this.length = length;
    }

    public boolean get(int index) {
        return (arr[index / 8] & (1 << (index % 8))) != 0;
    }

    public void set(int index, boolean val) {
        int idx = index / 8;
        int bit = 1 << (index % 8);
        if (val)
            arr[idx] |= (byte) bit;
        else
            arr[idx] &= (byte) ~bit;
    }

    public int length() { return length; }

    private final byte[] arr;
    private final int length;

    public static void main(String[] args) {
        BitVector v = new BitVector(100);
        for (int i = 0; i < 100; ++i)
            v.set(i, i % 3 == 0);

        for (int i = 0; i < v.length(); ++i)
            System.out.print(v.get(i) ? "X" : ".");
        System.out.println();
    }

}
