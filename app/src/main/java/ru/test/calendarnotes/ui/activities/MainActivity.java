package ru.test.calendarnotes.ui.activities;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import ru.test.calendarnotes.R;
import ru.test.calendarnotes.data.TaskModel;
import ru.test.calendarnotes.ui.adapters.ScheduleAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView scheduleList;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        List<TaskModel> schedule = new ArrayList<>();
        TaskModel t = new TaskModel();
        t.setName("Моё первое дело");
        t.setDescription("Ну вот как-то так");
        t.setDate_start(new Timestamp(Instant.now().toEpochMilli()));
        t.setDate_finish(new Timestamp(Instant.now().toEpochMilli() + 10000));
        t.setId(0);

        schedule.add(t);
        TaskModel t2 = new TaskModel();
        t2.setName("Моё второе дело");
        schedule.add(t2);
        schedule.add(t2);

        modelSerialization(t);

        scheduleList = findViewById(R.id.schedule_list);
        scheduleList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        scheduleList.setAdapter(new ScheduleAdapter(getBaseContext(), schedule));
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

    private void modelSerialization(TaskModel taskToSerialize){
        gson = new Gson();
        Log.d("Main", gson.toJson(taskToSerialize));
    }
}
