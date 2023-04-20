<template>
	<view class="box">
		<view class="head">
			<view class="" style="position: absolute;display: flex;align-items: center;" @click="goBack">
				<view class="cuIcon-back"></view>
				<view class="" style="font-size: 17px;">
					返回
				</view> 
			</view>
			<view class="" style="margin: 0 auto;font-weight: 600;font-size: 15px;">
				手机号设置
			</view>
		</view>
		
		<view class="" style="background-color: white;padding: 10px;">
			<view class="" style="font-size: 18px;font-weight: 600;">
				绑定新手机号
			</view>
			<view class="uni-form-item uni-column" style="margin-top: 16px;">
				<input type="number" v-model="info.tel" class="uni-input" name="" placeholder="请输入新手机号" />
			</view>
			<view class="uni-form-item uni-column column-with-btn">
				<input type="number" class="uni-input" name="" placeholder="请输入验证码" v-model="info.code" />
				<button @tap="sendCode">{{codeBtn.text}}</button>
			</view>
			
			<button type="primary" @click="updateTel">确定</button> 
		</view>
		
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				info:{
					tel:'',
					code:'',
					tempKey:''
				},
				seconds: 60,
				codeBtn: {
					text: '获取验证码',
					waitingCode: false,
					count: this.seconds
				},
			}
		},
		methods: {
			
			updateTel(){
				let userinfo = this.$store.state.user.info
				let oid = this.$store.state.user.oid
				uni.request({
					url:'http://1.15.179.24:9520/user/verifyCode',
					method:'POST',
					data:{
						code:this.info.code,
						tempKey:this.info.tempKey
					},
					success: (res) => {
						console.log(res)
						if(res.data.code == 200){
							console.log(res.data.message)
							// this.goToNext()
							
							uni.request({
								url:'http://1.15.179.24:9520/user/updateUser',
								method:'POST',
								header:{
									Authorization:userinfo.token
								},
								data:{
									userId:oid,
									phoneNumber:this.info.tel,
									userName:this.info.tel
								},
								success: (res) => {
									console.log("绑定手机号",res)
									if(res.data.code == 400){
										uni.showToast({
											icon:'none',
											title:'该手机号已存在用户!',
											duration:1000
										})	
									}else{
										this.$store.commit('change_phoneNumber',this.info.tel)
										uni.showToast({
											icon:'none',
											title:'绑定手机号成功',
											duration:500
										})	
										setTimeout(()=>{
											uni.navigateBack()	
										},500)	
									}
									
								}
							})
						}
						else{
							uni.showToast({
								icon:'none',
								title:'验证码错误'
							})
						}
					}
				})
			},
			sendCode: function () {
				let userinfo = this.$store.state.user.info
				let oid = this.$store.state.user.oid
				if(this.info.tel==''){
					uni.showToast({
						icon:'error',
						title:'请输入手机号',
						duration:1000
					})
					return
				}
				this.codeBtn.waitingCode = true;
				this.codeBtn.count = this.seconds;
				this.codeBtn.text = this.codeBtn.count + 's';
				uni.request({
					url:'http://1.15.179.24:9520/user/sendCode',
					method:'POST',
					data:{
						phoneNumber:this.info.tel,
						userId:oid
					},
					success: (res) => {
						console.log(res)
						this.info.tempKey = res.data.data
					}
				})
				
				let countdown = setInterval( () => {
					this.codeBtn.count--;
					this.codeBtn.text = this.codeBtn.count + 's';
					if( this.codeBtn.count < 0 ){
						clearInterval(countdown);
						this.codeBtn.text = '重新发送';
						this.codeBtn.waitingCode = false;
					}
				},1000);
			},
			goBack(){
				uni.navigateBack()
			}
		}
	}
</script>

<style lang="scss">
	$color-primary: #b49950;
.box{
	background-color:#F5F5F5;
	min-height: 100vh;
	
}
.head{
	height: 75px;
	background-color: #B6DFF8;
	display: flex;
	// justify-content: center;
	align-items: flex-end;
	padding-bottom: 10px;
}
.uni-form-item{
		margin-bottom: 40upx;
		padding: 0;
		border-bottom: 1px solid dimgrey;
		.uni-input{
			font-size: 35upx;
			padding: 2px 0;
		}
	}
	.column-with-btn{
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: center;
		button{
			font-size: 24upx;
			margin: 0;
			width: 260upx;
			text-align: center;
			&:after{
				border: none
			}
			&.active{
				background-color: $color-primary;
				color: $uni-text-color-inverse;
			}
		}
	}
	button[type="primary"]{
		background-color: $color-primary;
		border-radius: 0;
		font-size: 34upx;
		width: 100%;
		margin-top: 60upx;
	}
</style>
