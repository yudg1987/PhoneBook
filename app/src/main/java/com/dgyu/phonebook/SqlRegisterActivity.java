package com.dgyu.phonebook;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    private ImageView pic;
    private int[] pics = {R.mipmap.a, R.mipmap.b, R.mipmap.d};
    private int picPostion = R.mipmap.ic_launcher;
    private String editUserName;
    private User editUser;

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

        pic = findViewById(R.id.pic);
        pic.setOnClickListener(this);

        //编辑功能赋值
        editUserName = getIntent().getStringExtra("editUserName");
        if (!TextUtils.isEmpty(editUserName)) {
            UserService uService = new UserService(SqlRegisterActivity.this);
            editUser = uService.findUserByUserName(editUserName);
            username.setText(editUser.getUsername());
            password.setText(editUser.getPassword());
            //此处有坑，必须转化为String类型才行
            age.setText(editUser.getAge() == 0 ? "" : editUser.getAge() + "");
            String sexStr=editUser.getSex();
            //便利RadioButton
            for (int i = 0; i < sex.getChildCount(); i++) {
                RadioButton radioButton=(RadioButton)sex.getChildAt(i);
                String radioButtonText=(String)radioButton.getText();
                if(sexStr.equals(radioButtonText)){
                    radioButton.setChecked(true);
                    break;
                }
            }
            phone.setText(editUser.getPhone());
            pic.setImageResource(editUser.getPic() == 0 ? picPostion : editUser.getPic());

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
        switch (id) {
            case R.id.Register:
                String name = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String phoneInput = phone.getText().toString().trim();
                if (null == name || "".equals(name)) {
                    Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (null == pass || "".equals(pass)) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (null == phoneInput || "".equals(phoneInput)) {
                    Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                String agestr = age.getText().toString().trim();
                String sexstr = ((RadioButton) SqlRegisterActivity.this.findViewById(sex.getCheckedRadioButtonId())).getText().toString();
                Log.i("TAG", name + "_" + pass + "_" + agestr + "_" + sexstr);
                User user = new User();
                user.setUsername(name);
                user.setPassword(pass);
                user.setAge(Integer.parseInt(agestr));
                user.setSex(sexstr);
                user.setPhone(phoneInput);
                user.setPic(picPostion);
                UserService uService = new UserService(SqlRegisterActivity.this);
                //新增操作
                if (TextUtils.isEmpty(editUserName)) {
                    if (uService.checkAccountIsExists(name)) {
                        Toast.makeText(SqlRegisterActivity.this, "用户" + name + "已存在", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    uService.register(user);
                    Toast.makeText(SqlRegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                } else {//编辑操作
                    user.setId(editUser.getId());
                    uService.update(user);
                    Toast.makeText(SqlRegisterActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                }
                Intent it = new Intent();
                it.setClass(this, SqlLiteLoginActivity.class);
                startActivity(it);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.pic:
                //头像选择
                AlertDialog.Builder builder = new AlertDialog.Builder(SqlRegisterActivity.this);
                PicAdapter picAdapter = new PicAdapter(SqlRegisterActivity.this, pics);
                builder.setTitle("选择头像").setSingleChoiceItems(picAdapter, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pic.setImageResource(pics[which]);
                        dialog.dismiss();
                        //保存选中的图片
                        picPostion = pics[which];
                    }
                });
                //show不调用，adapter也不会出发执行
                builder.show();
                break;

        }

    }
}

