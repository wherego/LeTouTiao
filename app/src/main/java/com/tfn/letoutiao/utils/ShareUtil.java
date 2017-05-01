package com.tfn.letoutiao.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by tf on 2017/4/29.
 */

public class ShareUtil {

    public static void share(Context context, String shareContent) {
        StringBuffer sb = new StringBuffer();
        sb.append(shareContent);
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, sb.toString());
            context.startActivity(Intent.createChooser(intent, "分享该内容到"));
        } catch (Exception e) {
            Toast.makeText(context, "该手机不支持该操作", Toast.LENGTH_LONG).show();
        }
    }

}
