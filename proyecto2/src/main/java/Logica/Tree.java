/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Pauleth
 */
public class Tree<E> {

    private class TreeNode<E> {

        private E content;
        private List<Tree<E>> children;

        public TreeNode(E content) {
            this.content = content;
            this.children = new LinkedList<>();
        }

        public E getContent() {
            return content;
        }

        public void setContent(E content) {
            this.content = content;
        }

        public List<Tree<E>> getChildren() {
            return children;
        }

        public void setChildren(List<Tree<E>> children) {
            this.children = children;
        }

        public void addChild(Tree<E> child) {
            this.children.add(child);
        }

        public void addChildren(List<Tree<E>> children) {
            this.children.addAll(children);
        }

        public Tree<E> getChild(int index) {
            if (index < 0 || index >= children.size()) {
                throw new IndexOutOfBoundsException("Índice fuera de rango.");
            }
            return children.get(index);
        }

        public int getNumberOfChildren() {
            return children.size();
        }

        public boolean hasChildren() {
            return !children.isEmpty();
        }

    }

    private TreeNode<E> root;

    public Tree() {
        this.root = new TreeNode<>(null); // O proporciona un contenido inicial si es necesario
    }

    public Tree(E content) {
        this.root = new TreeNode<>(content);
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public E getRoot() {
        return root.getContent();
    }

    private TreeNode<E> getRootNode() {
        return this.root;
    }

    private void setRootNode(TreeNode<E> root) {
        this.root = root;
    }

    public void setRoot(E content) {
        this.root.setContent(content);
    }

    public boolean isLeaf() {
        return this.root.getChildren().isEmpty();
    }

    public void addChild(E content) {
        TreeNode<E> childNode = new TreeNode<>(content);
        this.root.getChildren().add(new Tree<>(childNode.getContent()));
    }
    public List<E> getChildrenContent() {
        List<E> childrenContent = new ArrayList<>();
        for (Tree<E> child : this.root.getChildren()) {
            childrenContent.add(child.getRoot());
        }
        return childrenContent;
    }
    
}
