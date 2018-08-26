package com.vea.atoms.mvp.gank.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vea.atoms.mvp.gank.R;
import com.vea.atoms.mvp.gank.R2;
import com.vea.atoms.mvp.gank.mvp.model.entity.GankItemBean;



import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xuwc on 2016/11/30.
 */
public class TechAdapter extends BaseRecyclerAdapter<GankItemBean>{

    public TechAdapter(Context context ) {
        super(context, ONLY_FOOTER);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_tech, parent, false));

    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, GankItemBean item, int position) {

        ViewHolder h = (ViewHolder)holder;
        h.tvContent.setText(item.getDesc());
        h.tvAuthor.setText(item.getWho());
        String date =item.getPublishedAt();
        int idx = date.indexOf(".");

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.tv_tech_title)
        TextView tvContent;
        @BindView(R2.id.tv_tech_author)
        TextView tvAuthor;
        @BindView(R2.id.tv_tech_time)
        TextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
