package com.exercise;

import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class WikiNodeExample {

    public void recursiveDFS(Node root){
        if(root instanceof TextNode) {
            System.out.println(root);
        }
        for(Node node : root.childNodes()){
            recursiveDFS(node);
        }
    }

    public void iterativeDFS(Node root){
        Deque<Node> stack = new ArrayDeque();
        stack.push(root);

        while(!stack.isEmpty()){
            Node node = stack.pop();
            if(node instanceof TextNode){
                System.out.println(root);
            }
            List<Node> children = node.childNodes();
            Collections.reverse(children);
            for(Node child : children){
                stack.push(child);
            }
        }
    }
}
