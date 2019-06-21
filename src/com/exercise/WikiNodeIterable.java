package com.exercise;

import org.jsoup.nodes.Node;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class WikiNodeIterable implements Iterable<Node> {
    private Node root;

    public WikiNodeIterable(Node root){
        this.root = root;
    }

    @Override
    public Iterator<Node> iterator() {
        return new WikiNodeIterator();
    }

    private class WikiNodeIterator implements Iterator<Node>{
        Deque<Node> stack;

        public WikiNodeIterator(){
            stack = new ArrayDeque();
            stack.push(root);
        }

        @Override
        public void remove() {
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public Node next() {
            if (stack.isEmpty()) {
                throw new NoSuchElementException();
            }

            Node node = stack.pop();
            List<Node> children = new ArrayList<>(node.childNodes());
            Collections.reverse(children);
            for (Node child : children) {
                stack.push(child);
            }
            return node;
        }
    }
}
