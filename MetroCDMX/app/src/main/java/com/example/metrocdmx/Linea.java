package com.example.metrocdmx;

public class Linea {
    public String numComercial;
    public String nombreOficial;
    public double longitudKm;
    public int anioInauguracion;
    public boolean existe;

    public Linea(String numComercial, String nombreOficial,
                 double longitudKm, int anioInauguracion, boolean existe) {
        this.numComercial = numComercial;
        this.nombreOficial = nombreOficial;
        this.longitudKm = longitudKm;
        this.anioInauguracion = anioInauguracion;
        this.existe = existe;
    }
}