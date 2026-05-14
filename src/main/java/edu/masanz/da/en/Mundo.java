package edu.masanz.da.en;

import java.util.Map;

public class Mundo {
    // Diccionario de objetos: ID -> Instancia
    private Map<String, Sala> salas;
    private Map<String, Jugador> jugadores;

    // Diccionario de estado: ID Jugador -> ID Sala
    private Map<String, String> ubicaciones;
}
