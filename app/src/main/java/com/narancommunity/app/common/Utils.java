package com.narancommunity.app.common;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.narancommunity.app.interfaces.PicLoadCallBack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;
import static android.util.TypedValue.COMPLEX_UNIT_IN;
import static android.util.TypedValue.COMPLEX_UNIT_MM;
import static android.util.TypedValue.COMPLEX_UNIT_PT;
import static android.util.TypedValue.COMPLEX_UNIT_PX;
import static android.util.TypedValue.COMPLEX_UNIT_SP;

/**
 * Written by fancy on 2017/3/7.
 */
public class Utils {

    /**
     * Try to return the absolute file path from the given Uri
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public boolean checkCameraPermissions(Activity context) {
        final int MY_PERMISSIONS_REQUEST_USE_CAMERA_CALL = 1;
        final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 2;

        if (ContextCompat.checkSelfPermission(context, "android.permission.CAMERA") != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(context, "android.permission.CAMERA")) {
//                showMessageOKCancel("You need to allow access to your camera",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(context,
                        new String[]{"android.permission.CAMERA"}, MY_PERMISSIONS_REQUEST_USE_CAMERA_CALL);
//                            }
//                        });
                return false;
            }
            ActivityCompat.requestPermissions(context, new String[]{"android.permission.CAMERA"}, MY_PERMISSIONS_REQUEST_USE_CAMERA_CALL);
            return false;
        } else if (ContextCompat.checkSelfPermission(context, "android.permission.RECORD_AUDIO") != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(context, "android.permission.RECORD_AUDIO")) {
//                showMessageOKCancel("You need to allow GOJI to record audio",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(context,
                        new String[]{"android.permission.RECORD_AUDIO"}, MY_PERMISSIONS_REQUEST_RECORD_AUDIO);
//                            }
//                        });
                return false;
            }
            ActivityCompat.requestPermissions(context, new String[]{"android.permission.RECORD_AUDIO"}, MY_PERMISSIONS_REQUEST_RECORD_AUDIO);
            return false;
        }
        return true;
    }

    public static String checkUserNameValue(String value) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0, length = value.length(); i < length; i++) {
            char c = value.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                stringBuffer.append(String.format("\\u%04x", (int) c));
            } else {
                stringBuffer.append(c);
            }
        }
        return stringBuffer.toString();
    }

    public static void requestPermission(Activity context) {
        // TODO Auto-generated method stub
        // 6.0以上系统才可以判断权限

        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE) {
//            // 进入设置系统应用权限界面
//            Intent intent = new Intent(Settings.ACTION_SETTINGS);
//            context.startActivity(intent);
//            return;
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {// 运行系统在5.x环境使用
//            // 进入设置系统应用权限界面
//            Intent intent = new Intent(Settings.ACTION_SETTINGS);
//            context.startActivity(intent);
//            return;
//        }
        return;
    }

    public static String[] listToArray(List<String> list) {
        if (list != null && list.size() > 0) {
            String[] array = new String[list.size()];
            String[] s = list.toArray(array);
            return s;
        } else {
            return new String[]{};
        }

    }

    public static List<String> arrayToList(String[] list) {
        if (list != null && list.length > 0)
            return Arrays.asList(list);
        else
            return new ArrayList<>();
    }

    //    public static String getDate(String time, String format) {
//        DateFormat formatter = new SimpleDateFormat(format);
//        Calendar calendar = Calendar.getInstance();
//        long ss = Long.parseLong(time);
//        calendar.setTimeInMillis(ss);
//        return formatter.format(calendar.getTime());
//    }

//    public static void showShare(Context context) {
//        ShareSDK.initSDK(context);
//        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
//        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
//        oks.setTitle("标题");
//        // titleUrl是标题的网络链接，QQ和QQ空间等使用
//        oks.setTitleUrl("http://sharesdk.cn");
//        // text是分享文本，所有平台都需要这个字段
//        oks.setText("我是分享文本");
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://sharesdk.cn");
//        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
//        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(context.getString(R.string.app_name));
//        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");
//        // 启动分享GUI
//        oks.show(context);
//    }

    public static String getValue(Object value) {
        if (null == value)
            return "";
        else
            return value.toString();
    }

    public static Integer getValue(Integer value) {
        if (null == value)
            return 0;
        else
            return value;
    }

    /**
     * 获得日期
     */
    public static String getFormatedDateTime(String pattern, long dateTime) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
        return sDateFormat.format(new Date(dateTime + 0));
    }

    /**
     * 日期字符串形式转成毫秒值
     *
     * @param time   2016-12-20 12:30
     * @param format yyyy-MM-DD HH:mm
     * @return
     */
    public static String stringToTimeMillis(String time, String format) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat(format).parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return c.getTimeInMillis() + "";
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context
     * @param dpValue 要转换的dp值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context
     * @param pxValue 要转换的px值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getStateBar(Activity context) {
        /**
         * 获取状态栏高度——方法3
         * 应用区的顶端位置即状态栏的高度
         * *注意*该方法不能在初始化的时候用
         * */
        Rect rectangle = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
        return rectangle.top;
    }

    public final static String MD5(String pwd) {
        //用于加密的字符
        char md5String[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            //使用平台的默认字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中
            byte[] btInput = pwd.getBytes();

            //信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
            MessageDigest mdInst = MessageDigest.getInstance("MD5");

            //MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要
            mdInst.update(btInput);

            // 摘要更新之后，通过调用digest（）执行哈希计算，获得密文
            byte[] md = mdInst.digest();

            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {   //  i = 0
                byte byte0 = md[i];  //95
                str[k++] = md5String[byte0 >>> 4 & 0xf];    //    5
                str[k++] = md5String[byte0 & 0xf];   //   F
            }

            //返回经过加密后的字符串
            return new String(str);

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取文件夹大小
     *
     * @param file File实例
     * @return long
     */
    public static long getFolderSize(java.io.File file) {

        long size = 0;
        try {
            java.io.File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);

                } else {
                    size = size + fileList[i].length();

                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //return size/1048576;
        return size;
    }

    /**
     * 删除指定目录下文件及目录
     *
     * @param filePath       文件夹路径
     * @param deleteThisPath 是否删除这个文件夹
     * @return
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "KB";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    public static boolean isEmpty(String str) {
        if (str == null || str.equals("")) {
            return true;
        }
        return false;
    }

    //    public static void setImgLRound(Context context, Object imgUrl, ImageView imageView) {
//        if (context != null && imgUrl != null && !imgUrl.equals("")) {
//            try {
//                Glide.with(context)
//                        .load(imgUrl)
//                        .centerCrop()
//                        .placeholder(R.drawable.img_loading_l)
//                        .dontAnimate()
//                        .into(imageView);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void setImgL(Context context, Object imgUrl, ImageView imageView) {
//        if (context != null && imgUrl != null && !imgUrl.equals("")) {
//
//            try {
//                Glide.with(context)
//                        .load(imgUrl)
//                        .centerCrop()
//                        .dontAnimate()
//                        .placeholder(R.drawable.img_loading_l)
//                        .into(imageView);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
    public static void setImgF(Context context, Object imgUrl, ImageView imageView) {
        if (context != null && imgUrl != null && !imgUrl.equals("")) {
            try {
                Glide.with(context)
                        .load(imgUrl)
                        .asBitmap()
                        .centerCrop()
                        .dontAnimate()
//                        .placeholder(R.drawable.img_loading_f)
                        .into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Glide保存图片
    public static void savePicture(Context context, final String fileName, String url, final PicLoadCallBack listener) {
        if (url.equals("")) {
            Toaster.toast(context, "未查询到图片！");
            return;
        }
        Glide.with(context).load(url).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
            @Override
            public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                try {
                    String address = savaFileToSD(fileName, bytes);
                    listener.OnComplete(address);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //往SD卡写入文件的方法
    public static String savaFileToSD(String filename, byte[] bytes) throws Exception {
        //如果手机已插入sd卡,且app具有读写sd卡的权限
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filePath = Environment.getExternalStorageDirectory().getCanonicalPath() + "/wishSea";
            File dir1 = new File(filePath);
            if (!dir1.exists()) {
                dir1.mkdirs();
            }
            filename = filePath + "/" + filename;
            //这里就不要用openFileOutput了,那个是往手机内存中写数据的
            FileOutputStream output = new FileOutputStream(filename);
            output.write(bytes);
            //将bytes写入到输出流中
            output.close();
            Log.i("fancy", "图片已成功保存到" + filePath);
            return filename;
            //关闭输出流
        } else {
            Log.i("fancy", "SD卡不存在或者不可读写");
            return "";
        }
    }

//
//    /**
//     * 设置宽的长款
//     *
//     * @param context
//     * @param imgUrl
//     * @param imageView
//     */
//    public static void setImgLW(Context context, Object imgUrl, ImageView imageView) {
//        if (context != null && imgUrl != null && !imgUrl.equals("")) {
//
//            try {
//                Glide.with(context)
//                        .load(imgUrl)
//                        .centerCrop()
//                        .placeholder(R.drawable.wzxq_zhanwei)
//                        .into(imageView);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void setImgRound(Context context, Object imgUrl, ImageView imageView) {
//        if (context != null && imgUrl != null && !imgUrl.equals("")) {
//
//            try {
//                Glide.with(context)
//                        .load(imgUrl)
//                        .centerCrop()
//                        .dontAnimate()
//                        .placeholder(R.drawable.img_loading_f)
//                        .into(imageView);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public static String getDeviceID(Context context) {
        //IMEI（imei）
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "android";
        }
        String imei = tm.getDeviceId();
        if (imei == null || imei.equals(""))
            imei = tm.getSimSerialNumber();
        return imei;
    }

    /**
     * 格式为 yyyy-MM-dd HH:mm:ss"
     *
     * @param startTime
     * @return 格式为System.currentTimeMillion
     */
    public static String stringTimeToMillion(String startTime) {
        if (startTime.equals("")) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
        Date date = null;
        try {
            date = sdf.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long start = date.getTime();
        return start + "";
    }

//    /**
//     *
//     *
//     * @param startTime
//     * @return
//     * @throws ParseException
//     */
//    public static String dateDiffSecond(String startTime) {
//        if (startTime == null || startTime.equals("null") || startTime.equals(""))
//            return "史前世纪吧";
//        long endTime = System.currentTimeMillis();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
//        Date date = null;
//        try {
//            date = sdf.parse(startTime);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        long start = date.getTime();
//        // 按照传入的格式生成一个simpledateformate对象
//        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
//        long nh = 1000 * 60 * 60;// 一小时的毫秒数
//        long nm = 1000 * 60;// 一分钟的毫秒数
//        long ns = 1000;// 一秒钟的毫秒数
//        long diff;
//        long day = 0;
//        long hour = 0;
//        long min = 0;
//        long sec = 0;
//        // 获得两个时间的毫秒时间差异
//        diff = endTime - start;
//        day = diff / nd;// 计算差多少天
//        hour = diff % nd / nh + day * 24;// 计算差多少小时
//        min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
//        sec = diff % nd % nh % nm / ns;// 计算差多少秒
//        // 输出结果
//        if (day >= 365) {
//            return getDate(startTime, "yyyy年MM月dd日");
//        } else if (day >= 1) {
//            return getDate(startTime, "MM月dd日");
//        } else if (hour > 0) {
//            return hour + "小时前";
//        } else if (min > 5) {
//            return min + "分钟前";
//        } else {
//            return "刚刚";
//        }
//    }

    //获得日期
    public static String getDate(String time, String format) {
        DateFormat formatter = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        long ss = Long.parseLong(time);
        calendar.setTimeInMillis(ss);
        return formatter.format(calendar.getTime());
    }

    /**
     * 格式为 System.currentTimeMillion
     *
     * @param startTime
     * @return
     */
    public static String dateDiff(String startTime) {
        if (startTime == null || startTime.equals("null") || startTime.equals(""))
            return "史前世纪吧";
        long endTime = System.currentTimeMillis();
        long start = Long.parseLong(startTime);
        // 按照传入的格式生成一个simpledateformate对象
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        // 获得两个时间的毫秒时间差异
        diff = endTime - start;
        day = diff / nd;// 计算差多少天
        hour = diff % nd / nh + day * 24;// 计算差多少小时
        min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
        sec = diff % nd % nh % nm / ns;// 计算差多少秒
        // 输出结果
        if (day >= 365) {
            return getDate(startTime, "yyyy年MM月dd日");
        } else if (day >= 1) {
            return getDate(startTime, "MM月dd日");
        } else if (hour > 0) {
            return hour + "小时前";
        } else if (min > 5) {
            return min + "分钟前";
        } else {
            return "刚刚";
        }
    }

    /**
     * 获取软件版本号
     * 1.0.1这种形式
     */
    public static String getAPPVersionName(Context context) {
        PackageManager pm = context.getPackageManager();//得到PackageManager对象
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);//得到PackageInfo对象，封装了一些软件包的信息在里面
            String appVersion = pi.versionName;//获取清单文件中versionCode节点的值
            return appVersion + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static void setGridViewHeightBasedOnChildren(GridView gridView, int columns) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int items = listAdapter.getCount();
        int rows = 0;

        View listItem = listAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight();

        float x = 1;
        if (items > columns) {
            x = items / columns;
            rows = (int) (x + 1);
            totalHeight *= rows;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);

    }

    public static void showSoftInput(EditText editText) {
        InputMethodManager imm = (InputMethodManager) editText.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(editText, 0);
        }
    }

    public static void hideSoftInput(EditText editText) {
        InputMethodManager imm = (InputMethodManager) editText.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    public static void hideSoftInput(Activity context) {
        InputMethodManager inputMethodManager = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken()
                , InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void toggleSoftInput(EditText editText) {
        InputMethodManager imm = (InputMethodManager) editText.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInputFromWindow(editText.getWindowToken(),
                    0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static boolean isHavePermission(Context context, String mPermission) {
        PackageManager pm = context.getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission(/*"android.permission.RECORD_AUDIO"*/mPermission, "com.naran.narancommunity"));
        return permission;
    }

    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }


    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;
        Object obj = beanClass.newInstance();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }
        return obj;
    }

    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
        return map;
    }


    public static void setActivityMenuColor(final Activity activity, final int color, final String text) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        if (inflater.getFactory() != null) {
            inflater = inflater.cloneInContext(activity);
        }
        inflater.setFactory(
                new LayoutInflater.Factory() {
                    public View onCreateView(String name, Context context,
                                             AttributeSet attrs) {
                        // 指定自定义inflate的对象
                        if (name.equalsIgnoreCase("com.android.internal.view.menu.IconMenuItemView")
                                || name.equalsIgnoreCase("com.android.internal.view.menu.ActionMenuItemView")) {
                            try {
                                LayoutInflater f = activity.getLayoutInflater();
                                final View view = f.createView(name, null,
                                        attrs);
                                if (view instanceof TextView) {
                                    new Handler().post(new Runnable() {
                                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                                        public void run() {
                                            // view.setBackgroundResource(R.color.login_btn_normal);// 设置背景图片
                                            ((TextView) view).setText(text);
                                            ((TextView) view).setTextColor(activity
                                                    .getResources()
                                                    .getColor(color));
                                        }
                                    });
                                }
                                return view;
                            } catch (InflateException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                        return null;
                    }
                });
    }

    public static void setFragmentActivityMenuColor(FragmentActivity context, final int color) {
        final LayoutInflater layoutInflater = context.getLayoutInflater();
        final LayoutInflater.Factory existingFactory = layoutInflater
                .getFactory();
        try {
            Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
            field.setAccessible(true);
            field.setBoolean(layoutInflater, false);
            context.getLayoutInflater().setFactory(
                    new LayoutInflater.Factory() {
                        @Override
                        public View onCreateView(String name,
                                                 final Context context, AttributeSet attrs) {
                            if (name.equalsIgnoreCase("com.android.internal.view.menu.IconMenuItemView")
                                    || name.equalsIgnoreCase("com.android.internal.view.menu.ActionMenuItemView")) {
                                View view = null;
                                // if a factory was already set, we use the
                                // returned view
                                if (existingFactory != null) {
                                    view = existingFactory.onCreateView(name,
                                            context, attrs);
                                    if (view == null) {
                                        try {
                                            view = layoutInflater.createView(
                                                    name, null, attrs);
                                            final View finalView = view;
                                            if (view instanceof TextView) {
                                                new Handler()
                                                        .post(new Runnable() {
                                                            public void run() {
                                                                ((TextView) finalView)
                                                                        .setTextColor(context
                                                                                .getResources()
                                                                                .getColor(color));
                                                            }
                                                        });
                                            }
                                            return finalView;
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                return view;
                            }
                            return null;
                        }
                    });
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void showErrorToast(Context context, Throwable throwable) {
        String message = throwable.getMessage();
        if (message == null || message.equals(""))
            Toaster.toast(context, "啊哦，好像网络没反应啊！");
        else
            Toaster.toast(context, throwable.getMessage());
    }

    public static String getFormatPhone(String userPhone) {
        return userPhone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static final int SIZETYPE_B = 1;// 获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;// 获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;// 获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;// 获取文件大小单位为GB的double值

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFileOrFilesSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return FormetFileSize(blockSize, sizeType);
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFileOrFilesSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return FormetFileSize(blockSize);
    }

    /**
     * 获取指定文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    private static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    private static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df
                        .format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }


    public static float applyDimension(int unit, float value, Context context) {
        DisplayMetrics metrics = getScreenMetrics(context);
        switch (unit) {
            case COMPLEX_UNIT_PX:
                return value;
            case COMPLEX_UNIT_DIP:
                return value * metrics.density;
            case COMPLEX_UNIT_SP:
                return value * metrics.scaledDensity;
            case COMPLEX_UNIT_PT:
                return value * metrics.xdpi * (1.0f / 72);
            case COMPLEX_UNIT_IN:
                return value * metrics.xdpi;
            case COMPLEX_UNIT_MM:
                return value * metrics.xdpi * (1.0f / 25.4f);
        }
        return 0;
    }

    public static DisplayMetrics getScreenMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }
}
