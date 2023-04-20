<template>
	<view class="box">
		
		<view class="" style="color: white;display: flex;font-size: 18px;">
			<text class="cuIcon-back" style="color: white;"></text>
			<text style="margin: 0 auto;">大数据</text>
		</view>
		
		<view class="" style="display: flex;margin-top: 40px;">
			<view class="" style="color: white;display: flex;font-size: 25px;margin-left: 15px;">
				<text class="cuIcon-rankfill" style="font-size: 28px;"></text>
				<text class="" style="margin-left: 10px;font-weight: 800;">
					大数据统计
				</text>
			</view>
			<image :src="userinfo.avatarUrl" mode="" style="height: 45px;width: 45px;border-radius: 50%;margin-left: auto;margin-right: 15px;border: 1px white solid;">
			</image>
		</view>
		
		<view class="" style="display: flex;flex-direction: column;margin-left: 15px;margin-top: 15px;">
			<view class="" style="color: lightgray;">
				上月总支出
			</view>
			<view class="" style="color: white;font-size: 28px;margin-top: 10px;font-weight: 700;">
				¥{{(2800).toFixed(2)}}
			</view>
		</view>
		
		<view class="" style="margin-top: 20px;">
			<scroll-view scroll-x="true" style="height: 145px;width: 100%;white-space: nowrap;">
						<view class="middleBox" v-for="(item,index) in [0,1,2,3,4,5]">
							<view class="" style="white-space: normal;">
								<view class="" style="display: flex;">
									<image :src="require(`../../static/apply/report/0${index+1}.png`)" mode="" style="height: 20px;width: 20px;"></image>
									<text class="" style="margin-left: 10px;">
										{{Item[index]}}
									</text>	
								</view>
								超过
								<text class="" style="font-size: 24px;color: steelblue;font-weight: 700;">
									{{num[index]}}%
								</text>
							</view>
							<view class="" style="height: 110px;margin-top: -10px;">
								<qiun-data-charts 
									  type="ring"
									  :opts="opts[index]"
									  :chartData="chartData[index]"
									/>
							</view>
						</view>	
			</scroll-view>
		</view>
		
		<bigdata2 style="margin-top: 20px;"></bigdata2>
		
	</view>
</template>

<script>
	import bigdata2 from "../apply/bigdata2.vue"
	let opt = {
				rotate: false,
				rotateLock: false,
				color: ["#1890FF","#EEEEEE"],
				padding: [0,0,0,0],
				enableScroll: false,
				dataLabel:false,
				legend: {
				  show: false,
				  position: "right",
				  lineHeight: 25
				},
				title: {
				  name: "",
				  fontSize: 10,
				  color: "#666666"
				},
				subtitle: {
				  name: "",
				  fontSize: 10,
				  color: "#7cb5ec"
				},
				extra: {
				  ring: {
					ringWidth: 8,
					customRadius:30,
					activeOpacity: 0.5,
					activeRadius: 10,
					offsetAngle: 0,
					labelWidth: 5,
					// labelText:'',
					border: false,
					borderColor: "#FFFFFF",
					offsetAngle:-90
				  }
				}
		}
	export default {
		components:{
			bigdata2
		},
		data() {
			return {
				Item:['收入','饮食支出','休闲支出','购物支出','教育支出','医疗支出'],
				
				num:[56,47,85,63,47,34],
				color:['#1784FC','#FCCA00','#83B53A','#EC5A5A','#9758F9','#F69726'],
				opts:[
					{
						rotate: false,
						rotateLock: false,
						color: ["#1890FF","#EEEEEE"],
						padding: [0,0,0,0],
						enableScroll: false,
						dataLabel:false,
						legend: {
						  show: false,
						  position: "right",
						  lineHeight: 25
						},
						title: {
						  name: "",
						  fontSize: 10,
						  color: "#666666"
						},
						subtitle: {
						  name: "",
						  fontSize: 10,
						  color: "#7cb5ec"
						},
						extra: {
						  ring: {
							ringWidth: 15,
							customRadius:35,
							activeOpacity: 0.5,
							activeRadius: 10,
							offsetAngle: 0,
							labelWidth: 5,
							// labelText:'',
							border: false,
							borderColor: "#FFFFFF",
							offsetAngle:-90
						  }
						}
					},
						{
							rotate: false,
							rotateLock: false,
							color: ["#1890FF","#EEEEEE"],
							padding: [0,0,0,0],
							enableScroll: false,
							dataLabel:false,
							legend: {
							  show: false,
							  position: "right",
							  lineHeight: 25
							},
							title: {
							  name: "",
							  fontSize: 10,
							  color: "#666666"
							},
							subtitle: {
							  name: "",
							  fontSize: 10,
							  color: "#7cb5ec"
							},
							extra: {
							  ring: {
								ringWidth: 15,
								customRadius:35,
								activeOpacity: 0.5,
								activeRadius: 10,
								offsetAngle: 0,
								labelWidth: 5,
								// labelText:'',
								border: false,
								borderColor: "#FFFFFF",
								offsetAngle:-90
							  }
							}
						},
						{
							rotate: false,
							rotateLock: false,
							color: ["#1890FF","#EEEEEE"],
							padding: [0,0,0,0],
							enableScroll: false,
							dataLabel:false,
							legend: {
							  show: false,
							  position: "right",
							  lineHeight: 25
							},
							title: {
							  name: "",
							  fontSize: 10,
							  color: "#666666"
							},
							subtitle: {
							  name: "",
							  fontSize: 10,
							  color: "#7cb5ec"
							},
							extra: {
							  ring: {
								ringWidth: 15,
								customRadius:35,
								activeOpacity: 0.5,
								activeRadius: 10,
								offsetAngle: 0,
								labelWidth: 5,
								// labelText:'',
								border: false,
								borderColor: "#FFFFFF",
								offsetAngle:-90
							  }
							}
						},
						{
							rotate: false,
							rotateLock: false,
							color: ["#1890FF","#EEEEEE"],
							padding: [0,0,0,0],
							enableScroll: false,
							dataLabel:false,
							legend: {
							  show: false,
							  position: "right",
							  lineHeight: 25
							},
							title: {
							  name: "",
							  fontSize: 10,
							  color: "#666666"
							},
							subtitle: {
							  name: "",
							  fontSize: 10,
							  color: "#7cb5ec"
							},
							extra: {
							  ring: {
								ringWidth: 15,
								customRadius:35,
								activeOpacity: 0.5,
								activeRadius: 10,
								offsetAngle: 0,
								labelWidth: 5,
								// labelText:'',
								border: false,
								borderColor: "#FFFFFF",
								offsetAngle:-90
							  }
							}
						},
						{
							rotate: false,
							rotateLock: false,
							color: ["#1890FF","#EEEEEE"],
							padding: [0,0,0,0],
							enableScroll: false,
							dataLabel:false,
							legend: {
							  show: false,
							  position: "right",
							  lineHeight: 25
							},
							title: {
							  name: "",
							  fontSize: 10,
							  color: "#666666"
							},
							subtitle: {
							  name: "",
							  fontSize: 10,
							  color: "#7cb5ec"
							},
							extra: {
							  ring: {
								ringWidth: 15,
								customRadius:35,
								activeOpacity: 0.5,
								activeRadius: 10,
								offsetAngle: 0,
								labelWidth: 5,
								// labelText:'',
								border: false,
								borderColor: "#FFFFFF",
								offsetAngle:-90
							  }
							}
						},
						{
							rotate: false,
							rotateLock: false,
							color: ["#1890FF","#EEEEEE"],
							padding: [0,0,0,0],
							enableScroll: false,
							dataLabel:false,
							legend: {
							  show: false,
							  position: "right",
							  lineHeight: 25
							},
							title: {
							  name: "",
							  fontSize: 10,
							  color: "#666666"
							},
							subtitle: {
							  name: "",
							  fontSize: 10,
							  color: "#7cb5ec"
							},
							extra: {
							  ring: {
								ringWidth: 15,
								customRadius:35,
								activeOpacity: 0.5,
								activeRadius: 10,
								offsetAngle: 0,
								labelWidth: 5,
								// labelText:'',
								border: false,
								borderColor: "#FFFFFF",
								offsetAngle:-90
							  }
							}
						}
				],
				chartData:[
					{
						"series": [
							{
								"data":[{"name":"","value":20},{"name":"","value":80}]
							}
						]
					},
					{
						"series": [
							{
								"data":[{"name":"","value":20},{"name":"","value":80}]
							}
						]
					},
					{
						"series": [
							{
								"data":[{"name":"","value":20},{"name":"","value":80}]
							}
						]
					},
					{
						"series": [
							{
								"data":[{"name":"","value":20},{"name":"","value":80}]
							}
						]
					},
					{
						"series": [
							{
								"data":[{"name":"","value":20},{"name":"","value":80}]
							}
						]
					},
					{
						"series": [
							{
								"data":[{"name":"","value":20},{"name":"","value":80}]
							}
						]
					}
				]
			}
		},
		computed:{
			userinfo(){
				return this.$store.state.user.info
			},
			userIsLogin(){
				return this.$store.state.user.isLogin
			}
			
		},
		
		onShow() {
			for(let i=0;i<6;i++){
				this.chartData[i].series =[{
					"data":[{"name":"","value":this.num[i]},{"name":"","value":100-this.num[i]}]
				}]	
				this.opts[i].color[0] = this.color[i]
			}
		},
		methods: {
			
		}
	}
</script>

<style lang="scss">
.box{
	min-height: 100vh;
	padding-top: 40px;
	padding-bottom: 60px;
	background-color: #F8F8F8;
	background-image: url('../../static/picture/bigdata3.png');
	background-size:cover;
	background-repeat: no-repeat;
	background-attachment: fixed;
}

.middleBox{
	background-color: white;
	height: 100%;
	width: 120px;
	// border: 1px solid blue;
	display: inline-block;
	border-radius: 18px;
	margin-right: 5px;
	margin-left: 10px;
	padding: 10px;
}
</style>
