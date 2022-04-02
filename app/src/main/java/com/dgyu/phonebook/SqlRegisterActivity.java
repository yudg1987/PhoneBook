package com.dgyu.phonebook;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.dgyu.phonebook.domain.User;

public class SqlRegisterActivity extends Activity implements View.OnClickListener {
    EditText username;
    EditText password;
    EditText age;
    RadioGroup sex;
    Button register, back;
    EditText phone;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_register);
        findViews();

    }

    private void findViews() {
        username = findViewById(R.id.usernameRegister);
        password = findViewById(R.id.passwordRegister);
        age = findViewById(R.id.ageRegister);
        sex = findViewById(R.id.sexRegister);
        phone = findViewById(R.id.phoneRegister);
        register = findViewById(R.id.Register);
        register.setOnClickListener(this);

        back = findViewById(R.id.back);
        back.setOnClickListener(this);
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
            case R.id.Register:
                String name = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String phoneInput = phone.getText().toString().trim();

                if (null == name || "".equals(name)) {
                    Toast.makeText(this, "账号不能为空", 0).show();
                    return;
                }
                if (null == pass || "".equals(pass)) {
                    Toast.makeText(this, "密码不能为空", 0).show();
                    return;
                }
                if (null == phoneInput || "".equals(phoneInput)) {
                    Toast.makeText(this, "手机号不能为空", 0).show();
                    return;
                }

                String agestr = age.getText().toString().trim();
                String sexstr = ((RadioButton) SqlRegisterActivity.this.findViewById(sex.getCheckedRadioButtonId())).getText().toString();
                Log.i("TAG", name + "_" + pass + "_" + agestr + "_" + sexstr);
                UserService uService = new UserService(SqlRegisterActivity.this);
                if (uService.checkAccountIsExists(name)) {
                    Toast.makeText(SqlRegisterActivity.this, "用户" + name + "已存在", Toast.LENGTH_LONG).show();
                    return;
                }
                User user = new User();
                user.setUsername(name);
                user.setPassword(pass);
                user.setAge(Integer.parseInt(agestr));
                user.setSex(sexstr);
                user.setPhone(phoneInput);
                uService.register(user);
                Toast.makeText(SqlRegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                Intent it = new Intent();
                it.setClass(this, SqlLiteLoginActivity.class);
                startActivity(it);
                break;
            case R.id.back:
                finish();
                break;

        }

    }
}

