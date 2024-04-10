<template>
  <div class="vue-codemirror">
    <el-row :gutter="15" class="mirow">
      <el-col :span="12" class="micol">
        <el-row class="mitoprow">
          <el-card class="box-card">
            <template v-slot:header>
              <div class="clearfix">
                <span>HTML</span>
              </div>
            </template>
            <Codemirror :option="editorp" />
          </el-card>
        </el-row>
        <el-row class="mibottomrow">
          <el-card class="box-card">
            <template v-slot:header>
              <div class="clearfix">
                <span>CSS</span>
              </div>
            </template>
            <Codemirror :option="editorp2" />
          </el-card>
        </el-row>
      </el-col>
      <el-col :span="12" class="micol">
        <el-card class="box-card">
          <template v-slot:header>
            <div class="clearfix">
              <span>JAVASCRIPT</span>
            </div>
          </template>
          <Codemirror :option="editorp3" />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import Codemirror from "../components/CodeMirror.vue";
import { reactive } from "vue";

const options = {
  language: "html",
  mode: "text/html",
  code: `<button class="btn">默认按钮</button>`,
};

const options2 = {
  language: "css",
  mode: "text/css",
  code: `.btn {
    color: red;
  }`,
};

const options3 = {
  language: "js",
  mode: "text/javascript",
  code: `console.log(1111);`,
};

const editorp = reactive({
  el: "editor",
  options: options,
});

const editorp2 = reactive({
  el: "editor2",
  options: options2,
});

const editorp3 = reactive({
  el: "editor3",
  options: options3,
});
</script>

<style scoped>
.vue-codemirror {
  width: 100%;
  height: 100%;
}

.mirow {
  width: 100%;
  height: 100%;
}

.micol {
  height: 100%;
}

.mitoprow {
  margin-bottom: 15px;
  height: calc(50% - 15px);
}

.mibottomrow {
  height: 50%;
}

.el-card {
  border-radius: 0px;
  border: none;
  width: 100%;
  height: 100%;
}

:deep(.el-card__body) {
  width: calc(100% - 40px);
  height: calc(100% - 98px);
}
</style>