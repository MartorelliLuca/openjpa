package org.apache.openjpa.util.utils;

import java.util.Random;

public class InvalidObject extends Object {
    private final Random random = new Random();
    @Override
    public String toString() {
        // Non restituisce una stringa
        return null;
    }

    @Override
    public int hashCode() {
        return random.nextInt();  // Metodo hashCode implementato male
    }
}