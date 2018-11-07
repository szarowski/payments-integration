package com.payments.util;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Currency;
import java.util.UUID;

public class Random {

    private static final SecureRandom RND = new SecureRandom();
    private static final String NUM = CharacterType.NUMERIC.getCharacters();
    private static final String ALPHA_NUM = CharacterType.NUMERIC.getCharacters() + CharacterType.UPPER_CASE.getCharacters() + CharacterType.LOWER_CASE.getCharacters();

    public static int intVal() { return intVal(1000); }
    public static int intVal(final int max) { return max == 0 ? 0 : RND.nextInt(max); }

    public static BigDecimal amountVal() { return BigDecimal.valueOf((double) (intVal(1000000) / 100)).movePointLeft(1); }

    public static String string() { return string(8); }
    public static String string(final int length) {
        return RND.ints(length, (int) '!', ((int) '~') + 1)
                .mapToObj((i) -> (char) i)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString()
                .trim();
    }

    public static String alphaNumeric() { return alphaNumeric(8); }
    public static String alphaNumeric(final int length) {
        return RND.ints(length, 0, ALPHA_NUM.length())
                .mapToObj(ALPHA_NUM::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    public static UUID uuid() { return UUID.randomUUID(); }

    @SafeVarargs
    public static <T> T value(final T... values) { return values[intVal(values.length)]; }

    @SuppressWarnings("unchecked")
    public static <T> T value(final Collection<T> values) {
        return (T) values.toArray()[intVal(values.size())];
    }

    @SuppressWarnings("unchecked")

    public static Currency currency() { return value(Currency.getAvailableCurrencies()); }
}