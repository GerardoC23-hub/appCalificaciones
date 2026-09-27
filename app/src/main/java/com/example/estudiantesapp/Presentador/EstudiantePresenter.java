package com.example.estudiantesapp.Presentador;

import com.example.estudiantesapp.Vista.MainActivityView;
import com.example.estudiantesapp.Modelo.Estudiante;
import com.example.estudiantesapp.Modelo.EstudianteBecado;

/**
 * Presentador (MVP): conecta la Vista con el Modelo. Convierte el texto
 * que escribe el usuario en números, se los pasa al Estudiante, y regresa
 * los resultados ya formateados a la Vista.
 *
 * Autor: Gerardo Cruz Hernández
 */
public class EstudiantePresenter {

    private MainActivityView vista;
    private Estudiante estudiante;

    public EstudiantePresenter(MainActivityView vista) {
        this.vista = vista;
    }

    // Crea el Estudiante (o EstudianteBecado) y registra sus notas.
    // Regresa true/false para que la Vista sepa si debe seguir calculando o no.
    public boolean registrarEstudiante(String nombre, String[] notasTexto, boolean esBecado, String porcentajeBecaTexto) {
        try {
            int cantidad = notasTexto.length;

            if (esBecado) {
                float porcentaje;
                if (porcentajeBecaTexto.trim().isEmpty()) {
                    porcentaje = 0;
                } else {
                    porcentaje = Float.parseFloat(porcentajeBecaTexto);
                }
                estudiante = new EstudianteBecado(nombre, cantidad, porcentaje);
            } else {
                estudiante = new Estudiante(nombre, cantidad);
            }

            for (int i = 0; i < cantidad; i++) {
                float valor = Float.parseFloat(notasTexto[i]);
                estudiante.registrarCalificacion(i, valor);
            }

            vista.mostrarNombre(estudiante.getNombre());
            return true;

        } catch (NumberFormatException e) {
            vista.mostrarError("Verifica que las calificaciones sean números válidos");
            return false;
        } catch (IllegalArgumentException e) {
            vista.mostrarError(e.getMessage());
            return false;
        }
    }

    // Pide el promedio al Modelo y lo manda a mostrar.
    public void calcularPromedio() {
        if (estudiante == null) {
            return;
        }
        float promedio = estudiante.calcularPromedio();
        vista.mostrarPromedio(String.format("%.2f", promedio));
    }

    // Pide la nota más alta al Modelo y la manda a mostrar.
    public void buscarNotaMaxima() {
        if (estudiante == null) {
            return;
        }
        float maxima = estudiante.buscarNotaMaxima();
        vista.mostrarNotaMaxima(String.format("%.2f", maxima));
    }

    // Pide la nota más baja (método recursivo) al Modelo y la manda a mostrar.
    public void buscarNotaMinima() {
        if (estudiante == null) {
            return;
        }
        float minima = estudiante.buscarNotaMinimaRecursiva(0);
        vista.mostrarNotaMinima(String.format("%.2f", minima));
    }

    // Pide cuántas notas aprobaron y lo manda a mostrar.
    public void contarAprobadas() {
        if (estudiante == null) {
            return;
        }
        int cantidad = estudiante.contarAprobadas();
        vista.mostrarAprobadas(cantidad);
    }

    // Pide el estado final (Aprobado/Reprobado) y lo manda a mostrar.
    public void obtenerEstadoFinal() {
        if (estudiante == null) {
            return;
        }
        String estado = estudiante.obtenerEstadoFinal();
        vista.mostrarEstadoFinal(estado);
    }

    // Calcula cuánto paga el estudiante de colegiatura, solo si es becado
    // (se verifica con instanceof). Si no es becado, avisa que no aplica.
    public void calcularMontoAPagar(String colegiaturaTexto) {
        if (estudiante == null) {
            return;
        }

        if (!(estudiante instanceof EstudianteBecado)) {
            vista.mostrarMontoAPagar("No aplica (no es becado)");
            return;
        }

        try {
            float colegiatura = Float.parseFloat(colegiaturaTexto);
            EstudianteBecado becado = (EstudianteBecado) estudiante;

            float porcentaje = becado.getPorcentajeBeca();
            float monto = becado.calcularMontoAPagar(colegiatura);

            vista.mostrarMontoAPagar("Beca: " + porcentaje + "% | Monto a pagar: $" + String.format("%.2f", monto));

        } catch (NumberFormatException e) {
            vista.mostrarError("Verifica que la colegiatura sea un número válido");
        }
    }
}