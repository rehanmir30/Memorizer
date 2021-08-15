package com.example.memorizer;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.memorizer.model.SavedTasks;

import java.util.ArrayList;


public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private ArrayList<SavedTasks> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;

        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            mTextView1 = itemView.findViewById(R.id.tasktitle);
            mTextView2 = itemView.findViewById(R.id.time1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public ExampleAdapter(ArrayList<SavedTasks> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        SavedTasks currentItem = mExampleList.get(position);

        String color=currentItem.getColor();
        if (color.equals("Low")) {
            //holder.mTextView1.setTextColor(0xF7C600);
            holder.mTextView1.setTextColor(Color.parseColor("#F7C600"));
        }
        if (color.equals("Medium")) {
//            holder.mTextView1.setTextColor(0x000087);
            holder.mTextView1.setTextColor(Color.parseColor("#000087"));
        }
        if (color.equals("High")) {
        //    holder.mTextView1.setTextColor(0xF70000);
            holder.mTextView1.setTextColor(Color.parseColor("#FF6347"));
        }

        holder.mTextView1.setText(currentItem.getTitle());
        holder.mTextView2.setText(currentItem.getTime());

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}