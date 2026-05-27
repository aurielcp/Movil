package com.example.androidya;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.io.*;

public class MainActivity extends AppCompatActivity {
    private EditText etDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etDatos = findViewById(R.id.etDatos);
    }

    public void grabarInterno(View v) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(openFileOutput("interno.txt", MODE_PRIVATE));
            osw.write(etDatos.getText().toString());
            osw.close();
            Toast.makeText(this, "Guardado en Interno", Toast.LENGTH_SHORT).show();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void grabarSD(View v) {
        // En versiones modernas usar getExternalFilesDir() para evitar permisos complicados
        File tarjeta = getExternalFilesDir(null);
        File f = new File(tarjeta, "datos_sd.txt");
        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(f));
            osw.write(etDatos.getText().toString());
            osw.close();
            Toast.makeText(this, "Guardado en SD", Toast.LENGTH_SHORT).show();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void grabarSQLite(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("codigo", 1);
        registro.put("descripcion", etDatos.getText().toString());
        registro.put("precio", 0.0);
        bd.insert("articulos", null, registro);
        bd.close();
        Toast.makeText(this, "Guardado en SQLite", Toast.LENGTH_SHORT).show();
    }
}