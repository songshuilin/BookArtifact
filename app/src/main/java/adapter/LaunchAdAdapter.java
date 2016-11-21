package adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roor on 2016/11/21.
 */

public class LaunchAdAdapter extends PagerAdapter {
    List<ImageView> imageViewList = new ArrayList<>();
    public LaunchAdAdapter(List<ImageView> imageViewList){
        this.imageViewList = imageViewList;
    }
    @Override
    public int getCount() {
        return imageViewList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imageViewList.get(position));
        return imageViewList.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
