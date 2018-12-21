package asus.com.bwie.gwc3.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

import asus.com.bwie.gwc3.Apis;
import asus.com.bwie.gwc3.R;
import asus.com.bwie.gwc3.adapter.ShopListAdapter;
import asus.com.bwie.gwc3.bean.AddShopCarBean;
import asus.com.bwie.gwc3.bean.ShopList;
import asus.com.bwie.gwc3.presenter.IPresenterImpl;
import asus.com.bwie.gwc3.view.IView;

public class RecycleActivity extends AppCompatActivity implements IView {
    private int mPage;
    private XRecyclerView xRecyclerView;
    private IPresenterImpl iPresenter;
    private Map<String, String> map;
    private LinearLayoutManager linearLayoutManager;
    private ShopListAdapter shopListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        map = new HashMap<>();
        mPage=0;
        xRecyclerView = findViewById(R.id.xrecycleView);
        iPresenter = new IPresenterImpl(this);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage=1;
                requestData();

            }

            @Override
            public void onLoadMore() {
                requestData();
            }
        });
        linearLayoutManager = new LinearLayoutManager(this);
        xRecyclerView.setLayoutManager(linearLayoutManager);
        shopListAdapter = new ShopListAdapter(this);
        xRecyclerView.setAdapter(shopListAdapter);
        mPage=1;
        requestData();
        shopListAdapter.setGetPidClick(new ShopListAdapter.PidClickListener() {
            @Override
            public void onClick(int position) {
                Map<String,String> map1=new HashMap<>();
                map1.put("uid","71");
                map1.put("pid",position+"");
                iPresenter.startRequest(Apis.AddShopCarPath,map1,AddShopCarBean.class);
            }
        });
        shopListAdapter.setItemClickListener(new ShopListAdapter.onItemLongClick() {
            @Override
            public void onLongoingClick() {
                Intent intent=new Intent(RecycleActivity.this,ShopCarActivity.class);
                startActivity(intent);
            }
        });




    }

    private void requestData() {
        map.clear();
        map.put("keywords","手机");
        map.put("page",mPage+"");
        iPresenter.startRequest(Apis.ListPath,map,ShopList.class);
    }

    @Override
    public void onSuccessData(Object data) {
        if (data instanceof ShopList){
            ShopList shopList= (ShopList) data;
            if (mPage==1){
                shopListAdapter.setmList(shopList.getData());
            }else {
                shopListAdapter.addmList(shopList.getData());
            }
            mPage++;
            xRecyclerView.loadMoreComplete();
            xRecyclerView.refreshComplete();

        }
        if (data instanceof AddShopCarBean){
            AddShopCarBean addShopCarBean= (AddShopCarBean) data;
            Toast.makeText(this,addShopCarBean.getMsg(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailData(Exception e) {

    }
}
