package com.example.doreopartners.scatterplot;

//RECYCLER VIEW ADAPTER FOR DISPLAYING IKNUMBERS BASED ON STAFF LOGIN DETIALS
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class fieldrecycler extends RecyclerView.Adapter<fieldrecycler.ViewHolder>  {
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
    modelselectfield model;
    //modelclass3 model3;
    private List<modelselectfield> member_list;
    private List<modelselectfield> examplelistfull;
    // itemsCopy.addAll(items);

    public fieldrecycler(ArrayList<modelselectfield> memberList,Context context) {

        this.member_list=memberList;
        examplelistfull= new ArrayList<>(memberList);

        mContext=context;
        //setHasStableIds(true);

    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.fieldrecycler,parent,false);
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
//
            desciption=itemView.findViewById(R.id.description);
            field_size=itemView.findViewById(R.id.field_size);


            parentLayout=itemView.findViewById(R.id.parent_layout);



            parentLayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    final TextView ikTextView = view.findViewById(R.id.field_id);

                    final String field_id = ikTextView.getText().toString();
                    final TextView description1=view.findViewById(R.id.description);
                    final TextView field_size1=view.findViewById(R.id.field_size);
                    final String description=description1.getText().toString();
                    final String field_size=field_size1.getText().toString();



                                    Intent intent= new Intent(mContext,getlocation.class);
                                    //Intent intent= new Intent(mContext,pictureMLactivity.class);

                                    intent.putExtra("field_id",field_id);
                                    intent.putExtra("description",description);
                                    intent.putExtra("field_size",field_size);

                                    mContext.startActivity(intent);


                                    //startActivity(intent2);




                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }


}

