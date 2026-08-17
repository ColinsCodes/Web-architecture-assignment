<script setup>
import { ref } from "vue";
import StartScreen from "./components/StartScreen.vue";
import PlayGame from "./components/PlayGame.vue";

const gameState = ref(undefined);

function updateGameState(newGameState) {
  gameState.value = newGameState;
}
</script>

<template>
  <PlayGame
    v-if="gameState !== undefined"
    @gameStateChanged="updateGameState"
    v-bind:gameState="gameState"
  />

  <StartScreen v-else @gameStateChanged="updateGameState" />
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
