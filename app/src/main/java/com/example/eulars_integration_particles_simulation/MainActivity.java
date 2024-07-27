package com.example.eulars_integration_particles_simulation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ParticleView particleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        particleView = findViewById(R.id.particleView);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        Button buttonRed = findViewById(R.id.buttonRed);
        Button buttonGreen = findViewById(R.id.buttonGreen);
        Button buttonBlue = findViewById(R.id.buttonBlue);
        Button buttonOrange = findViewById(R.id.buttonOrange);
        Button buttonYellow = findViewById(R.id.buttonYellow);
        Button buttonRefresh = findViewById(R.id.buttonRefresh);

        SeekBar seekBarGravity = findViewById(R.id.seekBarGravity);

        buttonRed.setOnClickListener(v -> particleView.changeParticleColor(Color.RED));
        buttonGreen.setOnClickListener(v -> particleView.changeParticleColor(Color.GREEN));
        buttonBlue.setOnClickListener(v -> particleView.changeParticleColor(Color.BLUE));
        buttonOrange.setOnClickListener(v -> particleView.changeParticleColor(Color.parseColor("#FFA500")));
        buttonYellow.setOnClickListener(v -> particleView.changeParticleColor(Color.YELLOW));

        buttonRefresh.setOnClickListener(v -> particleView.resetParticles());

        seekBarGravity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float gravity = progress / 100.0f * 200.0f; // Scale progress to desired gravity range
                particleView.setGravity(gravity);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(particleView, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(particleView);
    }
}












/*
package com.example.eulars_integration_particles_simulation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ParticleView particleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        particleView = findViewById(R.id.particleView);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        Button buttonRed = findViewById(R.id.buttonRed);
        Button buttonGreen = findViewById(R.id.buttonGreen);
        Button buttonBlue = findViewById(R.id.buttonBlue);
        Button buttonOrange = findViewById(R.id.buttonOrange);
        Button buttonYellow = findViewById(R.id.buttonYellow);

        SeekBar seekBarGravity = findViewById(R.id.seekBarGravity);

        buttonRed.setOnClickListener(v -> particleView.changeParticleColor(Color.RED));
        buttonGreen.setOnClickListener(v -> particleView.changeParticleColor(Color.GREEN));
        buttonBlue.setOnClickListener(v -> particleView.changeParticleColor(Color.BLUE));
        buttonOrange.setOnClickListener(v -> particleView.changeParticleColor(Color.parseColor("#FFA500")));
        buttonYellow.setOnClickListener(v -> particleView.changeParticleColor(Color.YELLOW));

        seekBarGravity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float gravity = progress / 100.0f * 200.0f; // Scale progress to desired gravity range
                particleView.setGravity(gravity);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(particleView, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(particleView);
    }
}


*/






///////////////////////////////////////////////////////////////









/*
package com.example.eulars_integration_particles_simulation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ParticleView particleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        particleView = findViewById(R.id.particle_view);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(particleView, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(particleView);
    }
}
*/
