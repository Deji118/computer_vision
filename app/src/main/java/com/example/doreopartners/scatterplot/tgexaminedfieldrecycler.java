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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class tgexaminedfieldrecycler extends RecyclerView.Adapter<tgexaminedfieldrecycler.ViewHolder> {
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
    modelclassikexamined model;
    //modelclass3 model3;
    private List<modelclassikexamined> member_list;


    public tgexaminedfieldrecycler(ArrayList<modelclassikexamined> memberList, Context context) {

        this.member_list=memberList;
        //this.number_list=number_list;
//        firstname1=firstname2;
//        lastname1=lastname2;
//        number1=number2;
//        phonenumber1=phonenumber2;

        mContext=context;
        //setHasStableIds(true);

    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.tgexaminedfieldrecycler,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;



    }

    @Override
    public void onBindViewHolder( ViewHolder holder, final int position) {

        model = member_list.get(position);
//        model3=number_list.get(position);
        //for (int i = 0; i < member_list.size(); i++)
        //if (member_list.get(i))

        Log.d(TAG, "onBindViewHolder: called.");
        holder.imageName.setText(model.getik_number());
//        String first_name=model.getFirst_name();
//        String last_name=model.getLast_name();
//        holder.firstname.setText(first_name+" "+last_name);



        //holder.lastname.setText(model.getLast_name());
        //holder.number.setText(model3.getnumber());
        // holder.phonenumber.setText(model.getPhone_number());
//        setHasStableIds(true);

    }


    @Override
    public int getItemCount() {
        return member_list.size();
    }
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView imageName;
        TextView firstname;
        //TextView lastname;
        TextView leadername;
        TextView numbertext;
        TextView number;


        RelativeLayout parentLayout;
        public ViewHolder (View itemView)
        {
            super(itemView);
            imageName=itemView.findViewById(R.id.iknumber);
//            firstname=itemView.findViewById(R.id.firstname);
//            //lastname=itemView.findViewById(R.id.lastname);
//            leadername=itemView.findViewById(R.id.leadername);
            // numbertext= itemView.findViewById(R.id.numbertext);
            // number=itemView.findViewById(R.id.number);
            parentLayout=itemView.findViewById(R.id.parent_layout);


            parentLayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
//                Log.d(TAG,"onClick: clicked on: "+ mImageNames.get(position));
//                    String iknumber=model.getik_number();
                    // String firstname=model.getFirst_name();
                    //String lastname=model.getLast_name();

                    final TextView ikTextView = view.findViewById(R.id.iknumber);

                    final String iknumber = ikTextView.getText().toString();


                    //Toast.makeText(mContext,mImageNames.get(position), Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(mContext,examinedmembers.class);
                    intent.putExtra("ik_number1",iknumber);
                    Toast.makeText(mContext,iknumber, Toast.LENGTH_SHORT).show();
                    DatabaseHelper db = new DatabaseHelper(mContext);

                    //intent.putExtra("ik_number",mImageNames.get(position));
                    //intent.putExtra("first_name",firstname);
                    //intent.putExtra("last_name",lastname);

                    //OnlineDBHelper db=new OnlineDBHelper(mContext);

                    //intent.putExtra("crop_type",db.getcroptype("imagenames"));
                    //intent.putExtra("ik_number",db.getmemb("imagenames"));
                    //mEditor.putString("ik_number",db.get("staff_id"));
                    // intent.putExtra("first_name",db.getfirstname("imagenames"));
                    //intent.putExtra("last_name",db.getlastname("imagenames"));
                    //String ik_number=getIntent().getStringExtra("imagenames");

                    //prefsEdit.putString("MIN_LOC_UPDATE_DISTANCE",intent.getStringExtra("min_dist"));
                    //intent.putExtra("MIN_WALKING_SPEED", intent.getStringExtra("max_walk_speed"));
                    //intent.putExtra("MIN_BIKE_SPEED", intent.getStringExtra("max_bike_speed"));
                    //Bundle b = intent.getExtras();


                    mContext.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

}

