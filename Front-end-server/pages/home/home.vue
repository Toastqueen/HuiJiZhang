<template>
	<view class="box">
		<view class="" style="height: 120px;background-color: #B6DFF8;display: flex;flex-direction: column;">
			<view class="head_box_1">
				<text class="cuIcon-text"></text>
				{{accountName}}
				<text class="cuIcon-right"></text>
			</view>
			<view style="display: flex;margin-top: 14px;font-size: 18px;font-weight: 500;align-items: center;">
				<view :class="chooseMonth?'active_choose':''" @tap="chooseMonth=true" style="margin-left: 6px;">选择月份</view>
				<view :class="chooseMonth?'':'active_choose'" @tap="chooseMonth=false" style="margin-left: 6px;">选择年份</view>
				<view class="text-gray" v-if="chooseMonth==true" style="margin-left: 6px;">
					<picker mode="date" :value="monthTime" start="2021-01" end="2050-09" @change="bindDateChange" fields="month">
						{{monthTime}}
					</picker>
				</view>	
				<view class="text-gray" v-else style="margin-left: 6px;">
					<picker mode="date" @change="bindYearChange" fields="year">
						{{yearTime}}
					</picker>
				</view>	
			</view>
		</view>
		<view class="lineChart" style="display: flex;flex-direction: column;">
			<view class="">
				<radio-group style="display: flex;margin-top: 16px;padding-right: 16px;" @change="changeShow">
					<label class="margin-left-sm">
						<radio class="radio" value="" checked style="transform: scale(0.7);" value="1"></radio>
						<text class="">支出</text>
					</label>
					<label class="" style="margin-left: auto;">
						<radio class="radio" value="" style="transform: scale(0.7);" value="0"></radio>
						<text class="">收入</text>
					</label>
				</radio-group>	
			</view>
			
			<view class="charts-box" style="height: 500rpx;" v-if="showPaid==true">
				<qiun-data-charts type="line"
					:ontouch="true"
					:opts="{dataLabel:false,enableScroll:true,xAxis:{itemCount:15,labelCount:8,scrollShow:true},
						yAxis:{disableGrid:false,data:[{disabled:false}]}}"
					:chartData="historyData" />
			</view>	
			
			<view class="charts-box" style="height: 500rpx;" v-if="showPaid==false">
				<qiun-data-charts type="column"
					:ontouch="true"
					:opts="opts"
					:chartData="columnData" />
			</view>	
			
		</view>
		
		<view class="lineChart" style="display: flex;flex-direction: column;">
			<view class="" v-show="showPaid==true" style="margin: 0 auto;">
				<text style="color: royalblue;" v-show="smallClass" @click="smallClass=false,ListenAPI">轻点切换为大类</text>
				<text style="color: royalblue;" v-show="!smallClass" @click="smallClass=true,ListenAPI">轻点切换为小类</text>
			</view>
			
			<view class="charts-box">
				<qiun-data-charts type="pie"
					:opts="{update:true,padding:[15,0,4,0],legend:{position: 'bottom'},title:{name: expendCount},subtitle: {name: '',fontSize:'16'},
					extra:{
						pie:{
							border: false
						}
					}
					}"
					:chartData="expendDetail" />
			</view>	
		</view>
		
		<!-- <cusTab class="bottom-box"></cusTab> -->
	</view>
</template>
<script>
	// import cusTab from '../../components/cusTab.vue';
	import the_data from "../../static/json/finance/1.json";
	let _now = new Date();
	let now_time = {};
	now_time.year = _now.getFullYear()
	now_time.month = _now.getMonth() + 1
	now_time.day = _now.getDate()
	
	const Mycolors = ['#FF5733', '#C70039', '#16A085', '#1E90FF', '#FFD700', '#FFA500', '#FF6347',
	'#845EC2','#2C7174','#99EAEE','#E8DBA2','#CA7B3E','#CA3EA7','#D49193',
	'#5087EC','#68BBC4','#58A55C','#F2BD42','#EE752F','#D95040'
	];
	
	if(now_time.month<10){
		now_time.month = '0'+now_time.month
	}
	
	export default{
		// components:{
		// 	cusTab
		// },
		data(){
			return {
				tmp:0,
				smallClass:true,
				newArrs:[],//时间排序
				payArrs:[],//每个单位的消费数
				showPaid:true,
				chooseMonth:true,
				show:false,
				single: '',
				value1:Number(new Date()),
				monthTime:now_time.year+'-'+now_time.month,
				yearTime:now_time.year,
				nowTime: {
					year: now_time.year,
					month: now_time.month,
					day: now_time.day
				},
				myWallet: {
					remaining: 3000.34,
					expend: 5240.32,
					income: 8240.66
				},
				expendCount:0,
				historyBtn:[
					{
						name: "支出",
						state:1
					},
					{
						name: "收入",
						state:0
					}
				],
				historyData:the_data[0],
				expendDetail:{
					"series": [
						{
							"data":[]
						}
					]
				},
				opts: {
					color: ["#1890FF","#91CB74","#FAC858","#EE6666","#73C0DE","#3CA272","#FC8452","#9A60B4","#ea7ccc"],
					padding: [15,15,0,5],
					enableScroll: false,
					legend: {},
					xAxis: {
					  disableGrid: true
					},
					yAxis: {
					  data: [
						{
						  min: 0
						}
					  ]
					},
					extra: {
					  column: {
						type: "group",
						width: 30,
						activeBgColor: "#000000",
						activeBgOpacity: 0.08
					  }
					}
				},
				
				columnData:{
					"categories": ['收入'],
					"series": []
				}
				
			}
		},
		mounted() {
			// this.tmpFunction()
			this.changeLineChart()
		},
		updated() {
			this.changeLineChart()
		},
		computed:{
			accountName(){
				return this.$store.state.user.accountName
			}
		},
		methods:{
			tmpFunction(){
				this.tmp = 1;
				this.$nextTick(()=>{
					this.tmp =0;
					let newArr= [];
					let oldData=this.$store.state.account.item;
					let userinfo = this.$store.state.user.info
					console.log("老数组:",oldData)		
					uni.request({
						url:'http://1.15.179.24:9520/record/getMoneyRecordList?userId='+userinfo.userId,
						method:'GET',
						header:{
							Authorization:userinfo.token
						},
						success: (res) => {
							console.log(res)
							if(res.data["code"]==200){
								let resdata = res.data["data"]
								oldData = resdata["list"]
								console.log("由查询得到的list数组:",oldData)
								
								oldData.forEach((item,i)=>{
									let index=-1;
									let isExists=newArr.some((newItem,j)=>{
										// console.log(item.createTime)
										if(item.createTime.split(" ")[0] === newItem.createTime.split(" ")[0]){
											index = j;
											return true;
										}
									})
									if(!isExists){
										newArr.push({
											// fullDate:item.fullDate,
											timeSeconds:Date.parse(item.createTime),
											year:new Date(item.createTime).getFullYear(),
											month:new Date(item.createTime).getMonth()+1,
											date:new Date(item.createTime).getDate(),
											day:new Date(item.createTime).getDay(),
											createTime:item.createTime,
											payMoney:(item.isPayment==true)?item.money:0,
											gainMoney:(item.isPayment==false)?item.money:0,
											subList: [item]
										})
									}else{
										newArr[index].subList.push(item);
										if(item.isPayment==true){
											newArr[index].payMoney = Number(newArr[index].payMoney) + Number(item.money)
										}
										else{
											newArr[index].gainMoney = Number(newArr[index].gainMoney) + Number(item.money)
										}
									}
								})
								console.log("由老数组得到的新数组：",newArr)
								this.newArrs = newArr
							}
						},
					})
					this.ListenAPI()
				})
			},
			
			changeShow(e){
				console.log(e.detail.value)
				if(e.detail.value==1){
					//支出
					console.log("展示支出")
					this.showPaid = true
				}else{
					console.log("展示收入")
					this.showPaid = false
					
				}
			},
			
			bindDateChange: function(e) {
				this.monthTime = e.detail.value
				this.changeLineChart()
			},
			bindYearChange:function(e){
				this.yearTime = e.detail.value	
				this.changeLineChart()
			},
			changeState(idx){
				console.log("改变选择",idx)
				console.log(the_data[idx])
				this.historyBtn[idx].state=1
				for(let i=0;i<this.historyBtn.length;i++){
					if(i!=idx){
						this.historyBtn[i].state=0
					}
				}
				this.historyData = the_data[idx]
			},
			
			ListenAPI(){
				let userinfo = this.$store.state.user.info
				
				this.smalldata = []
				this.bigdata = []
				
				let myUrl = 'http://1.15.179.24:9520/record/paymentStatisticDetailOfType?userId='
				
				
				let monthTime = this.monthTime
				// console.log(userinfo.userId)
				let yearTime = this.yearTime
				let startTime = ''
				let endTime = ''
				if(this.chooseMonth == true){
					startTime = monthTime.replace('-','/') + "/01"
					endTime = monthTime.replace('-','/') + "/31"
				}else{
					startTime = yearTime+"/01/01"
					endTime = yearTime+"/12/31"
				}
				uni.request({
					url:myUrl+userinfo.userId+'&startTime='+startTime+'&endTime='+endTime,
					method:'GET',
					header:{
						Authorization:userinfo.token
					},
					success: (res) => {
							console.log("查询指定时间范围的小类型支出")
							console.log(res.data.data)
							let resdata = res.data.data
							this.smalldata = []
							resdata.forEach((item,i)=>{
								this.smalldata.push({
									"name": item.typeName,
									"value": item.money,
									"color":Mycolors[Math.floor(Math.random()*Mycolors.length)]
								})
							})
							if(this.smallClass){
								this.expendDetail.series[0].data = this.smalldata
							}	
					},
				})
				
				//父类型
				uni.request({
					url:'http://1.15.179.24:9520/record/paymentStatisticDetailOfParentType?userId='+userinfo.userId+'&startTime='+startTime+'&endTime='+endTime,
					method:'GET',
					header:{
						Authorization:userinfo.token
					},
					success: (res) => {
						console.log("查询指定时间范围的小类型支出")
						console.log(res)
						let resdata = res.data.data
						this.bigdata=[]
						resdata.forEach((item,i)=>{
							this.bigdata.push({
								"name": item.typeName,
								"value": item.money,
								"color":Mycolors[Math.floor(Math.random()*Mycolors.length)]
							})
						})
						if(!this.smallClass){
							this.expendDetail.series[0].data = this.bigdata
						}
					},
				})	
				
			},
			ListenAPI_2(){
				let userinfo = this.$store.state.user.info
				this.smalldata = []
				let tmpArr = []
				let myUrl = 'http://1.15.179.24:9520/record/incomeStatisticDetailOfType?userId='
				let monthTime = this.monthTime
				// console.log(userinfo.userId)
				let yearTime = this.yearTime
				let startTime = ''
				let endTime = ''
				if(this.chooseMonth == true){
					startTime = monthTime.replace('-','/') + "/01"
					endTime = monthTime.replace('-','/') + "/31"
				}else{
					startTime = yearTime+"/01/01"
					endTime = yearTime+"/12/31"
				}
				console.log(myUrl+userinfo.userId+'&startTime='+startTime+'&endTime='+endTime)
				console.log(userinfo.token)
				uni.request({
					url:myUrl+userinfo.userId+'&startTime='+startTime+'&endTime='+endTime,
					method:'GET',
					header:{
						Authorization:userinfo.token
					},
					success: (res) => {
							console.log("查询指定时间范围的收入情况")
							console.log(res.data)
							let resdata = res.data.data
							
							this.smalldata = []
							console.log(this.smalldata)
							resdata.forEach((item,i)=>{
								this.smalldata.push({
									"name": item.typeName,
									"value": item.money,
									"color":Mycolors[Math.floor(Math.random()*Mycolors.length)]
								})
								tmpArr.push({
									name: item.typeName,
									data: [item.money]
								})
							})
							this.columnData.series = tmpArr
							this.expendDetail.series[0].data = this.smalldata
							console.log(this.columnData.series)
							if(this.columnData.series.length==0){
								console.log('数组为空')
								this.columnData.series.push({
									name:'暂无数据',
									data:[0]
								})
							}
					},
				})
			},
			
			async function1(monYear,chooseMonth){
				//适合于 支出
				let myUrl = 'http://1.15.179.24:9520/record/paymentStatisticDetailOfParentType?userId='
				let userinfo = this.$store.state.user.info
				console.log(monYear,chooseMonth)
				if(chooseMonth == true){
					let dayArr = []
					for(let i=0;i<31;i++){
						
						dayArr[i] = 0;
						let day = i+1;
						if(day<10){
							day = '0'+day
						}
						let dayTime = (monYear + '/' + day).replace('-','/')
						
						uni.request({
							url:myUrl+userinfo.userId+'&startTime='+dayTime +'&endTime='+dayTime,
							method:'GET',
							header:{
								Authorization:userinfo.token
							},
							success: (res) => {
								// console.log(dayTime,res)
								let resdata = res.data.data
								resdata.forEach((item,j)=>{
									dayArr[i]+=item.money
								})
								// console.log(dayTime,dayArr[i])
							},
						})
					}
					let a = await new Promise((resolve,reject)=>{
						setTimeout(() => {
						  resolve(dayArr);
						}, 1000);
					})
					console.log("测试a",a)
					this.historyData = the_data[0]
					this.historyData.series[0].data = a
					
				}else{
					let monArr = []
					// let yearMoney = 0
					for(let i=0;i<12;i++){
						monArr[i] = 0;
						let mon = i+1;
						if(mon<10){
							mon = '0'+mon
						}
						let monthTime = monYear+'-'+mon
						uni.request({
							url:myUrl+userinfo.userId+'&startTime='+monthTime.replace('-','/') + "/01"+'&endTime='+monthTime.replace('-','/') + "/31",
							method:'GET',
							header:{
								Authorization:userinfo.token
							},
							success: (res) => {
								console.log(monthTime,res.data.data)
								let resdata = res.data.data
								resdata.forEach((item,j)=>{
									monArr[i]+=item.money
								})
								console.log(monthTime,monArr[i])
							},
						})
					}
					// console.log(monArr)
					// return monArr
					let a = await new Promise((resolve,reject)=>{
						setTimeout(() => {
						  resolve(monArr);
						}, 1000);
					})
					console.log("测试b",a)
					this.historyData = the_data[1]
					this.historyData.series[0].data = a
					// return a
				}
			},
			
			
			changeLineChart(){
				// let userinfo = this.$store.state.user.info
				if(this.chooseMonth==true){
					console.log("图表发生改变,选择月份",this.monthTime)
					if(this.showPaid == true){
						this.function1(this.monthTime,true)
						this.expendDetail.series[0].data=[]
						console.log(this.monthTime)
						this.ListenAPI()
					}
					else{
						this.ListenAPI_2()
					}
				}
				else{
					console.log("图表发生改变,选择年份",this.yearTime)
					if(this.showPaid == true){
						this.function1(this.yearTime,false)
						this.expendDetail.series[0].data=[]
						console.log(this.yearTime)
						this.ListenAPI()	
					}
					else{
						this.ListenAPI_2()
					}
					
				}
			}
		},
		watch:{
			chooseMonth(){
				console.log("监听到choosemonth")
				this.changeLineChart()
			},
			showPaid(){
				console.log("监听到showPaid")
				this.changeLineChart()
			},
			smallClass(){
				if(this.smallClass == true){
					console.log('展示小类')
					// this.expendDetail.series[0].data = this.smalldata
				}
				else{
					console.log("展示大类")
					// this.expendDetail.series[0].data = this.bigdata
				}
			}
		}
	}
</script>
<style lang="scss">
	.box{
		width: 100%;
		// height: 800px;
		// border: 3px solid rebeccapurple;
		
		// padding-bottom: 12px;
	}
	.head_box_1{
		// margin: 20px;
		border-radius: 10px;
		background-color:#2174A7;
		padding: 6px;
		font-size: 16px;
		color: white;
		margin: 0 auto;
		margin-top: auto;
		text{
			margin: 10px;
		}
	}
	.active_choose{
		border-bottom: orange 12rpx solid;
	}
	.top_head {
		height: 435rpx;
		width: 100%;
		padding: 110rpx 10rpx 0rpx 10rpx;
		background: url("https://vkceyugu.cdn.bspapp.com/VKCEYUGU-e9420bcd-26cd-4faf-b47a-9949982f7c41/0edf7889-12cb-4151-94b7-a2c12d27108a.jpg") no-repeat center 0px;
		background-size: 100% 100%;
		box-sizing: border-box;
	
		.top_desc {
			width: 100%;
			border-radius: 20rpx;
			background-color: #fff;
			margin-top: 20rpx;
			padding: 20rpx;
			box-sizing: border-box;
	
			.text-gray {
				font-size: 28rpx;
				color: #ccc;
				margin-right: 10rpx;
			}
	
			.remaining {
				font-size: 46rpx;
			}
	
			.flex_1 {
				flex: 1;
			}
	
			.head_block {
				margin-top: 20rpx;
	
				.income {
					color: #E34B5E;
				}
			}
		}
	
		.text_des {
			height: 100rpx;
			color: #fff;
			font-weight: 900;
			position: relative;
			margin-left: 60rpx;
	
			text {
				display: inline-block;
				height: 100%;
			}
	
			.month_num {
				font-size: 90rpx;
			}
	
			.month_text {
				font-size: 56rpx;
			}
	
			.month_year {
				font-size: 22rpx;
				position: absolute;
				left: 60rpx;
				top: 20rpx;
			}
	
			.point {
				font-size: 40rpx;
			}
	
			.title {
				font-size: 40rpx;
			}
		}
	}
	.lineChart{
		width: 90%;
		margin: 0 auto;
		box-sizing: border-box;
		margin-top: 20rpx;
		border-radius: 20px;
		background-color: white;
	}
	.picker{
		padding-top: 15rpx;
		padding-left: 10rpx;
		font-size: 20px;
	}
	.the_title{
		display: flex;
		align-items: center;
		.left_title{
			display: flex;
			font-size: 40rpx;
			font-weight: bold;
		}
		.right_btn{
			display: flex;
			font-size: 30rpx;
			color: #ccc;
			
			.rbtn{
				margin-top: 50rpx;
				margin-left:20rpx;
				margin-right: 20rpx;
			}
			.active_btn{
				color:gray;
				border:1px solid #ccc;
				border-radius: 20rpx;
				padding-left: 20rpx;
				padding-right:20rpx;
				padding-bottom: 5rpx;
			}
		}
	}
	.bottom-box{
		width: 100%;
		position: sticky;
		bottom: 0;
	}
</style>