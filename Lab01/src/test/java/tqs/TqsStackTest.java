package tqs;

import static java.lang.invoke.MethodHandles.lookup;
import static java.util.logging.Logger.getLogger;
import static org.junit.jupiter.api.Assertions.*;

import org.example.TqsStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.*;


public class TqsStackTest {

    static final System.Logger log = getLogger(lookup().lookupClass());


//    @BeforeEach
//    void setup() {
//        TqsStack stack = new TqsStack();
//    }

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

    @Test
    void testPop() {
        TqsStack<Integer> stack = new TqsStack<>();
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.size());
        assertFalse(stack.isEmpty());
    }

    @Test
    void testPopEmptyStack() {
        TqsStack<Integer> stack = new TqsStack<>();
        stack.push(1);
        assertThrows(NoSuchElementException.class, stack::pop);
    }

    @Test
    void testPeekEmptyStack() {
        TqsStack<Integer> stack = new TqsStack<>();
        stack.peek();
        assertThrows(NoSuchElementException.class, stack::pop);
    }

//    @AfterEach
//    void teardown() {
//        mySut.releaseId();
//        mySut.close();
//    }

}
