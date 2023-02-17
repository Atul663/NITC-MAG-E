package com.example.nitcmag_e.getStarted;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.nitcmag_e.R;
import com.google.firebase.inappmessaging.MessagesProto;

import java.util.List;

public class getStartedPageAdapter extends PagerAdapter {

    Context mContext;
    List<screenItems> list;

    public getStartedPageAdapter(Context mContext, List<screenItems> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.get_started_img_screen,null);

        ImageView img = layout.findViewById(R.id.imageViewGetStartedImg);
        TextView title = layout.findViewById(R.id.textViewGetStartedTitle);
        TextView description = layout.findViewById(R.id.textViewGetStartedDescription);

        title.setText(list.get(position).getTitle());
        title.setText(list.get(position).getDescription());
        img.setImageResource(list.get(position).getImg());

        container.addView(layout);
        
        return layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
