package ucr.farroyo.laberinto;

/**
 *  Crea un laberinto, una rata y un queso
 *  El programa pone a la rata a buscar el queso por recursividad
 *  Se despliega a primera solucion encontrada
 *
 * @author Programacion I 2019-2
 * @version 2019/Nov/27
 */

public class Laberinto {
    public static final String direccion[] = {"N","E","S","O"};
    public static final int sumaF[] = {-1, 0,1,0};
    public static final int sumaC[] = { 0,1,0,-1};
    public static final char RATA = 'R';
    public static final char QUESO = 'Q';
    public static final char MURO = 'M';
    public static final char PARED = 'X';
    public static final char VACIO = ' ';
    public static final char CAMINO = '*';
    public static final char MARCA = '.';
    private char laberinto[][];
    private int rataF;
    private int rataC;

/**
 *   Determina si la casilla indicada esta en una posicion valida
 * @param	int	fila de la posicion
 * @param	int	columna de la posicion
 * @return	boolean	verdadero si la casilla es valida
 */
    public boolean esValida( int fila, int columna ) {
        return  fila > 0 &&
                fila < this.laberinto.length &&
                columna > 0 &&
                columna < this.laberinto[ fila ].length;
    }

/**
 *  Metodo disponible al usuario de la clase para que la rata busque el queso
 */
    public void encuentraQueso(){
        encuentraQueso( this.rataF, this.rataC );
    }

/**
 *  Metodo auxiliar, privado, para buscar el queso recursivamente
 *
 * @param	int	fila de la casilla a buscar
 * @param	int	columna de la casilla a buscar
 * @return	boolean	si el queso fue encontrado
 */
    private boolean encuentraQueso( int fila, int columna ){
        boolean encontrado = false;
        int filaSiguiente, columnaSiguiente;

        if ( esValida( fila, columna ) ) {	// Posicion valida en el laberinto
            encontrado = ( QUESO == laberinto[ fila ][ columna ] );
            if ( ! encontrado ) {
                if ( VACIO == this.laberinto[ fila ][ columna ] || RATA == this.laberinto[ fila ][ columna ] ) {
                    if ( VACIO == this.laberinto[ fila ][ columna ] ) {
                        this.laberinto[ fila ][ columna ] = MARCA;
                    }
                    for ( int cambio = 0; (! encontrado) && cambio < this.direccion.length; cambio++ ) {
                        filaSiguiente = fila + sumaF[ cambio ];
                        columnaSiguiente = columna + sumaC[ cambio ];
                        encontrado = encuentraQueso( filaSiguiente, columnaSiguiente );
                        if ( encontrado ) {
                            if ( MARCA == this.laberinto[ fila ][ columna ] ) {
                                this.laberinto[ fila ][ columna ] = CAMINO;
                            }
                        }
                    }
                }
            }
        }

        return encontrado;

    }


/**
 *  Constructor de la clase
 * @param	int	dimension de la matriz cuadrada a crear para representa el laberinto
 */
    public Laberinto( int n ) {
        this.laberinto = new char [ n ][ n ];
        for ( int f=0; f< this.laberinto.length; ++f ) {
            for (int c=0; c< this.laberinto[f].length; ++c ) {
                if (f==0 || f==this.laberinto.length-1  || c==0 || c==this.laberinto[f].length-1) {
                    this.laberinto[f][c]= MURO;
                }
                else {
                    if (Math.random() > 0.6) {
                        if ( f == 1 || f == this.laberinto.length - 2  || c == 1 || c == this.laberinto[ f ].length-2 ){
                            this.laberinto[ f ][ c ]= VACIO;
                        }
                        else {
                            this.laberinto[ f ][ c ]= PARED;
                        }
                    }
                    else {
                        this.laberinto[ f ][ c ]= VACIO;
                    }
                }
            }
        }
        ponerRata();
        ponerQueso();
    }


/**
 *  Coloca la rata de manera semi-aleatoria en el laberinto
 */
    public void ponerRata(){
        this.rataF = (int) (Math.random()* (this.laberinto.length - 2)) + 1;
        this.rataC = 1;
        this.laberinto[ this.rataF ][ this.rataC ] = RATA;
    }


/**
 *  Coloca el queso de manera aleatoria en el laberinto
 */
    public void ponerQueso(){
        int f= (int) (Math.random() * (this.laberinto.length - 2)) + 1;
        int c= (int) (Math.random() * (this.laberinto[f].length - 2)) + 1;

        this.laberinto[ f ][ c ]=QUESO;
    }


/**
 *  Convierte el laberinto a una tira de caracteres para despliegue
 */
    public String toString(){
        String reset   = "</font></td>";
        String red     = "<td><font color=#ff0000>";
        String green   = "<td><font color=#00ff00>";
        String yellow  = "<td><font color=#FFFF00>";
        String blue    = "<td><font color=#0000FF>";
        String black = "<td><font color=#000000>";
        String cyan    = "<td><font color=#00FFFF>";
        String white   = "<td><font color=#ffffff>";
        StringBuffer tira = new StringBuffer( "" );
        tira.append("<html><body><table>");
        for ( int f=0; f< this.laberinto.length; ++f ) {
            tira.append( "<tr>" );
            for (int c=0; c < this.laberinto[ f ].length; ++c ){
                switch ( this.laberinto[ f ][ c ] ) {
                    case RATA:
                        tira.append( red + RATA + "\t" + reset );
                        break;
                    case CAMINO:
                        tira.append( green + CAMINO + reset );
                        break;
                    case QUESO:
                        tira.append( yellow + QUESO + reset );
                        break;
                    case MURO:
                        tira.append( cyan + MURO + reset );
                        break;
                    default:
                        tira.append( magenta + this.laberinto[ f ][ c ] + reset);
                }
            }
            tira.append( "</tr>" );
        }
        tira.append("</table></body></html>");

        return tira.toString();

    }
}


