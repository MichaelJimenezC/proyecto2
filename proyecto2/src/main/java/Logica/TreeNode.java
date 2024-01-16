/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

/**
 *
 * @author Pauleth
 */
import java.util.LinkedList;
import java.util.List;

public class TreeNode<E> {

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
