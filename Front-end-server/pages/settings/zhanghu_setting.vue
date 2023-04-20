<template>
	<view class="box">
		<view class="head" style="z-index: 8;width: 100%;position: sticky;top: 0;padding-top: 60px;display: flex;">
			<view class="" style="" @click="goBack">
				<image src="../../static/back.svg" mode="" style="height: 22px;width: 22px;"></image>
			</view>
			<view class="head_box_1">
				账户设置
			</view>
		</view>
		
		<view class="aBox" style="padding: 20px;" :style="{backgroundColor:(currentAccount==item.accountid?'#B9D492':'#97C4EE')}"  v-for="(item,index) in accountList" :key="index">
			<view class="" style="display: flex;align-items: center;">
				<view class="" style="font-size: 24px;font-weight: 700;">
					{{item.accountname}}
				</view>
				<text class="cuIcon-write" style="color: black;font-size: 22px;margin-left: auto;" @click="showModal(item)"></text>
			</view>
			<view class="" style="font-size: 14px;color: gray;margin-top: 15px;margin-bottom: 10px;">
				{{item.info}}
			</view>
			<view class="" style="display: flex;flex-direction: column;font-size: 14px;font-weight: 600;">
				
				<view class="" style="display: flex;margin-top: 15px;">
					<view class="">
						本月收入: {{allGet[index]}}
					</view>
					<view class="" style="margin-left: auto;">
						本月支出: {{allPay[index]}}
					</view>
				</view>
			</view>
			<view class="" style="display: flex;margin-top: auto;">
				<view class="" style="margin-left: 0;font-size: 12px;font-weight: 600;" @click="changeAccount(item,index)">
					选择该账户
				</view>
				<image src="../../static/detail/shanchu.svg" mode="heightFix" style="height: 20px;margin-left: auto;" @click="del(item)"></image>
			</view>
			
		</view>
		
		<view class="" style="margin: 0 auto;width: 35%;display: flex;
		align-items: center;margin-top: 40px;background-color: white;height: 40px;
		justify-content: space-around;border-radius: 20px;
		box-shadow: 0 0 1px 1px rgba(0, 0, 0, 0.2);
		" @click="addAccount">
			<image src="../../static/add.svg" mode="heightFix" style="height: 30px;"></image>
			<view class="" style="font-size: 14px;font-weight: 600;">
				点击添加账户
			</view>
		</view>
		
		<view class="modal" v-show="show">
			<view class="">
				账户名称
				<input type="text" v-model="name" style="border-bottom: 1px solid darkgray;margin-top: 5px;">
			</view>
			<view class="" style="margin-top: 10px;">
				账户介绍
				<input type="text" v-model="info" style="border-bottom: 1px solid darkgray;margin-top: 5px;">
			</view>
			<view class="" style="margin-top: auto;display: flex;">
				<button style="width: 40%;" @tap="show=false">取消</button>
				<button style="width: 40%;" @tap="saveModal">保存</button>
			</view>
		</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				show:false,
				accountList:[],
				len:0,
				name:'',
				info:'',
				id:'',
				currentAccount:'',
				allGet:[],
				allPay:[]
				
			}
		},
		onShow() {
			this.$nextTick(()=>{
				let mon=new Date().getMonth()+1
				let year=new Date().getFullYear()
				if(mon<10){
					mon ='0'+mon
				}
				let start = year+'/'+mon+'/01'
				let end =  year+'/'+mon+'/31'
				let userinfo = this.$store.state.user.info
				let oid = this.$store.state.user.oid
				console.log(oid)
				uni.request({
					url:'http://1.15.179.24:9520/account/list?userid='+oid,
					method:'GET',
					header:{
						Authorization:userinfo.token
					},
					success: (res) => {
						console.log(res)
						console.log(res.data.data)
						this.accountList = [...res.data.data]
						this.len = this.accountList.length
						// this.currentAccount = this.accountList[0].accountid
						if(userinfo.userId!=oid){
							console.log('不是默认账户',userinfo.userId)
							this.currentAccount = userinfo.userId
						}
						this.accountList.forEach((item,index)=>{
							// item.accountid
							item.allGet = 0;
							item.allPay = 0;
							this.allGet[index]=0;
							this.allPay[index]=0;
							uni.request({
								url:'http://1.15.179.24:9520/record/incomeStatisticDetailOfType?userId='+item.accountid+'&startTime='+start+'&endTime='+end,
								method:'GET',
								header:{
									Authorization:userinfo.token
								},
								success: (res) => {
									 res.data.data.forEach((item2,i)=>{
										item.allGet+=Number(item2.money)
										this.allGet[index]+=Number(item2.money)
									})
								}
							})
							uni.request({
								url:'http://1.15.179.24:9520/record/paymentStatisticDetailOfParentType?userId='+item.accountid+'&startTime='+start+'&endTime='+end,
								method:'GET',
								header:{
									Authorization:userinfo.token
								},
								success: (res) => {
									// console.log("查询支出的钱",res)
									let resdata = res.data.data
									resdata.forEach((item2,i)=>{
										// console.log(item.money)
										item.allPay+=Number(item2.money)
										this.allPay[index]+=Number(item2.money)
									})
								},
							})
							// setTimeout(()=>{
								// console.log(item)
								this.accountList[index] = item
								// console.log(this.accountList)
							// },1000)
						})
					}
				})	
			})
			
		},
		updated(){
			let userinfo = this.$store.state.user.info
			let oid = this.$store.state.user.oid
			uni.request({
				url:'http://1.15.179.24:9520/account/list?userid='+oid,
				method:'GET',
				header:{
					Authorization:userinfo.token
				},
				success: (res) => {
					// console.log(res.data.data)
					this.accountList = res.data.data
					this.len = this.accountList.length
					if(userinfo.userId!=oid){
						// console.log('不是默认账户',userinfo.userId,oid)
						this.currentAccount = userinfo.userId
					}
				}
			})
		},
		watch:{
			len(){
				console.log('长度发生变化')
				this.listenApi()
			}
		},
		methods: {
			changeAccount(item,index){
				this.$store.commit('change_userId',item.accountid)
				this.$store.commit('change_accountName',item.accountname)
				this.currentAccount = item.accountid
				let oid = this.$store.state.user.oid
				if(index==0){
					this.$store.commit('change_userId',oid)
					// this.currentAccount = item.accountid
				}
			},
			del(item){
				let userinfo = this.$store.state.user.info
				console.log(userinfo.token)
				console.log(item)
				uni.request({
					url:'http://1.15.179.24:9520/account/delete',
					method:'POST',
					header:{
						Authorization:userinfo.token
					},
					data:{
						accountid:item.accountid
					},
					success: (res) => {
						console.log(res)
						this.len--
					}
				})
			},
			listenApi(){
				let mon=new Date().getMonth()+1
				let year=new Date().getFullYear()
				if(mon<10){
					mon ='0'+mon
				}
				let start = year+'/'+mon+'/01'
				let end =  year+'/'+mon+'/31'
				let userinfo = this.$store.state.user.info
				let oid = this.$store.state.user.oid
				console.log(oid)
				uni.request({
					url:'http://1.15.179.24:9520/account/list?userid='+oid,
					method:'GET',
					header:{
						Authorization:userinfo.token
					},
					success: (res) => {
						console.log(res)
						console.log(res.data.data)
						this.accountList = [...res.data.data]
						this.len = this.accountList.length
						// this.currentAccount = this.accountList[0].accountid
						if(userinfo.userId!=oid){
							console.log('不是默认账户',userinfo.userId)
							this.currentAccount = userinfo.userId
						}
						this.accountList.forEach((item,index)=>{
							// item.accountid
							item.allGet = 0;
							item.allPay = 0;
							this.allGet[index] =0;
							this.allPay[index] =0;
							uni.request({
								url:'http://1.15.179.24:9520/record/incomeStatisticDetailOfType?userId='+item.accountid+'&startTime='+start+'&endTime='+end,
								method:'GET',
								header:{
									Authorization:userinfo.token
								},
								success: (res) => {
									 res.data.data.forEach((item2,i)=>{
										item.allGet+=Number(item2.money)
										this.allGet[index]+=Number(item2.money)
									})
								}
							})
							uni.request({
								url:'http://1.15.179.24:9520/record/paymentStatisticDetailOfParentType?userId='+item.accountid+'&startTime='+start+'&endTime='+end,
								method:'GET',
								header:{
									Authorization:userinfo.token
								},
								success: (res) => {
									// console.log("查询支出的钱",res)
									let resdata = res.data.data
									resdata.forEach((item2,i)=>{
										// console.log(item.money)
										item.allPay+=Number(item2.money)
										this.allPay[index]+=Number(item2.money)
									})
								},
							})
							// setTimeout(()=>{
								// console.log(item)
								this.accountList[index] = item
								// console.log(this.accountList)
							// },1000)
						})
					}
				})
				setTimeout(()=>{
					console.log(this.accountList)
				},2000)
			},
			goBack(){
				uni.navigateBack()
			},
			showModal(item){
				this.show = true
				this.name=item.accountname
				this.info = item.info
				this.id = item.accountid
				console.log("显示模态框")
			},
			saveModal(){
				let userinfo = this.$store.state.user.info
				let oid = this.$store.state.user.oid
				uni.request({
					url:'http://1.15.179.24:9520/account',
					method:'PUT',
					header:{
						Authorization:userinfo.token
					},
					data:{
						userid: oid, 
						accountname: this.name, 
						accountid: this.id, 
						info:this.info
					},
					success: (res) => {
						console.log(res)
					}
				})
				this.show = false
			},
			addAccount(){
				let userinfo = this.$store.state.user.info
				let oid = this.$store.state.user.oid
				this.len++;
				uni.request({
					url:'http://1.15.179.24:9520/account',
					method:'POST',
					header:{
						Authorization:userinfo.token
					},
					data:{
						userid:oid,
						accountname:"测试账户"+this.len,
						info:'暂无介绍'
					},
					success: (res) => {
						console.log(res)
						
					}
				})
			}
		}
	}
</script>

<style lang="scss">
.box{
	min-height: 100vh;
	background-color:#F5F5F5;
}
.head{
	height: 90px;
	background-color: #B6DFF8;
}
.head_box_1{
	// margin: 20px;
	border-radius: 10px;
	// background-color:#2174A7;
	padding: 5px;
	font-size: 16px;
	font-weight: 600;
	position: absolute;
	margin-top: auto;
	left:50%;
	transform: translate(-50%,0);
	text{
		margin: 10px;
	}
}
.aBox{
	width: 80%;height: 200px;background-color: #97C4EE;margin: 0 auto;margin-top: 20px;
	border-radius: 10px;
	box-shadow: 0 0 2px 2px rgba(0, 0, 0, 0.2);
	display: flex;
	flex-direction: column;
}
.modal{
	position: fixed;
	top: 50%;
	left: 50%;
	transform: translate(-50%,-50%);
	z-index: 10;
	height: 220px;
	width: 60%;
	padding: 20px;
	border-radius: 20px;
	background-color: white;
	display: flex;
	flex-direction: column;
}
</style>
