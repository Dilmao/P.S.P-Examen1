package Lacasa_Diego_20231128_PSP_2DAM_EXAMEN_1_1;

import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class Analizador extends RecursiveTask<ArrayList<Integer>> {
    private static final int UMBRAL = 100;
    private final ArrayList<Integer> a_Vector;
    private final int a_Inicio;
    private int a_Fin = 0;

    public Analizador(ArrayList<Integer> p_Vector, int p_Inicio, int p_Fin) {
        this.a_Vector = p_Vector;
        this.a_Inicio = p_Inicio;
        this.a_Fin = p_Fin;
    }

    // Método para dividir el trabajo en tareas más pequeñas y ejecutarlas en paralelo.
    private void getPrimoRec(ArrayList<Integer> a_Primos) {
        int l_Medio = ((a_Inicio + a_Fin) / 2) + 1;
        Analizador l_Tarea1 = new Analizador(a_Vector, a_Inicio, l_Medio);
        Analizador l_Tarea2 = new Analizador(a_Vector, l_Medio, a_Fin);

        l_Tarea1.fork();
        l_Tarea2.fork();

        l_Tarea1.join();
        l_Tarea2.join();

        ArrayList<Integer> l_Resultado1 = l_Tarea1.join();
        ArrayList<Integer> l_Resultado2 = l_Tarea2.join();

        a_Primos.addAll(l_Resultado1);
        a_Primos.addAll(l_Resultado2);

    }

    // Método para encontrar números primos de forma secuencial.
    private void getPrimosSec(ArrayList<Integer> a_Primos) {
        for (Integer l_Numero : a_Vector)  {
            if (esPrimo(l_Numero)) {
                a_Primos.add(l_Numero);
            }
        }
    }

    // Método para verificar si un número es primo.
    private boolean esPrimo(int p_Numero) {
        int l_Contador = 0;
        if (p_Numero <= 1) {
            return false;
        }
        for (l_Contador = 2; l_Contador <= Math.sqrt(p_Numero); l_Contador++) {
            if (p_Numero % l_Contador == 0) {
                return false;
            }
        }
        return true;
    }

    // Método que realiza el trabajo de encontrar números primos.
    @Override
    protected ArrayList<Integer> compute() {
        ArrayList<Integer> a_Primos = new ArrayList<>();

        if ((a_Fin - a_Inicio) <= UMBRAL) {
            getPrimoRec(a_Primos);
        } else {
            getPrimosSec(a_Primos);
        }
        return a_Primos;
    }
}
