package com.ekirei.tennistest2.Fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ekirei.tennistest2.R;

import java.util.HashMap;

/**
 * Created by Luigi on 18/08/2015.
 */
public class FragmentPlayersBio extends Fragment {

    ImageView player_image, flag_little;
    TextView name_text, surname_text, ranking_vertical_text, ranking_value_text, nation_sigle_text;
    View separator_veritical;


    public FragmentPlayersBio() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_player_bio, container, false);

        player_image = (ImageView) rootView.findViewById(R.id.image_playerbio);
        name_text = (TextView) rootView.findViewById(R.id.text_name);
        name_text.setAlpha(0);
        surname_text = (TextView) rootView.findViewById(R.id.text_surname);
        surname_text.setAlpha(0);
        ranking_vertical_text = (TextView) rootView.findViewById(R.id.text_ranking_string);
        ranking_vertical_text.setAlpha(0);
        ranking_value_text = (TextView) rootView.findViewById(R.id.text_ranking_number);
        ranking_value_text.setAlpha(0);
        separator_veritical = (View) rootView.findViewById(R.id.separator_vertical);
        separator_veritical.setAlpha(0);
        flag_little = (ImageView) rootView.findViewById(R.id.image_flag);
        nation_sigle_text = (TextView) rootView.findViewById(R.id.text_nation_sigle);
        nation_sigle_text.setAlpha(0);

        ListView mBioListView = (ListView) rootView.findViewById(R.id.listview_bio_data);

        HashMap<String, String> murrayMap = new HashMap<String,String>();
        murrayMap.put("Age", "28");
        murrayMap.put("Turned Pro", "2005");
        murrayMap.put("Weight", "84 Kg");
        murrayMap.put("Height", "191 cm");
        murrayMap.put("Career High", "2");
        murrayMap.put("W/L", "529 - 159");
        murrayMap.put("Titles", "34");
        murrayMap.put("Prize Money", "$ 38,938,535");

        mBioListView.setAdapter(new BioListAdapter(getActivity(), murrayMap));
        mBioListView.setClickable(false);

        /*AnimationSet as = new AnimationSet(true);
        as.setFillAfter(true);
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(600);*/

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ObjectAnimator alphaAnimationPlayerImage = ObjectAnimator.ofFloat(player_image, View.ALPHA, 0, 1).setDuration(800);
        AnimatorSet nameAnimation = new AnimatorSet();
        nameAnimation.playTogether(
                ObjectAnimator.ofFloat(name_text, View.ALPHA, 0, 1).setDuration(200),
                ObjectAnimator.ofFloat(name_text, View.TRANSLATION_X, -300, 0).setDuration(400)
        );
        AnimatorSet surnameAnimation = new AnimatorSet();
        surnameAnimation.playTogether(
                ObjectAnimator.ofFloat(surname_text, View.ALPHA, 0, 1).setDuration(200),
                ObjectAnimator.ofFloat(surname_text, View.TRANSLATION_X, -300, 0).setDuration(400)
        );
        AnimatorSet rankingValueAnimation = new AnimatorSet();
        rankingValueAnimation.playTogether(
                ObjectAnimator.ofFloat(ranking_value_text, View.ALPHA, 0, 1).setDuration(200),
                ObjectAnimator.ofFloat(ranking_value_text, View.TRANSLATION_X, -500, 0).setDuration(400)
        );
        AnimatorSet rankingStringAnimation = new AnimatorSet();
        rankingStringAnimation.playTogether(
                ObjectAnimator.ofFloat(ranking_vertical_text, View.ALPHA, 0, 1).setDuration(200),
                ObjectAnimator.ofFloat(ranking_vertical_text, View.TRANSLATION_X, -500, 0).setDuration(400)
        );
        ObjectAnimator alphaAnimationSeparator = ObjectAnimator.ofFloat(separator_veritical, View.ALPHA, 0, 1).setDuration(300);
        ObjectAnimator alphaAnimationFlagImage = ObjectAnimator.ofFloat(flag_little, View.ALPHA, 0, 1).setDuration(400);
        AnimatorSet nationSigleTextAnimation = new AnimatorSet();
        nationSigleTextAnimation.playTogether(
                ObjectAnimator.ofFloat(nation_sigle_text, View.ALPHA, 0, 1).setDuration(200),
                ObjectAnimator.ofFloat(nation_sigle_text, View.TRANSLATION_X, -600, 0).setDuration(400)
        );

//        ObjectAnimator translateAnimationName = ObjectAnimator.ofFloat(name_text, View.TRANSLATION_X, -300, 0).setDuration(400);
//        ObjectAnimator translateAnimationSurname = ObjectAnimator.ofFloat(surname_text, View.TRANSLATION_X, -300, 0).setDuration(400);
        AnimatorSet as1 = new AnimatorSet();
        as1.play(nameAnimation).after(alphaAnimationPlayerImage).before(surnameAnimation);
        as1.start();
        AnimatorSet as2 = new AnimatorSet();
        as2.setStartDelay(1200);
        as2.play(rankingStringAnimation).after(rankingValueAnimation);
        as2.start();
        AnimatorSet as3 = new AnimatorSet();
        as3.setStartDelay(2000);
        as3.play(alphaAnimationFlagImage).after(alphaAnimationSeparator).before(nationSigleTextAnimation);
        as3.start();

    }

    public class BioListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private HashMap<String, String> mMap;

        public BioListAdapter(Context context, HashMap<String, String> biolist) {
            mInflater = LayoutInflater.from(context);
            mMap = biolist;
        }

        public int getCount() {
            return mMap.size();
        }

        public String getItem(int position) {
            return mMap.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewGroup vg;

            if (convertView != null) {
                vg = (ViewGroup) convertView;
            } else {
                vg = (ViewGroup) mInflater.inflate(R.layout.item_bio_data_listview, null);

            }

            TextView value = (TextView) vg.findViewById(R.id.text_value);
            TextView descr = (TextView) vg.findViewById(R.id.text_description);

            switch (position) {
                case 0:
                    value.setText(mMap.get("Age"));
                    descr.setText("Age");
                    break;
                case 1:
                    value.setText(mMap.get("Turned Pro"));
                    descr.setText("Turned Pro");
                    break;
                case 2:
                    value.setText(mMap.get("Weight"));
                    descr.setText("Weight");
                    break;
                case 3:
                    value.setText(mMap.get("Height"));
                    descr.setText("Height");
                    break;
                case 4:
                    value.setText(mMap.get("Career High"));
                    descr.setText("Career High");
                    break;
                case 5:
                    value.setText(mMap.get("W/L"));
                    descr.setText("W/L");
                    break;
                case 6:
                    value.setText(mMap.get("Titles"));
                    descr.setText("Titles");
                    break;
                case 7:
                    value.setText(mMap.get("Prize Money"));
                    descr.setText("Prize Money");
                    break;
                default: break;
            }


            return vg;
        }
    }
}
