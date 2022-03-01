package com.example.futbolito;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class Juego extends View implements SensorEventListener {

    Paint dibujo = new Paint();
    int altPantalla, anchoPantalla;
    int tamPelota =25;
    int borde =10;
    float ejeX=0, ejeY=0, ejeZ=0;
    Sensor acelerometro;
    SensorManager sensorManager;

    public Juego(Context context){
        super(context);
        //SensorManager para registrar el acelerómetro
        sensorManager = (SensorManager) getContext().getSystemService
                (Context.SENSOR_SERVICE);
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_FASTEST);

        //Obtener las medidas de la pantalla
        Display screen = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).
                getDefaultDisplay();
        altPantalla = screen.getHeight();
        anchoPantalla = screen.getWidth();
        setBackgroundResource(R.drawable.background);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //control del eje X
        ejeX-=sensorEvent.values[0];

        //controlar el borde izquierdo de la pantalla
        if(ejeX < (tamPelota + borde)){
            ejeX = (tamPelota+borde);
        }
        //controlar el borde izquierdo de la pantalla
        else if(ejeX > (anchoPantalla-(tamPelota+borde))){
            ejeX = anchoPantalla-(tamPelota+borde);
        }

        //control del eje Y
        ejeY+=sensorEvent.values[1];

        // controlar el borde inferior de la pantalla
        if(ejeY < (tamPelota+borde)){
            ejeY=tamPelota+borde;
        }
        //controlar el borde superior de la pantalla
        else if(ejeY>(altPantalla-tamPelota-170)){
            ejeY = altPantalla-tamPelota-170;
        }

        //To force a view to draw, call invalidate().
        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    //Dibuja la pelota y los elementos de pantalla
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        dibujo.setColor(Color.GRAY);
        //canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),
        //        R.drawable.background),0,0,dibujo);
        canvas.drawCircle(ejeX,ejeY, ejeZ+tamPelota, dibujo);
        dibujo.setColor(Color.GREEN);
        dibujo.setTextSize(25);
        canvas.drawText("ejeX: "+ejeX+" ejeY: "+ejeY, ejeX-55, ejeY+30, dibujo);

    }

}
