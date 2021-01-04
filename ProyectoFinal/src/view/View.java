/*
 * 39494048N
 * Pablo Vidal Fernández
 * pablovidalfdez@usal.es
 * Programación III, Grado en Estadística
 */
package view;

import static com.coti.tools.Esdia.readInt;
import static com.coti.tools.Esdia.readString;
import static com.coti.tools.Esdia.yesOrNo;
import static com.coti.tools.OpMat.printToScreen3;
import controller.Controller;
import data.Equipo;
import static java.lang.System.out;
import java.util.ArrayList;
import static stripAccents.StripAccents.stripAccents;

/**
 *
 * @author pablo
 */
public class View {
    
    Controller c = new Controller();

    public void runMenu(String menu) throws Exception {
        //este es el clásico runMenu, imprime las diferentes opciones y recibe la orden del usuario
        boolean salir = false;
        String option;
        String[] availableOptions = {"1", "2", "3", "4", "5", "q"};
        do{
            System.out.printf("%nMENÚ PRINCIPAL");
            option = readString(menu, availableOptions);
            switch(option){
                case "1" -> this.modoGestionTemporada();
                case "2" -> this.modoGestionJugadoras();
                case "3" -> this.modoGestionJornadas();
                case "4" -> this.modoVisualizar();
                case "5" -> this.modoAlmacenarResultado();
                case "q" -> salir = yesOrNo("%nDesea salir del programa?%n");
                default -> System.out.println("%nNooo qué hiciste esa no es una opción%n");
            } //fin del switch
        } while(!salir); //fin del do-while
        
    } //fin del runMenu
    
    //diferentes opciones del programa
    
    private void modoGestionTemporada(){
        //este es un submenú para la gestión de la temporada
        //cada opción lleva a su correspondiente método
        System.out.printf("%nMODO GESTIÓN TEMPORADA%n");
        String option;
        boolean salir = false;
        do{
            System.out.println("1.- Iniciar temporada");
            System.out.println("2.- Cargar jornadas");
            System.out.println("3.- Cargar equipos");
            System.out.println("4.- Cargar jugadoras");
            System.out.println("q.- Volver al menú anterior");
            option = readString("%nIntroduzca su elección: %n"); 
            switch(option){
                case "1" -> this.setTemporada();
                case "2" -> this.cargarJornadas();
                case "3" -> this.cargarEquipos();
                case "4" -> this.cargarJugadoras();
                case "q" -> salir = true;
                default -> System.out.println("\nNo es una expresión válida\n");
            }
       
        }while(!salir);
        
    }    
    
    private void modoGestionJugadoras(){
        //Comprobaciones acerca de si existe la temporada, hay jornadas, equipos y jugadoras cargadas
        if(c.getTemporada().isEmpty()){
            System.out.printf("%nAmigo, has de iniciar la temporada y cargar las"
                    + " jornadas, los equipos y las jugadoras%n");
            return;
        }
        if(c.getNumJornadas()==0){
            System.out.printf("%nAmigo, has de cargar las jornadas, los equipos y las jugadoras!%n");
            return;
        }
        
        if(c.getNumEquipos()==0){
            System.out.printf("%nAmigo, has de cargar los equipos y las jugadoras!%n");
            return;
        }
        
        if(c.getNumJugadorasPE()==0){
            System.out.printf("Amigo, carga las jugadoras!");
            return;
        }
        //después de las comprobaciones, se abre el submenú de gestión de jugadoras
        //cada opción lleva a su correspondiente método
        boolean salir = false;
        String option;
        do{
            System.out.printf("%nMODO GESTIÓN JUGADORA%n");
            System.out.println("1.- Modificar datos de una jugadora");
            System.out.println("2.- Eliminar una jugadora de un equipo");
            System.out.println("3.- Añadir una jugadora a un equipo");
            System.out.println("q.- Volver al menú anterior");
            option = readString("%nIntroduzca su elección: %n"); 
            switch(option){
                case "1" -> this.modificarJugadora();
                case "2" -> this.eliminarJugadora();
                case "3" -> this.añadirJugadora();
                case "q" -> salir = true;
                default -> System.out.println("\nNo es una expresión válida\n");
            }
       
        } while(!salir);
 
    }
    
    private void modoGestionJornadas(){
        
        //Comprobaciones acerca de si existe la temporada, hay jornadas, equipos y jugadoras cargadas
        if(c.getTemporada().isEmpty()){
            System.out.printf("%nAmigo, has de iniciar la temporada y cargar las"
                    + " jornadas, los equipos y las jugadoras%n");
            return;
        }
        if(c.getNumJornadas()==0){
            System.out.printf("%nAmigo, has de cargar las jornadas, los equipos y las jugadoras!%n");
            return;
        }
        
        if(c.getNumEquipos()==0){
            System.out.printf("%nAmigo, has de cargar los equipos y las jugadoras!%n");
            return;
        }
        
        if(c.getNumJugadorasPE()==0){
            System.out.printf("Amigo, carga las jugadoras!");
            return;
        }
        
        //al acabar las comprobaciones, lleva al submenú de gestión de jornadas
        //cada opción lleva a su correspondiente método
        boolean salir = false;
        String option;
        do{
            System.out.printf("%nMODO GESTIÓN DE JORNADAS%n");
            System.out.println("1.- Leer resultados de la jornada");
            System.out.println("2.- Modificar fecha de la jornada");
            System.out.println("3.- Modificar fecha u hora de un partido");
            System.out.println("4.- Mostrar los resultados de la jornada");
            System.out.println("5.- Mostrar la clasificación de una jornada");
            System.out.println("q.- Volver al menú anterior");
            option = readString("%nIntroduzca su elección: %n"); 
            switch(option){
                case "1" -> this.leerResultadosJornada();
                case "2" -> this.modificarFechaJornada();
                case "3" -> this.modificarFechaPartido();
                case "4" -> this.mostrarResultadosJornada();
                case "5" -> this.mostrarClasificacionJornada();
                case "q" -> salir = true;
                default -> System.out.println("\nNo es una expresión válida\n");
            }
       
        } while(!salir);
 
    }

    private void modoVisualizar() throws Exception {
        
        //Comprobaciones acerca de si existe la temporada, hay jornadas, equipos y jugadoras cargadas
        if(c.getTemporada().isEmpty()){
            System.out.printf("%nAmigo, has de iniciar la temporada y cargar las"
                    + " jornadas, los equipos y las jugadoras%n");
            return;
        }
        if(c.getNumJornadas()==0){
            System.out.printf("%nAmigo, has de cargar las jornadas, los equipos y las jugadoras!%n");
            return;
        }
        
        if(c.getNumEquipos()==0){
            System.out.printf("%nAmigo, has de cargar los equipos y las jugadoras!%n");
            return;
        }
        
        if(c.getNumJugadorasPE()==0){
            System.out.printf("Amigo, carga las jugadoras!");
            return;
        }
        
        //después de acabar las comprobaciones, aparece el submenú de gestión de visualización
        //cada opción lleva a su correspondiente método
        boolean salir = false;
        String option;
        do{
            System.out.printf("%nMODO VISUALIZACIÓN%n");
            System.out.println("1.- Visualizar jugadoras de un equipo");
            System.out.println("2.- Listado de equipos");
            System.out.println("3.- Búsqueda de jugadoras");
            System.out.println("q.- Volver al menú anterior");
            option = readString("%nIntroduzca su elección: %n"); 
            switch(option){
                case "1" -> this.mostrarJugadorasEquipo();
                case "2" -> this.mostrarEquipos();
                case "3" -> this.mostrarBusquedaJugadora();
                case "q" -> salir = true;
                default -> System.out.println("\nNo es una expresión válida\n");
            }
       
        } while(!salir);
        
    }

    private void modoAlmacenarResultado() {
        //Comprobaciones acerca de si existe la temporada, hay jornadas, equipos y jugadoras cargadas
        if(c.getTemporada().isEmpty()){
            System.out.printf("%nAmigo, has de iniciar la temporada y cargar las"
                    + " jornadas, los equipos y las jugadoras%n");
            return;
        }
        if(c.getNumJornadas()==0){
            System.out.printf("%nAmigo, has de cargar las jornadas, los equipos y las jugadoras!%n");
            return;
        }
        
        if(c.getNumEquipos()==0){
            System.out.printf("%nAmigo, has de cargar los equipos y las jugadoras!%n");
            return;
        }
        
        if(c.getNumJugadorasPE()==0){
            System.out.printf("Amigo, carga las jugadoras!");
            return;
        }
        
        boolean salir = false;
        String option;
        do{
            System.out.printf("%nMODO ALMACENAR%n");
            System.out.println("1.- Guardar jugadoras de un equipo en formato .enc");
            System.out.println("2.- Guardar relación de equipos con formato .enc");
            System.out.println("3.- Guardar clasificación de una jornada en formato HTML");
            System.out.println("q.- Volver al menú anterior");
            option = readString("%nIntroduzca su elección: %n"); 
            switch(option){
                case "1" -> this.guardarJugadoras();
                case "2" -> this.guardarEquipos();
                case "3" -> this.guardarClasificacionHTML();
                case "q" -> salir = true;
                default -> System.out.println("\nNo es una expresión válida\n");
            }
       
        } while(!salir);
        
    }
    
    //MÉTODOS DE GESTIÓN DE TEMPORADA

    private void setTemporada() {
        //lee la temporada y la pasa al controlador
        String temporada = readString("Introduzca la temporada%n(Solo se tienen datos de 2020/21): ");
        c.setTemporada(temporada);
    }

    private void cargarEquipos() {
        //llama al controlador y luego imprime cuántos equipos se han cargado
        c.cargarEquipos();
        System.out.printf("%nHay %d equipos cargados :)%n%n", c.getNumEquipos());
    }

    private void cargarJugadoras() {
        //llama al controlador para cargar las jugadoras e imprime cuántas ha cargado en el primer equipo
        c.cargarJugadoras();
        System.out.printf("%nHay %d jugadoras cargadas en el primer equipo :)%n%n", c.getNumJugadorasPE());
    }
        
    private void cargarJornadas() {
        //llama al controlador e imprime cuántas jornadas ha cargado
        c.cargarJornadas();
        System.out.printf("%nHay %d jornadas cargadas :)%n%n", c.getNumJornadas());
    }
    
    //MÉTODOS DE CARGA Y GUARDADO DE INFORMACIÓN RELATIVOS A ARCHIVOS BINARIOS

    public void cargar() {
        //llama al controlador para cargar los datos binarios al inicio del programa
        c.cargar();
    }
    
    public void guardar(){
        //llama al controlador para guardar los resultados en archivos binarios
        c.guardar();
    }
    
    //MÉTODOS DE GESTIÓN DE JUGADORAS
    
    private void modificarJugadora() {
        String nombre_equipo;
        String jugadora;
        ArrayList<String> nombres_equipos = c.getNombresEquipos();
        Equipo equipo;
        do{
            nombre_equipo = stripAccents(readString("¿De qué equipo es la jugadora? %n(Pulse q para salir): ").toUpperCase());
        }while(!nombres_equipos.contains(nombre_equipo) || nombre_equipo.equalsIgnoreCase("q"));
        
        //pide al usuario que introduzca el nombre del equipo y se comprueba si el equipo existe
        //mientras el equipo no exista, se seguirá pidiendo 
        
        if(nombre_equipo.equalsIgnoreCase("q")){
            return;
        }
        //si pulsa q, se sale de este menú y vuelve al anterior
        
        int indice_equipo = nombres_equipos.indexOf(nombre_equipo);
        equipo = c.getEquipo(indice_equipo);
        //como cuando se cargan los equipos se cargan sus nombres, la posicion 
        //del nombre del equipo en la lista de nombres es la misma que la del equipo
        //en la lista de equipos
        
        do{
            jugadora = stripAccents(readString("Introduzca el nombre completo de la jugadora%n(Pulse q para salir): ").toUpperCase());
        } while(!(equipo.getNombresJugadoras().contains(jugadora)||jugadora.equalsIgnoreCase("q")));
        
        if(jugadora.equalsIgnoreCase("q")){
            return;
        }
        
        //misma comprobación de antes. Se ve si la jugadora existe y mientras no exista
        //si la jugadora no existe, se seguirá pidiendo un nombre válido
        
        int indice_jugadora = equipo.getNombresJugadoras().indexOf(jugadora);
        //misma lógica de antes con el nombre de la jugadora y su posición en la lista de jugadoras
        
        
        //aquí empieza otro menú, con la estructura del clásico runMenu vista en clase
        boolean salir = false;
        String option;
        do{
            out.printf("%n¿Qué atributo desea modificar?");
            out.printf("%n1.- Posición"
                    + "%n2.- Dorsal"
                    + "%n3.- Fecha de nacimiento"
                    + "%n4.- Nacionalidad"
                    + "%n5.- Altura"
                    + "%nq.- Salir%n");

            option = readString("Introduzca su elección: ");
            String atributo = new String();
            switch(option){
                case "1":
                    atributo = readString("Introduzca la nueva posición: ");
                    break;
                case "2":
                    atributo = Integer.toString(readInt("Introduzca el nuevo dorsal: "));
                    break;
                    //hay que asegurarse de que el usuario efectivamente introduce un número
                    //ya que los dorsales son numéricos
                case "3":
                    atributo = readString("Introduzca la nueva fecha de nacimiento: ");
                    break;
                case "4":
                    atributo = readString("Introduzca la nueva nacionalidad: ");
                    break;
                case "5": 
                    atributo = Integer.toString(readInt("Introduzca la nueva altura"));
                    break;
                    //hay que asegurarse que la altura es numérica, como con el dorsal
                case "q":
                    salir = true;
                    break;
                default:
                    System.out.println("Amigo, ¡esa no es una opción válida!");
                    break;
            }
            boolean parseable = false;
            int a = -1;
            try{
                a = Integer.parseInt(option);
                parseable = true;
            } catch(Exception ex){
                parseable = false;
            }
            if(parseable){
                if(a>0 && a<6){
                    c.modificarJugadora(indice_equipo,indice_jugadora,
                        Integer.parseInt(option), atributo);
                }
            }
            //se comprueba que el usuario ha introducido una opción válida y se le pasa al controlador
            //la elección y el nuevo valor del atributo
            
        }while(!salir);
        
    }

    private void eliminarJugadora() {
        String nombre_equipo;
        String jugadora;
        ArrayList<String> nombres_equipos = c.getNombresEquipos(); //se importan los nombres de los equipos
        Equipo equipo;
        do{
            nombre_equipo = stripAccents(readString("De qué equipo es la jugadora?%n(Pulse q para salir):").toUpperCase());
        }while(!nombres_equipos.contains(nombre_equipo) || nombre_equipo.equalsIgnoreCase("q"));
        //se comprueba que el equipo existe
        
        if(nombre_equipo.equalsIgnoreCase("q")){
            return;
        }
        //se vuelve al menú anterior si el usuario lo desea
        
        int indice_equipo = nombres_equipos.indexOf(nombre_equipo);
        equipo = c.getEquipo(indice_equipo);
        //se obtiene el índice del equipo
        
        do{
            jugadora = stripAccents(readString("Introduzca el nombre completo de la jugadora: ").toUpperCase());
        } while(!equipo.getNombresJugadoras().contains(jugadora));
        //se comprueba que la jugadora exista
        
        int indice_jugadora = equipo.getNombresJugadoras().indexOf(jugadora);
        //se obtiene el índice de la jugadora
        
        c.eliminarJugadora(indice_equipo, indice_jugadora);
        //se pasa al controlador el índice del equipo y de la jugadora para que 
        //sepa qué eliminar
    }

    private void añadirJugadora() {
        
        String nombre_equipo;
        ArrayList<String> nombres_equipos = c.getNombresEquipos();
        Equipo equipo;
        do{
            nombre_equipo = stripAccents(readString("¿A qué equipo desea añadir la jugadora?%n(Pulse q para salir): ").toUpperCase());
        } while(!(nombres_equipos.contains(nombre_equipo) || nombre_equipo.equalsIgnoreCase("q")));
        //se comprueba que el equipo exista
        
        if(nombre_equipo.equalsIgnoreCase("q")){
            return;
        }
        int indice_equipo = nombres_equipos.indexOf(nombre_equipo);
        //se obtiene su índice 
        
        String nombre = readString("Introduzca el nombre de la jugadora %n(Pulse q para salir): ").toUpperCase();
        if(nombre.equalsIgnoreCase("q")){
            return;
        }
        
        String posicion = readString("Introduzca la posicion de la jugadora: ");
        String dorsal = Integer.toString(readInt("Introduzca el dorsal de la jugadora: "));
        String nacimiento = readString("Introduzca la fecha de nacimiento de la jugadora: ");
        String altura = Integer.toString(readInt("Introduzca la altura de la jugadora: "));
        String nacionalidad = readString("Introduzca la nacionalidad de la jugadora: ");
        String[] datos = {nombre, posicion, dorsal, nacimiento, nacionalidad, altura};
        //se leen los datos
         
        c.añadirJugadora(indice_equipo, datos);
        //se mandan los datos al controlador y el índice del equipo para saber dónde añadir a la jugadora
        System.out.println(c.getDatosIndividualesJugadora(indice_equipo));
        //se muestran los datos por pantalla de la jugadora añadida
        //como se añade siempre en la primera posición, no hace falta su índice
    }
    
    //MÉTODOS DE VISUALIZACIÓN

    private void mostrarJugadorasEquipo() throws Exception {
        ArrayList<String> nombres_equipos = c.getNombresEquipos();
        String nombre_equipo;
        do{
            nombre_equipo = stripAccents(readString("Introduzca el nombre del equipo%n(Pulse q para salir): ").toUpperCase());
        }while(!(nombres_equipos.contains(nombre_equipo)|| nombre_equipo.equalsIgnoreCase("q")));
        
        if(nombre_equipo.equalsIgnoreCase("q")){
            return;
        }
        //hasta aquí se ha comprobado que el equipo existe y si el usuario desea volver atrás
        
        int indice_equipo = nombres_equipos.indexOf(nombre_equipo);
        c.ordenarJugadorasEquipo(indice_equipo);
        //se manda la orden de ordenar las jugadoras
        String[][] temp = c.getJugadorasEquipo(indice_equipo);
        //se almacenan las jugadoras
        System.out.println("");
        printToScreen3(temp);
        //se muestran en pantalla bien bonitas con mi cotimétodo favorito olééé
        System.out.println("");
    }

    private void mostrarEquipos() throws Exception {
        
        c.ordenarEquipos(); 
        //se manda la orden de ordenar los equipos
        String[][] temp = c.getEquiposOrdenados();
        //se almacenan los equipos ordenados con sus datos, que vienen en un String[][] y no en una lista
        System.out.println("");
        printToScreen3(temp);
        //se muestran en pantalla bien bonitos
        System.out.println("");
        
    }

    private void mostrarBusquedaJugadora() throws Exception {
        String letra;
        do{
            letra = readString("Introduzca la letra por la que desea filtrar"
                + "%n(Pulse q para salir del programa): ");
        }while(!(Character.isLetter(letra.charAt(0)) || letra.equalsIgnoreCase("q")));
        //se lee la letra y se comprueba si efectivamente es una letra
        //con el método isLetter() de la clase wrapper de char, Character
        if(letra.equalsIgnoreCase("q")){
            return;
        }
        //ya clásica opción de salida para volver al menú anterior
        
        Character letra2 = (Character) (stripAccents(letra)).charAt(0);
        //se hace una refundición de la letra para que sea un character y se le quitan los acentos
        String[][] jugadoras = c.busquedaJugadora(letra2);
        //se almacenan las jugadoras que empiecen por el caracter deseado
        System.out.println("");
        printToScreen3(jugadoras); 
        //se imprimen en pantalla
        System.out.println("");
    }

    //MÉTODOS DE ALMACENAMIENTO

    private void guardarJugadoras() {
        
        String equipo;
        ArrayList<String> nombres_equipos = c.getNombresEquipos();
        do{
            equipo = stripAccents(readString("Introduzca el equipo del que quiere guardar las jugadoras "
                    + "%n(Pulse q para salir): ").toUpperCase());
        }while(!(nombres_equipos.contains(equipo) || equipo.equalsIgnoreCase("q")));
        
        if(equipo.equalsIgnoreCase("q")){
            return;
        }
        //se comprueba que el equipo existe y el usuario tiene la opción de salir
        
        int indice_equipo = nombres_equipos.indexOf(equipo);
        //se almacena la posición del equipo en la lista de equipos
        c.guardarJugadoras(equipo, indice_equipo);
        //se manda el nombre del equipo y su posición para saber qué hay que guardar 
        //y con qué nombre
        
        System.out.printf("%nJugadoras guardadas con éxito%n");
        
    }

    private void guardarEquipos() {
        c.guardarEquipos();
        System.out.printf("%nEquipos guardados con éxito%n");
        //como no hay nada que filtrar, simplemente se manda la instrucción
        //de que se guarden los equipos
    }

    private void guardarClasificacionHTML() {
        System.out.println("Lo siento profe, no lo hice :(");
    }
    
    //MÉTODOS DE GESTIÓN DE JORNADAS

    private void leerResultadosJornada() {
        int num_jornada;
        do{
            num_jornada = readInt("¿De qué jornada desea leer los datos?%n(Pulse 0 para salir): ");
            }while(!((num_jornada>0 && num_jornada <16) || num_jornada == 0));
        if(num_jornada == 0){
            return;
        }
        //se lee la jornada y se comprueba si el usuario quiere salir del programa
        c.leerResultadosJornada(num_jornada-1);
        //se llama al índice -1 porque se debe tener en cuenta que la jornada 1 
        //se almacenará en la posición 0
        System.out.println("Resultados leídos");
    }
    
    private void modificarFechaJornada() {
        int num_jornada;
        do{
            num_jornada = readInt("¿De qué jornada desea modificar la fecha?%n(Pulse 0 para salir): ");
            }while(!((num_jornada>0 && num_jornada <15) || num_jornada == 0));
        if(num_jornada == 0){
            return;
        }
        //se lee la jornada y se comprueba si el usuario quiere salir del programa
        String nueva_fecha;
        do{
            nueva_fecha = readString("Introduzca la nueva fecha: ");
        }while(nueva_fecha.length() > 20);
        
        c.modificarFechaJornada(num_jornada-1,nueva_fecha);
        //se llama al índice -1 porque se debe tener en cuenta que la jornada 1 
        //se almacenará en la posición 0
        System.out.println("Hecho :) ");
    }

    private void modificarFechaPartido() {
        int num_jornada;
        do{
            num_jornada = readInt("¿De qué jornada es el partido del que desea "
                    + "modificar la fecha u hora?%n(Pulse 0 para salir): ");
            }while(!((num_jornada>0 && num_jornada <15) || num_jornada == 0));
        if(num_jornada == 0){
            return;
        }
        //se lee la jornada y se comprueba si el usuario quiere salir del programa
        ArrayList<String> nombres_equipos = c.getNombresEquipos();
        String nombre_equipo;
        do{
            nombre_equipo = stripAccents(readString("Introduzca el nombre de alguno de los dos equipos"
                    + "%n(Pulse q para salir): ").toUpperCase());
        }while(!nombres_equipos.contains(nombre_equipo));
        if(nombre_equipo.equalsIgnoreCase("q")){
            return;
        }
        //se lee el equipo que ha jugado el partido y se comprueba si el usuario quiere salir del programa
        String eleccion;
        do{
            eleccion = readString("%n¿Qué desea cambiar?%n1.- Fecha%n2.- Hora%nElección: ");
        }while(!(eleccion.equals("1")||eleccion.equals("2")));
        //se comprueba qué quiere cambiar el usuario
        String nuevo_valor = readString("Introduzca el nuevo valor%n(Pulse q para salir): ");
        if(nuevo_valor.equalsIgnoreCase("q")){
            return;
        }
        if(eleccion.equals("1")){
            c.modificarFechaPartido(num_jornada-1, nombre_equipo, nuevo_valor);
        }
        if(eleccion.equals("2")){
            c.modificarHoraPartido(num_jornada-1, nombre_equipo, nuevo_valor);
        }
        //se llama al índice -1 porque se debe tener en cuenta que la jornada 1 
        //se almacenará en la posición 0
    }

    private void mostrarResultadosJornada() {
        int num_jornada;
        do{
            num_jornada = readInt("¿De qué jornada desea mostrar los resultados?%n(Pulse 0 para salir): ");
            }while(!((num_jornada>0 && num_jornada <15) || num_jornada == 0));
        if(num_jornada == 0){
            return;
        }
        //se lee la jornada y se comprueba si el usuario quiere salir del programa
        
        String resultados = c.getResultadosJornada(num_jornada-1);
        //se llama al índice -1 porque se debe tener en cuenta que la jornada 1 
        //se almacenará en la posición 0
        System.out.println("");
        out.printf(resultados);
    }

    private void mostrarClasificacionJornada() {
        int num_jornada;
        do{
            num_jornada = readInt("¿De qué jornada desea mostrar la clasificación?%n(Pulse 0 para salir): ");
            }while(!((num_jornada>0 && num_jornada <16) || num_jornada == 0));
        if(num_jornada == 0){
            return;
        }
        
        String[][] temp;
        //se llama al índice -1 porque se debe tener en cuenta que la jornada 1 
        //se almacenará en la posición 0
        try{
            temp = c.getClasificacion(num_jornada-1);
            System.out.println("");
            printToScreen3(temp);
            System.out.println("");
            //ahora pedimos que devuelva la clasificación y la imprimimos bonita
        }catch(Exception ex){
            System.out.println("");
            System.out.println("No se pudo imprimir la clasificación");
        }
    }
    
} //fin de la clase View
