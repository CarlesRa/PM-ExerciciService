package com.carlesramos.pm_exerciciservice.services;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class NumerosPrimosService extends IntentService {

    public static final String DAME_PRIMOS = "com.carlesramos.dameprimos";
    private final int GET_COUNT = 0;
    private Thread erCurrante = null;
    private ArrayList<Integer> numerosPrimos = new ArrayList<>();
    private int cantidadPrimos = 0;
    private int posiblesPrimos = 1;
    private int contador = 0;

    public NumerosPrimosService() {
        super("ServiciosPrimo");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        while (true) {
            posiblesPrimos += 2;
            for (int i = 1; i <= posiblesPrimos; i++) {

                if (posiblesPrimos % i == 0) {
                    contador++;
                }
            }
            if (contador == 2) {
                Intent bcIntent = new Intent();
                cantidadPrimos++;
                bcIntent.setAction(DAME_PRIMOS);
                bcIntent.putExtra("cant",cantidadPrimos);
                bcIntent.putExtra("primos",numerosPrimos);
                sendBroadcast(bcIntent);
                numerosPrimos.add(posiblesPrimos);
                Log.i("NumPrimo: ", String.valueOf(posiblesPrimos));
            }
            contador = 0;
        }
    }
}
