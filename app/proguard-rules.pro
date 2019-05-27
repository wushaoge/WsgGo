# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html

# Optimizations: If you don't want to optimize, use the
# proguard-android.txt configuration file instead of this one, which
# turns off the optimization flags.  Adding optimization introduces
# certain risks, since for example not all optimizations performed by
# ProGuard works on all versions of Dalvik.  The following flags turn
# off various optimizations known to have issues, but the list may not
# be complete or up to date. (The "arithmetic" optimization can be
# used if you are only targeting Android 2.0 or later.)  Make sure you
# test thoroughly if you go this route.

# 混淆时采用的算法(google推荐，一般不改变)
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
# 指定压缩级别
-optimizationpasses 5
# 优化时允许访问并修改有修饰符的类和类的成员
-allowaccessmodification
# 不做预检验,preverify是proguard的四个步骤之一,Android不需要preverify,去掉这一步可以加快混淆速度
-dontpreverify
# 忽略所有警告
-ignorewarnings

# The remainder of this file is identical to the non-optimized version
# of the Proguard configuration file (except that the other file has
# flags to turn off optimization).

# 混淆时不使用大小写混合类名
-dontusemixedcaseclassnames
# 不跳过library中的非public的类
-dontskipnonpubliclibraryclasses
# 打印混淆的详细信息
-verbose
# 保留行号
-keepattributes SourceFile,LineNumberTable
# 注解
-keepattributes *Annotation*
# 内部类
-keepattributes InnerClasses
# 异常
-keepattributes Exceptions
# 泛型
-keepattributes Signature
# 反射
-keepattributes EnclosingMethod

#接入Google原生的一些服务时使用,一般用不到
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}

# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
   public <init>(android.content.Context);
   public <init>(android.content.Context, android.util.AttributeSet);
   public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#-keepclassmembers class * implements android.os.Parcelable {
#  public static final android.os.Parcelable$Creator CREATOR;
#}

#-keepclassmembers class * implements java.io.Serializable {
#    static final long serialVersionUID;
#    private static final java.io.ObjectStreamField[] serialPersistentFields;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#}

-keepclassmembers class **.R$* {
    public static <fields>;
}

# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
-dontwarn android.support.**

# Understand the @Keep support annotation.
-keep class android.support.annotation.Keep

-keep @android.support.annotation.Keep class * {*;}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}

#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}

#不混淆实体类
-keep class com.banksteel.jiyun.entity.** { *; }

# Butterknife
-keepnames class * { @butterknife.* *; }
-keep class butterknife.*
-dontwarn butterknife.internal.**
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }

# glide
-dontwarn com.bumptech.glide.**
-keep class com.bumptech.glide.** {*;}

# 各种加载框架
-dontwarn com.wang.avi.**
-keep class com.wang.avi.** {*;}

# photoview
-dontwarn uk.co.senab.photoview.**
-keep class uk.co.senab.photoview.** {*;}

# flexbox
-dontwarn com.google.android.flexbox.**
-keep class com.google.android.flexbox.** {*;}

# 友盟
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

# okgo
-dontwarn com.lzy.okgo.**
-keep class com.lzy.okgo.** {*;}
-dontwarn com.lzy.okserver.**
-keep class com.lzy.okserver.** {*;}

# okhttp
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# eventBus
-keep class org.simple.** { *; }
-keep interface org.simple.** { *; }
-keepclassmembers class * {
    @org.simple.eventbus.Subscriber <methods>;
}

# 崩溃日志
-dontwarn com.sun.activation.registries.**
-keep class com.sun.activation.registries.** {*;}
-dontwarn javax.activation.**
-keep class javax.activation.** {*;}
-dontwarn myjava.awt.datatransfer.**
-keep class myjava.awt.datatransfer.** {*;}
-dontwarn org.apache.harmony.**
-keep class org.apache.harmony.** {*;}
-dontwarn com.wenming.library.**
-keep class com.wenming.library.** {*;}
-dontwarn com.sun.mail.**
-keep class com.sun.mail.** {*;}
-dontwarn javax.mail.**
-keep class javax.mail.** {*;}
-dontwarn net.lingala.zip4j.**
-keep class net.lingala.zip4j.** {*;}

# 滑动关闭activity
-dontwarn me.imid.swipebacklayout.lib.**
-keep class me.imid.swipebacklayout.lib.** {*;}

# wheel选择控件
-dontwarn com.aigestudio.wheelpicker.**
-keep class com.aigestudio.wheelpicker.** {*;}

# 沉浸式状态栏
-dontwarn com.readystatesoftware.systembartint.**
-keep class com.readystatesoftware.systembartint.** {*;}

# gson
-dontwarn com.google.**
-keep class com.google.gson.** {*;}

# nineoldandroids
-dontwarn com.nineoldandroids.**
-keep class com.nineoldandroids.** {*;}

# Banner
-dontwarn cn.bingoogolapple.bgabanner.**
-keep class cn.bingoogolapple.bgabanner.** {*;}

## kotlin
#-dontwarn kotlin.**
## 运行时不进行空检查
#-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
#    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
#}

# smartrefresh
-dontwarn com.scwang.smartrefresh.layout.**
-keep class com.scwang.smartrefresh.layout.** {*;}

#takephoto
-keep class com.jph.takephoto.** { *; }
-dontwarn com.jph.takephoto.**

-keep class com.darsh.multipleimageselect.** { *; }
-dontwarn com.darsh.multipleimageselect.**

-keep class com.soundcloud.android.crop.** { *; }
-dontwarn com.soundcloud.android.crop.**

# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
-dontnote rx.internal.util.PlatformDependent

#BaseRecyclerViewAdapterHelper
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}

-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
    public static void dropTable(org.greenrobot.greendao.database.Database, boolean);
    public static void createTable(org.greenrobot.greendao.database.Database, boolean);
}