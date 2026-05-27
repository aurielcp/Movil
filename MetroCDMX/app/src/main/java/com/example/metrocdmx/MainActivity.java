package com.example.metrocdmx;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.widget.ListView;
import android.widget.ArrayAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private LineaAdapter adapter;
    private List<Linea> todasLineas = new ArrayList<>();
    private View layoutLoading, layoutStats;
    private TextView tvError, tvStatLineas, tvStatKm;
    private EditText etBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de vistas
        rv            = findViewById(R.id.miRecyclerView);
        layoutLoading = findViewById(R.id.layoutLoading);
        layoutStats   = findViewById(R.id.layoutStats);
        tvError       = findViewById(R.id.tvError);
        tvStatLineas  = findViewById(R.id.tvStatLineas);
        tvStatKm      = findViewById(R.id.tvStatKm);
        etBuscar      = findViewById(R.id.etBuscar);

        rv.setLayoutManager(new LinearLayoutManager(this));

        // Uso de lambda para el clic de las líneas
        adapter = new LineaAdapter(new ArrayList<>(), this::mostrarDetalle);
        rv.setAdapter(adapter);

        etBuscar.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int a, int b, int c) {}
            public void onTextChanged(CharSequence s, int a, int b, int c) { filtrar(s.toString()); }
            public void afterTextChanged(Editable s) {}
        });

        findViewById(R.id.btnActualizar).setOnClickListener(v -> cargarDatosLocales());

        cargarDatosLocales();
    }

    private void cargarDatosLocales() {
        // Aseguramos la visibilidad correcta al cargar
        layoutLoading.setVisibility(View.VISIBLE);
        layoutStats.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);

        // Cargamos los datos desde el repositorio central
        todasLineas = MetroRepository.getDatosLocales();
        actualizarUI(todasLineas);
    }

    private void actualizarUI(List<Linea> lista) {
        double totalKm = 0;
        for (Linea l : lista) totalKm += l.longitudKm;

        adapter.setDatos(lista);
        tvStatLineas.setText(lista.size() + " líneas en la red");
        tvStatKm.setText(String.format("%.0f km totales", totalKm));

        // Cambiamos visibilidad una vez cargado
        layoutLoading.setVisibility(View.GONE);
        layoutStats.setVisibility(View.VISIBLE);
        rv.setVisibility(View.VISIBLE);
    }

    private void filtrar(String query) {
        String q = query.toLowerCase().trim();
        List<Linea> resultado = new ArrayList<>();
        for (Linea l : todasLineas) {
            String texto = (l.numComercial + " " + l.nombreOficial).toLowerCase();
            if (q.isEmpty() || texto.contains(q)) {
                resultado.add(l);
            }
        }
        adapter.setDatos(resultado);
    }

    private void mostrarDetalle(Linea l) {
        String estaciones = (l.estaciones != null && !l.estaciones.isEmpty())
                ? String.join(", ", l.estaciones)
                : "Datos no disponibles";

        new AlertDialog.Builder(this)
                .setTitle("Línea " + l.numComercial.replace("L", ""))
                .setMessage("Estado: " + (l.existe ? "En servicio" : "Sin servicio") +
                        "\nLongitud: " + l.longitudKm + " km" +
                        "\nEstaciones: " + estaciones)
                .setPositiveButton("Ver créditos", (d, w) -> startActivity(new Intent(this, CreditosActivity.class)))
                .setNegativeButton("Cerrar", null)
                .show();
    }
}