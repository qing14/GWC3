package asus.com.bwie.gwc3.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import asus.com.bwie.gwc3.R;
import asus.com.bwie.gwc3.bean.ShopList;

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ViewHolder> {
        private List<ShopList.DataBean> mList;
        private Context mContext;

    public ShopListAdapter(Context mContext) {
        this.mList =new ArrayList<>();
        this.mContext = mContext;
    }
    public int getPid (int position){
        int numid = mList.get(position).getPid();
        return numid;
    }

    public void setmList(List<ShopList.DataBean> mLists) {
        this.mList = mLists;
        notifyDataSetChanged();
    }
    public void addmList(List<ShopList.DataBean> mLists) {
        mList.addAll(mLists);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ShopListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=View.inflate(mContext, R.layout.recycle_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopListAdapter.ViewHolder holder, final int position) {
        final ShopList.DataBean bean = mList.get(position);
        String[] split = bean.getImages().split("\\|");
        Glide.with(mContext).load(split[0]).into(holder.shop_img);
        holder.shop_title.setText(bean.getTitle());
        holder.shop_price.setText("￥"+bean.getPrice());
        holder.shop_salenum.setText("已售"+bean.getSalenum()+"件");
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener!=null){
                    itemClickListener.onLongoingClick();
                }
            }
        });
        holder.addShopCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    int pid = bean.getPid();
                    listener.onClick(pid);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView shop_img;
        private final TextView shop_title;
        private final TextView shop_price;
        private final TextView shop_salenum;
        private final LinearLayout linearLayout;
        private final Button addShopCar;

        public ViewHolder(View itemView) {
            super(itemView);
            shop_img = itemView.findViewById(R.id.shop_img);
            shop_title = itemView.findViewById(R.id.shop_title);
            shop_price = itemView.findViewById(R.id.shop_price);
            shop_salenum = itemView.findViewById(R.id.shop_salenum);
            linearLayout = itemView.findViewById(R.id.linear);
            addShopCar = itemView.findViewById(R.id.addShopCar);

        }
    }
    public PidClickListener listener;

    public void setGetPidClick(PidClickListener clickListener) {
        this.listener = clickListener;
    }

    public interface PidClickListener{
        void onClick(int pid);
    }

    public onItemLongClick itemClickListener;

    public void setItemClickListener(onItemLongClick itemClicklistener){
        itemClickListener=itemClicklistener;
    }


    public interface onItemLongClick{
        void onLongoingClick();
    }

}
