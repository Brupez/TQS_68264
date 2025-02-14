package org.example;

import java.util.LinkedList;

public class TqsStack<T> {
    private LinkedList<T> list = new LinkedList<>();

    public void push(T element) {
        //list.addFirst(element);
    }

    public T pop() {
//        if (list.isEmpty()) {
//            throw new IllegalStateException("Stack is empty");
//        }
//        return list.removeFirst();
        return null;
    }

    public T peek() {
//        if (list.isEmpty()) {
//            throw new IllegalStateException("Stack is empty");
//        }
//        return list.getFirst();
        return null;

    }

    public boolean isEmpty() {
//        return list.isEmpty();
        return true;
    }

    public int size() {
//        return list.size();
        return 0;
    }
}