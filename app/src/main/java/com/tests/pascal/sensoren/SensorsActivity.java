package com.tests.pascal.sensoren;

import java.util.Formatter;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import android.content.Context;
import android.app.Activity;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SensorsActivity extends Activity {// AppCompatActivity {

    // Make strings for logging
    private final String TAG = this.getClass().getSimpleName();
    private final String ANSWER = "answer";

    // The string "fortytwo" is used as an example of state
    private final String state = "fortytwo";

    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_sensors);
        String answer = null;
        // savedState could be null
        if (null != savedState) {
            answer = savedState.getString(ANSWER);
        }
        Log.i(TAG, getMethodName()
                + (null == savedState ? "" : (getString(R.string.restore) + " " + answer)));
    }

    private SensorManager sm = null;
    @Override
    protected void onRestart() {
        super.onRestart();
        // Notification that the activity will be started
        Log.i(TAG, getMethodName());
    }
    // Samsung specific sensors as of http://developer.samsung.com/forum/thread/galaxy-s7-built-in-hardware-sensors/201/297805?boardName=SDK&startId=00tZE~&startPage=5&curPage=7
    public static final int TYPE_SAMSUNG_TILT_DETECTOR_V1 = 22;
    public static final int TYPE_SCREEN_ORIENTATION_SENSOR_V3  = 65558;
    public static final int TYPE_GRIP_SENSOR_V512_SEMTECH = 65560;
    public static final int TYPE_TMD4093_RGB_IR_SENSOR_V1 = 65578;
    public static final int TYPE_INTERRUPT_GYROSCOPE_SENSOR_V1 = 65579;

    @Override
    protected void onStart() {
        super.onStart();
        // Notification that the activity is starting
        Log.i(TAG, getMethodName());
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor>sensors = sm.getSensorList(Sensor.TYPE_ALL);
        StringBuilder sb = new StringBuilder();
        Formatter ft = new Formatter(sb);
        ft.format(getString(R.string.num_sensors_fmt), sensors.size());
        String numSensorsText = sb.toString();
        Log.i(TAG, numSensorsText);
        TextView numSensorsView = (TextView) findViewById(R.id.pa);
        numSensorsView.setText(numSensorsText);
        List<String> sensorNames = new ArrayList<String>(sensors.size());
        for (Sensor s : sensors)
        {
            String name;
            switch(s.getType())
            {
                case Sensor.TYPE_ACCELEROMETER: name =  "ACCELEROMETER"; break;
                case Sensor.TYPE_ACCELEROMETER_UNCALIBRATED: name =  "ACCELEROMETER_UNCALIBRATED"; break;
                case Sensor.TYPE_ALL: name =  "ALL"; break;
                case Sensor.TYPE_AMBIENT_TEMPERATURE: name =  "AMBIENT_TEMPERATURE"; break;
                case Sensor.TYPE_DEVICE_PRIVATE_BASE: name =  "DEVICE_PRIVATE_BASE"; break;
                case Sensor.TYPE_GAME_ROTATION_VECTOR: name =  "GAME_ROTATION_VECTOR"; break;
                case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR: name =  "GEOMAGNETIC_ROTATION_VECTOR"; break;
                case Sensor.TYPE_GRAVITY: name =  "GRAVITY"; break;
                case Sensor.TYPE_GYROSCOPE: name =  "GYROSCOPE"; break;
                case Sensor.TYPE_GYROSCOPE_UNCALIBRATED: name =  "GYROSCOPE_UNCALIBRATED"; break;
                case Sensor.TYPE_HEART_BEAT: name =  "HEART_BEAT"; break;
                case Sensor.TYPE_HEART_RATE: name =  "HEART_RATE"; break;
                case Sensor.TYPE_LIGHT: name =  "LIGHT"; break;
                case Sensor.TYPE_LINEAR_ACCELERATION: name =  "LINEAR_ACCELERATION"; break;
                case Sensor.TYPE_LOW_LATENCY_OFFBODY_DETECT: name =  "LOW_LATENCY_OFFBODY_DETECT"; break;
                case Sensor.TYPE_MAGNETIC_FIELD: name =  "MAGNETIC_FIELD"; break;
                case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED: name =  "MAGNETIC_FIELD_UNCALIBRATED"; break;
                case Sensor.TYPE_MOTION_DETECT: name =  "MOTION_DETECT"; break;
                /** @deprecated */
                case Sensor.TYPE_ORIENTATION: name =  "ORIENTATION"; break;
                case Sensor.TYPE_POSE_6DOF: name =  "POSE_6DOF"; break;
                case Sensor.TYPE_PRESSURE: name =  "PRESSURE"; break;
                case Sensor.TYPE_PROXIMITY: name =  "PROXIMITY"; break;
                case Sensor.TYPE_RELATIVE_HUMIDITY: name =  "RELATIVE_HUMIDITY"; break;
                case Sensor.TYPE_ROTATION_VECTOR: name =  "ROTATION_VECTOR"; break;
                case Sensor.TYPE_SIGNIFICANT_MOTION: name =  "SIGNIFICANT_MOTION"; break;
                case Sensor.TYPE_STATIONARY_DETECT: name =  "STATIONARY_DETECT"; break;
                case Sensor.TYPE_STEP_COUNTER: name =  "STEP_COUNTER"; break;
                case Sensor.TYPE_STEP_DETECTOR: name =  "STEP_DETECTOR"; break;
                /** @deprecated */
                case Sensor.TYPE_TEMPERATURE: name =  "TEMPERATURE"; break;
                /* Samsung Sensors */
                case TYPE_SAMSUNG_TILT_DETECTOR_V1: name =  "SAMSUNG_TILT_DETECTOR_V1"; break;
                case TYPE_SCREEN_ORIENTATION_SENSOR_V3: name =  "SCREEN_ORIENTATION_SENSOR_V3"; break;
                case TYPE_GRIP_SENSOR_V512_SEMTECH: name =  "GRIP_SENSOR_V512_SEMTECH"; break;
                case TYPE_TMD4093_RGB_IR_SENSOR_V1: name =  "TMD4093_RGB_IR_SENSOR_V1"; break;
                case TYPE_INTERRUPT_GYROSCOPE_SENSOR_V1: name =  "INTERRUPT_GYROSCOPE_SENSOR_V1"; break;
                default:
                    name = getString(R.string.sensor_unknown_type) + s.getType();
                    break;

            }
            sensorNames.add(name);
            Log.i(TAG, getString(R.string.sensor_found) + sensorNames.get(sensorNames.size()-1));
        }
        // From API level 24 sensorNames.sort(String::compareToIgnoreCase);
        Collections.sort(sensorNames, String.CASE_INSENSITIVE_ORDER);
        ListView sensorListView = (ListView) findViewById(R.id.listSensors);
        ArrayAdapter <String> sensorAdapter = new ArrayAdapter<String>(this, R.layout.onesensor, R.id.oneSensorVIew, sensorNames);
        sensorListView.setAdapter(sensorAdapter);
    }
    private String getMethodName()
    {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return stackTrace[3].getMethodName();
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Notification that the activity will interact with the user
        Log.i(TAG, getMethodName());
    }

    protected void onPause() {
        super.onPause();
        // Notification that the activity will stop interacting with the user
        Log.i(TAG, getMethodName() + (isFinishing() ? getString(R.string.finishing) : ""));
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Notification that the activity is no longer visible
        Log.i(TAG, getMethodName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Notification the activity will be destroyed
        Log.i(TAG,
                getMethodName()
                        // Log which, if any, configuration changed
                        + Integer.toString(getChangingConfigurations(), 16));
    }

    // ////////////////////////////////////////////////////////////////////////////
    // Called during the lifecycle, when instance state should be saved/restored
    // ////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save instance-specific state
        outState.putString(ANSWER, state);
        super.onSaveInstanceState(outState);
        Log.i(TAG, getMethodName());

    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        Log.i(TAG, getMethodName());
        return new Integer(getTaskId());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);
        // Restore state; we know savedState is not null
        String answer = null != savedState ? savedState.getString(ANSWER) : "";
        Object oldTaskObject = getLastNonConfigurationInstance();
        if (null != oldTaskObject) {
            int oldtask = ((Integer) oldTaskObject).intValue();
            int currentTask = getTaskId();
            // Task should not change across a configuration change
            assert oldtask == currentTask;
        }
        Log.i(TAG, getMethodName()
                + (null == savedState ? "" : getString(R.string.restore)) + " " + answer);
    }

    // ////////////////////////////////////////////////////////////////////////////
    // These are the minor lifecycle methods, you probably won't need these
    // ////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onPostCreate(Bundle savedState) {
        super.onPostCreate(savedState);
        String answer = null;
        // savedState could be null
        if (null != savedState) {
            answer = savedState.getString(ANSWER);
        }
        Log.i(TAG, getMethodName()
                + (null == savedState ? "" : (getString(R.string.restore) + " " + answer)));

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.i(TAG, getMethodName());
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        Log.i(TAG, getMethodName());
    }
}
