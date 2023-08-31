<script>
    import { Button, Modal, ModalHeader, ModalBody } from "sveltestrap";
    import Task from "./Task.svelte";
    let open = false;
    const toggle = () => (open = !open);
</script>


<nav class="navbar bg-white fixed-top">
    <div class="container bg-lws ">
        <div class="navbar-brand mb-0 fs-2 letter-spacing text-white">Task<span class="text-lws">Dashboard</span></div>
        <Button color="info" on:click={toggle}>New Task</Button>
    </div>
</nav>

<div>
    <Modal isOpen={open} {toggle}>
        <ModalHeader {toggle}>New Question</ModalHeader>
        <ModalBody>
            <Task on:close={toggle} />
        </ModalBody>
    </Modal>
</div>

<style>
    .bg-lws {
        background-color: #B645B2;
    }

    .text-lws {
        color: #18D8E8;
    }

    .letter-spacing {
        letter-spacing: 0.5rem;
    }
</style>