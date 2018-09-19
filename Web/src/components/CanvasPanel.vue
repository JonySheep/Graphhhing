<template>
  <div>
    <div class="canvas-panel-container">
      <el-row>
        <el-col :span="20" v-on:mousedown="beginPath">
          <canvas id="canvas"
                  height="500"
                  width="1000"
                  class="canvas-panel"
                  v-on:mousedown="canvasDown"
                  v-on:mouseup="canvasUp"
                  v-on:mousemove="canvasMove"
          ></canvas>
        </el-col>
        <el-col :span="4" class="tag-col">
        </el-col>
      </el-row>
    </div>
    <div class="operator">
      <el-row>
        <el-col :span="3">
          <p class="prompt-font">Color Picker</p>
        </el-col>
        <el-col :span="17" >
          <el-color-picker v-model="color"></el-color-picker>
        </el-col>
        <el-col :span="2">
          <el-button type="danger" @click="saveCanvas()">Save</el-button>
        </el-col>
        <el-col :span="2">
          <el-button @click="emptyCanvas()">Clear</el-button>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      color: '#ff7c76',
      context: {},
      thisCanvas: {},
      canvasMoveUse: false, // 是否正在画图
      isStart: false // 是否是开始画图
    }
  },
  // 渲染时调用
  mounted () {
    this.thisCanvas = document.getElementById('canvas')
    this.context = this.thisCanvas.getContext('2d')
    this.setCanvasStyle()
  },
  methods: {
    /**
     * 设置绘画配置
     */
    setCanvasStyle () {
      this.context.strokeStyle = this.color
    },
    /**
     * 绘图开始
     * @param event
     */
    beginPath (event) {
      const canvas = document.querySelector('#canvas')
      if (event.target !== canvas) {
        this.context.beginPath()
      }
    },
    /**
     * 鼠标抬起的对应action
     * @param e actionEvent
     */
    canvasUp (e) {
      this.canvasMoveUse = false
    },
    /**
     * 鼠标移动对应的action
     * @param e actionEvent
     */
    canvasMove (e) {
      if (this.canvasMoveUse && !this.isStart) {
        console.log('canvasMove')
        let canvasX = e.offsetX - e.target.offsetLeft
        let canvasY = e.offsetY - e.target.offsetTop
        this.context.lineTo(canvasX, canvasY)
        this.context.stroke()
      }
    },
    /**
     * 鼠标按下的action
     * @param event actionEvent
     */
    canvasDown (event) {
      if (this.isStart) {
        this.isStart = false
      }
      this.canvasMoveUse = true
      // client是基于整个页面的坐标
      // offset是canvas距离顶部以及左边的距离
      const canvasX = event.offsetX - event.target.offsetLeft
      const canvasY = event.offsetY - event.target.offsetTop
      this.setCanvasStyle()
      // 清除子路径
      this.context.beginPath()
      this.context.moveTo(canvasX, canvasY)
    },
    /**
     * 保存该图画
     */
    saveCanvas () {
    },
    /**
     * 清空画布
     */
    emptyCanvas () {
      this.context.clearRect(0, 0, 1000, 500)
    }
  }
}
</script>

<style>
  .canvas-panel-container{
    height: 500px;
    width: 100%;
    border-bottom: coral 3px solid;
    padding-bottom:20px;
  }
  .operator{
    margin-top: 15px;
    margin-left: 20px;
  }
  .prompt-font{
    font-size: 20px;
    margin: 10px;
  }
  .canvas-panel{
    border-right: coral 1px solid;
    position: absolute;
    cursor: crosshair;
  }
</style>
