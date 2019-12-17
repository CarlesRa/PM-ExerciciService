package com.carlesramos.pm_exerciciservice.services;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class NumerosPrimosService extends IntentService {

    public static final String DAME_PRIMOS = "com.carlesramos.dameprimos";

    public NumerosPrimosService() {
        super("ServiciosPrimo");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        ArrayList<Integer> numerosPrimos = new ArrayList<>();
        int posiblesPrimos = 1;
        int cantidadPrimos = 0;
        int contador = 0;

        while (true) {

            posiblesPrimos += 2;

            for (int i = 1; i <= posiblesPrimos; i++) {

                if (posiblesPrimos % i == 0) {
                    contador++;
                }
            }

            if (contador == 2) {

                cantidadPrimos++;
                Intent bcIntent = new Intent();
                numerosPrimos.add(posiblesPrimos);
                bcIntent.setAction(DAME_PRIMOS);
                bcIntent.putExtra("cant",cantidadPrimos);
                bcIntent.putExtra("primos",numerosPrimos);
                sendBroadcast(bcIntent);

            }

            contador = 0;
        }
    }
}
