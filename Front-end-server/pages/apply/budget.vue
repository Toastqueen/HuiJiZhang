<template>
	<view class="box" style="background-color: #F8F8F8;" >
		<view class="overlay" v-show="ifShowModal">
			
		</view>
		
		<view class="budgetModal" v-if="show">
			<view class="Item" v-for="(payItem,payIndex) in myData.pay" :key="payIndex">
				<view class="" style="font-size: 16px;background-color:#F0E68C;padding:1px 10px 1px 10px;">
					{{payItem.typeName}}
				</view>
				<input type="number" v-model="myData.budget[payIndex].typeBudget" style="border-bottom: 1px gray dashed;width: 40%;margin-left: auto;text-align: center;">
			</view>
			<view class="modal_button" style="display: flex;justify-content: space-around;margin-top: 10px;">
				<view style="width: 35%;height: 28px;border-radius: 14px; line-height: 40px;background-color:#4095E5;display: flex;align-items: center;" @click="saveModal">
					<text style="font-size: 16px;color: white;font-weight: 500;margin: 0 auto;">保存</text>
				</view>
				<view style="width: 35%;height: 28px;border-radius: 14px;line-height: 40px;background-color:#B2DDF5;display: flex;align-items: center;" @click="cancelModal">
					<text style="font-size: 16px;color: dodgerblue;font-weight: 500;margin: 0 auto;">取消</text>
				</view>
			</view>	
		</view>
		
		<view class="head" style="z-index: 8;width: 100%;position: sticky;top: 0;padding-top: 60px;display: flex;">
			<view class="" style="" @click="goBack">
				<image src="../../static/back.svg" mode="" style="height: 22px;width: 22px;"></image>
			</view>
			<view class="head_box_1">
				<text class="cuIcon-text"></text>
				{{accountName}}
				<text class="cuIcon-right"></text>
			</view>
		</view>
		<view class="" style="width: 100%;height: 50px;background-color: #B6DFF8;">
			<view class="" style="margin-left: 20px;font-size: 14px;font-weight: bold;">
				{{year}}年{{month}}月
			</view>
		</view>
		<view class="my_card" style="display: flex;flex-direction: column;margin-top: -30px;">
			<view class="" style="color: gray;margin: 10px;">
				支出总预算
			</view>
			<view class="" style="margin-left: 10px;font-size: 30px;font-weight: 700;" @click="editBudget">
				{{(myData.tot).toFixed(2)}}
				<image src="../../static/edit.svg" mode="" style="height: 25px;width: 25px;"></image>
			</view>
			<view class="" style="margin: 10px;display: flex;">
				<view class="" style="color: gray;">
					已用
					<text style="color: black;margin-left: 10px;">{{(havePaid.toFixed(2))}}</text>
				</view>
				<view class="" style="color: gray;margin-left: 50px;">
					可用
					<text style="color: black;margin-left: 10px;">{{(myData.tot-havePaid).toFixed(2)}}</text>
				</view>
			</view>
		</view>
		<view class="pay_setting" style="margin-top: 20px;" >
			<view class="" v-for="(payItem,payIndex) in myData.pay" :key="payIndex">
				<view class="" style="display: flex;align-items: center;position: relative;margin-top: 8px;margin-bottom: 4px;">
					<text class="title" style="font-weight: 500;margin-right: 10px;">{{payItem.typeName}}支出</text>
					<text style="position: absolute;left:40%;color: gray;">支出预算</text>
					<text style="position: absolute;left:60%">{{myData.budget[payIndex].typeBudget}}</text>
					<text style="position: absolute;left:75%;color: gray;">支出</text>
					<text style="position: absolute;left:90%">{{(Rest.get(payItem.typeId)>0)?Rest.get(payItem.typeId):0}}</text>
				</view>
				
				
				<view class="" v-for="(item,index) in payItem.subList" style="border-bottom: 1px solid #F8F8F8;display: flex;align-items: center;position: relative;margin-bottom: 2px;">
					<image :src="require(`../../static/items/${item.typeId<100?item.typeId:100}.png`)" mode="" style="background-color: #E6F4F1;border-radius: 50%;"></image>
					<text style="margin-left: 10px;font-weight: 500;">{{item.typeName}}</text>
					<text style="position: absolute;left:75%;color: gray;">支出</text>
					<text style="position: absolute;left:90%">{{(rest.get(item.typeId)>0)?rest.get(item.typeId):0}}</text>
				</view>
			</view>
		
			
		</view>
	</view>
</template>

<script>
import { methods } from '../../uni_modules/uview-ui/libs/mixin/mixin';
	export default {
		data() {
			return {
				rest:new Map(),
				Rest:new Map(),
				havePaid:0,
				myData:{
					pay:[],
					gain:[],
					budget:[{
						typeBudget:0,
						typeId:''
					}],
					tot:0
				},
				num1:(0).toFixed(2),
				money1:0,
				money2:0,
				ifShowModal:0,
				typeName:'',
				parentId:'',
				recordType:'',
				contentsType:'',
				show:false,
				content:'确定删除此金额类型吗',
				deleteTypeId:'',
				year:new Date().getFullYear(),
				month:new Date().getMonth()+1
			}
			
		},
		computed:{
			userinfo(){
				return this.$store.state.user.info
			},
			accountName(){
				return this.$store.state.user.accountName
			}
		},
		onShow() {
			// this.mymy = 2023
			// 首先获取 支出大类，收入大类
			// 获取金额类别信息
			let userinfo = this.$store.state.user.info
			let Pay_Arr = [];
			let Gain_Arr = [];
			uni.request({
				url:'http://1.15.179.24:9520/type/getAllMoneyType?userId='+userinfo.userId,
				method:'GET',
				header:{
					Authorization:userinfo.token//注意token 别过期了
				},
				success: (res) => {
					let len = 0;
					console.log(res.data.data)//得到 数组
					let datalist = res.data.data
					datalist.forEach((item, i) => {
					  // console.log(item, i + 1);
					  if (item["parent_id"] == 0) {
					    // console.log("大类:" + item["typeName"]);
					    if (item["type"] === "支出") {
					      Pay_Arr.push({ ...item, subList: [] });
						  this.myData.budget[len] = {
							  typeBudget:0,
							  typeId:item['typeId']
						  }
						  len++;
					    } else {
					      Gain_Arr.push(item);
					    }
					  } else {
					    // console.log("小类:" + item["typeName"]);
					    // console.log(item["parent_id"]);
					    let idx = item["parent_id"];
					    Pay_Arr.forEach((newitem, j) => {
					      if (newitem["typeId"] === idx) {
					        newitem.subList.push(item);
					      }
					    });
					  }
					});
					this.myData.pay = Pay_Arr
					this.myData.gain =Gain_Arr
					
					console.log(userinfo)
					uni.request({
						url:'http://1.15.179.24:9520/budget/listOfMonth/'+userinfo.userId,
						method:'GET',
						header:{
							Authorization:userinfo.token
						},
						success: (res2) => {
							console.log("查看本月预算",res2.data)
							let resdata = res2.data.data
							resdata.forEach((item,i)=>{
								this.myData.budget.forEach((item2,j)=>{
									if(item2.typeId == item.typeId){
										item2.typeBudget = item.typeBudget
									}
								})	
							})
							this.myData.budget.forEach((item,i)=>{
								this.myData.tot+=Number(item.typeBudget)
							})
						}
					})		
				}
			})
			let year = new Date().getFullYear()
			let month = new Date().getMonth()+1
			let monthTime=year+'-'+month
			//查询本月小类型的支出
			// rest = new Map()
			uni.request({
				url:'http://1.15.179.24:9520/record/paymentStatisticDetailOfType?userId='+userinfo.userId+'&startTime='+monthTime.replace('-','/') + "/01"+'&endTime='+monthTime.replace('-','/') + "/31",
				method:'GET',
				header:{
					Authorization:userinfo.token
				},
				success: (res) => {
					// console.log(res.data.data)
					let resdata = res.data.data
					resdata.forEach((item,i)=>{
						this.rest.set(item.typeId,item.money)
					})
				},
			})
			
			//查询大类型
			this.havePaid = 0
			uni.request({
				url:'http://1.15.179.24:9520/record/paymentStatisticDetailOfParentType?userId='+userinfo.userId+'&startTime='+monthTime.replace('-','/') + "/01"+'&endTime='+monthTime.replace('-','/') + "/31",
				method:'GET',
				header:{
					Authorization:userinfo.token
				},
				success: (res) => {
					// console.log(res.data.data)
					let resdata = res.data.data
					resdata.forEach((item,i)=>{
						this.havePaid += Number(item.money)
						this.Rest.set(item.typeId,item.money)
					})
				},
			})
			
		},
		methods: {
			goBack(){
				uni.navigateBack()
			},
			showModal(parentId,recordType,contentsType){
				this.ifShowModal=1;
				console.log(parentId,recordType,contentsType)
				this.parentId = parentId
				this.recordType = recordType
				this.contentsType = contentsType
			},
			
			editBudget(){
				this.show = true
			},
			
			saveModal(){
				let userinfo = this.$store.state.user.info
				this.show = false
				console.log(this.myData.budget)
				this.myData.tot =0
				this.myData.budget.forEach((item,i)=>{
					if(item.typeBudget>0){
						this.myData.tot +=Number(item.typeBudget)
						console.log("新建预算",item)
						uni.request({
							url:'http://1.15.179.24:9520/budget/addTypeBudget',
							method:'POST',
							header:{
								Authorization:userinfo.token
							},
							data:{
								userId:userinfo.userId,
								typeId:item.typeId,
								typeBudget:Number(item.typeBudget),
								monthYear:1
							},
							success: (res) => {
								console.log(res)
							}
						})	
					}
					
				})
			},
			
			cancelModal(){
				this.show = false
			}
			
		}
	}
</script>

<style lang="scss">

.box{
	background-color:#F5F5F5;
	// min-height: 100vh;
	
}
.head{
	height: 90px;
	background-color: #B6DFF8;
	// display: flex;
	/* justify-content: center; */
	// align-items: flex-end;
	// padding-bottom: 10px;
}
.head_box_1{
	// margin: 20px;
	border-radius: 10px;
	background-color:#2174A7;
	padding: 5px;
	font-size: 15px;
	color: white;
	position: absolute;
	margin-top: auto;
	left:50%;
	transform: translate(-50%,0);
	text{
		margin: 10px;
	}
}

.my_card{
	width: 94%;
	margin: 0 auto;
	background-image: url("../../static/picture/budget.png");
	background-size: cover;
	border-radius: 10px;
}

.pay_setting{
	border-radius: 20px;
	width: 85%;
	background-color: white;
	margin: 0 auto;
	padding: 10px;
	// margin-top: 30px;
}
image{
	
	width: 65rpx;
	height: 65rpx;
	padding: 3rpx;
	margin: 2px;
}
.overlay{
	position: fixed;
	top:0;
	left: 0;
	width: 100%;
	height: 100%;
	background-color: rgba(0, 0, 0, 0.2);
	z-index: 8;
}
.budgetModal{
	position: fixed;
	// height: 300px;
	width: 180px;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	border-radius: 15px;
	background-color: white;
	z-index: 10;
	display: flex;
	flex-direction: column;
	padding: 20px;
	box-shadow: 0 0 2px 2px rgba(0, 0, 0, 0.2);
}
.Item{
	display: flex;
	margin-bottom: 9px;
}
</style>
