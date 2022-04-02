package com.dgyu.phonebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dgyu.phonebook.domain.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecycleView;
    private MyRecycleViewAdapter mAdapter;//适配器
    private LinearLayoutManager mLinearLayoutManager;//布局管理器
    private List mList;

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
        mAdapter = new MyRecycleViewAdapter(mList);
        //设置布局管理器
        mRecycleView.setLayoutManager(mLinearLayoutManager);
        //设置适配器adapter
        mRecycleView.setAdapter(mAdapter);
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

        MyRecycleViewAdapter(List list) {
            mList = list;
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
            holder.pic.setImageResource(user.getPic() == 0 ?R.mipmap.ic_launcher:user.getPic());
            holder.username.setText(user.getUsername());
            holder.phone.setText(user.getPhone());
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

            public MyHolder(View itemView) {
                super(itemView);
                pic=itemView.findViewById(R.id.pic);
                username = itemView.findViewById(R.id.username);
                phone = itemView.findViewById(R.id.phone);
            }
        }
    }
}
