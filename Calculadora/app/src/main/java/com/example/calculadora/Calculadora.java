<<<<<<< HEAD
package com.example.calculadora;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Calculadora extends Activity implements View.OnClickListener {
    Button[] btnDigitos = new Button[10];
    Button btnSum, btnRes, btnMul, btnDiv, btnPunto, btnIgual;
    EditText pantalla;
    double op1, op2;
    String tipoOperacion = "";
    boolean pintarPunto = true;

    protected void onCreate(Bundle b) {
        super.onCreate(b);
        LinearLayout panelPrincipal = new LinearLayout(this);
        panelPrincipal.setOrientation(LinearLayout.VERTICAL);

        pantalla = new EditText(this);
        pantalla.setTextSize(40);
        panelPrincipal.addView(pantalla);

        // Crear botones de operaciones
        btnSum = crearBoton("+");
        btnRes = crearBoton("-");
        btnMul = crearBoton("*");
        btnDiv = crearBoton("/");
        btnPunto = crearBoton(".");
        btnIgual = crearBoton("=");

        LinearLayout panelBotones = new LinearLayout(this);
        panelBotones.setOrientation(LinearLayout.VERTICAL);

        // Agregar botones (simplificado para el ejemplo)
        for (int i = 0; i <= 9; i++) {
            btnDigitos[i] = crearBoton(String.valueOf(i));
            panelBotones.addView(btnDigitos[i]);
        }
        panelBotones.addView(btnSum);
        panelBotones.addView(btnRes);
        panelBotones.addView(btnMul);
        panelBotones.addView(btnDiv);
        panelBotones.addView(btnPunto);
        panelBotones.addView(btnIgual);

        panelPrincipal.addView(panelBotones);
        setContentView(panelPrincipal);
    }

    private Button crearBoton(String texto) {
        Button btn = new Button(this);
        btn.setText(texto);
        btn.setOnClickListener(this);
        return btn;
    }

    public void onClick(View v) {
        String texto = ((Button) v).getText().toString();

        if (texto.matches("[0-9]")) {
            pantalla.setText(pantalla.getText() + texto);
        } else if (texto.equals(".")) {
            if (pintarPunto) {
                pantalla.setText(pantalla.getText() + ".");
                pintarPunto = false;
            }
        } else if (texto.equals("=")) {
            op2 = Double.parseDouble(pantalla.getText().toString());
            double res = 0;
            switch (tipoOperacion) {
                case "+": res = op1 + op2; break;
                case "-": res = op1 - op2; break;
                case "*": res = op1 * op2; break;
                case "/": res = (op2 != 0) ? op1 / op2 : 0; break;
            }
            pantalla.setText(String.valueOf(res));
            pintarPunto = true;
        } else {
            // Es una operación (+, -, *, /)
            op1 = Double.parseDouble(pantalla.getText().toString());
            tipoOperacion = texto;
            pantalla.setText("");
            pintarPunto = true;
        }
    }
=======
package com.example.calculadora;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Calculadora extends Activity implements View.OnClickListener {
    Button[] btnDigitos = new Button[10];
    Button btnSum, btnRes, btnMul, btnDiv, btnPunto, btnIgual;
    EditText pantalla;
    double op1, op2;
    String tipoOperacion = "";
    boolean pintarPunto = true;

    protected void onCreate(Bundle b) {
        super.onCreate(b);
        LinearLayout panelPrincipal = new LinearLayout(this);
        panelPrincipal.setOrientation(LinearLayout.VERTICAL);

        pantalla = new EditText(this);
        pantalla.setTextSize(40);
        panelPrincipal.addView(pantalla);

        // Crear botones de operaciones
        btnSum = crearBoton("+");
        btnRes = crearBoton("-");
        btnMul = crearBoton("*");
        btnDiv = crearBoton("/");
        btnPunto = crearBoton(".");
        btnIgual = crearBoton("=");

        LinearLayout panelBotones = new LinearLayout(this);
        panelBotones.setOrientation(LinearLayout.VERTICAL);

        // Agregar botones (simplificado para el ejemplo)
        for (int i = 0; i <= 9; i++) {
            btnDigitos[i] = crearBoton(String.valueOf(i));
            panelBotones.addView(btnDigitos[i]);
        }
        panelBotones.addView(btnSum);
        panelBotones.addView(btnRes);
        panelBotones.addView(btnMul);
        panelBotones.addView(btnDiv);
        panelBotones.addView(btnPunto);
        panelBotones.addView(btnIgual);

        panelPrincipal.addView(panelBotones);
        setContentView(panelPrincipal);
    }

    private Button crearBoton(String texto) {
        Button btn = new Button(this);
        btn.setText(texto);
        btn.setOnClickListener(this);
        return btn;
    }

    public void onClick(View v) {
        String texto = ((Button) v).getText().toString();

        if (texto.matches("[0-9]")) {
            pantalla.setText(pantalla.getText() + texto);
        } else if (texto.equals(".")) {
            if (pintarPunto) {
                pantalla.setText(pantalla.getText() + ".");
                pintarPunto = false;
            }
        } else if (texto.equals("=")) {
            op2 = Double.parseDouble(pantalla.getText().toString());
            double res = 0;
            switch (tipoOperacion) {
                case "+": res = op1 + op2; break;
                case "-": res = op1 - op2; break;
                case "*": res = op1 * op2; break;
                case "/": res = (op2 != 0) ? op1 / op2 : 0; break;
            }
            pantalla.setText(String.valueOf(res));
            pintarPunto = true;
        } else {
            // Es una operación (+, -, *, /)
            op1 = Double.parseDouble(pantalla.getText().toString());
            tipoOperacion = texto;
            pantalla.setText("");
            pintarPunto = true;
        }
    }
>>>>>>> df0bef2bfb30a6c88d60c03407a963903b50a08c
}