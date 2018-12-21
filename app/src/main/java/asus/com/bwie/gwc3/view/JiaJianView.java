package asus.com.bwie.gwc3.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import asus.com.bwie.gwc3.R;
import asus.com.bwie.gwc3.adapter.ShangPinAdapter;
import asus.com.bwie.gwc3.bean.ShopBean;

public class JiaJianView extends LinearLayout implements View.OnClickListener {

    private EditText edit_num;
    private ImageView jian;
    private ImageView add;
    private List<ShopBean.DataBean.ListBean> list=new ArrayList<>();
    private int position;
    private ShangPinAdapter shangPinAdapter;

    public void setData(ShangPinAdapter shangPinAdapter, List<ShopBean.DataBean.ListBean> list, int position) {

        this.list = list;
        this.shangPinAdapter = shangPinAdapter;
        this.position=position;
        num=list.get(position).getNum();
        edit_num.setText(num+"");

    }

    public JiaJianView(Context context) {
        super(context);
        init(context);
    }

    public JiaJianView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public JiaJianView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }



    private Context context;
    private void init(Context context) {
        this.context=context;
        View view = View.inflate(context, R.layout.add_minus_view, null);
        jian = view.findViewById(R.id.jian);
        add = view.findViewById(R.id.add);
        edit_num = view.findViewById(R.id.edit_num);
        add.setOnClickListener(this);
        jian.setOnClickListener(this);
        addView(view);

        edit_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //改变数量
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
    private int num;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add:
                num++;
                edit_num.setText(num+"");
                list.get(position).setNum(num);
                listener.jiajian();
                shangPinAdapter.notifyItemChanged(position);
                break;
            case R.id.jian:
                if (num>1){
                    num--;

                }else {
                    Toast.makeText(getContext(),"我是有底线的",Toast.LENGTH_SHORT).show();
                }
                edit_num.setText(num+"");
                list.get(position).setNum(num);
                listener.jiajian();
                shangPinAdapter.notifyItemChanged(position);
                break;
        }

    }

    private JiajianListener listener;
    public void setOnJiaJian(JiajianListener listeners){
        this.listener=listeners;
    }


    public interface JiajianListener{
        void jiajian();
    }
}
