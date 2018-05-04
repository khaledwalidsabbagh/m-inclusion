package com.minclusion.iteration1.CommonDialogues.controller.dialogue;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.minclusion.iteration1.R;
import com.minclusion.iteration1.utils.UsageLogger;

import java.util.List;

import adapters.DialogueListAdapter;
import db.Dialogue;
import interfaces.ListItemClickListener;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class CategoryFragment extends Fragment implements ListItemClickListener {

    private RecyclerView recyclerDialogue;
    private List<Dialogue> dialogues;
    private int[] dialogueIds;
    private String[] dialogueTitles, dialogueUris;
    private int catId;

    private static final String SHOWCASE_ID = "HOME_PAGE_TABBED_PANE_TUTORIAL";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_category, container, false);

        catId = getArguments().getInt("categoryId");
        dialogues = Dialogue.getAllInCategory(catId);

        this.recyclerDialogue = rootView.findViewById(R.id.dialogueRecyclerView);
        DialogueListAdapter adapter = new DialogueListAdapter(getContext(), dialogues, this);

        this.recyclerDialogue.setLayoutManager(new LinearLayoutManager(getContext()));
        this.recyclerDialogue.setAdapter(adapter);

        dialogueIds = new int[dialogues.size()];
        dialogueTitles = new String[dialogues.size()];
        dialogueUris = new String[dialogues.size()];
        for (int i = dialogues.size() - 1; i >= 0; i--) {
            dialogueIds[i] = dialogues.get(i).getId();
            dialogueTitles[i] = dialogues.get(i).getTitleAr() + "\n" + dialogues.get(i).getTitleSe();
            dialogueUris[i] = dialogues.get(i).getVideoUri();
        }

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

//    public void onResume() {
//        super.onResume();
//
//        //if this tab is the currently visible one,
//        // show a walkthrough
//        if (isAdded() && isVisible() && getUserVisibleHint()) {
//            //final Target viewTarget = new ViewTarget(listviewDialogue.getChildAt(0).getId(), getActivity());
//            getActivity().findViewById(listviewDialogue.getId()).post(new Runnable() {
//                @Override
//                public void run() {
//
//                    ShowcaseConfig config = new ShowcaseConfig();
//                    config.setDelay(500); // half second between each showcase view
//
//                    int height = 100;
//                    int width = listviewDialogue.getWidth();
//
//                    if (listviewDialogue.getChildAt(0) != null){
//                        //height = listviewDialogue.getChildAt(0).getHeight() * 3;
//                        height = 600;
//                        width = listviewDialogue.getWidth();
//                    }
//                    config.setShape(new RectangleShape(width, height));
//
//                    MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity(), SHOWCASE_ID);
//
//                    sequence.setOnItemShownListener(new MaterialShowcaseSequence.OnSequenceItemShownListener() {
//                        @Override
//                        public void onShow(MaterialShowcaseView itemView, int position) {
//                            Toast.makeText(itemView.getContext(), "Item #" + position, Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                    sequence.setConfig(config);
//
//                    // show a walkthrough for the first element displayed
//                    MaterialShowcaseView.Builder builder = new MaterialShowcaseView.Builder(getActivity())
//                            .setTarget(listviewDialogue.getChildAt(0) != null ? listviewDialogue.getChildAt(0):
//                                    listviewDialogue)
//                            .singleUse(SHOWCASE_ID)
//                            .setDismissText("OK")
//                            .setTitleText("Daily Dialogues")
//                            .setContentText("Click one item from this list to practice daily dialogues")
//                            //.setTargetTouchable(true)
//                            .setDismissOnTargetTouch(true);
//
//                    //se the highlight as rectanle
//                    builder.setShape(new RectangleShape(width, 20) );
//
//                    // add a walkthrough step
//                    sequence.addSequenceItem(
//                                builder.build()
//                    );
//
//                    // reset the settings so that the user can see the tutorial again
//                    // TODO this MUST be configured as an appliaiton setting
//                    MaterialShowcaseView.resetSingleUse(getContext(), SHOWCASE_ID);
//
//                    //start the guide
//                    sequence.start();
//                }
//            });
//
//        }
//    }

    /***
     * showTutorial: Gives the user a nice walkthrough in the application
     */
    private void showIWalkThrough() {

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity(), SHOWCASE_ID);

        sequence.setOnItemShownListener(new MaterialShowcaseSequence.OnSequenceItemShownListener() {
            @Override
            public void onShow(MaterialShowcaseView itemView, int position) {
                Toast.makeText(itemView.getContext(), "Item #" + position, Toast.LENGTH_SHORT).show();
            }
        });

        sequence.setConfig(config);

        // add a walk through tutorial for the tabs (emergency and everyday languages)
        if (recyclerDialogue != null) {
            sequence.addSequenceItem(recyclerDialogue,
                    "Tab MAN",
                    "TUTORIAL FOR THIS TAB",
                    "GOT IT");
        }

        // reset the settings so that the user can see the tutorial again
        // TODO this MUST be configured as an appliaiton setting
        MaterialShowcaseView.resetSingleUse(getContext(), SHOWCASE_ID);

        //start the guide
        sequence.start();
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(getContext(), DailyDialogueActivity.class);
        intent.putExtra("Index", clickedItemIndex);
        intent.putExtra("DialogueIds", dialogueIds);
        intent.putExtra("DialogueTitles", dialogueTitles);
        intent.putExtra("videoUris", dialogueUris);

        startActivity(intent);
        UsageLogger.appendActivity(getContext(), "selected dialogue id, " + dialogues.get(clickedItemIndex));
    }

}
