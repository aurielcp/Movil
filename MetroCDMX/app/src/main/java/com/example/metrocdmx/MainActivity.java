package com.example.metrocdmx;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;

public class MainActivity extends Activity {
    private RecyclerView rv;
    private LineaAdapter adapter;
    private List<String> listaEstados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle si) {
        super.onCreate(si);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.miRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar con datos vacíos o carga
        adapter = new LineaAdapter(listaEstados);
        rv.setAdapter(adapter);

        consultarApi();
    }

    private void consultarApi() {
        new Thread(() -> {
            try {
                // TU URL RAW DE GITHUB GIST AQUÍ
                URL url = new URL("https://gist.githubusercontent.com/aurielcp/1d706f9195853d7bde81a61a1ab7b209/raw/2f3b225631429f89e427235ec247c44c95911ee6/gistfile1.txt");
                Scanner scanner = new Scanner(url.openStream());
                String respuesta = scanner.useDelimiter("\\A").next();

                // Parseo básico del JSON (asumiendo un arreglo simple ["Estado1", "Estado2"...])
                JSONArray jsonArray = new JSONArray(respuesta);
                listaEstados.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    listaEstados.add(jsonArray.getString(i));
                }

                new Handler(Looper.getMainLooper()).post(() -> {
                    adapter.notifyDataSetChanged();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}