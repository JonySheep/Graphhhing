<template>
  <div class='back-ground'>
    <el-card class='canvas-container'>
      <CanvasPanel></CanvasPanel>
    </el-card>
    <div class="list-container">
      <CanvasList></CanvasList>
    </div>
  </div>
</template>

<script>
import '../styles/background.css'
import CanvasPanel from '../components/CanvasPanel.vue'
import CanvasList from '../components/CanvasList'

export default {
  name: 'GraphPage',
  components: {
    CanvasPanel,
    CanvasList
  },
  data () {
    return {
      msg: "Let's Drawing!"
    }
  }
}
</script>

<style>
  .canvas-container{
    height:620px;
    background-color: white;
  }
  .list-container {
    margin-top: 100px;
  }
</style>
