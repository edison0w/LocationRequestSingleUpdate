package com.example.edison.locationrequestsingleupdate;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Declaración de variables
    LocationManager mLocationManager;
    TextView latitud, longitud;
    LocationListener mLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializamos los componentes
        latitud = (TextView) findViewById(R.id.txtLatitud);
        longitud = (TextView) findViewById(R.id.txtLongitud);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Cuando obtenga una actualizacion seteamos los valores en los TextView
                        latitud.setText(String.format("%s %f", "latitud", location.getLatitude()));
                        longitud.setText(String.format("%s %f", "longitud", location.getLongitude()));
                    }
                });

                // Finalizamos el Looper si es diferente de null
                Looper myLooper = Looper.myLooper();
                if (myLooper!=null) {
                    myLooper.quit();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }

    public void onclick(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                // Inicializamos el Looper en el hilo actual
                Looper.prepare();

                // Registrate en una sola actualización de posición,
                // utilizando el proveedor GPS, y una devolución de llamada.
                // Nota: el proveedor puede ser otro Network por ejemplo pero para este ejmplo utilizao GPS
                mLocationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, mLocationListener, Looper.myLooper());

                // Ejecutamos la cola de mensajes en este hilo
                Looper.loop();

            }
        }).start();

    }
}
