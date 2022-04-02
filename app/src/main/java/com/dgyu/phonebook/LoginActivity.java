package com.dgyu.phonebook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dgyu.phonebook.model.User;

import java.util.List;

/**
 * 登录模块
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_login;
    private Button btn_register;
    private Button btn_forget;
    private Button btn_sqllite;

    private EditText account;
    private EditText password;

    private CheckBox rember;
    SharedPreferences sp=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp=getSharedPreferences("currentUser",MODE_PRIVATE);

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

        btn_forget = findViewById(R.id.btn_forget);
        btn_forget.setOnClickListener(this);

        btn_sqllite = findViewById(R.id.sqllite);
        btn_sqllite.setOnClickListener(this);

        account = findViewById(R.id.account);
        password = findViewById(R.id.password);

        rember = findViewById(R.id.rember);

        if(null!=sp){
            String accountPre=sp.getString("account",null);
            account.setText(accountPre);
            password.setText(sp.getString("password",null));
            if(!TextUtils.isEmpty(accountPre)){
                rember.setChecked(true);
            }
        }
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
                String accountInput = account.getText().toString();
                String passwordInput = password.getText().toString();
                if (null == accountInput || "".equals(accountInput)) {
                    Toast toast=Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT);
                    toast.setText("账号不能为空");
                    toast.show();
                    return;
                }
                if (null == passwordInput || "".equals(passwordInput)) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<User> users = RegisterActivity.users;
                if (null == users || users.size() <= 0) {
                    Toast.makeText(this, "用户不存在，登陆失败", Toast.LENGTH_SHORT).show();
                    break;
                }
                for (User user : users) {
                    if (user.getAccount().equals(accountInput) && user.getPassword().equals(passwordInput)) {
                        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();

                        SharedPreferences.Editor edit=sp.edit();
                        //记住密码功能
                        if(rember.isChecked()){
                            edit.putString("account",accountInput);
                            edit.putString("password",passwordInput);
                        }else{
                            edit.clear();
                        }
                        edit.commit();
                        return;
                    }
                }
                Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_register:
                gotoRegister(it);
                break;
            case R.id.btn_forget:
                gotoForget(it);
                break;
            case R.id.sqllite:
                it.setClass(this, SqlLiteLoginActivity.class);
                startActivity(it);
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
