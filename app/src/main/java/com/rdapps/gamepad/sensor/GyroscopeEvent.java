package com.rdapps.gamepad.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;

import lombok.Data;

@Data
public class GyroscopeEvent {
    public int accuracy;
    public Sensor sensor;
    public long timestamp;
    public float[] values;

    private static String TAG = GyroscopeEvent.class.getName();

    public static GyroscopeEvent createFromSensorEvent(SensorEvent event) {
        GyroscopeEvent e = new GyroscopeEvent();
        e.accuracy = event.accuracy;
        e.sensor = event.sensor;
        e.timestamp = event.timestamp;
        e.values = event.values;
        return e;
    }

}
