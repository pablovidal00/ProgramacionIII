/*
 * 39494048N
 * Pablo Vidal Fernández
 * pablovidalfdez@usal.es
 * Programación III, Grado en Estadística
 */
package data;

import java.io.Serializable;
import static java.lang.System.err;
import java.util.ArrayList;
import static stripAccents.StripAccents.stripAccents;

/**
 *
 * @author pablo
 */
public class Equipo implements Serializable {
    
    //declaración de variables
    private String nombre;
    private String direccion;
    private String telefono;
    private String web;
    private String email;
    private ArrayList<Jugadora> jugadoras;
    private ArrayList<String> nombres_jugadoras;
    private static final long serialVersionUID = 1L;
    //para garantizar que la serialización funciona aunque haya un cambio en el programa
    
    //getters y setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    //añadir jugadoras a un equipo
    
    public void addJugadora(Jugadora jugadora){
        jugadoras.add(0, jugadora);
        //se añade siempre en la primera posición para localizarla e imprimir sus datos
    }
    
    public void addNombreJugadora(String nombre){
        nombres_jugadoras.add(0, stripAccents(nombre.toUpperCase()));
        //se añade en la primera posición para que tenga la misma posición que su correspondiente Jugadora
    }
    
    public ArrayList<Jugadora> getJugadoras(){
        return this.jugadoras;
    }
    
    public ArrayList<String> getNombresJugadoras(){
        return this.nombres_jugadoras;
    }
    
    public void setNombresJugadoras(){
        if(!nombres_jugadoras.isEmpty()){
            nombres_jugadoras.clear();
        }
        if(!jugadoras.isEmpty()){
            for(Jugadora jugadora:jugadoras){
                nombres_jugadoras.add(stripAccents(jugadora.getNombre().toUpperCase()));
            }
        } 
        //si no está vacío se vacía
        //se recorre el ArrayList de Jugadoras y se añaden sus nombres
        //es muy práctico que las posiciones de la jugadora y su nombre coincidan
    }
    
    //constructores
    
    public Equipo(String nombre, String direccion, String telefono, String web, String email) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.web = web;
        this.email = email;
        this.jugadoras = new ArrayList<>();
        this.nombres_jugadoras = new ArrayList<>();
    } //creo que no se usa este constructor pero bueno por si quisiese instanciarlo así en el factory
    
    public Equipo(){
        this.jugadoras = new ArrayList<>();
        this.nombres_jugadoras = new ArrayList<>();
    }
    
    //métodos para devolver un array de Strings con los datos del equipo 
    
    public String[] getDatos(){
        String[] datos = {this.getNombre(), this.getDireccion(), this.getTelefono(),
            this.getWeb(), this.getEmail()};
        return datos;
        //devuelve un String[] con los datos del equipo
    }
    
    public String getDatosMenosDireccion(){
        String datos = String.format("%-40s | %-15s | %-45s | %-40s%n", this.getNombre(), this.getTelefono(),
            this.getWeb(), this.getEmail());
        return datos;
        //devuelve un String con los datos del equipo y ancho fijo
        //este método se utiliza cuando se guardan los equipos en .enc
    }
    
    //métodos factory, como los vistos en clase
    
    public static Equipo factory(String[] data){
        
        if(data.length != 5){
            return null;
        }
        Equipo equipo = new Equipo();
        try{
            equipo.setNombre(data[0]);
            equipo.setDireccion(data[1]);
            equipo.setTelefono(data[2]);
            equipo.setWeb(data[3]);
            equipo.setEmail(data[4]);
            equipo.jugadoras = new ArrayList<>(); //se inicializa el ArrayList
            return equipo; //devuelve el equipo que se ha creado
        }catch(Exception ex){
            err.printf("%nEquipo fallido%n");
            ex.toString();
            return null;
        }
    
    }
    
    public static Equipo factory(String data){
        
        String[] datos = data.split("#");
        return Equipo.factory(datos);
        
    }
        
} //fin de la clase Equipo
