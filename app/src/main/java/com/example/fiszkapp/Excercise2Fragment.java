package com.example.fiszkapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Excercise2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Excercise2Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String word;
    private String rightAnswer;
    private int cardId;
    TextView textWord;
    EditText editTextAnswer;
    Button buttonConfirm;
    int priority;
    DBHelper helper;

    public Excercise2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param word Parameter 1.
     * @param rightAnswer Parameter 2.
     * @return A new instance of fragment Excercise2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Excercise2Fragment newInstance(String word, String rightAnswer, int cardId)
    {
        Excercise2Fragment fragment = new Excercise2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, word);
        args.putString(ARG_PARAM2, rightAnswer);
        args.putInt(ARG_PARAM3, cardId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            word = getArguments().getString(ARG_PARAM1);
            rightAnswer = getArguments().getString(ARG_PARAM2);
            cardId = getArguments().getInt(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_excercise2, container, false);
        textWord=view.findViewById(R.id.textGivenWord);
        textWord.setText(word);
        editTextAnswer=view.findViewById(R.id.editTextAnswer);
        buttonConfirm=view.findViewById(R.id.buttonConfirmAnswer);
        helper=new DBHelper(getActivity());
        Cursor c=helper.getElementFromAttribute("Flashcard", "ID", String.valueOf(cardId), false);
        c.moveToNext();
        priority=c.getInt(3);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextAnswer.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(), "Najpierw wprowad?? odpowied??", Toast.LENGTH_SHORT).show();
                }
                else if(editTextAnswer.getText().toString().toLowerCase().equals(rightAnswer.toLowerCase()))
                {
                    editTextAnswer.setTextColor(Color.GREEN);
                    ((LessonActivity)getActivity()).answerList.add(true);
                    priority=(priority+1)*2;
                }
                else
                {
                    editTextAnswer.setTextColor(Color.RED);
                    ((LessonActivity)getActivity()).answerList.add(false);
                    priority/=2;
                }
                ContentValues values = new ContentValues();
                values.put("PRIORITY",priority);
                helper.updateData(String.valueOf(cardId), "Flashcard", values);
            }
        });
        return view;
    }
}