package com.dgyu.phonebook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SqlLiteLoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText username;
    EditText password;
    Button login, register;
    SharedPreferences sp = null;
    private CheckBox rember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_lite_login);
        findViews();
        sp = getSharedPreferences("currentUser", MODE_PRIVATE);
        //记住密码功能
        if (null != sp) {
            String accountPre = sp.getString("account", null);
            username.setText(accountPre);
            password.setText(sp.getString("password", null));
            if (!TextUtils.isEmpty(accountPre)) {
                rember.setChecked(true);
            }
        }
    }

    private void findViews() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        login.setOnClickListener(this);

        register = findViewById(R.id.register);
        register.setOnClickListener(this);
        rember = findViewById(R.id.rember);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.login:
                String name = username.getText().toString();
                String pass = password.getText().toString();
                Log.i("TAG", name + "_" + pass);
                UserService uService = new UserService(SqlLiteLoginActivity.this);
                boolean flag = uService.login(name, pass);
                if (flag) {
                    Log.i("TAG", "登录成功");
                    Toast toast = Toast.makeText(SqlLiteLoginActivity.this, "登录成功", Toast.LENGTH_SHORT);
                    toast.setText("登录成功");
                    toast.show();
                    SharedPreferences.Editor edit = sp.edit();
                    //记住密码功能
                    if (rember.isChecked()) {
                        edit.putString("account", name);
                        edit.putString("password", pass);
                    } else {
                        edit.clear();
                    }
                    edit.commit();
                    Intent it = new Intent();
                    it.setClass(this, MainActivity.class);
                    startActivity(it);
                } else {
                    Log.i("TAG", "登录失败");
                    Toast toast = Toast.makeText(SqlLiteLoginActivity.this, "登录失败", Toast.LENGTH_SHORT);
                    toast.setText("登录失败");
                    toast.show();
                }
                break;
            case R.id.register:
                Intent intent = new Intent(this, SqlRegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
