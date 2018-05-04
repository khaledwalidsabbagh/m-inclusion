package com.minclusion.iteration1.cookandlearn.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.minclusion.iteration1.BaseActivity;
import com.minclusion.iteration1.R;
import com.minclusion.iteration1.utils.QuickActionWindow;
import com.minclusion.iteration1.utils.Util;

import java.util.ArrayList;
import java.util.List;

import entities.cookandlearn.CookingStep;
import entities.cookandlearn.Translation;

/**
 * CookingStepActivity is responsible to give the user the first introductory
 * pages of the app. The pages introduce what the application is, ...
 */
public class CookingStepActivity extends BaseActivity {
    // pager to show a slider for cooking steps
    private ViewPager pager;

    // buttons to move between elements in the pager
    private FloatingActionButton next, previous;

    // adapter for cooking pager
    private CookingStepPagerAdapter cookingStepPagerAdapter;

    //temporary location for "cooking step" data
    private List<CookingStep> cookingSteps;

    // radio button group to highlight the current step
    private RadioGroup paginationGroup;
    // an array of radio buttons showing the steps the user is on
    private RadioButton[] stepButtons;

    // reference to the dish this cooking step belongs to
    private Integer dishId;

    //translations db conn
    private Translation translation = new Translation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the activities UI content
        setContentView(R.layout.activity_cooking_step);

        // initialize the app drawer and toolbar
        initializeToolBarAndAppDrawer();
        toolbar.setTitle(getString(R.string.cook_and_learn));

        //get reference for UI elements
        next = findViewById(R.id.next);
        previous = findViewById(R.id.previous);
        pager = findViewById(R.id.step_pager);
        paginationGroup = findViewById(R.id.pagination);

        // get the dish selected
        try {
            dishId = getIntent().getIntExtra("dishId", Util.NON_EXISTING_ID);
        }catch(Exception ex){

        }

        //initialize the data and adapter
        cookingStepPagerAdapter = new CookingStepPagerAdapter(dishId);
        cookingSteps = cookingStepPagerAdapter.getCookingStepList();
        stepButtons = new RadioButton[cookingSteps.size()];

        // initially show the next icon (->) and not the ovä(practice) text
        findViewById(R.id.practice_text).setVisibility(View.INVISIBLE);
        next.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.next_icon));

        // configure data fro the pager
        pager.setAdapter(cookingStepPagerAdapter);

        // add some radio buttons (corresponding to step numbers) to the radio group
        for(int i =0; i < cookingSteps.size(); i++){
            //CookingStep c = cookingStepPagerAdapter.getCookingStepList().get(i);
            // add a radio button for this step
            stepButtons[i] = new RadioButton(this);
            stepButtons[i].setId(i);
            paginationGroup.addView(stepButtons[i]);
            // set the first element as checked
            stepButtons[0].setChecked(true);
        }



        // set an action listener for the radio group
        paginationGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                //set the current checked box
                pager.setCurrentItem(checkedId);
            }
        });

        // add an action listener for the next button
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pager.getCurrentItem() != cookingStepPagerAdapter.getCount() - 1) {
                    pager.setCurrentItem(pager.getCurrentItem() + 1);
                } else {
                    Intent intent = new Intent(getApplicationContext(), DragAndDropWordToImageGame.class);
                    intent.putExtra("dishId", dishId);
                    intent.putExtra("step", 0);
                    startActivity(intent);
                }

            }
        });

        // set the previous button to hidden initially, since we are at elemnet 0 in the pager
        // and element zero doesn't have a previous pager element
        previous.setVisibility(View.INVISIBLE);
        // add an action listener for the previous button
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show the previous element in the pager
                pager.setCurrentItem(pager.getCurrentItem() - 1);
            }
        });

        // activated when the user moves between pages
        pager.addOnPageChangeListener(
                new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageSelected(int position) {
                        stepButtons[position].setChecked(true);
                        //if this is the last cooking step set text to continue
                        if (position != cookingSteps.size() - 1) {

                            findViewById(R.id.practice_text).setVisibility(View.INVISIBLE);

                            next.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                                    R.drawable.next_icon));

                        }else{
                            next.setImageDrawable(null);
                            findViewById(R.id.practice_text).setVisibility(View.VISIBLE);
                        }

                        // if this is the first element, hide the previous button
                        if (pager.getCurrentItem() <= 0) {
                            previous.setVisibility(View.INVISIBLE);
                        } else {
                            // otherwise show the preivous button
                            previous.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {}
                });

    }

    /**
     * CookingStepPagerAdapter provides a factory for the pages that will be displayed
     * to show the steps involved in cooking a dish
     */
    class CookingStepPagerAdapter extends PagerAdapter {
        // place to show an introductory page's image
        ImageView image;
        // text views to show the title and description of the introduction page
        TextView title, description;

        // holds the list of cooking steps
        List<CookingStep> cookingStepList = new ArrayList<CookingStep>();

        /*
         * Default constuctor adds some elements to the cooking step
         * from the intent
         */
        public CookingStepPagerAdapter(Integer dishId) {
            // save the dish id passed

            // get the cooking steps for this dish from the data-store
            cookingStepList = CookingStep.getAll(dishId);
        }

        @Override
        public int getCount() {
            return cookingStepList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater inflater = getLayoutInflater();
            View itemView = inflater.inflate(R.layout.introduction_pager_item, container,
                    false);
            itemView.findViewById(R.id.collaborator).setVisibility(View.INVISIBLE);

            image = itemView.findViewById(R.id.icon);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);

            String properResourceName = Util.getResourceName(cookingStepList.get(position).getImage());

            // construct a resouurce id
            int resID = getResources().getIdentifier(properResourceName,
                    "raw", getPackageName());

            // load image with Glide library
            Glide.with(CookingStepActivity.this)
                    .load(resID)
                    .into(image);

            //set the data
            title.setText(String.format("%s %d", getString(R.string.cooking_step),
                    cookingStepList.get(position).getStepNumber()));

            description.setText(cookingStepList.get(position).getText());

            final String descriptionText = cookingStepList.get(position).getText();
            //create a spannablestring to store the cooking steps
            final SpannableString ss = new SpannableString(cookingStepList.get(position).getText());

            for(String word: translation.getTranslations().keySet()){
                String arabicTranslation = translation.getTranslations().get(word);
                //if the description contains a translation as a word
                int startOfWord = descriptionText.indexOf(word);
                if(startOfWord != -1 && ((startOfWord == descriptionText.length()-1) |
                        descriptionText.charAt(startOfWord + word.length()) == ' ')){

                    startOfWord = descriptionText.indexOf(word);
                    int endOfWord = startOfWord + word.length();

                    ss.setSpan(new CookingStepClickableSpan(CookingStepActivity.this, word,
                                    arabicTranslation), startOfWord, endOfWord,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            description.setText(ss);
            description.setMovementMethod(LinkMovementMethod.getInstance());
            description.setHighlightColor(Color.TRANSPARENT);

            (container).addView(itemView);
            return itemView;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            (container).removeView((LinearLayout) object);
        }

        public List<CookingStep> getCookingStepList(){
            return cookingStepList;
        }
    }
}
