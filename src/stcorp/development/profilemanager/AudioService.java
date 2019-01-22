package stcorp.development.profilemanager;



import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;



public class AudioService extends Service implements SensorEventListener{

    public static String PROFILE_MODE="";

    public String log_tag ="Profile Mode";

    //Sensor Variables
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mProximity;


    //Sensor Values;
    private float distance;
    private float aX;
    private float aY;
    private float aZ;

    //Audio Manager

    private AudioManager audioManager;

    @Override
    public void onCreate() {
        initSensors();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerSensorListeners();
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {

           
            public void run() {
            }
        });
        return Service.START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy()
    {
        deregisterSensorListeners();

        Handler handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {

          
            public void run() {
            }
        });
    }


    public void onSensorChanged(SensorEvent sensorEvent)
    {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            aX = sensorEvent.values[0];
            aY = sensorEvent.values[1];
            aZ = sensorEvent.values[2];
        }
        else if(sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY)
        {
            distance = sensorEvent.values[0];
        }
        else
        {

        }

        activateProfile();
    }

    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void initSensors()
    {
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor (Sensor.TYPE_ACCELEROMETER);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    private void registerSensorListeners()
    {
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this,mProximity,SensorManager.SENSOR_DELAY_NORMAL);
    }

    private  void deregisterSensorListeners()
    {
        mSensorManager.unregisterListener(this);
    }

    private  void activateProfile()
    {        
        if (aZ < -6 && distance<=0) {
        	audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        }
        else if(aZ < -6 && distance>=1)
        {
        	audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
        else if(aZ>6 && distance>=1)
        {
        	audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
        else if(aZ>6 && distance<=0)
        {
        	audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        }
        else if(aY>6)
        {
        	audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
    }
}