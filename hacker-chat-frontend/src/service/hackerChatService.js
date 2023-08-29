import {get, writable} from "svelte/store";
import SockJS from "sockjs-client"
import {Stomp} from "@stomp/stompjs";
import {createISOTime} from "./utilities.js";

export const userName = writable("");
export const chatMessages = writable([]);

let stompClient = null;

export const connect = () => {
    if (get(userName)) {
        const socket = new SockJS("/chat");
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
    }
}

export const sendMessage = (content) => {
    if (content && stompClient) {
        const chatMessage = {
            userName: get(userName),
            content: content,
            type: "CHAT",
            sendAt: createISOTime(),
            receivedAt: createISOTime()
        };
        stompClient.send("/app/chat/messages", {}, JSON.stringify(chatMessage));
    }
}

const onConnected = () => {
    stompClient.subscribe("/topic/public", onMessageReceived);
    stompClient.subscribe("/topic/" + get(userName), onMessageReceived);
    stompClient.send("/app/chat/users", {}, JSON.stringify(
        {
            userName: get(userName),
            content: `${get(userName)} joined the chat.`,
            type: 'JOIN',
            sendAt: createISOTime(),
            receivedAt: createISOTime()
        }));
}

const onError = (error) => {
    console.log(error);
}

const onMessageReceived = (payload) => {
    let chatMessage = JSON.parse(payload.body);
    console.log("### onMessageReceived: " + JSON.stringify(chatMessage));
    chatMessages.update(cms => {
        cms = [...cms, chatMessage]
        return cms;
    });
}

