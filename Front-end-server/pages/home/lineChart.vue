<template>
  <view>
	  123
    <canvas canvas-id="TwHJcxleUViSrqHzFuIUIppIaUSEEZEw" id="TwHJcxleUViSrqHzFuIUIppIaUSEEZEw" class="charts" @touchend="tap"/>
  </view>
</template>

<script>
import uCharts from '@/uni_modules/qiun-data-charts/js_sdk/u-charts/u-charts.js';
var uChartsInstance = {};
export default {
  name:"lineChart",
  data() {
    return {
      cWidth: 750,
      cHeight: 500
    };
  },
  onReady() {
    //这里的 750 对应 css .charts 的 width
    this.cWidth = uni.upx2px(750);
    //这里的 500 对应 css .charts 的 height
    this.cHeight = uni.upx2px(500);
    this.getServerData();
  },
  methods: {
    getServerData() {
      //模拟从服务器获取数据时的延时
      setTimeout(() => {
        //模拟服务器返回数据，如果数据格式和标准格式不同，需自行按下面的格式拼接
        let res = {
            categories: ["2016","2017","2018","2019","2020","2021"],
            series: [
              {
                name: "成交量A",
                data: [35,8,25,37,4,20]
              },
              {
                name: "成交量B",
                data: [70,40,65,100,44,68]
              },
              {
                name: "成交量C",
                data: [100,80,95,150,112,132]
              }
            ]
          };
        this.drawCharts('TwHJcxleUViSrqHzFuIUIppIaUSEEZEw', res);
      }, 500);
    },
    drawCharts(id,data){
      const ctx = uni.createCanvasContext(id, this);
      uChartsInstance[id] = new uCharts({
        type: "line",
        context: ctx,
        width: this.cWidth,
        height: this.cHeight,
        categories: data.categories,
        series: data.series,
        animation: true,
        background: "#FFFFFF",
        color: ["#1890FF","#91CB74","#FAC858","#EE6666","#73C0DE","#3CA272","#FC8452","#9A60B4","#ea7ccc"],
        padding: [15,10,0,15],
        legend: {},
        xAxis: {
          disableGrid: true
        },
        yAxis: {
          gridType: "dash",
          dashLength: 2
        },
        extra: {
          line: {
            type: "straight",
            width: 2,
            activeType: "hollow"
          }
        }
      });
    },
    tap(e){
      uChartsInstance[e.target.id].touchLegend(e);
      uChartsInstance[e.target.id].showToolTip(e);
    }
  }
};
</script>

<style scoped>
  .charts{
    width: 500rpx;
    height: 500rpx;
  }
</style>