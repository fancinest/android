package com.narancommunity.app.common;

import android.content.Context;

import com.narancommunity.app.net.AppConstants;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

/**
 * writer：fancy on 2016/12/15 17:38
 * classname : 数据库类
 */
public class DBHelper {
    private static DB snappydb;

    public static DB getDB(Context context) {

        try {
            snappydb = DBFactory.open(context, AppConstants.DB_NAME);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        return snappydb;
    }

    public static void close() {
        if (snappydb != null)
            try {
                snappydb.close();
            } catch (SnappydbException e) {
                e.printStackTrace();
            }
    }
}
