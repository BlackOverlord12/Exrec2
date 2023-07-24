package com.example.exrec2;


import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class MainActivity extends Activity {
    private EditText editTextAltura;
    private EditText editTextPeso;
    private TextView textViewIMC;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);

        editTextAltura = findViewById(R.id.editTextAltura);
        editTextPeso = findViewById(R.id.editTextPeso);
        textViewIMC = findViewById(R.id.textViewIMC);

        Button buttonCalcular = findViewById(R.id.buttonCalcular);
        buttonCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularIMC();

            }
        });

        Button buttonGuardar = findViewById(R.id.buttonGuardar);
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarIMC();
                editTextAltura.setText("");
                editTextPeso.setText("");
            }
        });

        Button buttonVerHistorial = findViewById(R.id.buttonVerHistorial);
        buttonVerHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verHistorial();
            }
        });

        Button buttonSalir = findViewById(R.id.buttonSalir);
        buttonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarConfirmacionSalida();
            }
        });
    }

    private void calcularIMC() {
        String alturaStr = editTextAltura.getText().toString().trim();
        String pesoStr = editTextPeso.getText().toString().trim();

        if (alturaStr.isEmpty() || pesoStr.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa la altura y el peso.", Toast.LENGTH_SHORT).show();
            return;
        }

        double altura = Double.parseDouble(alturaStr);
        double peso = Double.parseDouble(pesoStr);

        double imc = peso / ((altura / 100) * (altura / 100));
        textViewIMC.setText("IMC: " + String.format("%.2f", imc));
    }

    private void guardarIMC() {
        String alturaStr = editTextAltura.getText().toString().trim();
        String pesoStr = editTextPeso.getText().toString().trim();
        String imcStr = textViewIMC.getText().toString().trim().replace("IMC: ", "");

        if (alturaStr.isEmpty() || pesoStr.isEmpty() || imcStr.isEmpty()) {
            Toast.makeText(this, "Por favor, calcula el IMC antes de guardar.", Toast.LENGTH_SHORT).show();
            return;
        }

        double altura = Double.parseDouble(alturaStr);
        double peso = Double.parseDouble(pesoStr);
        double imc = Double.parseDouble(imcStr);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbContract.HistorialEntry.COLUMN_ALTURA, altura);
        values.put(DbContract.HistorialEntry.COLUMN_PESO, peso);
        values.put(DbContract.HistorialEntry.COLUMN_IMC, imc);

        long newRowId = db.insert(DbContract.HistorialEntry.TABLE_NAME, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "IMC guardado correctamente.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al guardar el IMC.", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
    private void mostrarConfirmacionSalida() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar salida");
        builder.setMessage("¿Estás seguro de que deseas salir?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Si el usuario confirma la salida, llamamos a finish() para cerrar la actividad
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Si el usuario cancela, simplemente cerramos el cuadro de diálogo
                dialog.dismiss();
            }
        });
        builder.show();
    }
    private void verHistorial() {
        Intent intent = new Intent(MainActivity.this, HistorialActivity.class);
        startActivity(intent);
    }
}
