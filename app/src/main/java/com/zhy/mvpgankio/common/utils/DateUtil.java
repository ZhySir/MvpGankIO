package com.zhy.mvpgankio.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换
 * Created by zhy on 2019/1/18.
 */

public class DateUtil {

    /**
     * UTC格式转换
     * 2019-01-03T06:13:39.765Z
     *
     * @param dateStr
     * @return
     */
    public static String getUTCYMD(String dateStr) {
        String y = "";
        try {
            String z = dateStr.replace("Z", " UTC");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            Date date1 = sdf1.parse(z);
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            y = sdf2.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return y;
    }

}
