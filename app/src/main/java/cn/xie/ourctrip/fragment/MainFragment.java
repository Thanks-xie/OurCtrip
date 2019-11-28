package cn.xie.ourctrip.fragment;

import android.content.Context;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import cn.xie.ourctrip.R;
import cn.xie.ourctrip.util.GlideImageLoader;
import cn.xie.ourctrip.util.GlideRoundImage;
import cn.xie.ourctrip.util.Utils;


/**
 * @author xiejinbo
 * @date 2019/11/26 0026 15:56
 */
public class MainFragment extends Fragment {
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainLayout = inflater.inflate(R.layout.fragment_main_layout, container, false);
        context = mainLayout.getContext();
        initView(mainLayout);
        initBanner(mainLayout);
        initSceneryHotBanner(mainLayout);
        return mainLayout;
    }

    /**
     * 加载热门风景轮播图
     * @param mainLayout
     */
    private void initSceneryHotBanner(View mainLayout) {
        List<String> images = new ArrayList<>();
        images.add("https://lvchen.coding.net/p/tupianyun/git/raw/master/image20.jpg");
        images.add("https://lvchen.coding.net/p/tupianyun/git/raw/master/image25.jpg");
        images.add("https://lvchen.coding.net/p/tupianyun/git/raw/master/image26.jpg");
        images.add("https://lvchen.coding.net/p/tupianyun/git/raw/master/image27.jpg");
        images.add("https://lvchen.coding.net/p/tupianyun/git/raw/master/image30.jpg");
        Banner banner = (Banner) mainLayout.findViewById(R.id.banner_scenery_hot);
        Utils.initBanner(context,banner,images,10);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText(context, "position" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化布局
     *
     * @param mainLayout
     */
    private void initView(View mainLayout) {



    }

    /**
     * 加载广告banner轮播图
     *
     * @param mainLayout
     */
    private void initBanner(View mainLayout) {
        List<String> images = new ArrayList<>();
        images.add("https://lvchen.coding.net/p/tupianyun/git/raw/master/image8.jpg");
        images.add("https://lvchen.coding.net/p/tupianyun/git/raw/master/image9.jpg");
        images.add("https://lvchen.coding.net/p/tupianyun/git/raw/master/image11.jpg");
        images.add("https://lvchen.coding.net/p/tupianyun/git/raw/master/image13.jpg");
        images.add("https://lvchen.coding.net/p/tupianyun/git/raw/master/image16.jpg");
        images.add("https://lvchen.coding.net/p/tupianyun/git/raw/master/image19.jpg");
        Banner banner = (Banner) mainLayout.findViewById(R.id.banner);
        Utils.initBanner(context,banner,images,0);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText(context, "position" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }


}

