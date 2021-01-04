/*
 * 39494048N
 * Pablo Vidal Fernández
 * pablovidalfdez@usal.es
 * Programación III, Grado en Estadística
 */
package controller;

import data.Equipo;
import data.LigFemBal;
import java.util.ArrayList;

/**
 *
 * @author pablo
 */
public class Controller {
    
    LigFemBal m = new LigFemBal();
    
    //LOS MÉTODOS NO ESTÁN ORDENADOS PORQUE TODOS HACEN LLAMÁDAS A MÉTODOS DEL MODELO
    //DE IGUAL NOMBRE Y PASAN COMO PARÁMETRO LO QUE RECIBEN EN VIEW
    //LA ÚNICA OPERACIÓN ES EN EL MÉTODO leerResultados(),
    //DONDE ASIGNA A UN STRING EL NÚMERO ESCRITO DE LA JORNADA

    public void setTemporada(String temporada) {
        m.setTemporada(temporada);
    }

    public void cargarEquipos() {
        m.cargarEquipos();
    }

    public int getNumEquipos() {
        return m.getNumEquipos();
    }

    public void cargarJugadoras() {
        m.cargarJugadoras();
    }

    public int getNumJugadorasPE() {
        return m.getNumJugadorasPE();
    }

    public void cargarJornadas() {
        m.cargarJornadas();
    }

    public int getNumJornadas() {
        return m.getNumJornadas();
    }

    public void cargar() {
        m.cargar();
    }
    
    public void guardar(){
        m.guardar();
    }

    public void eliminarJugadora(int indexEquipo, int indexJugadora) {
        m.eliminarJugadora(indexEquipo, indexJugadora);
    }

    public void añadirJugadora(int indexEquipo, String[] datos) {
        m.añadirJugadora(indexEquipo, datos);
    }

    public void modificarJugadora(int indexEquipo, int indexJugadora, int eleccion, String atributo) {
        m.modificarJugadora(indexEquipo, indexJugadora, eleccion, atributo);
    }

    public ArrayList<String> getNombresEquipos() {
        return m.getNombresEquipos();
    }

    public Equipo getEquipo(int index) {
        return m.getEquipo(index);
    }

    public String getTemporada() {
        return m.getTemporada();
    }

    public void ordenarJugadorasEquipo(int indice_equipo) {
        m.ordenarJugadorasEquipo(indice_equipo);
    }

    public String[][] getJugadorasEquipo(int indice_equipo) {
        return m.getJugadorasEquipo(indice_equipo);
    }

    public String getDatosIndividualesJugadora(int indice_equipo) {
         return m.getDatosIndividualesJugadora(indice_equipo);
    }

    public void ordenarEquipos() {
        m.ordenarEquipos();
    }

    public String[][] getEquiposOrdenados() {
        return m.getEquiposOrdenados();
    }

    public String[][] busquedaJugadora(Character letra) {
         return m.busquedaJugadora(letra);
    }

    public void guardarJugadoras(String equipo, int indice_equipo) {
        m.guardarJugadoras(equipo, indice_equipo);
    }

    public void guardarEquipos() {
        m.guardarEquipos();
    }

    public void leerResultadosJornada(int num_jornada) {
        String jornada = new String();
        switch(num_jornada){
            case 1 -> jornada = "uno";
            case 2 -> jornada = "dos";
            case 3 -> jornada = "tres";
            case 4 -> jornada = "cuatro";
            case 5 -> jornada = "cinco";
            case 6 -> jornada = "seis";
            case 7 -> jornada = "siete";
            case 8 -> jornada = "ocho";
            case 9 -> jornada = "nueve";
            case 10 -> jornada = "diez";
            case 11 -> jornada = "once";
            case 12 -> jornada = "doce";
            case 13 -> jornada = "trece";
            case 14 -> jornada = "catorce";
            case 15 -> jornada = "quince";
        } //reconozco que esto es un poco chapuza pero no se me ocurría otra cosa
        
        //al menos el controller hace una operación
        m.leerResultadosJornada(jornada, num_jornada);
    }

    public void modificarFechaJornada(int num_jornada, String nueva_fecha) {
         m.modificarFechaJornada(num_jornada, nueva_fecha);
    }

    public void modificarFechaPartido(int num_jornada, String nombre_equipo, String nuevo_valor) {
        m.modificarFechaPartido(num_jornada, nombre_equipo, nuevo_valor);
    }

    public void modificarHoraPartido(int num_jornada, String nombre_equipo, String nuevo_valor) {
        m.modificarHoraPartido(num_jornada, nombre_equipo, nuevo_valor);
    }

    public String getResultadosJornada(int num_jornada) {
        return m.getResultadosJornada(num_jornada);
    }
    
    public String[][] getClasificacion(int num_jornada){
        return m.getClasificacion(num_jornada);
    }
    
} //fin de la clase Controller   
