<script>
    import { Button, Modal, ModalHeader, ModalBody } from "sveltestrap";
    import Task from "./Task.svelte";
    let open = false;
    const toggle = () => (open = !open);
</script>

<section class="bg-primary text-white text-start py-2">
    <div class="container">
        <h1 class="display-6">Dashboard</h1>
        <h2 class="lead fw-light">
            <slot />
        </h2>
        <Button color="info" on:click={toggle}>New Question</Button>
    </div>
</section>

<div>
    <Modal isOpen={open} {toggle}>
        <ModalHeader {toggle}>New Question</ModalHeader>
        <ModalBody>
            <Task on:close={toggle} />
        </ModalBody>
    </Modal>
</div>
