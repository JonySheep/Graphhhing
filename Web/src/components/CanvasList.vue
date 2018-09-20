<template>
  <div>
    <ul">
      <li v-for="pic in canvasList"
          v-bind:key="pic.key">
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
        }
      ]
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
  }
  .picture-card{
    width: 250px;
    margin: 20px;
    height: 125px;
  }
</style>
