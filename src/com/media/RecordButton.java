package com.media;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.lianlin.R;

public class RecordButton extends Button {

	public RecordButton(Context context) {
		super(context);
		init();
	}

	public RecordButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public RecordButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void setSavePath(String path) {
		mFileName = path;
	}

	public void setOnFinishedRecordListener(OnFinishedRecordListener listener) {
		finishedListener = listener;
	}

	private String mFileName = null;

	private OnFinishedRecordListener finishedListener;

	private static final int MIN_INTERVAL_TIME = 2000;// 2s
	private long startTime;
    private String now_path,now_filename,now_date;
	private Dialog recordIndicator;

	private static int[] res = { R.drawable.mic_2, R.drawable.mic_3,
			R.drawable.mic_4, R.drawable.mic_5 };

	private static ImageView view;

	private MediaRecorder recorder;

	private ObtainDecibelThread thread;

	private Handler volumeHandler;

	private void init() {
		volumeHandler = new ShowVolumeHandler();
	}

	Boolean isDJ=true;
	public void Enable(){
		isDJ=true;
	}
	public void UnEnable(){
		isDJ=false;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {

	 if(isDJ){
		if (mFileName == null)
			return false;

		int action = event.getAction();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			initDialogAndStartRecord();
			break;
		case MotionEvent.ACTION_UP:
			finishRecord();
			break;
		case MotionEvent.ACTION_CANCEL:// 褰撴墜鎸囩Щ鍔ㄥ埌view澶栭潰锛屼細cancel
			cancelRecord();
			break;
		}
	 }
		return true;
	}

	private void initDialogAndStartRecord() {

		startTime = System.currentTimeMillis();
		recordIndicator = new Dialog(getContext(),
				R.style.like_toast_dialog_style);
		view = new ImageView(getContext());
		view.setImageResource(R.drawable.mic_2);
		recordIndicator.setContentView(view, new LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		recordIndicator.setOnDismissListener(onDismiss);
		LayoutParams lp = recordIndicator.getWindow().getAttributes();
		lp.gravity = Gravity.CENTER;

		startRecording();
		recordIndicator.show();
	}

	private void finishRecord() {
		stopRecording();
		recordIndicator.dismiss();

		long intervalTime = System.currentTimeMillis() - startTime;
		if (intervalTime < MIN_INTERVAL_TIME) {
			
			Toast.makeText(getContext(), "时间太短！", Toast.LENGTH_SHORT).show();
			File file = new File(mFileName);
			file.delete();
			return;
		}

		if (finishedListener != null)
			finishedListener.onFinishedRecord(mFileName,intervalTime,now_path,now_filename,now_date);
	}

	private void cancelRecord() {
		stopRecording();
		recordIndicator.dismiss();

		Toast.makeText(getContext(), "取消录音！", Toast.LENGTH_SHORT).show();
		File file = new File(mFileName);
		file.delete();
	}

	private void startRecording() {
		File file = new File(Environment
				.getExternalStorageDirectory()+"/mlmo/add/luyin/"); 
		if(!file.exists()){
		file.mkdirs();// 创建文件夹 
		}
		
		//先随机生成录音文件名
		 SimpleDateFormat    formatter1    =   new    SimpleDateFormat    ("yyyyMMddHHmmss");       
	       Date    curDate1    =   new    Date(System.currentTimeMillis());//获取当前时间       
	       String    str    =    formatter1.format(curDate1);      
	       
	       SimpleDateFormat    formatter    =   new    SimpleDateFormat    ("yyyy-MM-dd  HH:mm:ss");       
	        Date    curDate    =   new    Date(System.currentTimeMillis());//获取当前时间       
	        now_date    =    formatter.format(curDate);   
			now_path = Environment.getExternalStorageDirectory()
					.getAbsolutePath()+"/mlmo/add/luyin/"+str+".mp3";
			now_filename=str+".mp3";
			Log.v("当前文件测试1：","kk  "+now_filename);
			setSavePath(now_path);
		
		
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(mFileName);

		try {
			recorder.prepare();
		} catch (IOException e) {
			e.printStackTrace();
		}

		recorder.start();
		thread = new ObtainDecibelThread();
		thread.start();

	}

	private void stopRecording() {
		if (thread != null) {
			thread.exit();
			thread = null;
		}
		if (recorder != null) {
			recorder.stop();
			recorder.release();
			recorder = null;
		}
	}
	
	private class ObtainDecibelThread extends Thread {

		private volatile boolean running = true;

		public void exit() {
			running = false;
		}

		@Override
		public void run() {
			while (running) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (recorder == null || !running) {
					break;
				}
				int x = recorder.getMaxAmplitude();
				if (x != 0) {
					int f = (int) (10 * Math.log(x) / Math.log(10));
					if (f < 26)
						volumeHandler.sendEmptyMessage(0);
					else if (f < 32)
						volumeHandler.sendEmptyMessage(1);
					else if (f < 38)
						volumeHandler.sendEmptyMessage(2);
					else
						volumeHandler.sendEmptyMessage(3);

				}

			}
		}

	}

	private OnDismissListener onDismiss = new OnDismissListener() {

		@Override
		public void onDismiss(DialogInterface dialog) {
			stopRecording();
		}
	};

	static class ShowVolumeHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			view.setImageResource(res[msg.what]);
		}
	}

	public interface OnFinishedRecordListener {
		//public void onFinishedRecord(String audioPath);

		public void onFinishedRecord(String mFileName, long intervalTime,String now_path,String now_filename,String now_date);
	}

}
