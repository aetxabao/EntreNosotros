package edu.masanz.da.en;

public class Jugador {

    private String nombre;
    private boolean esImpostor;
    private boolean estaVivo;
    private Sala sala;

    public  Jugador(String nombre) {
        this.nombre = nombre;
        this.esImpostor = false;
        this.estaVivo = true;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEsImpostor() {
        return esImpostor;
    }

    public void setEsImpostor(boolean esImpostor) {
        this.esImpostor = esImpostor;
    }

    public boolean isEstaVivo() {
        return estaVivo;
    }

    public void setEstaVivo(boolean estaVivo) {
        this.estaVivo = estaVivo;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }
}
