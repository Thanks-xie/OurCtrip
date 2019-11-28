package cn.xie.ourctrip.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

import cn.xie.ourctrip.view.RoundImageView;

/**
 * @author xiejinbo
 * @date 2019/11/18 0018 13:43
 */
public class GlideImageLoader extends ImageLoader {
    //图片圆角弧度
    private float radius;

    public GlideImageLoader(float radius){
        this.radius = radius;
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
        Glide
                .with(context)
                .load(path)
                .centerCrop()
                .into(imageView);
    }
    /**
     * 自定义圆角类
     * @param context
     * @return
     */
    @Override
    public ImageView createImageView(Context context) {
        RoundImageView roundImageView = new RoundImageView(context);
        roundImageView.setImageViewRadius(context,radius);
        return roundImageView;

    }
}
