package com.exercise;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

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

    public void mergeSortInPlace(List<T> list,Comparator<T> comparator){
        List<T> result = mergeSort(list,comparator);
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
            List<T> winner = pickWinner(first,second,comparator);
            result.add(winner.remove(0));
        }
        return result;
    }

    private List<T> pickWinner(List<T> first, List<T> second, Comparator<T> comparator){
        if(first.size() == 0){
            return second;
        }
        if(second.size() == 0){
            return first;
        }
        int cmp = comparator.compare(first.get(0),second.get(0));
        if(cmp < 0){
            return first;
        }
        if(cmp > 0){
            return second;
        }
        return first;
    }
}
