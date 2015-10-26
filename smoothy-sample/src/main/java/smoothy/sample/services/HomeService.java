package smoothy.sample.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import smoothy.BindExtra;
import smoothy.SmoothyBundle;

/**
 * Created by mehdi on 22/10/2015.
 */
public class HomeService extends Service {

    @BindExtra(optional = true)
    String mName;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SmoothyBundle.bind(this, intent);

        Toast.makeText(getApplicationContext(), "Start service [bundle name = '" + mName + "']",
                Toast.LENGTH_LONG).show();

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
