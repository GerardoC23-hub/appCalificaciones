package com.example.estudiantesapp.Vista;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.estudiantesapp.Presentador.EstudiantePresenter;
import com.example.estudiantesapp.R;

public class MainActivityView extends AppCompatActivity {

    private EditText txtNombre, txtNota1, txtNota2, txtNota3, txtNota4, txtNota5, txtPorcentajeBeca, txtColegiatura;
    private CheckBox chkBecado;
    private Button btnCalcular;
    private TextView txtNombreMostrado, txtPromedio, txtNotaMaxima, txtNotaMinima, txtAprobadas, txtEstado, txtMontoAPagar, txtAutor;

    private EstudiantePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombre = findViewById(R.id.txtNombre);
        txtNota1 = findViewById(R.id.txtNota1);
        txtNota2 = findViewById(R.id.txtNota2);
        txtNota3 = findViewById(R.id.txtNota3);
        txtNota4 = findViewById(R.id.txtNota4);
        txtNota5 = findViewById(R.id.txtNota5);
        chkBecado = findViewById(R.id.chkBecado);
        txtPorcentajeBeca = findViewById(R.id.txtPorcentajeBeca);
        txtColegiatura = findViewById(R.id.txtColegiatura);
        btnCalcular = findViewById(R.id.btnCalcular);
        txtNombreMostrado = findViewById(R.id.txtNombreMostrado);
        txtPromedio = findViewById(R.id.txtPromedio);
        txtNotaMaxima = findViewById(R.id.txtNotaMaxima);
        txtNotaMinima = findViewById(R.id.txtNotaMinima);
        txtAprobadas = findViewById(R.id.txtAprobadas);
        txtEstado = findViewById(R.id.txtEstado);
        txtMontoAPagar = findViewById(R.id.txtMontoAPagar);
        txtAutor = findViewById(R.id.txtAutor);

        txtAutor.setText("Autor: Gerardo Cruz Hernández");

        presenter = new EstudiantePresenter(this);
        btnCalcular.setOnClickListener(this::calcularTodo);
    }

    // Junta las 5 notas, registra al estudiante y, solo si salió bien,
    // pide todos los demás cálculos al presenter.
    private void calcularTodo(View v) {
        String[] notas = new String[]{
                txtNota1.getText().toString(),
                txtNota2.getText().toString(),
                txtNota3.getText().toString(),
                txtNota4.getText().toString(),
                txtNota5.getText().toString()
        };

        boolean registroExitoso = presenter.registrarEstudiante(
                txtNombre.getText().toString(),
                notas,
                chkBecado.isChecked(),
                txtPorcentajeBeca.getText().toString()
        );

        if (!registroExitoso) {
            return; // hubo un error, ya se mostró el Toast
        }

        presenter.calcularPromedio();
        presenter.buscarNotaMaxima();
        presenter.buscarNotaMinima();
        presenter.contarAprobadas();
        presenter.obtenerEstadoFinal();
        presenter.calcularMontoAPagar(txtColegiatura.getText().toString());
    }

    public void mostrarNombre(String nombre) {
        txtNombreMostrado.setText("Estudiante: " + nombre);
    }

    public void mostrarPromedio(String promedio) {
        txtPromedio.setText("Promedio: " + promedio);
    }

    public void mostrarNotaMaxima(String maxima) {
        txtNotaMaxima.setText("Nota más alta: " + maxima);
    }

    public void mostrarNotaMinima(String minima) {
        txtNotaMinima.setText("Nota más baja: " + minima);
    }

    public void mostrarAprobadas(int cantidad) {
        txtAprobadas.setText("Calificaciones aprobadas: " + cantidad);
    }

    public void mostrarEstadoFinal(String estado) {
        txtEstado.setText("Estado: " + estado);
    }

    public void mostrarMontoAPagar(String texto) {
        txtMontoAPagar.setText(texto);
    }

    public void mostrarError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
}