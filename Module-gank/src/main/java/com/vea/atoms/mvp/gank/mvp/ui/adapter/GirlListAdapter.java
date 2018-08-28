package com.vea.atoms.mvp.gank.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vea.atoms.mvp.commonsdk.adapter.BaseRecyclerAdapter;
import com.vea.atoms.mvp.commonsdk.utils.ImageLoader;
import com.vea.atoms.mvp.gank.R;
import com.vea.atoms.mvp.gank.R2;
import com.vea.atoms.mvp.gank.mvp.model.entity.GankItemBean;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * ================================================
 * Created by Vea on 2016/11/24
 * ================================================
 */
public class GirlListAdapter extends BaseRecyclerAdapter<GankItemBean> {

    public GirlListAdapter(Context context) {
        super(context, ONLY_FOOTER);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.gank_item_girl, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, GankItemBean item, int position) {
        ViewHolder h = (ViewHolder) holder;
        ImageLoader.loadImage(mContext, h.image, item.getUrl());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.iv_girl)
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
