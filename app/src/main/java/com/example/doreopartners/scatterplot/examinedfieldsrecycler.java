package com.example.doreopartners.scatterplot;

//RECYCLER VIEW ADAPTER FOR DISPLAYING IKNUMBERS BASED ON STAFF LOGIN DETIALS
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class examinedfieldsrecycler extends RecyclerView.Adapter<examinedfieldsrecycler.ViewHolder>  {
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String>mImageNames=new ArrayList<>();
    private String firstname1;
    private String lastname1;
    private String number1;
    private Context mContext;
    SharedPreferences member;
    SharedPreferences prefs;
    SharedPreferences.Editor memEdit;
    //private List<modelclass3>number_list;
    modelexaminedfields model;
    //modelclass3 model3;
    private List<modelexaminedfields> member_list;
    private List<modelexaminedfields> examplelistfull;
    // itemsCopy.addAll(items);

    public examinedfieldsrecycler(ArrayList<modelexaminedfields> memberList,Context context) {

        this.member_list=memberList;
        examplelistfull= new ArrayList<>(memberList);

        mContext=context;


    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.examinedfieldreycler,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;



    }

    @Override
    public void onBindViewHolder( ViewHolder holder, final int position) {

        model = member_list.get(position);


        Log.d(TAG, "onBindViewHolder: called.");
        holder.imageName.setText(model.getField_id());
        holder.desciption.setText(model.getdescription());
        holder.field_size.setText(model.getField_size());
        holder.date.setText(model.getdate());

    }


    @Override
    public int getItemCount() {
        return member_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView imageName;
        TextView desciption;
        //TextView lastname;
        TextView field_id;
        TextView field_size;
        TextView date;



        RelativeLayout parentLayout;
        public ViewHolder (View itemView)
        {
            super(itemView);
            imageName=itemView.findViewById(R.id.field_id);

            desciption=itemView.findViewById(R.id.description);
            field_size=itemView.findViewById(R.id.field_size);
            date=itemView.findViewById(R.id.date);

            parentLayout=itemView.findViewById(R.id.parent_layout);

        }

        @Override
        public void onClick(View v) {

        }
    }


}

