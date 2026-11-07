package com.example.catatanpanen;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Pembungkus SharedPreferences.
 * Menyimpan pengaturan kecil berbentuk key-value: nama lahan, satuan, tema gelap.
 */
public class PrefsManager {

    private static final String PREF_NAME = "panen_prefs";   // file: shared_prefs/panen_prefs.xml
    private static final String KEY_NAMA_LAHAN = "nama_lahan";
    private static final String KEY_SATUAN = "satuan";
    private static final String KEY_TEMA_GELAP = "tema_gelap";

    private final SharedPreferences prefs;

    public PrefsManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setNamaLahan(String nama) {
        prefs.edit().putString(KEY_NAMA_LAHAN, nama).apply();
    }

    public String getNamaLahan() {
        return prefs.getString(KEY_NAMA_LAHAN, "Kebun Saya");
    }

    public void setSatuan(String satuan) {
        prefs.edit().putString(KEY_SATUAN, satuan).apply();
    }

    public String getSatuan() {
        return prefs.getString(KEY_SATUAN, "kg");
    }

    public void setTemaGelap(boolean gelap) {
        prefs.edit().putBoolean(KEY_TEMA_GELAP, gelap).apply();
    }

    public boolean isTemaGelap() {
        return prefs.getBoolean(KEY_TEMA_GELAP, false);
    }
}