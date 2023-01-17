package com.eternalcode.gitcheck.shared;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PreconditionsTest {

    @Test
    void testConstructor() throws NoSuchMethodException {
        Constructor<Preconditions> constructor = Preconditions.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));

        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, constructor::newInstance);
    }

    @Test
    void testNotNull() {
        assertDoesNotThrow(() -> Preconditions.notNull(new Object(), "test"));
        assertThrows(IllegalArgumentException.class, () -> Preconditions.notNull(null, "test"));
    }

    @Test
    void testNotEmpty() {
        assertDoesNotThrow(() -> Preconditions.notEmpty("test", "test"));
        assertThrows(IllegalArgumentException.class, () -> Preconditions.notEmpty("", "test"));
    }

}