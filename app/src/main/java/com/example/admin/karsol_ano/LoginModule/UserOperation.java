package com.example.admin.karsol_ano.LoginModule;

/**
 * Created by kavish on 09-10-2017.
 */
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.util.Log;

public class UserOperation {
    SQLiteDatabase  db;

    public UserOperation(Context ctx)
    {
       try {
           db = ctx.openOrCreateDatabase("sqldb", Context.MODE_PRIVATE, null);
           db.execSQL("create table if not exists student(email text,password text);");
       }
       catch(Exception e)
       {

          Log.d("error",e.getMessage());
       }


    }
    public String check(User user){
        String msg=null;
        try {
            Cursor c = db.rawQuery("Select * from student where email='"+user.getEmail()+"',passowrd='"+user.getPassword()+"''", null);
            boolean x= c.moveToFirst();
            if(x)
            {
                msg="success";
            }
            else
            {
                msg="error";
            }

        }catch (Exception e){
            msg = "error";
        }
        return msg;
    }
    public User getUser( )
    {User user=null;
        try {
            Cursor c = db.rawQuery("Select * from student ", null);
            if(c.moveToFirst()) {
                user = new User();
                user.setEmail(c.getString(0));
                user.setPassword(c.getString(1));
            }
            else{
                user = null;
            }
        }catch (Exception e){
            user=null;
        }
        return user;
    }
    public String Change(User user){
        String msg=null,sql=null;
        try {
            String x = check(user);
            if (x.equals("success")) {//update
                sql = "Update student set password='" + user.getPassword() + "', email='" + user.getEmail() + "'";
                db.execSQL(sql);
                msg = "success";
            } else {//insert
                db.execSQL("Delete from student");
                sql = "Insert into student values('" + user.getEmail() + "','" + user.getPassword() + "')";
                db.execSQL(sql);
                msg = "success";
            }
        }catch(Exception e){
            msg = "error";
        }
        return msg;

    }


}
