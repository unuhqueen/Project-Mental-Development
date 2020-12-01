package com.example.prefinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoAdapter extends  RecyclerView.Adapter<TodoAdapter.ViewHolder>  {

    private ArrayList<Todo> mData = null;

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView textView_todo_item;
        protected ImageButton deleteBt;
        protected ImageButton checkBt;


        private View.OnClickListener mListener;

        public ViewHolder(View itemView) {
            super(itemView);

            this.textView_todo_item = itemView.findViewById(R.id.textview_todo_item);
            this.deleteBt = itemView.findViewById(R.id.button_todo_item);

            deleteBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        mData.remove(position);
                        notifyDataSetChanged();
                        textView_todo_item.setPaintFlags(0);
                    }
                }
            });

        }
    }

    TodoAdapter(ArrayList<Todo> list) {mData = list; }

    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
        ViewHolder vh = new ViewHolder(view);

        return vh;

    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView_todo_item.setText(mData.get(position).getTodoName());
    }

    public int getItemCount() {return mData.size(); }
}
