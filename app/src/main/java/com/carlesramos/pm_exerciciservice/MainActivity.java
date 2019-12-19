package com.carlesramos.pm_exerciciservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
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
    private boolean isBinded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvListadoPrimos = findViewById(R.id.rvListaPrimos);
        btConsultar = findViewById(R.id.btConsultar);
        tvCantidadPrimos = findViewById(R.id.tvCantidadPrimos);
        btSave = findViewById(R.id.btSave);

        final Intent intent = new Intent(this, NumerosPrimosService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);


        btConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCantidadPrimos.setText(String.valueOf(myService.getNumerosPrimos().size()));
                rvListadoPrimos.setAdapter(new PrimoAdapter(myService.getNumerosPrimos(), MainActivity.this));
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

                    for (int i=0; i<myService.getNumerosPrimos().size(); i++){
                        writer.write((myService.getNumerosPrimos().get(i).toString() + "\n"));
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

    public ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            NumerosPrimosService.LocalBinder binder = (NumerosPrimosService.LocalBinder)service;
            myService = binder.getService();
            isBinded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBinded = false;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
