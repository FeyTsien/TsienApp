package com.tsienlibrary.loadsir.callback;


import com.kingja.loadsir.callback.Callback;
import com.tsienlibrary.R;

/**
 * Description:TODO
 * Create Time:2017/9/4 10:20
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class Error404Callback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_error_404;
    }
}