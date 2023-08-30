<script>
    import {userName} from "../service/hackerChatService.js";
    import {createEventDispatcher} from "svelte";

    const dispatch = createEventDispatcher();

    export let profilePicBot;
    export let profilePicUser;

    export let chatMessage;

    const isToday = (dateString) => {
        const dateToCheck = new Date(dateString);
        const currentDate = new Date();
        console.log(dateString);
        return (
            dateToCheck.getFullYear() === currentDate.getFullYear() &&
            dateToCheck.getMonth() === currentDate.getMonth() &&
            dateToCheck.getDate() === currentDate.getDate()
        );
    };

    const messageTextAlignment = () => {
        return chatMessage.userName === $userName ? 'text-end' : 'text-start';
    }

    const sendTo = () => {
        dispatch("sendTo", {
            userName: chatMessage.userName,
            questionId: chatMessage.questionId || ''
        });
    }
</script>

<div class="chat-msg d-block mb-2"
     class:right={chatMessage.userName === $userName}>
    <div class="chat-info clearfix small">
        <div class:float-end={chatMessage.userName === $userName}
             class:float-start={chatMessage.userName !== $userName}>
            <div class="chat-timestamp">
                <div class="chat-username font-weight-bold text-black {messageTextAlignment()}">
                    {chatMessage.userName}
                </div>
                {#if isToday(chatMessage.sendAt)}
                    <div class="chat-send-date {messageTextAlignment()}">
                        {new Date(chatMessage.sendAt).toLocaleTimeString([], {hour: '2-digit', minute: '2-digit'})}
                    </div>
                {:else}
                    <div class="chat-send-date {messageTextAlignment()}">
                        {new Date(chatMessage.sendAt).toLocaleString([], {
                            year: 'numeric',
                            month: '2-digit',
                            day: '2-digit',
                            hour: '2-digit',
                            minute: '2-digit',
                            hour12: false,
                        })}
                    </div>
                {/if}
            </div>
        </div>
    </div>
    <img class="chat-img"
         src={chatMessage.userName !== 'Malicious Marvin' ? profilePicUser : profilePicBot} alt="pic"
         on:click={sendTo}/>
    <div class="chat-text">
        {#each chatMessage.content.split("|") as line}
            <div class="d-flex">
                <span class="mr-auto">{line}</span>
            </div>
        {/each}
    </div>
</div>

<style>
    .chat-msg:after {
        clear: both;
    }

    .chat-text {
        border-radius: 5px;
        position: relative;
        padding: 5px 10px;
        background: #e2e6ee;
        border: 1px solid transparent;
        margin: 2px 80px 5px 50px;
        color: #444;
    }

    .chat-text:after,
    .chat-text:before {
        content: "";
        position: absolute;
        right: 100%;
        top: 12px;
        height: 0;
        width: 0;
        border: 6px solid transparent;
        border-right-color: #e2e6ee;
        margin-top: -6px;
        pointer-events: none;
        z-index: 444;
    }

    .chat-img {
        border-radius: 50%;
        float: left;
        width: 40px;
        height: 40px;
        cursor: pointer;
    }

    .right .chat-text {
        background: greenyellow;
        margin: 2px 50px 5px 80px;
    }

    .right .chat-text:after,
    .right .chat-text:before {
        right: auto;
        left: 100%;
        border-right-color: transparent;
        border-left-color: greenyellow;
    }

    .right .chat-img {
        float: right;
    }

    .chat-timestamp {
        margin: 0 50px;
        color: #999;
    }

</style>
