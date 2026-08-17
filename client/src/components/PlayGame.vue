<script setup>
import { defineProps } from "vue";
const props = defineProps({
  gameState: Object,
});
</script>

<template>
  <div>
    <div class="header">
      <p>
        {{ props.gameState.players[0].name }} vs.
        {{ props.gameState.players[1].name }}
      </p>
    </div>

    <div class="board">To do...</div>
  </div>
</template>

<style scoped>
.header {
  font-weight: bold;
}

.board {
  color: darkslategray;
}
</style>