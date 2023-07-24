package com.example.exrec2;

import android.provider.BaseColumns;

public final class DbContract {
    private DbContract() {} // Constructor privado para evitar que se cree una instancia de la clase

    public static class HistorialEntry implements BaseColumns {
        public static final String TABLE_NAME = "historial";
        public static final String COLUMN_ALTURA = "altura";
        public static final String COLUMN_PESO = "peso";
        public static final String COLUMN_IMC = "imc";
    }
}
