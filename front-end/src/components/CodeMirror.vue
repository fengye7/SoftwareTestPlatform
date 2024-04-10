<template>
  <div class="codemirror">
    <Codemirror
      class="code"
      v-model="code"
      :style="{ height: '100%' }"
      :mode="mode"
      :spellcheck="true"
      :autofocus="true"
      :indent-with-tab="true"
      :tabSize="4"
      :extensions="extensions"
    />
  </div>
</template>
<script setup>
import { Codemirror } from "vue-codemirror";
import { javascript } from "@codemirror/lang-javascript";
import { css } from "@codemirror/lang-css";
import { html } from "@codemirror/lang-html";
import { oneDark } from "@codemirror/theme-one-dark";
// 引入vue模块
import { ref, onMounted, defineProps } from "vue";
let code = ref("");
let mode = ref("");
let extensions = [];

// 定义从父组件接收的属性
const props = defineProps({
  option: Object,
});

onMounted(() => {
  mode.value = props.option.options.mode;
  code.value = props.option.options.code;
  if (props.option.options.language === "js") {
    extensions = [javascript(), oneDark];
  } else if (props.option.options.language === "css") {
    extensions = [css(), oneDark];
  } else {
    extensions = [html(), oneDark];
  }
});
</script>
<style scoped>
.codemirror {
  width: 100%;
  height: 100%;
}
</style>