package com.narancommunity.app.common;

import android.content.Context;
import android.widget.Toast;


/**
 * Created by Administrator on 2015/12/7.
 */
public class Toaster {
    public static Toast toast ;

    public static void toast(Context context, String info) {

        if (info == null || info.isEmpty()) {
            return;
        }
        if (toast != null){
            if(info.contains("token错误或过期")){
                info = "登录过期，请退出重新登录！";
            }
            toast.setText(info);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        } else{
            toast = Toast.makeText(context, info, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public static void toastLong(Context context, String info) {
        if (info == null || info.isEmpty()) {
            return;
        }
        if (toast != null){
            if(info.contains("token错误或过期")){
                info = "登录过期，请退出重新登录！";
            }
            toast.setText(info);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        } else{
            toast = Toast.makeText(context, info, Toast.LENGTH_LONG);
            toast.show();
        }
    }

//    public static void toastLong(String info) {
//        toastLong(NRApplication.getInstance(), info);
//    }
//
//    public static void toast(String info) {
//        toast(NRApplication.getInstance(), info);
//    }

}
