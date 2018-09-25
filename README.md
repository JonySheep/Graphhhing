# Graphhhing软件构造实践作业说明
### 1.启动方式：

开启前端：使用命令行进入 Web文件夹，输入

```
npm install
npm run dev
```

开启后端：打开IDE，使用Gradle工具boot run打开spring boot

### 2.代码结构：

#### 前端代码（/Web）：

| 文件名                          | 内容               |
| ------------------------------- | ------------------ |
| index.html                      | 主html界面         |
| /src/main.js                    | 入口js函数         |
| /src/App.vue                    | 主vue组件          |
| /src/pages/GraphPage.vue        | 主页面             |
| /src/components/CanvasList.vue  | 展示所有图片的组件 |
| /src/components/CanvasPanel.vue | 画板组件           |

#### 后端代码（/src/main/java）：

| 文件名                      |                    |
| --------------------------- | ------------------ |
| /Controller/GraphController | controller接口类   |
| /Data/CanvasSvc             | 数据处理接口       |
| /Data/CanvasSvcImpl         | 数据处理接口实现类 |
| /Entity/Canvas              | 一张绘图实体类     |
| /Entity/CanvasList          | 绘图列表实体类     |
| /Entity/Figure              | 一个几何图形实体类 |
| /Entity/FigureList          | 几何图形列表实体类 |
| /Entity/Point               | 坐标点实体类       |
| /util/ResultMessageEnum     | 处理结果枚举类     |

### 3. 使用说明:

1. 可判断形状的条件：
   1. 图形闭合
   2. 一笔为圆形，二笔为三角形，三笔为四边形，四笔为五边形

2. 若超过五笔未闭合，则判断为非图形，不会显示标签



![IMG_0436](https://raw.githubusercontent.com/JonySheep/Graphhhing/master/IMG_0436.JPG)

