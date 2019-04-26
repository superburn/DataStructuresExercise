package com.exercise;

import java.util.*;
import java.util.function.UnaryOperator;

/**
 * Exercise3 add(e) add(index,e) remove(index) indexOf
 */
public class MyLinkedList<T> implements List<T> {
    private int size;
    private Node head;


    private class Node {
        private T data;
        private Node next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }

        public Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    public MyLinkedList() {
        size = 0;
        head = null;
    }

    public static void main(String[] args) {
//        List<Integer> linkedList = new LinkedList();
//        linkedList.add(1);
//        linkedList.add(2);
//        linkedList.add(3);
//        linkedList.add(4);
//        linkedList.add(5);
//        linkedList.add(2, 10);
//        System.out.println(Arrays.toString(linkedList.toArray()));
        //test add
        List<Integer> list = new MyLinkedList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(0, 10);
        System.out.println(Arrays.toString(list.toArray()));
        System.out.println(list.indexOf(5));
        list.remove(2);
        System.out.println(Arrays.toString(list.toArray()));
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {

    }

    @Override
    public void sort(Comparator<? super T> c) {

    }

    @Override
    public Spliterator<T> spliterator() {
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        Node node = head;
        for (int i = 0; node != null; node = node.next, i++) {
            array[i] = node.data;
        }
        return array;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        if (head == null) {
            head = new Node(t);
        } else {
            Node node = head;
            for (; node.next != null; node = node.next) {
            }
            node.next = new Node(t);
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public T get(int index) {
        Node node = getNode(index);
        return node.data;
    }

    @Override
    public T set(int index, T element) {
        return null;
    }

    @Override
    public void add(int index, T element) {
        /**my implementation   wrong wrong wrong,because index = 0 doesn't work**/
//        if (index < 0 || index > size) {
//            throw new IndexOutOfBoundsException();
//        }
//        if (size == 0) {
//            add(element);
//        } else {
//            Node preNode = head;
//            for (int i = 0; i < index - 1; i++, preNode = preNode.next) {
//            }
//            Node nextNode = preNode.next;
//            preNode.next = new Node(element, nextNode);
//        }
//        size++;
        /**my implementation**/

        /**my implementation modified**/
//        if (index < 0 || index > size) {
//            throw new IndexOutOfBoundsException();
//        }
//        if (index == 0) {
//            add(element);
//        } else {
//            Node preNode = head;
//            for (int i = 0; i < index - 1; i++, preNode = preNode.next) {
//            }
//            Node nextNode = preNode.next;
//            preNode.next = new Node(element, nextNode);
//        }
//        size++;
        /**my implementation**/

        /**book implementation**/
        if(index == 0){
            head = new Node(element,head);
        }else{
            Node preNode = getNode(index - 1);
            preNode.next = new Node(element,preNode.next);
        }
        size++;
        /**book implementation**/
    }

    @Override
    public T remove(int index) {
        /**my implementation**/
//        if(index == 0){
//            head = head.next;
//            size--;
//            return head.data;
//        }else{
//            Node preNode = getNode(index - 1);
//            preNode.next = preNode.next.next;
//            size--;
//            return preNode.next.data;
//        }
        /**my implementation**/

        /**book implementation**/
        T element = get(index);
        if (index == 0) {
            head = head.next;
        } else {
            Node node = getNode(index-1);
            node.next = node.next.next;
        }
        size--;
        return element;
        /**book implementation**/
    }

    @Override
    public int indexOf(Object o) {
        Node node = head;
        for(int i = 0;node != null;node = node.next,i++){
            if(node.data.equals(o)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex >= size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        int i = 0;
        MyLinkedList<T> list = new MyLinkedList<T>();
        for (Node node=head; node != null; node = node.next) {
            if (i >= fromIndex && i <= toIndex) {
                list.add(node.data);
            }
            i++;
        }
        return list;

        /*improve*/

    }

    private Node getNode(int index){
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        Node node = head;
        for (int i = 0; i < index; i++){
            node = node.next;
        }
        return node;
    }

    private boolean equals(Object target, Object element) {
        if (target == null) {
            return element == null;
        } else {
            return target.equals(element);
        }
    }
}


