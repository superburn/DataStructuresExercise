package com.exercise;

import java.util.*;
import java.util.function.UnaryOperator;

/**
 * Exercise2 add(e) add(index,e) set remove indexOf
 */
public class MyArrayList<T> implements List<T> {
    int size;

    private T[] array;

    public MyArrayList() {
        array = (T[]) new Object[10];
        size = 0;
    }

    public static void main(String[] args) {
//        List<Integer> arrayList = new ArrayList();
//        arrayList.add(1);
//        arrayList.set(0,0);
//        System.out.println(Arrays.toString(arrayList.toArray()));
        //test add
        List<Integer> list = new MyArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        System.out.println(Arrays.toString(list.toArray()));
        //test add index
        list.add(2, 7);
        System.out.println(Arrays.toString(list.toArray()));
        //test remove
        list.remove(2);
        System.out.println(Arrays.toString(list.toArray()));
        //test indexOf
        System.out.println(list.indexOf(null));
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
        return Arrays.copyOf(array, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        if (size >= array.length) {
            T[] bigger = (T[]) new Object[array.length * 2];
            System.arraycopy(array, 0, bigger, 1, array.length);
            array = bigger;
        }
        array[size] = t;
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return array[index];
    }

    @Override
    public T set(int index, T element) {
        /**my implementation**/
//        if(index <0 || index >= size){
//            throw new IndexOutOfBoundsException();
//        }
//        T oldValue = array[index];
//        array[index] = element;
//        return oldValue;
        /**my implementation**/

        /*book implementation**/
        // no need to check index; get will do it for us
        T old = get(index);
        array[index] = element;
        return old;
        /*book implementation**/
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        /******my implementation******/
        // copy to a new array
//        int length = size + 1 >= array.length ? array.length * 2 : array.length;
//        T[] newArray = (T[]) new Object[length];
//        System.arraycopy(array, 0, newArray, 0, index);
//        newArray[index] = element;
//        System.arraycopy(array, index, newArray, index + 1, size - index);
//        array = newArray;
//        size++;
        /******my implementation******/

        /******book implementation*****/
        // add the element to get the resizing
        add(element);
        // shift the elements
        for (int i = size - 1; i > index; i--) {
            array[i] = array[i - 1];
        }
        // put the new one in the right place
        array[index] = element;
        /******book implementation*****/
    }

    @Override
    public T remove(int index) {
        /*my first version*/
//        T removeValue = array[index];
//        array[index] = null;
//        System.arraycopy(array, index + 1, array, index, size - index - 1);
//        array[size - 1] = null;
//        return removeValue;
        /*my first version*/

        /*my modified version*/
//        T removeValue = array[index];
//        array[index] = null;
//        int numMoved = size - index - 1;
//        if (numMoved > 0)
//            System.arraycopy(array, index + 1, array, index, size - index - 1);
//        array[--size] = null;
//        return removeValue;
        /*my modified version*/

        /******book implementation*****/
        T element = get(index);
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        size--;
        return element;
        /******book implementation*****/
    }

    @Override
    public int indexOf(Object o) {
        /**my implementation**/
//        // refer to ArrayList
//        if (o == null) {
//            for (int i = 0; i < size; i++) {
//                if (array[i] == null) {
//                    return i;
//                }
//            }
//        } else {
//            for (int i = 0; i < size; i++) {
//                if (o.equals(array[i])) {
//                    return i;
//                }
//            }
//        }
//        return -1;
        /**my implementation**/

        /***book implement**/
        for (int i = 0; i < size; i++) {
            if (equals(o, array[i])) {
                return i;
            }
        }
        return -1;
        /***book implement**/
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
        return null;
    }

    /**
     * Checks whether an element of the array is the target.
     * <p>
     * Handles the special case that the target is null.
     *
     * @param target
     * @param element
     */
    private boolean equals(Object target, Object element) {
        if (target == null) {
            return element == null;
        } else {
            return target.equals(element);
        }
    }
}
