package com.example.estudiantesapp.Modelo;

/**
 * EstudianteBecado: hereda de Estudiante y agrega el manejo de la beca.
 * El porcentaje de beca NO se captura: se calcula con el promedio actual
 * usando una tabla de rangos. Si hay materias reprobadas no hay beca.
 *
 * Autor: Gerardo Cruz Hernández
 */
public class EstudianteBecado extends Estudiante {

    // Tabla de beca (de mayor a menor): con promedio >= promediosMinimos[i]
    // le corresponde porcentajes[i].
    private static final float[] promediosMinimos = {9.5f, 9.0f, 8.5f, 8.0f};
    private static final int[] porcentajes = {100, 75, 50, 25};

    private float colegiatura;

    // Construye la parte "Estudiante" con super() y guarda la colegiatura.
    public EstudianteBecado(String nombre, float colegiatura) {
        super(nombre);
        SetColegiatura(colegiatura);
    }

    // Regresa el monto de la colegiatura.
    public float GetColegiatura() {
        return colegiatura;
    }

    // Cambia la colegiatura, validando que no sea negativa.
    // Es final porque se usa dentro del constructor.
    public final void SetColegiatura(float colegiatura) {
        if (Float.isNaN(colegiatura) || colegiatura < 0) {
            throw new IllegalArgumentException("La colegiatura debe ser un número mayor o igual a 0");
        }
        this.colegiatura = colegiatura;
    }

    // Calcula el porcentaje de beca según el promedio. Con materias reprobadas
    // o con promedio menor al mínimo de la tabla no hay beca (0).
    public int CalcularPorcentajeBeca() {
        if (ContarReprobadas() > 0) {
            return 0;
        }
        float promedio = CalcularPromedio();
        for (int i = 0; i < promediosMinimos.length; i++) {
            if (promedio >= promediosMinimos[i]) {
                return porcentajes[i];
            }
        }
        return 0;
    }

    // Aplica el descuento del porcentaje actual y regresa lo que se debe pagar.
    public float CalcularMontoAPagar() {
        float descuento = colegiatura * (CalcularPorcentajeBeca() / 100f);
        return colegiatura - descuento;
    }

    // Regresa un mensaje con el estado de la beca.
    public String ObtenerEstadoBeca() {
        if (ContarReprobadas() > 0) {
            return "Sin beca: tiene materias reprobadas";
        }
        int porcentaje = CalcularPorcentajeBeca();
        if (porcentaje == 0) {
            return "Sin beca: el promedio es menor a " + promediosMinimos[promediosMinimos.length - 1];
        }
        return "Beca del " + porcentaje + "%";
    }
}