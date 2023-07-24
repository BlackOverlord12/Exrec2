package com.example.exrec2;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class HistorialCursorAdapter extends CursorAdapter {
    public HistorialCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_historial, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textViewAltura = view.findViewById(R.id.textViewAltura);
        TextView textViewPeso = view.findViewById(R.id.textViewPeso);
        TextView textViewIMC = view.findViewById(R.id.textViewIMC);

        double altura = cursor.getDouble(cursor.getColumnIndexOrThrow(DbContract.HistorialEntry.COLUMN_ALTURA));
        double peso = cursor.getDouble(cursor.getColumnIndexOrThrow(DbContract.HistorialEntry.COLUMN_PESO));
        double imc = cursor.getDouble(cursor.getColumnIndexOrThrow(DbContract.HistorialEntry.COLUMN_IMC));

        textViewAltura.setText("Altura: " + String.format("%.2f", altura) + " cm");
        textViewPeso.setText("Peso: " + String.format("%.2f", peso) + " kg");
        textViewIMC.setText("IMC: " + String.format("%.2f", imc));
    }
}
