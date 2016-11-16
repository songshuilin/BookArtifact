package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;




/**
 * 作者  ：宋水林
 * 时间 ：2016-11-15
 * 描述 ：小说的展示，的适配器
 *
 */
public class NovelFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mListFragments;

    public NovelFragmentAdapter(FragmentManager fm, List<Fragment> mListFragments) {
        super(fm);
        this.mListFragments=mListFragments;
    }

    /**
     * 返回该位置的fragment
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return mListFragments.get(position);
    }

    /**
     * fragment的数量
     * @return
     */
    @Override
    public int getCount() {
        return mListFragments.size();
    }

}
