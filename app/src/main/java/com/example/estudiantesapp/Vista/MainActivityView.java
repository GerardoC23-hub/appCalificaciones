package com.example.estudiantesapp.Vista;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.estudiantesapp.Presentador.EstudiantePresenter;
import com.example.estudiantesapp.R;

/**
 * Vista (MVP): pantalla principal. Solo captura datos y muestra resultados;
 * toda la lógica está en el Presentador y el Modelo. Las materias son fijas:
 * la pantalla dibuja una fila por materia y solo se escribe la calificación.
 *
 * Autor: Gerardo Cruz Hernández
 */
public class MainActivityView extends AppCompatActivity {

    private static final int colorVerde = Color.parseColor("#2E7D32");
    private static final int colorRojo = Color.parseColor("#C62828");
    private static final int colorTexto = Color.parseColor("#1C1C1E");

    private EditText[] txtNotas; // una nota por cada materia fija
    private EditText txtNombre, txtColegiatura;
    private CheckBox chkBecado;
    private View tilColegiatura, layoutResultados;
    private Button btnCalcular;
    private LinearLayout layoutNotas, layoutCalificaciones;
    private TextView txtNombreMostrado, txtEstado, txtPromedio, txtNotaMaxima, txtNotaMinima,
            txtAprobadas, txtReprobadas, txtBeca, txtMontoAPagar, txtAutor;

    private EstudiantePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombre = findViewById(R.id.txtNombre);
        layoutNotas = findViewById(R.id.layoutNotas);
        chkBecado = findViewById(R.id.chkBecado);
        tilColegiatura = findViewById(R.id.tilColegiatura);
        txtColegiatura = findViewById(R.id.txtColegiatura);
        btnCalcular = findViewById(R.id.btnCalcular);

        layoutResultados = findViewById(R.id.layoutResultados);
        txtEstado = findViewById(R.id.txtEstado);
        layoutCalificaciones = findViewById(R.id.layoutCalificaciones);
        txtNombreMostrado = findViewById(R.id.txtNombreMostrado);
        txtPromedio = findViewById(R.id.txtPromedio);
        txtNotaMaxima = findViewById(R.id.txtNotaMaxima);
        txtNotaMinima = findViewById(R.id.txtNotaMinima);
        txtAprobadas = findViewById(R.id.txtAprobadas);
        txtReprobadas = findViewById(R.id.txtReprobadas);
        txtBeca = findViewById(R.id.txtBeca);
        txtMontoAPagar = findViewById(R.id.txtMontoAPagar);
        txtAutor = findViewById(R.id.txtAutor);

        txtAutor.setText("Autor: Gerardo Cruz Hernández");

        presenter = new EstudiantePresenter(this);

        FiltrarNombreSoloLetras();
        CrearFilasDeNotas();

        // El campo de colegiatura solo se muestra si el estudiante es becado.
        chkBecado.setOnCheckedChangeListener((boton, marcado) ->
                tilColegiatura.setVisibility(marcado ? View.VISIBLE : View.GONE));

        btnCalcular.setOnClickListener(this::CalcularTodo);
    }

    // Evita que se escriban números o símbolos en el nombre (solo letras,
    // espacios, apóstrofo y guion). El Modelo vuelve a validarlo por seguridad.
    private void FiltrarNombreSoloLetras() {
        InputFilter soloLetras = (fuente, inicio, fin, destino, inicioDestino, finDestino) -> {
            for (int i = inicio; i < fin; i++) {
                char caracter = fuente.charAt(i);
                if (!Character.isLetter(caracter) && caracter != ' ' && caracter != '\'' && caracter != '-') {
                    return "";
                }
            }
            return null;
        };
        txtNombre.setFilters(new InputFilter[]{soloLetras});
    }

    // Dibuja una fila por cada materia fija: el nombre a la izquierda y el
    // campo para escribir su calificación a la derecha.
    private void CrearFilasDeNotas() {
        String[] materias = presenter.ObtenerMaterias();
        txtNotas = new EditText[materias.length];

        for (int i = 0; i < materias.length; i++) {
            LinearLayout fila = CrearFila();

            TextView lblMateria = new TextView(this);
            lblMateria.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            lblMateria.setText(materias[i]);
            lblMateria.setTextColor(colorTexto);
            lblMateria.setTextSize(14);

            EditText txtNota = new EditText(this);
            txtNota.setLayoutParams(new LinearLayout.LayoutParams(Dp(90), LinearLayout.LayoutParams.WRAP_CONTENT));
            txtNota.setHint("0 - 10");
            txtNota.setTextSize(14);
            txtNota.setGravity(Gravity.CENTER);
            txtNota.setSingleLine(true);
            txtNota.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

            txtNotas[i] = txtNota;
            fila.addView(lblMateria);
            fila.addView(txtNota);
            layoutNotas.addView(fila);
        }
    }

    // Junta las notas y le pide al presenter que registre al estudiante.
    // Los resultados los dibuja el propio presenter llamando a los métodos Mostrar...
    private void CalcularTodo(View v) {
        String[] notas = new String[txtNotas.length];
        for (int i = 0; i < txtNotas.length; i++) {
            notas[i] = txtNotas[i].getText().toString();
        }

        // Se oculta lo anterior para no dejar datos viejos si hay un error.
        layoutResultados.setVisibility(View.GONE);

        boolean registroExitoso = presenter.RegistrarEstudiante(
                txtNombre.getText().toString(),
                notas,
                chkBecado.isChecked(),
                txtColegiatura.getText().toString()
        );

        if (registroExitoso) {
            layoutResultados.setVisibility(View.VISIBLE);
        }
    }

    // ===================== MÉTODOS QUE USA EL PRESENTADOR =====================

    public void MostrarNombre(String nombre) {
        txtNombreMostrado.setText("Estudiante: " + nombre);
    }

    public void MostrarEstado(String mensaje, boolean aprobado) {
        txtEstado.setText("Estado: " + mensaje);
        txtEstado.setTextColor(aprobado ? colorVerde : colorRojo);
    }

    // Dibuja una fila por materia: nombre a la izquierda, nota y etiqueta a la derecha.
    public void MostrarCalificaciones(String[] materias, String[] notas, String[] etiquetas, boolean[] reprobadas) {
        layoutCalificaciones.removeAllViews();

        for (int i = 0; i < materias.length; i++) {
            LinearLayout fila = CrearFila();

            TextView lblMateria = new TextView(this);
            lblMateria.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            lblMateria.setText(materias[i]);
            lblMateria.setTextColor(colorTexto);
            lblMateria.setTextSize(14);

            TextView lblNota = new TextView(this);
            lblNota.setText(notas[i] + "  ·  " + etiquetas[i]);
            lblNota.setTextSize(14);
            lblNota.setTextColor(reprobadas[i] ? colorRojo : colorVerde);

            fila.addView(lblMateria);
            fila.addView(lblNota);
            layoutCalificaciones.addView(fila);
        }
    }

    public void MostrarPromedio(String promedio) {
        txtPromedio.setText("Promedio: " + promedio);
    }

    public void MostrarNotaMaxima(String maxima) {
        txtNotaMaxima.setText("Nota más alta: " + maxima);
    }

    public void MostrarNotaMinima(String minima) {
        txtNotaMinima.setText("Nota más baja: " + minima);
    }

    public void MostrarAprobadas(int cantidad) {
        txtAprobadas.setText("Materias aprobadas: " + cantidad);
    }

    public void MostrarReprobadas(int cantidad) {
        txtReprobadas.setText("Materias reprobadas: " + cantidad);
    }

    public void MostrarBeca(String texto) {
        txtBeca.setText("Beca: " + texto);
    }

    public void MostrarMontoAPagar(String texto) {
        txtMontoAPagar.setText(texto);
    }

    public void MostrarError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    // ===================== AUXILIARES =====================

    // Crea una fila horizontal con separación vertical.
    private LinearLayout CrearFila() {
        LinearLayout fila = new LinearLayout(this);
        fila.setOrientation(LinearLayout.HORIZONTAL);
        fila.setGravity(Gravity.CENTER_VERTICAL);
        fila.setPadding(0, Dp(4), 0, Dp(4));
        return fila;
    }

    // Convierte dp a píxeles según la densidad de la pantalla.
    private int Dp(int valor) {
        return Math.round(valor * getResources().getDisplayMetrics().density);
    }
}