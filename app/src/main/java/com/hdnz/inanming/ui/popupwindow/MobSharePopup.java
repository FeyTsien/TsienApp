package com.hdnz.inanming.ui.popupwindow;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdnz.inanming.R;
import com.hdnz.inanming.bean.MobSharePlatformBean;
import com.hdnz.inanming.mobShare.MobShareParm;
import com.hdnz.inanming.mobShare.MobShareToDifferentPlatformHelper;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.tsienlibrary.mvp.GsonManger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.sharesdk.framework.Platform;
import razerdp.basepopup.BasePopupWindow;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2019/01/11
 *     desc   :
 * </pre>
 */
public class MobSharePopup extends MyBasePopupWindow{

    private static Map<String, String> shareMap;
    private List<MobSharePlatformBean> platformBeans;


    //@field  分享平台名称
    private String[] shareNames;
    // @field  分享平台类名称
    private String[] shareClassNames;
    // @field  分享平台图标
    private int[] shareIcons;

    RecyclerView rv_share_list;
    RecyclerViewAdapter<MobSharePlatformBean> adapter;

    public static MobSharePopup getInstance(Context context, String ShareData) {
        shareMap = GsonManger.getGsonManger().gsonFromat(ShareData, Map.class);
        return new MobSharePopup(context);
    }

    private MobSharePopup(Context context) {
        super(context);
    }


    @Override
    protected int getLayouId() {
        return R.layout.popup_mob_share;
    }

    @Override
    protected void initData() {
        platformBeans = new ArrayList<>();
        shareNames = getContext().getResources().getStringArray(R.array.share_names);
        shareClassNames = getContext().getResources().getStringArray(R.array.share_class_names);

        shareIcons = new int[]{R.drawable.ssdk_oks_classic_sinaweibo,
                R.drawable.ssdk_oks_classic_qzone, R.drawable.ssdk_oks_classic_wechat, R.drawable.ssdk_oks_classic_wechatmoments, R.drawable.ssdk_oks_classic_qq,
                R.drawable.ssdk_oks_classic_alipay, R.drawable.ssdk_oks_classic_alipaymoments, R.drawable.ssdk_oks_classic_dingding};

        for (int i = 0; i < shareNames.length; i++) {
            MobSharePlatformBean mobSharePlatformBean = new MobSharePlatformBean(shareNames[i], shareIcons[i]);
            platformBeans.add(mobSharePlatformBean);
        }

        LogUtils.e("mobSharePlatformBean: " + platformBeans);
    }

    @Override
    protected  void initView(View rootView) {

        adapter = new RecyclerViewAdapter<MobSharePlatformBean>(platformBeans, R.layout.item_mob_layout) {
            @Override
            public void bindView(RecyclerViewAdapter.MyViewHolder holder, int position) {
                holder.setImageView(R.id.share_icon, platformBeans.get(position).getPlatformIcon());
                holder.setTextView(R.id.share_text, platformBeans.get(position).getPlatformName());
            }
        };

        rv_share_list = rootView.findViewById(R.id.rv_share_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        rv_share_list.setLayoutManager(gridLayoutManager);


        rv_share_list.setAdapter(adapter);
        //item点击事件
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                ToastUtils.showShort(platformBeans.get(pos).getPlatformName());
                if (shareMap != null) {
                    MobShareToDifferentPlatformHelper.getInstance()
                            .init(getMobShare())
                            .share(shareClassNames[pos]);
                    dismiss();//关闭当前弹框
                }
            }
        });

        rootView.findViewById(R.id.share_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private MobShareParm getMobShare() {
        MobShareParm mobShareParm = new MobShareParm();

        mobShareParm.setShareType(Platform.SHARE_WEBPAGE);
//        mobShareParm.setTitle(shareMap.get("title"));
        mobShareParm.setTitleUrl(shareMap.get("shareUrl"));
        mobShareParm.setImageUrl(shareMap.get("picUrl"));
        mobShareParm.setText(shareMap.get("content"));
//                String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
//                absolutePath += "/tencent/MicroMsg/WeiXin/";
//                File file = new File(absolutePath);
//                String[] list = file.list();
//                mobShareParm.setImagePath(absolutePath + list[0]);
        mobShareParm.setUrl(shareMap.get("shareUrl"));
        return mobShareParm;
    }

}
