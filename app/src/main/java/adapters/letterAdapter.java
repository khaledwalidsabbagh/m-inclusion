package adapters;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.minclusion.iteration1.R;

import java.util.List;

import db.Vowel;
import db.Word;

public class letterAdapter extends ArrayAdapter<Word> {
    private Context context;
    private List<Word> words;
    private Button bSe;
    private MediaPlayer mPlayer;
    private Integer letterId;
    private String letterTitle, vowelType, letterType;
    private int p;
    private List<Vowel> vowels;
    private Vowel vowel;
    private Spannable newWord;


    public letterAdapter(Context context, List<Word> words, int letterId, String vowelTitle, String vowelType, String letterType) {
        super(context, R.layout.letter_thumbnail, words);
        this.context = context;
        this.words = words;
        this.letterId = letterId;
        this.letterTitle = vowelTitle;
        this.vowelType = vowelType;
        this.letterType = letterType;
        vowel = new Vowel();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.letter_thumbnail, parent, false);
        bSe = (Button) view.findViewById(R.id.bVowel);
        p = position;

        if (letterType.equals("vowel")) {
            newWord = highlightVowels(words.get(position).getSe());
        } else {
            newWord = highlightConsonants(words.get(position).getSe());
        }

        bSe.setText(newWord);
        bSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer = MediaPlayer.create(view.getContext(), view.getResources().
                        getIdentifier(words.get(position).getVideoPath(), "raw", view.getContext().getPackageName()));
                mPlayer.start();
                //wait until media player is finished then release from memory
                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        mPlayer.release();
                    }
                });
            }
        });

        return view;
    }

    public Spannable highlightConsonants(String s) {
        s = s + "\n" + words.get(p).getAr();
        Spannable wordtoSpan = new SpannableString(s);
        String[] consonants = null;

        //splitting consonants in each tab into two
        consonants = letterTitle.split("\\W");

        if (consonants.length > 1) // two or more compund consonants
        {
            int index1 = s.indexOf(consonants[0]);
            int index2 = s.indexOf(consonants[1]);
            int index3 = s.indexOf("sk");

            if (index1 != -1)
                wordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), index1, index1 + consonants[0].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            if (index2 != -1)
                wordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), index2, index2 + consonants[1].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            if (consonants[0].equals("sj") && index3 != -1)
                wordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), index3, index3 + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else
            wordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), letterTitle.indexOf(0), letterTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return wordtoSpan;
    }

    public Spannable highlightVowels(String s) {
        s = s + "\n" + words.get(p).getAr();
        Spannable WordtoSpan = new SpannableString(s);
        int vowelCounter = 0;
        for (int i = 0; i < words.get(p).getSe().length(); i++) { //s.length()
            if (s.charAt(i) == 'a'
                    || WordtoSpan.charAt(i) == 'i'
                    || WordtoSpan.charAt(i) == 'e'
                    || WordtoSpan.charAt(i) == 'u'
                    || WordtoSpan.charAt(i) == 'o'
                    || WordtoSpan.charAt(i) == 'y'
                    || WordtoSpan.charAt(i) == 'å'
                    || WordtoSpan.charAt(i) == 'ä'
                    || WordtoSpan.charAt(i) == 'ö'
                    ) {
                vowelCounter++;

                if (s.charAt(i) == letterTitle.charAt(0)) {
                    boolean isFound = vowel.getPosition(Integer.toString(words.get(p).getId()), Integer.toString(vowelCounter), vowelType);
                    if (isFound) {
                        if (vowelType.equals("short"))
                            WordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        else
                            WordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }

            }
        }
        return WordtoSpan;
    }

}
