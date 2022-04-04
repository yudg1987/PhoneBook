package com.dgyu.phonebook;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dgyu.phonebook.domain.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecycleView;
    private MyRecycleViewAdapter mAdapter;//适配器
    private LinearLayoutManager mLinearLayoutManager;//布局管理器
    private List mList;
    final public static int REQUEST_CODE_ASK_CALL_PHONE=123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

        mRecycleView = findViewById(R.id.rv_list);
        //初始化数据
        initData();
        //创建布局管理器，垂直设置LinearLayoutManager.VERTICAL，水平设置LinearLayoutManager.HORIZONTAL
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //创建适配器，将数据传递给适配器
        mAdapter = new MyRecycleViewAdapter(this, mList);
        //设置布局管理器
        mRecycleView.setLayoutManager(mLinearLayoutManager);
        //设置适配器adapter
        mRecycleView.setAdapter(mAdapter);


    }

    /**
     * 检查权限后的回调
     * @param requestCode 请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_ASK_CALL_PHONE: //拨打电话
                if (permissions.length != 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {//失败
                    Toast.makeText(this, "请允许拨号权限后再试", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }*/

    //动态权限申请后处理
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted callDirectly(mobile);
                } else {
                    // Permission Denied Toast.makeText(MainActivity.this,"CALL_PHONE Denied", Toast.LENGTH_SHORT) .show();
                    Toast.makeText(this, "请允许拨号权限后再试", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private void initUI() {
        mRecycleView = findViewById(R.id.rv_list);
    }

    private void initData() {
        UserService uService = new UserService(this);
        mList = uService.findAllUsers();
    }

    public void zhuxiao(View v) {
        finish();
        Intent it = new Intent();
        it.setClass(this, SqlLiteLoginActivity.class);
    }

    public static class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.MyHolder> {
        private List mList;//数据源
        private Context mContext;

        MyRecycleViewAdapter(Context mContext, List list) {
            mList = list;
            this.mContext = mContext;
        }

        //创建ViewHolder并返回，后续item布局里控件都是从ViewHolder中取出
        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //将我们自定义的item布局R.layout.recycle_view_item转换为View
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_view_item, parent, false);
            //将view传递给我们自定义的ViewHolder
            MyHolder holder = new MyHolder(view);
            //返回这个MyHolder实体
            return holder;
        }

        //通过方法提供的ViewHolder，将数据绑定到ViewHolder中
        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            User user = (User) mList.get(position);
            //holder.username.setText("姓名:" + user.getUsername() + ",密码：" + user.getPassword() + ",年龄：" + user.getAge() + ",性别:" + user.getSex());
            //holder.textView.setText(mList.get(position).toString());
            holder.pic.setImageResource(user.getPic() == 0 ? R.mipmap.ic_launcher : user.getPic());
            holder.username.setText(user.getUsername());
            holder.phone.setText(user.getPhone());
            //编辑功能 跳转到注册页面
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent();
                    it.setClass(mContext, SqlRegisterActivity.class);
                    it.putExtra("editUserName", user.getUsername());
                    mContext.startActivity(it);
                }
            });
            //拨号功能
            holder.call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Build.VERSION.SDK_INT >= 23) {
                        int checkCallPhonePermission = ContextCompat.checkSelfPermission(mContext,
                                Manifest.permission.CALL_PHONE);
                        if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions((Activity) mContext, new String[] {
                                    Manifest.permission.CALL_PHONE
                            }, REQUEST_CODE_ASK_CALL_PHONE);
                            return;
                        } else {
                            // 上面已经写好的拨号方法 callDirectly(mobile);
                            System.out.println("Android23以上拨打了电话");
                            Log.i("onBindViewHolder", "拨打了电话");
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            Uri data = Uri.parse("tel:" + user.getPhone());
                            intent.setData(data);
                            mContext.startActivity(intent);
                        }
                    } else {
                        // 上面已经写好的拨号方法 callDirectly(mobile);
                        System.out.println("Android23以下拨打了电话");
                        Log.i("onBindViewHolder", "拨打了电话");
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:" + user.getPhone());
                        intent.setData(data);
                        mContext.startActivity(intent);
                    }


                }
            });
        }

        //获取数据源总的条数
        @Override
        public int getItemCount() {
            return null == mList ? 0 : mList.size();
        }

        /**
         * 自定义的ViewHolder
         */
        class MyHolder extends RecyclerView.ViewHolder {
            ImageView pic;
            TextView username;
            TextView phone;
            Button edit;
            Button call;

            public MyHolder(View itemView) {
                super(itemView);
                pic = itemView.findViewById(R.id.pic);
                username = itemView.findViewById(R.id.username);
                phone = itemView.findViewById(R.id.phone);
                edit = itemView.findViewById(R.id.edit);
                call = itemView.findViewById(R.id.call);
            }
        }
    }
}
