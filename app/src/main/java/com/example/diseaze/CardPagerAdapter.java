package com.example.diseaze;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

public class CardPagerAdapter extends PagerAdapter {

    private Context mContext;
    private int[] mCardLayouts;

    public CardPagerAdapter(Context context, int[] cardLayouts) {
        mContext = context;
        mCardLayouts = cardLayouts;
    }

    @Override
    public int getCount() {
        return mCardLayouts.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(mCardLayouts[position], container, false);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((CardView) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}