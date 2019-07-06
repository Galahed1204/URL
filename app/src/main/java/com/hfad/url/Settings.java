package com.hfad.url;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    EditText etLogin,etPassword;
    SharedPreferences sPref;

    final String SAVED_LOGIN = "saved_login";
    final String SAVED_PASSWORD = "saved_password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        etLogin = findViewById(R.id.editLogin);
        etPassword = findViewById(R.id.editPassword);
        loadText();
    }

    public void onClickDone(View view){

        sPref = getPreferences(MODE_PRIVATE);
        Editor ed = sPref.edit();
        ed.putString(SAVED_LOGIN, etLogin.getText().toString());
        ed.putString(SAVED_PASSWORD, etPassword.getText().toString());
        ed.commit();

        CharSequence text = "Your settings has been updated";
        int duration = Snackbar.LENGTH_LONG;
        Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinator),text,duration);
        snackbar.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Settings.this, "Undone", Toast.LENGTH_SHORT).show();
            }
        });
        snackbar.show();
    }

    void loadText(){
        sPref = getPreferences(MODE_PRIVATE);
        String savedLogin = sPref.getString(SAVED_LOGIN, "");
        String savedPassword = sPref.getString(SAVED_PASSWORD, "");
        etLogin.setText(savedLogin);
        etPassword.setText(savedPassword);
        //Toast.makeText(this, "Text loaded", Toast.LENGTH_SHORT).show();
    }

}
