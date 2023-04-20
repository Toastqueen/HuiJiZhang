<template>
	<!-- 首页 -->
	<!-- 总共分为3个部分，头部，记一笔。近期账单 -->
	<view class="box">
		<view class="" style="height: 90px ;background-color: #B6DFF8;padding-top: 60px;z-index: 10;position: fixed;width: 100%;display: flex;flex-direction: column;
	align-items: center;">
			<view class="head_box_1">
				<text class="cuIcon-text"></text>
				{{accountName}}
				<!-- <text class="cuIcon-right"></text> -->
			</view>
			
		</view>
		
		<view class="head" style="padding-top: 100px;padding-bottom: 70px;">
			
			<view class="head_box_2">
				本月支出(元)
			</view>
			<view class="head_box_3">
				<text class="padding-right-sm">¥</text>
				{{(paidMoney).toFixed(2)}}
			</view>
			<view class="head_box_4">
				<view class="head_box_left">
					本月收入 {{(gotMoney).toFixed(2)}}
				</view>
				<view class="head_box_right">
					月结余 {{(gotMoney-paidMoney).toFixed(2)}}
				</view>
			</view>
			<view class="head_box_4">
				本月预算 {{budgetMoney}}
				<text class="cuIcon-write" @click="editBudget"></text>
			</view>
		</view>
		
		<view class="budgetInput" style="" v-if="budgetShow==true">
			<input type="number" style="height: 30px;width: 60%;
			border-bottom: 1px solid #C38328 ;margin: 0 auto;text-align: center;margin-top: 10px;margin-bottom: 40px;" :placeholder="budgetMoney" v-model="tmpBudget">
			<view class="" style="display: flex;">
				<button style="width: 50%;color: #70A8E3;height: 40px;" @click="budgetShow=false,tmpBudget=''">取消</button>
				<button style="width: 50%;color: #C38328;height: 40px;" @click="VerifyBudget">确定</button>	
			</view>
			
		</view>
		
		<view class="foot" style="border-radius: 30px 30px 0 0;transform: translate(0,-50px);z-index:5;background-color:  #f9f9f9;box-shadow:0px 0px 3px 1px rgba(0, 0, 0, 0.2);padding-top:13px;">
			<view class="middle" >
				<view class="middle_box" style="background-color: #77B3EB;" @click="goToAccount">
					<text class="middle_box_text">记账</text>
					<image src="../../static/index/jizhang.svg" mode="heightFix" style="height: 56px;"></image>
				</view>
				<view class="middle_box" style="background-color: #AADAF4;" @click="takeMyPhotos">
					<text class="middle_box_text">智能识别</text>
					<image src="../../static/index/zhineng.svg" mode="heightFix" style="height: 56px;"></image>
				</view>
			</view>
			<!-- <view class="" @click="clearStorage">
				清除本地缓存
			</view> -->
			<view class="foot_head">
				<view class="foot_box_1">
					近期账单
				</view>
				<view class="foot_box_2" style="width: 40%;">
					<u-search placeholder="搜索" v-model="keyword" 
					:clearabled="true" 
					:animation="true"
					bg-color="white"
					@clear="my_clear"
					@custom="my_search"></u-search>
				</view>
				<view class="foot_box_3" :style="{backgroundColor:(show_type==1?'orange':'#B6DFF8')}" @tap="show_type=1">
					按时间
				</view>
				<view class="foot_box_3" :style="{backgroundColor:(show_type==2?'orange':'#B6DFF8')}" @tap="show_type=2">
					按类型
				</view>
			</view>
			<view class="foot_bills">
				<view class="no_bills" v-show="0">
					<image src="../../static/bill/bill.png" mode="widthFix"></image>
					<text>未发现近期账单，试着记一笔</text>
				</view>
				<!-- <view class="">
					{{itemList}}
				</view>
				<view class="">
					{{newData}}
				</view> -->
				<view class="have_bills" v-show="1&show_type==1" style="padding: 10px; padding-bottom: 13px;background-color: white;width: 94%;margin:auto;margin-top: 10px;border-radius: 15px;box-shadow: 0 0 2px 2px rgba(0, 0, 0, 0.2);">
					<!-- 第一层循环，按日期 -->
					<view v-for="(item,index) in newData" :key="index">
						<view class="text-gray" style="font-size: 30rpx;margin-bottom: 8px;margin-top: 4px;">
							{{item.year}}年{{item.month}}月{{item.date}}日 星期{{weekday[item.day]}}
						</view>
						<!-- <u-line ></u-line> -->
						<!-- 第二层循环 细分 -->
						<view style="">
							<view v-for="(itm,idx) in item.subList" :key="idx">
								<view class="item" style="display: flex;align-items: center;margin-bottom: 4px;" @tap="goToDetals(itm)">
									 <image :src="require(`../../static/items/${itm.typeId<100?itm.typeId:100}.png`)" mode="" ></image>
									 <view class="" style="display: flex;flex-direction: column">
										<text class="Name" style="margin-left: 10px;margin-bottom: 4px;font-size: 14px;font-weight: 500;">{{itm.typeName}}</text>
										<view class="">
											<text style="margin-left: 10px;color: dimgray;">{{itm.seller}}</text>
											<text style="margin-left: 10px;color:#C38328;">{{itm.remark}}</text>
										</view>
									 </view>
									 
									 <text class="Money" style="margin-left: auto;font-weight: 500;color: #70A8E3;" :style="{color:(itm.isPayment==false?'chocolate':'#70A8E3')}">
										¥{{(itm.money).toFixed(2)}}
									 </text>
								</view>
								<u-line v-show="idx!=(item.subList.length-1)"></u-line>
							</view>
						</view>	
					</view>
				</view>
				
				<view class="" v-show="1&show_type==2" style="padding: 10px; padding-bottom: 20px;background-color: white;width: 94%;margin:auto;margin-top: 10px;border-radius: 15px;box-shadow: 0 0 2px 2px rgba(0, 0, 0, 0.2);">
					<view v-for="(payItem,payIndex) in myData.pay" :key="payIndex" v-show="payItem.money>0">
			
						
						<view class="" style="margin-bottom: 6px;display: flex;color: grey;">
							<text class="title" style="">{{payItem.typeName}}</text>
							<text style="" style="margin-left: auto;">¥{{(payItem.money).toFixed(2)}}</text>		
						</view>
						
						<view class="" v-for="(item,index) in payItem.subList" style="margin-right: 5px;">
							<view class="" v-show="item.money>0" >
								<view  style="display: flex;align-items: center;margin-top: 6px;margin-bottom: 6px;">
									<image :src="require(`../../static/items/${item.typeId<100?item.typeId:100}.png`)" mode="" style="background-color: #E6F4F1;border-radius: 50%;height: 34px;width: 34px;"></image>
									<text style="margin-left: 10px;font-weight: 500;">{{item.typeName}}</text>
									<view class="" @tap="item.deepListShow=!item.deepListShow" style="margin-left: 10px;">
										<u-icon name="arrow-right" style="background-color: #EFEFEF;" v-show="item.deepListShow==0"></u-icon>
										<u-icon name="arrow-down" style="background-color: #EFEFEF;" v-show="item.deepListShow==1"></u-icon>
									</view>
									<text style="margin-left: auto;font-weight: 500;color: #70A8E3;">¥{{(item.money).toFixed(2)}}</text>	
								</view>
								<u-line></u-line>
								<view class="" style="background-color:#EFEFEF;border-radius: 7px;" v-show="item.deepListShow==1" >
									<view class="" v-for="(newItem,newIndex) in item.deepList" 
									style="display: flex;align-items: center;padding: 3px;" @tap="goToDetals(newItem)">
										<image :src="require(`../../static/items/${newItem.typeId<100?newItem.typeId:100}.png`)" mode="" style="background-color: #E6F4F1;border-radius: 50%;height: 50rpx;width: 50rpx;"></image>
										
										<view class="" style="display: flex;flex-direction: column;">
											<text style="margin-left: 10px;margin-bottom: 5px; color: #70A8E3;font-weight: 500;">{{newItem.createTime.split(" ")[0]}}</text>
											<view class="">
												<text style="margin-left: 10px;color: dimgray;">{{newItem.seller}}</text>
												<text style="margin-left: 10px;color:#C38328;">{{newItem.remark}}</text>	
											</view>
										</view>
										<text style="margin-left: auto;font-weight: 500;color: #70A8E3;">¥{{(newItem.money).toFixed(2)}}</text>
									</view>		
								</view>
								
							</view>
							
						</view>
					</view>
				</view>
			</view>
			
		</view>
		
		
		
			
	</view>
</template>

<script>
	export default {
		data() {
			return {
				budgetShow:false,
				show_type:1,
				keyword:'',
				title: 'Hello',
				name:'',
				money:'',
				weekday:['日','一','二','三','四','五','六'],
				newData:[],
				myData:{
					pay:[],
					gain:[]
				},
				showDetail:0,
				paidMoney:0,
				gotMoney:0,
				budgetMoney:0,
				tmpBudget:'',
				tmpVal:0,
				pageNum:1,
				pageSize:30
			}
		},
		computed:{
			itemList(){
				return this.$store.state.account.item
			},
			accountName(){
				return this.$store.state.user.accountName
			}
		},
		
		mounted() {
			let that = this
			uni.$on('onReachBottom',function(){
				console.log('触底了')
				that.goTo_onReachBottom()
				return
			})
				let newArr= [];
				let oldData=this.$store.state.account.item;
				let userinfo = this.$store.state.user.info
				// console.log("老数组:",oldData)		
				uni.request({
					url:'http://1.15.179.24:9520/record/getMoneyRecordList?userId='+userinfo.userId+'&pageNum='+this.pageNum+'&pageSize='+this.pageSize,
					method:'GET',
					header:{
						Authorization:userinfo.token
					},
					success: (res) => {
						// console.log(res)
						if(res.data["code"]==200){
							let resdata = res.data["data"]
							oldData = resdata["list"]
							// console.log("由查询得到的list数组:",oldData)
							
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
							// console.log("由老数组得到的新数组：",newArr)
							
							//把 newArr 加起来，用作统计
							this.$store.commit('account/InitIndexArr',newArr)
							
							function compare(key){
								return function(value1,value2){
									let val1 = value1[key];
									let val2 = value2[key];
									return val2-val1;
								}
							}
							newArr.sort(compare('timeSeconds'))
							// this.newData = newArr
							this.newData.push.apply(this.newData,newArr)
							
							//获取按类型分类的数据
							// let userinfo = this.$store.state.user.info
							let Pay_Arr = [];
							let Gain_Arr = [];
							uni.request({
								url:'http://1.15.179.24:9520/type/getAllMoneyType?userId='+userinfo.userId,
								method:'GET',
								header:{
									Authorization:userinfo.token//注意token 别过期了
								},
								success: (res2) => {
									// console.log(res2.data.data)//得到 数组
									let datalist = res2.data.data
									datalist.forEach((item, i) => {
									  // console.log(item, i + 1);
									  if (item["parent_id"] == 0) {
										// console.log("大类:" + item["typeName"]);
										if (item["type"] === "支出") {
										  Pay_Arr.push({ ...item, money:0,subList:[]});
										} else {
										  Gain_Arr.push(item);
										}
									  } else {
										// console.log("小类:" + item["typeName"]);
										// console.log(item["parent_id"]);
										let idx = item["parent_id"];
										Pay_Arr.forEach((newitem, j) => {
										  if (newitem["typeId"] === idx) {
											newitem.subList.push({...item,money:0,deepListShow:0,deepList:[]});
										  }
										});
									  }
									});
									// console.log(Pay_Arr);
									// console.log(Gain_Arr);
									// console.log("更新前:",this.myData)
									this.myData.pay = Pay_Arr
									this.myData.gain =Gain_Arr
									// console.log(this.myData.pay)
									oldData.forEach((item2,ii)=>{
										// item2.parent_id 
										this.myData.pay.forEach((ItemClass,jj)=>{
											// if(item2.parent_id == .typeId)
											
											ItemClass.subList.forEach((newItem2,kk)=>{
												if(newItem2.typeId == item2.typeId){
													//合并进deepList数组
													newItem2.deepList.push(item2)
													newItem2.money =Number(newItem2.money)+Number(item2.money)
													ItemClass.money = Number(ItemClass.money)+Number(item2.money)
												}
											})
										})
									})
								}
							})
							
						}
					}
				})
					
					
				//查询本月支出
				let mon = new Date().getMonth()+1
				
				if(mon<10){
					mon = '0'+mon
				}
				let monthTime = new Date().getFullYear() +'-'+mon
				this.paidMoney = 0;
				this.gotMoney = 0;
				uni.request({
					url:'http://1.15.179.24:9520/record/paymentStatisticDetailOfParentType?userId='+userinfo.userId+'&startTime='+monthTime.replace('-','/') + "/01"+'&endTime='+monthTime.replace('-','/') + "/31",
					method:'GET',
					header:{
						Authorization:userinfo.token
					},
					success: (res) => {
						// console.log("查询支出的钱",res)
						let resdata = res.data.data
						resdata.forEach((item,i)=>{
							console.log(item.money)
							this.paidMoney+=Number(item.money)
						})
					},
				})
				// this.paidMoney = allMoney1
				uni.request({
					url:'http://1.15.179.24:9520/record/incomeStatisticDetailOfType?userId='+userinfo.userId+'&startTime='+monthTime.replace('-','/') + "/01"+'&endTime='+monthTime.replace('-','/') + "/31",
					method:'GET',
					header:{
						Authorization:userinfo.token
					},
					success: (res) => {
						let resdata = res.data.data
						resdata.forEach((item,i)=>{
							this.gotMoney+=Number(item.money)
						})
					},
				})
				
				uni.request({
					url:'http://1.15.179.24:9520/budget/allBudget/'+userinfo.userId,
					method:'GET',
					header:{
						Authorization:userinfo.token
					},
					success: (res) => {
						console.log("获取总预算",res)
						this.budgetMoney = res.data.data.month_budget
					},
					fail: (err) => {
						console.log(err)
					}
				})	
		},
		
		methods: {
			
			goTo_onReachBottom() {
				console.log("页面到底")
				this.pageNum++;
				let newArr= [];
				let oldData=this.$store.state.account.item;
				let userinfo = this.$store.state.user.info
				// console.log("老数组:",oldData)		
				uni.request({
					url:'http://1.15.179.24:9520/record/getMoneyRecordList?userId='+userinfo.userId+'&pageNum='+this.pageNum+'&pageSize='+this.pageSize,
					method:'GET',
					header:{
						Authorization:userinfo.token
					},
					success: (res) => {
						// console.log(res)
						if(res.data["code"]==200){
							let resdata = res.data["data"]
							oldData = resdata["list"]
							// console.log("由查询得到的list数组:",oldData)
							
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
							// console.log("由老数组得到的新数组：",newArr)
							
							//把 newArr 加起来，用作统计
							this.$store.commit('account/InitIndexArr',newArr)
							
							function compare(key){
								return function(value1,value2){
									let val1 = value1[key];
									let val2 = value2[key];
									return val2-val1;
								}
							}
							newArr.sort(compare('timeSeconds'))
							// this.newData = newArr
							this.newData.push.apply(this.newData,newArr)
							
							//获取按类型分类的数据
							// let userinfo = this.$store.state.user.info
							let Pay_Arr = [];
							let Gain_Arr = [];
							uni.request({
								url:'http://1.15.179.24:9520/type/getAllMoneyType?userId='+userinfo.userId,
								method:'GET',
								header:{
									Authorization:userinfo.token//注意token 别过期了
								},
								success: (res2) => {
									// console.log(res2.data.data)//得到 数组
									let datalist = res2.data.data
									datalist.forEach((item, i) => {
									  // console.log(item, i + 1);
									  if (item["parent_id"] == 0) {
										// console.log("大类:" + item["typeName"]);
										if (item["type"] === "支出") {
										  Pay_Arr.push({ ...item, money:0,subList:[]});
										} else {
										  Gain_Arr.push(item);
										}
									  } else {
										// console.log("小类:" + item["typeName"]);
										// console.log(item["parent_id"]);
										let idx = item["parent_id"];
										Pay_Arr.forEach((newitem, j) => {
										  if (newitem["typeId"] === idx) {
											newitem.subList.push({...item,money:0,deepListShow:0,deepList:[]});
										  }
										});
									  }
									});
									// console.log(Pay_Arr);
									// console.log(Gain_Arr);
									// console.log("更新前:",this.myData)
									this.myData.pay = Pay_Arr
									this.myData.gain =Gain_Arr
									// console.log(this.myData.pay)
									oldData.forEach((item2,ii)=>{
										// item2.parent_id 
										this.myData.pay.forEach((ItemClass,jj)=>{
											// if(item2.parent_id == .typeId)
											
											ItemClass.subList.forEach((newItem2,kk)=>{
												if(newItem2.typeId == item2.typeId){
													//合并进deepList数组
													newItem2.deepList.push(item2)
													newItem2.money =Number(newItem2.money)+Number(item2.money)
													ItemClass.money = Number(ItemClass.money)+Number(item2.money)
												}
											})
										})
									})
								}
							})
							
						}
					}
				})
			},
			
			
			editBudget(){
				console.log("打开输入预算框")
				this.budgetShow = true
			},
			VerifyBudget(){
				let userinfo = this.$store.state.user.info
				this.budgetShow = false
				this.budgetMoney = this.tmpBudget
				uni.request({
					url:'http://1.15.179.24:9520/budget/updateAllbudget',
					method:'PUT',
					header:{
						Authorization:userinfo.token
					},
					data:{
						userId:userinfo.userId,
						monthBudget:this.tmpBudget,
						yearBudget:0
					},
					success: (res) => {
						this.tmpBudget = ''
						console.log(res)
					}
				})
			},
			my_search(){
				console.log("this is search")
				console.log(this.keyword)
			},
			my_clear(){
				this.keyword=""
			},
			show(){
				console.log(this.newData)
			},
			clearStorage(){
				console.log("清除缓存")
				uni.getStorage({
					key:'info',
					success:function(res){
						console.log(JSON.parse(res.data))
					}
				})
				uni.clearStorage()
				// console.log(this.itemList)
			},
			
			goToAccount(){
				uni.navigateTo({
					url:'/pages/account/account'
				})
			},
			
			goToDetals(e){
				console.log(e)
				this.$store.commit('account/InitDetails',e)
				uni.navigateTo({
					url:'/pages/details/details',
					success: (res) => {
						console.log(res)
					},
					fail: (err) => {
						console.log(err)
					}
				})
			},
			takeMyPhotos(){
				console.log("开启智能识别")
				let tmpArr = []
				let imgArr = [] //
				let tot =0
				let userinfo = this.$store.state.user.info
				let token = userinfo.token
				let userId = userinfo.userId
				uni.chooseImage({
					count:5,
					sizeType:['compressed'],
					success: (res) => {
						console.log(res.tempFilePaths)
						tmpArr = res.tempFilePaths//临时图片路径
						this.$store.commit('account/InitTmpArr',tmpArr)
						uni.navigateTo({
							url:'/pages/identify/identify'
						})
					}
				})
			}
		}
	}
</script>

<style lang="scss">
	
	
.box{
	width: 100%;
	// height: 800px;
	// border: 3px solid rebeccapurple;
	height: 100%;
	padding-bottom: 5px;
	background-color: #f9f9f9;
	box-sizing: border-box;
}
.budgetInput{
	background-color: white;
	position: absolute;
	z-index: 8;
	top: 50%;
	left: 50%;
	transform: translate(-50%,-50%);
	width: 80%;
	height: 130px;
	border-radius: 14px;
}

.head_box_1{
	// margin: 20px;
	border-radius: 10px;
	background-color:#2174A7;
	padding: 5px;
	font-size: 15px;
	color: white;
	margin-top: auto;
	text{
		margin: 10px;
	}
}
.head{
	// border: 1px saddlebrown solid;
	padding-top: 50px;
	padding-bottom: 40px;
	// border-radius:0 0 30px 30px;
	display: flex;
	flex-direction: column;
	align-items: center;
	background-color: #B6DFF8;
	font-weight: 600;
	
	.head_box_2{
		margin: 10px;
		font-size: 15px;
	}
	.head_box_3{
		margin: 10px;
		font-size: 25px;
	}
	.head_box_4{
		display: flex;
		flex-direction: row;
		font-size: 15px;
		font-weight: 500;
		.head_box_left{
			margin: 10px;
			margin-right: 70px;
		}
		.head_box_right{
			margin: 10px;
		}
	}
}
.middle{
	
	display: flex;
	// flex-direction: row;
	justify-content: space-around;
	
}
.middle_box{
	height: 80px;
	width: 41%;
	// border: 1px solid sandybrown;
	border-radius: 10px;
	box-shadow: 0 0 2px 2px rgba(0, 0, 0, 0.2);
	display: flex;
	// flex-direction: column;
	// justify-content: center;
	justify-content: space-around;
	align-items: center;
	// display: flex;
}
.middle_box_text{
	// border: 1px solid crimson;
	font-size: 17px;
	font-weight: 600;
}
.foot_head{
	margin-top: 20px;
	display: flex;
	height: 30px;
	line-height: 30px;
	
	.foot_box_1{
		font-weight: bold;
		margin-left: 5px;
		margin-right: 5px;
	}
	.foot_box_3{
		margin-left: 15px;
		background-color: #B6DFF8;
		border-radius: 13px;
		justify-content: center;
		padding-left: 8px;
		padding-right: 8px;
		color: white;
	}
	
}
.no_bills{
	display: flex;
	flex-direction: column;
	text-align: center;
	image{
		width: 250px;
		margin: auto;
	}
}
.item{
	height: 40px;
	
	// display: flex;
	// align-items: center;
	// position: relative;
	.Name{
		// position: absolute;
		// left: 70px;
		font-size: 15px;
	}
	image{
		height: 34px;
		width: 34px;
		// margin-left: 20px;
		// border: 1px solid crimson;
		padding: 2px;
		background-color: #EFF5F6;
		border-radius: 50%;
	}
}
.getMoney{
	color:chocolate
}
</style>
