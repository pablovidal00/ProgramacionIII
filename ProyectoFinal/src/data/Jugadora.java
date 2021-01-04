/*
 * 39494048N
 * Pablo Vidal Fernández
 * pablovidalfdez@usal.es
 * Programación III, Grado en Estadística
 */
package data;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;
/**
 *
 * @author pablo
 */
public class Jugadora implements Serializable{
    
    //declaración de variables
    private String nombre;
    private String posicion;
    private String datos_nacimiento;
    private int dorsal;
    private String nacionalidad;
    private int altura;
    private static final long serialVersionUID = 1L;
    //para garantizar que la serialización funciona aunque haya un cambio en el programa
    
    //constructores
    public Jugadora(String nombre, String posicion, int dorsal, String datos_nacimiento, String nacionalidad, int altura) {
        
        this.nombre = nombre;
        this.posicion = posicion;
        this.datos_nacimiento = datos_nacimiento;
        this.nacionalidad = nacionalidad;
        this.altura = altura;
        this.dorsal = dorsal;
        
    } //constructor con todas las propiedades

    public Jugadora() {
        
    } //constructor vacio
     
    //setters y getters
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getDatos_nacimiento() {
        return datos_nacimiento;
    }

    public void setDatos_nacimiento(String datos_nacimiento) {
        this.datos_nacimiento = datos_nacimiento;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
    
    public Date getFecha(){
        String[] datos_spliteados = this.getDatos_nacimiento().split(" ");
        String string_fecha = datos_spliteados[0];
        Date fecha = new Date(); 
        //si no se indica lo contrario, se asigna la fecha de nacimiento 1/1/2000
        //en algún momento tendrá que haber nacido la jugadora y esta fecha parece
        //tan buena como otra cualquiera
        try{
            fecha = new SimpleDateFormat("dd/MM/yyyy").parse(string_fecha); 
        }catch(Exception ex){
            try{
                fecha = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2000");
        //si no se indica lo contrario, se asigna la fecha de nacimiento 1/1/2000
        //en algún momento tendrá que haber nacido la jugadora y esta fecha parece
        //tan buena como otra cualquiera
            }catch(Exception ex2){} //este catch hay que ponerlo pero el try de arriba siempre va a funcionar
        }
        return fecha;
        
        //este método sirve para separar la fecha de los datos de nacimiento
        //y así poder ordenar por este atributo a las jugadoras
    }

    
    //fin de los getters y setters
    
    public String[] getDatosTabulares(){
        
        String[] datos = {this.getNombre(), this.getPosicion(), Integer.toString(this.getDorsal()), this.getDatos_nacimiento(),
            Integer.toString(this.getAltura()), this.getNacionalidad()};
        
        return datos;
        //se devuelve un String[] con los datos de la jugadora
    }
    
    public String getDatosIndividuales(){
        
        String datos = String.format("Nombre: %s%nPosición: %s%nDorsal: %d%n"
                + "Nacimiento: %s%nAltura: %d%nNacionalidad: %s",this.getNombre(),
                this.getPosicion(), this.getDorsal(), this.getDatos_nacimiento(), 
                this.getAltura(), this.getNacionalidad());
        return datos;
        //Se devuelve un String con los datos bien bonitos de la jugadora
    }
    
    //métodos factory
    
    public static Jugadora factory(String[] data){
        
        if(data.length!=6){ 
            return null;
        }
        
        Jugadora jugadora = new Jugadora();
        try{
            jugadora.setNombre(data[0]);
            jugadora.setPosicion(data[1]);
            jugadora.setDorsal(Integer.parseInt(data[2]));
            jugadora.setDatos_nacimiento(data[3]);
            jugadora.setNacionalidad(data[4]);
            jugadora.setAltura(Integer.parseInt(data[5]));
            return jugadora;
        } catch(Exception ex){
            //out.printf("Algo ha fallado, amigo%n");
            System.out.println(ex.toString());
            return null;
        }
      
    }
    
    public static Jugadora factory(String data){
        
        String [] datos = data.split("\t");
        return Jugadora.factory(datos);
        
    }
    
} //fin de la clase Jugadora
