<template>
  <div>
    <el-card>
      <template #header>
        <h3>测试集成excel模块</h3>
      </template>
      <div class="sheet-container">
        <div id="sheet-online-container" class="my-excel"></div>
      </div>
    </el-card>
  </div>
</template>

<script>
import Spreadsheet from "x-data-spreadsheet";
import zhCN from "x-data-spreadsheet/src/locale/zh-cn";

export default {
  name: "ExcelOnline",
  data() {
    return {
      sheet: null,
      options: {
        mode: "edit",
        showToolbar: true,
        showGrid: true,
        showContextmenu: true,
        view: {
          height: () =>
            document.getElementById("sheet-online-container").clientHeight,
          width: () =>
            document.getElementById("sheet-online-container").clientWidth,
        },
        row: {
          len: 100,
          height: 25,
        },
        col: {
          len: 26,
          width: 100,
          indexWidth: 60,
          minWidth: 60,
        },
        style: {
          bgcolor: "#ffffff",
          align: "left",
          valign: "middle",
          textwrap: false,
          strike: false,
          underline: false,
          color: "#0a0a0a",
          font: {
            name: "Helvetica",
            size: 10,
            bold: false,
            italic: false,
          },
        },
      },
    };
  },
  methods: {
    InitSheet() {
      const sheetContainer = document.getElementById("sheet-online-container");
      if (!sheetContainer) {
        console.error("Element with id 'sheet-online-container' not found");
        return;
      }

      Spreadsheet.locale("zh-cn", zhCN);
      this.sheet = new Spreadsheet(sheetContainer, this.options);
    },
  },
  mounted() {
    this.$nextTick(() => {
      // 初始化表格
      this.InitSheet();
    });
  },
};
</script>

<style scoped>
.my-excel {
  width: 100vh;
  height: 70vh;
}
</style>