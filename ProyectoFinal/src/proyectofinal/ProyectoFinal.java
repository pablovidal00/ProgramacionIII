/*
 * 39494048N
 * Pablo Vidal Fernández
 * pablovidalfdez@usal.es
 * Programación III, Grado en Estadística
 */
package proyectofinal;

import static com.coti.tools.DiaUtil.showFinalTime;
import static java.lang.System.out;
import view.View;

/**
 *
 * @author pablo
 */
public class ProyectoFinal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        
        View v = new View();
        v.cargar();
        v.runMenu("%n1.- Gestión de temporada"
                + "%n2.- Gestión de jugadoras"
                + "%n3.- Gestión de jornadas"
                + "%n4.- Visualizar"
                + "%n5.- Almacenar información"
                + "%nq.- Salir%n");
        v.guardar();
        out.printf("%n%nTerminación normal del programa%n%n");
        showFinalTime();
        
    } //fin del main 
    
} //fin de la clase Solución
