package com.denistiago.urlshortener.repository

/**
 * Implementation based on following but with shuffled alphabet
 * https://gist.github.com/subchen/11200812
 */
class Base62 {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    private static Character[] digitsChar
    private static final int SEED = 1
    private static final int BASE
    private static final int FAST_SIZE = 'z' as char
    private static final int[] digitsIndex = new int[FAST_SIZE + 1]


    static {

        List<Character> characters = ALPHABET as List<Character>
        Collections.shuffle(characters, new Random(SEED))
        digitsChar =  characters
        BASE = digitsChar.length

        for (int i = 0; i < FAST_SIZE; i++) {
            digitsIndex[i] = -1
        }
        for (int i = 0; i < BASE; i++) {
            digitsIndex[digitsChar[i]] = i
        }
    }

    static long decode(String s) {
        long result = 0L
        long multiplier = 1
        for (int pos = s.length() - 1; pos >= 0; pos--) {
            int index = getIndex(s, pos)
            result += index * multiplier
            multiplier *= BASE
        }
        return result
    }

    static String encode(long number) {
        if (number < 0) throw new IllegalArgumentException("Number(Base62) must be positive: " + number)
        if (number == 0) return "0"
        StringBuilder buf = new StringBuilder()
        while (number != 0) {
            buf.append(digitsChar[(int) (number % BASE)])
            number /= BASE
        }
        return buf.reverse().toString()
    }

    private static int getIndex(String s, int pos) {
        char c = s.charAt(pos)
        if (c > FAST_SIZE) {
            throw new IllegalArgumentException("Unknow character for Base62: " + s)
        }
        int index = digitsIndex[c]
        if (index == -1) {
            throw new IllegalArgumentException("Unknow character for Base62: " + s)
        }
        return index
    }
}