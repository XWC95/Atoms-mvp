package com.vea.atoms.mvp.commonsdk.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;

import java.security.MessageDigest;


/**
 * ================================================
 * Glide 图片加载辅助类,支持圆形图片
 *
 * Created by Vea on 30/03/2018 17:16
 * ================================================
 */
public class ImageLoader {

    private ImageLoader() {
    }

    public static void loadImage(Context context, ImageView view, String url) {
        Glide.with(context).load(url).into(view);
    }

    /**
     * @param placeholder 加载中的图片
     * @param error       加载失败显示的图片
     */
    public static void loadImage(Context context, ImageView view, String url, int placeholder, int error) {
        RequestOptions options = new RequestOptions()
            .centerCrop()
            .placeholder(placeholder)
            .error(error)
            .priority(Priority.HIGH);

        Glide.with(context).load(url).apply(options).into(view);
    }

    /**
     *  加载圆形图片
     * @param context
     * @param view
     * @param url
     */
    public static void loadCircleImage(Context context, ImageView view, String url) {
        RequestOptions options = new RequestOptions()
            .centerCrop()
            .priority(Priority.HIGH)
            .transform(new GlideCircleTransform());
        Glide.with(context).load(url).apply(options).into(view);
    }

    /**
     *  加载圆形图片
     * @param context
     * @param view
     * @param url
     * @param error
     */
    public static void loadCircleImage(Context context, ImageView view, String url, int error) {
        RequestOptions options = new RequestOptions()
            .centerCrop()
            .placeholder(error)
            .error(error)
            .priority(Priority.HIGH)
            .transform(new GlideCircleTransform());
        Glide.with(context).load(url).apply(options).into(view);
    }

    public static class GlideCircleTransform extends BitmapTransformation {

        public GlideCircleTransform() {
            super();
        }

        @Override
        public Bitmap transform(BitmapPool pool, Bitmap toTransform,
            int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public void updateDiskCacheKey(MessageDigest messageDigest) {

        }
    }
}
