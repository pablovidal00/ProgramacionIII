/*
 * 39494048N
 * Pablo Vidal Fernández
 * pablovidalfdez@usal.es
 * Programación III, Grado en Estadística
 */
package data;

import java.io.Serializable;
import static stripAccents.StripAccents.stripAccents;


/**
 *
 * @author pablo
 */
public class Datos_equipo implements Serializable, Cloneable{
    
    String nombre;
    int partidos_jugados = 0;
    int partidos_ganados = 0;
    int partidos_perdidos = 0;
    int puntos_clasificacion = 0;
    int puntos_favor = 0;
    int puntos_contra = 0;
    private static final long serialVersionUID = 1L;
    //para garantizar que la serialización funciona aunque haya un cambio en el programa
    
    //métodos getter y setter

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPartidos_jugados() {
        return partidos_jugados;
    }

    public void setPartidos_jugados(int partidos_jugados) {
        this.partidos_jugados = partidos_jugados;
    }

    public int getPartidos_ganados() {
        return partidos_ganados;
    }

    public void setPartidos_ganados(int partidos_ganados) {
        this.partidos_ganados = partidos_ganados;
    }

    public int getPartidos_perdidos() {
        return partidos_perdidos;
    }

    public void setPartidos_perdidos(int partidos_perdidos) {
        this.partidos_perdidos = partidos_perdidos;
    }

    public int getPuntos_clasificacion() {
        return puntos_clasificacion;
    }

    public void setPuntos_clasificacion(int puntos_clasificacion) {
        this.puntos_clasificacion = puntos_clasificacion;
    }

    public int getPuntos_favor() {
        return puntos_favor;
    }

    public void setPuntos_favor(int puntos_favor) {
        this.puntos_favor = puntos_favor;
    }

    public int getPuntos_contra() {
        return puntos_contra;
    }

    public void setPuntos_contra(int puntos_contra) {
        this.puntos_contra = puntos_contra;
    }
    
    public String[] getDatosTabulares(){
        String [] datosTabulares = {this.nombre, String.valueOf(this.puntos_clasificacion),
             String.valueOf(this.partidos_jugados),  String.valueOf(this.partidos_ganados),  
             String.valueOf(this.partidos_perdidos), 
              String.valueOf(this.puntos_favor),  String.valueOf(this.puntos_contra)};
        return datosTabulares;
        
        //devuelve los datos del equipo de forma adecuada para mostrar en una tabla
    }
   
    //constructor

    public Datos_equipo(String nombre) {
        this.nombre = nombre;
    }
    
    //para ir actualizando 
    
    public void actualizar(Partido partido){
        if(stripAccents(partido.getLocal()).equalsIgnoreCase(stripAccents(this.nombre))){
            this.puntos_favor += partido.getPuntos_local();
            this.puntos_contra += partido.getPuntos_visitante();
            //se comprueba si el equipo local del partido es el equipo instanciado
            //en dicho caso, suma los puntos a favor del local y suma los puntos en contra del visitante
            if(partido.victoriaLocal()){
                this.partidos_ganados++;
                this.puntos_clasificacion += 2;
                this.partidos_jugados++;
                //el método victoriaLocal() devuelve true si ha ganado el local;
                //por lo que suma un partido ganado, uno jugado y dos puntos en la clasificacion
            }else if(partido.victoriaVisitante()){
                this.partidos_perdidos++;
                this.puntos_clasificacion +=1;
                this.partidos_jugados++;
                //el método victoriaVisitante() devuelve true si ha perdido el local;
                //por lo que suma un partido perdido, uno jugado y dos puntos en la clasificacion
            }
            
            //notese que si hay empate es porque el resultado es 0-0, que simboliza que
            //el partido no se ha jugado ya que en baloncesto no hay empates
            //en este caso, no se suma nada ya que no se ha disputado el partido
            
        }else if(stripAccents(partido.getVisitante()).equalsIgnoreCase(stripAccents(this.nombre))){
            //todo este bloque de código hace lo mismo que el de arriba pero si el equipo instanciado
            //es el visitante del partido
            this.puntos_favor += partido.getPuntos_visitante();
            this.puntos_contra += partido.getPuntos_local();
            if(partido.victoriaVisitante()){
                this.partidos_ganados++;
                this.puntos_clasificacion += 2;
                this.partidos_jugados++;
            }else if(partido.victoriaLocal()){
                this.partidos_perdidos++;
                this.puntos_clasificacion +=1;
                this.partidos_jugados++;
            }
        }
        //si el equipo no es ninguno de los que ha jugado el partido, no actualiza nada
    }
    
    //para poder copiar
   
    @Override
    
    protected Object clone() throws CloneNotSupportedException{
        Datos_equipo clone = null;
        try{
            clone = (Datos_equipo) super.clone();
        } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
        }
        return clone;
        
        //hace falta hacer el override a clone para poder hacer una deepcopy 
        //del arraylist clasificacion
        
        //en este caso, solo clona String e int así que no hace falta especificar más
        //desde aquí querría agradecer a StackOverflow por ayudarme a solucionar este problema
    }
    
}
