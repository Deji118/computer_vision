package com.example.doreopartners.scatterplot;

public class modelselectfield {

//this is the model class that host the info of member fields to be examined

    private String field_id;
    private String description;
    private String field_size;



    public modelselectfield( String unique_id, String description,String field_size ) {

        this.field_id = unique_id;
        this.description = description;
        this.field_size=field_size;


    }


    public String getField_id() {
        return field_id;
    }

    public String getdescription() {
        return description;
    }
    public String getField_size() {
        return field_size;
    }


}