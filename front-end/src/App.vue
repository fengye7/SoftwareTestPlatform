
<template>
  <div class="main-container">
    <side-nav />
    <div class="content-container">
      <top-menu />
      <main-content-view/>
    </div>
  </div>
</template>

<script setup>
import SideNav from "@/components/SideNav.vue";
import TopMenu from "@/components/TopMenu.vue";
import MainContentView from "@/views/MainContentView.vue";
</script>


<style scoped>
.main-container {
  display: flex;
}

.content-container {
  flex: 1; /* 让内容容器占据剩余空间 */
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
} 
</style>
