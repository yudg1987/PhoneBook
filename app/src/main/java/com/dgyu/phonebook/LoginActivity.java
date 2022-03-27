package com.dgyu.phonebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 登录模块
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_login;
    private Button btn_register;
    private Button btn_forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

        btn_forget = findViewById(R.id.btn_forget);
        btn_forget.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent it = new Intent();
        switch (id) {
            case R.id.btn_login:
                break;
            case R.id.btn_register:
                gotoRegister(it);
                break;
            case R.id.btn_forget:
                gotoForget(it);
                break;
        }


    }

    private void gotoRegister(Intent it) {
        it.setClass(this, RegisterActivity.class);
        startActivity(it);
    }

    private void gotoForget(Intent it) {
        it.setClass(this, ForgetActivity.class);
        startActivity(it);
    }


}
