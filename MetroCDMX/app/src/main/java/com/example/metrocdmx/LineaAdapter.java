package com.example.metrocdmx;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LineaAdapter extends RecyclerView.Adapter<LineaAdapter.ViewHolder> {

    public interface OnLineaClickListener {
        void onClick(Linea linea);
    }

    private List<Linea> datos;
    private OnLineaClickListener listener;

    private static final String[] COLORES = {
            "#E9068C","#0C4FAB","#A7A823","#74C4E2","#FAAE28",
            "#C6A84A","#F18A1D","#00AB68","#6B3F22","#8C1464",
            "#BDBCBC","#C8961E"
    };
    private static final String[] KEYS = {
            "L1","L2","L3","L4","L5","L6","L7","L8","L9","LA","LB","L12"
    };

    public LineaAdapter(List<Linea> datos, OnLineaClickListener listener) {
        this.datos = datos;
        this.listener = listener;
    }

    public void setDatos(List<Linea> nuevos) {
        this.datos = nuevos;
        notifyDataSetChanged();
    }

    private String getColor(String numComercial) {
        for (int i = 0; i < KEYS.length; i++) {
            if (KEYS[i].equals(numComercial)) return COLORES[i];
        }
        return "#888888";
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_linea, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        Linea l = datos.get(position);
        String color = getColor(l.numComercial);
        int colorInt = Color.parseColor(color);

        // Badge circular con color de línea
        GradientDrawable circle = new GradientDrawable();
        circle.setShape(GradientDrawable.OVAL);
        circle.setColor(colorInt);
        h.tvBadge.setBackground(circle);
        h.tvBadge.setText(l.numComercial.replace("L", ""));

        // Barra lateral
        h.barraColor.setBackgroundColor(colorInt);

        // Nombre y km
        h.tvNombre.setText(nombreLegible(l.numComercial));
        h.tvKm.setText(l.longitudKm > 0
                ? String.format("%.1f km · Desde %d", l.longitudKm, l.anioInauguracion)
                : "Datos no disponibles");

        // Estado
        String estadoTexto;
        int estadoBg, estadoFg;
        if (!l.existe) {
            estadoTexto = "Sin servicio";
            estadoBg = Color.parseColor("#FFEBEE");
            estadoFg = Color.parseColor("#C62828");
        } else if (l.longitudKm < 10 && l.longitudKm > 0) {
            estadoTexto = "Parcial";
            estadoBg = Color.parseColor("#FFF8E1");
            estadoFg = Color.parseColor("#F57F17");
        } else {
            estadoTexto = "En servicio";
            estadoBg = Color.parseColor("#E8F5E9");
            estadoFg = Color.parseColor("#2E7D32");
        }

        GradientDrawable pill = new GradientDrawable();
        pill.setShape(GradientDrawable.RECTANGLE);
        pill.setCornerRadius(50);
        pill.setColor(estadoBg);
        h.tvEstado.setBackground(pill);
        h.tvEstado.setText(estadoTexto);
        h.tvEstado.setTextColor(estadoFg);

        h.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onClick(l);
        });
    }

    private String nombreLegible(String num) {
        switch (num) {
            case "LA": return "Línea A";
            case "LB": return "Línea B";
            default: return "Línea " + num.replace("L", "");
        }
    }

    @Override
    public int getItemCount() { return datos.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBadge, tvNombre, tvKm, tvEstado;
        View barraColor;

        public ViewHolder(View v) {
            super(v);
            tvBadge = v.findViewById(R.id.tvBadge);
            barraColor = v.findViewById(R.id.barraColor);
            tvNombre = v.findViewById(R.id.tvNombreLinea);
            tvKm = v.findViewById(R.id.tvKm);
            tvEstado = v.findViewById(R.id.tvEstadoLinea);
        }
    }
}