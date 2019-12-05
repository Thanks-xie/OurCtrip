package cn.xie.ourctrip.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.map.geolocation.TencentPoi;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import cn.xie.ourctrip.R;
import cn.xie.ourctrip.activity.SearchActivity;
import cn.xie.ourctrip.util.GlideImageLoader;
import cn.xie.ourctrip.util.GlideRoundImage;
import cn.xie.ourctrip.util.Utils;
import cn.xie.ourctrip.view.EditTextSearch;


/**
 * @author xiejinbo
 * @date 2019/11/26 0026 15:56
 */
public class MainFragment extends Fragment implements TencentLocationListener {
    private Context context;
    private EditTextSearch editTextSearch;
    private TextView locationCity;
    private TencentLocationManager tencentLocationManager;
    // 用于记录定位参数, 以显示到 UI
    private String mRequestParams;
    private static final int[] LEVELS = new int[] {
            TencentLocationRequest.REQUEST_LEVEL_GEO,
            TencentLocationRequest.REQUEST_LEVEL_NAME,
            TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA,
            TencentLocationRequest.REQUEST_LEVEL_POI};
    private static final int DEFAULT = 2;

    private int mIndex = DEFAULT;
    private int mLevel = LEVELS[DEFAULT];

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
     *
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
        Utils.initBanner(context, banner, images, 10);
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
        editTextSearch = mainLayout.findViewById(R.id.search);
        editTextSearch.setFocusable(false);
        editTextSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchActivity.class);
                startActivity(intent);
            }
        });
        locationCity = mainLayout.findViewById(R.id.location_text);
        locationHandler.sendEmptyMessage(1);
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
        Utils.initBanner(context, banner, images, 0);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText(context, "position" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 自动定位当前位置
     */
    @SuppressLint("HandlerLeak")
    private Handler locationHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (Build.VERSION.SDK_INT >= 23) {
                String[] permissions = {
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };

                if (context.checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(permissions, 0);
                }
            }
            autoGetLocation();
        }


    };

    /**
     * 初始化tencentLocationManager
     */
    public void autoGetLocation() {
        tencentLocationManager = TencentLocationManager.getInstance(context);
        startLocation();
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int error, String s) {
        Log.e("xiejinbo", "error: " + error);
        if (error == TencentLocation.ERROR_OK) {
            // 定位成功
            String location = toString(tencentLocation,mLevel);
            Log.e("xiejinbo", "location: " + location);
            stopLocation();
        }
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }

    /**
     * 开始定位
     */
    private void startLocation() {
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setInterval(5000);
        request.setRequestLevel(mLevel);
        tencentLocationManager.requestLocationUpdates(request, this);
    }

    /**
     * 停止定位
     */
    private void stopLocation() {
        if (tencentLocationManager != null) {
            tencentLocationManager.removeUpdates(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        stopLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意。
                    autoGetLocation();
                } else {

                }
                break;
        }
    }

    // ===== util method
    private  String toString(TencentLocation location, int level) {
        StringBuilder sb = new StringBuilder();

        sb.append("latitude=").append(location.getLatitude()).append(",");
        sb.append("longitude=").append(location.getLongitude()).append(",");
        sb.append("altitude=").append(location.getAltitude()).append(",");
        sb.append("accuracy=").append(location.getAccuracy()).append(",");

        switch (level) {
            case TencentLocationRequest.REQUEST_LEVEL_GEO:
                break;

            case TencentLocationRequest.REQUEST_LEVEL_NAME:
                sb.append("name=").append(location.getName()).append(",");
                sb.append("address=").append(location.getAddress()).append(",");
                break;

            case TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA:
            case TencentLocationRequest.REQUEST_LEVEL_POI:
            case 7:
                sb.append("nation=").append(location.getNation()).append(",");
                sb.append("province=").append(location.getProvince()).append(",");
                sb.append("city=").append(location.getCity()).append(",");
                sb.append("district=").append(location.getDistrict()).append(",");
                sb.append("town=").append(location.getTown()).append(",");
                sb.append("village=").append(location.getVillage()).append(",");
                sb.append("street=").append(location.getStreet()).append(",");
                sb.append("streetNo=").append(location.getStreetNo()).append(",");

                //此处动态设置用户当前所处城市：
                locationCity.setText(location.getDistrict());
                if (level == TencentLocationRequest.REQUEST_LEVEL_POI) {
                    List<TencentPoi> poiList = location.getPoiList();
                    int size = poiList.size();
                    for (int i = 0, limit = 3; i < limit && i < size; i++) {
                        sb.append("\n");
                        sb.append("poi[" + i + "]=")
                                .append(toString(poiList.get(i))).append(",");
                    }
                }

                break;

            default:
                break;
        }

        return sb.toString();
    }
    private static String toString(TencentPoi poi) {
        StringBuilder sb = new StringBuilder();
        sb.append("name=").append(poi.getName()).append(",");
        sb.append("address=").append(poi.getAddress()).append(",");
        sb.append("catalog=").append(poi.getCatalog()).append(",");
        sb.append("distance=").append(poi.getDistance()).append(",");
        sb.append("latitude=").append(poi.getLatitude()).append(",");
        sb.append("longitude=").append(poi.getLongitude()).append(",");
        return sb.toString();
    }
}

