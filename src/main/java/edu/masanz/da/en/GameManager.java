package edu.masanz.da.en;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GameManager {

    private static final GameManager INSTANCE = new GameManager();

    // Diccionario de objetos: ID -> Instancia
    private Map<String, Sala> mapaSalas = new TreeMap<>();
    private Map<String, Jugador> mapaJugadores = new TreeMap<>();

    // Diccionario de estado: ID Jugador -> ID Sala
    public Map<Sala, List<Jugador>> mapaSalasListaJugadores = new TreeMap<>();

    private String mapaTextual = """
[cocina] - [pasillo] - [dormitorio]
   |           |             |
   L-------[despensa]--------J
""";

    private GameManager() {
        initMapaSalas();
        initMapaSalasListaJugadores();
    }

    private void initMapaSalasListaJugadores() {
        mapaSalasListaJugadores.put(mapaSalas.get("cocina"), new ArrayList<>());
        mapaSalasListaJugadores.put(mapaSalas.get("pasillo"), new ArrayList<>());
        mapaSalasListaJugadores.put(mapaSalas.get("dormitorio"), new ArrayList<>());
        mapaSalasListaJugadores.put(mapaSalas.get("despensa"), new ArrayList<>());
    }

    private void initMapaSalas() {
        Sala cocina = new Sala("cocina");
        Sala pasillo = new Sala("pasillo");
        Sala dormitorio = new Sala("dormitorio");
        Sala despensa = new Sala("despensa");
        cocina.addSalaAdyacente(pasillo);
        cocina.addSalaAdyacente(despensa);
        pasillo.addSalaAdyacente(dormitorio);
        pasillo.addSalaAdyacente(cocina);
        pasillo.addSalaAdyacente(despensa);
        dormitorio.addSalaAdyacente(pasillo);
        dormitorio.addSalaAdyacente(despensa);
        despensa.addSalaAdyacente(pasillo);
        despensa.addSalaAdyacente(cocina);
        despensa.addSalaAdyacente(dormitorio);
        mapaSalas.put("cocina", cocina);
        mapaSalas.put("pasillo", pasillo);
        mapaSalas.put("dormitorio", dormitorio);
        mapaSalas.put("despensa", despensa);
    }

    public static GameManager getInstance() {
        return INSTANCE;
    }

    public String getMapaTextual() {
        return mapaTextual;
    }

    public void addJugador(String nombre) {

        // Instancia jugador
        Jugador jugador = new Jugador(nombre);

        // Al 30% de posibilidades es un intruso
        if (Math.random()<0.3) { jugador.setImpostor(true); }

        // Poner en jugador en qué sala está
        int i = (int) (Math.random() * mapaSalas.size());
        Sala sala = (new ArrayList<>(mapaSalas.values())).get(i);
        jugador.setSala(sala);

        // Agregar al mapa de jugadores
        mapaJugadores.put(nombre, jugador);

        // Agregar a mapaSalasListaJugadores
        mapaSalasListaJugadores.get(sala).add(jugador);

    }

    public String whereIsJugador(String nombreJugador) {
        return mapaJugadores.get(nombreJugador).getSala().getNombre();
    }

    public boolean move(String clientName, String nombreSala) {
        //
        return true;
    }
}
