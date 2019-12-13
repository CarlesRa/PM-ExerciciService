package com.carlesramos.pm_exerciciservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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

import com.carlesramos.pm_exerciciservice.adapters.PrimoAdapter;
import com.carlesramos.pm_exerciciservice.services.NumerosPrimosService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvListadoPrimos;
    private Button btConsultar;
    private TextView tvCantidadPrimos;
    private ArrayList<Integer> numerosPrimos;
    private NumerosPrimosService myService;
    private int cantidadPrimos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvListadoPrimos = findViewById(R.id.rvListaPrimos);
        btConsultar = findViewById(R.id.btConsultar);
        tvCantidadPrimos = findViewById(R.id.tvCantidadPrimos);
        myService = null;


        Intent intent = new Intent(this, NumerosPrimosService.class);
        startService(intent);
        IntentFilter filter = new IntentFilter();
        filter.addAction(NumerosPrimosService.DAME_PRIMOS);
        PrimosReciver primosReciver = new PrimosReciver();
        registerReceiver(primosReciver,filter);

        btConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCantidadPrimos.setText(String.valueOf(cantidadPrimos));
                rvListadoPrimos.setAdapter(new PrimoAdapter(numerosPrimos, MainActivity.this));
                rvListadoPrimos.setLayoutManager(new LinearLayoutManager(MainActivity.this));
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
