package com.tsienlibrary.utils;

import android.text.TextUtils;
import android.widget.TextView;

public class TextViewUitls {

    public static void setText(TextView textView, String text) {
        if (TextUtils.isEmpty(text)) {
            textView.setText("");
        } else {
            textView.setText(text);
        }
    }

    public static void setText(TextView textView, int text) {
        textView.setText(String.valueOf(text));
    }

    public static void setText(TextView textView, double text) {
        textView.setText(String.valueOf(text));
    }
}
