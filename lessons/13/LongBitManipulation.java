/**
 * Bit manipulation (or "bit twiddling") terminology:
 *
 * - "set a bit": Change a bit to one, no matter what its value was.
 * - "clear a bit": Change a bit to zero, no matter what its value was.
 * - "flip a bit": Change a bit from one to zero or zero to one.
 * - "mask": A special value designed to select one or more bits from another value.
 *
 * - "LSB": The least significant bit, i.e. the bit in the ones position.  This is the rightmost
 *   bit when the value is written in binary.
 * - "MSB": The most significant bit; for a long value, this the sign bit.  This is the leftmost
 *   bit when the value is written in binary.
 *
 * Bits are traditionally counted from the LSB, starting at 0, toward the MSB, so that `(1 << i)`
 * produces a bit at position `i`.
 */
public class LongBitManipulation {

    /**
     * Returns a value with a single bit set.
     * @param bit
     *   The position of the bit to set; bit 0 is the LSB.
     */
    public static long getBitMask(int bit) {
        if (bit < 0 || bit >= Long.SIZE)
            throw new IndexOutOfBoundsException("invalid bit position: " + bit);
        return 1l << bit;
    }

    /**
     * Returns whether a bit is set.
     * @param value
     *   The value to examine.
     * @param bit
     *   The bit position to examine.
     */
    public static boolean getBit(long value, int bit) {
        return (value & getBitMask(bit)) != 0;
    }

    /**
     * Sets a bit.
     * @param value
     *   The value in which to set the bit.
     * @param bit
     *   The bit position to set.
     * @return
     *   The updated value.
     */
    public static long setBit(long value, int bit) {
        return value | getBitMask(bit);
    }

    /**
     * Clears a bit.
     * @param value
     *   The value in which to clear the bit.
     * @param bit
     *   The bit position to clear.
     * @return
     *   The updated value.
     */
    public static long clearBit(long value, int bit) {
        return value & ~getBitMask(bit);
    }

    /**
     * Sets or clears a bit.
     * @param value
     *   The value in which to set or clear the bit.
     * @param bit
     *   The bit position to set or clear.
     * @param bitValue
     *   True to set the bit, false to clear it.
     * @return
     *   The updated value.
     */
    public static long setBitTo(long value, int bit, boolean bitValue) {
        return bitValue ? setBit(value, bit) : clearBit(value, bit);
    }

    /**
     * Flips a bit.
     * @param value
     *   The value in which to clear the bit.
     * @param bit
     *   The bit position to flip.
     * @return
     *   The updated value.
     */
    public static long flipBit(long value, int bit) {
        return value ^ getBitMask(bit);
    }

    /**
     * Formats a value in 64-digit binary.
     */
    public static String format(long value) {
        String result = Long.toBinaryString(value);
        // Long.toBinaryString() omits leading zeros; pad them back in.
        while (result.length() < Long.SIZE)
            result = '0' + result;
        return result;
    }

    public static void main(String[] args) {
        // This is how you write a long binary literal.  Note the '0b' at the beginning, which
        // makes it binary, and the 'L' at the end, which makes it long.
        long val = 0b1001000111001010110100011000001001101010000111001011100010100001L;

        System.out.println(format(val));
        System.out.println(format(setBit(val, 6)));
        System.out.println(format(clearBit(val, 6)));
        System.out.println(format(flipBit(val, 6)));
        System.out.println(format(setBit(val, 60)));
        System.out.println(format(clearBit(val, 60)));
        System.out.println(format(flipBit(val, 60)));
        System.out.println(format(setBit(val, 62)));
        System.out.println(format(clearBit(val, 62)));
        System.out.println(format(flipBit(val, 62)));
    }

}
