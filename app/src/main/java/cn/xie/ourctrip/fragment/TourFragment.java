package cn.xie.ourctrip.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import cn.xie.ourctrip.R;

/**
 * @author xiejinbo
 * @date 2019/11/26 0026 15:56
 */
public class TourFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.fragment_tour_layout, container, false);
        return messageLayout;
    }

}

