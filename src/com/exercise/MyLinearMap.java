package com.exercise;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyLinearMap<K,V> implements Map<K,V> {
    private List<Entry> entries = new ArrayList<>();

    public class Entry implements Map.Entry<K,V>{
        private K key;
        private V value;

        public Entry(K key,V value){
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }
        @Override
        public V getValue() {
            return value;
        }
        @Override
        public V setValue(V newValue) {
            this.value = newValue;
            return value;
        }
    }

    /**
     * Compares two keys or two values, handling null correctly.
     *
     * @param target
     * @param obj
     * @return
     */
    private boolean equals(Object target, Object obj) {
        if (target == null) {
            return obj == null;
        }
        return target.equals(obj);
    }

    /**
     * Returns the entry that contains the target key, or null if there is none.
     *
     * @param target
     */
    private Entry findEntry(Object target) {
        for(Entry entry : entries){
            if(equals(entry.getKey(),target)){
                return entry;
            }
        }
        return null;
    }

    @Override
    public V get(Object key) {
        Entry entry = findEntry(key);
        if(entry != null){
            return entry.getValue();
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        Entry e = findEntry(key);
        if(e == null){
            entries.add(new Entry(key,value));
            return null;
        }else{
            V oldValue = e.getValue();
            e.setValue(value);
            return oldValue;
        }
    }

    @Override
    public V remove(Object key) {
        Entry entry = findEntry(key);
        if(entry != null){
            V value = entry.getValue();
            entries.remove(entry);
            return value;
        }
        return null;
    }

    @Override
    public boolean remove(Object key, Object value) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for(Entry entry : entries){
            if(equals(entry.getValue(),value)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return null;
    }
}
