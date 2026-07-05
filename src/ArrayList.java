import java.util.Arrays;
import java.util.Iterator;

// Clase ArrayList generica.   //Recuerden implementar todos los métodos en ArrayList.
// E representa el tipo de dato que guardara la lista.
// Implementa la interfaz Lista<E>.
public class ArrayList<E> implements Lista<E> {

    // Tamanio maximo inicial por defecto del arreglo
    private static final int MAX = 5;

    // Indica cuantos elementos tiene actualmente la lista
    private int indice = 0;

    // Arreglo donde se almacenan los datos
    private Object[] datos = null;

    // Constructor por defecto.
    // Crea una lista con tamanio inicial MAX.
    public ArrayList() {
        this(MAX);
    }

    // Constructor que permite indicar el tamanio inicial del arreglo.
    public ArrayList(int tam) {

        // Si el tamanio es negativo, se lanza una excepcion.
        if (tam < 0) {
            throw new IllegalArgumentException();
        }

        // Se crea el arreglo con el tamanio indicado.
        datos = new Object[tam];
    }

    // Metodo privado que limpia las referencias del arreglo.
    // Esto ayuda a que los objetos puedan ser eliminados por el Garbage Collector.
    private void asegurarGC() {
        for (int i = 0; i < datos.length; i++) {
            datos[i] = null;
        }
    }

// Agrega un elemento al final de la lista usando agregarFinal().
    @Override
    public void agregarElemento(E e) {

        agregarFinal(e);
    }

    // Agrega un elemento al final de la lista.
    @Override
    public void agregarFinal(E e) {

        Object[] aux = null;

        // Si el arreglo ya casi esta lleno, se crea uno mas grande.
        if (!(indice < datos.length - 1)) {

            // Nuevo arreglo con crecimiento del 50%.
            aux = new Object[datos.length + datos.length / 2];

            // Copia los datos del arreglo viejo al nuevo.
            System.arraycopy(datos, 0, aux, 0, datos.length);

            // Limpia las referencias del arreglo anterior.
            asegurarGC();

            // El arreglo principal ahora apunta al arreglo nuevo.
            datos = aux;
        }

        // Se guarda el elemento en la posicion actual.
        datos[indice] = e;

        // Se incrementa el numero de elementos.
        indice++;
    }

    // Agrega un elemento al inicio de la lista.
    @Override
    public void agregarInicio(E e) {

        Object[] auxobj = null;

        // Imprime el numero actual de elementos.
        System.out.println(indice);

        // Si todavia hay espacio, recorre los elementos una posicion a la derecha.
        if (indice < datos.length - 1) {
            System.arraycopy(datos, 0, datos, 1, indice + 1);
        } else {

            // Si no hay espacio, crea un arreglo mas grande.
            auxobj = new Object[datos.length + datos.length / 2];

            // Copia los elementos al nuevo arreglo desde la posicion 1.
            System.arraycopy(datos, 0, auxobj, 1, datos.length);

            // Limpia las referencias del arreglo anterior.
            asegurarGC();

            // El arreglo principal ahora apunta al arreglo nuevo.
            datos = auxobj;
        }

        // Coloca el nuevo elemento al inicio.
        datos[0] = e;

        // Aumenta el contador de elementos.
        indice++;
    }

    @Override
    public void agregarPosicion(E e, int posicion) {

	//Hacemos un condicional si la posicion dado esta fura del rango, arrojando una excepcion.
        if (posicion < 0 || posicion > indice) {
            throw new IllegalArgumentException("Esta fuera del rangop");
        }
	//En este caso, hacemos condicional, para saber si queda o no espacio en el arreglo, si no, lo expandimos.
        if (indice == datos.length) {
            Object[] aux = new Object[datos.length + (datos.length / 2)];

            System.arraycopy(datos, 0, aux, 0, posicion);
            System.arraycopy(datos, posicion, aux, posicion + 1, indice - posicion);

            asegurarGC();
            datos = aux;
        } else {
	//Si no, solamente colocamos el elemento en la posicion desocupada, deeplanzado el arreglo una posicion.
            System.arraycopy(datos, posicion, datos, posicion + 1, indice - posicion);
        }
		//Agregamos el elemento
        datos[posicion] = e;
		//Incrementamos el contador de elementos
        indice++;
    }

    //Elimina un elemento del final de la coleccion
    @Override
    public E eliminarElemento(){
	//Guardamamos el ultimo elemento.
        E elemento = (E) datos[indice - 1];
	//Actualizamos el tamano y el contenido del arreglo con un elemento ya menos
        datos[indice - 1] = null;
	//Decrementamos el contador de elementos del arreglo
        indice --;
	//Volvemos la lista sin el elemento
        return elemento;
    }

    //Elimina un elemento del inicio de la coleccion
    @Override
    public E eliminarElementoInicio(){
	//Nos posicionamos, en el primer dato ubicado en la posicion 0.
        E elemento = (E) datos[0];
	//Realizamos el arreglo, empezando con una posicion adelante del primer elemento, del 0 al 1
        System.arraycopy(datos, 1, datos, 0, indice - 1);
	//Eliminamos los elementos sobreescritos.
        datos[indice - 1]= null;
	//Decrementamos el contador de elementos que hay
        indice --;
        return elemento;

    }

    //Elimina un elemento del final de la coleccion
    @Override
    public E eliminarElementoFinal(){

        E elemento = (E) datos[indice - 1];

        indice --;
        return elemento;
    }

    //Elimina un elemento de un determinada posicion
    @Override
    public E eliminarElementoPosicion(int posicion){
	//Guardar el elemento antes de borrarlo, de n posicion
        E elemento = (E) datos[posicion];
	//Realiza el arreglo donde deplazamos desde posicion +1
        System.arraycopy(datos, posicion + 1, datos, posicion, indice - posicion - 1);
	//Limpia elementos sobreescritos
        datos[indice - 1] = null;
	//Actualizamos el contador de elementos
	indice --;
	//regresamos el valor
        return elemento;
    }


    // Indica si la lista esta vacia.
    @Override
    public boolean esVacia() {
        return indice == 0;
    }

    // Devuelve cuantos elementos tiene la lista.
    @Override
    public int numElementos() {
        return indice;
    }

    // Limpia la lista.
    @Override
    public void limpiarLista() {

        // Reinicia el contador de elementos.
        indice = 0;

        // Borra las referencias de los objetos almacenados.
        asegurarGC();
    }

    
    @Override
	//Guardamos la posicion del elemento de los datos que hay.
	public E consultar(int posicion){
        return (E) datos[posicion];
    }

    //
    @Override
    public E[] convertirArreglo() {
	//Hacemos de los datos un arreglo
        return (E[]) Arrays.copyOf(datos, indice);
    }



    //Un Iterator : Permite recorrer la lista usando un Iterator.
        // Un Iterator es un objeto que permite recorrer una colección
    // elemento por elemento, sin necesidad de acceder directamente
    // a las posiciones del arreglo.
    // 
    // En este caso, el Iterator permitirá recorrer los elementos
    // guardados en nuestra lista ArrayList usando un ciclo for-each,
    // por ejemplo:
    //
    // for(E elemento : lista) {
    //     System.out.println(elemento);
    // }
    //
    // Para que esto funcione, la clase debe proporcionar el método iterator().

    // Este método permite recorrer la lista usando un Iterator.
    @Override
    public Iterator<E> iterator() {

        // Se crea y se regresa un nuevo Iterator.
        // Este Iterator sabe cómo recorrer los datos guardados
        // dentro del arreglo interno llamado datos.
        //
        // new Iterator<E>() { ... }
        // significa que estamos creando una clase anónima,
        // es decir, una clase sin nombre que implementa Iterator.
        return new Iterator<E>() {

            // Esta variable indica la posición actual del recorrido.
            // Inicia en 0 porque los arreglos en Java comienzan
            // en la posición 0.
            //
            // Ejemplo:
            // datos[0], datos[1], datos[2], ...
            int i = 0;

            // Este método pregunta si todavía quedan elementos
            // por recorrer en la lista.
            @Override
            public boolean hasNext() {

                // indice representa la cantidad de elementos reales
                // que tiene la lista.
                //
                // i representa la posición actual del Iterator.
                //
                // Mientras i sea menor que indice, significa que
                // todavía hay elementos disponibles.
                //
                // Ejemplo:
                // Si indice = 3, hay elementos en:
                // datos[0], datos[1] y datos[2]
                //
                // Si i = 0: 0 < 3 → true
                // Si i = 1: 1 < 3 → true
                // Si i = 2: 2 < 3 → true
                // Si i = 3: 3 < 3 → false
                //
                // Cuando devuelve false, el recorrido termina.
                return i < indice;
            }

            // Este método devuelve el siguiente elemento de la lista.
            @Override
            public E next() {

                // Como el arreglo datos fue declarado como Object[],
                // puede guardar objetos de cualquier tipo.
                //
                // Sin embargo, la lista es genérica: ArrayList<E>.
                // Eso significa que debe regresar elementos del tipo E.
                //
                // Por eso se hace una conversión:
                // de Object a E.
                //
                // Ejemplo:
                // Si la lista es ArrayList<String>,
                // entonces E representa String.
                @SuppressWarnings("unchecked")
                E aux = (E) datos[i];

                // Después de guardar el elemento actual en aux,
                // se aumenta i para avanzar a la siguiente posición.
                //
                // Ejemplo:
                // Si i valía 0, ahora valdrá 1.
                // Si i valía 1, ahora valdrá 2.
                i++;

                // Se regresa el elemento que se obtuvo antes de avanzar.
                //
                // Aunque i ya cambió, aux conserva el dato anterior.
                //
                // Ejemplo:
                // Si datos[0] era "Ana", se regresa "Ana".
                // En la siguiente llamada a next(), se regresará datos[1].
                return aux;
            }
        };
    }
}
