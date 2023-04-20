<template>
	<view class="box" style="background-color: #F8F8F8;" >
		<view class="overlay" v-show="ifShowModal">
			
		</view>
		<view class="modal" v-show="ifShowModal">
			<view class="modal_head" style="background-color: #56A1E7;height: 30%;border-radius:15px 15px 0 0;display: flex;">
				<image src="" mode="" style="background-color: #E6F4F1;border-radius: 50%;height: 85rpx;width: 85rpx;margin: auto auto;"></image>
			</view>
			<view class="modal_body" style="display: flex;flex-direction: column;">
				<view class="" style="font-weight: 600;font-size: 15px;margin: 10px;">
					类别名称
				</view>
				<view class="" style="width: 85%;margin:0 auto;background-color: white;height: 37px;border-radius: 5px;display: flex;align-items: center;">
					<input type="text" name="" id="" placeholder="请输入自定义类别名称" v-model="typeName">
					<image src="../../static/my/settings/classSetting/zhonglei.svg" mode="" style="width: 55rpx;height: 55rpx;"></image>
				</view>
				<view class="modal_button" style="display: flex;justify-content: space-around;margin-top: 15px;">
					<button type="primary" style="width: 30%;height: 30px;background-color: #56A1E7;line-height: 30px;" @tap="saveModal">保存</button>
					<button type="default" style="width: 30%;height: 30px;line-height: 30px;" @tap="ifShowModal=0">取消</button>
				</view>
			</view>
		</view>
		<view class="head" style="z-index: 8;width: 100%;position: sticky;top: 0;padding-top: 60px;display: flex;">
			<view class="" style="" @click="goBack">
				<text class="cuIcon-back"></text>
				<text class="" style="font-size: 17px;">
					返回
				</text> 
			</view>
			<view class="" style="font-weight: 600;font-size: 15px;margin: 0 auto;transform: translate(-50%,0);">
				类别设置
			</view>
		</view>
		<view class="pay_setting" style="margin-top: 20px;" >
			<view class="" style="font-size: 18px;color: #4095E5;font-weight: 600;">
				支出种类设置
			</view>
			<view class="" v-for="(payItem,payIndex) in myData.pay" :key="payIndex">
				<view class="" style="display: flex;align-items: center;">
					<text class="title" style="font-weight: 500;margin-right: 10px;">{{payItem.typeName}}支出</text>
					<image src="../../static/my/settings/classSetting/shanchu.svg" mode="" style="padding: 0;width: 35rpx;" @click="delParent(payItem)"></image>	
				</view>
				
				
				<view class="" v-for="(item,index) in payItem.subList" style="border-bottom: 1px solid #F8F8F8;margin-right: 5px;display: flex;align-items: center;">
					<image src="../../static/my/settings/classSetting/honggang.svg" mode="" style="height: 45rpx;padding: 0;" @click="del(item.typeId)"></image>
					<image :src="require(`../../static/items/${item.typeId<100?item.typeId:100}.png`)" mode="" style="background-color: #E6F4F1;border-radius: 50%;"></image>
					<text style="margin-left: 10px;font-weight: 500;">{{item.typeName}}</text>
					<image src="../../static/my/settings/classSetting/xuanze.svg" mode="" style="height: 40rpx;padding: 0;margin-left: auto;"></image>
				</view>
				
				<view class="" style="display: flex;align-items: center;margin-bottom: 10px;" @click="showModal(payItem.typeId,1,1)">
					<image src="../../static/my/settings/classSetting/tianjia.svg" mode="" style="height: 50rpx;"></image>
					<text style="font-weight: 500;">添加新类别</text>
				</view>
			</view>
			<!-- <text class="u-line"></text> -->
			<view class="" style="display: flex;align-items: center;border-top: 1px solid lightgray;" @click="showModal(0,1,0)">
				<image src="../../static/my/settings/classSetting/zhonglei.svg" mode="" style="height: 50rpx;"></image>
				<text style="font-weight: 500;color: #4095E5;">添加新支出大类别</text>
			</view>
			
		</view>
		
		<view class="pay_setting" style="margin-top: 20px;">
			<view class="" style="font-size: 18px;color: #4095E5;font-weight: 600;">
				收入种类设置
			</view>
			<view class="" v-for="(gainItem,gainIndex) in myData.gain" :key="gainIndex" style="border-bottom: 1px solid #F8F8F8;margin-right: 5px;display: flex;align-items: center;">
				<image src="../../static/my/settings/classSetting/honggang.svg" mode="" style="height: 45rpx;padding: 0;" @click="del(gainItem.typeId)"></image>
				<image :src="require(`../../static/items/${gainItem.typeId<100?gainItem.typeId:100}.png`)" mode="" style="background-color: #E6F4F1;border-radius: 50%;"></image>
				<text style="margin-left: 10px;font-weight: 500;">{{gainItem.typeName}}</text>
				<image src="../../static/my/settings/classSetting/xuanze.svg" mode="" style="height: 40rpx;padding: 0;margin-left: auto;"></image>
			</view>
			<view class="" style="display: flex;align-items: center;margin-bottom: 10px;" @click="showModal(0,0,0)">
				<image src="../../static/my/settings/classSetting/tianjia.svg" mode="" style="height: 50rpx;"></image>
				<text style="font-weight: 500;">添加新类别</text>
			</view>
		</view>
		
		<u-modal :show="show" :content="content" :showCancelButton="true" @confirm="DeleteModal" @cancel="show=false"></u-modal>
	</view>
</template>

<script>
import { methods } from '../../uni_modules/uview-ui/libs/mixin/mixin';
	export default {
		data() {
			return {
				myData:{
					pay:[],
					gain:[]
				},
				ifShowModal:0,
				typeName:'',
				parentId:'',
				recordType:'',
				contentsType:'',
				show:false,
				content:'确定删除此金额类型吗',
				deleteTypeId:''
			}
			
		},
		computed:{
			userinfo(){
				return this.$store.state.user.info
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
					// console.log(res.data.data)//得到 数组
					let datalist = res.data.data
					datalist.forEach((item, i) => {
					  // console.log(item, i + 1);
					  if (item["parent_id"] == 0) {
					    // console.log("大类:" + item["typeName"]);
					    if (item["type"] === "支出") {
					      Pay_Arr.push({ ...item, subList: [] });
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
					// console.log(Pay_Arr);
					// console.log(Gain_Arr);
					// console.log("更新前:",this.myData)
					this.myData.pay = Pay_Arr
					this.myData.gain =Gain_Arr
					// console.log(this.myData.pay)
				}
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
			saveModal(){
				this.ifShowModal = 0;
				let userinfo = this.$store.state.user.info
				console.log(this.typeName)
				uni.request({
					url:'http://1.15.179.24:9520/type/addMoneyType?userId='+userinfo.userId,
					method:"POST",
					header:{
						Authorization:userinfo.token
					},
					data:{
						"typeName":this.typeName,
						"parentId":this.parentId,
						"recordType":this.recordType,
						"contentsType":this.contentsType
					},
					success: (res) => {
						console.log(res)
						
					}
				})
			},
			del(id){
				this.deleteTypeId = id
				console.log(id,"被删除")
				this.show=true
				
			},
			DeleteModal(){
				let userinfo = this.$store.state.user.info
				let id = this.deleteTypeId
				console.log(id)
				console.log(userinfo.token)
				uni.request({
					url:'http://1.15.179.24:9520/type/deleteMoneyType?userId='+userinfo.userId,
					method:'POST',
					header:{
						Authorization:userinfo.token
					},
					data:{
						typeId:id
					},
					success: (res) => {
						console.log("删除该金额类型")
						console.log(res)
					}
				})
				this.show = false
			},
			delParent(payitem){
				payitem.subList.forEach((item,i)=>{
					this.del(item.typeId)
				})
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
.modal{
	position: fixed;
	height: 200px;
	width: 250px;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	border-radius: 15px;
	background-color: #F5F3F3;
	z-index: 10;
}
</style>
