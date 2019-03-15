//package com.hdnz.inanming.ui.fragment.message;
//
//
//import android.view.View;
//
//import com.blankj.utilcode.util.ActivityUtils;
//import com.hdnz.inanming.R;
//import com.hdnz.inanming.mvp.contract.MVPContract;
//import com.hdnz.inanming.mvp.presenter.MVPPresenter;
//import com.hdnz.inanming.mvp.view.MVPFragment;
//import com.hdnz.inanming.ui.activity.message.GovernmentMessagesActivity;
//import com.hdnz.inanming.ui.activity.message.SystemMessagesActivity;
//import com.tsienlibrary.bean.CommonBean;
//
//import butterknife.OnClick;
//
//
///**
// * ================
// * ===== 消息 =====
// * ================
// */
//
//public class MessageFragmentB extends MVPFragment<MVPContract.View, MVPPresenter> {
//
//    public static MessageFragmentB newInstance() {
//        return new MessageFragmentB();
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.fragment_message;
//    }
//
//    @Override
//    protected void initData() {
//
//    }
//
//    @Override
//    protected void initView() {
//
//    }
//
//
//    /**
//     * 政务办理、系统消息
//     */
//    @OnClick({R.id.rl_government_affairs, R.id.rl_system_messages})
//    void onClicks(View view) {
//
//        switch (view.getId()) {
//            case R.id.rl_government_affairs:
//                ActivityUtils.startActivity(GovernmentMessagesActivity.class);
//                break;
//            case R.id.rl_system_messages:
//                ActivityUtils.startActivity(SystemMessagesActivity.class);
//                break;
//        }
//    }
//
//
//    @Override
//    public void requestSuccess(String requestUrl, CommonBean commonBean) {
//
//    }
//
//    @Override
//    public void requestFail(String requestUrl,String msg,int code) {
//
//    }
//}
