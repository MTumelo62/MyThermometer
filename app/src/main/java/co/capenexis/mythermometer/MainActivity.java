package co.capenexis.mythermometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import static android.hardware.SensorManager.SENSOR_DELAY_FASTEST;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Thermometer thermometer;
    private float temperature;
    private Timer timer;
    private Object SensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        thermometer = (Thermometer) findViewById(R.id.thermometer);
        SensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

    }


    @Override
    protected void onResume() {
        super.onResume();
        //loadAmbientTemperature();
        simulateAmbidientTemperature();

    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterAll();
    }

    private void simulateAmbidientTemperature() {
     timer = new Timer();
     timer.scheduleAtFixedRate(new TimerTask() {
         @Override
         public void run() {
            temperature = Utils.randInt(-10, 35);
            runOnUiThread(new Runnable(){
                @Override
                public void run(){
                    thermometer.SetCurrentTemp(temperature);
                    getSupportActionBar().setTitle(getString(R.string.app_name)+":" + temperature);
                }
            });
         }
     },0,3500);
    }

    private void loadAmbientTemperature() {
        Sensor sensor;
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SENSOR_DELAY_FASTEST);
        } else {
            Toast.makeText(this, "No Ambient Temperature Sensor !", Toast.LENGTH_LONG).show();
        }
    }

    private void unregisterAll() {
        //sensorManager.unregisterListener(this);
        timer.cancel();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.values.length > 0) {
            temperature = sensorEvent.values [0];
            thermometer.setCurrentTemp(temperature);
            getSupportActionBar().setTitle(getString(R.string.app_name) + ":" + temperature);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}