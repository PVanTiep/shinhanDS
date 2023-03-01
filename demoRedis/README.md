## Link kết quả demo
<a href="https://drive.google.com/file/d/1WEmf8qoDePgts9h6Cgi96SWdPUDWnevO/view?usp=share_link" target="_blank">Link video demo</a>
## Code update
**Chat message**
```java
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
    public enum MessageType{
        CHAT,
        JOIN,
        LEAVE,
        UPDATE,
        CLOSE
    }
}
```
**Chat controller**
```java
@Autowired
    private SimpMessageSendingOperations messageTemplate;
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage){
        template.opsForList().rightPush("List-chat",chatMessage);
        return chatMessage;
    }
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("username",chatMessage.getSender());
        List<ChatMessage> arrayList = new ArrayList<>();
        arrayList = template.opsForList().range("List-chat", 0, -1);
        for (ChatMessage chatMessage1:arrayList) {
                chatMessage1.setType(ChatMessage.MessageType.UPDATE);
                messageTemplate.convertAndSend("/topic/public", chatMessage1);

        }
        ChatMessage chatMessage1 = new ChatMessage();
        chatMessage1.setType(ChatMessage.MessageType.CLOSE);
        messageTemplate.convertAndSend("/topic/public",chatMessage1);
        return chatMessage;
```
**main.js**
```js
else if (update && message.type === "UPDATE") {
        messageElement.classList.add("chat-message");
        var avatarElement = document.createElement("i");
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style["background-color"] = getAvatarColor(message.sender);
        console.log(message.sender);
        messageElement.appendChild(avatarElement);
        var usernameElement = document.createElement("span");
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
        //update = false;
    } else {
        update = false;
        key = true;
    }
    if (key == false) {
        var textElement = document.createElement("p");
        var messageText = document.createTextNode(message.content);
        textElement.appendChild(messageText);
        messageElement.appendChild(textElement);
        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;
    } else key = false;
```
