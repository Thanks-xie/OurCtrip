package cn.xie.ourctrip.activity;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import cn.xie.ourctrip.R;
import cn.xie.ourctrip.fragment.CustomerFragment;
import cn.xie.ourctrip.fragment.MainFragment;
import cn.xie.ourctrip.fragment.MineFragment;
import cn.xie.ourctrip.fragment.TourFragment;
import cn.xie.ourctrip.fragment.TripFragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

    /**
     * 五个Fragment
     */
    private MainFragment mainFragment;
    private TripFragment tripFragment;
    private TourFragment tourFragment;
    private CustomerFragment customerFragment;
    private MineFragment mineFragment;

    /**
     * 五个界面布局
     */
    private View mainFragmentLayout;
    private View tripFragmentLayout;
    private View tourFragmentLayout;
    private View customerFragmentLayout;
    private View mineFragmentLayout;


    /**
     * 五个tab上的图片控件
     */
    private ImageView mainIcon;
    private ImageView tripIcon;
    private ImageView tourIcon;
    private ImageView customerIcon;
    private ImageView mineIcon;

    /**
     * 五个tab上的文字控件
     */
    private TextView mainText;
    private TextView tripText;
    private TextView tourText;
    private TextView customerText;
    private TextView mineText;

    /**
     * 对五个Fragment进行管理
     */
    private FragmentManager fragmentManager;

    /**
     * 当前选中的fragment tab
     */
    private int currentPage = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(null != savedInstanceState){
            currentPage = savedInstanceState.getInt("neo");
        }
        //透明式状态栏
        if (Build.VERSION.SDK_INT>=21){
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        initView();

        fragmentManager = getSupportFragmentManager();
        // 第一次启动时选中第0个tab
        //在FragmentManager里面根据Tag去找相应的fragment. 用户屏幕发生旋转，重新调用onCreate方法。否则会发生重叠
        mainFragment = (MainFragment) fragmentManager.findFragmentByTag("main");
        tripFragment = (TripFragment) fragmentManager.findFragmentByTag("trip");
        tourFragment = (TourFragment) fragmentManager.findFragmentByTag("tour");
        customerFragment = (CustomerFragment) fragmentManager.findFragmentByTag("customer");
        mineFragment = (MineFragment) fragmentManager.findFragmentByTag("mine");
        // 第一次启动时选中第0个tab
        setTabSelection(currentPage);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (fragmentManager!=null){

        }
    }

    private void initView() {
        mainFragmentLayout = findViewById(R.id.main_layout);
        tripFragmentLayout = findViewById(R.id.trip_layout);
        tourFragmentLayout = findViewById(R.id.photo_tour_layout);
        customerFragmentLayout = findViewById(R.id.customer_layout);
        mineFragmentLayout = findViewById(R.id.mine_layout);

        mainIcon = findViewById(R.id.main_image);
        tripIcon = findViewById(R.id.trip_image);
        tourIcon = findViewById(R.id.photo_tour_image);
        customerIcon = findViewById(R.id.customer_image);
        mineIcon = findViewById(R.id.mine_image);

        mainText = (TextView) findViewById(R.id.main_text);
        tripText = (TextView) findViewById(R.id.trip_text);
        tourText = (TextView) findViewById(R.id.photo_tour_text);
        customerText = (TextView) findViewById(R.id.customer_text);
        mineText = (TextView) findViewById(R.id.mine_text);
    }



    /**
     * 点击主页tab
     * @param view
     */
    public void onMainTabClick(View view) {
        setTabSelection(0);
        currentPage = 0;
    }

    /**
     * 点击行程tab
     * @param view
     */
    public void onTripTabClick(View view) {
        setTabSelection(1);
        currentPage = 1;
    }

    /**
     * 点击旅拍tab
     * @param view
     */
    public void onPhotoTourTabClick(View view) {
        setTabSelection(2);
        currentPage = 2;
    }
    /**
     * 点击客服Tab
     * @param view
     */
    public void onCustomerTabClick(View view) {
        setTabSelection(3);
        currentPage = 3;
    }
    /**
     * 点击我的Tab
     * @param view
     */
    public void onMineTabClick(View view) {
        setTabSelection(4);
        currentPage = 4;
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index
     *            每个tab页对应的下标。0表示主页，1表示行程，2表示旅拍，3表示客服，4表示我的。
     */
    private void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                // 当点击了主页tab时，改变控件的图片和文字颜色
                mainIcon.setImageResource(R.drawable.main_icon_selected);
                mainText.setTextColor(getResources().getColor(R.color.menu_tab_text));
                if (mainFragment == null) {
                    // 如果MainFragment为空，则创建一个并添加到界面上
                    mainFragment = new MainFragment();
                    transaction.add(R.id.content, mainFragment,"main");
                } else {
                    // 如果MainFragment不为空，则直接将它显示出来
                    transaction.show(mainFragment);
                }
                break;
            case 1:
                // 当点击了行程tab时，改变控件的图片和文字颜色
                tripIcon.setImageResource(R.drawable.trip_icon_selected);
                tripText.setTextColor(getResources().getColor(R.color.menu_tab_text));
                if (tripFragment == null) {
                    // 如果TripFragment为空，则创建一个并添加到界面上
                    tripFragment = new TripFragment();
                    transaction.add(R.id.content, tripFragment,"trip");
                } else {
                    // 如果TripFragment不为空，则直接将它显示出来
                    transaction.show(tripFragment);
                }
                break;
            case 2:
                // 当点击了旅拍tab时，改变控件的图片和文字颜色
                tourIcon.setImageResource(R.drawable.photo_tour_selected);
                tourText.setTextColor(getResources().getColor(R.color.menu_tab_text));
                if (tourFragment == null) {
                    // 如果TourFragment为空，则创建一个并添加到界面上
                    tourFragment = new TourFragment();
                    transaction.add(R.id.content, tourFragment,"tour");
                } else {
                    // 如果TourFragment不为空，则直接将它显示出来
                    transaction.show(tourFragment);
                }
                break;
            case 3:
                // 当点击了客服tab时，改变控件的图片和文字颜色
                customerIcon.setImageResource(R.drawable.customer_icon_selected);
                customerText.setTextColor(getResources().getColor(R.color.menu_tab_text));
                if (customerFragment == null) {
                    // 如果CustomerFragment为空，则创建一个并添加到界面上
                    customerFragment = new CustomerFragment();
                    transaction.add(R.id.content, customerFragment,"setting");
                } else {
                    // 如果CustomerFragment不为空，则直接将它显示出来
                    transaction.show(customerFragment);
                }
                break;
            case 4:
                default:
                // 当点击了客服tab时，改变控件的图片和文字颜色
                mineIcon.setImageResource(R.drawable.mine_icon_selected);
                mineText.setTextColor(getResources().getColor(R.color.menu_tab_text));
                if (mineFragment == null) {
                    // 如果MineFragment为空，则创建一个并添加到界面上
                    mineFragment = new MineFragment();
                    transaction.add(R.id.content, mineFragment,"mine");
                } else {
                    // 如果MineFragment不为空，则直接将它显示出来
                    transaction.show(mineFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        mainIcon.setImageResource(R.drawable.main_icon_unselected);
        mainText.setTextColor(Color.parseColor("#ffffff"));
        tripIcon.setImageResource(R.drawable.trip_icon_unselected);
        tripText.setTextColor(Color.parseColor("#ffffff"));
        tourIcon.setImageResource(R.drawable.photo_tour_unselected);
        tourText.setTextColor(Color.parseColor("#ffffff"));
        customerIcon.setImageResource(R.drawable.customer_icon_unselected);
        customerText.setTextColor(Color.parseColor("#ffffff"));
        mineIcon.setImageResource(R.drawable.mine_icon_unselected);
        mineText.setTextColor(Color.parseColor("#ffffff"));
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (mainFragment != null) {
            transaction.hide(mainFragment);
        }
        if (tripFragment != null) {
            transaction.hide(tripFragment);
        }
        if (tourFragment != null) {
            transaction.hide(tourFragment);
        }
        if (customerFragment != null) {
            transaction.hide(customerFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("neo",currentPage);
        super.onSaveInstanceState(outState);
        Log.i("neo", "onSaveInstanceState");
    }
}
