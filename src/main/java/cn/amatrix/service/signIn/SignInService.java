package cn.amatrix.service.signIn;

import java.awt.EventQueue;
import java.awt.Toolkit;

import cn.amatrix.model.message.Message;
import cn.amatrix.model.message.Message.MessageEndPoint;
import cn.amatrix.model.users.User;
import cn.amatrix.utils.webSocketClient.WebSocketClient;
import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEventQueue;

/**
 * SignUpService 类用于处理注册相关的业务逻辑。
 */
public class SignInService {
    
    WebSocketClient client;
    public enum SignInCodeStatus {
        SUCCESS, FAILED, UNSIGNUP, UNKNOW
    }

    /**
     * 构造方法，检查系统事件队列是否是 ReceivedWebSocketMessageEventQueue 的实例。
     * @throws IllegalArgumentException 如果系统事件队列不是 ReceivedWebSocketMessageEventQueue 的实例
     */

    public SignInService( WebSocketClient client ) throws IllegalArgumentException {
        // 检查系统事件队列是否是 ReceivedWebSocketMessageEventQueue 的实例
        EventQueue eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
        if ( eventQueue instanceof ReceivedWebSocketMessageEventQueue ) {
            this.client = client;
        } else
            throw new IllegalArgumentException("EventQueue is not instance of ReceivedWebSocketMessageEventQueue");
    }

    /**
     * 提交注册信息。
     * @param email 邮箱地址
     * @param password 密码
     * 
     * @apiNote 向 client 发送的 Message 结构如下：
     * @apiNote Message.type: "submitSignInInfo"
     * @apiNote Message.content: 一个JSON对象，包含了验证码和用户信息。其结构为 { "captcha": "验证码", "userInfo": User.toJson() }
     */
    public void submitSignInInformation( String username, String email, String password ) {

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        String userInfo = user.toJson();

        MessageEndPoint receiver = new MessageEndPoint();
        receiver.setType("Server");
        MessageEndPoint sender = new MessageEndPoint();
        sender.setId("WaitingForSignInInformation");
        sender.setType("user");
        Message message = new Message( receiver, sender,"submitSignInInfo", userInfo, "提交登录信息");

        client.sendMessage(message.toJson());
    }

    /**
     * 处理验证码响应。
     * @param message 服务器返回的消息
     * @return 一个 Map 对象，包含了验证码状态和额外信息，其结构为 { SignInCodeStatus: String }
     * @throws IllegalArgumentException 如果消息类型不是 SignInCodeStatus
     */
    public Status handleWebSocketResponse(Message message) throws IllegalArgumentException {

        if ( !message.getType().equals("SignInCodeStatus") ) {
            throw new IllegalArgumentException("Message type is not SignInCodeStatus.");
        }

        String content = message.getContent();
        SignInCodeStatus status;
        String additionalInfo = message.getStatus();

        switch (content) {
            case "SUCCESS":
                status = SignInCodeStatus.SUCCESS;
                break;
            case "FAILED":
                status = SignInCodeStatus.FAILED;
                break;
            case "UNSIGNUP":
                status = SignInCodeStatus.UNSIGNUP;
                break;
            default:
                status = SignInCodeStatus.UNKNOW;
                additionalInfo = "Unknown response type.";
                break;
        }
        
        return new Status(status, additionalInfo);
    }

    public class Status {
        SignInCodeStatus status;
        String additionalInfo;

        public Status(SignInCodeStatus status, String additionalInfo) {
            this.status = status;
            this.additionalInfo = additionalInfo;
        }

        public SignInCodeStatus getStatus() {
            return status;
        }

        public String getAdditionalInfo() {
            return additionalInfo;
        }
    }

    public static void main(String[] args) {
        User user = new User();
        user.setEmail("3432900546@qq.com");
        user.setPassword("123456");
        String userInfo = user.toJson();

        MessageEndPoint receiver = new MessageEndPoint();
        receiver.setType("Server");
        MessageEndPoint sender = new MessageEndPoint();
        sender.setId("WaitingForSignInInformation");
        sender.setType("user");
        Message message = new Message( receiver, sender,"submitSignInInfo", userInfo, "提交登录信息");

        System.out.println(message.toJson());
    }
}
