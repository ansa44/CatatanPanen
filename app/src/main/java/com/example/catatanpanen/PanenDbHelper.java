package com.example.catatanpanen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Database SQLite untuk riwayat panen (data banyak baris, perlu diurutkan/query).
 */
public class PanenDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "panen.db";
    private static final int DB_VERSION = 1;

    public PanenDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE panen ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "tanggal TEXT, "
                + "tanaman TEXT, "
                + "jumlah REAL, "
                + "satuan TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS panen");
        onCreate(db);
    }

    public void tambah(String tanggal, String tanaman, double jumlah, String satuan) {
        ContentValues cv = new ContentValues();
        cv.put("tanggal", tanggal);
        cv.put("tanaman", tanaman);
        cv.put("jumlah", jumlah);
        cv.put("satuan", satuan);
        getWritableDatabase().insert("panen", null, cv);
    }

    public List<String> semuaRiwayat() {
        List<String> hasil = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery(
                "SELECT tanggal, tanaman, jumlah, satuan FROM panen ORDER BY id DESC", null);
        while (c.moveToNext()) {
            hasil.add(c.getString(0) + "  •  " + c.getString(1)
                    + "  •  " + c.getDouble(2) + " " + c.getString(3));
        }
        c.close();
        return hasil;
    }
}