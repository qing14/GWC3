package asus.com.bwie.gwc3.view;

public interface IView<T> {
    void onSuccessData(T data);
    void onFailData(Exception e);
}
