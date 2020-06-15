package ar.com.desarrollosrosarinos.farttimmer;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class FartTimerReceiver extends BroadcastReceiver {
    MediaPlayer mPlayer;
    public static final String SELECTED_FART = "ar.com.desarrollosrosarinos";
    @Override
    public void onReceive(Context arg0, Intent arg1) {
        mPlayer = MediaPlayer.create(arg0,arg1.getExtras().getInt(SELECTED_FART,R.raw.fartshort));//Create MediaPlayer object with MP3 file under res/raw folder
        mPlayer.start();//Start playing the music
    }

}