package com.example.doreopartners.scatterplot;

public class modelclassikexamined {

//this is the model class for the TGs that have been examined.

    private String first_name;
    private String last_name;
    private String ik_number;
    private String number;



    public modelclassikexamined(String ik_number ) {

        this.ik_number = ik_number;
        //this.member_id = member_id;

    }



    public String getik_number() {
        return ik_number;
    }

//    public String getMember_id() {
//        return member_id;
//    }
}