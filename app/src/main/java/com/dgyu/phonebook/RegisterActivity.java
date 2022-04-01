package com.dgyu.phonebook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dgyu.phonebook.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户注册模块
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    public static List<User> users = new ArrayList<>();
    private Button btn_save;
    private Button btn_back;
    private Button btn_reset;
    private EditText account;
    private EditText password;
    private EditText confirmPassword;
    private EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        btn_reset = findViewById(R.id.btn_reset);
        btn_reset.setOnClickListener(this);


        account = findViewById(R.id.account);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        phone = findViewById(R.id.phone);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        Log.i("id", id + "");
        switch (id) {
            case R.id.btn_save:
                String accountInput = account.getText().toString();
                String passwordInput = password.getText().toString();
                String confirmPasswordInput = confirmPassword.getText().toString();
                String phoneInput = phone.getText().toString();
                Log.i("account", accountInput);
                Log.i("passwordInput", passwordInput);
                Log.i("confirmPasswordInput", confirmPasswordInput);
                Log.i("phoneInput", phoneInput);
                if (null == accountInput || "".equals(accountInput)) {
                    Toast.makeText(this, "账号不能为空", 0).show();
                    return;
                }
                if (null == passwordInput || "".equals(passwordInput)) {
                    Toast.makeText(this, "密码不能为空", 0).show();
                    return;
                }
                if (!passwordInput.equals(confirmPasswordInput)) {
                    Toast.makeText(this, "密码与确认密码不一致", 0).show();
                    return;
                }
                if (null == phoneInput || "".equals(phoneInput)) {
                    Toast.makeText(this, "手机不能为空", 0).show();
                    return;
                }
                for (User user : users) {
                    if (user.getAccount().equals(accountInput) && user.getPassword().equals(passwordInput)) {
                        Toast.makeText(this, "用户已存在", 0).show();
                        return;
                    }
                }
                User user = new User();
                user.setAccount(accountInput);
                user.setPassword(passwordInput);
                user.setPhone(phoneInput);
                users.add(user);
                Intent it = new Intent();
                it.setClass(this, LoginActivity.class);
                startActivity(it);
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_reset:
                account.setText("");
                password.setText("");
                confirmPassword.setText("");
                phone.setText("");

                break;
        }
    }
}
