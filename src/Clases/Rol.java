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
public class Rol {
    private String nombre;
    private List<Rol> sub;

    public Rol() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Rol> getSub() {
        return sub;
    }

    public void setSub(List<Rol> sub) {
        this.sub = sub;
    }
}