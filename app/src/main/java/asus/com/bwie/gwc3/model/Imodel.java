package asus.com.bwie.gwc3.model;

import java.util.Map;

import asus.com.bwie.gwc3.callback.MyCallBack;

public interface Imodel {

    void startRequestData(String urlData, Map<String,String> map, Class clazz, MyCallBack myCallBack);

}
