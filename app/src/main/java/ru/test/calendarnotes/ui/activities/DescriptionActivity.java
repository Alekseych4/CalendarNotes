package ru.test.calendarnotes.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ru.test.calendarnotes.R;

public class DescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        getSupportActionBar().setTitle(getString(R.string.descr));
    }
}
