package utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;
import com.example.edu.bookartifact.R;

import static android.util.Log.i;

/**
 * Created by roor on 2016/11/18.
 */

public class TopicRefreshHeadView extends LinearLayout implements SwipeRefreshTrigger, SwipeTrigger {
    public TopicRefreshHeadView(Context context) {
        super(context);

    }

    public TopicRefreshHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public TopicRefreshHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @Override
    public void onRefresh() {
        ((ImageView)(this.getChildAt(0))).setImageResource(R.drawable.loading_icon);
        RotateAnimation rotate=new RotateAnimation(0.0f, 360.0f
                , Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setRepeatCount(12);
        rotate.setRepeatMode(Animation.INFINITE);
        ((ImageView)(this.getChildAt(0))).startAnimation(rotate);
    }

    @Override
    public void onPrepare() {
    }

    @Override
    public void onMove(int yScrolled, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            if (yScrolled >= getHeight()) {
                ((ImageView)(this.getChildAt(0))).setImageResource(R.drawable.up);
            } else {
                ((ImageView)(this.getChildAt(0))).clearAnimation();
                ((ImageView)(this.getChildAt(0))).setImageResource(R.drawable.ic_pulltorefresh_arrow);
            }
        } else {
//            setText("REFRESH RETURNING");
        }
    }

    @Override
    public void onRelease() {
//        setText("onRelease");
        i("@@@@@", "onRelease" );
    }

    @Override
    public void onComplete() {
        i("@@@@", "onComplete" + "");
        (this.getChildAt(0)).clearAnimation();
        ((ImageView)(this.getChildAt(0))).setImageResource(R.drawable.refresh_finished_circle);
    }

    @Override
    public void onReset() {
        // setText("onReset");
    }
}
