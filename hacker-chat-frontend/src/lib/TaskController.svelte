<script>
    import {onMount} from "svelte";
    import {tasks} from "../stores/stores.js";
    import Navbar from "./Navbar.svelte";
    import TaskList from './TaskList.svelte';
    import TaskApiService from "../service/taskApiService.js";

    onMount(async () => {
        $tasks = await TaskApiService.getTasks();
    })

    $: count = $tasks.length;
    $: countDone = $tasks.filter(task => task.done).length
</script>

<svelte:head><title>Dashboard {countDone}/{count}</title></svelte:head>

<Navbar></Navbar>
<div class="content">
    <TaskList></TaskList>
</div>

<style>
    .content {
        margin-top: 5em;
    }
</style>