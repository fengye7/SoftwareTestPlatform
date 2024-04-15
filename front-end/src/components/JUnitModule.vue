<template>
  <div>
    <h3>在线编辑器</h3>
    <select v-model="selectValue">
      <option value="cpp">C++</option>
      <option value="python">Python</option>
      <option value="java">Java</option>
    </select>
    <hr />
    <br />
    <div class="my-codemirror" ref="editor"></div>
  </div>
</template>

<script setup>
// import { python } from "@codemirror/lang-python";
// import { cpp } from "@codemirror/lang-cpp";
// import { java } from "@codemirror/lang-java";

import { ref, onMounted } from "vue";

import { EditorState } from "@codemirror/state";
import { EditorView, lineNumbers } from "@codemirror/view";
import { history } from "@codemirror/commands";
import { autocompletion, closeBrackets } from "@codemirror/autocomplete";
import { bracketMatching, syntaxHighlighting } from "@codemirror/language";
import { oneDarkHighlightStyle, oneDark } from "@codemirror/theme-one-dark";

import { graphql } from "cm6-graphql";
import query from "./js/sample-query";
import { TestSchema } from "./js/testSchema";

const selectValue = ref("java");

const state = EditorState.create({
  doc: query,
  extensions: [
    bracketMatching(),
    closeBrackets(),
    history(),
    autocompletion(),
    lineNumbers(),
    oneDark,
    syntaxHighlighting(oneDarkHighlightStyle),
    graphql(TestSchema, {
      onShowInDocs(field, type, parentType) {
        alert(
          `Showing in docs.: Field: ${field}, Type: ${type}, ParentType: ${parentType}`
        );
      },
      onFillAllFields(view, schema, _query, cursor, token) {
        alert(`Filling all fields. Token: ${token}`);
      },
    }),
  ],
});
onMounted(() => {
  const editor = document.getElementById("editor");
  editor.appendChild(new EditorView({
    state,
  }));
});
</script>

<style scoped>
.my-codemirror {
  width: 80vh;
  max-height: 80vh;
}
</style>