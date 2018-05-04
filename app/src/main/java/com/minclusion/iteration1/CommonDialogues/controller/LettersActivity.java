package com.minclusion.iteration1.CommonDialogues.controller;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.minclusion.iteration1.BaseActivity;
import com.minclusion.iteration1.R;

import db.Consonant;

import java.util.List;

import db.Vowel;

public class LettersActivity extends BaseActivity {
    private List<Vowel> vowels;
    private List<Consonant> consonants;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public String tabTitle, type = "";
    private Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_letters);
        initializeToolBarAndAppDrawer();
        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        if (type.equals("vowel")) {
            vowels = Vowel.getAll();
        } else {
            consonants = Consonant.getAll();
        }
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.vpVowels);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(8);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tVowels);
        tabLayout.setupWithViewPager(mViewPager);

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            bundle = new Bundle();

            if (type.equals("vowel")) {
                bundle.putInt("letter", vowels.get(position).getId());
                bundle.putString("letterType", type);

            } else {
                bundle.putInt("letter", consonants.get(position).getId());
                bundle.putString("letterImage", consonants.get(position).getImagePath());
                bundle.putString("letterType", type);
                bundle.putString("titleWord", consonants.get(position).getMainWord());
                bundle.putString("description", consonants.get(position).getDescription());
            }

            bundle.putString("letterName", getPageTitle(position).toString());

            LettersFragment tabVowel = new LettersFragment();
            tabVowel.setArguments(bundle);
            return tabVowel;
        }

        @Override
        public int getCount() {
            if (type.equals("vowel"))
                return vowels.size();
            else
                return consonants.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if (type.equals("vowel")) {
                if (position >= 0 && position < 6) {
                    tabTitle = vowels.get(position).getName();
                } else {
                    switch (position) {
                        case 6:
                            tabTitle = "å";
                            break;
                        case 7:
                            tabTitle = "ä";
                            break;
                        case 8:
                            tabTitle = "ö";
                            break;
                    }
                }
            } else if (consonants.get(position).getName().equals("sj"))
                tabTitle = "sj-ljud";
            else
                tabTitle = consonants.get(position).getName();
            return tabTitle;
        }
    }
}
