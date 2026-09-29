package com.example.estudiantesapp.Modelo;

/**
 * TDA Estudiante: guarda el nombre y las calificaciones de un estudiante en
 * un arreglo de tamaño fijo. Las materias son fijas (plan de estudios), así que
 * solo se captura la calificación de cada una; la calificación de la posición
 * i pertenece a la materia de la posición i.
 *
 * Autor: Gerardo Cruz Hernández
 */
public class Estudiante {

    // Nota mínima para aprobar una materia (se usa en todos los métodos).
    protected static final float notaAprobatoria = 7;

    // Materias
    private static final String[] materiasPlan = {
            "Inglés IV",
            "Ética Profesional",
            "Cálculo de Varias Variables",
            "Aplicaciones Web",
            "Estructura de Datos",
            "Desarrollo de Aplicaciones Móviles",
            "Análisis y Diseño de Software"
    };

    private String nombre;
    private float[] calificaciones;

    // Crea el estudiante (valida el nombre) y reserva una nota por cada materia fija.
    public Estudiante(String nombre) {
        SetNombre(nombre);
        this.calificaciones = new float[materiasPlan.length];
    }

    // ===================== MÉTODOS DE ACCESO (5) =====================

    // 1. Regresa una copia de los nombres de las materias fijas.
    public static String[] ObtenerMaterias() {
        return materiasPlan.clone();
    }

    // 2. Regresa el nombre del estudiante.
    public String GetNombre() {
        return nombre;
    }

    // 3. Cambia el nombre, validando que no venga vacío y que solo tenga letras
    // (se permiten acentos, ñ, espacios, apóstrofo y guion; no números).
    // Es final porque se usa dentro del constructor.
    public final void SetNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        String limpio = nombre.trim().replaceAll("\\s+", " ");
        if (!limpio.matches("\\p{L}+([ '\\-]\\p{L}+)*")) {
            throw new IllegalArgumentException("El nombre solo puede contener letras (sin números ni símbolos)");
        }
        this.nombre = limpio;
    }

    // 4. Regresa la calificación de la materia de una posición.
    public float GetCalificacion(int posicion) {
        return calificaciones[posicion];
    }

    // 5. Guarda la nota de una materia. Valida que esté entre 0 y 10.
    public void RegistrarCalificacion(int posicion, float valor) {
        if (Float.isNaN(valor) || valor < 0 || valor > 10) {
            throw new IllegalArgumentException("La calificación debe estar entre 0 y 10");
        }
        calificaciones[posicion] = valor;
    }

    // ===================== MÉTODOS DE DOMINIO (5) =====================

    // 6. Recorre el arreglo sumando todas las notas y saca el promedio.
    public float CalcularPromedio() {
        float suma = 0;
        for (int i = 0; i < calificaciones.length; i++) {
            suma = suma + calificaciones[i];
        }
        return suma / calificaciones.length;
    }

    // 7. Recorre el arreglo comparando notas para quedarse con la más alta.
    public float BuscarNotaMaxima() {
        float maxima = calificaciones[0];
        for (int i = 1; i < calificaciones.length; i++) {
            if (calificaciones[i] > maxima) {
                maxima = calificaciones[i];
            }
        }
        return maxima;
    }

    /**
     * 8. ÚNICO MÉTODO RECURSIVO. Regresa la POSICIÓN de la nota más baja
     * desde "indice" hasta el final del arreglo.
     * Caso base: última posición -> esa posición es la mínima.
     * Avance: se busca la mínima del resto (indice + 1) y se compara
     * contra la nota de la posición actual.
     */
    public int BuscarPosicionNotaMinimaRecursiva(int indice) {
        if (indice == calificaciones.length - 1) {
            return indice; // caso base
        }

        int posicionResto = BuscarPosicionNotaMinimaRecursiva(indice + 1); // avance

        if (calificaciones[indice] <= calificaciones[posicionResto]) {
            return indice;
        } else {
            return posicionResto;
        }
    }

    // 9. Recorre el arreglo contando cuántas materias están reprobadas (< 7).
    public int ContarReprobadas() {
        int contador = 0;
        for (int i = 0; i < calificaciones.length; i++) {
            if (calificaciones[i] < notaAprobatoria) {
                contador = contador + 1;
            }
        }
        return contador;
    }

    // 10. Indica si la materia de una posición está reprobada.
    public boolean EstaReprobada(int posicion) {
        return calificaciones[posicion] < notaAprobatoria;
    }
}