<template>
  <div>
    <button @click="changeTheme($event)">黑夜</button>
    <button @click="changeMode($event)">C++</button>
    <hr/><br/>
    <div style="height: 400px">
    <codemirror
      class="my-codemirror"
      v-model="code"
      placeholder="Code goes here..."
      :mode="mode"
      :spellcheck="spellcheck"
      :autofocus="autofocus"
      :indent-with-tab="indentWithTab"
      :tabSize="tabSize"
      :extensions="extensions"
      @ready="log('ready', $event)"
      @change="log('change', $event)"
      @focus="log('focus', $event)"
      @blur="useEditedCode"
      style="height: 400px"
    ></codemirror>
    </div>
  </div>
</template>

<script>
import { Codemirror } from "vue-codemirror";
import { python } from "@codemirror/lang-python";
import { cpp } from "@codemirror/lang-cpp";

import { oneDark } from "@codemirror/theme-one-dark";

import { reactive, ref, toRefs } from "vue";

export default {
  components: {
    Codemirror,
  },
  setup() {
    // 数据
    const code = ref(``);
    let selectValue = "cpp";
    let dateTime = "黑夜";
    const options = reactive({
      mode: "text/x-c++src",
      spellcheck: true,
      autofocus: true,
      indentWithTab: true,
      tabSize: 2,
      extensions: [cpp(), oneDark], //传递给CodeMirror EditorState。创建({扩展})
    });

    // 方法
    // 失去焦点时,使用已编辑的代码
    function useEditedCode() {
      console.log("@@@blur@@@code:", code.value);
      console.log("@@@blur@@@cpp:", cpp);
    }

    // 改变主题
    function changeTheme(e) {
      console.log("options.extensions:", options.extensions);
      if (e.target.innerHTML === "黑夜") {
        options.extensions = [];
        dateTime = e.target.innerHTML = "白天";
      } else {
        options.extensions = [oneDark];
        dateTime = e.target.innerHTML = "黑夜";
      }
    }
    // 改变模式
    function changeMode(e) {
      console.log("selectValue:", selectValue);
      if (selectValue === "cpp") {
        if (dateTime === "黑夜") options.extensions = [python(), oneDark];
        else options.extensions = [python()];
        selectValue = "python";
        e.target.innerHTML = "python";
        options.mode = "text/x-python";
      } else {
        if (dateTime === "黑夜") options.extensions = [cpp(), oneDark];
        else options.extensions = [cpp()];
        selectValue = "cpp";
        e.target.innerHTML = "C++";
        options.mode = "text/x-c++src";
      }
    }
    // 返回
    return {
      code,
      selectValue,
      dateTime,
      ...toRefs(options),
      log: console.log,
      useEditedCode,
      changeTheme,
      changeMode,
    };
  },
};
</script>

<style scoped>
.my-codemirror {
  width: 100%;
  height: 100%;
}
</style>