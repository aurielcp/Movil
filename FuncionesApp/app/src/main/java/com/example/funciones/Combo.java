package com.example.funciones;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class Combo extends Activity {
    Spinner spinnerColores;
    EditText etTexto;

    @Override
    public void onCreate(Bundle si) {
        super.onCreate(si);
        setContentView(R.layout.vista);

        spinnerColores = findViewById(R.id.textospinner);
        etTexto = findViewById(R.id.textouno);

        final String colores[] = {"Rojo", "Verde", "Azul"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                colores);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerColores.setAdapter(adapter);

        // Listener para cambiar el color según la selección
        spinnerColores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // Rojo
                        etTexto.setTextColor(Color.RED);
                        break;
                    case 1: // Verde
                        etTexto.setTextColor(Color.GREEN);
                        break;
                    case 2: // Azul
                        etTexto.setTextColor(Color.BLUE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}