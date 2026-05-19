package edu.masanz.da.en;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sala implements Comparable<Sala> {

    private String nombre;
    private List<Sala> salasAdyacentes;

    public Sala(String nombre){
        this.nombre = nombre;
        this.salasAdyacentes = new ArrayList<>();
    }

    // region getters & setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Sala> getSalasAdyacentes() {
        return salasAdyacentes;
    }

    public void setSalasAdyacentes(List<Sala> salasAdyacentes) {
        this.salasAdyacentes = salasAdyacentes;
    }

    // endregion

    public void addSalaAdyacente(Sala sala) {
        salasAdyacentes.add(sala);
    }

    @Override
    public int compareTo(Sala other) {
        return this.nombre.compareTo(other.nombre);
    }

//    @Override
//    public String toString() {
//        return nombre + " " + Arrays.toString(salasAdyacentes.toArray());
//    }
//
//    public static void main(String[] args) {
//        for (Sala sala : Mundo.getInstance().mapaSalas.values()) {
//            System.out.println(sala);
//        }
//    }

}
