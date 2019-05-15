package com.exercise;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/*List sort Exercise*/
public class ListSorter<T> {

    public void insertionSort(List<T> list, Comparator<T> comparator) {
        for (int i = 1; i < list.size(); i++) {
            T ele_i = list.get(i);
            int j = i;
            while (j > 0) {
                T ele_j = list.get(j - 1);
                if (comparator.compare(ele_i, ele_j) > 0) {
                    break;
                }
                list.set(j, ele_j);
                j--;
            }
            list.set(j, ele_i);
        }
    }

    public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
        List<T> result = mergeSort(list, comparator);
        list.clear();
        list.addAll(result);
    }

    public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
        int size = list.size();
        if (size <= 1) {
            return list;
        }
        List<T> first = mergeSort(list.subList(0, size / 2), comparator);
        List<T> second = mergeSort(list.subList(size / 2, size), comparator);
        return merge(first, second, comparator);
    }

    private List<T> merge(List<T> first, List<T> second, Comparator<T> comparator) {
        List<T> result = new LinkedList<>();
        int total = first.size() + second.size();
        for (int i = 0; i < total; i++) {
            List<T> winner = pickWinner(first, second, comparator);
            result.add(winner.remove(0));
        }
        return result;
    }

    private List<T> pickWinner(List<T> first, List<T> second, Comparator<T> comparator) {
        if (first.size() == 0) {
            return second;
        }
        if (second.size() == 0) {
            return first;
        }
        int cmp = comparator.compare(first.get(0), second.get(0));
        if (cmp < 0) {
            return first;
        }
        if (cmp > 0) {
            return second;
        }
        return first;
    }

    /*4 digits*/
    public List<T> radixSort(List<T> list) {
        int position, num;
        Queue<T>[] digitQueues = (LinkedList<T>[]) (new LinkedList[10]);
        for (int digitVal = 0; digitVal < 10; digitVal++) {
            digitQueues[digitVal] = (Queue<T>) new LinkedList<>();
        }

        //sort the list
        for (int digit = 0; digit <= 3; digit++) {
            for (int i = 0; i < list.size(); i++) {
                position = Character.digit(String.valueOf(list.get(i)).charAt(3 - digit), 10);
                digitQueues[position].add(list.get(i));
            }

            //gather numbers back into list
            num = 0;
            for (int digitVal = 0; digitVal < 10; digitVal++) {
                while (!digitQueues[digitVal].isEmpty()) {
                    list.set(num, digitQueues[digitVal].remove());
                    num++;
                }
            }
        }
        return list;
    }

    public List<T> heapSort(List<T> list,Comparator<T> comparator){
        PriorityQueue<T> heap = new PriorityQueue<>(list.size(),comparator);
        heap.addAll(list);
        list.clear();
        while(!heap.isEmpty()){
            list.add(heap.poll());
        }
        return list;
    }

    public List<T> topK(int k,List<T> list,Comparator<T> comparator){
        PriorityQueue<T> heap = new PriorityQueue<>(list.size(),comparator);
        for(T element : list){
            if(k < list.size()){
                heap.offer(element);
                continue;
            }
            int cmp = comparator.compare(element,heap.peek());
            if(cmp > 0){
                heap.poll();
                heap.offer(element);
            }
        }
        List<T> result = new ArrayList<>();
        if(!heap.isEmpty()){
            result.add(heap.poll());
        }
        return result;
    }
}
