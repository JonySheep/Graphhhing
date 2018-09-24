<template>
  <div class='back-ground'>
      <CanvasPanel></CanvasPanel>
  </div>
</template>

<script>
import '../styles/background.css'
import CanvasPanel from '../components/CanvasPanel.vue'

export default {
  name: 'GraphPage',
  components: {
    CanvasPanel
  },
  data () {
    return {
      msg: "Let's Drawing!"
    }
  }
}
</script>
