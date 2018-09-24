<template>
  <div>
    <ul>
      <li v-for="pic in canvasList"
          v-bind:key="pic.canvasId">
        <el-card class="picture-card"
                 shadow="hover"
        >
          <img :src="pic.canvasUrl" width="250" height="125"/>
        </el-card>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data () {
    return {
      canvasList: []
    }
  },

  props: ['refreshFlag'],

  mounted() {
    this.getCanvasList()
    window.setInterval(this.checkFlag, 1000)
  },

  methods: {
    getCanvasList() {
      let _this = this;
      this.$api.get('canvas', null, res => {
        _this.canvasList = res.data.canvasList
      })
    },

    checkFlag() {
      if (this.refreshFlag) {
        this.getCanvasList()
      }
    }
  }
}
</script>

<style>
  ul{
    list-style: none;
  }
  li{
    display: inline-block;
    padding: 0px;
  }
  .picture-card{
    width: 250px;
    margin: 20px;
  }
</style>
