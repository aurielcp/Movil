package com.example.metrocdmx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MetroRepository {
    public static List<Linea> getDatosLocales() {
        List<Linea> lista = new ArrayList<>();

        lista.add(new Linea("L1", "Observatorio–Pantitlán", 18.5, 1969, true, Arrays.asList("Observatorio", "Tacubaya", "Juanacatlán", "Chapultepec", "Sevilla", "Insurgentes", "Cuauhtémoc", "Balderas", "Salto del Agua", "Isabel la Católica", "Pino Suárez", "Merced", "Candelaria", "San Lázaro", "Moctezuma", "Balbuena", "Boulevard Puerto Aéreo", "Gómez Farías", "Zaragoza", "Pantitlán")));
        lista.add(new Linea("L2", "Cuatro Caminos–Tasqueña", 23.8, 1970, true, Arrays.asList("Cuatro Caminos", "Panteones", "Tacuba", "Cuitláhuac", "Popotla", "Colegio Militar", "Normal", "San Cosme", "Revolución", "Hidalgo", "Bellas Artes", "Allende", "Zócalo", "Pino Suárez", "San Antonio Abad", "Chabacano", "Viaducto", "Xola", "Villa de Cortés", "Nativitas", "Portales", "Ermita", "General Anaya", "Tasqueña")));
        lista.add(new Linea("L3", "Indios Verdes–Universidad", 23.2, 1970, true, Arrays.asList("Indios Verdes", "Deportivo 18 de Marzo", "Potrero", "La Raza", "Tlatelolco", "Guerrero", "Hidalgo", "Juárez", "Balderas", "Niños Héroes", "Hospital General", "Centro Médico", "Etiopía", "Eugenia", "División del Norte", "Zapata", "Coyoacán", "Viveros", "Miguel Ángel de Quevedo", "Copilco", "Universidad")));
        lista.add(new Linea("L4", "Martín Carrera–Santa Anita", 10.9, 1981, true, Arrays.asList("Martín Carrera", "Talismán", "Bondojito", "Consulado", "Canal del Norte", "Morelos", "Candelaria", "Fray Servando", "Jamaica", "Santa Anita")));
        lista.add(new Linea("L5", "Politécnico–Pantitlán", 19.2, 1981, true, Arrays.asList("Politécnico", "Instituto del Petróleo", "Autobuses del Norte", "La Raza", "Misterios", "Valle Gómez", "Consulado", "Eduardo Molina", "Aragón", "Oceanía", "Terminal Aérea", "Hangares", "Pantitlán")));
        lista.add(new Linea("L6", "El Rosario–Martín Carrera", 17.4, 1983, true, Arrays.asList("El Rosario", "Tezozómoc", "UAM-Azcapotzalco", "Ferrería", "Norte 45", "Vallejo", "Instituto del Petróleo", "Lindavista", "Deportivo 18 de Marzo", "La Villa-Basílica", "Martín Carrera")));
        lista.add(new Linea("L7", "El Rosario–Barranca del Muerto", 19.8, 1984, true, Arrays.asList("El Rosario", "Aquiles Serdán", "Camarones", "Refinería", "Tacuba", "San Joaquín", "Polanco", "Auditorio", "Constituyentes", "Tacubaya", "San Pedro de los Pinos", "San Antonio", "Mixcoac", "Barranca del Muerto")));
        lista.add(new Linea("L8", "Garibaldi–Constitución de 1917", 19.3, 1994, true, Arrays.asList("Garibaldi", "Bellas Artes", "San Juan de Letrán", "Salto del Agua", "Doctores", "Obrera", "Chabacano", "La Viga", "Santa Anita", "Coyuya", "Iztacalco", "Apatlaco", "Aculco", "Escuadrón 201", "Atlalilco", "Iztapalapa", "Cerro de la Estrella", "UAM-I", "Constitución de 1917")));
        lista.add(new Linea("L9", "Tacubaya–Pantitlán", 14.7, 1987, true, Arrays.asList("Tacubaya", "Patriotismo", "Chilpancingo", "Centro Médico", "Lázaro Cárdenas", "Chabacano", "Jamaica", "Mixiuhca", "Velódromo", "Ciudad Deportiva", "Puebla", "Pantitlán")));
        lista.add(new Linea("LA", "Pantitlán–La Paz", 17.3, 1991, true, Arrays.asList("Pantitlán", "Agrícola Oriental", "Canal de San Juan", "Tepalcates", "Guelatao", "Peñón Viejo", "Acatitla", "La Paz")));
        lista.add(new Linea("LB", "Buenavista–Ciudad Azteca", 23.7, 1999, true, Arrays.asList("Buenavista", "Guerrero", "Garibaldi", "Lagunilla", "Tepito", "Morelos", "San Lázaro", "Ricardo Flores Magón", "Romero Rubio", "Oceanía", "Deportivo Oceanía", "Bosque de Aragón", "Villa de Aragón", "Nezahualcóyotl", "Impulsora", "Río de los Remedios", "Múzquiz", "Ecatepec", "Olimpica", "Plaza Aragón", "Ciudad Azteca")));
        lista.add(new Linea("L12", "Mixcoac–Tláhuac", 24.7, 2012, true, Arrays.asList("Mixcoac", "Insurgentes Sur", "Hospital 20 de Noviembre", "Zapata", "Parque de los Venados", "Eje Central", "Ermita", "Mexicaltzingo", "Atlalilco", "Culhuacán", "San Andrés Tomatlán", "Lomas Estrella", "Calle 11", "Periférico Oriente", "Tezonco", "Olivos", "Nopalera", "Zapotitlán", "Tlaltenco", "Tláhuac")));

        return lista;
    }
}