package com.example.catatanpanen;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class SettingsActivity extends AppCompatActivity {

    private PrefsManager prefsManager;
    private EditText etNamaLahan;
    private RadioGroup rgSatuan;
    private RadioButton rbKg, rbKuintal;
    private SwitchCompat swTema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        prefsManager = new PrefsManager(this);

        etNamaLahan = findViewById(R.id.etNamaLahan);
        rgSatuan = findViewById(R.id.rgSatuan);
        rbKg = findViewById(R.id.rbKg);
        rbKuintal = findViewById(R.id.rbKuintal);
        swTema = findViewById(R.id.swTema);
        Button btnSimpan = findViewById(R.id.btnSimpanPengaturan);

        // Tampilkan nilai yang sudah tersimpan
        etNamaLahan.setText(prefsManager.getNamaLahan());
        if (prefsManager.getSatuan().equals("kuintal")) {
            rbKuintal.setChecked(true);
        } else {
            rbKg.setChecked(true);
        }
        swTema.setChecked(prefsManager.isTemaGelap());

        btnSimpan.setOnClickListener(v -> {
            String nama = etNamaLahan.getText().toString().trim();
            if (nama.isEmpty()) {
                Toast.makeText(this, "Nama lahan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }
            prefsManager.setNamaLahan(nama);                                   // apply()
            prefsManager.setSatuan(rgSatuan.getCheckedRadioButtonId() == R.id.rbKuintal
                    ? "kuintal" : "kg");                                       // apply()
            prefsManager.setTemaGelap(swTema.isChecked());                     // apply()
            Toast.makeText(this, "Pengaturan disimpan", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}