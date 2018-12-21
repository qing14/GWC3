package asus.com.bwie.gwc3.history;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import asus.com.bwie.gwc3.R;

public class HistoryTitleView extends LinearLayout {
    Context mContext;
    public HistoryTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View view = View.inflate(mContext, R.layout.title_week, null);


    }


}
