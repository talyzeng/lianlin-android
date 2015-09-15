package leadcloud;

/**
 * Created by lzw on 15/5/19.
 */

import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;

public interface AVIMTypedMessageArrayCallback {
  void done(List<AVIMTypedMessage> typedMessages, AVException e);
}
