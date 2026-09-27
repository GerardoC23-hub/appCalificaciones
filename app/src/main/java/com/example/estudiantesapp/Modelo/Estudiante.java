package com.example.estudiantesapp.Modelo;

/**
 * TDA Estudiante: guarda el nombre y las calificaciones de un estudiante
 * en un arreglo de tamaño fijo. Tiene 8 métodos: 2 de acceso y 6 de
 * dominio (5 iterativos + 1 recursivo).
 *
 * Autor: Gerardo Cruz Hernández
 */
public class Estudiante {

    private String nombre;
    private float[] calificaciones;

    // Crea el estudiante y reserva espacio para sus notas.
    public Estudiante(String nombre, int cantidadCalificaciones) {
        setNombre(nombre);
        if (cantidadCalificaciones <= 0) {
            throw new IllegalArgumentException("La cantidad de calificaciones debe ser mayor a 0");
        }
        this.calificaciones = new float[cantidadCalificaciones];
    }

    // Regresa el nombre del estudiante.
    public String getNombre() {
        return nombre;
    }

    // Cambia el nombre, validando que no venga vacío.
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre;
    }

    // Guarda una nota en una posición del arreglo. Valida que la posición
    // exista y que la nota esté entre 0 y 10.
    public void registrarCalificacion(int posicion, float valor) {
        if (posicion < 0 || posicion >= calificaciones.length) {
            throw new IllegalArgumentException("Posición fuera de rango");
        }
        if (valor < 0 || valor > 10) {
            throw new IllegalArgumentException("La calificación debe estar entre 0 y 10");
        }
        calificaciones[posicion] = valor;
    }

    // Recorre el arreglo sumando todas las notas y saca el promedio.
    public float calcularPromedio() {
        float suma = 0;
        for (int i = 0; i < calificaciones.length; i++) {
            suma = suma + calificaciones[i];
        }
        return suma / calificaciones.length;
    }

    // Recorre el arreglo comparando notas para quedarse con la más alta.
    public float buscarNotaMaxima() {
        float maxima = calificaciones[0];
        for (int i = 1; i < calificaciones.length; i++) {
            if (calificaciones[i] > maxima) {
                maxima = calificaciones[i];
            }
        }
        return maxima;
    }

    // Recorre el arreglo contando cuántas notas son >= 7 (aprobatorias).
    public int contarAprobadas() {
        int contador = 0;
        for (int i = 0; i < calificaciones.length; i++) {
            if (calificaciones[i] >= 7) {
                contador = contador + 1;
            }
        }
        return contador;
    }

    /**
     * ÚNICO MÉTODO RECURSIVO. Busca la nota más baja desde "indice" hasta el final.
     * Caso base: última posición -> esa nota es la mínima.
     * Avance: se compara la nota actual contra la mínima del resto (llamada recursiva).
     */
    public float buscarNotaMinimaRecursiva(int indice) {
        if (indice == calificaciones.length - 1) {
            return calificaciones[indice]; // caso base
        }

        float minimaResto = buscarNotaMinimaRecursiva(indice + 1); // avance

        if (calificaciones[indice] < minimaResto) {
            return calificaciones[indice];
        } else {
            return minimaResto;
        }
    }

    // Usa el promedio para decidir si el estudiante aprueba (>= 7) o reprueba.
    public String obtenerEstadoFinal() {
        if (calcularPromedio() >= 7) {
            return "Aprobado";
        } else {
            return "Reprobado";
        }
    }
}