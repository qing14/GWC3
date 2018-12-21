package asus.com.bwie.gwc3.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asus.com.bwie.gwc3.Apis;
import asus.com.bwie.gwc3.R;
import asus.com.bwie.gwc3.adapter.ShangJiaAdapter;
import asus.com.bwie.gwc3.bean.ShopBean;
import asus.com.bwie.gwc3.presenter.IPresenterImpl;
import asus.com.bwie.gwc3.view.IView;

public class ShopCarActivity extends AppCompatActivity implements IView {

    private IPresenterImpl iPresenter;
    private ShangJiaAdapter shangJiaAdapter;
    private List<ShopBean.DataBean> mshopList=new ArrayList<>();
    private TextView allPrice;
    private TextView allCount;
    private CheckBox selAll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        iPresenter = new IPresenterImpl(this);
        initView();
        getData();
        selAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSeller(selAll.isChecked());
                shangJiaAdapter.notifyDataSetChanged();
            }
        });
    }



    private void initView() {
        selAll = findViewById(R.id.selAll);
        allPrice = findViewById(R.id.allPrice);
        allCount = findViewById(R.id.allCount);
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        shangJiaAdapter = new ShangJiaAdapter(this);
        recyclerView.setAdapter(shangJiaAdapter);
        shangJiaAdapter.setSelOrDelSPListener(new ShangJiaAdapter.SelOrDelSPListener() {
            @Override
            public void selordelsp(List<ShopBean.DataBean> list) {
                double totalPrice = 0;
                int num = 0;
                int totalNum = 0;
                for (int a = 0; a < list.size(); a++) {
                    List<ShopBean.DataBean.ListBean> listAll = list.get(a).getList();
                    for (int i = 0; i < listAll.size(); i++) {
                        totalNum = totalNum + listAll.get(i).getNum();
                        if (listAll.get(i).isCheck()) {
                            totalPrice = totalPrice + (listAll.get(i).getPrice() * listAll.get(i).getNum());
                            num = num + listAll.get(i).getNum();
                        }
                    }
                }
                if (num < totalNum) {
                    selAll.setChecked(false);
                } else {
                    selAll.setChecked(true);
                }
                allPrice.setText("合计：" + totalPrice);
                allCount.setText("去结算（" + num + ")");
            }
        });
    }

    private void getData() {
        Map<String,String> map=new HashMap<>();
        map.put(Apis.Uid,"71");
        iPresenter.startRequest(Apis.SelShopCarPath,map,ShopBean.class);
    }

    @Override
    public void onSuccessData(Object data) {
        if (data instanceof ShopBean){
            ShopBean shopBean= (ShopBean) data;
            mshopList = shopBean.getData();
            if (mshopList!=null){
                mshopList.remove(0);
                shangJiaAdapter.setMlist(mshopList);
            }


        }
    }

    @Override
    public void onFailData(Exception e) {

    }
    private void checkSeller(boolean bool){
        double totalPrice=0;
        int num=0;
        for (int a=0;a<mshopList.size();a++){
            ShopBean.DataBean dataBean=mshopList.get(a);
            dataBean.setCheck(bool);
            List<ShopBean.DataBean.ListBean> listAll=mshopList.get(a).getList();

            for (int i=0;i<listAll.size();i++){
                listAll.get(i).setCheck(bool);
                totalPrice=totalPrice+(listAll.get(i).getPrice()*listAll.get(i).getNum());
                num=num+listAll.get(i).getNum();
            }
        }
        if (bool){
            allPrice.setText("合计："+totalPrice);
            allCount.setText("去结算（"+num+")");

        }else {
            allPrice.setText("合计：0.00");
            allCount.setText("去结算（0)");
        }



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.detach();
    }
}
