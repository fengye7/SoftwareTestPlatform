<template>
  <div class="not-found-container">
    <div class="not-found-content">
      <h1>404 - Page Not Found</h1>
      <p><strong> Maybe you haven't created a project yet. </strong><br>Please set up a project first.</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'NotFound'
}
</script>

<style scoped>
.not-found-container {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 95vh;
  background-color: #f5f5f5;
  background-image: url('@/assets/imgs/404-not-found.png');
  background-size: cover;
  background-position: center;
  text-align: center;
}

.not-found-content {
  background-color: rgba(255, 255, 255, 0.8);
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.not-found-content h1 {
  font-size: 2.5em;
  color: #d9534f;
  margin-bottom: 20px;
}

.not-found-content p {
  font-size: 1.2em;
  color: #666;
}
</style>
