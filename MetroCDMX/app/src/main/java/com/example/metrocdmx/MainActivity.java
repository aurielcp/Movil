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
import java.util.Arrays;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private LineaAdapter adapter;
    private List<Linea> todasLineas = new ArrayList<>();
    private View layoutLoading, layoutStats;
    private TextView tvError, tvStatLineas, tvStatKm;
    private EditText etBuscar;

    // Orden oficial de las 12 líneas
    private static final List<String> ORDEN = Arrays.asList(
            "L1","L2","L3","L4","L5","L6","L7","L8","L9","LA","LB","L12"
    );

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
            public void afterTextChanged(Editable s) {}
            public void onTextChanged(CharSequence s, int a, int b, int c) {
                filtrar(s.toString());
            }
        });

        findViewById(R.id.btnActualizar).setOnClickListener(v -> consultarApi());
        consultarApi();
    }

    private void consultarApi() {
        layoutLoading.setVisibility(View.VISIBLE);
        layoutStats.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://apimetro.dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MetroApiService service = retrofit.create(MetroApiService.class);

        service.getLineas().enqueue(new Callback<List<Linea>>() {
            @Override
            public void onResponse(Call<List<Linea>> call, Response<List<Linea>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Ordenar según el orden oficial de líneas
                    List<Linea> ordenadas = new ArrayList<>();
                    List<Linea> cuerpo = response.body();
                    for (String key : ORDEN) {
                        for (Linea l : cuerpo) {
                            if (key.equals(l.numComercial)) {
                                ordenadas.add(l);
                                break;
                            }
                        }
                    }
                    // Si la API devolvió líneas que no están en ORDEN, las agregamos al final
                    for (Linea l : cuerpo) {
                        if (!ORDEN.contains(l.numComercial)) {
                            ordenadas.add(l);
                        }
                    }
                    todasLineas = ordenadas;
                    actualizarUI(todasLineas);
                } else {
                    mostrarError("Respuesta inválida del servidor (código " + response.code() + ")");
                    cargarFallback();
                }
            }

            @Override
            public void onFailure(Call<List<Linea>> call, Throwable t) {
                mostrarError("Sin conexión: " + t.getMessage());
                cargarFallback();
            }
        });
    }

    private void actualizarUI(List<Linea> lista) {
        double totalKm = 0;
        for (Linea l : lista) totalKm += l.longitudKm;

        adapter.setDatos(lista);
        tvStatLineas.setText(lista.size() + " líneas en la red");
        tvStatKm.setText(String.format("%.0f km totales", totalKm));

        layoutLoading.setVisibility(View.GONE);
        layoutStats.setVisibility(View.VISIBLE);
        rv.setVisibility(View.VISIBLE);
    }

    private void filtrar(String query) {
        if (todasLineas.isEmpty()) return;
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
        String nombre = nombreLegible(l.numComercial);
        String estado = l.existe ? "✅ En servicio" : "🚫 Sin servicio";
        String km     = l.longitudKm > 0
                ? String.format("%.1f km", l.longitudKm)
                : "No disponible";
        String anio   = l.anioInauguracion > 0
                ? String.valueOf(l.anioInauguracion)
                : "No disponible";

        new AlertDialog.Builder(this)
                .setTitle(nombre)
                .setMessage(
                        "Estado: "        + estado + "\n" +
                                "Longitud: "      + km     + "\n" +
                                "Inauguración: "  + anio
                )
                .setPositiveButton("Ver créditos",
                        (d, w) -> startActivity(new Intent(this, CreditosActivity.class)))
                .setNegativeButton("Cerrar", null)
                .show();
    }

    private void mostrarError(String msg) {
        tvError.setText("⚠ " + msg);
        tvError.setVisibility(View.VISIBLE);
        layoutLoading.setVisibility(View.GONE);
    }

    private void cargarFallback() {
        todasLineas = new ArrayList<>(Arrays.asList(
                new Linea("L1",  "Observatorio–Pantitlán",          18.5, 1969, true),
                new Linea("L2",  "Cuatro Caminos–Tasqueña",         23.8, 1970, true),
                new Linea("L3",  "Indios Verdes–Universidad",       23.2, 1970, true),
                new Linea("L4",  "Martín Carrera–Santa Anita",      10.9, 1981, true),
                new Linea("L5",  "Politécnico–Pantitlán",           19.2, 1981, true),
                new Linea("L6",  "El Rosario–Martín Carrera",       17.4, 1983, true),
                new Linea("L7",  "El Rosario–Barranca del Muerto",  19.8, 1984, true),
                new Linea("L8",  "Garibaldi–Constitución de 1917",  19.3, 1994, true),
                new Linea("L9",  "Tacubaya–Pantitlán",              14.7, 1987, true),
                new Linea("LA",  "Pantitlán–La Paz",                17.3, 1991, true),
                new Linea("LB",  "Buenavista–Ciudad Azteca",        23.7, 1999, true),
                new Linea("L12", "Mixcoac–Tláhuac",                 24.7, 2012, true)
        ));
        actualizarUI(todasLineas);
    }

    private String nombreLegible(String num) {
        switch (num) {
            case "LA":  return "Línea A";
            case "LB":  return "Línea B";
            case "L12": return "Línea 12";
            default:    return "Línea " + num.replace("L", "");
        }
    }
}