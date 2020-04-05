package com.example.projectchisel.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectchisel.Model.Exercise;
import com.example.projectchisel.R;

import java.util.ArrayList;

public class AddExerciseAdapter extends RecyclerView.Adapter<AddExerciseAdapter.AddExerciseViewHolder> {
    private ArrayList<Exercise> mDataset;
    private OnItemClickListener mListener ;

    public interface OnItemClickListener{
        void onItemClick(int postion);
    }

    public void setOnItemClickListner(OnItemClickListener listener){
        mListener = listener;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class AddExerciseViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public ImageView imageView;

        public AddExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.exercise);
            imageView = itemView.findViewById(R.id.remove);

            imageView.setOnClickListener(v -> {
                if(mListener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        mListener.onItemClick(position);
                    }
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AddExerciseAdapter(ArrayList<Exercise> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AddExerciseAdapter.AddExerciseViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exerciseitem, parent, false);
        AddExerciseViewHolder vh = new AddExerciseViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(AddExerciseViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Exercise exercise = mDataset.get(position);
        String text = exercise.getExercise() + "-" + exercise.getSets() + "-" + exercise.getReps();
        holder.textView.setText(text);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}