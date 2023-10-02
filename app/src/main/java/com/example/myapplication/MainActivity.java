package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText editTextNumber;
    private String numeroGuardado;
    private int numeroAdivinar;
    private TextView historialTextView;

    private TextView TotalAdv;
    private int totalAciertos = 0;
    private List<String> historialList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        historialTextView = findViewById(R.id.historialTextView);
        historialTextView.setText("Historial:");

        TotalAdv = findViewById(R.id.totalAdv);


        button = findViewById(R.id.button);
        editTextNumber = findViewById(R.id.editTextNumber);

        Random random = new Random();
        numeroAdivinar = random.nextInt(100) + 1;

        // Deshabilita el botón al inicio
        button.setEnabled(false);

        // Agrega un TextWatcher al EditText
        editTextNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Verifica si el EditText contiene un número válido
                if (isValidNumber(s.toString())) {
                    button.setEnabled(true); // Habilita el botón si es válido
                } else {
                    button.setEnabled(false); // Deshabilita el botón si no es válido
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                numeroGuardado = editTextNumber.getText().toString();

                // Comprueba si el número ingresado es igual al número a adivinar
                if (numeroGuardado.equals(String.valueOf(numeroAdivinar))) {
                    Toast.makeText(MainActivity.this, "¡Adivinaste el número!", Toast.LENGTH_SHORT).show();
                    generarNuevoNumeroAdivinar(); // Genera un nuevo número a adivinar

                    // Agrega el número adivinado al historial solo si es correcto
                    historialList.add(numeroGuardado);

                    // Actualiza el TextView del historial
                    actualizarHistorial();
                } else {
                    // Compara los números y muestra un mensaje
                    int numeroUsuario = Integer.parseInt(numeroGuardado);
                    if (numeroUsuario < numeroAdivinar) {
                        Toast.makeText(MainActivity.this, "El número es más alto", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "El número es más bajo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
    private boolean isValidNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void actualizarHistorial() {
        StringBuilder historial = new StringBuilder("Historial:\n");
        totalAciertos = 0;
        for (String numero : historialList) {
            historial.append(numero).append("\n");
            totalAciertos++;
        }
        actualizarTotal();
        historialTextView.setText(historial.toString());
    }


    private void actualizarTotal() {
        TotalAdv.setText("Total de aciertos: " + totalAciertos);
    }



    private void generarNuevoNumeroAdivinar() {
        Random random = new Random();
        numeroAdivinar = random.nextInt(100) + 1;
    }

}