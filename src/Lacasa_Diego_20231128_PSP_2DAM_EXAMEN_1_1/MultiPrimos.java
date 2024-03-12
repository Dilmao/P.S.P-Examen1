package Lacasa_Diego_20231128_PSP_2DAM_EXAMEN_1_1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MultiPrimos {
    public MultiPrimos() {
    }

    static class MultiPrimo implements Runnable {
        private final Buffer a_Buffer;

        public MultiPrimo(Buffer p_Buffer) {
            this.a_Buffer = p_Buffer;
        }

        @Override
        public void run() {
            int l_ContadorBucle = 0;
            while (a_Buffer.a_NumeroSecuencia < 10_000) {
                // Obtiene la hora actual.
                Calendar l_Calendario = new GregorianCalendar();
                System.out.println("Hora: " + l_Calendario.get(Calendar.HOUR_OF_DAY) + ":" + Calendar.MINUTE + ":" + Calendar.SECOND);

                // Genera una secuencia de números y los agrega al buffer.
                for (l_ContadorBucle = 0; l_ContadorBucle < 1_000; l_ContadorBucle++) {
                    a_Buffer.a_NumeroSecuencia++;
                    a_Buffer.a_SecuenciaNumeros.add(a_Buffer.a_NumeroSecuencia);
                }

                // Divide el trabajo en tareas más pequeñas y las ejecuta en paralelo.
                Analizador l_TareaAnalizador = new Analizador(a_Buffer.a_SecuenciaNumeros,0, 1_000);
                l_TareaAnalizador.fork();
                ArrayList<Integer> l_ListaResultado = l_TareaAnalizador.join();

                // Imprime los números primos encontrados.
                for (Integer num : l_ListaResultado) {
                    System.out.println(num);
                }

                // Limpia la secuenca de números del buffer.
                a_Buffer.a_SecuenciaNumeros.clear();

                System.out.println("Hora: " + l_Calendario.get(Calendar.HOUR_OF_DAY) + ":" + l_Calendario.get(Calendar.MINUTE) + ":" + l_Calendario.get(Calendar.SECOND));

                try {
                    // Pausa la ejecución por 3 segundos.
                    Thread.sleep(3_000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
