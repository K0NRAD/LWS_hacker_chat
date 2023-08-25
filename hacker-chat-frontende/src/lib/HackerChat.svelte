<script>
    import {onMount} from "svelte";
    import HackerChatMessage from "./HackerChatMessage.svelte";
    import {chatMessages, connect, sendMessage} from "../service/hackerChatService.js";

    let messageContainer;
    let messageInputField;

    let profilePicUser = "user_750x750.png";
    let profilePicBot = "hacker_750x750.png";

    let chatMessageContent = "";

    onMount(() => {
        connect();
    });

    const onKeyDown = (event) => {
        if (event.key === 'Enter') {
            onClickSend();
        }
    }

    const onClickSend = () => {
        sendMessage(chatMessageContent)
    }

    const onSendTo = (event) => {
        const {userName, questionId} = event.detail;
        chatMessageContent = `@${userName}  ${questionId}`;
        messageInputField.focus();
    }

</script>

<div class="container mt-5">
    <div class="col-8  offset-2">
        <div class="card chat">
            <div class="card-header">
                <h1 class="text-center">Cyber Security Chat</h1>
            </div>
            <div class="card-body">
                <div class="chat-messages" bind:this={messageContainer}>
                    {#each $chatMessages as chatMessage}
                        <HackerChatMessage on:sendTo={onSendTo}
                                           {profilePicUser}
                                           {profilePicBot}
                                           {chatMessage}/>
                    {/each}
                </div>
            </div>
            <div class="card-footer">
                <div class="input-group">
                    <input type="text" placeholder="Type Message ..." class="form-control" bind:value={chatMessageContent}
                           on:keydown={onKeyDown} bind:this={messageInputField}/>
                    <span class="input-group-append">
                        <button type="button" class="btn btn-primary ms-2" on:click={onClickSend}>Send</button>
                    </span>
                </div>
            </div>
        </div>
    </div>
</div>


<style>
    .chat .card-body {
        position: relative;
        overflow-x: hidden;
        padding: 0;
    }

    .chat-messages {
        height: 600px;
        padding: 20px;
        overflow: auto;
        transform: translate(0, 0);
        transition: transform 0.8s ease-in-out;
    }
</style>
