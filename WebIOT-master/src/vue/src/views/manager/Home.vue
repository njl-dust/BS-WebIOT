<template>
  <div>

    <div class="card" style="line-height: 30px">
      <div>お帰りなさいー<span style="color:blue;font-weight: bold">{{ user.name }}</span> ！</div>
    </div>

  </div>
</template>

<script setup>
  import request from "@/utils/request";
  const user = JSON.parse(localStorage.getItem('student-user') || '{}')
</script>