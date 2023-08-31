<script>
    import JoinHackerChat from "./lib/JoinHackerChat.svelte";
    import HackerChat from "./lib/HackerChat.svelte";
    import TaskController from "./lib/TaskController.svelte";
    import {userName} from "./service/hackerChatService.js";
    import {calculateHash} from "./service/utilities.js";

    const maintenanceHashValue = "5e4a2f0c9053fce85fbdc2f8e5e150ce0baa634b4eacfb3e81db205ec077c58a";

    let showHackerChat = false;
    let isMaintenance = false;

    const onJoin = async (event) => {
        $userName = event.detail;
        showHackerChat = true;
        isMaintenance = maintenanceHashValue === await calculateHash($userName);
    }

</script>
<div class="container">
    <JoinHackerChat on:join={onJoin}></JoinHackerChat>
    {#if showHackerChat}
        {#if (isMaintenance)}
            <TaskController/>
        {:else }
            <HackerChat/>
        {/if}
    {/if}
</div>
