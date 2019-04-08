package ru.kliker02.practice.practice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText login;
    EditText pass;
    Button toLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (EditText)findViewById(R.id.log);
        pass = (EditText)findViewById((R.id.pas));
        toLogin = (Button)findViewById(R.id.toLogin);

        View.OnClickListener loginBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login.getText().toString().equals("admin") && pass.getText().toString().equals("admin")){
                    //переходим в другую активити
                    Intent intent = new Intent(v.getContext(), MainApp.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(),"Заполните все поля верно", Toast.LENGTH_SHORT).show();
                }


            }
        };
        toLogin.setOnClickListener(loginBtn);

    }
}

