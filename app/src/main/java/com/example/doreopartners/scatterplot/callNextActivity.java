package com.example.doreopartners.scatterplot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

public class callNextActivity {
    private  Context context;
    public  callNextActivity(@NonNull Context ctx){
        context = ctx;
    }

    public void startActivity(Activity newActivity){
        Intent intent = new Intent(context,newActivity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
