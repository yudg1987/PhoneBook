package com.dgyu.phonebook;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dgyu.phonebook.model.User;

/**
 * 忘记密码模块
 */
public class ForgetActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_back;
    private Button btn_query;
    private EditText account;
    private EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        btn_query = findViewById(R.id.btn_query);
        btn_query.setOnClickListener(this);

        account = findViewById(R.id.account);
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
        switch (id) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_query:
                String accountInput = account.getText().toString();
                String phoneInput = phone.getText().toString();
                if (null == accountInput || "".equals(accountInput)) {
                    Toast.makeText(this, "账号不能为空", 0).show();
                    return;
                }
                if (null == phoneInput || "".equals(phoneInput)) {
                    Toast.makeText(this, "手机不能为空", 0).show();
                    return;
                }
                for (User user : RegisterActivity.users) {
                    if (user.getAccount().equals(accountInput) && user.getPhone().equals(phoneInput)) {
                        Toast.makeText(this, "查询成功密码是:" + user.getPassword(), 0).show();
                        return;
                    }
                }
                Toast.makeText(this, "密码查找失败", 0).show();
                break;
        }
    }
}
