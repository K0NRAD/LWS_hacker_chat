<script>
    import {createEventDispatcher} from "svelte";
    import {Button, Card, Form, Input, InputGroup, InputGroupText} from 'sveltestrap';

    const dispatch = createEventDispatcher();

    let username = '';
    let showCard = true;

    const joinChat = (event) => {
        if (username.trim()) {
            event.preventDefault();
            showCard = false;
            dispatch("join", username);
        }
    }

</script>

{#if showCard}
    <div class="center">
        <Card class="text-center">
            <div class="card-header">
                <h5>Enter your name an join the</h5>
                <h1 class="card-title ls-6 ms-5 me-5">Cyber Security Chat</h1>
            </div>
            <div class="card-body mt-5">
                <Form on:submit={joinChat}>
                    <InputGroup>
                        <InputGroupText>Name</InputGroupText>
                        <Input type="text" class="form-control" bind:value={username}/>
                        <Button color="primary" type="submit">JOIN</Button>
                    </InputGroup>
                </Form>
            </div>
        </Card>
    </div>
{/if}

<style>
    .center {
        display: flex;
        align-items: center;
        justify-content: center;
        height: 100vh;
    }

    .ls-6 {
        letter-spacing: 6px;
    }

</style>


