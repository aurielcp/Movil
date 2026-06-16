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
            // Creamos una variable auxiliar para verificar si la estación está en esta línea
            boolean contieneEstacion = false;
            if (l.estaciones != null) {
                for (String estacion : l.estaciones) {
                    if (estacion.toLowerCase().contains(q)) {
                        contieneEstacion = true;
                        break; // Ya encontramos la estación, no necesitamos seguir buscando en esta línea
                    }
                }
            }

            // El filtro ahora incluye: Nombre oficial, Número o si contiene la estación
            String textoLinea = (l.numComercial + " " + l.nombreOficial).toLowerCase();

            if (q.isEmpty() || textoLinea.contains(q) || contieneEstacion) {
                resultado.add(l);
            }
        }
        adapter.setDatos(resultado);
    }

    private void mostrarDetalle(Linea l) {
        if (l.estaciones == null || l.estaciones.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Línea " + l.numComercial)
                    .setMessage("No hay datos de estaciones.")
                    .setNegativeButton("Cerrar", null)
                    .show();
            return;
        }

        // Creamos un ListView dinámico para el AlertDialog
        ListView listView = new ListView(this);

        // Usamos el layout 'item_estacion' que acabamos de crear
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.item_estacion, R.id.tvNombreEstacion, l.estaciones);

        listView.setAdapter(adapter);

        new AlertDialog.Builder(this)
                .setTitle("Estaciones - " + l.numComercial.replace("L", ""))
                .setView(listView)
                // Cambiado el botón positivo a "Cerrar" (antes Créditos)
                .setPositiveButton("Cerrar", (d, w) -> {
                    // Aquí, si necesitas que el botón de cerrar también abra los créditos,
                    // mantén la llamada a startActivity, o simplemente déjalo vacío para cerrar.
                    startActivity(new Intent(this, CreditosActivity.class));
                })
                // Cambiado el botón negativo a "Regresar" (antes Cerrar)
                .setNegativeButton("Regresar", null)
                .show();
    }
}