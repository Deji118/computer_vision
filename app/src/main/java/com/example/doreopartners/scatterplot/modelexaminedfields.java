package com.example.doreopartners.scatterplot;

public class modelexaminedfields {
//this is the model class for the examined fields


    private String field_id;
    private String description;
    private String field_size;
    private String timestamp;


    public modelexaminedfields( String unique_id, String description,String field_size ,String timestamp) {

        this.field_id = unique_id;
        this.description = description;
        this.field_size=field_size;
        this.timestamp=timestamp;

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
    public String getdate() {
        return timestamp;
    }

}