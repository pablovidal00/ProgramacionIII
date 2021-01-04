/*
 * 39494048N
 * Pablo Vidal Fernández
 * pablovidalfdez@usal.es
 * Programación III, Grado en Estadística
 */
package data;

import com.coti.tools.OpMat;
import static com.coti.tools.OpMat.join;
import com.coti.tools.Rutas;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import static java.lang.System.err;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import static stripAccents.StripAccents.stripAccents;
/**
 *
 * @author pablo
 */
public class LigFemBal {
    
    private String temporada = new String();
    private ArrayList<String> nombres_equipos = new ArrayList<>();
    private ArrayList<Equipo> equipos = new ArrayList<>();
    private ArrayList<Jornada> jornadas = new ArrayList<>();
    private Path BINARIOS = Rutas.pathToFolderOnDesktop("LigFemBal\\binarios"); //Se supone constante
    private Path FICH_SALIDA = Rutas.pathToFolderOnDesktop("LigFemBal\\fichsalida"); //Se supone constante
    
    //MÉTODOS SETTER
    
    public void setNombres_equipos(){
        if(!nombres_equipos.isEmpty()){
            nombres_equipos.clear();
        }
        for(Equipo equipo:equipos){
            nombres_equipos.add(stripAccents(equipo.getNombre().toUpperCase()));
        }
        
        //si el arraylist no está vacío, lo vaciamos
        //y luego se añade el nombre de cada equipo
        //así, la posición del nombre en la lista de nombres
        //es la misma en que la del equipo en la lista de equipos
    }

    public void setEquipos(ArrayList<Equipo> equipos) {
        this.equipos = equipos;
    }
    
    public void setJornadas(ArrayList<Jornada> jornadas){
        this.jornadas = jornadas;
    }
    
    public void setTemporada(String temporada){ 
        this.temporada = temporada;
    }
    
    //MÉTODOS GETTER

    public int getNumEquipos() {
        return equipos.size();
    }

    public int getNumJugadorasPE() {
        if(equipos.isEmpty()){
            return 0; 
        } else{
        return equipos.get(0).getJugadoras().size();
        }
        //si no hay equipos, las jugadoras no están cargadas y por eso 
        //primero compruebo si hay equipos cargados
    }

    public int getNumJornadas() {
        return jornadas.size();
    }
    
    public ArrayList<Equipo> getEquipos(){
        return this.equipos;
    }
    
    public ArrayList<String> getNombresEquipos() {
        return this.nombres_equipos;
    }

    public Equipo getEquipo(int index) {
        return equipos.get(index); //devuelve un equipo concreto
    }
    
    public String getTemporada() {
        return this.temporada;
    }
    
    //MÉTODOS PARA CARGAR DE DISCO
    
    public void cargarJugadoras(){
        if(equipos.isEmpty()){
            System.out.println("Amigo, usted debe cargar antes los equipos!");
            return;
        //se comprueba si los equipos están cargados, ya que hay que cargar 
        //los equipos antes que las jugadoras
        }else{
            if(!equipos.get(0).getJugadoras().isEmpty()){
                System.out.println("Amigo, usted ya ha cargado las jugadoras!");
                return;
            }
        }
        //si las jugadoras ya están cargadas, sencillamente no hace nada
        //como las jugadoras de todos los equipos se cargan a la vez, con comprobar 
        //en un equipo hay, llega para saber si están o no 
        
        String equipo;
        String equipo_sin_tildes;
        for(int i = 0; i<nombres_equipos.size();i++){
            equipo = nombres_equipos.get(i).toUpperCase();
            equipo_sin_tildes = stripAccents(equipo);
            //obtenemos el nombre del equipos sin tildes y en mayúsculas
            
            Path p = Rutas.pathToFileInFolderOnDesktop("LigFemBal\\jugadoras", equipo_sin_tildes + ".txt");
            //encontramos su ruta con la cotitool ya vista durante el curso
            String[][] data;
            try{
                data = OpMat.importFromDisk(p.toFile(), "\n");
            }catch(Exception ex){
                System.out.println(ex.toString());
                return;
            }
            Jugadora jugadora;
            if(data!=null){
                for(String[] lineas:data){
                    for(String linea:lineas){
                        jugadora = Jugadora.factory(linea);
                        if(jugadora!=null){
                            equipos.get(i).addJugadora(jugadora);
                            equipos.get(i).addNombreJugadora(jugadora.getNombre());
                        }
                    }
                }
            }
            
            //esto es un proceso de carga de datos ya habitual 
            //leemos los datos del txt y cada jugadora que se lee, se asigna a su equipo
            //también se asigna su nombre a una lista de nombres, para facilitar búsquedas posteriores
        }       
    }
    
    public void cargarEquipos(){
        if(jornadas.isEmpty()){
            System.out.println("Amigo, usted debe cargar antes las jornadas!");
            return;
        }
        if(!equipos.isEmpty()){
            System.out.println("Amigo, usted ya ha cargado los equipos!");
            return;
        }
        //se comprueba que las jornadas han sido cargadas y que los equipos no 
        //en caso contrario, sale en pantalla el aviso de lo que ha pasado
        
        Path p = Rutas.pathToFileInFolderOnDesktop("LigFemBal", "datosequipos.txt");
        String[][] data;
        try{
            data = OpMat.importFromDisk(p.toFile(), "\n");
        } catch(Exception ex){
            System.out.println(ex.toString());
            return;
        }
        if(data!=null){
            Equipo equipo;
            for(String[] lineas: data){
                for(String linea:lineas){
                    equipo = Equipo.factory(linea);
                    if(equipo!=null){
                        equipos.add(equipo);
                    }
                }
            }
            setNombres_equipos();
            
        }
        //se cargan los datos del txt y se instancia cada equipo
        //se añaden a la lista de equipos y cuando todos están cargados
        //hacemos una lista con sus nombres para facilitar búsquedas posteriores
    }
    
    public void cargarJornadas(){
       if(temporada.isEmpty()){
           System.out.println("Amigo, usted debe antes iniciar la temporada!\n");
           return;
       }
       if(!jornadas.isEmpty()){
           System.out.println("Estimado usuario, usted ya ha cargado las jornadas\n");
           return;
       }
       //primero se comprueba si hay temporada iniciada y luego si ya hay jornadas
       Path p = Rutas.pathToFileInFolderOnDesktop("LigFemBal", "datosjornadas.txt");
       String[][] data;
       try{
           data = OpMat.importFromDisk(p.toFile(), "\n");
       }catch(Exception ex){
           err.printf("%nHay problemas para importar los datos%n");
           return;
       }
       
       Jornada jornada;
       for(String[] lineas:data){
           for(String linea:lineas){
               jornada = Jornada.factory(linea);
               if(jornada!=null){
                   jornadas.add(jornada);
               }
           }
       }
       //mismo proceso que en el resto de cargas. Se carga el txt y se llama
       //a los métodos factory. Si no es nulo, se añade a la lista de jornadas
        
    }
        
    //MÉTODOS PARA CARGAR Y GUARDAR INFORMACIÓN AL INICIAR EL PROGRAMA

    public void cargar()  {
        
        File archivo_temporada = new File(BINARIOS.toFile(),"temporada.bin");
        File archivo_jornadas = new File(BINARIOS.toFile(),"jornadas.bin");
        File archivo_equipos = new File(BINARIOS.toFile(),"equipos.bin");
        //se crean los file de los archivos binarios
        if(archivo_temporada.exists()){ //se comprueba si existe 
            try{
                ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(archivo_temporada)));
                String temp = (String)ois.readObject(); //refundición necesaria porque devuelve un Object
                ois.close();
                this.setTemporada(temp);
                //si existe, se lee el archivo, se refunde en String y lo ponemos como temporada
            }catch(Exception ex){
                err.printf("%n No se ha leído la temporada del archivo binario :/");
                System.out.println(ex.toString());
            } //ponemos el catch por si acaso pero yo confío en que no sea necesario llegar a estos extremos
        }
        if(archivo_jornadas.exists()){
            //se comprueba si el archivo de jornadas existe
            try{
                ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(archivo_jornadas)));
                ArrayList<Jornada> jorn = (ArrayList<Jornada>)ois.readObject();
                ois.close();
                this.setJornadas(jorn);
                //mismo procedimiento: se lee, se refunde y lo ponemos como jornadas
            }catch(Exception ex){
                err.printf("%n No se han leído las jornadas del archivo binario :/");
                System.out.println(ex.toString());
            }
        }
        if(archivo_equipos.exists()){
        //de nuevo, comprobamos si existe el archivo de equipos
            try{
                ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(archivo_equipos)));
                ArrayList<Equipo> equipo_temp = (ArrayList<Equipo>)ois.readObject();
                ois.close();
                this.setEquipos(equipo_temp);
                this.setNombres_equipos();
                //aquí, además de establecer los equipos, también se establecen sus nombres
            }catch(Exception ex){
                err.printf("%n No ha ido bien esto :/");
                System.out.println(ex.toString());
            }
        }
    }//fin del método cargar

    public void guardar() {
        //Para guardar la temporada
        //Primero se comprueba que no esté vacío
        if(!temporada.isEmpty()){
            File guardar_temporada = new File(BINARIOS.toFile(),"temporada.bin");
            try{
                ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(guardar_temporada)));
                oos.writeObject(temporada);
                oos.close(); //DO THIS OR SUFFER
            }catch(Exception ex){
                err.printf("Oops");
                System.out.println(ex.toString());
            }
        }
        //Para guardar las jornadas
        //Comprobamos que haya algo que guardar
        if(!jornadas.isEmpty()){
            File guardar_jornadas = new File(BINARIOS.toFile(),"jornadas.bin");
            try{
                ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(guardar_jornadas)));
                oos.writeObject(jornadas);
                oos.close(); //Tu dies oder leide
            }catch(Exception ex){
                err.printf("Oops");
                System.out.println(ex.toString());
            }
        }
        //Para guardar los equipos
        //Hay que chequear que efectivamente tenemos equipos 
        if(!equipos.isEmpty()){
            File guardar_equipos = new File(BINARIOS.toFile(),"equipos.bin");
            try{
                ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(guardar_equipos)));
                oos.writeObject(equipos);
                oos.close(); //HAZ ESTO O SUFRE
            }catch(Exception ex){
                err.printf("Oops");
                System.out.println(ex.toString());
            }
        }
    }//fin del método guardar
    
    //Gestionar jugadoras

    public void eliminarJugadora(int indexEquipo, int indexJugadora) {
        equipos.get(indexEquipo).getJugadoras().remove(indexJugadora);
        equipos.get(indexEquipo).getNombresJugadoras().remove(indexJugadora);
        //se da el índice del equipo y el índice de la jugadora y se elimina
        //tanto de la lista de jugadoras como de nombres de jugadoras
    }//fin del método eliminarJugadora

    public void añadirJugadora(int indexEquipo, String[] datos) {
        Jugadora jugadora = Jugadora.factory(datos); 
        //se convierten los datos en una instancia de jugadora
        if(jugadora!=null){
            equipos.get(indexEquipo).addJugadora(jugadora);
            equipos.get(indexEquipo).addNombreJugadora(jugadora.getNombre());
        }
        //si es una jugadora válida, se añade
    }//fin del método añadirJugadora

    public void modificarJugadora(int indexEquipo, int indexJugadora, int eleccion,
            String atributo) {
        //se ve qué hay que cambiar con int elección, el equipo con indexEquipo
        //qué jugadora con indexJugadora y por último el nuevo valor del atributo con el String
        //previamente se ha comprobado que el String sea parseable a entero si lo que hay que cambiar es numérico
        switch(eleccion){
            case 1 -> equipos.get(indexEquipo).getJugadoras().get(indexJugadora).setPosicion(atributo);
            case 2 -> equipos.get(indexEquipo).getJugadoras().get(indexJugadora).setDorsal(atributo);
            case 3 -> equipos.get(indexEquipo).getJugadoras().get(indexJugadora).setDatos_nacimiento(atributo);
            case 4 -> equipos.get(indexEquipo).getJugadoras().get(indexJugadora).setNacionalidad(atributo);
            case 5 -> equipos.get(indexEquipo).getJugadoras().get(indexJugadora).setAltura(atributo);
        }
    } //fin del método modificarJugadora  
    
    //Visualizar datos
    
    public void ordenarJugadorasEquipo(int indice_equipo) {
        
        Equipo equipo = equipos.get(indice_equipo);
        Comparator<Jugadora> comp = Comparator.comparing(Jugadora::getPosicion).reversed().thenComparing(Jugadora::getAltura).reversed();
        equipo.getJugadoras().sort(comp);
        equipos.get(indice_equipo).setNombresJugadoras();
        //se localiza el equipo con el índice, se ordenan con el atributo posición y luego altura
        //y para que la posición de las jugadoras en la lista de jugadoras sea la misma que 
        //la de su nombre en la lista de nombres se llama al método setNombresJugadora 
    }

    public String[][] getJugadorasEquipo(int indice_equipo) {
        int num_jugadoras = equipos.get(indice_equipo).getJugadoras().size();
        String[][] tabla = new String[num_jugadoras][6];
        //como vamos a imprimir esto en pantalla con el printToScreen, vamos a pasar
        //los datos del ArrayList a un String[][]
        for(int i = 0; i<num_jugadoras;i++){ //recorremos el ArrayList
            tabla[i] = equipos.get(indice_equipo).getJugadoras().get(i).getDatosTabulares();
            //getDatosTabulares devuelve un String[] con los datos de cada jugadora
        }
        //System.out.println(equipos.get(indice_equipo).getNombresJugadoras().get(0));
        //Esto de aquí es una comprobación. Ya la quitaré
        return tabla;
        //se devuelve la tabla
        
    }

    public String getDatosIndividualesJugadora(int indice_equipo) {
        return equipos.get(indice_equipo).getJugadoras().get(0).getDatosIndividuales();
        //este método solo se utiliza para mostrar los datos de la jugadora cuando
        //se añade una jugadora a un equipo. Como la añade en la primera posición (índice 0)
        //para localizarla solo hace falta la posición del equipo
    }

    public void ordenarEquipos() {
        Comparator<Equipo> comp = Comparator.comparing(Equipo::getTelefono);
        this.equipos.sort(comp);
        this.setNombres_equipos();
        //se ordenan los equipos por su número de teléfono
        //y también se vuelven a establecer los nombres de los equipos para
        //que la posición del nombre en la lista de nombres sea la misma que la 
        //del equipo en la lista de equipos.
    }

    public String[][] getEquiposOrdenados() {
        int num_equipos = equipos.size();
        String [][] tabla = new String[num_equipos][5];
        //como se va a mostrar en pantalla, necesitamos un String[][]
        for(int i = 0; i<num_equipos; i++){
            tabla[i] = equipos.get(i).getDatos(); //getDatos devuelve un String[]
        }
        return tabla;
        //se devuelve la tabla
    }

    public String[][] busquedaJugadora(Character letra) {
        ArrayList<Jugadora> jugadoras_filtradas = new ArrayList<>();
        //primero creamos una lista donde almacenaremos las jugadoras que empiezan por esa letra
        for(int i =0; i<equipos.size(); i++){
            for(String nombre:equipos.get(i).getNombresJugadoras()){
                Character primera_letra = (Character)(stripAccents(nombre)).charAt(0);
                if(primera_letra.toString().equalsIgnoreCase(letra.toString())){
                    int indice = equipos.get(i).getNombresJugadoras().indexOf(nombre);
                    jugadoras_filtradas.add(equipos.get(i).getJugadoras().get(indice));
                }
            }
        }
        //se recorren todas las jugadoras buscando en todos los equipos
        //en cada nombre, se coge la primera letra sin acentos con charAt(0) y se compara con la letra
        //dada por el usuario. Si coincide, la jugadora se añade a la lista.
        
        if(!jugadoras_filtradas.isEmpty()){ //se comprueba si hay alguna jugadora
            Comparator<Jugadora> comp = Comparator.comparing(Jugadora::getFecha); 
            jugadoras_filtradas.sort(comp);
            //se ordenan por fecha
            String[][] tabla = new String[jugadoras_filtradas.size()][6];
            for(int i = 0; i<jugadoras_filtradas.size(); i++){
                tabla[i] = jugadoras_filtradas.get(i).getDatosTabulares();
            }

            return tabla;
        }else{
            String[][] frase = {{"No hay jugadoras que empiecen por esa letra"}};
            return frase;
        }
    }//fin del método busquedaJugadora
    
    //ALMACENAR RESULTADOS

    public void guardarJugadoras(String equipo, int indice_equipo) {
        
        File f = new File(FICH_SALIDA.toFile(), equipo + ".enc");
        //se crea el file del archivo enc
        ArrayList<Jugadora> jugadoras = equipos.get(indice_equipo).getJugadoras();
        //se cogen las jugadoras que se van a escribir
        try{
            PrintWriter pw = new PrintWriter(f);
            pw.printf("%-35s | %-10s | %-10s | %-55s | %-7s | %-10s %n%n", "Nombre",
                    "Posición", "Dorsal", "Datos de nacimiento", "Altura", "Nacionalidad");
            //este es el encabezado del documento
            for(int i = 0; i<jugadoras.size(); i++){
                pw.printf("%-35s | %-10s | %-10s | %-45s | %-7s | %-10s%n",jugadoras.get(i).getDatosTabulares());
            }
            pw.close();
            //con un bucle for, rellenamos el documento con ancho de columna fijo gracias
            //al printf. getDatosTabulares devuelve un String[]
        
        }catch(Exception ex){
            System.out.println("Problemitas con el enc de las jugadoras:( ");
        }//Hay que poner el catch pero de nuevo esperemos que no sea necesario llegar hasta aquí
    }//fin del método guardarJugadoras

    public void guardarEquipos() {
        
        File f = new File(FICH_SALIDA.toFile(), "equipos.enc");
        //se crea el file del archivo enc
        try{
            PrintWriter pw = new PrintWriter(f);
            pw.printf("%-40s | %-15s | %-45s | %-40s%n%n","NOMBRE","TELÉFONO","WEB","EMAIL");
            //este era el encabezado del documento
            for(int i = 0; i < equipos.size(); i++){
                pw.printf(equipos.get(i).getDatosMenosDireccion());
            }
            pw.close();
            //como en el método anterior, con el bucle se rellena el archivo
            //ancho de columnas fijo dado por el método getDatosMenosDireccion
            //este método devuelve en String.format todos los datos menos la direccion
            //la verdad es que esta última observación no era muy necesaria
        }catch(Exception ex){
            System.out.println("Problemas con el enc de los equipos");
        }
        
    }//fin del método guardarEquipos
    
    public void guardarClasificacionHTML(int num_jornada) {
        if(jornadas.get(num_jornada).getClasificacion().isEmpty()){
            System.out.printf("%nTodavía no se han cargado los datos necesarios"
                    + "para tener la clasificación de esa jornada%n");
            return;
        }else{
            File f = new File(FICH_SALIDA.toFile(), "Jornada " + (num_jornada+1) + ".html");
            //se crea el archivo HTML
            String[][] clasificacion = this.getClasificacion(num_jornada);
            try{
                PrintWriter pw = new PrintWriter(f);
                pw.printf("<!DOCTYPE html>%n"
                        + "<HTML>%n"
                        + "<HEAD>%n"
                        + "<meta charset=\"UTF-8\">%n"
                        + "<H1>Clasificación de la jornada %d</H1>%n"
                        + "</HEAD>%n"
                        + "<BODY>", num_jornada+1);
                pw.printf("<TABLE BORDER=1>%n");

                for(String[] datos_equipo:clasificacion){
                    pw.printf("<TR><TD>%s</TD><TD>%s</TD><TD>%s</TD><TD>%s</TD>"
                            + "<TD>%s</TD><TD>%s</TD><TD>%s</TD><TD>%s</TD><TR>%n", datos_equipo);
                }
                pw.printf("</TABLE>%n</BODY>%n</HTML>%n");
                pw.close(); //DO THIS OR SUFFER
                
                //aquí se ha escrito el HTML. He hecho esto basándome en el código del HTML disponible 
                //en la clasificación de referencia. Sale razonablemente parecido
            
                }catch(FileNotFoundException ex){
                    System.out.printf("ERROR: no se ha creado %s%n%n", f.toString());
                    return;
                }
        }
    }
    
    //GESTIÓN DE JORNADA
    
    public void leerResultadosJornada(String jornada, int num_jornada) {
        if(jornadas.get(num_jornada).getClasificacion().isEmpty()){
            //primero se comprueba si hay clasificación de la jornada,
            //ya que si existe, la jornada ya ha sido leída
            Path p = Rutas.pathToFileInFolderOnDesktop("LigFemBal\\resul_jornadas", jornada + ".txt");
            String[][] data;
            try{
                data = OpMat.importFromDisk(p.toFile(), "\n");
                for(String[] lineas:data){
                    for(String linea:lineas){
                        jornadas.get(num_jornada).asignarResultado(linea);
                        //para cada partido, en la jornada se asigna el resultado correspondiente
                    }
                }
                this.setClasificacion(num_jornada);
                //se calcula la clasificación
                
            }catch(Exception ex){
                System.out.println(ex.toString());
                return;
            }
        }    
    }

    public void modificarFechaJornada(int num_jornada, String nueva_fecha) {
        jornadas.get(num_jornada).setFecha(nueva_fecha);
        //se recibe el índice de la jornada y se usa el setter de fecha
    }

    public void modificarFechaPartido(int num_jornada, String nombre_equipo, String nuevo_valor) {
        for(int i = 0; i<jornadas.get(num_jornada).getPartidos().size(); i++){
            if(jornadas.get(num_jornada).getPartidos().get(i).getLocal().equalsIgnoreCase(nombre_equipo) ||
                jornadas.get(num_jornada).getPartidos().get(i).getVisitante().equalsIgnoreCase(nombre_equipo)){
                //primero se ubica con un bucle for el partido al que hace referencia, comparando el nombre de 
                //equipo que se recibe con los equipos de los partidos de la jornada
                jornadas.get(num_jornada).getPartidos().get(i).setFecha(nuevo_valor);
                break;
                //en cuanto lo encuentra, se usa el setter y se para el bucle
            }
        }
    }

    public void modificarHoraPartido(int num_jornada, String nombre_equipo, String nuevo_valor) {
        for(int i = 0; i<jornadas.get(num_jornada).getPartidos().size(); i++){
            if(jornadas.get(num_jornada).getPartidos().get(i).getLocal().equalsIgnoreCase(nombre_equipo) ||
                jornadas.get(num_jornada).getPartidos().get(i).getVisitante().equalsIgnoreCase(nombre_equipo)){
                //primero se ubica con un bucle for el partido al que hace referencia, comparando el nombre de 
                //equipo que se recibe con los equipos de los partidos de la jornada
                jornadas.get(num_jornada).getPartidos().get(i).setHora(nuevo_valor);
                break;
                //en cuanto lo encuentra, se usa el setter y se para el bucle
            }
        }
    }

    public String getResultadosJornada(int num_jornada) {
        String resultado = new String();
        for(Partido partido:jornadas.get(num_jornada).getPartidos()){
            resultado += partido.getResultado();
        }
        //al String se le va añadiendo el resultado de cada partido
        return resultado;
        //se devuelve el String
    }
    
    public void setClasificacion(int num_jornada){
        if(jornadas.get(num_jornada).getClasificacion().isEmpty()){
            //primero se comprueba si ya existe la clasificación
            ArrayList<Datos_equipo> clasificacion = new ArrayList<>();
            Datos_equipo equipo;
            if(num_jornada == 0){
                for(String nombre:this.getNombresEquipos()){
                    equipo = new Datos_equipo(nombre);
                    clasificacion.add(equipo);
                }
                //si es la primera jornada, se crea una colección de Datos_equipo
                //de tamaño igual al número de equipos y con su nombre

            }else{
                    if(jornadas.get(num_jornada-1).getClasificacion().isEmpty()){
                        return;
                        //no se hace nada si no existe la clasificación de la jornada anterior
                        //ya que se necesita como base;
                    }else{
                        ArrayList<Datos_equipo> clasificacion_anterior = jornadas.get(num_jornada-1).getClasificacion();
                        Iterator<Datos_equipo> iterator = clasificacion_anterior.iterator();
                        while(iterator.hasNext()){
                            try{
                                clasificacion.add((Datos_equipo)iterator.next().clone());
                            } catch(CloneNotSupportedException ex){
                                return;
                            }
                        }
                        //aquí lo que se hace es crear una copia "dura" (deep copy)
                        //de la clasificación anterior, ya que si simplemente se copia,
                        //las modificaciones en una lista afectan a la otra también
                        //porque solo copia las referencias
                        //Para poder hacer esto, hay que override clone en Datos_equipo
                            
                    }
                } 
                   
            //Hay que asignar a cada elemento de la clasificacion su correspondiente partido:
            for(Datos_equipo datos_equipo:clasificacion){
                for(Partido partido:jornadas.get(num_jornada).getPartidos()){
                    datos_equipo.actualizar(partido);
                }
            }

            Comparator<Datos_equipo> comp = Comparator.comparing(Datos_equipo::getPuntos_clasificacion).reversed().
                    thenComparing(Datos_equipo::getPartidos_jugados);
            clasificacion.sort(comp);
            jornadas.get(num_jornada).setClasificacion(clasificacion);
        }
    }
    
    public String[][] getClasificacion(int num_jornada){
        if(!jornadas.get(num_jornada).getClasificacion().isEmpty()){
            //lo primero que se hace es comprobar si la clasificación existe
            String[][] clasificacion = new String[17][7];
            clasificacion[0][0] = "Equipo";
            clasificacion[0][1] = "PTS";
            clasificacion[0][2] = "PJ";
            clasificacion[0][3] = "PG";
            clasificacion[0][4] = "PP";
            clasificacion[0][5] = "PF";
            clasificacion[0][6] = "PC";
            //esto sencillamente es el encabezado
            for(int i = 0; i<16; i++){
                clasificacion[i+1] = jornadas.get(num_jornada).getClasificacion().get(i).getDatosTabulares();
            }
            String[][] posiciones = {{"Posición"}, {"1"}, {"2"}, {"3"}, {"4"}, {"5"}, {"6"}, {"7"},
             {"8"}, {"9"}, {"10"}, {"11"}, {"12"}, {"13"}, {"14"}, {"15"}, {"16"}};
            String[][] clasificacion_con_posiciones = join(posiciones, clasificacion);
            return clasificacion_con_posiciones;
            //se añaden las posiciones porque van los equipos van en orden
        }else{
            String[][] frase = {{"Usted no tiene cargadas las jornadas necesarias "}};
            //si no existe la clasificación, es que las jornadas no están correctamente cargadas
            //ya que cuando se leen los resultados, también se lee la clasificación
            return frase;
        }
        
    }

}//fin de la clase LigFemBal
