/*
 * 39494048N
 * Pablo Vidal Fernández
 * pablovidalfdez@usal.es
 * Programación III, Grado en Estadística
 */
package data;

import java.io.Serializable;

/**
 *
 * @author pablo
 */
public class Partido implements Serializable {
    
    private String local;
    private String visitante;
    private int puntos_local = 0;
    private int puntos_visitante = 0;
    private String hora;
    private String fecha;
    private static final long serialVersionUID = 1L;
    //para garantizar que la serialización funciona aunque haya un cambio en el programa
    
    //Getters y setters
    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getVisitante() {
        return visitante;
    }

    public void setVisitante(String visitante) {
        this.visitante = visitante;
    }

    public int getPuntos_local() {
        return puntos_local;
    }

    public void setPuntos_local(int puntos_local) {
        this.puntos_local = puntos_local;
    }

    public int getPuntos_visitante() {
        return puntos_visitante;
    }

    public void setPuntos_visitante(int puntos_visitante) {
        this.puntos_visitante = puntos_visitante;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public String getResultado(){
        String resultado = String.format("%s - %s: %d-%d%n", this.getLocal(), 
                this.getVisitante(), this.getPuntos_local(), this.getPuntos_visitante());
        return resultado;
        //devuelve un String con los equipos que han disputado el encuentro y su resultado
    }
    
    //Constructores
    public Partido(String local, String visitante, String hora, String fecha) {
        this.local = local;
        this.visitante = visitante;
        this.hora = hora;
        this.fecha = fecha;
    }

    public Partido() {
        
    }
    
    //métodos para saber el resultado
    public boolean victoriaLocal(){
        boolean resultado = this.puntos_local>this.puntos_visitante;
        return resultado;
        //true si gana el local
    }
    
    public boolean victoriaVisitante(){
        boolean resultado = this.puntos_local<this.puntos_visitante;
        return resultado;
        //true si gana el visitante
    }
    //métodos factory
    public static Partido factory(String[] data){
        
        if(data.length!=4){
            return null;
        }
        
        Partido partido = new Partido();
        try{
            
            partido.setLocal(data[0]);
            partido.setVisitante(data[1]);
            partido.setHora(data[2]);
            partido.setFecha(data[3]);
            return partido;
        } catch(Exception ex){
            System.out.println("Nooooo algo salió mal");
            System.out.println(ex.toString());
            return null;
        }
        
    }
    
    public static Partido factory(String data){
        
        String[] datos = data.split("[$]");
        return Partido.factory(datos);
        
    }
    
}
