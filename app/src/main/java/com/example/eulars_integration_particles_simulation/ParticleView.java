package com.example.eulars_integration_particles_simulation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Paint;

import java.util.Random;

public class ParticleView extends View implements SensorEventListener {

    private int NUM_PARTICLES = 10; // Set to 10 for at least 10 different balls
    private final float PARTICLE_SIZE = 15; // in pixels

    private float[] xPositions;
    private float[] yPositions;
    private float[] xVelocities;
    private float[] yVelocities;

    private float accelerometerX;
    private float accelerometerY;

    private final float DEFAULT_GRAVITY = 80.81f;
    private float gravity = DEFAULT_GRAVITY; // m/s^2
    private final float VISCOSITY = 0.019f;

    private Paint[] paints;

    public ParticleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initParticles();
    }

    private void initParticles() {
        paints = new Paint[NUM_PARTICLES];
        for (int i = 0; i < NUM_PARTICLES; i++) {
            paints[i] = new Paint();
            paints[i].setColor(getRandomColor());
            paints[i].setStyle(Paint.Style.FILL);
        }

        xPositions = new float[NUM_PARTICLES];
        yPositions = new float[NUM_PARTICLES];
        xVelocities = new float[NUM_PARTICLES];
        yVelocities = new float[NUM_PARTICLES];
        invalidate(); // redraw the view with new particle positions

        // initialize particle positions randomly
        Random random = new Random();
        for (int i = 0; i < NUM_PARTICLES; i++) {
            xPositions[i] = random.nextFloat() * getWidth();
            yPositions[i] = random.nextFloat() * getHeight();
            xVelocities[i] = random.nextFloat() * 200 - 100;
            yVelocities[i] = random.nextFloat() * 200 - 100;
        }
    }

    private int getRandomColor() {
        Random random = new Random();
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerX = event.values[0];
            accelerometerY = event.values[1];
            invalidate(); // redraw the view with new particle positions
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // simulate particle motion using Euler integration
        float deltaTime = 0.1f;
        for (int i = 0; i < NUM_PARTICLES; i++) {
            float xAccel = -gravity * accelerometerX - VISCOSITY * xVelocities[i];
            float yAccel = gravity * accelerometerY - VISCOSITY * yVelocities[i];
            xVelocities[i] += xAccel * deltaTime;
            yVelocities[i] += yAccel * deltaTime;
            xPositions[i] += xVelocities[i] * deltaTime;
            yPositions[i] += yVelocities[i] * deltaTime;

            // ensure particles stay within the view bounds
            if (xPositions[i] < 0) {
                xPositions[i] = 0;
                xVelocities[i] = -xVelocities[i];
            } else if (xPositions[i] > getWidth()) {
                xPositions[i] = getWidth();
                xVelocities[i] = -xVelocities[i];
            }
            if (yPositions[i] < 0) {
                yPositions[i] = 0;
                yVelocities[i] = -yVelocities[i];
            } else if (yPositions[i] > getHeight()) {
                yPositions[i] = getHeight();
                yVelocities[i] = -yVelocities[i];
            }

            // draw particle
            canvas.drawCircle(xPositions[i], yPositions[i], PARTICLE_SIZE, paints[i]);
        }
    }

    public void changeParticleColor(int color) {
        for (Paint paint : paints) {
            paint.setColor(color);
        }
        invalidate(); // redraw the view with the new particle color
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public void resetParticles() {
        initParticles();
    }
}



/*

package com.example.eulars_integration_particles_simulation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Paint;

import java.util.Random;

public class ParticleView extends View implements SensorEventListener {

    private int NUM_PARTICLES = 10; // Set to 10 for at least 10 different balls
    private final float PARTICLE_SIZE = 15; // in pixels

    private float[] xPositions;
    private float[] yPositions;
    private float[] xVelocities;
    private float[] yVelocities;

    private float accelerometerX;
    private float accelerometerY;

    private final float DEFAULT_GRAVITY = 80.81f;
    private float gravity = DEFAULT_GRAVITY; // m/s^2
    private final float VISCOSITY = 0.019f;

    private Paint[] paints;

    public ParticleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paints = new Paint[NUM_PARTICLES];
        for (int i = 0; i < NUM_PARTICLES; i++) {
            paints[i] = new Paint();
            paints[i].setColor(getRandomColor());
            paints[i].setStyle(Paint.Style.FILL);
        }

        xPositions = new float[NUM_PARTICLES];
        yPositions = new float[NUM_PARTICLES];
        xVelocities = new float[NUM_PARTICLES];
        yVelocities = new float[NUM_PARTICLES];
        invalidate(); // redraw the view with new particle positions

        // initialize particle positions randomly
        Random random = new Random();
        for (int i = 0; i < NUM_PARTICLES; i++) {
            xPositions[i] = random.nextFloat() * getWidth();
            yPositions[i] = random.nextFloat() * getHeight();
            xVelocities[i] = random.nextFloat() * 200 - 100;
            yVelocities[i] = random.nextFloat() * 200 - 100;
        }
    }

    private int getRandomColor() {
        Random random = new Random();
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerX = event.values[0];
            accelerometerY = event.values[1];
            invalidate(); // redraw the view with new particle positions
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // simulate particle motion using Euler integration
        float deltaTime = 0.1f;
        for (int i = 0; i < NUM_PARTICLES; i++) {
            float xAccel = -gravity * accelerometerX - VISCOSITY * xVelocities[i];
            float yAccel = gravity * accelerometerY - VISCOSITY * yVelocities[i];
            xVelocities[i] += xAccel * deltaTime;
            yVelocities[i] += yAccel * deltaTime;
            xPositions[i] += xVelocities[i] * deltaTime;
            yPositions[i] += yVelocities[i] * deltaTime;

            // ensure particles stay within the view bounds
            if (xPositions[i] < 0) {
                xPositions[i] = 0;
                xVelocities[i] = -xVelocities[i];
            } else if (xPositions[i] > getWidth()) {
                xPositions[i] = getWidth();
                xVelocities[i] = -xVelocities[i];
            }
            if (yPositions[i] < 0) {
                yPositions[i] = 0;
                yVelocities[i] = -yVelocities[i];
            } else if (yPositions[i] > getHeight()) {
                yPositions[i] = getHeight();
                yVelocities[i] = -yVelocities[i];
            }

            // draw particle
            canvas.drawCircle(xPositions[i], yPositions[i], PARTICLE_SIZE, paints[i]);
        }
    }

    public void changeParticleColor(int color) {
        for (Paint paint : paints) {
            paint.setColor(color);
        }
        invalidate(); // redraw the view with the new particle color
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }
}
*/



////////////////////////////////////////////////////////


/*
package com.example.eulars_integration_particles_simulation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Paint;
import android.widget.SeekBar;

import java.util.Random;

public class ParticleView extends View implements SensorEventListener {

    private int NUM_PARTICLES = 500;
    private final float PARTICLE_SIZE = 15; // in pixels

    private float[] xPositions;
    private float[] yPositions;
    private float[] xVelocities;
    private float[] yVelocities;

    private float accelerometerX;
    private float accelerometerY;

    private final float DEFAULT_GRAVITY = 80.81f;
    private float gravity = DEFAULT_GRAVITY; // m/s^2
    private final float VISCOSITY = 0.019f;

    private Paint paint;

    public ParticleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);

        xPositions = new float[NUM_PARTICLES];
        yPositions = new float[NUM_PARTICLES];
        xVelocities = new float[NUM_PARTICLES];
        yVelocities = new float[NUM_PARTICLES];
        invalidate(); // redraw the view with new particle positions

        // initialize particle positions randomly
        Random random = new Random();

        for (int i = 0; i < NUM_PARTICLES; i++) {
            xPositions[i] = random.nextFloat() * getWidth();
            yPositions[i] = random.nextFloat() * getHeight();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerX = event.values[0];
            accelerometerY = event.values[1];
            invalidate(); // redraw the view with new particle positions
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // simulate particle motion using Euler integration
        float deltaTime = 0.1f;
        for (int i = 0; i < NUM_PARTICLES; i++) {
            float xAccel = -gravity * accelerometerX - VISCOSITY * xVelocities[i];
            float yAccel = gravity * accelerometerY - VISCOSITY * yVelocities[i];
            xVelocities[i] += xAccel * deltaTime;
            yVelocities[i] += yAccel * deltaTime;
            xPositions[i] += xVelocities[i] * deltaTime;
            yPositions[i] += yVelocities[i] * deltaTime;

            // ensure particles stay within the view bounds
            if (xPositions[i] < 0) {
                xPositions[i] = 0;
                xVelocities[i] = -xVelocities[i];
            } else if (xPositions[i] > getWidth()) {
                xPositions[i] = getWidth();
                xVelocities[i] = -xVelocities[i];
            }
            if (yPositions[i] < 0) {
                yPositions[i] = 0;
                yVelocities[i] = -yVelocities[i];
            } else if (yPositions[i] > getHeight()) {
                yPositions[i] = getHeight();
                yVelocities[i] = -yVelocities[i];
            }

            // draw particle
            canvas.drawCircle(xPositions[i], yPositions[i], PARTICLE_SIZE, paint);
        }
    }

    public void changeParticleColor(int color) {
        paint.setColor(color);
        invalidate(); // redraw the view with the new particle color
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }
}*/




/////////////////////////////////////////////////////////////////// 27 july 2024 .////////////////////////////


/*package com.example.eulars_integration_particles_simulation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Paint;
import java.util.Random;


public class ParticleView extends View implements SensorEventListener {

    private int NUM_PARTICLES = 500;
    private final float PARTICLE_SIZE = 15; // in pixels

    private float[] xPositions;
    private float[] yPositions;
    private float[] xVelocities;
    private float[] yVelocities;

    private float accelerometerX;
    private float accelerometerY;

    private final float GRAVITY = 80.81f; // m/s^2
    private final float VISCOSITY = 0.019f;

    private Paint paint;
    private Paint paint2;
    private Paint paint3;
    private Paint paint4;
    private Paint paint5;

    private Paint paint6;


    public ParticleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);

        paint2 = new Paint();
        paint2.setColor(Color.BLUE);
        paint2.setStyle(Paint.Style.FILL);

        paint3 = new Paint();
        paint3.setColor(Color.GREEN);
        paint3.setStyle(Paint.Style.FILL);

        paint4 = new Paint();
        paint4.setColor(Color.RED);
        paint4.setStyle(Paint.Style.FILL);

        paint5 = new Paint();
        paint5.setColor(Color.BLUE);
        paint5.setStyle(Paint.Style.FILL);

        paint6 = new Paint();
        paint6.setColor(Color.GREEN);
        paint6.setStyle(Paint.Style.FILL);

        xPositions = new float[NUM_PARTICLES];
        yPositions = new float[NUM_PARTICLES];
        xVelocities = new float[NUM_PARTICLES];
        yVelocities = new float[NUM_PARTICLES];
        invalidate(); // redraw the view with new particle positions

        // initialize particle positions randomly
        Random random = new Random();

        for (int i = 0; i < NUM_PARTICLES; i++) {
            xPositions[i] = random.nextFloat() * getWidth();
            yPositions[i] = random.nextFloat() * getHeight();

        }

        // add two more balls with different colors at different positions
        xPositions[NUM_PARTICLES-2] = random.nextFloat() * getWidth();
        yPositions[NUM_PARTICLES-2] =  random.nextFloat() * getHeight();
        xVelocities[NUM_PARTICLES-2] =  random.nextFloat() * 100 - 50;
        yVelocities[NUM_PARTICLES-2] = random.nextFloat() * 500 - 50;

      paint.setColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        paint3.setColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));

        paint2.setColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        paint4.setColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        paint5.setColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        paint6.setColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));

// increment the number of particles
        xPositions[NUM_PARTICLES-1] = random.nextFloat() * getWidth();
        yPositions[NUM_PARTICLES-1] = random.nextFloat() * getHeight();
        xVelocities[NUM_PARTICLES-1] = random.nextFloat() * 200 - 50;
        yVelocities[NUM_PARTICLES-1] = random.nextFloat() * 400 - 50;

        xPositions[NUM_PARTICLES-3] = random.nextFloat() * getWidth();
        yPositions[NUM_PARTICLES-3] = random.nextFloat() * getHeight();
        xVelocities[NUM_PARTICLES-3] = random.nextFloat() * 200 - 50;
        yVelocities[NUM_PARTICLES-3] = random.nextFloat() * 400 - 50;

        xPositions[NUM_PARTICLES-4] = random.nextFloat() * getWidth();
        yPositions[NUM_PARTICLES-4] = random.nextFloat() * getHeight();
        xVelocities[NUM_PARTICLES-4] = random.nextFloat() * 200 - 50;
        yVelocities[NUM_PARTICLES-4] = random.nextFloat() * 400 - 50;


        xPositions[NUM_PARTICLES-5] = random.nextFloat() * getWidth();
        yPositions[NUM_PARTICLES-5] = random.nextFloat() * getHeight();
        xVelocities[NUM_PARTICLES-5] = random.nextFloat() * 2 - 50;
        yVelocities[NUM_PARTICLES-5] = random.nextFloat() * 1 - 50;

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerX = event.values[0];
            accelerometerY = event.values[1];
            invalidate(); // redraw the view with new particle positions
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // simulate particle motion using Euler integration
        float deltaTime = 0.1f;
        for (int i = 0; i < NUM_PARTICLES; i++) {
            float xAccel = -GRAVITY * accelerometerX - VISCOSITY * xVelocities[i];
            float yAccel = GRAVITY * accelerometerY - VISCOSITY * yVelocities[i];
            xVelocities[i] += xAccel * deltaTime;
            yVelocities[i] += yAccel * deltaTime;
            xPositions[i] += xVelocities[i] * deltaTime;
            yPositions[i] += yVelocities[i] * deltaTime;

            // ensure particles stay within the view bounds
            if (xPositions[i] < 0) {
                xPositions[i] = 0;
                xVelocities[i] = -xVelocities[i];
            } else if (xPositions[i] > getWidth()) {
                xPositions[i] = getWidth();
                xVelocities[i] = -xVelocities[i];
            }
            if (yPositions[i] < 0) {
                yPositions[i] = 0;
                yVelocities[i] = -yVelocities[i];
            } else if (yPositions[i] > getHeight()) {
                yPositions[i] = getHeight();
                yVelocities[i] = -yVelocities[i];
            }

            // draw particle
            canvas.drawCircle(xPositions[i], yPositions[i], PARTICLE_SIZE, paint);
            canvas.drawCircle(xPositions[i], yPositions[i], PARTICLE_SIZE, paint2);
            canvas.drawCircle(xPositions[i], yPositions[i], PARTICLE_SIZE, paint3);
            canvas.drawCircle(xPositions[i], yPositions[i], PARTICLE_SIZE, paint4);
            canvas.drawCircle(xPositions[i], yPositions[i], PARTICLE_SIZE, paint5);
            canvas.drawCircle(xPositions[i], yPositions[i], PARTICLE_SIZE, paint6);
        }



        /////////////////////////   27 July 2024 ////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////// 27 july 2024 .////////////////////////////





         //draw additional balls with different colors and starting positions
//        paint.setColor(Color.WHITE);
//        canvas.drawCircle(getWidth() * 0.25f, getHeight() * 0.25f, PARTICLE_SIZE, paint);
//        paint.setColor(Color.CYAN);
//        canvas.drawCircle(getWidth() * 0.75f, getHeight() * 0.25f, PARTICLE_SIZE, paint2);
//        paint.setColor(Color.YELLOW);
//        canvas.drawCircle(getWidth() * 0.5f, getHeight() * 0.75f, PARTICLE_SIZE, paint3);
//        invalidate();

    }


    // implement other required methods for SensorEventListener
}*/














//
//public class ParticleView extends View implements SensorEventListener {
//
//    private final int NUM_PARTICLES = 1000;
//    private final float PARTICLE_SIZE = 10; // in pixels
//
//    private float[] xPositions;
//    private float[] yPositions;
//    private float[] xVelocities;
//    private float[] yVelocities;
//
//    private float accelerometerX;
//    private float accelerometerY;
//
//    private final float GRAVITY = 50.81f; // m/s^2
//    private final float VISCOSITY = 0.009f;
//
//    private Paint paint;
//
//
//
//    public ParticleView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//
//        paint = new Paint();
//        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.FILL);
//        xPositions = new float[NUM_PARTICLES];
//        yPositions = new float[NUM_PARTICLES];
//        xVelocities = new float[NUM_PARTICLES];
//        yVelocities = new float[NUM_PARTICLES];
//
//        // initialize particle positions randomly
//        Random random = new Random();
//        for (int i = 0; i < NUM_PARTICLES; i++) {
//            xPositions[i] = random.nextFloat() * getWidth();
//            yPositions[i] = random.nextFloat() * getHeight();
//        }
//    }
//
//    @Override
//    public void onSensorChanged(SensorEvent event) {
//        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//            accelerometerX = event.values[0];
//            accelerometerY = event.values[1];
//            invalidate(); // redraw the view with new particle positions
//        }
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//    }
//
//    @Override
//    public void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        // simulate particle motion using Euler integration
//        float deltaTime = 0.1f;
//        for (int i = 0; i < NUM_PARTICLES; i++) {
//            float xAccel = -GRAVITY * accelerometerX - VISCOSITY * xVelocities[i];
//            float yAccel = GRAVITY * accelerometerY - VISCOSITY * yVelocities[i];
//            xVelocities[i] += xAccel * deltaTime;
//            yVelocities[i] += yAccel * deltaTime;
//            xPositions[i] += xVelocities[i] * deltaTime;
//            yPositions[i] += yVelocities[i] * deltaTime;
//
//            // ensure particles stay within the view bounds
//            if (xPositions[i] < 0) {
//                xPositions[i] = 0;
//                xVelocities[i] = -xVelocities[i];
//            } else if (xPositions[i] > getWidth()) {
//                xPositions[i] = getWidth();
//                xVelocities[i] = -xVelocities[i];
//            }
//            if (yPositions[i] < 0) {
//                yPositions[i] = 0;
//                yVelocities[i] = -yVelocities[i];
//            } else if (yPositions[i] > getHeight()) {
//                yPositions[i] = getHeight();
//                yVelocities[i] = -yVelocities[i];
//            }
//
//            // draw particle
//            canvas.drawCircle(xPositions[i], yPositions[i], PARTICLE_SIZE, paint);
//
//
//        }
//    }
//
//    // implement other required methods for SensorEventListener
//}
