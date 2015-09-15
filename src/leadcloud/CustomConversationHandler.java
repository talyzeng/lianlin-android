package leadcloud;

import java.util.List;

import android.content.Context;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationEventHandler;

// 处理对话成员变化事件
public class CustomConversationHandler extends AVIMConversationEventHandler {
	   private Context gContext = null;
	  private void toast(String str) {
	    Toast.makeText(gContext, str, Toast.LENGTH_SHORT).show();
	  }
	  private void toast(Context context, String str) {
	    Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	  }

	  @Override
	  public void onMemberLeft(AVIMClient client, AVIMConversation conversation, List<String> members, String kickedBy) {
	    // 请按自己需求改写
	 //   toast(MsgUtils.nameByUserIds(members) + " left, kicked by " + MsgUtils.nameByUserId(kickedBy));
	    //注：MsgUtils 是一个辅助类，nameByUserIds 用来将 userId 转换成用户名
	  }

	  @Override
	  public void onMemberJoined(AVIMClient client, AVIMConversation conversation, List<String> members, String invitedBy) {
	    // 请按自己需求改写
	  //  toast(MsgUtils.nameByUserIds(members) + " joined , invited by " + MsgUtils.nameByUserId(invitedBy));
	    //注：MsgUtils 是一个辅助类，nameByUserIds 用来将 userId 转换成用户名
	  }

	  @Override
	  public void onKicked(AVIMClient client, AVIMConversation conversation, String kickedBy) {
	    // 请按自己需求改写
	    //toast("you are kicked by " + MsgUtils.nameByUserId(kickedBy));
	  }

	  @Override
	  public void onInvited(AVIMClient client, AVIMConversation conversation, String operator) {
	    // 请按自己需求改写
	   // toast("you are invited by " + MsgUtils.nameByUserId(operator));
	  }
	};
