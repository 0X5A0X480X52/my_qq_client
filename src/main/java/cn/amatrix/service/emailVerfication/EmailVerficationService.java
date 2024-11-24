package cn.amatrix.service.emailVerfication;

import java.awt.EventQueue;
import java.awt.Toolkit;

import com.alibaba.fastjson2.JSONObject;

import cn.amatrix.model.message.Message;
import cn.amatrix.model.message.Message.MessageEndPoint;
import cn.amatrix.model.users.User;
import cn.amatrix.utils.webSocketClient.WebSocketClient;
import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEventQueue;

/**
 * SignUpService 类用于处理注册相关的业务逻辑。
 */
public class EmailVerficationService {
    
    WebSocketClient client;
    public enum EmailVerificationCodeStatus {
        TIMEOUT, INVALID, SUCCESS, EMAILED, FAILED, HAVESIGNUP, UNSIGNUP, UNKNOWN
    } 

    /**
     * 构造方法，检查系统事件队列是否是 ReceivedWebSocketMessageEventQueue 的实例。
     * @throws IllegalArgumentException 如果系统事件队列不是 ReceivedWebSocketMessageEventQueue 的实例
     */

    public EmailVerficationService( WebSocketClient client ) throws IllegalArgumentException {
        // 检查系统事件队列是否是 ReceivedWebSocketMessageEventQueue 的实例
        EventQueue eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
        if ( eventQueue instanceof ReceivedWebSocketMessageEventQueue ) {
            this.client = client;
        } else
            throw new IllegalArgumentException("EventQueue is not instance of ReceivedWebSocketMessageEventQueue");
    }

    /**
     * 获取验证码。
     * @param email 邮箱地址
     * 
     * @apiNote 向 client 发送的 Message 结构如下：
     * @apiNote Message.type: "getVerificationCode"
     * @apiNote Message.content: 邮箱地址
     */
    public void getVerificationCode( String email ) {

        MessageEndPoint receiver = new MessageEndPoint();
        receiver.setType("Server");
        MessageEndPoint sender = new MessageEndPoint();
        sender.setId("WaitingForVerificationCode");
        sender.setType("user");
        Message message = new Message( receiver, sender,"getVerificationCode", email, "请求验证码");
        
        client.sendMessage(message.toJson());
    }

    /**
     * 提交注册信息。
     * @param captcha 验证码
     * @param email 邮箱地址
     * @param password 密码
     * 
     * @apiNote 向 client 发送的 Message 结构如下：
     * @apiNote Message.type: "submitSignUpInfo"
     * @apiNote Message.content: 一个JSON对象，包含了验证码和用户信息。其结构为 { "captcha": "验证码", "userInfo": User.toJson() }
     */
    public void submitSignUpInformation( String captcha, String username, String email, String password ) {

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        String userInfo = user.toJson();

        JSONObject signUpInfo = new JSONObject();
        signUpInfo.put("captcha", captcha);
        signUpInfo.put("userInfo", userInfo);

        String signUpInfoString = signUpInfo.toString();

        MessageEndPoint receiver = new MessageEndPoint();
        receiver.setType("Server");
        MessageEndPoint sender = new MessageEndPoint();
        sender.setId("WaitingForSignUpInformation");
        sender.setType("user");
        Message message = new Message( receiver, sender,"submitSignUpInfo", signUpInfoString, "提交注册信息");

        client.sendMessage(message.toJson());
    }

    /**
     * 提交找回密码信息。
     * @param captcha 验证码
     * @param email 邮箱地址
     * @param newPassword 新密码
     * 
     * @apiNote 向 client 发送的 Message 结构如下：
     * @apiNote Message.type: "submitPasswordRecoveryInfo"
     * @apiNote Message.content: 一个JSON对象，包含了验证码和用户信息。其结构为 { "captcha": "验证码", "userInfo": User.toJson() }
     */
    public void submitPasswordRecoveryInformation(String captcha, String email, String newPassword) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(newPassword);
        String userInfo = user.toJson();

        JSONObject recoveryInfo = new JSONObject();
        recoveryInfo.put("captcha", captcha);
        recoveryInfo.put("userInfo", userInfo);

        String recoveryInfoString = recoveryInfo.toString();

        MessageEndPoint receiver = new MessageEndPoint();
        receiver.setType("Server");
        MessageEndPoint sender = new MessageEndPoint();
        sender.setId("WaitingForPasswordRecovery");
        sender.setType("user");
        Message message = new Message(receiver, sender, "submitPasswordRecoveryInfo", recoveryInfoString, "提交找回密码信息");

        client.sendMessage(message.toJson());
    }

    /**
     * 提交修改敏感信息。
     * @param captcha 验证码
     * @param email 邮箱地址
     * @param newSensitiveInfo 新敏感信息
     * 
     * @apiNote 向 client 发送的 Message 结构如下：
     * @apiNote Message.type: "submitSensitiveInfoChange"
     * @apiNote Message.content: 一个JSON对象，包含了验证码和用户信息。其结构为 { "captcha": "验证码", "userInfo": User.toJson() }
     */
    public void submitSensitiveInfoChange(String captcha, String email, String name) {
        User user = new User();
        user.setEmail(email);
        user.setUsername(name);
        String userInfo = user.toJson();

        JSONObject sensitiveInfoChange = new JSONObject();
        sensitiveInfoChange.put("captcha", captcha);
        sensitiveInfoChange.put("userInfo", userInfo);

        String sensitiveInfoChangeString = sensitiveInfoChange.toString();

        MessageEndPoint receiver = new MessageEndPoint();
        receiver.setType("Server");
        MessageEndPoint sender = new MessageEndPoint();
        sender.setId("WaitingForSensitiveInfoChange");
        sender.setType("user");
        Message message = new Message(receiver, sender, "submitSensitiveInfoChangeInfo", sensitiveInfoChangeString, "提交修改敏感信息");

        client.sendMessage(message.toJson());
    }

    /**
     * 提交注销账户信息。
     * @param captcha 验证码
     * @param email 邮箱地址
     * 
     * @apiNote 向 client 发送的 Message 结构如下：
     * @apiNote Message.type: "submitAccountDeletion"
     * @apiNote Message.content: 一个JSON对象，包含了验证码和用户信息。其结构为 { "captcha": "验证码", "userInfo": User.toJson() }
     */
    public void submitAccountDeletion(String captcha, String email) {
        User user = new User();
        user.setEmail(email);
        String userInfo = user.toJson();

        JSONObject accountDeletion = new JSONObject();
        accountDeletion.put("captcha", captcha);
        accountDeletion.put("userInfo", userInfo);

        String accountDeletionString = accountDeletion.toString();

        MessageEndPoint receiver = new MessageEndPoint();
        receiver.setType("Server");
        MessageEndPoint sender = new MessageEndPoint();
        sender.setId("WaitingForAccountDeletion");
        sender.setType("user");
        Message message = new Message(receiver, sender, "submitAccountDeletionInfo", accountDeletionString, "提交注销账户信息");

        client.sendMessage(message.toJson());
    }

    /**
     * 处理验证码响应。
     * @param message 服务器返回的消息
     * @return 一个 Map 对象，包含了验证码状态和额外信息，其结构为 { EmailVerificationCodeStatus: String }
     * @throws IllegalArgumentException 如果消息类型不是 EmailVerificationCodeStatus
     */
    public Status handleWebSocketResponse(Message message) throws IllegalArgumentException {

        if ( !message.getType().equals("EmailVerificationCodeStatus") ) {
            throw new IllegalArgumentException("Message type is not EmailVerificationCodeStatus.");
        }

        String content = message.getContent();
        EmailVerificationCodeStatus status;
        String additionalInfo = message.getStatus();

        switch (content) {
            case "TIMEOUT":
                status = EmailVerificationCodeStatus.TIMEOUT;
                break;
            case "INVALID":
                status = EmailVerificationCodeStatus.INVALID;
                break;
            case "SUCCESS":
                status = EmailVerificationCodeStatus.SUCCESS;
                break;
            case "EMAILED":
                status = EmailVerificationCodeStatus.EMAILED;
                break;
            case "FAILED":
                status = EmailVerificationCodeStatus.FAILED;
                break;
            case "HAVESIGNUP":
                status = EmailVerificationCodeStatus.HAVESIGNUP;
                break;
            case "UNSIGNUP":
                status = EmailVerificationCodeStatus.UNSIGNUP;
                break;
            default:
                status = EmailVerificationCodeStatus.UNKNOWN;
                additionalInfo = "Unknown response type.";
                break;
        }
        
        return new Status(status, additionalInfo);
    }

    public class Status {
        EmailVerificationCodeStatus status;
        String additionalInfo;

        public Status(EmailVerificationCodeStatus status, String additionalInfo) {
            this.status = status;
            this.additionalInfo = additionalInfo;
        }

        public EmailVerificationCodeStatus getStatus() {
            return status;
        }

        public String getAdditionalInfo() {
            return additionalInfo;
        }
    }

    public static void main(String[] args) {
        User user = new User();
        user.setEmail("email");
        user.setPassword("password");
        String userInfo = user.toJson();

        JSONObject signUpInfo = new JSONObject();
        signUpInfo.put("captcha", "captcha");
        signUpInfo.put("userInfo", userInfo);

        String signUpInfoString = signUpInfo.toString();

        MessageEndPoint receiver = new MessageEndPoint();
        receiver.setType("Server");
        MessageEndPoint sender = new MessageEndPoint();
        sender.setId("WaitingForSignUpInformation");
        sender.setType("user");
        Message message = new Message( receiver, sender,"submitSignUpInfo", signUpInfoString, "提交注册信息");

        System.out.println(message.toJson());
    }
}
