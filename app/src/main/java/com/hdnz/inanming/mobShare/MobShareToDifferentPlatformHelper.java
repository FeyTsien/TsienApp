package com.hdnz.inanming.mobShare;

import android.text.TextUtils;

import com.blankj.utilcode.util.ToastUtils;

import java.util.HashMap;

import cn.sharesdk.alipay.friends.Alipay;
import cn.sharesdk.alipay.moments.AlipayMoments;
import cn.sharesdk.dingding.friends.Dingding;
import cn.sharesdk.douban.Douban;
import cn.sharesdk.douyin.Douyin;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.system.email.Email;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.wechat.favorite.WechatFavorite;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import cn.sharesdk.youdao.YouDao;


/**
 * Copyright (C), 2017-2018, 华电南自（贵州）科技有限公司
 * FileName:    MobShareToDifferentPlatformHelper
 * Author:      肖昕
 * Date:        2018-10-9 13:42
 * Description: mob 根据不同平台设置分享内容辅助类
 * Version:     v1.0
 * History:     历史信息
 */
public class MobShareToDifferentPlatformHelper {
    /**
     * @field mob分享的参数类对象
     */
    private MobShareParm mobShareParm;
    /**
     * @field 当前类对象
     */
    private static volatile MobShareToDifferentPlatformHelper instance;
    /**
     * @field 分享设置的参数对象
     */
    private Platform.ShareParams shareParams;
    /**
     * @field 分享的平台
     */
    private Platform platform;

    /**
     * 私有构造方法
     */
    private MobShareToDifferentPlatformHelper() {

    }

    /**
     * 获取单例静态方法
     *
     * @return
     */
    public static MobShareToDifferentPlatformHelper getInstance() {
        if (instance == null) {
            synchronized (MobShareToDifferentPlatformHelper.class) {
                if (null == instance) {
                    instance = new MobShareToDifferentPlatformHelper();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化传递参数，此方法必须在share之前调用
     *
     * @param mobShareParm
     * @return
     */
    public MobShareToDifferentPlatformHelper init(MobShareParm mobShareParm) {
        this.mobShareParm = mobShareParm;
        return this;
    }

    /**
     * 分享方法
     *
     * @return
     */
    public MobShareToDifferentPlatformHelper share(String platform) {
        if (null == mobShareParm) {
            ToastUtils.showShort("分享内容错误...");
            return this;
        }
        if (platform.equals(SinaWeibo.NAME)) {
            //新浪微博
            return shareToSinaWeibo();
        }
        if (platform.equals(TencentWeibo.NAME)) {
            //腾讯微博
            return shareToTencentWeibo();
        }
        if (platform.equals(QZone.NAME)) {
            //QQ空间
            return shareToQZone();
        }
        if (platform.equals(Wechat.NAME)) {
            //微信
            return shareToWechat();
        }
        if (platform.equals(WechatMoments.NAME)) {
            //微信朋友圈
            return shareToWechatMoments();
        }
        if (platform.equals(WechatFavorite.NAME)) {
            //微信收藏
            return shareToWechatFavorite();
        }
        if (platform.equals(QQ.NAME)) {
            //QQ好友
            return shareToQQ();
        }
        if (platform.equals(Email.NAME)) {
            //邮件
            return shareToEmail();
        }
        if (platform.equals(ShortMessage.NAME)) {
            //短信
            return shareToShortMessage();
        }
        if (platform.equals(Douban.NAME)) {
            //豆瓣
            return shareToDouBan();
        }
        if (platform.equals(YouDao.NAME)) {
            //有道词典
            return shareToYouDao();
        }
        if (platform.equals(Alipay.NAME)) {
            //支付宝
            return shareToAlipay();
        }
        if (platform.equals(AlipayMoments.NAME)) {
            //支付宝朋友圈
            return shareToAlipayMoments();
        }
        if (platform.equals(Dingding.NAME)) {
            //钉钉
            return shareToDingDing();
        }
        if (platform.equals(Douyin.NAME)) {
            //抖音小视频
            return shareToDouYin();
        }
        return this;
    }

    /**
     * 分享到QQ好友
     *
     * @return
     */
    public MobShareToDifferentPlatformHelper shareToQQ() {
        if (null == mobShareParm) {
            ToastUtils.showShort("分享内容错误...");
            return this;
        }
        if (null == shareParams) {
            shareParams = new Platform.ShareParams();
        }
        platform = ShareSDK.getPlatform(QQ.NAME);

        setShareImage();
        setShareTitle();
        if (!TextUtils.isEmpty(mobShareParm.getMusicUrl())) {
            shareParams.setMusicUrl(mobShareParm.getMusicUrl());
        }
        /**
         * （3.1.0版本之后，包含3.1.0）QQ绕过审核形式支持分享多图（BypassApproval=”true”），
         *  ImageArray String类型图片数组，可传手机本地图片路径和图片链接
         */
        setShareMoreImage();
        //调用分享
        showShare();
        return this;
    }

    /**
     * 分享到QQ空间
     *
     * @return
     */
    public MobShareToDifferentPlatformHelper shareToQZone() {
        if (null == mobShareParm) {
            ToastUtils.showShort("分享内容错误...");
        }
        if (shareParams == null) {
            shareParams = new Platform.ShareParams();
        }

        platform = ShareSDK.getPlatform(QZone.NAME);
        //设置分享类型
        if (mobShareParm.getShareType() != null && Platform.SHARE_VIDEO == mobShareParm.getShareType()) {
            shareParams.setShareType(mobShareParm.getShareType());
        }

        //分享视频
        if (!TextUtils.isEmpty(mobShareParm.getFilePath())) {
            shareParams.setFilePath(mobShareParm.getFilePath());
        }

        setShareTitle();
        setSiteInfo();
        setShareImage();
        setShareMoreImage();
        //调用分享
        showShare();
        return this;
    }

    /**
     * 分享到微信好友,提示：必须需要客户端才可以分享
     *
     * @return
     */
    public MobShareToDifferentPlatformHelper shareToWechat() {
        if (null == mobShareParm) {
            ToastUtils.showShort("分享内容错误...");
        }
        if (null == shareParams) {
            shareParams = new Platform.ShareParams();
        }
        platform = ShareSDK.getPlatform(Wechat.NAME);

        //处理微信分享类型
        handleWeChatType(mobShareParm.getShareType());
        //调用分享
        showShare();
        return this;
    }

    /**
     * 分享到微信朋友圈
     *
     * @return
     */
    public MobShareToDifferentPlatformHelper shareToWechatMoments() {
        if (null == mobShareParm) {
            ToastUtils.showShort("分享内容错误...");
        }
        if (null == shareParams) {
            shareParams = new Platform.ShareParams();
        }
        platform = ShareSDK.getPlatform(WechatMoments.NAME);
        //处理微信分享类型
        handleWeChatType(mobShareParm.getShareType());
        //调用分享
        showShare();
        return this;
    }

    /**
     * 分享到微信收藏
     *
     * @return
     */
    public MobShareToDifferentPlatformHelper shareToWechatFavorite() {
        if (null == mobShareParm) {
            ToastUtils.showShort("分享内容错误...");
        }
        if (null == shareParams) {
            shareParams = new Platform.ShareParams();
        }
        platform = ShareSDK.getPlatform(WechatFavorite.NAME);
        //处理微信分享类型
        handleWeChatType(mobShareParm.getShareType());
        //调用分享
        showShare();
        return this;
    }

    /**
     * 分享到新浪微博
     *
     * @return
     */
    public MobShareToDifferentPlatformHelper shareToSinaWeibo() {
        if (null == mobShareParm) {
            ToastUtils.showShort("分享内容错误...");
            return this;
        }
        if (null == shareParams) {
            shareParams = new Platform.ShareParams();
        }
        platform = ShareSDK.getPlatform(SinaWeibo.NAME);

        setShareTitle();
        setShareImage();
        //注意：以下2种分享类型必须有新浪微博客户端才可以分享；
        if (!TextUtils.isEmpty(mobShareParm.getFilePath())) {
            shareParams.setFilePath(mobShareParm.getFilePath());
        }
        setShareMoreImage();
        showShare();
        return this;
    }

    /**
     * 分享到腾讯微博
     *
     * @return
     */
    public MobShareToDifferentPlatformHelper shareToTencentWeibo() {
        if (null == mobShareParm) {
            ToastUtils.showShort("分享内容错误...");
            return this;
        }
        if (null == shareParams) {
            shareParams = new Platform.ShareParams();
        }
        platform = ShareSDK.getPlatform(TencentWeibo.NAME);


        setShareTitle();
        setShareLocation();
        setShareImage();
        setShareMoreImage();
        showShare();
        return this;
    }

    /**
     * 分享到豆瓣
     * @return
     */
    public MobShareToDifferentPlatformHelper shareToDouBan() {
        if (null == mobShareParm) {
            ToastUtils.showShort("分享内容错误...");
            return this;
        }
        if (null == shareParams) {
            shareParams = new Platform.ShareParams();
        }
        platform = ShareSDK.getPlatform(Douban.NAME);

        setShareTitle();
        setShareImage();
        //调用分享
        showShare();
        return this;
    }

    /**
     * 分享到有道云笔记
     *
     * @return
     */
    public MobShareToDifferentPlatformHelper shareToYouDao() {
        if (null == mobShareParm) {
            ToastUtils.showShort("分享内容错误...");
            return this;
        }
        if (null == shareParams) {
            shareParams = new Platform.ShareParams();
        }
        platform = ShareSDK.getPlatform(YouDao.NAME);

        setShareTitle();
        //此参数可选
        if (!TextUtils.isEmpty(mobShareParm.getNotebook())) {
            shareParams.setNotebook(mobShareParm.getNotebook());
        }
        //此参数可选
        if (!TextUtils.isEmpty(mobShareParm.getAddress())) {
            shareParams.setAddress(mobShareParm.getAddress());
        }
        //此参数可选
        if (!TextUtils.isEmpty(mobShareParm.getUrl())) {
            shareParams.setUrl(mobShareParm.getUrl());
        }
        setShareImage();
        //调用分享
        showShare();
        return this;
    }

    /**
     * 分享到支付宝
     * @return
     */
    public MobShareToDifferentPlatformHelper shareToAlipay() {
        if (null == mobShareParm) {
            ToastUtils.showShort("分享内容错误...");
            return this;
        }
        if (null == shareParams) {
            shareParams = new Platform.ShareParams();
        }
        platform = ShareSDK.getPlatform(Alipay.NAME);

        //设置分享类型
        if (null != mobShareParm.getShareType()) {
            shareParams.setShareType(mobShareParm.getShareType());
        }else {
            //默认分享图文网页
            shareParams.setShareType(Platform.SHARE_WEBPAGE);
        }
        setShareTitle();

        //url，消息点击后打开的页面
        if (!TextUtils.isEmpty(mobShareParm.getUrl())) {
            shareParams.setUrl(mobShareParm.getUrl());
        }
        setShareImage();
        //调用分享
        showShare();
        return this;
    }

    /**
     * 分享到支付宝朋友圈，提示：生活圈只支持分享网页、必须需要客户端才可以分享
     * @return
     */
    public MobShareToDifferentPlatformHelper shareToAlipayMoments() {
        if (null == mobShareParm) {
            ToastUtils.showShort("分享内容错误...");
            return this;
        }
        if (null == shareParams) {
            shareParams = new Platform.ShareParams();
        }
        platform = ShareSDK.getPlatform(AlipayMoments.NAME);

        //设置分享类型，生活圈只支持分享网页
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        setShareTitle();

        //url，消息点击后打开的页面
        if (!TextUtils.isEmpty(mobShareParm.getUrl())) {
            shareParams.setUrl(mobShareParm.getUrl());
        }
        setShareImage();
        //调用分享
        showShare();
        return this;
    }

    /**
     * 分享到钉钉
     * @return
     */
    public MobShareToDifferentPlatformHelper shareToDingDing() {
        if (null == mobShareParm) {
            ToastUtils.showShort("分享内容错误...");
            return this;
        }
        if (null == shareParams) {
            shareParams = new Platform.ShareParams();
        }
        platform = ShareSDK.getPlatform(Dingding.NAME);

        //设置分享类型
        if (null != mobShareParm.getShareType()) {
            shareParams.setShareType(mobShareParm.getShareType());
        }else {
            //默认分享图文网页
            shareParams.setShareType(Platform.SHARE_WEBPAGE);
        }
        setShareTitle();

        //url，消息点击后打开的页面
        if (!TextUtils.isEmpty(mobShareParm.getUrl())) {
            shareParams.setUrl(mobShareParm.getUrl());
        }
        setShareImage();
        //调用分享
        showShare();
        return this;
    }

    /**
     * 分享到抖音，提示：只能用客户端分享，调用分享默认提示成功
     * @return
     */
    public MobShareToDifferentPlatformHelper shareToDouYin() {
        if (null == mobShareParm) {
            ToastUtils.showShort("分享内容错误...");
            return this;
        }
        if (null == shareParams) {
            shareParams = new Platform.ShareParams();
        }
        platform = ShareSDK.getPlatform(Douyin.NAME);

        //设置分享类型
        if (null != mobShareParm.getShareType()) {
            shareParams.setShareType(mobShareParm.getShareType());
        }else {
            //只支持分享视频
            shareParams.setShareType(Platform.SHARE_VIDEO);
        }
        //分享短视频
        if (!TextUtils.isEmpty(mobShareParm.getFilePath())) {
            shareParams.setFilePath(mobShareParm.getFilePath());
        }
        //调用分享
        showShare();
        return this;
    }

    /**
     * 分享邮件，邮件分享调用手机上的邮件客户端，如果没有客户端，将不能分享邮件
     * @return
     */
    public MobShareToDifferentPlatformHelper shareToEmail() {
        if (null == mobShareParm) {
            ToastUtils.showShort("分享内容错误...");
        }
        if (null == shareParams) {
            shareParams = new Platform.ShareParams();
        }
        platform = ShareSDK.getPlatform(Email.NAME);

        //设置分享类型
        if (null != mobShareParm.getShareType()) {
            shareParams.setShareType(mobShareParm.getShareType());
        }
        setShareTitle();
        setShareImage();
        if (!TextUtils.isEmpty(mobShareParm.getAddress())) {
            shareParams.setAddress(mobShareParm.getAddress());
        }
        if (!TextUtils.isEmpty(mobShareParm.getFilePath())) {
            shareParams.setFilePath(mobShareParm.getFilePath());
        }
        //调用
        showShare();
        return this;
    }

    /**
     * 分享到信息，信息分短信和彩信，如果设置了标题或者图片，会直接当作彩信发送。发送信息的时候使用手机的信息软件
     *
     * @return
     */
    public MobShareToDifferentPlatformHelper shareToShortMessage() {
        if (null == mobShareParm) {
            ToastUtils.showShort("分享内容错误...");
        }
        if (shareParams == null) {
            shareParams = new Platform.ShareParams();
        }
        platform = ShareSDK.getPlatform(ShortMessage.NAME);
        //设置分享类型
        if (null != mobShareParm.getShareType()) {
            shareParams.setShareType(mobShareParm.getShareType());
        }

        setShareTitle();
        if (!TextUtils.isEmpty(mobShareParm.getAddress())) {
            shareParams.setAddress(mobShareParm.getAddress());
        }
        if (!TextUtils.isEmpty(mobShareParm.getFilePath())) {
            shareParams.setFilePath(mobShareParm.getFilePath());
        }
        setShareImage();
        //调用分享
        showShare();
        return this;
    }

    /**
     * 展示分享内容
     */
    private void showShare() {
        if (null == platform) {
            ToastUtils.showShort("分享平台不存在...");
        }
        // 设置分享事件回调（注：回调放在不能保证在主线程调用，不可以在里面直接处理UI操作）
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
                ToastUtils.showShort("分享失败");
            }

            @Override
            public void onComplete(Platform plat, int arg1, HashMap arg2) {
                //此处处理微信，因为只要走分享，都会走这个方法，其他两个方法不走
                if (!plat.getName().equals(Wechat.NAME) && !plat.getName().equals(WechatMoments.NAME) && !plat.getName().equals(WechatFavorite.NAME)) {
                    //分享成功的回调
                    ToastUtils.showShort("分享成功");
                }else {
                }
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
                //取消分享的回调
                ToastUtils.showShort("分享取消");
            }
        });
        // 执行图文分享
        platform.share(shareParams);
    }


    /*****************************************************  以下是私有方法  ***************************************************/


    /**
     * 设置分享图片
     */
    private void setShareImage() {
        if (null == shareParams) {
            shareParams = new Platform.ShareParams();
        }
        if (!TextUtils.isEmpty(mobShareParm.getImageUrl())) {
            shareParams.setImageUrl(mobShareParm.getImageUrl());
        } else {
            if (!TextUtils.isEmpty(mobShareParm.getImagePath())) {
                shareParams.setImagePath(mobShareParm.getImagePath());
            }
        }
    }

    /**
     * 设置分享文本、链接
     */
    private void setShareTitle() {
        if (null == shareParams) {
            shareParams = new Platform.ShareParams();
        }
        if (!TextUtils.isEmpty(mobShareParm.getTitle())) {
            shareParams.setTitle(mobShareParm.getTitle());
        }
        if (!TextUtils.isEmpty(mobShareParm.getTitleUrl())) {
            shareParams.setTitleUrl(mobShareParm.getTitleUrl());
        }
        if (!TextUtils.isEmpty(mobShareParm.getText())) {
            shareParams.setText(mobShareParm.getText());
        }
    }

    /**
     * 设置分享网站标题和网站链接
     */
    private void setSiteInfo() {
        if (null == shareParams) {
            shareParams = new Platform.ShareParams();
        }
        if (!TextUtils.isEmpty(mobShareParm.getSite())) {
            shareParams.setSite(mobShareParm.getSite());
        }
        if (!TextUtils.isEmpty(mobShareParm.getSiteUrl())) {
            shareParams.setSiteUrl(mobShareParm.getSiteUrl());
        }
    }

    /**
     * 设置分享图片
     */
    private void setShareMoreImage() {
        if (null == shareParams) {
            shareParams = new Platform.ShareParams();
        }
        if (!TextUtils.isEmpty(mobShareParm.getText())) {
            //text可不传
            shareParams.setText(mobShareParm.getText());
        }
        if (null != mobShareParm.getImageArray() && mobShareParm.getImageArray().length > 0) {
            shareParams.setImageArray(mobShareParm.getImageArray());
        }
    }

    /**
     * 设置分享经纬度，仅在腾讯微博使用，且不是必要字段
     */
    private void setShareLocation() {
        if (null == shareParams) {
            shareParams = new Platform.ShareParams();
        }
        if (mobShareParm.getLatitude() != null) {
            shareParams.setLatitude(mobShareParm.getLatitude());
        }
        if (mobShareParm.getLongitude() != null) {
            shareParams.setLongitude(mobShareParm.getLongitude());
        }
    }

    /**
     * 设置分享应用（微信）
     */
    private void setShareWxApps() {
        if (!TextUtils.isEmpty(mobShareParm.getFilePath())) {
            shareParams.setFilePath(mobShareParm.getFilePath());
        }
        if (!TextUtils.isEmpty(mobShareParm.getExtInfo())) {
            shareParams.setExtInfo(mobShareParm.getExtInfo());
        }
        if (!TextUtils.isEmpty(mobShareParm.getWxPath())) {
            shareParams.setWxPath(mobShareParm.getWxPath());
        }
        if (!TextUtils.isEmpty(mobShareParm.getWxUserName())) {
            shareParams.setWxUserName(mobShareParm.getWxUserName());
        }
    }


    /**
     * 处理微信分享类型
     * 朋友圈不能分享表情、文件和应用，收藏不能分享表情和应用
     *
     * @param shareType
     */
    private void handleWeChatType(Integer shareType) {
        if (null != shareType) {
            shareParams.setShareType(shareType);
        }else {
            //默认分享图文
            shareParams.setShareType(Platform.SHARE_IMAGE);
        }

        setShareTitle();
        setShareImage();
        if (!TextUtils.isEmpty(mobShareParm.getMusicUrl())) {
            shareParams.setMusicUrl(mobShareParm.getMusicUrl());
        }
        //url（消息点击后打开的页面）
        if (!TextUtils.isEmpty(mobShareParm.getUrl())) {
            shareParams.setUrl(mobShareParm.getUrl());
        }

        //分享应用、文件、小程序
        setShareWxApps();
    }
}
