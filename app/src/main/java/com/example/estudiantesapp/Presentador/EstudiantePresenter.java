package com.example.estudiantesapp.Presentador;

import com.example.estudiantesapp.Modelo.Estudiante;
import com.example.estudiantesapp.Modelo.EstudianteBecado;
import com.example.estudiantesapp.Vista.MainActivityView;

import java.util.Locale;

/**
 * Presentador (MVP): conecta la Vista con el Modelo. Convierte el texto que
 * escribe el usuario en números, se los pasa al Estudiante, y regresa los
 * resultados ya formateados a la Vista (incluyendo los textos de estado).
 *
 * Autor: Gerardo Cruz Hernández
 */
public class EstudiantePresenter {

    private final MainActivityView vista;
    private Estudiante estudiante;

    public EstudiantePresenter(MainActivityView vista) {
        this.vista = vista;
    }

    // Regresa los nombres de las materias fijas para que la Vista arme sus filas.
    public String[] ObtenerMaterias() {
        return Estudiante.ObtenerMaterias();
    }

    // Crea el Estudiante (o EstudianteBecado), registra sus notas y muestra
    // los resultados. Regresa true/false para que la Vista sepa si salió bien.
    public boolean RegistrarEstudiante(String nombre, String[] notasTexto,
                                       boolean esBecado, String colegiaturaTexto) {
        try {
            Estudiante nuevo;

            if (esBecado) {
                if (colegiaturaTexto.trim().isEmpty()) {
                    vista.MostrarError("Escribe la colegiatura del estudiante becado");
                    return false;
                }
                float colegiatura = Float.parseFloat(colegiaturaTexto.trim());
                nuevo = new EstudianteBecado(nombre, colegiatura);
            } else {
                nuevo = new Estudiante(nombre);
            }

            String[] materias = Estudiante.ObtenerMaterias();

            if (notasTexto.length != materias.length) {
                vista.MostrarError("Faltan calificaciones por capturar");
                return false;
            }

            for (int i = 0; i < notasTexto.length; i++) {
                if (notasTexto[i].trim().isEmpty()) {
                    vista.MostrarError("Escribe la calificación de " + materias[i]);
                    return false;
                }
                float valor = Float.parseFloat(notasTexto[i].trim());
                nuevo.RegistrarCalificacion(i, valor);
            }

            // Solo se reemplaza el estudiante anterior si todo salió bien.
            estudiante = nuevo;
            MostrarResultados();
            return true;

        } catch (NumberFormatException e) {
            vista.MostrarError("Verifica que las calificaciones y la colegiatura sean números válidos");
            return false;
        } catch (IllegalArgumentException e) {
            vista.MostrarError(e.getMessage());
            return false;
        }
    }

    // Pide todos los datos al Modelo y se los manda ya formateados a la Vista.
    private void MostrarResultados() {
        String[] materias = Estudiante.ObtenerMaterias();
        int total = materias.length;
        int cantidadReprobadas = estudiante.ContarReprobadas();

        String[] notas = new String[total];
        String[] etiquetas = new String[total];
        boolean[] reprobadas = new boolean[total];

        for (int i = 0; i < total; i++) {
            notas[i] = Formato(estudiante.GetCalificacion(i));
            reprobadas[i] = estudiante.EstaReprobada(i);
            etiquetas[i] = reprobadas[i] ? "Reprobada" : "Aprobada";
        }

        vista.MostrarNombre(estudiante.GetNombre());
        vista.MostrarEstado(ArmarEstadoFinal(materias, reprobadas, cantidadReprobadas),
                cantidadReprobadas == 0);
        vista.MostrarCalificaciones(materias, notas, etiquetas, reprobadas);

        vista.MostrarPromedio(Formato(estudiante.CalcularPromedio()));
        vista.MostrarNotaMaxima(Formato(estudiante.BuscarNotaMaxima()));

        // Método recursivo: regresa la posición de la nota más baja.
        int posicionMinima = estudiante.BuscarPosicionNotaMinimaRecursiva(0);
        vista.MostrarNotaMinima(materias[posicionMinima]
                + " (" + Formato(estudiante.GetCalificacion(posicionMinima)) + ")");

        vista.MostrarAprobadas(total - cantidadReprobadas);
        vista.MostrarReprobadas(cantidadReprobadas);

        // La beca solo aplica si el estudiante es EstudianteBecado (instanceof).
        if (estudiante instanceof EstudianteBecado) {
            EstudianteBecado becado = (EstudianteBecado) estudiante;
            vista.MostrarBeca(becado.ObtenerEstadoBeca());
            vista.MostrarMontoAPagar("Colegiatura: $" + Formato(becado.GetColegiatura())
                    + " | Beca: " + becado.CalcularPorcentajeBeca() + "%"
                    + " | A pagar: $" + Formato(becado.CalcularMontoAPagar()));
        } else {
            vista.MostrarBeca("No aplica (no es becado)");
            vista.MostrarMontoAPagar("No aplica (no es becado)");
        }
    }

    // Arma el texto del estado del semestre. Si hay materias reprobadas,
    // las lista como "A y B" o "A, B y C". Primera línea: estado; segunda: detalle.
    private String ArmarEstadoFinal(String[] materias, boolean[] reprobadas, int cantidadReprobadas) {
        if (cantidadReprobadas == 0) {
            return "Aprobado";
        }

        String texto = "";
        int encontradas = 0;

        for (int i = 0; i < materias.length; i++) {
            if (reprobadas[i]) {
                encontradas = encontradas + 1;
                if (encontradas == 1) {
                    texto = materias[i];
                } else if (encontradas == cantidadReprobadas) {
                    texto = texto + " y " + materias[i];
                } else {
                    texto = texto + ", " + materias[i];
                }
            }
        }
        return "Pendiente\nPara aprobar el semestre debe aprobar: " + texto;
    }

    // Da formato de dos decimales a un número.
    private String Formato(float valor) {
        return String.format(Locale.US, "%.2f", valor);
    }
}