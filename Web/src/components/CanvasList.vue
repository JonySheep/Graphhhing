<template>
  <div>
    <ul>
      <li v-for="pic in canvasList"
          v-bind:key="pic.canvasId">
        <el-card class="picture-card"
                 shadow="hover"
        >
          <img :src="pic.canvasUrl" width="220" height="110"/>
          <el-button type="text"
                     style="margin: 0px"
                     @click="openPic(pic.canvasId)"
          >Picture {{pic.canvasId}}</el-button>
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
    /**
     * 得到图片列表
     */
    getCanvasList() {
      let _this = this;
      this.$api.get('canvas', null, res => {
        _this.canvasList = res.data.canvasList
      })
    },

    /**
     * 用来实时刷新，实现保存后实时展示
     */
    checkFlag() {
      if (this.refreshFlag) {
        this.getCanvasList()
      }
    },

    /**
     * 向父组件传递消息，展示一张图片
     * @param id
     */
    openPic (id) {
      this.$emit('openPic', id)
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
    text-align: center;
    padding: 0px;
  }
</style>
