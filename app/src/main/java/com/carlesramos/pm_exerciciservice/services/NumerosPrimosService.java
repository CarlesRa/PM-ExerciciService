package com.carlesramos.pm_exerciciservice.services;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;

public class NumerosPrimosService extends Service {

    public static final String DAME_PRIMOS = "com.carlesramos.dameprimos";
    private IBinder binder = new LocalBinder();
    private Thread currante;
    private ArrayList<Integer> numerosPrimos;
    @Override
    public void onCreate() {
        super.onCreate();
        numerosPrimos = new ArrayList<>();
        currante = new Thread(){
            @Override
            public void run(){
                int posiblesPrimos = 2;
                int cantidadPrimos = 0;
                int contador = 0;
                while (true) {
                    if (posiblesPrimos > 2) {
                        posiblesPrimos += 2;
                    }

                    for (int i = 1; i <= posiblesPrimos; i++) {

                        if (posiblesPrimos % i == 0) {
                            contador++;
                        }
                    }

                    if (contador == 2) {
                        cantidadPrimos++;
                        numerosPrimos.add(posiblesPrimos);

                    }

                    if (posiblesPrimos == 2){
                        posiblesPrimos++;
                    }

                    contador = 0;
                }
            }
        };
        currante.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class LocalBinder extends Binder{
        public NumerosPrimosService getService(){
            return NumerosPrimosService.this;
        }
    }

    public ArrayList<Integer> getNumerosPrimos() {
        return numerosPrimos;
    }
}
