package com.example.broadcastbestpractice;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText usernameEdit = (EditText) findViewById(R.id.edit_text_username);
        final EditText passwordEdit = (EditText) findViewById(R.id.edit_text_password);
        Button loginButton = (Button) findViewById(R.id.button_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = usernameEdit.getText().toString();
                String pswd = passwordEdit.getText().toString();
                if (name.equals("admin") && pswd.equals("admin")) {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Account or password invalid.", Toast.LENGTH_SHORT).show();
                    //Snackbar.make(view, "Account or password invalid.", Snackbar.LENGTH_LONG).setAction("OK", null).show();
                }
            }
        });
    }
}
