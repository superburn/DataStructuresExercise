package com.exercise;

import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class MyTreeMap<K, V> implements Map<K, V> {
    private int size = 0;
    private Node root = null;

    public class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

    }

    private Node findNode(Object key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        Comparable<? super K> comparable = (Comparable<? super K>) key;

        Node node = root;
        while (node != null) {
            if (comparable.compareTo(node.key) > 0) {
                node = node.right;
            } else if (comparable.compareTo(node.key) < 0) {
                node = node.left;
            } else {
                return node;
            }
        }
        return null;
    }

    @Override
    public boolean remove(Object key, Object value) {
        return false;
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
    public boolean containsKey(Object key) {
        return findNode(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        /***stack***/
//        Deque<Node> stack = new LinkedList<>();
//        stack.push(root);
//
//        while(!stack.isEmpty()){
//            Node node = stack.pop();
//            if(node.right != null){
//                if(equals(value,node.right.value)){
//                    return true;
//                }
//                stack.push(node.right);
//            }
//            if(node.left != null){
//                if(equals(value,node.left.value)){
//                    return true;
//                }
//                stack.push(node.left);
//            }
//        }
//        return false;
        /***stack***/

        /***recursive***/
        return containsValueHelper(root,value);
        /***recursive***/
    }

    private boolean containsValueHelper(Node node,Object value){
        /**my wrong method**/
//        if (equals(value, node.value)) {
//            return true;
//        }
//        if (node.left != null) {
//            node = node.left;
//            return containValueHelper(node,value);
//        }
//        if (node.right != null) {
//            node = node.right;
//            return containValueHelper(node,value);
//        }
//        return false;
        /**my wrong method**/

        if (node == null) {
            return false;
        }
        if (equals(value, node.value)) {
            return true;
        }
        if (containsValueHelper(node.left, value)) {
            return true;
        }
        if (containsValueHelper(node.right, value)) {
            return true;
        }
        return false;
    }


    @Override
    public V get(Object key) {
        Node node = findNode(key);
        if (node == null) {
            return null;
        }
        return node.value;
    }

    @Override
    public V put(K key, V value) {
        if (key == null) {
            throw new NullPointerException();
        }
        if (root == null) {
            root = new Node(key, value);
            size++;
            return null;
        }
        return putHelper(root, key, value);
    }

    private V putHelper(Node node, K key, V value) {
        /**my implementation   search two times**/
//        V oldValue = null;
//        Node isExist = findNode(key);
//        if (isExist != null) {
//            oldValue = isExist.value;
//            isExist.value = value;
//        } else {
//            Comparable<? super K> comparable = (Comparable<? super K>) key;
//
//            while (node != null) {
//                if (comparable.compareTo(node.key) > 0) {
//                    if (node.right == null) {
//                        node.right = new Node(key, value);
//                        size++;
//                        return null;
//                    }
//                    node = node.right;
//                } else if (comparable.compareTo(node.key) < 0) {
//                    if (node.left == null) {
//                        node.left = new Node(key, value);
//                        size++;
//                        return null;
//                    }
//                    node = node.left;
//                }
//            }
//        }
//        return oldValue;
        /**my implementation**/

        /**book implementation**/
        Comparable<? super K> k = (Comparable<? super K>) key;
        int cmp = k.compareTo(node.key);

        if (cmp < 0) {
            if (node.left == null) {
                node.left = new Node(key, value);
                size++;
                return null;
            } else {
                return putHelper(node.left, key, value);
            }
        }
        if (cmp > 0) {
            if (node.right == null) {
                node.right = new Node(key, value);
                size++;
                return null;
            } else {
                return putHelper(node.right, key, value);
            }
        }
        V oldValue = node.value;
        node.value = value;
        return oldValue;
        /**book implementation**/

    }

    @Override
    public V remove(Object key) {
        V oldValue = null;
        //if root
        if (equals(key, root.key)) {
            oldValue = selfBalance(null, key);
            size--;
        } else {
            Node parentNode = findParentNode(key);
            if (parentNode != null) {
                oldValue = selfBalance(parentNode, key);
                size--;
            }
        }
        return oldValue;
    }

    private Node findParentNode(Object key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        Node parentNode = null;
        Comparable<? super K> comparable = (Comparable<? super K>) key;

        Node node = root;
        while (node != null) {
            if (comparable.compareTo(node.key) > 0) {
                parentNode = node;
                node = node.right;
            } else if (comparable.compareTo(node.key) < 0) {
                parentNode = node;
                node = node.left;
            } else {
                return parentNode;
            }
        }
        return null;
    }

    private V selfBalance(Node parentNode, Object key) {
        V oldValue = null;
        Node target = null;
        Node left = null;
        Node right = null;
        //root
        if (parentNode == null) {
            oldValue = root.value;
            left = root.left;
            right = root.right;
            if(left == null && right == null){
                root = null;
            } else if (right != null) {
                root = right;
                if(left != null) {
                    put(left.key, left.value);
                }
            } else if(left != null){
                root = left;
                if(right != null){
                    put(right.key,right.value);
                }
            }
        } else if (parentNode.left != null && equals(key, parentNode.left.key)) { //left subTree
            target = parentNode.left;
            oldValue = target.value;
            left = target.left;
            right = target.right;
            if(left == null && right == null){
                parentNode.left = null;
            }else if (left != null) {
                parentNode.left = left;
                if (right != null) {
                    left.right = right;
                }
            }
        } else if (parentNode.right != null && equals(key, parentNode.right.key)) { //right subTree
            target = parentNode.right;
            oldValue = target.value;
            left = target.left;
            right = target.right;
            if(left == null && right == null){
                parentNode.right = null;
            }else if (right != null) {
                parentNode.right = right;
                if (left != null) {
                    right.left = left;
                }
            }
        }
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }


    @Override
    public Set<K> keySet() {
        Set<K> set = new LinkedHashSet<>();
        addInOrder(root,set);
        return set;
    }

    private void addInOrder(Node node,Set<K> set){
        if(node == null) return;
        addInOrder(node.left,set);
        set.add(node.key);
        addInOrder(node.right,set);
    }

    @Override
    public Collection<V> values() {
        Set<V> set = new HashSet<>();
        Deque<Node> stack = new LinkedList<>();
        stack.push(root);

        while (!stack.isEmpty()){
            Node node = stack.pop();
            if (node == null) continue;
            set.add(node.value);
            stack.push(node.left);
            stack.push(node.right);
        }
        return set;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
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

    public static void main(String[] args) {
        Map<Integer, String> map = new MyTreeMap<>();
        map.put(1, "1");
        map.put(4, "4");
        map.put(5, "5");
        map.put(2, "2");
        map.put(7, "7");
        map.put(3, "3");
        map.put(8, "8");
        map.put(0, "0");
        map.put(9, "9");
        map.put(6, "6");
        System.out.println(Arrays.toString(map.keySet().toArray()));
        map.remove(1);
        System.out.println(Arrays.toString(map.keySet().toArray()));

        Map<Integer, String> map2 = new MyTreeMap<>();
        map2.put(1, "1");
        System.out.println(Arrays.toString(map2.keySet().toArray()));
        map2.remove(1);
        System.out.println(Arrays.toString(map2.keySet().toArray()));
    }
}
