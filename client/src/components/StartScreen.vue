<script setup>
import { ref, defineEmits } from "vue";
let playerOne = ref("");
let playerTwo = ref("");
let errorMessage = ref("");

const emit = defineEmits(["game-state-changed"]);

async function confirmPlayers() {
  if (!playerOne.value) {
    errorMessage.value = "Player 1 name is required";
    return;
  }

  if (!playerTwo.value) {
    errorMessage.value = "Player 2 name is required";
    return;
  }

  errorMessage.value = undefined;

  try {
    const response = await fetch("api/start", {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        nameplayer1: playerOne.value,
        nameplayer2: playerTwo.value,
      }),
    });

    if (response.ok) {
      const gameState = await response.json();
      emit("game-state-changed", gameState);
    } else {
      console.error(response.statusText);
    }
  } catch (error) {
    console.error(error.toString());
  }
}
</script>

<template>
  <div>
    <input placeholder="Player 1 name" v-model="playerOne" />

    <input placeholder="Player 2 name" v-model="playerTwo" />

    <p>{{ errorMessage }}</p>

    <button v-on:click="confirmPlayers">Start</button>
  </div>
</template>

<style scoped>
p {
  color: red;
}
</style>