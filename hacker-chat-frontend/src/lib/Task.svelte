<script>
    import {Toast} from "sveltestrap";
    import {createEventDispatcher} from "svelte/internal";
    import {German} from "flatpickr/dist/l10n/de";
    import {onMount} from "svelte";
    import {tasks} from "../stores/stores";
    import {customDateTimeToLocalDateTime, localDateTimeToCustomDateTime} from "../service/utilities.js";
    import flatpickr from "flatpickr";
    import TaskApiService from "../service/taskApiService.js";

    onMount(() => {
        flatpickr("#input-release", {
            enableTime: true,
            dateFormat: "d.m.Y H:i",
            locale: German,
        });
    });

    export let task = {
        id: null,
        question: "",
        answers: ["", "", ""],
        correct: 0,
        releaseAt: "",
        done: false,
    };

    let releaseAt = localDateTimeToCustomDateTime(task.releaseAt);

    $: {
        task.releaseAt = customDateTimeToLocalDateTime(releaseAt);
    }

    let isShowToast = false;
    let toastTitle = "";
    let toastMessage = "";

    let isNewTask = $tasks.findIndex((t) => t.id === task.id) === -1;

    const dispatch = createEventDispatcher();

    const onClickSave = () => {
        if (task.question.trim() === "" ||
            task.answers[0].trim() === "" ||
            task.answers[1].trim() === "" ||
            task.answers[2].trim() === "" ||
            task.releaseAt.trim() === "") {
            toastTitle = "ERROR";
            toastMessage = "Missing arguments"
            isShowToast = true;
            return;
        }
        console.log(task);

        TaskApiService.postTask(task)
            .then(savedTask => {
                task = savedTask;
                const index = $tasks.findIndex((t) => t.id === task.id);
                if (index !== -1) {
                    $tasks[index] = task;
                } else {
                    $tasks = [task, ...$tasks];
                }
            });

        dispatch("close");
        toastTitle = "OK";
        toastMessage = "Task successful saved!"
        isShowToast = true;
    };

    const onClickDelete = (id) => {
        TaskApiService.deleteTaskById(id);
        $tasks = $tasks.filter((t) => t.id !== id);
        dispatch("close");
    };

    const onClickCancel = () => {
        dispatch("close");
    };
</script>

<div class="card">
    <form on:submit|preventDefault={onClickSave}>
        <div class="card-header mb-3">
            <div class="d-flex align-items-center justify-content-between">
                <label for="input-question" class="form-label ms-1 fw-bold">Task</label>
                <input type="checkbox" class="ms-3" id="checkbox-id" bind:checked={task.done}/>
            </div>
            <input type="text" class="form-control" id="input-question" bind:value={task.question}/>
        </div>
        <div class="card-body">
            {#each ["A", "B", "C"] as answer, index}
                <div class="mb-3">
                    <div class="input-group">
                        <div class="input-group-text">
                            <span class="me-2 fw-bold">{answer}</span>
                            <input class="form-check-input mt-0" type="radio" bind:group={task.correct} value={index}/>
                        </div>
                        <input
                                type="text"
                                class="form-control"
                                id="input-answer-{index}"
                                bind:value={task.answers[index]}/>
                    </div>
                </div>
            {/each}
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon">
                    <img src="release.png" alt="icon" width="30" height="30"/>
                </span>
                <input type="text" class="form-control" id="input-release" bind:value={releaseAt}/>
            </div>
        </div>
        <div class="card-footer">
            <div class="button-group">
                <button type="submit" class="btn btn-primary equal-width-btn me-2">Save</button>
                {#if isNewTask}
                    <button type="button" class="btn btn-danger equal-width-btn" on:click={onClickCancel}>Cancel
                    </button
                    >
                {:else}
                    <button type="button" class="btn btn-danger equal-width-btn" on:click={() => onClickDelete(task.id)}
                    >Delete
                    </button
                    >
                {/if}
            </div>
        </div>
    </form>
</div>

<Toast class={"position-fixed bottom-0 end-0 me-4 mb-4"}
       autohide
       body
       header={toastTitle}
       delay={2000}
       isOpen={isShowToast} on:close={() => (isShowToast = false)}>{toastMessage}</Toast>

<style>
    @import "flatpickr/dist/flatpickr.min.css";

    .equal-width-btn {
        width: 120px;
    }
</style>
