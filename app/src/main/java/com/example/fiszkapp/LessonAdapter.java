package com.example.fiszkapp;

import android.content.Context;
import android.database.Cursor;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder>
{
    private Context context;
    private Cursor cursor;
    public LessonAdapter(Context context, Cursor cursor)
    {
        this.context=context;
        this.cursor=cursor;
    }
    @Override
    public LessonViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.lesson_item, parent, false);
        return new LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position)
    {
        if(cursor.isClosed() || !cursor.moveToPosition(position))
        {
            return;
        }
        holder.textStarted.setText(cursor.getString(1));
        holder.textEnded.setText(cursor.getString(2));
        holder.textTotalQuest.setText(cursor.getInt(3));
        holder.textCorrect.setText(cursor.getInt(4));
        holder.textGainedExp.setText(cursor.getInt(5));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class LessonViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener
    {
        TextView textStarted;
        TextView textEnded;
        TextView textCorrect;
        TextView textTotalQuest;
        TextView textGainedExp;
        CardView cardView;

        public LessonViewHolder(final View itemView)
        {
            super(itemView);
            textStarted=itemView.findViewById(R.id.textStarted);
            textEnded=itemView.findViewById(R.id.textEnded);
            textCorrect=itemView.findViewById(R.id.textCorrect);
            textTotalQuest=itemView.findViewById(R.id.textTotalQuest);
            textGainedExp=itemView.findViewById(R.id.textGainedExp);
            cardView=itemView.findViewById(R.id.cardLessonItem);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }
    }
}
