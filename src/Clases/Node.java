/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.util.List;

/**
 *
 * @author Usuario
 */
public class Node{
    private String name;
    private List<Node> sub;

    public Node() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getSub() {
        return sub;
    }

    public void setSub(List<Node> sub) {
        this.sub = sub;
    }
}