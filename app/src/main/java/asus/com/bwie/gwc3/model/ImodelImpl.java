package asus.com.bwie.gwc3.model;

import java.util.Map;

import asus.com.bwie.gwc3.callback.MyCallBack;
import asus.com.bwie.gwc3.okhttp.OkHttpUtils;

public class ImodelImpl implements Imodel {

    @Override
    public void startRequestData(String urlData, Map<String, String> map, Class clazz, final MyCallBack myCallBack) {
        OkHttpUtils.getOkHttpUtils().getEneuque(urlData, map, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                myCallBack.onSuccess(data);
            }

            @Override
            public void onFail(Exception e) {
                myCallBack.onFail(e);
            }
        });
    }
}
