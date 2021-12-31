package com.example.fiszkapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class BadgeAdapter extends RecyclerView.Adapter<BadgeAdapter.BadgeViewHolder>
{
    private Context context;
    private Cursor cursor;
    public BadgeAdapter(Context context, Cursor cursor)
    {
        this.context=context;
        this.cursor=cursor;
    }
    public class BadgeViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener
    {
        TextView badgeName;
        TextView badgeDescription;
        CardView cardView;
        ImageView icon;
        public BadgeViewHolder(final  View itemView)
        {
            super(itemView);
            badgeName=itemView.findViewById(R.id.badgeName);
            badgeDescription=itemView.findViewById(R.id.badgeDescriptiion);
            icon=itemView.findViewById(R.id.badgeIcon);
            cardView=itemView.findViewById(R.id.cardBadgeItem);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }
    }
    @Override
    public BadgeViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.badge_item,parent,false);
        return new BadgeViewHolder(view);
    }
    @Override
    public void onBindViewHolder(BadgeViewHolder holder, int position)
    {
        if(cursor.isClosed() || !cursor.moveToPosition(position))
        {
            return;
        }
        String imagePath=cursor.getString(4);
        String name=cursor.getString(1);
        String description=cursor.getString(2);
        int id = cursor.getInt(0);
        holder.badgeName.setText(name);
        holder.badgeDescription.setText(description);
        holder.icon.setImageDrawable(Drawable.createFromPath(imagePath));
        holder.itemView.setTag(id);
    }
    @Override
    public int getItemCount()
    {
        return cursor.getCount();
    }
    /*public void swapCursor(Cursor cursor)
    {
        if(this.cursor!=null)
        {
            this.cursor.close();
        }
        this.cursor=cursor;
    }*/
}
