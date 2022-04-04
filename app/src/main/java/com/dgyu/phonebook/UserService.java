package com.dgyu.phonebook;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dgyu.phonebook.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private DatabaseHelper dbHelper;

    public UserService(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    //登录用
    public boolean login(String username, String password) {
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        String sql = "select * from user where username=? and password=?";
        Cursor cursor = sdb.rawQuery(sql, new String[]{username, password});
        if (cursor.moveToFirst() == true) {
            cursor.close();
            sdb.close();
            return true;
        }
        sdb.close();
        return false;
    }

    //查询所有用户
    public List<User> findAllUsers() {
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        String sql = "select * from user ";
        List<User> users = new ArrayList<>();
        Cursor cursor = sdb.rawQuery(sql, null);
        if (null != cursor && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String username = cursor.getString(1);
                String password = cursor.getString(2);
                int age = cursor.getInt(3);
                String sex = cursor.getString(4);
                int pic = cursor.getInt(5);
                String phone = cursor.getString(6);
                User u = new User();
                u.setUsername(username);
                u.setPassword(password);
                u.setAge(age);
                u.setSex(sex);
                u.setPic(pic);
                u.setPhone(phone);
                users.add(u);
            }
        }
        sdb.close();
        return users;
    }

    //根据用户名查询用户
    public User findUserByUserName(String usernameParam) {
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        String sql = "select * from user where username=?";
        Cursor cursor = sdb.rawQuery(sql, new String[]{usernameParam});
        if (null != cursor && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String username = cursor.getString(1);
                String password = cursor.getString(2);
                int age = cursor.getInt(3);
                String sex = cursor.getString(4);
                int pic = cursor.getInt(5);
                String phone = cursor.getString(6);
                User u = new User();
                u.setUsername(username);
                u.setPassword(password);
                u.setAge(age);
                u.setSex(sex);
                u.setPic(pic);
                u.setPhone(phone);
                u.setId(id);
                return u;
            }
        }
        sdb.close();
        return null;
    }

    //登录用
    public boolean checkAccountIsExists(String username) {
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        String sql = "select * from user where username=?";
        Cursor cursor = sdb.rawQuery(sql, new String[]{username});
        if (cursor.moveToFirst() == true) {
            cursor.close();
            sdb.close();
            return true;
        }
        sdb.close();
        return false;
    }

    //注册用户
    public boolean register(User user) {
        SQLiteDatabase sdb = dbHelper.getWritableDatabase();
        String sql = "insert into user(username,password,age,sex,phone,pic) values(?,?,?,?,?,?)";
        Object[] obj = {user.getUsername(), user.getPassword(), user.getAge(), user.getSex(), user.getPhone(), user.getPic()};
        sdb.execSQL(sql, obj);
        sdb.close();
        return true;
    }

    //修改用户
    public  boolean update(User user) {
        SQLiteDatabase sdb = dbHelper.getWritableDatabase();
        String sql="update user set  username=?,password=?,age=?,sex=?,phone=?,pic=? where id=?";
        Object[] obj = {user.getUsername(), user.getPassword(), user.getAge(), user.getSex(), user.getPhone(), user.getPic(),user.getId()};
        sdb.execSQL(sql, obj);
        sdb.close();
        return true;
    }
}
