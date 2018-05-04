package adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.minclusion.iteration1.R;

/**
 * Created by Lisanu Tebikew Yallew on 1/17/18.
 */

public class DishFragmentPagerAdapter extends FragmentPagerAdapter {
        private Fragment[] fragments;
        private Context ctx;

        public DishFragmentPagerAdapter(FragmentManager fm, Fragment[] frags, Context ctx){
            super(fm);
            this.fragments = frags;
            this.ctx = ctx;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return ctx.getString(R.string.meals);
                case 1:
                    return ctx.getString(R.string.appetizers);
                case 2:
                    return ctx.getString(R.string.fika);
            }
            return null;
        }

    }