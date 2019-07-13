package com.example.doreopartners.scatterplot;

//RECYCLER VIEW ADAPTER FOR DISPLAYING IKNUMBERS BASED ON STAFF LOGIN DETIALS
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class selectmemberrecycler extends RecyclerView.Adapter<selectmemberrecycler.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String>mImageNames=new ArrayList<>();
    private ArrayList<String> firstname1;
    private ArrayList <String>lastname1;
    private ArrayList <String>number1;
    private ArrayList <String>phonenumber1;
    private List<modelselectmember> member_list;

    private Context mContext;
    SharedPreferences member;
    SharedPreferences prefs;
    SharedPreferences.Editor prefsEdit;
    String first_name;
    String last_name;
    String counter;
    String member_id;
    String unique_id;

    modelselectmember model;

    public selectmemberrecycler(ArrayList<modelselectmember> memberList,Context context) {
        this.member_list=memberList;


        mContext=context;


    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.selectmemberrecycler,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;


    }
    @Override
    public void onBindViewHolder( ViewHolder holder, final int position) {

        model = member_list.get(position);
       // DatabaseHelper myDb;
        //myDb = new DatabaseHelper(mContext);
        OnlineDatabase db;
        db=new OnlineDatabase(mContext);


        Log.d(TAG, "onBindViewHolder: called.");
        holder.imageName.setText(model.getMember_id());
        holder.firstname.setText(model.getFirst_name());
        //holder.lastname.setText(model.getLast_name());
        //holder.number.setText(number1.get(position));
        String first_name=model.getFirst_name();
        String last_name=model.getLast_name();
        holder.firstname.setText(first_name+" "+last_name);
        holder.phonenumber.setText(model.getPhone_number());
        int counter=0;
        //counter=db.memberfields((model.getMember_id()));
        //holder.number.setText(String.valueOf(counter));



    }

    @Override
    public int getItemCount() {
        return member_list.size();
        // return mImageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView imageName;
        TextView firstname;
        TextView lastname;
        TextView membername;
        TextView numbertext;
        TextView number;
        TextView phonenumbertext;
        TextView phonenumber;


        RelativeLayout parentLayout;
        public ViewHolder (View itemView)
        {
            super(itemView);
            imageName=itemView.findViewById(R.id.member_id);
            firstname=itemView.findViewById(R.id.firstname);

            membername=itemView.findViewById(R.id.membername);
            //numbertext= itemView.findViewById(R.id.numbertext);
            //number=itemView.findViewById(R.id.number);
            phonenumbertext=itemView.findViewById(R.id.phonenumbertext);
            phonenumber= itemView.findViewById(R.id.phonenumber);



            parentLayout=itemView.findViewById(R.id.parent_layout);
            parentLayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
//                Log.d(TAG,"onClick: clicked on: "+ mImageNames.get(position));
//                    String iknumber=model.getik_number();
                    // String firstname=model.getFirst_name();
                    //String lastname=model.getLast_name();

                    final TextView memberid = view.findViewById(R.id.member_id);

                    member_id = memberid.getText().toString();
                    final TextView firstname = view.findViewById(R.id.firstname);

                    final String name = firstname.getText().toString();
                    String []name2=name.split(" ");
                    first_name=name2[0];
                    last_name=name2[1];


                    //final TextView lastname = view.findViewById(R.id.lastname);

                    //final String last_name = lastname.getText().toString();
                    //final TextView number = view.findViewById(R.id.number);

                    //counter=number.getText().toString();


                    //Toast.makeText(mContext,mImageNames.get(position), Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(mContext,instruction.class);
                    intent.putExtra("member_id",member_id);

                    intent.putExtra("first_name",first_name);
                    intent.putExtra("last_name",last_name);
                   // intent.putExtra("counter",counter);

                    Toast.makeText(mContext,first_name, Toast.LENGTH_SHORT).show();


                    //prefsEdit.putString("MIN_LOC_UPDATE_DISTANCE",intent.getStringExtra("min_dist"));
                    intent.putExtra("MIN_WALKING_SPEED", intent.getStringExtra("max_walk_speed"));
                    intent.putExtra("MIN_BIKE_SPEED", intent.getStringExtra("max_bike_speed"));
                    Bundle b = intent.getExtras();


                    mContext.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

}

