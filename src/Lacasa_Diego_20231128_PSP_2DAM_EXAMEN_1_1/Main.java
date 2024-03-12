package Lacasa_Diego_20231128_PSP_2DAM_EXAMEN_1_1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final int NUMERO_HILOS = 3;
    public static void main(String[] args) {
        // Creación de una instancia de Buffer para almacenar la secuencia de números generados.
        Buffer l_Buffer = new Buffer();

        // Creación de un pool de hilos con un número fijo de hilos para ejecutar tareas en paralelo.
        final ExecutorService l_Executor = Executors.newFixedThreadPool(NUMERO_HILOS);

        // Creación de una tarea que realiza el procesamiento principal, pasando el buffer como argumento.
        final Runnable l_TareaMultiPrimo = new MultiPrimos.MultiPrimo(l_Buffer);

        // Envío de la tarea al pool de hilos para su ejecución.
        l_Executor.submit(l_TareaMultiPrimo);

        // Apagado del pool de hilos después de que todas las tareas hayan sido completadas.
        l_Executor.shutdown();
    }
}
