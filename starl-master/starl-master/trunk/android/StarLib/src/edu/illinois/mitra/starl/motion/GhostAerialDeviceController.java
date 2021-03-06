package edu.illinois.mitra.starl.motion;


import android.app.Service;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;



import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Semaphore;
import com.ehang.coptersdk.connection.ConnectionListener;
import com.ehang.coptersdk.CopterControl;
import com.ehang.coptersdk.OnSendListener;


public class GhostAerialDeviceController
{
    private static String TAG = "GADeviceController";

    private Context context;
    OnSendListener copterListener;

    private Service deviceService;

    private DeviceControllerListener listener;

    private ConnectionListener GBoxListener;
    private ConnectionListener CopterListener;
    private final float TAKEOFF = 5.0f;

    //figure out mac address stuff

    public GhostAerialDeviceController(Context context)
    {
        this.context = context;
    }

    public void start()
    {
        Log.d(TAG, "start ...");


        //Set state change listeners
        registerListeners ();

        startNetwork();
    }

    public void stop()
    {
        Log.d(TAG, "stop ...");
        stopNetwork();

        //remove state change listeners

    }

    private void startNetwork()
    {
        final Context context = this.context;
        OnSendListener copterListener = new OnSendListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, "Pairing Succeeds", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure() {
                Toast.makeText(context, "Pairing Fails", Toast.LENGTH_SHORT).show();

            }
        };

        CopterControl.getInstance().getInstance().startPair(copterListener);

    }


    private void stopNetwork()
    {
        CopterControl.getInstance().getInstance().stopPair();
    }

    protected void registerListeners ()
    {

        CopterListener = new ConnectionListener(){
            @Override
            public void onConnect() {
                Toast.makeText(context, "CopterConnected", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onDisconnect() {
                Toast.makeText(context, "Copter Connected", Toast.LENGTH_SHORT).show();

            }
        };
        CopterControl.getInstance().getConnection().addCopterConnectionListener(CopterListener);

    }


    public void sendTakeoff()
    {
        final Context context = this.context;
        CopterControl.getInstance().takeoff(TAKEOFF, new OnSendListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, "Takeoff Succeeds", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure() {
                Toast.makeText(context, "Takeoff Fails", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void sendLanding()
    {
        final Context context = this.context;
        CopterControl.getInstance().land(new OnSendListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, "Land Succeeds", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure() {
                Toast.makeText(context, "Land Fails", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void sendEmergency()
    {
        final Context context = this.context;
        CopterControl.getInstance().land(new OnSendListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, "Land Succeeds", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure() {
                Toast.makeText(context, "Land Fails", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void setFlag(boolean loiter)
    {
        if(loiter)
        {
            CopterControl.getInstance().setThrottle(0);
        }
    }

    public void setGaz(int gaz)
    {
        CopterControl.getInstance().setThrottle(gaz);
    }

    public void setRoll (double roll)
    {
        float tmp = (float) roll;
        CopterControl.getInstance().attitudeControl((float)roll, null);
    }

    public void setPitch (double pitch)
    {
        CopterControl.getInstance().attitudeControl(null, (float)pitch);
    }

    public void setYaw (double yaw)
    {
        CopterControl.getInstance().attitudeControl(null, null, (float)yaw);
    }

    public void setListener(DeviceControllerListener listener)
    {
        this.listener = listener;
    }


    public void sendMaxTilt(float maxTilt) {

    }

    public void sendMaxAltitude(float maxAlt) {

    }

    public void setListener() {

    }


}
