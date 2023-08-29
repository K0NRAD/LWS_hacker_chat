<script>
	import Task from './Task.svelte';
    import { tasks } from "./../stores/stores.js";
</script>

<div class="container">
    <div class="row g-2 mt-1">
        {#each $tasks as task (task)}
            <div class="col-4">
                <Task {task} />
            </div>
        {:else}
            <h1 class="fs-3 mt-4">Keine Aufgaben vorhanden</h1>
        {/each}
    </div>
</div>
