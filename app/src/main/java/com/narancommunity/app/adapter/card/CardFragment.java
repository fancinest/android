package com.narancommunity.app.adapter.card;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.narancommunity.app.R;


public class CardFragment extends Fragment {
    View viewCom;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewCom = inflater.inflate(R.layout.item_shuzhai, container, false);
        return viewCom;
    }

    public View getCardView() {
        return viewCom;
    }
}
