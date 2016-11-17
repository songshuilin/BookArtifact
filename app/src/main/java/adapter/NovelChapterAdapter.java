package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;
/**
 * 作者  ：宋水林
 * 时间 ：2016-11-17
 * 描述 ：todo
 *
 */
public class NovelChapterAdapter extends FragmentPagerAdapter {

    private List<Fragment> mChapterListFragments;
    public NovelChapterAdapter(FragmentManager fm, List<Fragment> mChapterListFragments) {
        super(fm);
        this.mChapterListFragments=mChapterListFragments;

    }

    @Override
    public Fragment getItem(int position) {
        return mChapterListFragments.get(position);
    }

    @Override
    public int getCount() {
        return mChapterListFragments.size();
    }
}
