<template>
  <div class="main-container">
    <side-nav/>
    <div class="content-container">
      <top-menu/>
      <router-view/>
    </div>
  </div>
</template>

<script>
import SideNav from '@/components/SideNav.vue'
import TopMenu from '@/components/TopMenu.vue'

export default {
  name: 'HomeView',
  components: {
    SideNav,
    TopMenu
  }
}
</script>

<style scoped>
.main-container {
  display: flex;
}

.content-container {
  flex: 1; /* 让内容容器占据剩余空间 */
}
</style>
