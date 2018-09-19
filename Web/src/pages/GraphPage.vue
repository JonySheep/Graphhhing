<template>
  <div class="back-ground">
    <el-card class="canvas-container">

    </el-card>
  </div>
</template>

<script>
import '../styles/background.css'

export default {
  name: 'GraphPage',
  data () {
    return {
      msg: "Let's Drawing!"
    }
  }
}
</script>

<style>
  .canvas-container{
    height:600px;
    width: 100%;
    background-color: white;
  }
</style>
