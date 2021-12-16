package com.example.fiszkapp;

import android.content.Context;
import android.database.Cursor;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder>
{
    private Context context;
    private Cursor cursor;

    public CollectionAdapter(Context context, Cursor cursor)
    {
        this.context=context;
        this.cursor=cursor;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener
    {
        public TextView textViewCollection;
        CardView cardView;
        public ViewHolder(final View itemView)
        {
            super(itemView);
            textViewCollection=itemView.findViewById(R.id.textViewCollection);
            cardView=itemView.findViewById(R.id.cardCollectionItem);
            cardView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
        {
            menu.add(this.getAdapterPosition(), 121, 0, "Edytuj");
            menu.add(this.getAdapterPosition(), 122, 0, "Usuń");
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.collection_item,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        if(cursor.isClosed() || !cursor.moveToPosition(position))
        {
            return;
        }
        String name=cursor.getString(1);
        int id=cursor.getInt(0);
        holder.textViewCollection.setText(name);
        holder.itemView.setTag(id);
    }
    @Override
    public int getItemCount()
    {
        return cursor.getCount();
    }
    public void swapCursor(Cursor cursor)
    {
        //wymiana kursora na nowy, służy do odświeżania bazy danych
        if(this.cursor!=null)
        {
            this.cursor.close();
        }
        this.cursor=cursor;
        if(cursor!=null)
        {
            notifyDataSetChanged();
        }
    }
}
