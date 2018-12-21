package asus.com.bwie.gwc3.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import asus.com.bwie.gwc3.R;
import asus.com.bwie.gwc3.bean.ShopBean;

public class ShangJiaAdapter extends RecyclerView.Adapter<ShangJiaAdapter.ViewHolder> {
    private List<ShopBean.DataBean> mlist=new ArrayList<>();
    private Context mContext;

    public ShangJiaAdapter(Context mContext) {
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.sj_view, null);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.sj_name.setText(mlist.get(position).getSellerName());
        final ShangPinAdapter shangPinAdapter=new ShangPinAdapter(mContext,mlist.get(position).getList());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        holder.recyclerView.setAdapter(shangPinAdapter);

        holder.sj_check.setChecked(mlist.get(position).isCheck());


        shangPinAdapter.setListener(new ShangPinAdapter.ShopCallBackListener() {
            @Override
            public void callBack() {
                if (selOrDelSPListener!=null){
                    selOrDelSPListener.selordelsp(mlist);
                }

                List<ShopBean.DataBean.ListBean> listBeans=mlist.get(position).getList();
                boolean isAllChecked =true;
                for (ShopBean.DataBean.ListBean bean:listBeans){
                    if (!bean.isCheck()){
                        isAllChecked=false;
                        break;
                    }
                }
                holder.sj_check.setChecked(isAllChecked);
                mlist.get(position).setCheck(isAllChecked);

            }
        });
        holder.sj_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先改变自己的标志位
                mlist.get(position).setCheck(holder.sj_check.isChecked());
                //调用商品的全选反选
                shangPinAdapter.selOrdelAll(holder.sj_check.isChecked());
            }
        });



    }
    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public void setMlist(List<ShopBean.DataBean> mlist) {
        this.mlist = mlist;
        notifyDataSetChanged();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        private final RecyclerView recyclerView;
        private final CheckBox sj_check;
        private final TextView sj_name;

        public ViewHolder(View itemView) {
            super(itemView);
            sj_check = itemView.findViewById(R.id.sj_check);
            sj_name = itemView.findViewById(R.id.sj_name);
            recyclerView = itemView.findViewById(R.id.recycleView_shop);
            }
    }

    private SelOrDelSPListener selOrDelSPListener;

    public void setSelOrDelSPListener(SelOrDelSPListener selOrDelSPListener) {
        this.selOrDelSPListener = selOrDelSPListener;
    }

    public interface SelOrDelSPListener{
        void selordelsp(List<ShopBean.DataBean> list);
    }
}
