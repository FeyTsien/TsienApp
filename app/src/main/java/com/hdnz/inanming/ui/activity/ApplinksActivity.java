package com.hdnz.inanming.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ApplinksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Uri uri = intent.getData();
        String host = uri.getHost();
        String path = uri.getPath();
        if (host.equals("这里填写域名")) {
            if (path.equals("链接路径")) {
                // 跳转app指定A界面
            } else if (path.equals("链接路径")) {
                // 跳转app指定B界面
            } else {
            }
        }
    }
}
