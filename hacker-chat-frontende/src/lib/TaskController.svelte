<script>
    import {onMount} from "svelte";
    import { tasks } from "../stores/stores.js";
    import Navbar from "./Navbar.svelte";
    import TaskList from './TaskList.svelte';
    import TaskListApi from "../stores/taskListApi.js";

    onMount( async () => {
        $tasks = await TaskListApi.load();
    })
    $: count = $tasks.length;
    $: countDone = $tasks.filter( task => task.done).length
</script>

<svelte:head><title>Dashboard {countDone}/{count}</title></svelte:head>

<Navbar>Tasks {countDone}/{count}</Navbar>
<TaskList></TaskList>

