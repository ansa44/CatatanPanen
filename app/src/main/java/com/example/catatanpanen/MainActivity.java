package com.example.catatanpanen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private PrefsManager prefsManager;
    private PanenDbHelper dbHelper;

    private TextView tvLahan, tvSatuan;
    private Spinner spTanaman;
    private EditText etJumlah;
    private ListView lvRiwayat;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefsManager = new PrefsManager(this);
        dbHelper = new PanenDbHelper(this);

        tvLahan = findViewById(R.id.tvLahan);
        tvSatuan = findViewById(R.id.tvSatuan);
        spTanaman = findViewById(R.id.spTanaman);
        etJumlah = findViewById(R.id.etJumlah);
        lvRiwayat = findViewById(R.id.lvRiwayat);
        Button btnSimpan = findViewById(R.id.btnSimpan);
        Button btnPengaturan = findViewById(R.id.btnPengaturan);

        spTanaman.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Singkong", "Jagung"}));

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        lvRiwayat.setAdapter(adapter);

        btnSimpan.setOnClickListener(v -> simpanPanen());
        btnPengaturan.setOnClickListener(v ->
                startActivity(new Intent(this, SettingsActivity.class)));
    }

    // Pengaturan dibaca lagi setiap layar tampil (termasuk setelah kembali dari Pengaturan)
    @Override
    protected void onResume() {
        super.onResume();

        AppCompatDelegate.setDefaultNightMode(prefsManager.isTemaGelap()
                ? AppCompatDelegate.MODE_NIGHT_YES
                : AppCompatDelegate.MODE_NIGHT_NO);

        tvLahan.setText("Halo, " + prefsManager.getNamaLahan() + "!");
        tvSatuan.setText("Satuan: " + prefsManager.getSatuan());
        muatRiwayat();
    }

    private void simpanPanen() {
        String teks = etJumlah.getText().toString().trim();
        if (teks.isEmpty()) {
            Toast.makeText(this, "Isi jumlah panen dulu", Toast.LENGTH_SHORT).show();
            return;
        }
        double jumlah = Double.parseDouble(teks);
        String tanggal = new SimpleDateFormat("dd MMM yyyy", new Locale("id", "ID"))
                .format(new Date());
        String tanaman = spTanaman.getSelectedItem().toString();

        dbHelper.tambah(tanggal, tanaman, jumlah, prefsManager.getSatuan());
        etJumlah.setText("");
        muatRiwayat();
        Toast.makeText(this, "Panen tersimpan", Toast.LENGTH_SHORT).show();
    }

    private void muatRiwayat() {
        adapter.clear();
        adapter.addAll(dbHelper.semuaRiwayat());
        adapter.notifyDataSetChanged();
    }
}