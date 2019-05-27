package com.wsg.go.netbuild;


import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.GetRequest;
import com.wsg.go.view.activity.base.BaseMVCActivity;
import com.wsg.go.view.fragment.base.BaseFragment;


/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2018-12-26 10:14
 * 描    述：封装网络请求
 * 修订历史：
 * ================================================
 */
public class NetUtils {


    /**
     * 请求主协议
     * @param mThis BaseMVCActivity or BaseFragment
     * @param clazz 实体类
     * @param url 请求地址
     * @param cmd cmd返回标识
     * @param <T>
     */
    public static <T> void getMain(Object mThis, Class<T> clazz, String url, String cmd){
        GetRequest<T> request = OkGo.<T>get(url).tag(mThis);

        if(mThis instanceof BaseMVCActivity){
            request.execute(((BaseMVCActivity)mThis).getCallbackCustomDataMainInterface(clazz, cmd));
        }else if(mThis instanceof BaseFragment){
            request.execute(((BaseFragment)mThis).getCallbackCustomDataMainInterface(clazz, cmd));
        }
    }

    /**
     *
     * @param mThis BaseMVCActivity or BaseFragment
     * @param clazz 实体类
     * @param url 请求地址
     * @param cmd cmd返回标识
     * @param <T>
     */
    public static <T> void get(Object mThis, Class<T> clazz, String url, String cmd, boolean isShowDialog){
        GetRequest<T> request = OkGo.<T>get(url).tag(mThis);

        if(mThis instanceof BaseMVCActivity){
            if(isShowDialog){
                request.execute(((BaseMVCActivity)mThis).getCallbackCustomData(clazz, cmd));
            }else{
                request.execute(((BaseMVCActivity)mThis).getCallbackCustomDataNoDialog(clazz, cmd));
            }
        }else if(mThis instanceof BaseFragment){
            if(isShowDialog){
                request.execute(((BaseFragment)mThis).getCallbackCustomData(clazz, cmd));
            }else{
                request.execute(((BaseFragment)mThis).getCallbackCustomDataNoDialog(clazz, cmd));
            }
        }
    }


    /**
     * 请求主协议
     * @param mThis BaseMVCActivity or BaseFragment
     * @param clazz 实体类
     * @param url 请求地址
     * @param cmd cmd返回标识
     * @param <T>
     */
    public static <T> void getMainNew(Object mThis, Class<T> clazz, String url, String cmd){
        GetRequest<T> request = OkGo.<T>get(url).tag(mThis);

        if(mThis instanceof BaseMVCActivity){
            request.execute(((BaseMVCActivity)mThis).getCallbackCustomDataMainInterface(clazz, cmd));
        }else if(mThis instanceof BaseFragment){
            request.execute(((BaseFragment)mThis).getCallbackCustomDataMainInterface(clazz, cmd));
        }
    }

    /**
     *
     * @param mThis BaseMVCActivity or BaseFragment
     * @param clazz 实体类
     * @param url 请求地址
     * @param cmd cmd返回标识
     * @param <T>
     */
    public static <T> void getNew(Object mThis, Class<T> clazz, String url, String cmd, boolean isShowDialog){
        GetRequest<T> request = OkGo.<T>get(url).tag(mThis);

        if(mThis instanceof BaseMVCActivity){
            if(isShowDialog){
                request.execute(((BaseMVCActivity)mThis).getCallbackCustomData(clazz, cmd));
            }else{
                request.execute(((BaseMVCActivity)mThis).getCallbackCustomDataNoDialog(clazz, cmd));
            }
        }else if(mThis instanceof BaseFragment){
            if(isShowDialog){
                request.execute(((BaseFragment)mThis).getCallbackCustomData(clazz, cmd));
            }else{
                request.execute(((BaseFragment)mThis).getCallbackCustomDataNoDialog(clazz, cmd));
            }
        }
    }

}
