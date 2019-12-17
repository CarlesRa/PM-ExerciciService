package com.carlesramos.pm_exerciciservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.carlesramos.pm_exerciciservice.adapters.PrimoAdapter;
import com.carlesramos.pm_exerciciservice.services.NumerosPrimosService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvListadoPrimos;
    private Button btConsultar;
    private TextView tvCantidadPrimos;
    private ArrayList<Integer> numerosPrimos;
    private NumerosPrimosService myService;
    private int cantidadPrimos;
    private Button btSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvListadoPrimos = findViewById(R.id.rvListaPrimos);
        btConsultar = findViewById(R.id.btConsultar);
        tvCantidadPrimos = findViewById(R.id.tvCantidadPrimos);
        myService = null;
        btSave = findViewById(R.id.btSave);

        Intent intent = new Intent(this, NumerosPrimosService.class);
        startService(intent);
        IntentFilter filter = new IntentFilter();
        filter.addAction(NumerosPrimosService.DAME_PRIMOS);
        PrimosReciver primosReciver = new PrimosReciver();
        LocalBroadcastManager.getInstance(this).registerReceiver(primosReciver, filter);

        btConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCantidadPrimos.setText(String.valueOf(cantidadPrimos));
                rvListadoPrimos.setAdapter(new PrimoAdapter(numerosPrimos, MainActivity.this));
                rvListadoPrimos.setHasFixedSize(true);
                rvListadoPrimos.setLayoutManager(new GridLayoutManager(MainActivity.this,5));
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File directorio = new File(getExternalFilesDir(null), "NumerosPrimos");

                if (!directorio.exists()){
                    directorio.mkdir();
                }

                File file = new File(directorio, "listaPrimos.txt");

                try {

                    FileOutputStream outputStream = new FileOutputStream(file);
                    OutputStreamWriter writer = new OutputStreamWriter(outputStream);

                    for (int i=0; i<numerosPrimos.size(); i++){
                        writer.write((numerosPrimos.get(i).toString() + "\n"));
                    }

                    writer.close();
                    outputStream.close();
                    Toast.makeText(MainActivity.this, "Data has been saved!!", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    Toast.makeText(MainActivity.this, "Error saving data...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public class PrimosReciver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            cantidadPrimos = intent.getIntExtra("cant",0);
            numerosPrimos = intent.getIntegerArrayListExtra("primos");
        }
    }

}
