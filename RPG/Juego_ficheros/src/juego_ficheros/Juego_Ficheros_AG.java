package juego_ficheros;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Albert Grau
 * @version 2.0
 */
public class Juego_Ficheros_AG {

    static Scanner sc = new Scanner(System.in);
    static boolean robar = true;
    static String usuario = "", contrasena = "", mays;//variables del login//
    static boolean valusuario = true;//variable determinante para dar permiso para jugar//
    static int VidaE,ataqueE,velocidadE ;
    static String nombreE; 
    //variables jugador/classes//
    static int vidaJ = 20, dañoJ = 50, ataqueSP = dañoJ * 3, velocidadJ = 10, armaduraP = 0, armadura = 0;//estadisticas jugador//
    static int contadorSp = 0;//contador de cargas para el ataque especial//
    static int combate = 0;//se usa en el while//
    static int eleccionC, eleccionR, eleccionClasse;//elecciones de switches sistema de combate//
    static int dinero = 0, elecionj, elecionobj;//variables usadas para el mercader//
    static boolean compra = true, clase = true;//determina si se acaba la compra o no//
    static double porcentajes;//porcentaje de los cofres//
    static double porcentajeF;//porcentaje final donde solo hay 2 decimales//
    static int cofre = 0;//tirada de cofre//
    static boolean jugador = true, vusuario=true;
    static int atacar;
    static String Nusuario;
    static int defender;
    static LocalDateTime data = LocalDateTime.now();
    static DateTimeFormatter dat1 = DateTimeFormatter.ofPattern("dd_MM_YYYY");
    static String fecha = data.format(dat1);
    static boolean tienda = true;
    static int puerta = 0;//sirve para indicar si el jugador ira por la izquierda o derecha
    static BigDecimal bd;//para hacer que los double solo tengan 2 decimales//
    // final variables //

    public static void main(String[] args) {
        System.out.println("Quieres jugar al juego(1) o quieres ver los ficheros del juego(2)");
        switch (sc.nextInt()) {
            case 1:{
                CreacionCarpeta();
                if (login(0)) {
                    Clases();
                    Progression(1);
                }
                break;
            }
            case 2:{
                ListarFicheros();
                break;    
            }    
            default:
                break;
        }
        

    }

    /**
     * @author Albert
     *
     * Limpieza grandre de la consola
     */

    public static void clearConsole() {//Limpieza grandre de la consola//
        for (int i = 0; i < 50; ++i) {
            System.out.println();
        }
    }

    /**
     * @author Albert
     *
     * Limpieza pequeña de la consola
     */
    public static void clearConsole2() {//limpieza pequeña de la consola//
        for (int i = 0; i < 20; ++i) {
            System.out.println();
        }
    }

    /**
     * @author Albert
     *
     * Limpieza grandre de la consola
     * @param contador sirve para contar las veces que se puede ejectuar el
     * login
     * @return devuelve un boolean en baase a si el login a sido correcto o no
     */
    public static boolean login(int contador) {//Sistema de usuarios//
        File log = new File("Projecte_files/usuarios.txt");
        System.out.println("Deseas iniciar sesion(1) o crear un nuevo usuario(2)");
        switch (sc.nextInt()) {
            case 1:{
                try {
                    System.out.println("Dime el nombre de tu usuario");
                    Nusuario= sc.next().toLowerCase();
                    Scanner leer = new Scanner(log);
                    while (leer.hasNextLine()) {
                        String linea = leer.nextLine();
                        String[] splitLinea = linea.split(",");
                        if (splitLinea[0].toLowerCase().equals(Nusuario)) {
                            while (contador<3) {
                                System.out.println("Dime la contraseña de tu usuario");
                                String contrasena= sc.next().toLowerCase();
                                if (splitLinea[1].toLowerCase().equals(contrasena)) {
                                    contador=3;
                                    return true;
                                }else if (contador==3&&!splitLinea[1].toLowerCase().equals(contrasena)) {
                                    System.out.println("Contraseña incorrecta ");
                                    return false;
                                }else{
                                    System.out.println("Contraseña incorrecta vuelva a introducirla");
                                    contador++;
                                }
                            }
                        }else{
                            System.out.println("Nombre de ususario incorrecto vuelva a iniciar el programa para agregar el ususario");
                            return false;
                        }
                    }    
                    leer.close();   
                } catch (FileNotFoundException ex) {
                    System.out.println("error leer");
                }
                break;
            }
            case 2:{
                try {
                    Scanner leer = new Scanner(log);
                    FileWriter au=new FileWriter(log, true);
                    System.out.println("dime tu nombre de usuario");
                    Nusuario=sc.next().toLowerCase();
                    System.out.println("Dime tu contraseña");
                    String contrasenausu=sc.next().toLowerCase();
                    while (leer.hasNextLine()) {
                        String linea=leer.nextLine();
                        String[] splitlinea= linea.split(",");
                        String usu=splitlinea[0];
                        if (usu.equals(usuario)) {
                            System.out.println("nombre de usuario en uso, vuelva a correr el programa para introducir uno nuevo");
                            vusuario=false;
                        }
                    }
                    if (vusuario==true) {
                        System.out.println("Usuario introducido correctamente");
                        au.write(usuario+","+contrasenausu+"\n");
                    }
                    leer.close();
                    au.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            default:
                break;
        }
        clearConsole2();
        return valusuario;
    }

    /**
     * @author Albert
     *
     * Sistma de clases que determina las estadisticas del jugador
     */
    public static void Clases() {//Sistma de clases que determina las estadisticas del jugador//
        System.out.println("Hola viajero, antes de emprender tu aventura me gustaria saber que clase de jugador te gustaria ser");

        System.out.println("\n1:Marginado, una opcion para la persona que duda de que seria mejor, personaje equilibrado el cual no destaca en nada");//clase de marginado//
        System.out.println("Vida inicial 20, daño inicial 5, armadura 0,velocidad 3, dinero 0");
        System.out.println("\n2:Programador, una classe rapida y con un daño superior a las otras, con dinero inicial ya que esta explotado por una multinacional, pero con una vida mucho menor");//clase de programador//
        System.out.println("Vida inicial 13, daño inicial 7, armadura 0, velocidad 5, dinero 2 ");
        System.out.println("\n3:Lolero, claramente no hay mucho que explicar, esos años sentados al ordenador te han pasado factura pero a la vez te han dado mas resistencia y fuerza");//clase de lolero//
        System.out.println("Vida inicial 25, daño inicial 4, armadura 1, velocidad 1, dinero 0");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("!error");
        }

        System.out.println("\nAhora que has tenido tiempo para pensar dime, que clase es la que quieres?");
        eleccionClasse = sc.nextInt();

        switch (eleccionClasse) {
            case 1: {
                System.out.println("Asi que vas a ser un marginado, te deseo lo mejor viajero, disfruta de la travesia");
                vidaJ = 20;
                dañoJ = 4;
                velocidadJ = 3;
                armaduraP = 0;
                dinero = 0;
                clase = false;
                break;
            }
            case 2: {
                System.out.println("Asi que vas a ser un Programador, te deseo lo mejor viajero, disfruta de la travesia");
                vidaJ = 13;
                dañoJ = 6;
                velocidadJ = 5;
                armaduraP = 0;
                dinero = 2;
                clase = false;
                break;
            }
            case 3: {
                System.out.println("Asi que vas a ser un Lolero, te deseo lo mejor viajero, disfruta de la travesia");
                vidaJ = 25;
                dañoJ = 3;
                velocidadJ = 1;
                armaduraP = 1;
                dinero = 0;
                clase = false;
                break;

            }

        }

    }

    /**
     * @author Albert
     *
     * Metodo que es una lucha contra un perro
     */
    public static void perroN() {//Metodo que es una lucha contra un perro//
        if (velocidadJ > velocidadE) {
            if (vidaJ > 0) {
                switch (eleccionJugador()) {
                    case 1: {
                        ataqueJugador();
                        break;
                    }
                    case 2: {
                        defensaJugador();
                        break;
                    }
                    case 3: {
                        ataqueSpJugador();
                        System.out.println("Le has metido tal collejon al perro, que a salio volando");
                        break;
                    }
                    default: {//por si se escoge un numero que no sirve//
                        System.out.println("tu eres tonto");
                        break;
                    }
                }
            } else {
                jugador = false;
            }
            if (VidaE > 0) {
                switch (eleccionEnemigo(3)) {
                    case 1: {
                        AtaqueEnemigo();
                        System.out.println("El perro te a dejado a " + vidaJ);
                        break;
                    }
                    case 2: {
                        DefensaEnemigo(1);
                        System.out.println("el perro se defiende y recupera 1 de vida, tiene " + VidaE);
                        break;
                    }
                    case 3: {
                        VidaE=VidaE- 2;
                        System.out.println("el perro se a caido, se a echo  2 de daño");
                        break;
                    }
                    default:
                        System.out.println("error");
                        break;
                }
            } else {
                jugador = true;
            }
        } else {
            if (VidaE > 0) {
                switch (eleccionEnemigo(3)) {
                    case 1: {
                        AtaqueEnemigo();
                        System.out.println("El perro te a dejado a " + vidaJ);
                        break;
                    }
                    case 2: {
                        DefensaEnemigo(1);
                        System.out.println("el perro se defiende y recupera 1 de vida, tiene " + VidaE);
                        break;
                    }
                    case 3: {
                        VidaE = VidaE - 2;
                        System.out.println("el perro se a caido, se a echo  2 de daño");
                        break;
                    }
                    default:
                        System.out.println("error");
                        break;
                }
            } else {
                jugador = true;
            }
            if (vidaJ > 0) {
                switch (eleccionJugador()) {
                    case 1: {
                        ataqueJugador();
                        break;
                    }
                    case 2: {
                        defensaJugador();
                        break;
                    }
                    case 3: {
                        System.out.println("Le has metido tal collejon al perro, que a salio volando");
                        ataqueSpJugador();
                        break;
                    }
                    default: {//por si se escoge un numero que no sirve//
                        System.out.println("tu eres tonto");
                        break;
                    }
                }
            } else {
                jugador = false;
            }
        }
        if (vidaJ > 0 && VidaE > 0) {
            perroN();
        }
    }

    /**
     * @author Albert
     *
     * Sistema de ataque reutilizable
     * @param enemi sirve para ver la posicion del enemigo contra el que luchas
     * en ese momento
     */
    public static void ataqueJugador() {//Sistema de ataque reutilizable//
        VidaE = VidaE - dañoJ;
        System.out.println("El " + nombreE + " tiene " + VidaE + "de vida");
        if (VidaE == 0) {
            jugador = false;
        }

    }

    /**
     * @author Albert
     *
     * Sistema de defensa reutilizable
     *
     */
    public static void defensaJugador() {//Sistema de defensa reutilizable//
        armadura += 1;
        contadorSp++;
        System.out.println("+1 DE ARMADURA Y YA TIENES " + contadorSp + " contadores del ataque especial");
    }

    /**
     * @author Albert
     *
     * Sistema de ataque especial reutilizable
     * @param enemi sirve para ver la posicion del enemigo contra el que luchas
     * en ese momento
     */
    public static void ataqueSpJugador() {//Metodo del ataque especial del Jugador//
        if (contadorSp >= 2) {//determina si el ataque especial se puede usar o no//
            contadorSp -= 2;
            VidaE = VidaE- ataqueSP;
        } else {//en caso de que se escoja usar el ataque especial y no se pueda//
            System.out.println("aun no puedes usar la habilidad especial, pierdes el turno");
        }

        if (VidaE == 0) {
            jugador = false;
        }

    }

    /**
     * @author Albert
     *
     * Sistema de ataque del enemigo reutilizable
     * @param enemi sirve para ver la posicion del enemigo contra el que luchas
     * en ese momento
     */
    public static void AtaqueEnemigo() {//Metodo para hacer el ataque del enemigo en caso de que quiera hacerlo//
        atacar = vidaJ + armaduraP + armadura - atacar;
        vidaJ = atacar;
        armadura = 0;
    }

    /**
     * @author Albert
     *
     * Sistema defensa enemiga reutilizable
     * @param enemi sirve para ver la posicion del enemigo contra el que luchas
     * en ese momento
     * @param defensaEn sirve para ver la defensa del enemigo actual
     */
    public static void DefensaEnemigo(int defensaEn) {//metodo para hacer la defensa del enemigo en caso de que decida usarla en combate//
        VidaE =VidaE + defensaEn;
        
    }

    /**
     * @author Albert
     *
     * Sistema de Eleccion del jugador reutilizable
     * @return devuelve la eleccion del jugador
     */
    public static int eleccionJugador() {//Switch para escoger la accion del jugador//
        System.out.println("1:atacar");
        System.out.println("2:defender");
        System.out.println("3:ataque especial");
        eleccionC = sc.nextInt();//aqui se escoge la seleccion del jugador //
        return eleccionC;
    }

    /**
     * @author Albert
     *
     * Escoge que accion hara el enemigo de forma aleatoria
     * @param num sirve en base a la cantidad de oopciones que tenga el enemigo
     * @return sirve para devolver la posicion
     */
    public static int eleccionEnemigo(int num) {//Escoge que accion hara el enemigo de forma aleatoria//
        eleccionR = (int) (Math.random() * num + 1);
        return eleccionR;
    }

    /**
     * @author Albert
     *
     * Generacion de enemigo en base a la sala en la que te encuentras
     * @param sala sirve par aver la sala en la que estas actualmente
     * @return sirve para devolver la posicion donde estara un enemigo
     */
    public static int EnemigoGenerado(int sala) {//Generacion de enemigo en base a la sala en la que te encuentras//
        if (sala == 1) {
            return (int) (Math.random() * 2);
        } else if (sala == 2) {
            return (int) (Math.random() * 2 + 2);
        } else if (sala == 3) {
            return (int) (Math.random() * 2 + 4);
        } else if (sala == 4) {
            return (int) (Math.random() * 2 + 6);
        } else if (sala == 5) {
            return (int) (Math.random() * 2 + 8);
        } else {
            return 10;
        }
    }

    /**
     * @author Albert
     *
     * Metodo para avanzar a traves de las salas
     * @param contador sirve para pasar la sala en la que te encuentras en ese
     * momento
     */
    public static void Progression(int contador) {//Metodo para avanzar a traves de las salas//
        switch (EnemigoGenerado(contador)) {
            case 0: {
                DatosEnemigo(1);
                perroN();
                if (comprovante(1) == true) {
                    clearConsole();
                    Progression(contador + 1);
                } else {
                    Derrota("Perro");
                }
                break;
            }
            case 1: {
                DatosEnemigo(2);
                Mochila();
                if (comprovante(2) == true) {
                    clearConsole();
                    Progression(contador + 1);
                } else {
                    Derrota("Mochila");
                }
                break;
            }
            case 2: {
                DatosEnemigo(3);
                Mimico();
                if (comprovante(0) == true) {
                    GeneradorCofre();
                    clearConsole();
                    Progression(contador + 1);
                } else {
                    Derrota("Mimico");
                }
                break;
            }
            case 3: {
                cofre();
                if (vidaJ > 0) {
                    clearConsole();
                    Progression(contador + 1);
                } else {
                    Derrota("Cofre");
                }
                break;
            }
            case 4: {
                DatosEnemigo(7);
                Sadam();
                if (comprovante(1) == true) {
                    clearConsole();
                    Progression(contador + 1);
                } else {
                    Derrota("Sadam");
                }
                break;
            }
            case 5: {
                DatosEnemigo(9);
                Nil();
                if (comprovante(0) == true) {
                    clearConsole();
                    Progression(contador + 1);
                } else {
                    Derrota("Nil");
                }
                break;
            }
            case 6: {
                DatosEnemigo(5);
                Libertus();
                if (comprovante(2) == true) {
                    clearConsole();
                    Progression(contador + 1);
                } else {
                    Derrota("Libertus");
                }
                break;
            }
            case 7: {
                DatosEnemigo(4);
                Fanta();
                if (comprovante(0) == true) {
                    clearConsole();
                    Progression(contador + 1);
                } else {
                    Derrota("Fanta");
                }
                break;
            }
            case 8: {
                descanso();
                clearConsole();
                Progression(contador + 1);
                break;
            }
            case 9: {
                DatosEnemigo(6);
                Mercader();
                clearConsole();
                Progression(contador + 1);
                break;
            }
            case 10: {
                DatosEnemigo(8);
                Jose();
                if (comprovante( 0) == true) {
                    Victoria();
                } else {
                    Derrota("Jose");
                }
                break;
            }

            default:

                break;
        }
    }

    public static boolean comprovante(int monedas) {//metodo para comprobar si has sido derrotado//
        if (jugador == false) {
            System.out.println("Se a acabado el combate has perdido");
            System.out.println("\nSi quieres jugar otra partida debes reiniciar el proyecto");
            return jugador;
        } else {
            System.out.println("Has conseguido matar al " + nombreE);
            System.out.println("El " + nombreE + " a soltado " + monedas + " monedas");
            dinero = dinero + monedas;
            return jugador;
        }
    }

    public static int EleccionCofre() {//Switch para hacer una eleccion para los cofres//
        System.out.println("Hay un cofre en la sala");
        System.out.println("1:Pegarle una patada");
        System.out.println("2:Abrir el cofre");
        return sc.nextInt();
    }

    public static void cofre() {//metodo cuando encuentras un cofre//
        switch (EleccionCofre()) {
            case 1: {
                vidaJ -= 2;
                System.out.println("El cofre se a abierto, pero se te a saltado la uña, te haces 2 de daño, te quedan " + vidaJ + " de vida");
                GeneradorCofre();
                break;
            }
            case 2: {
                System.out.println("Decides abrir el cofre");
                GeneradorCofre();
                break;
            }
            default: {
                System.out.println("Mondongo");
                break;
            }

        }
    }

    public static void GeneradorCofre() {//Metodo de generacion de un item aleatorio//
        if (porcentajeF <= 0.15) {
            System.out.println("Como cabe esto aqui dentro, obtienes un espadon");
            dañoJ += 2;
            velocidadJ -= 1;
        } else if (porcentajeF <= 0.3) {
            System.out.println(" Has conseguido unas botas !!!");
            velocidadJ += 2;
            armadura += 1;
        } else if (porcentajeF <= 0.45) {
            System.out.println(" Has conseguido daga");
            dañoJ += 1;
            velocidadJ += 1;
        } else if (porcentajeF <= 0.7) {
            System.out.println(" Has encontrado una pocion");
            vidaJ += 5;
        } else if (porcentajeF <= 0.85) {
            System.out.println(" Has encontrado 1 pechera");
            vidaJ += 3;
            armadura += 2;
        } else if (porcentajeF <= 0.9) {
            System.out.println("Coño vaya espadon");
            dañoJ += 4;
        } else {
            System.out.println("Te has encontrado 1 anillo especial");
            dañoJ += 1;
            velocidadJ += 1;
            armadura += 1;
            vidaJ -= 2;
        }
    }

    public static void Mochila() {//Metodo de la pelea contra la mochila//
        if (velocidadJ > velocidadE) {
            if (vidaJ > 0) {
                switch (eleccionJugador()) {
                    case 1: {
                        ataqueJugador();
                        break;
                    }
                    case 2: {
                        defensaJugador();
                        break;
                    }
                    case 3: {
                        ataqueSpJugador();
                        System.out.println("Del ostion la mochila se a puesto recta");
                        break;
                    }
                    default: {//por si se escoge un numero que no sirve//
                        System.out.println("tu eres tonto");
                        break;
                    }
                }
            } else {
                jugador = false;
            }
            if (VidaE > 0) {
                switch (eleccionEnemigo(3)) {
                    case 1: {
                        AtaqueEnemigo();
                        System.out.println("La mochila te ataca, te deja a " + vidaJ);
                        break;
                    }
                    case 2: {
                        DefensaEnemigo(1);
                        System.out.println("La mochila le da un puff a un ventolin, recupera 1 de vida " + VidaE);
                        break;
                    }
                    case 3: {
                        VidaE -= 2;
                        System.out.println("La mochila se a estampado contra un banco, se a echo 2 de daño");
                        break;
                    }
                    default:
                        System.out.println("error");
                        break;
                }
            } else {
                jugador = true;
            }
        } else {
            if (VidaE > 0) {
                switch (eleccionEnemigo(3)) {
                    case 1: {
                        AtaqueEnemigo();
                        System.out.println("La mochila te ataca, te deja a " + vidaJ);
                        break;
                    }
                    case 2: {
                        DefensaEnemigo(1);
                        System.out.println("La mochila le da un puff a un ventolin, recupera 1 de vida " + VidaE);
                        break;
                    }
                    case 3: {
                        VidaE -= 2;
                        System.out.println("La mochila se a estampado contra un banco, se a echo 2 de daño");
                        break;
                    }
                    default:
                        System.out.println("error");
                        break;
                }
            } else {
                jugador = true;
            }
            if (vidaJ > 0) {
                switch (eleccionJugador()) {
                    case 1: {
                        ataqueJugador();
                        break;
                    }
                    case 2: {
                        defensaJugador();
                        break;
                    }
                    case 3: {
                        ataqueSpJugador();
                        System.out.println("Del ostion la mochila se a puesto recta");
                        break;
                    }
                    default: {//por si se escoge un numero que no sirve//
                        System.out.println("tu eres tonto");
                        break;
                    }
                }
            } else {
                jugador = false;
            }
        }
        if (vidaJ > 0 && VidaE > 0) {
            Mochila();
        }
    }

    public static void PeleaMimico() {//metodo de la pelea contra el mimico//
        if (velocidadJ > velocidadE) {
            if (vidaJ > 0) {
                switch (eleccionJugador()) {
                    case 1: {
                        ataqueJugador();
                        break;
                    }
                    case 2: {
                        defensaJugador();
                        break;
                    }
                    case 3: {
                        ataqueSpJugador();
                        System.out.println("Le arrancas la lengua");
                        break;
                    }
                    default: {//por si se escoge un numero que no sirve//
                        System.out.println("tu eres tonto");
                        break;
                    }
                }
            } else {
                jugador = false;
            }
            if (VidaE > 0) {
                switch (eleccionEnemigo(2)) {
                    case 1: {
                        AtaqueEnemigo();
                        System.out.println("Te mete tal lametzo que te deja  a " + vidaJ);
                        break;
                    }
                    case 2: {
                        DefensaEnemigo(1);
                        System.out.println("decide esconderse en su cofre de madera, recupera 1 de vida, esta a  " + VidaE);
                        break;
                    }

                    default:
                        System.out.println("error");
                        break;
                }
            } else {
                jugador = true;
            }
        } else {
            if (VidaE > 0) {
                switch (eleccionEnemigo(2)) {
                    case 1: {
                        AtaqueEnemigo();
                        System.out.println("Te mete tal lametzo que te deja  a " + vidaJ);
                        break;
                    }
                    case 2: {
                        DefensaEnemigo(1);
                        System.out.println("decide esconderse en su cofre de madera, recupera 1 de vida, esta a  " +VidaE);
                        break;
                    }

                    default:
                        System.out.println("error");
                        break;
                }
            } else {
                jugador = true;
            }
            if (vidaJ > 0) {
                switch (eleccionJugador()) {
                    case 1: {
                        ataqueJugador();
                        break;
                    }
                    case 2: {
                        defensaJugador();
                        break;
                    }
                    case 3: {
                        ataqueSpJugador();
                        System.out.println("Le arrancas la lengua");
                        break;
                    }
                    default: {//por si se escoge un numero que no sirve//
                        System.out.println("tu eres tonto");
                        break;
                    }
                }
            } else {
                jugador = false;
            }
        }
        if (vidaJ > 0 && VidaE > 0) {
            PeleaMimico();
        }
    }

    public static void Mimico() {//Metodo donde se hace el mimico//
        switch (EleccionCofre()) {
            case 1: {
                System.out.println("Le pegas una patada, aparece una lengua del tamaño de tu cuerpo, ten cuidado que es un enemigo");
                VidaE = VidaE - 2;
               
                System.out.println("le dejas a " + VidaE);
                PeleaMimico();
                break;
            }
            case 2: {
                System.out.println("Parece que no era un cofre te mete un lameton que te quita vida");
                vidaJ = vidaJ - ataqueE;
                System.out.println("te deja a " + vidaJ);
                PeleaMimico();
                break;

            }

            default: {
                System.out.println("tonto");
                break;
            }

        }

    }

    public static void Sadam() {//Metodo de pelea con Sadam Husein//
        if (velocidadJ > velocidadE) {
            if (vidaJ > 0) {
                switch (eleccionJugador()) {
                    case 1: {
                        ataqueJugador();
                        break;
                    }
                    case 2: {
                        defensaJugador();
                        break;
                    }
                    case 3: {
                        ataqueSpJugador();
                        System.out.println("Parece que has dejado enterrado al enemigo");
                        break;
                    }
                    default: {//por si se escoge un numero que no sirve//
                        System.out.println("tu eres tonto");
                        break;
                    }
                }
            } else {
                jugador = false;
            }
            if (VidaE > 0) {
                switch (eleccionEnemigo(2)) {
                    case 1: {
                        AtaqueEnemigo();
                        System.out.println("Sadam Husein te pega con su filtro de ventilacion, te quedas a " + vidaJ);
                        break;
                    }
                    case 2: {
                        DefensaEnemigo(2);
                        System.out.println("Sadam se hace una barrera de arena gracias a Ala, se queda a  " + VidaE);
                        break;
                    }
                    default:
                        System.out.println("error");
                        break;
                }
            } else {
                jugador = true;
            }
        } else {
            if (VidaE > 0) {
                switch (eleccionEnemigo(2)) {
                    case 1: {
                        AtaqueEnemigo();
                        System.out.println("Sadam Husein te pega con su filtro de ventilacion, te quedas a " + vidaJ);
                        break;
                    }
                    case 2: {
                        DefensaEnemigo(2);
                        System.out.println("Sadam se hace una barrera de arena gracias a Ala, se queda a  " + VidaE);
                        break;
                    }
                    default:
                        System.out.println("error");
                        break;
                }
            } else {
                jugador = true;
            }
            if (vidaJ > 0) {
                switch (eleccionJugador()) {
                    case 1: {
                        ataqueJugador();
                        break;
                    }
                    case 2: {
                        defensaJugador();
                        break;
                    }
                    case 3: {
                        ataqueSpJugador();
                        System.out.println("Parece que has dejado enterrado al enemigo");
                        break;
                    }
                    default: {//por si se escoge un numero que no sirve//
                        System.out.println("tu eres tonto");
                        break;
                    }
                }
            } else {
                jugador = false;
            }
        }
        if (vidaJ > 0 && VidaE > 0) {
            Sadam();
        }
    }

    public static void Nil() {//Metodo de pelea contra Nil Jou//
        if (velocidadJ > VidaE) {
            if (vidaJ > 0) {
                switch (eleccionJugador()) {
                    case 1: {
                        ataqueJugador();
                        break;
                    }
                    case 2: {
                        defensaJugador();
                        break;
                    }
                    case 3: {
                        ataqueSpJugador();
                        System.out.println("Vaya colleja se a llevado el rubito ese");
                        break;
                    }
                    default: {//por si se escoge un numero que no sirve//
                        System.out.println("tu eres tonto");
                        break;
                    }
                }
            } else {
                jugador = false;
            }
            if (VidaE > 0) {
                switch (eleccionEnemigo(3)) {
                    case 1: {
                        AtaqueEnemigo();
                        System.out.println("Te hace tal llave de judo que te deja a  " + vidaJ);
                        break;
                    }
                    case 2: {
                        DefensaEnemigo(2);
                        System.out.println("Abre su expansion de dominio, se cura 2 de vida, esta a " + VidaE);
                        break;
                    }
                    case 3: {
                        VidaE -= 1;
                        System.out.println("Parece que se a echo daño solo, como? no se pero se a echo daño");
                        break;
                    }
                    default:
                        System.out.println("error");
                        break;
                }
            } else {
                jugador = true;
            }
        } else {
            if (VidaE > 0) {
                switch (eleccionEnemigo(3)) {
                    case 1: {
                        AtaqueEnemigo();
                        System.out.println("Te hace tal llave de judo que te deja a  " + vidaJ);
                        break;
                    }
                    case 2: {
                        DefensaEnemigo(2);
                        System.out.println("Abre su expansion de dominio, se cura 2 de vida, esta a " + VidaE);
                        break;
                    }
                    case 3: {
                        VidaE -= 1;
                        System.out.println("Parece que se a echo daño solo, como? no se pero se a echo daño");
                        break;
                    }
                    default:
                        System.out.println("error");
                        break;
                }
            } else {
                jugador = true;
            }
            if (vidaJ > 0) {
                switch (eleccionJugador()) {
                    case 1: {
                        ataqueJugador();
                        break;
                    }
                    case 2: {
                        defensaJugador();
                        break;
                    }
                    case 3: {
                        ataqueSpJugador();
                        System.out.println("Vaya colleja se a llevado el rubito ese");
                        break;
                    }
                    default: {//por si se escoge un numero que no sirve//
                        System.out.println("tu eres tonto");
                        break;
                    }
                }
            } else {
                jugador = false;
            }
        }
        if (vidaJ > 0 && VidaE > 0) {
            Nil();
        }
    }

    public static void Libertus() {//Metodo de la pelea contra el titere de libertus//
        System.out.println("Bueno, bueno, a quien tenemos aqui?");
        System.out.println("TE VOY A PARTIR ESE CARTETO DE TONTO QUE LLEVAS");
        if (velocidadJ > velocidadE) {
            if (vidaJ > 0) {
                switch (eleccionJugador()) {
                    case 1: {
                        ataqueJugador();
                        break;
                    }
                    case 2: {
                        defensaJugador();
                        break;
                    }
                    case 3: {
                        ataqueSpJugador();
                        System.out.println("Del ostion la mochila se a puesto recta");
                        break;
                    }
                    default: {//por si se escoge un numero que no sirve//
                        System.out.println("tu eres tonto");
                        break;
                    }
                }
            } else {
                jugador = false;
            }
            if (VidaE > 0) {
                switch (eleccionEnemigo(3)) {
                    case 1: {
                        AtaqueEnemigo();
                        System.out.println("La marioneta te pega una W misteriosa, te deja a " + vidaJ);
                        break;
                    }
                    case 2: {
                        DefensaEnemigo(1);
                        System.out.println("Libertus se rie de ti, y se cura 2 de vida, ahora tiene: " + VidaE);
                        break;
                    }
                    case 3: {
                        VidaE -= 1;
                        System.out.println("Libertus ha pifiado, se tropieza y se cae encima de su cuchillo, pierde 1 vida");
                        break;
                    }
                    default:
                        System.out.println("error");
                        break;
                }
            } else {
                jugador = true;
            }
        } else {
            if (VidaE > 0) {
                switch (eleccionEnemigo(3)) {
                    case 1: {
                        AtaqueEnemigo();
                        System.out.println("La marioneta te pega una W misteriosa, te deja a " + vidaJ);
                        break;
                    }
                    case 2: {
                        DefensaEnemigo(1);
                        System.out.println("Libertus se rie de ti, y se cura 2 de vida, ahora tiene: " + VidaE);
                        break;
                    }
                    case 3: {
                        VidaE -= 1;
                        System.out.println("Libertus ha pifiado, se tropieza y se cae encima de su cuchillo, pierde 1 vida");
                        break;
                    }
                    default:
                        System.out.println("error");
                        break;
                }
            } else {
                jugador = true;
            }
            if (vidaJ > 0) {
                switch (eleccionJugador()) {
                    case 1: {
                        ataqueJugador();
                        break;
                    }
                    case 2: {
                        defensaJugador();
                        break;
                    }
                    case 3: {
                        ataqueSpJugador();
                        System.out.println("Parece que has dejado enterrado al enemigo");
                        break;
                    }
                    default: {//por si se escoge un numero que no sirve//
                        System.out.println("tu eres tonto");
                        break;
                    }
                }
            } else {
                jugador = false;
            }
        }
        if (vidaJ > 0 && VidaE > 0) {
            Libertus();
        }
    }

    public static void Fanta() {//Metodo de la pelea contra la fanta de sandia del Li//
        System.out.println("Joder ahora es una fanta, que sera lo siguiente Jose Placencia");
        if (velocidadJ > velocidadE) {
            if (vidaJ > 0) {
                switch (eleccionJugador()) {
                    case 1: {
                        ataqueJugador();
                        break;
                    }
                    case 2: {
                        defensaJugador();
                        break;
                    }
                    case 3: {
                        ataqueSpJugador();
                        System.out.println("Le has quitado la etiqueta de un torton");
                        break;
                    }
                    default: {//por si se escoge un numero que no sirve//
                        System.out.println("tu eres tonto");
                        break;
                    }
                }
            } else {
                jugador = false;
            }
            if (VidaE > 0) {
                switch (eleccionEnemigo(3)) {
                    case 1: {
                        AtaqueEnemigo();
                        System.out.println("La Fanta te deja a " + vidaJ);
                        break;
                    }
                    case 2: {
                        DefensaEnemigo(1);
                        System.out.println("La fanta se a tapado el tapon del gas, recupera 2 de vida. Ahora tiene " + VidaE);
                        break;
                    }

                    case 3: {
                        VidaE -= 1;
                        System.out.println("Parece que la fanta esta periendo mas gas del que tiene, le queda " + VidaE + " de vida ");
                        break;
                    }

                    default:
                        System.out.println("error");
                        break;
                }
            } else {
                jugador = true;
            }
        } else {
            if (VidaE > 0) {
                switch (eleccionEnemigo(3)) {
                    case 1: {
                        AtaqueEnemigo();
                        System.out.println("La Fanta te deja a " + vidaJ);
                        break;
                    }
                    case 2: {
                        DefensaEnemigo(1);
                        System.out.println("La fanta se a tapado el tapon del gas, recupera 2 de vida. Ahora tiene " + VidaE);
                        break;
                    }

                    case 3: {
                        VidaE -= 1;
                        System.out.println("Parece que la fanta esta periendo mas gas del que tiene, le queda " + VidaE + " de vida ");
                        break;
                    }

                    default:
                        System.out.println("error");
                        break;
                }
            } else {
                jugador = true;
            }
            if (vidaJ > 0) {
                switch (eleccionJugador()) {
                    case 1: {
                        ataqueJugador();
                        break;
                    }
                    case 2: {
                        defensaJugador();
                        break;
                    }
                    case 3: {
                        ataqueSpJugador();
                        System.out.println("Parece que has dejado enterrado al enemigo");
                        break;
                    }
                    default: {//por si se escoge un numero que no sirve//
                        System.out.println("tu eres tonto");
                        break;
                    }
                }
            } else {
                jugador = false;
            }
        }
        if (vidaJ > 0 && VidaE > 0) {
            Fanta();
        }
    }

    public static void descanso() {//Sala donde puedes recuperar algo de vida si decides descansar//
        System.out.println("te has econtrado 1 hogera");
        System.out.println("1: Te tumbas un rato");
        System.out.println("2:te vas");
        switch (sc.nextInt()) {
            case 1: {
                System.out.println("descansas un rato, recuperas 3 de vida");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    System.out.println("!error");
                }
                vidaJ = vidaJ + 3;
                break;
            }
            case 2: {
                System.out.println("Te vas y no pasa nada. Que te esperabas");
                break;
            }

            default:
                break;
        }
    }

    public static void Mercader() {//Metodo donde aparece un mercader que te vende cosas//
        System.out.println("Te encuentras un mercader, este te ofrece unos consumibles a cambio de dinero");
        while (tienda) {
            System.out.println("Drivers(+1 de ataque) 1€ ");
            System.out.println("Cable Display port(+1 de velocidad) 3€");
            System.out.println("'Lacasitos (+1 de velocidad, +1 de ataque) 5€'");
            System.out.println("Tienes " + dinero + "€");
            switch (sc.nextInt()) {
                case 1: {
                    if (dinero >= 1) {
                        dinero -= 1;
                        dañoJ += 1;
                        System.out.println("gracias por la compra te interesa algo mas?");
                    } else {
                        System.out.println("No tienes dinero rey, vete de aqui");
                        tienda = false;
                    }
                    break;
                }
                case 2: {
                    if (dinero >= 3) {
                        dinero -= 3;
                        velocidadJ += 1;
                        System.out.println("gracias por la compra te interesa algo mas?");
                    } else {
                        System.out.println("No tienes dinero rey, vete de aqui");
                        tienda = false;
                    }
                    break;
                }
                case 3: {
                    if (dinero >= 5) {
                        dinero -= 5;
                        velocidadJ += 1;
                        dañoJ += 1;
                        System.out.println("gracias por la compra te interesa algo mas?");
                    } else {
                        System.out.println("No tienes dinero rey, vete de aqui");
                        tienda = false;
                    }
                    break;
                }
                case 4: {
                    System.out.println("una pena que no quieras nada, mucha suerte");
                    tienda = false;
                    break;
                }
                default: {
                    System.out.println("Tu eres tonto");
                    break;
                }

            }
        }
    }

    public static void Jose() {//Metodo de la pelea contra Jose Placencia(Boss final)//
        System.out.println("Diablo Jose, desde cuando te chutas esteroides");
        System.out.println("Ten cuidado es el boss final");
        if (velocidadJ > velocidadE) {
            if (vidaJ > 0) {
                switch (eleccionJugador()) {
                    case 1: {
                        ataqueJugador();
                        break;
                    }
                    case 2: {
                        defensaJugador();
                        break;
                    }
                    case 3: {
                        ataqueSpJugador();
                        System.out.println("Uff parece que le has actualizado los drivers");
                        break;
                    }
                    default: {//por si se escoge un numero que no sirve//
                        System.out.println("tu eres tonto");
                        break;
                    }
                }
            } else {
                jugador = false;
            }
            if (VidaE > 0) {
                switch (eleccionEnemigo(3)) {
                    case 1: {
                        AtaqueEnemigo();
                        System.out.println("Que solo me enseñas un okay, procede a lanzarte un ordenador a la cabeza. Te deja a  " + vidaJ);
                        break;
                    }
                    case 2: {
                        DefensaEnemigo(3);
                        System.out.println("Bueno no me molesteis que esto ya lo e explicado, se sienta en su silla y gana 3 de vida, esta a  " + VidaE);
                        break;
                    }

                    case 3: {
                        int ataqueSpJose = vidaJ - 4;
                        vidaJ = ataqueSpJose;
                        System.out.println("Hahahah este ataque no te lo esperabas e, te penetra la armadura y te deja a " + vidaJ);
                        break;
                    }

                    default:
                        System.out.println("error");
                        break;
                }
            } else {
                jugador = true;
            }
        } else {
            if (VidaE > 0) {
                switch (eleccionEnemigo(3)) {
                    case 1: {
                        AtaqueEnemigo();
                        System.out.println("Que solo me enseñas un okay, procede a lanzarte un ordenador a la cabeza. Te deja a  " + vidaJ);
                        break;
                    }
                    case 2: {
                        DefensaEnemigo(3);
                        System.out.println("Bueno no me molesteis que esto ya lo e explicado, se sienta en su silla y gana 3 de vida, esta a  " + VidaE);
                        break;
                    }

                    case 3: {
                        int ataqueSpJose = vidaJ - 4;
                        vidaJ = ataqueSpJose;
                        System.out.println("Hahahah este ataque no te lo esperabas e, te penetra la armadura y te deja a " + vidaJ);
                        break;
                    }

                    default:
                        System.out.println("error");
                        break;
                }
            } else {
                jugador = true;
            }
            if (vidaJ > 0) {
                switch (eleccionJugador()) {
                    case 1: {
                        ataqueJugador();
                        break;
                    }
                    case 2: {
                        defensaJugador();
                        break;
                    }
                    case 3: {
                        ataqueSpJugador();
                        System.out.println("Parece que has dejado enterrado al enemigo");
                        break;
                    }
                    default: {//por si se escoge un numero que no sirve//
                        System.out.println("tu eres tonto");
                        break;
                    }
                }
            } else {
                jugador = false;
            }
        }
        if (vidaJ > 0 && VidaE > 0) {
            Jose();
        }

    }

    public static void Victoria() {//Este metododo se encarga de abrir un link a yt en caso de victoria//

        URL url = null;
        try {
            File lista= new File("Ranking/lista.txt");
            FileWriter de= new FileWriter(lista);
            de.write(Nusuario+",Ganador y asesino de Jose Placencia"+"\n");
            url = new URL("https://www.youtube.com/watch?v=gN5hj3vXMX8");
            try {
                Desktop.getDesktop().browse(url.toURI());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            de.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        
    }

    public static void Derrota(String enemigo) {//Este metodo se usa en caso de que la persona pierda un combate, lo que hace es abrir un link a yt//
        
        URL url = null;
        try {
            File lista= new File("Ranking/lista.txt");
            FileWriter de= new FileWriter(lista);
            de.write(Nusuario+",derrotado por "+ enemigo+"\n");
            url = new URL("https://www.youtube.com/watch?v=WyGZyoWl7uo");
            try {
                Desktop.getDesktop().browse(url.toURI());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            de.close();
        } catch (IOException  e1) {
            e1.printStackTrace();
        }
    }

    public static void DatosEnemigo(int numenemi) {
        File fichero = new File("Projecte_files\\Enemigos.txt");
            try {
                Scanner leer = new Scanner(fichero);
                while (leer.hasNextLine()) {
                    String linea = leer.nextLine();
                    String[] splitLinea = linea.split(",");
                    if (splitLinea[0].equals(Integer.toString(numenemi))) {
                        nombreE=splitLinea[1];
                        VidaE =Integer.parseInt(splitLinea[2]) ;
                        ataqueE = Integer.parseInt(splitLinea[3]);
                        velocidadE  = Integer.parseInt(splitLinea[4]);
                    }
                }    
                leer.close();   
            } catch (FileNotFoundException ex) {
                System.out.println("error leer");
            }
       
    }

    public static void CreacionCarpeta() {
        File carpeta= new File("Ranking");
        File lista= new File("Ranking/lista.txt");
        if (!carpeta.exists()&& !lista.exists()) {
            if (!lista.exists()) {
                try {
                    carpeta.mkdir();
                    lista.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }   
            }
        }
    }
    public static void ListarFicheros() {
        try {
         File listaficheros = new File("Projecte_files");
             File[] lista = listaficheros.listFiles();
             for (int i = 0; i < lista.length; i++) {
                 File ficheros = lista[i];
                 if (ficheros.isDirectory()) {
                     System.out.print("[Dir]");
                 } else {
                     System.out.print("[Fit]");
                 }
                 Date data = new Date(ficheros.lastModified());
                 System.out.println(ficheros.getName() + " " + ficheros.length() + " " + data);
             }
        
        } catch (Exception e) {
         System.out.println(e);
        }
     }
}
