package com.example.fiszkapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Excercise1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Excercise1Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    // TODO: Rename and change types of parameters
    private String word;
    private String rightAnswer;
    private boolean isReversed;
    private String collectionName;
    private String wrongAnswer;
    TextView textWord;
    Button buttonOption1;
    Button buttonOption2;
    DBHelper helper;

    public Excercise1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Excercise1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Excercise1Fragment newInstance(String word, String rightAnswer, boolean isReversed, String collectionName) {
        Excercise1Fragment fragment = new Excercise1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, word);
        args.putString(ARG_PARAM2, rightAnswer);
        args.putBoolean(ARG_PARAM3, isReversed);
        args.putString(ARG_PARAM4, collectionName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            word = getArguments().getString(ARG_PARAM1);
            rightAnswer = getArguments().getString(ARG_PARAM2);
            isReversed = getArguments().getBoolean(ARG_PARAM3);
            collectionName=getArguments().getString(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        helper = new DBHelper(getActivity());
        View view =inflater.inflate(R.layout.fragment_excercise1, container, false);
        textWord=view.findViewById(R.id.textWordQuestion);
        buttonOption1=view.findViewById(R.id.buttonOption1);
        buttonOption2=view.findViewById(R.id.buttonOption2);
        textWord.setText(word);
        wrongAnswer=helper.getWordsInRandomOrder(collectionName, isReversed, rightAnswer).get(0);
        setOptionWords();
        buttonOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAnswer(buttonOption1);
            }
        });
        buttonOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAnswer(buttonOption2);
            }
        });
        return view;
    }

    public void setOptionWords()
    {
        String word1="";
        String word2="";
        if(flipACoin())
        {
            word1 = rightAnswer;
            word2 = wrongAnswer;
        }
        else
        {
            word2=rightAnswer;
            word1=wrongAnswer;
        }
        buttonOption1.setText(word1);
        buttonOption2.setText(word2);
    }
    public void handleAnswer(Button chosenOption)
    {
        if(chosenOption.getText().equals(rightAnswer))
        {
            chosenOption.setBackgroundColor(Color.GREEN);
        }
        else
        {
            chosenOption.setBackgroundColor(Color.RED);
        }
    }
    private boolean flipACoin()
    {
        return (int)Math.round(Math.random()) == 0;
    }
}