package asus.com.bwie.gwc3.presenter;

import java.util.Map;

import asus.com.bwie.gwc3.activity.RecycleActivity;
import asus.com.bwie.gwc3.callback.MyCallBack;
import asus.com.bwie.gwc3.model.Imodel;
import asus.com.bwie.gwc3.model.ImodelImpl;
import asus.com.bwie.gwc3.okhttp.ICallBack;
import asus.com.bwie.gwc3.view.IView;

public class IPresenterImpl implements IPresenter {

    private ImodelImpl imodel;
    private IView iView;

    public IPresenterImpl(IView iView) {
        imodel=new ImodelImpl();
        this.iView = iView;
    }

    @Override
    public void startRequest(String urlData, Map<String, String> map, Class clazz) {
        imodel.startRequestData(urlData, map, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iView.onSuccessData(data);
            }

            @Override
            public void onFail(Exception e) {
                iView.onFailData(e);
            }
        });
    }
    public void detach(){
        imodel=null;
        iView=null;
    }
}
