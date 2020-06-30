package com.example.proto_4;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DetailRecyclerViewAdapter extends RecyclerView.Adapter<DetailRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> name = new ArrayList<>();
//    private ArrayList<Integer> yesno = new ArrayList<>();

    private Context mContext;

    public DetailRecyclerViewAdapter(Context mContext, ArrayList<String> name) {
        this.name = name;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

//         이미지 불러오는 것
//        Glide.with(mContext).asBitmap().load(mImaged.get(position)).into(holder.image);

        holder.tv_name.setText(name.get(position));


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on : " + name.get(position));

                Toast.makeText(mContext, name.get(position), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, DetailActivicy.class);
                intent.putExtra("name", name.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageButton star;
        TextView tv_name;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
