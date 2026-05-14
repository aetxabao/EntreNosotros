package edu.masanz.da.en;

import java.util.ArrayList;
import java.util.List;

public class Sala {

    private String nombre;
    private List<Sala> salasAdyacentes;

    public Sala(String nombre){
        this.nombre = nombre;
        this.salasAdyacentes = new ArrayList<>();
    }


}
