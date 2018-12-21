package asus.com.bwie.gwc3.callback;

public interface MyCallBack<T>{
    void onSuccess(T data);
    void onFail(Exception e);
}
