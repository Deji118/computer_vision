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

public class examinedmemberrecycler extends RecyclerView.Adapter<examinedmemberrecycler.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String>mImageNames=new ArrayList<>();
    private ArrayList<String> firstname1;
    private ArrayList <String>lastname1;
    private ArrayList <String>number1;
    private List<modelexaminedmember> member_list;

    private Context mContext;
    SharedPreferences member;
    SharedPreferences prefs;
    SharedPreferences.Editor prefsEdit;

    modelexaminedmember model3;

    public examinedmemberrecycler(ArrayList<modelexaminedmember> memberList, Context context) {
        this.member_list=memberList;
          mContext=context;


    }



    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.memberexaminedfieldrecycler,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;


    }
    @Override
    public void onBindViewHolder( ViewHolder holder, final int position) {
        model3 = member_list.get(position);
        Log.d(TAG, "onBindViewHolder: called.");
        holder.imageName.setText(model3.getMember_id());
        String first_name=model3.getFirst_name();
        String last_name=model3.getLast_name();
        holder.firstname.setText(first_name+" "+last_name);


    }

    @Override
    public int getItemCount() {
        return member_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView imageName;
        TextView firstname;
        TextView lastname;
        TextView membername;
        TextView numbertext;
        TextView number;
        TextView uniqueidtext;
        TextView uniqueid;


        RelativeLayout parentLayout;
        public ViewHolder (View itemView)
        {
            super(itemView);
            imageName=itemView.findViewById(R.id.member_id);
            firstname=itemView.findViewById(R.id.firstname);
            //lastname=itemView.findViewById(R.id.lastname);
            membername=itemView.findViewById(R.id.membername);
            parentLayout=itemView.findViewById(R.id.parent_layout);
            parentLayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    final TextView memberid = view.findViewById(R.id.member_id);

                    final String member_id = memberid.getText().toString();
                    final TextView firstname = view.findViewById(R.id.firstname);

                    final String first_name = firstname.getText().toString();

                    Intent intent= new Intent(mContext,examinedfields.class);
                    intent.putExtra("member_id2",member_id);

//
//
                    mContext.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

}

