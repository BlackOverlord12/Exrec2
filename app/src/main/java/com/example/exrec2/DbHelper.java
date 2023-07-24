package com.example.exrec2;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "historial_imc.db";
    private static final int DATABASE_VERSION = 1;

    // Sentencia SQL para crear la tabla de historial
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + DbContract.HistorialEntry.TABLE_NAME + " (" +
                    DbContract.HistorialEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DbContract.HistorialEntry.COLUMN_ALTURA + " REAL NOT NULL," +
                    DbContract.HistorialEntry.COLUMN_PESO + " REAL NOT NULL," +
                    DbContract.HistorialEntry.COLUMN_IMC + " REAL NOT NULL)";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aquí puedes implementar la lógica para actualizar la base de datos si es necesario
        // En esta implementación simple, simplemente borramos y recreamos la tabla
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.HistorialEntry.TABLE_NAME);
        onCreate(db);
    }
}
