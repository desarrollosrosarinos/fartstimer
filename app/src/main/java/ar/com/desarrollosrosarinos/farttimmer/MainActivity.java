package ar.com.desarrollosrosarinos.farttimmer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.CountDownTimer;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    int fartSelected = R.raw.fartshort;
    int time = 0;
    //private final int NotificationID = 31654654;
    EditText fartEditTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SeekBar txtTime = findViewById(R.id.farts_time);
        fartEditTime = findViewById(R.id.farts_editTime);

        txtTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                time = progress;
                fartEditTime.setText(""+time);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(MainActivity.this, "Tiempo :" + time,Toast.LENGTH_SHORT).show();
            }
        });

        Button start = findViewById(R.id.start_button);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time = time * 1000;
                /*if (time > 0){
                    new NotificationForegroundOreo().startNotificationForeground(this, NotificationID, getString(R.string.app_name), "Timer:"+time);
                }*/

                if (time > 0) {
                    Intent alarmIntent = new Intent(MainActivity.this, FartTimerReceiver.class);
                    alarmIntent.putExtra(FartTimerReceiver.SELECTED_FART,fartSelected);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
                    AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                        manager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+time, pendingIntent);
                    }else{
                        manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+time, pendingIntent);
                    }
                    moveTaskToBack(true);
                }else{
                    MediaPlayer mPlayer = MediaPlayer.create(MainActivity.this,fartSelected);//Create MediaPlayer object with MP3 file under res/raw folder
                    mPlayer.start();
                }
            }
        });

        Spinner spinner = findViewById(R.id.farts_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.farts_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        switch (pos){
            case 0:
                fartSelected = R.raw.fartshort;
                break;
            case 1:
                fartSelected = R.raw.fartshort2;
                break;
            case 2:
                fartSelected = R.raw.fartfunny;
                break;
            case 3:
                fartSelected = R.raw.fartfunny2;
                break;
            case 4:
                fartSelected = R.raw.fartliquid;
                break;
            case 5:
                fartSelected = R.raw.fartliquid2;
                break;
            case 6:
                fartSelected = R.raw.fartliquid3;
                break;
            case 7:
                fartSelected = R.raw.fartliquid4;
                break;
            case 8:
                fartSelected = R.raw.fartliquid5;
                break;
            case 9:
                fartSelected = R.raw.fart1;
                break;
            case 10:
                fartSelected = R.raw.fartbounce;
                break;
            case 11:
                fartSelected = R.raw.fartbounce2;
                break;
            case 12:
                fartSelected = R.raw.fartbounce3;
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
