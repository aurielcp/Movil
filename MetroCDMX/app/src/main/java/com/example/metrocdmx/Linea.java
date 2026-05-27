package com.example.metrocdmx;

import com.google.gson.annotations.SerializedName;

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

    // Constructor para el fallback
    public Linea(String numComercial, String nombreOficial,
                 double longitudKm, int anioInauguracion, boolean existe) {
        this.numComercial     = numComercial;
        this.nombreOficial    = nombreOficial;
        this.longitudKm       = longitudKm;
        this.anioInauguracion = anioInauguracion;
        this.existe           = existe;
    }
}