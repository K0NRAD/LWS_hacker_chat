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
                <h1 class="card-title ls-8 ms-5 me-5">
                    Cyber
                    <div class="letter-container">
                        <div class="letter" style="animation-delay: 0s;">S</div>
                        <div class="letter" style="animation-delay: 0.2s;">e</div>
                        <div class="letter" style="animation-delay: 0.6s;">c</div>
                        <div class="letter" style="animation-delay: 0.8s;">u</div>
                        <div class="letter" style="animation-delay: 1.0s;">r</div>
                        <div class="letter" style="animation-delay: 1.2s;">i</div>
                        <div class="letter" style="animation-delay: 1.4s;">t</div>
                        <div class="letter" style="animation-delay: 1.6s;">y</div>
                    </div>
                    Chat
                </h1>
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

    .ls-8 {
        letter-spacing: 8px;
    }

    .letter-container {
        display: inline-flex;
    }

    .letter {
        display: inline-block;
        color: red;
        transform-origin: center;
        animation: rotateLetter 1.6s linear 1 forwards;
    }

    @keyframes rotateLetter {
        0% {
            transform: rotateX(0deg);
        }
        100% {
            transform: rotateX(360deg);
        }
    }
</style>


