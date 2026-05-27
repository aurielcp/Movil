package com.example.metrocdmx;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private LineaAdapter adapter;
    private List<Linea> todasLineas = new ArrayList<>();
    private View layoutLoading, layoutStats;
    private TextView tvError, tvStatLineas, tvStatKm;
    private EditText etBuscar;

    private static final String API_URL =
            "https://apimetro.dev/movilidad/METRO/linea";

    @Override
    protected void onCreate(Bundle si) {
        super.onCreate(si);
        setContentView(R.layout.activity_main);

        rv            = findViewById(R.id.miRecyclerView);
        layoutLoading = findViewById(R.id.layoutLoading);
        layoutStats   = findViewById(R.id.layoutStats);
        tvError       = findViewById(R.id.tvError);
        tvStatLineas  = findViewById(R.id.tvStatLineas);
        tvStatKm      = findViewById(R.id.tvStatKm);
        etBuscar      = findViewById(R.id.etBuscar);

        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LineaAdapter(new ArrayList<>(), this::mostrarDetalle);
        rv.setAdapter(adapter);

        // Búsqueda en tiempo real
        etBuscar.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int a, int b, int c) {}
            public void onTextChanged(CharSequence s, int a, int b, int c) { filtrar(s.toString()); }
            public void afterTextChanged(Editable s) {}
        });

        findViewById(R.id.btnActualizar).setOnClickListener(v -> consultarApi());

        consultarApi();
    }

    private void filtrar(String query) {
        List<Linea> resultado = new ArrayList<>();
        String q = query.toLowerCase().trim();
        for (Linea l : todasLineas) {
            String nombre = l.numComercial + " " + l.nombreOficial;
            if (q.isEmpty() || nombre.toLowerCase().contains(q)) {
                resultado.add(l);
            }
        }
        adapter.setDatos(resultado);
    }

    private void consultarApi() {
        layoutLoading.setVisibility(View.VISIBLE);
        layoutStats.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);

        new Thread(() -> {
            try {
                URL url = new URL(API_URL);
                Scanner scanner = new Scanner(url.openStream());
                String respuesta = scanner.useDelimiter("\\A").next();
                scanner.close();

                JSONArray arr = new JSONArray(respuesta);
                List<Linea> lista = new ArrayList<>();
                double totalKm = 0;

                // Orden oficial de las líneas
                String[] orden = {"L1","L2","L3","L4","L5","L6","L7","L8","L9","LA","LB","L12"};

                for (String key : orden) {
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = arr.getJSONObject(i);
                        String num = obj.optString("num_comercial", "");
                        if (!num.equals(key)) continue;

                        double km = obj.optDouble("longitud_km", 0);
                        int anio = obj.optInt("anio_inauguracion", 0);
                        boolean existe = obj.optBoolean("existe", true);
                        String nombre = obj.optString("nombre_oficial", num);
                        lista.add(new Linea(num, nombre, km, anio, existe));
                        totalKm += km;
                        break;
                    }
                }

                todasLineas = lista;
                final double kmFinal = totalKm;
                final int total = lista.size();

                new Handler(Looper.getMainLooper()).post(() -> {
                    adapter.setDatos(new ArrayList<>(todasLineas));
                    tvStatLineas.setText(total + " líneas en la red");
                    tvStatKm.setText(String.format("%.0f km totales", kmFinal));
                    layoutLoading.setVisibility(View.GONE);
                    layoutStats.setVisibility(View.VISIBLE);
                    rv.setVisibility(View.VISIBLE);
                });

            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    layoutLoading.setVisibility(View.GONE);
                    tvError.setVisibility(View.VISIBLE);
                    tvError.setText("⚠ " + getString(R.string.error_red));
                    cargarFallback();
                });
            }
        }).start();
    }

    private void mostrarDetalle(Linea l) {
        String nombre = "Línea " + l.numComercial.replace("L", "");
        if (l.numComercial.equals("LA")) nombre = "Línea A";
        if (l.numComercial.equals("LB")) nombre = "Línea B";

        String estado = l.existe ? "✅ En servicio" : "🚫 Sin servicio";
        String km = l.longitudKm > 0 ? String.format("%.1f km", l.longitudKm) : "—";
        String anio = l.anioInauguracion > 0 ? String.valueOf(l.anioInauguracion) : "—";

        String msg = "Estado: " + estado +
                "\nLongitud: " + km +
                "\nInauguración: " + anio;

        new AlertDialog.Builder(this)
                .setTitle(nombre)
                .setMessage(msg)
                .setPositiveButton("Ver créditos", (d, w) -> {
                    startActivity(new Intent(this, CreditosActivity.class));
                })
                .setNegativeButton("Cerrar", null)
                .show();
    }

    private void cargarFallback() {
        todasLineas = new ArrayList<>();
        Object[][] datos = {
                {"L1", "Observatorio–Pantitlán", 18.5, 1969},
                {"L2", "Cuatro Caminos–Tasqueña", 23.8, 1970},
                {"L3", "Indios Verdes–Universidad", 23.2, 1970},
                {"L4", "Martín Carrera–Santa Anita", 10.9, 1981},
                {"L5", "Politécnico–Pantitlán", 19.2, 1981},
                {"L6", "El Rosario–Martín Carrera", 17.4, 1983},
                {"L7", "El Rosario–Barranca del Muerto", 19.8, 1984},
                {"L8", "Garibaldi–Constitución de 1917", 19.3, 1994},
                {"L9", "Tacubaya–Pantitlán", 14.7, 1987},
                {"LA", "Pantitlán–La Paz", 17.3, 1991},
                {"LB", "Buenavista–Ciudad Azteca", 23.7, 1999},
                {"L12","Mixcoac–Tláhuac", 24.7, 2012},
        };
        for (Object[] d : datos) {
            todasLineas.add(new Linea(
                    (String) d[0], (String) d[1],
                    (double) d[2], (int) d[3], true
            ));
        }
        adapter.setDatos(new ArrayList<>(todasLineas));
        layoutStats.setVisibility(View.VISIBLE);
        rv.setVisibility(View.VISIBLE);
        tvStatLineas.setText("12 líneas en la red");
        tvStatKm.setText("232 km totales (aprox.)");
    }
}