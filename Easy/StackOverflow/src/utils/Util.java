package src.utils;

import src.exceptions.InvalidResourceException;

public class Util {
    public static void ensureResourceValidity(Object o1, Object o2) throws InvalidResourceException {
        if (o1 == null || o2 == null) {
            throw new InvalidResourceException("The specified resource(S) must not be null");
        }
    }

    public static void ensureResourceValidity(Object o) throws InvalidResourceException {
        ensureResourceValidity(o, o);
    }
}
