package asus.com.bwie.gwc3.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import asus.com.bwie.gwc3.R;
import asus.com.bwie.gwc3.bean.ShopBean;
import asus.com.bwie.gwc3.view.JiaJianView;

public class ShangPinAdapter extends RecyclerView.Adapter<ShangPinAdapter.ViewHolder> {

    private Context context;
    private List<ShopBean.DataBean.ListBean> list=new ArrayList<>();

    public ShangPinAdapter(Context context, List<ShopBean.DataBean.ListBean> listBeans) {
        this.context = context;
        this.list = listBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=View.inflate(context, R.layout.sp_view,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String splitreplace = list.get(position).getImages().split("\\|")[0].replace("https", "http");
        Glide.with(context).load(splitreplace).into(holder.sp_img);
        holder.sp_name.setText(list.get(position).getTitle());
        holder.sp_price.setText(list.get(position).getPrice()+"");
        holder.sp_check.setChecked(list.get(position).isCheck());
        holder.sp_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list.get(position).setCheck(isChecked);
                if (mshopCallBackListener !=null){
                    mshopCallBackListener.callBack();
                }
            }
        });
        holder.jiaJianView.setData(this,list,position);
        holder.jiaJianView.setOnJiaJian(new JiaJianView.JiajianListener() {
            @Override
            public void jiajian() {
                if (mshopCallBackListener!=null){
                    mshopCallBackListener.callBack();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox sp_check;
        private final ImageView sp_img;
        private final TextView sp_name;
        private final TextView sp_price;
        private final JiaJianView jiaJianView;

        public ViewHolder(View itemView) {
            super(itemView);
            sp_check = itemView.findViewById(R.id.sp_check);
            sp_img = itemView.findViewById(R.id.sp_img);
            sp_name = itemView.findViewById(R.id.sp_name);
            sp_price = itemView.findViewById(R.id.sp_price);
            jiaJianView = itemView.findViewById(R.id.jiajianView);


        }
    }
    public void selOrdelAll(boolean isSelectAll){
        for (ShopBean.DataBean.ListBean listBean:list){
            listBean.setCheck(isSelectAll);
        }
        notifyDataSetChanged();
    }
    private ShopCallBackListener mshopCallBackListener;

    public void setListener(ShopCallBackListener listeners) {
        this.mshopCallBackListener = listeners;
    }

    public interface ShopCallBackListener{
        void callBack();
    }
}
