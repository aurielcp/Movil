package com.example.metrocdmx;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class Linea {
    @SerializedName("num_comercial")
    public String numComercial;
    @SerializedName("nombre_oficial")
    public String nombreOficial;
    @SerializedName("longitud_km")
    public double longitudKm;
    @SerializedName("anio_inauguracion")
    public int anioInauguracion;
    @SerializedName("existe")
    public boolean existe;
    public List<String> estaciones;

    public Linea() {}

    // Constructor para cuando SÍ tienes estaciones
    public Linea(String num, String nom, double km, int anio, boolean ex, List<String> est) {
        this.numComercial = num;
        this.nombreOficial = nom;
        this.longitudKm = km;
        this.anioInauguracion = anio;
        this.existe = ex;
        this.estaciones = est;
    }

    // Constructor para cuando NO tienes estaciones (Evita el error que tenías)
    public Linea(String num, String nom, double km, int anio, boolean ex) {
        this.numComercial = num;
        this.nombreOficial = nom;
        this.longitudKm = km;
        this.anioInauguracion = anio;
        this.existe = ex;
        this.estaciones = new ArrayList<>();
    }
}