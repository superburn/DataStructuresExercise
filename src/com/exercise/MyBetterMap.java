package com.exercise;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyBetterMap<K,V> implements Map<K,V> {
    protected List<MyLinearMap<K, V>> maps;

    public MyBetterMap(){
        makeMaps(2);
    }

    protected void makeMaps(int k) {
        maps = new ArrayList<>(k);
        for (int i = 0; i < k; i++) {
            maps.add(new MyLinearMap<K, V>());
        }
    }

    protected MyLinearMap<K, V> chooseMap(Object key){
        int index = 0;
        if(key != null){
            index = Math.abs(key.hashCode()) % maps.size();
        }
        return maps.get(index);
    }

    @Override
    public V get(Object key) {
        MyLinearMap<K,V> map = chooseMap(key);
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        MyLinearMap<K,V> map = chooseMap(key);
        return map.put(key,value);
    }

    @Override
    public boolean containsKey(Object key) {
        V value = get(key);
        return value == null ? false : true;
    }

    @Override
    public boolean containsValue(Object value) {
        for(MyLinearMap<K,V> map : maps){
            if(map.containsValue(value)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(Object key, Object value) {
        return false;
    }

    @Override
    public int size() {
        int total = 0;
        for(MyLinearMap<K,V> map : maps){
            total += map.size();
        }
        return total;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {
        for (int i = 0; i < maps.size(); i++) {
            maps.get(i).clear();
        }
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
    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
