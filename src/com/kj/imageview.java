package com.kj;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lianlin.R;



public class imageview extends LinearLayout{ 
     

 
     ImageView img;
   
     
    public imageview(Context context) { 
        super(context); 
       
    } 
    public imageview(final Context context, AttributeSet attrs) { 
        super(context, attrs); 
        
        // TODO Auto-generated constructor stub 
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
        inflater.inflate(R.layout.imageview, this); 

    
      
        img=(ImageView)findViewById(R.id.imageview_img);  
   	    img.setScaleType(ScaleType.FIT_XY);
    }
        
 

    public void setIMG(Bitmap bm){
      	img.setImageBitmap(bm);
      }
  

} 

