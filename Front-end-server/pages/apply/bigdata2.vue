<template>
  <view class="charts-box">
    <qiun-data-charts 
      type="area"
      :opts="opts"
      :chartData="chartData"
    />
  </view>
</template>

<script>
export default {
  data() {
    return {
      chartData: {},
      //您可以通过修改 config-ucharts.js 文件中下标为 ['area'] 的节点来配置全局默认参数，如都是默认参数，此处可以不传 opts 。实际应用过程中 opts 只需传入与全局默认参数中不一致的【某一个属性】即可实现同类型的图表显示不同的样式，达到页面简洁的需求。
      opts: {
        color: ["#1890FF","#91CB74","#FAC858","#EE6666","#73C0DE","#3CA272","#FC8452","#9A60B4","#ea7ccc"],
        padding: [15,15,0,15],
        enableScroll: false,
        legend: {},
        xAxis: {
          disableGrid: true
        },
        yAxis: {
          gridType: "dash",
          dashLength: 2
        },
        extra: {
          area: {
            type: "curve",
            opacity: 0.3,
            addLine: true,
            width: 2,
            gradient: false,
            activeType: "hollow"
          }
        }
      }
    };
  },
  mounted() {
    this.getServerData();
  },
  methods: {
    getServerData() {
      //模拟从服务器获取数据时的延时
	  let month = new Date().getMonth() + 1;
      setTimeout(() => {
        //模拟服务器返回数据，如果数据格式和标准格式不同，需自行按下面的格式拼接
        let res = {
            categories: ["饮食","休闲","购物","教育","医疗","出行"],
            series: [
              {
                name: month+'月',
                data: [35,8,25,37,4,20]
              },
              {
                name: (month-1)+'月',
                data: [70,40,65,100,44,68]
              },
			   {
				  name: (month-2)+'月',
				  data: [100,80,95,150,112,132]
				}
            ]
          };
        this.chartData = JSON.parse(JSON.stringify(res));
      }, 500);
    },
  }
};
</script>

<style scoped lang="scss">
  /* 请根据实际需求修改父元素尺寸，组件自动识别宽高 */
  .charts-box {
    width: 100%;
    height: 300px;
  }
</style>