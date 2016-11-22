package custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeLoadMoreTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;
import com.example.edu.bookartifact.R;

/**
 * Created by Administrator on 2016/11/18.
 */
public class LoadMoreFooterView extends LinearLayout implements SwipeTrigger, SwipeLoadMoreTrigger {
    public LoadMoreFooterView(Context context) {
        super(context);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onLoadMore() {
        ((ImageView) (this.getChildAt(0))).setVisibility(VISIBLE);
        ((ImageView) (this.getChildAt(0))).setImageResource(R.drawable.ic_loading_animation);
        ((TextView) this.getChildAt(1)).setText("拼命加载中...");
        RotateAnimation rotate=new RotateAnimation(0.0f, 360.0f
                , Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(500);
        rotate.setRepeatCount(30);
        rotate.setRepeatMode(Animation.INFINITE);
        ((ImageView)(this.getChildAt(0))).startAnimation(rotate);
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onMove(int yScrolled, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            if (yScrolled <= -getHeight()) {
                ((ImageView) (this.getChildAt(0))).setVisibility(VISIBLE);
                ((ImageView) (this.getChildAt(0))).setImageResource(R.drawable.ic_loading_animation);
                ((TextView) this.getChildAt(1)).setText("拼命加载中...");
            }
        }
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {
        ((ImageView) (this.getChildAt(0))).clearAnimation();
        ((ImageView) (this.getChildAt(0))).setVisibility(GONE);
        ((TextView) this.getChildAt(1)).setText("加载完成");
    }

    @Override
    public void onReset() {

    }
}