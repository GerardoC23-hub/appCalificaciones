package com.example.estudiantesapp.Modelo;

/**
 * EstudianteBecado: hereda de Estudiante y agrega el manejo de una beca.
 *
 * Autor: Gerardo Cruz Hernández
 */
public class EstudianteBecado extends Estudiante {

    private float porcentajeBeca;

    // Construye la parte "Estudiante" con super() y guarda el porcentaje de beca.
    public EstudianteBecado(String nombre, int cantidadCalificaciones, float porcentajeBeca) {
        super(nombre, cantidadCalificaciones);
        setPorcentajeBeca(porcentajeBeca);
    }

    // Regresa el porcentaje de beca.
    public float getPorcentajeBeca() {
        return porcentajeBeca;
    }

    // Cambia el porcentaje de beca, validando que esté entre 0 y 100.
    public void setPorcentajeBeca(float porcentajeBeca) {
        if (porcentajeBeca < 0 || porcentajeBeca > 100) {
            throw new IllegalArgumentException("El porcentaje de beca debe estar entre 0 y 100");
        }
        this.porcentajeBeca = porcentajeBeca;
    }

    // Aplica el descuento de la beca sobre una colegiatura y regresa lo que se debe pagar.
    public float calcularMontoAPagar(float colegiatura) {
        float descuento = colegiatura * (porcentajeBeca / 100);
        return colegiatura - descuento;
    }
}