<template>
  <div>
    <ul class="ul-style">
      <li v-for="pic in canvasList"
          v-bind:key="pic.key"
          class="canvas-list">
        <el-card class="picture-card"
                 shadow="hover"
        >
          <p>{{pic.content}}</p>
        </el-card>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data () {
    return {
      canvasList: [
        {
          content: 'picture1',
          key: 1
        },
        {
          content: 'picture2',
          key: 2
        },
        {
          content: 'picture3',
          key: 3
        },
        {
          content: 'picture4',
          key: 4
        },
        {
          content: 'picture5',
          key: 5
        },
        {
          content: 'picture6',
          key: 6
        },
      ],
    }
  }
}
</script>

<style>
  .ul-style{
    list-style: none;
  }
  .canvas-list{
    display: inline-block;
  }
  .picture-card{
    width: 250px;
    margin: 20px;
    height: 125px;
  }
</style>
