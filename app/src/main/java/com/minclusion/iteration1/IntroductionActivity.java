package com.minclusion.iteration1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.minclusion.iteration1.CommonDialogues.controller.dialogue.CategoryDialogueActivity;

/**
 * IntroductionActivity is responsible to give the user the first introductory
 * pages of the app. The pages introduce what the application is, ...
 */
public class IntroductionActivity extends AppCompatActivity {
    FloatingActionButton next, previous;
    ViewPager pager;
    IntroductionPagerAdapter adapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor preferenceEditor;

    // radio button group to highlight the current step
    RadioGroup pagination;

    // an array of radio buttons showing the introduction step the user is on
    RadioButton[] stepButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check if this is the first time running the app and show IntroductionActivity
        sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
        preferenceEditor = sharedPreferences.edit();
        //if the user has already seen the introduction page
        //hide it
        if (sharedPreferences.getBoolean("Seen", false) == false) {
            preferenceEditor.putBoolean("Seen", true);
            preferenceEditor.commit();
        } else {
            startActivity(new Intent(getApplicationContext(), CategoryDialogueActivity.class));
        }

        // Continue setting up the activity
        setContentView(R.layout.activity_introduction2);
        next = findViewById(R.id.next);
        previous =  findViewById(R.id.previous);

        next.setImageDrawable(ContextCompat.getDrawable(IntroductionActivity.this,
                R.drawable.next_icon));

        previous.setImageDrawable(ContextCompat.getDrawable(IntroductionActivity.this,
                R.drawable.prev_icon));

        pager = (ViewPager) findViewById(R.id.introduction_pager);

        adapter = new IntroductionPagerAdapter();
        pager.setAdapter(adapter);

        stepButtons = new RadioButton[adapter.getCount()];
        pagination = findViewById(R.id.pagination);
        // add some radio buttons (corresponding to introductory pages) to the radio group
        for(int i =0; i < stepButtons.length; i++){
            // add a radio button for this step
            stepButtons[i] = new RadioButton(this);
            stepButtons[i].setId(i);
            pagination.addView(stepButtons[i]);
            // set the first element as checked
            stepButtons[0].setChecked(true);
        }

        // set an action listener for the radio group
        pagination.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                //set the current checked box
                pager.setCurrentItem(checkedId);

            }
        });

        previous.setVisibility(View.INVISIBLE);

        // add an action listener for the next button
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if it is not the last element being displayed
                // show the next element in pager
                if (pager.getCurrentItem() != adapter.getCount() - 1) {
                    pager.setCurrentItem(pager.getCurrentItem() + 1);
                } else {
                    // if the last page in pager was being displayed, start the mainactivity
                    // when user presses next
                    startActivity(new Intent(getApplicationContext(), CategoryDialogueActivity.class));
                }
            }
        });

        // add an action listener for the previous button
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(pager.getCurrentItem() - 1);
            }
        });

        // activated when the user moves between pages
        pager.addOnPageChangeListener(
                new ViewPager.OnPageChangeListener() {
                    boolean lastPageChange = false;

                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        int lastIdx = adapter.getCount() - 1;
                    }

                    @Override
                    public void onPageSelected(int position) {
                        stepButtons[position].setChecked(true);
                        //if this is the last cooking step set text to continue
                        if (position == adapter.getCount() - 1) {
//                            next.setText("متابعة");
//                            next.setText(getString( R.string.continue_to_next));
                        } else {
//                            next.setText(getString(R.string.next));
                        }

                        // if this is the first element, hide the previous button
                        if (pager.getCurrentItem() <= 0) {
                            previous.setVisibility(View.INVISIBLE);
                        } else {
                            // otherwise show the preivous button
                            previous.setVisibility(View.VISIBLE);
                        }
                        if(position == 5){
                            startActivity(new Intent(getApplicationContext(), CategoryDialogueActivity.class));
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                        int lastIdx = adapter.getCount() - 1;
                        int curItem = pager.getCurrentItem();
                        if(curItem==lastIdx  && state==1){
                            lastPageChange = true;
                            startActivity(new Intent(getApplicationContext(), CategoryDialogueActivity.class));
                        }else  {
                            lastPageChange = false;
                        }
                    }
                });

    }

    /**
     * IntroductionPageAdapter provides a factory for the pages that will be displayed
     * later to show an introduction about the appliation later.
     */
    class IntroductionPagerAdapter extends PagerAdapter {
        // place to show an introductory page's image
        ImageView image;
        // text views to show the title and description of the introduction page
        TextView title, description, collaborator;
        // the images to show in the introduction pages
        int[] images = {
                R.mipmap.logo,
                R.drawable.intro_2,
                R.drawable.intro_3
        };
        // TODO: replace me ! strings describing the pages
        int[] pageDescriptions = {
                R.string.description1,
                R.string.description2,
                R.string.description3
        };

        // get the introduction page titles from res/values/strings.xml
        int[] pageTitles = {
                R.string.title1,
                R.string.title2,
                R.string.title2
        };

        @Override
        public int getCount() {
            return pageTitles.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater inflater;
            inflater = (LayoutInflater) getLayoutInflater();
            View itemView = inflater.inflate(R.layout.introduction_pager_item, container, false);

            image = itemView.findViewById(R.id.icon);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            collaborator = itemView.findViewById(R.id.collaborator);
            if(position == 2){
                collaborator.setText(getString(R.string.collaborator));
                collaborator.setVisibility(View.VISIBLE);
            }
            else
                collaborator.setVisibility(View.INVISIBLE);

            title.setText(getString(pageTitles[position]));
            description.setText(getString(pageDescriptions[position]));

            //image.setImageResource(images[position]);
            // load image with Glide library
            Glide.with(getApplicationContext())
                    .load(images[position])
                    .into(image);


            (container).addView(itemView);
            return itemView;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            (container).removeView((LinearLayout) object);
        }
    }

}
