package com.example.exrec2;


import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HistorialActivity extends Activity {
    private ListView listViewHistorial;
    private TextView textViewPromedioIMC;
    private DbHelper dbHelper;
    private HistorialCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        dbHelper = new DbHelper(this);

        listViewHistorial = findViewById(R.id.listViewHistorial);
        textViewPromedioIMC = findViewById(R.id.textViewPromedioIMC);

        Button buttonRegresar = findViewById(R.id.buttonRegresar);
        buttonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button buttonBorrarHistorial = findViewById(R.id.buttonBorrarHistorial);
        buttonBorrarHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarHistorial();
            }
        });

        mostrarHistorial();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Asegúrate de cerrar el cursor y la conexión de la base de datos al finalizar la actividad
        if (cursorAdapter != null) {
            cursorAdapter.swapCursor(null);
            cursorAdapter = null;
        }
        dbHelper.close();
    }

    private void borrarHistorial() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted = db.delete(DbContract.HistorialEntry.TABLE_NAME, null, null);
        db.close();

        if (rowsDeleted > 0) {
            Toast.makeText(this, "Historial borrado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No se pudo borrar el historial", Toast.LENGTH_SHORT).show();
        }

        mostrarHistorial();
    }

    private void mostrarHistorial() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DbContract.HistorialEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        // Asegúrate de cerrar el cursor anterior antes de asignar uno nuevo
        if (cursorAdapter != null) {
            cursorAdapter.swapCursor(null);
            cursorAdapter = null;
        }

        cursorAdapter = new HistorialCursorAdapter(this, cursor);
        listViewHistorial.setAdapter(cursorAdapter);

        double promedioIMC = calcularPromedioIMC(cursor);
        textViewPromedioIMC.setText("Promedio IMC: " + String.format("%.2f", promedioIMC));
    }

    private double calcularPromedioIMC(Cursor cursor) {
        int totalRegistros = cursor.getCount();
        double sumatoriaIMC = 0.0;

        if (totalRegistros > 0) {
            cursor.moveToFirst();
            do {
                double imc = cursor.getDouble(cursor.getColumnIndexOrThrow(DbContract.HistorialEntry.COLUMN_IMC));
                sumatoriaIMC += imc;
            } while (cursor.moveToNext());
        }

        double promedioIMC = sumatoriaIMC / totalRegistros;
        return promedioIMC;
    }
}

