/*
 * 39494048N
 * Pablo Vidal Fernández
 * pablovidalfdez@usal.es
 * Programación III, Grado en Estadística
 */
package data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author pablo
 */
public class Jornada implements Serializable{
    
    private int numJornada;
    private String fecha;
    private ArrayList<Partido> partidos;
    private ArrayList<Datos_equipo> clasificacion;
    private static final long serialVersionUID = 1L;
    //para garantizar que la serialización funciona aunque haya un cambio en el programa
    
    //constructor
    
    public Jornada(){
        partidos = new ArrayList<>();
        clasificacion = new ArrayList<>();
    }
    
    //getters y setters varios
    
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getNumJornada() {
        return numJornada;
    }

    public void setNumJornada(int numJornada) {
        this.numJornada = numJornada;
    }   
    
    public void setPartidos(String[] Partidos){
        
        Partido partido_temporal;
        for (String partido:Partidos){
            partido_temporal = Partido.factory(partido);
            if(partido_temporal!=null){
                partidos.add(partido_temporal);
            }
        }
        //el método recibe un String[] de partidos, así que por cada uno
        //hace el factory de partido y si no es nulo, se añade a la lista de 
        //partidos
        
    }

    public void setClasificacion(ArrayList<Datos_equipo> clasificacion) {
        this.clasificacion = clasificacion;
    }

    public ArrayList<Datos_equipo> getClasificacion() {
        return clasificacion;
    }
    
    public ArrayList<Partido> getPartidos() {
        return partidos;
    }
    
    //métodos factory
    
    public static Jornada factory(String[] tokens){
        
        if(tokens.length!=3){
            return null;
        }
        
        Jornada jornada = new Jornada();
        try{
            jornada.setNumJornada(Integer.parseInt(tokens[0]));
            jornada.setFecha(tokens[1]);
            jornada.setPartidos(tokens[2].split("#"));
            //splitea por el separador y llama al método setPartidos 
            return jornada;
            
        }catch(Exception ex){
            return null;
        }
        
    }
    
    public static Jornada factory(String data){
        var tokens = data.split("[+]");
        return Jornada.factory(tokens);
    }
    
    //método para ubicar partidos y asignar resultados
    
    public void asignarResultado(String data){
        String[] datos = data.split("=");
        String nombre_local = datos[0];
        for(int i = 0; i<partidos.size(); i++){
            if(partidos.get(i).getLocal().equalsIgnoreCase(nombre_local)){
                partidos.get(i).setPuntos_local(Integer.parseInt(datos[2]));
                partidos.get(i).setPuntos_visitante(Integer.parseInt(datos[3]));
                break;
            }
        }
        //este método recibe como parámetro una línea con el nombre de los dos equipos
        //que disputan un partido y el resultado. Por tanto, coge el nombre del local y con un 
        //bucle for encuentra el partido que le corresponde. Una vez lo encuentra, asigna los puntos
        //a cada equipo
    }
  
} //fin de la clase Jornada