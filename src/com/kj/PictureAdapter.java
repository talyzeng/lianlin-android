package com.kj;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.plistview.AsnycImageLoader_listview;
import com.lianlin.R;



//自定义适配器 

public class PictureAdapter extends BaseAdapter { 

private LayoutInflater inflater; 

private List<Picture> pictures; 



   public PictureAdapter(String[] titles, String[] images, Context context) { 

       super(); 

       pictures = new ArrayList<Picture>(); 

        inflater = LayoutInflater.from(context); 

        for (int i = 0; i < images.length; i++) { 

            Picture picture = new Picture(titles[i], images[i]); 

             pictures.add(picture); 

          } 
        loader = new AsnycImageLoader_listview(); 
      } 



  @Override 

 public int getCount() { 

   if (null != pictures) { 

      return pictures.size(); 

   } else { 

     return 0; 

     } 

     } 



  @Override 

   public Object getItem(int position) { 

     return pictures.get(position); 

     } 



   @Override 

    public long getItemId(int position) { 

      return position; 

    } 



   @Override 

   public View getView(int position, View convertView, ViewGroup parent) { 

   ViewHolder viewHolder; 

    if (convertView == null) { 

     convertView = inflater.inflate(R.layout.imageitem, null); 

     viewHolder = new ViewHolder(); 

     viewHolder.title = (TextView) convertView.findViewById(R.id.title); 

     viewHolder.image = (ImageView) convertView.findViewById(R.id.image); 

     convertView.setTag(viewHolder); 

     } else { 

     viewHolder = (ViewHolder) convertView.getTag(); 

     } 

    viewHolder.title.setText(pictures.get(position).getTitle()); 
	
    /* loadImage(pictures.get(position).getImageId(),viewHolder);*/
   
    FileInputStream f;
    Bitmap photo=null;
    try {
	   f = new FileInputStream(pictures.get(position).getImageId());


       BitmapFactory.Options options = new BitmapFactory.Options(); 
       options.inSampleSize = 8;//图片的长宽都是原来的1/8 
       BufferedInputStream bis = new BufferedInputStream(f); 
       photo = BitmapFactory.decodeStream(bis, null, options); 
//     setListPic(photo);

      } catch (FileNotFoundException e) {
	// TODO Auto-generated catch block
    	e.printStackTrace();
       } 
     viewHolder.image.setImageBitmap(photo); 

    return convertView; 

 } 


private static AsnycImageLoader_listview loader = null; 
public void loadImage(String url, final ViewHolder v) { 
	
	Drawable cacheImage = loader.loadDrawable(url, new AsnycImageLoader_listview.ImageCallback() { 
	@SuppressLint("NewApi")
	@Override 
	public void imageLoaded(Drawable drawable) { 
	try{
		
		v.image.setImageDrawable(drawable);
	
	}catch(Exception e){
	e.printStackTrace();
}
	} 
	}); 
	if (cacheImage != null) { 
		
		v.image.setImageDrawable(cacheImage);
	
	} 

	} 
 Drawable img_loading;
} 



class ViewHolder { 

public TextView title; 

public ImageView image; 

}

