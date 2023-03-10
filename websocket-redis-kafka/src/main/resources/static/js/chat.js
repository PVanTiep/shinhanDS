//"use strict";
var usernamePage = document.querySelector("#username-page");
var chatPage = document.querySelector("#chat-page");
var usernameForm = document.querySelector("#usernameForm");
var messageForm = document.querySelector("#messageForm");
var messageInput = document.querySelector("#message");
var messageArea = document.querySelector("#messageArea");
var connectingElement = document.querySelector(".connecting");
var stompClient = null;
var username = null;
var channel = 'room-901';
var colors = [
    "#2196F3",
    "#32c787",
    "#00BCD4",
    "#ff5652",
    "#ffc107",
    "#ff85af",
    "#FF9800",
    "#39bbb0",
];
function connect(event) {
    username = document.querySelector("#name").value.trim();
    if (username) {
        usernamePage.classList.add("hidden");
        chatPage.classList.remove("hidden");
        var socket = new SockJS("/ws");
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}
function onConnected() {
    channel = document.querySelector('input[name="channel"]:checked').value;
    // Subscribe to the Public Topic
    stompClient.subscribe("/topic/" + channel, function (message) {
        generateMessage(JSON.parse(message.body));
    });
    stompClient.subscribe('/user/queue/reply', function (response) {
        onMessageHistoryReceived(JSON.parse(response.body));
    });
    stompClient.send(
        "/app/chat.addUser/" + channel,
        {},
        JSON.stringify({ sender: username, type: "JOIN" })
    );
    connectingElement.classList.add("hidden");
}
function onError(error) {
    connectingElement.textContent =
        "Could not connect to WebSocket server. Please refresh this page to try again!";
    connectingElement.style.color = "red";
}
function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if (messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: "CHAT",
        };
        stompClient.send("/app/chat.sendMessage/" + channel, {}, JSON.stringify(chatMessage));
        messageInput.value = "";
    }
    event.preventDefault();
}
function generateMessage(message) {
    var messageElement = document.createElement("li");
    if (message.type === "JOIN") {
        messageElement.classList.add("event-message");
        message.content = message.sender + " joined!";
    } else if (message.type === "LEAVE") {
        messageElement.classList.add("event-message");
        message.content = message.sender + " left!";
    } else {
        messageElement.classList.add("chat-message");
        var avatarElement = document.createElement("i");
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style["background-color"] = getAvatarColor(message.sender);
        messageElement.appendChild(avatarElement);
        var usernameElement = document.createElement("span");
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }
    var textElement = document.createElement("p");
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);
    messageElement.appendChild(textElement);
    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}
function onMessageHistoryReceived(chatHistory) {
    chatHistory.forEach(function(obj) {
        generateMessage(obj);
    });
}
function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}
const addButton = document.getElementById('add-room');
const groupDiv = document.getElementById('group');
var num=902;
addButton.addEventListener('click', () => {
    //username = document.querySelector("#name").value.trim();
    const radioInput = document.createElement('input');
    radioInput.type = 'radio';
    radioInput.id = 'public';
    radioInput.name = 'channel';
    num++;
    radioInput.value = 'room-'+num;

    const radioLabel = document.createElement('label');
    radioLabel.for = 'public';
    radioLabel.textContent = 'Room '+num;

    groupDiv.appendChild(radioInput);
    groupDiv.appendChild(radioLabel);
});

usernameForm.addEventListener("submit", connect, true);
messageForm.addEventListener("submit", sendMessage, true);