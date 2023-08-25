<script>
    import JoinHackerChat from "./lib/JoinHackerChat.svelte";
    import {userName} from "./service/hackerChatService.js";
    import HackerChat from "./lib/HackerChat.svelte";

    let showHackerChat = false;

    const onJoin = (event) => {
        $userName = event.detail;
        showHackerChat = true;
    }
</script>

<JoinHackerChat on:join={onJoin}></JoinHackerChat>
{#if showHackerChat}
    <HackerChat />
{/if}