package tqs;

import static org.junit.jupiter.api.Assertions.*;

import org.example.TqsStack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.*;


public class TqsStackTest {

    // description for tests
    @DisplayName("Test stack is empty")
    @Test
    void testStackIsEmpty() {
        TqsStack<Integer> stack = new TqsStack<>();
        Assertions.assertTrue(stack.isEmpty());
    }

    @DisplayName("Test stack size")
    @Test
    void testStackSize() {
        TqsStack<Integer> stack = new TqsStack<>();
        assertEquals(0, stack.size());
    }

    @DisplayName("Test stack push")
    @Test
    void testPush() {
        TqsStack<Integer> stack = new TqsStack<>();
        stack.push(1);
        assertEquals(1, stack.size());
        assertFalse(stack.isEmpty());
    }

    // @Disable (disable test for coverage test purposes
    @Test
    void testPop() {
        TqsStack<Integer> stack = new TqsStack<>();
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.size());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.size());
        assertFalse(stack.isEmpty());
    }

    @Test
    void testPopEmptyStack() {
        TqsStack<Integer> stack = new TqsStack<>();
        assertThrows(NoSuchElementException.class, stack::pop);
    }

    @Test
    void testPeekEmptyStack() {
        TqsStack<Integer> stack = new TqsStack<>();
        assertThrows(NoSuchElementException.class, stack::peek);
    }

    // PopTop Tests

    @Test
    void testPopTop() {
        TqsStack<Integer> stack = new TqsStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.size());
        assertEquals(3, stack.popTopN(1));
        assertEquals(2, stack.size());
        assertFalse(stack.isEmpty());
    }

    @Test
    void testPopTopInsufficientElements() {
        TqsStack<Integer> stack = new TqsStack<>();
        stack.push(1);
        stack.push(2);
        assertThrows(NoSuchElementException.class, () -> stack.popTopN(3));
    }
}