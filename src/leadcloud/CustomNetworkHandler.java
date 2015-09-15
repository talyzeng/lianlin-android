package leadcloud;

import android.util.Log;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMClientEventHandler;

// 处理网络状态变化事件
public	class CustomNetworkHandler extends AVIMClientEventHandler {
	  @Override
	  public void onConnectionPaused(AVIMClient client) {
	    // 请按自己需求改写
	    Log.v("connect paused","1");
	  }

	  @Override
	  public void onConnectionResume(AVIMClient client) {
	    // 请按自己需求改写
	    Log.v("connect resume","1"); 
	  }
	}