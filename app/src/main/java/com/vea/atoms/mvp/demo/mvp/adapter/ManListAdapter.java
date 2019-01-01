package com.vea.atoms.mvp.demo.mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vea.atoms.mvp.commonsdk.adapter.BaseRecyclerAdapter;
import com.vea.atoms.mvp.commonsdk.utils.ImageLoader;
import com.vea.atoms.mvp.demo.R;
import com.vea.atoms.mvp.demo.mvp.model.entity.User;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ================================================
 * Created by Vea on 2018/8/26
 * ================================================
 */
public class ManListAdapter extends BaseRecyclerAdapter<User> {

    public ManListAdapter(Context context) {
        super(context, ONLY_FOOTER);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_man, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, User item, int position) {
        ViewHolder h = (ViewHolder) holder;
        ImageLoader.load(mContext, h.image, item.getAvatarUrl());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_girl)
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
