<template>
  <div>
    <el-card class="canvas-container">
    <div class="canvas-panel-container">
      <el-row>
        <el-col :span="20" >
          <div v-on:mousedown="beginPath" style="width: 1000px; height: 500px">
            <canvas id="canvas"
                    height="500"
                    width="1000"
                    class="canvas-panel"
                    v-on:mousedown="canvasDown"
                    v-on:mouseup="canvasUp"
                    v-on:mousemove="canvasMove"
            ></canvas>
          </div>
        </el-col>
        <el-col :span="4" class="tag-col">
          <ul>
            <li v-for="tagItem in figureList"
            >
              <el-tag type="danger"
                      class="tag-style"
              >{{tagItem.shape}}</el-tag>
            </li>
          </ul>
        </el-col>
      </el-row>
    </div>
    <div class="operator">
      <el-row>
        <el-col :span="15">
          <el-button type="danger" @click="newCanvas()">Start a new pic</el-button>
        </el-col>
        <el-col :span="3">
          <p class="prompt-font">Color Picker</p>
        </el-col>
        <el-col :span="2" >
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
    </el-card>
    <div class="list-container">
      <CanvasList :refreshFlag="flag"
                  @openPic="setCounter"
      ></CanvasList>
    </div>
  </div>
</template>

<script>
  import CanvasList from '../components/CanvasList'

export default {
  data () {
    return {
      color: '#ff7c76',
      context: {},
      thisCanvas: {},
      canvasMoveUse: false, // 是否正在画图
      isStart: true, // 是否是开始画图
      isShape: true, // 是否在画几何图形过程中，判断标准是上一条线的终点与下一条线的起点重合
      pointList: [], // 点集
      lineList: [], // 线集
      startPoint: {}, // 开始点
      lastPoint: {}, // 结束点
      figureList: [], // 识别的形状集合：需要post给后台
      counter: 1,
      flag: false,
      disable: false, // 是否能在画布上绘画
    }
  },
  components: {
    CanvasList
  },
  // 渲染时调用
  mounted () {
    this.thisCanvas = document.getElementById('canvas')
    this.context = this.thisCanvas.getContext('2d')
    this.setCanvasStyle()
    this.getReadOnlyCanvas(1)
  },
  methods: {
    /**
     * 创建一个新的绘图
     */
    newCanvas () {
      this.emptyCanvas()
      let _this = this
      this.$api.get('/canvas', null, res => {
        _this.counter = res.data.canvasList.length + 1
      })
      _this.disable = false
      this.figureList = []
    },
    /**
     * 设置绘画配置
     */
    setCanvasStyle () {
      this.context.strokeStyle = this.color
    },
    setCounter (canvasId) {
      this.getReadOnlyCanvas(canvasId)
    },
    /**
     * 绘图开始
     * @param event
     */
    beginPath (event) {
      const canvas = document.querySelector('#canvas')
      if (event.target !== canvas ) {
        this.context.beginPath()
      }
    },
    /**
     * 鼠标抬起的对应action
     * @param e actionEvent
     */
    canvasUp (e) {
      const canvasX = e.offsetX - e.target.offsetLeft
      const canvasY = e.offsetY - e.target.offsetTop
      this.lastPoint = {
        x: canvasX,
        y: canvasY
      }
      console.log(this)
      this.canvasMoveUse = false
      if (!this.isStart) {
        // 不是第一条线，需判断是否能组成形状
        if (this.isShape) {
          this.lineList.push(this.pointList)
          // 判断是否可封闭
          if (this.isConnect(this.startPoint.x, this.startPoint.y, canvasX, canvasY)) {
            this.recognizeFigure()
          }
        } else {
          let tempPointList = this.pointList
          this.initData()
          this.isStart = false
          this.lineList.push(tempPointList)
          this.startPoint = {
            x: this.lineList[0][0].x,
            y: this.lineList[0][0].y
          }
        }
      } else {
        // 第一条线，直接加入线段集合
        this.lineList.push(this.pointList)
      }
    },
    /**
     * 鼠标移动对应的action
     * @param e actionEvent
     */
    canvasMove (e) {
      if (this.canvasMoveUse && !this.isStart) {
        console.log('canvasMove')
        const canvasX = e.offsetX - e.target.offsetLeft
        const canvasY = e.offsetY - e.target.offsetTop
        let point = {
          x: canvasX,
          y: canvasY
        }
        this.pointList.push(point)
        this.context.lineTo(canvasX, canvasY)
        this.context.stroke()
      }
    },
    /**
     * 鼠标按下的action
     * @param event actionEvent
     */
    canvasDown (event) {
      if(this.disable) {
        return
      }
      // client是基于整个页面的坐标
      // offset是canvas距离顶部以及左边的距离

      const canvasX = event.offsetX - event.target.offsetLeft
      const canvasY = event.offsetY - event.target.offsetTop
      console.log("canvasDown  " + canvasX +"  " + canvasY)
      if (this.isStart) {
        this.startPoint = {
          x: canvasX,
          y: canvasY
        }
        this.pointList.push(this.startPoint)
        this.isStart = false
      } else {
        // 若不是起始点，判断是否与上一条线相连
        if (this.isConnect(this.lastPoint.x, this.lastPoint.y, canvasX, canvasY)) {
          this.isShape = true
        } else {
          this.isShape = false
        }
        this.pointList = []
      }
      this.canvasMoveUse = true
      this.setCanvasStyle()
      // 清除子路径
      this.context.beginPath()
      this.context.moveTo(canvasX, canvasY)
    },
    /**
     * 判断两点是否连接
     * startX - 2 <= x <= startX + 2 则为可封闭
     */
    isConnect (stdX, stdY, x, y) {
      if (x >= stdX - 5 && x <= stdX + 5 && y >= stdY - 5 && y <= stdY + 5) {
        return true
      } else {
        return false
      }
    },
    /**
     * 识别图形的形状
     */
    recognizeFigure () {
      let sumOfLine = this.lineList.length
      let shape = ''
      switch (sumOfLine) {
        case 1 : {
          shape = 'Circle'
          break
        }
        case 2 : {
          shape = 'triangle'
          break
        }
        case 3 : {
          shape = 'rectangle'
          break
        }
        case 4 : {
          shape = 'pentagon'
          break
        }
        default : {
          shape = ''
        }
      }
      // stroke text
      this.context.font = '20px Chalk'
      this.context.strokeText(shape, this.startPoint.x, this.startPoint.y)
      const figure = {
        shape: shape,
        textPoint: this.startPoint,
        color: this.color
      }
      this.figureList.push(figure)
      // clear data
      this.initData()
    },
    /**
     * 保存该图画
     */
    saveCanvas () {
      let canvasUrl = this.thisCanvas.toDataURL(this.counter + '/png', 1.0)

      let canvasFile = this.convertBase64UrlToBlob(canvasUrl, 'png')
      this.postCanvas(canvasFile)
    },
    /**
     * 将base64的url转换为blob的方法
     */
    convertBase64UrlToBlob (urlData, type) {
      // 去掉url的头，并转换为byte
      let bytes = window.atob(urlData.split(',')[1])
      // 处理异常,将ascii码小于0的转换为大于0
      let ab = new ArrayBuffer(bytes.length)
      let ia = new Uint8Array(ab)
      for (let i = 0; i < bytes.length; i++) {
        ia[i] = bytes.charCodeAt(i)
      }
      return new Blob([ab], {type: (this.counter) + '.' + type})
    },
    /**
     * 向后端发送数据
     */
    postCanvas (canvasFile) {
      let form = new FormData()
      form.append('canvas', canvasFile)
      let figureListObject = {
        figureList: this.figureList
      }
      form.append('figures', JSON.stringify(figureListObject))

      let _this = this
      this.$api.post('canvas/' + this.counter, form, res => {
        if (res.success) {
          _this.flag = true
          _this.$message('保存成功')
        }
      })

    },
    /**
     * 清空画布
     */
    emptyCanvas () {
      this.context.clearRect(0, 0, 1000, 500)
      this.initData()
      this.figureList = []
    },
    /**
     * 重新初始化数据
     */
    initData () {
      // clear current Data
      this.pointList = []
      this.lineList = []
      this.isStart = true
      this.startPoint = {}
      this.flag = false
    },
    /**
     * 读取图片和标签进行查看
     */
    getReadOnlyCanvas (counter) {
      let _this = this
      this.$api.get('/canvas/' + counter, null, res => {
        let img = new Image()
        img.src = res.data.canvasUrl
        _this.emptyCanvas()
        _this.context.drawImage(img, 0, 0, 1000, 500)
        _this.figureList = res.data.figureList
        _this.disable = true
      })
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
  .tag-style{
    margin: 5px;
  }

  .list-container {
    margin-top: 100px;
  }
  .canvas-container{
    height:620px;
    background-color: white;
  }
</style>
