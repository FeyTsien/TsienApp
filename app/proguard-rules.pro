 # Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


-dontwarn com.bumptech.glide.**
##XBanner 图片轮播混淆配置
-keep class com.stx.xhb.xbanner.**{*;}

#图片裁剪框架混淆配置（Yalantis/uCrop）
-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }

#百度OCR识别混淆配置
-keep class com.baidu.ocr.sdk.**{*;}
-dontwarn com.baidu.ocr.**

#MOB分享SDK混淆处理（MobSDK已经混淆处理，绕过此混淆，添加一些代码）
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep class com.mob.**{*;}
-keep class m.framework.**{*;}
-dontwarn cn.sharesdk.**
-dontwarn com.sina.**
-dontwarn com.mob.**
-dontwarn **.R$*

#==================混淆配置=====================
-keep class com.hdnz.inanming.** {*;} #//取消com.hdnz.inanming这个包名路径下所有类和子包的混淆
#解决Please correct the above warnings first.问题 app:transformClassesAndResourcesWithProguardFor
-ignorewarnings # 抑制警告

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}
-keep public class * extends android.os.IInterface


-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

-keepnames class * implements java.io.Serializable

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepattributes *Annotation*
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes SourceFile,LineNumberTable

-keep class **.R$* { *; }

-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment

-dontwarn com.android.support.**
-keep class com.android.support.** { *; }

-dontwarn de.greenrobot.event.**
-keep class de.greenrobot.event.** { *; }
-keepclassmembers class ** {
    public void onEvent*(**);
    void onEvent*(**);
}

-dontwarn org.greenrobot.**
-keep class org.greenrobot.**{*;}

-dontwarn com.google.**
-keep class com.google.**{*;}

-dontwarn com.jakewharton.**
-keep class com.jakewharton.**{*;}

-dontwarn com.squareup.**
-keep class com.squareup.**{*;}

-dontwarn io.reactivex.**
-keep class io.reactivex.**{*;}

-dontwarn com.bigkoo.**
-keep class com.bigkoo.**{*;}

#LoadSir
-dontwarn com.kingja.loadsir.**
-keep class com.kingja.loadsir.** {*;}
