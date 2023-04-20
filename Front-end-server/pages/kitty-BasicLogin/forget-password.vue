<template>
	<view class="content">
		<!-- {{num}}
		{{userinfo.name}} -->
		
		<!-- <view class="logo"><image src="../../static/kitty-BasicLogin/logo.png" mode=""></image></view> -->
		<view class="" style="padding: 10px;">
			<view class="" style="font-size: 18px;font-weight: 600;">
				找回密码
			</view>
			<view class="uni-form-item uni-column" style="margin-top: 16px;">
				<input type="number" v-model="info.tel" class="uni-input" name="" placeholder="请输入您的手机号"/>
			</view>
			<view class="uni-form-item uni-column column-with-btn">
				<input type="number" class="uni-input" name="" placeholder="请输入验证码" v-model="info.code" />
				<button @tap="sendCode">{{codeBtn.text}}</button>
			</view>
			<view class="uni-form-item uni-column" style="margin-top: 16px;">
				<input type="text" v-model="info.pwd" class="uni-input" name="" placeholder="请输入新密码" />
			</view>
			<button type="primary" @click="updatePwd">确定</button> 
		</view>
		
	</view>
</template>

<script>
	// let mon = ['一','二','三','四','五','六','七','八','九','十','十一','十二']
	export default {
		data() {
			return {
				month:new Date().getMonth(),
				 mon:['一','二','三','四','五','六','七','八','九','十','十一','十二'],
				 monE:['January','February','March','April','May','June','July','August','September','October','November',
				 'December'],
				isShow:false,
				mode: 'single',
				info:{
					tel:'',
					code:'',
					pwd:'',
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
		computed:{
			num(){
				return this.$store.state.account.num
			},
			userinfo(){
				return this.$store.state.user.info
			}
		},
		methods: {
			updatePwd(){
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
							// console.log(res.data.message)
							// this.goToNext()
							if(this.info.pwd.length<3){
								uni.showToast({
									icon:'none',
									title:'密码长度过短!',
									duration:1000
								})
								return
							}
							uni.request({
								url:'http://1.15.179.24:9520/user/updateUser',
								method:'POST',
								header:{
									Authorization:userinfo.token
								},
								data:{
									userId:oid,
									password:this.info.pwd
								},
								success: (res) => {
									console.log("修改密码",res)
									if(res.data.code == 400){
										uni.showToast({
											// icon:'none',
											title:'请重新设置!',
											duration:1000
										})	
									}else{
										// this.$store.commit('change_phoneNumber',this.info.tel)
										uni.showToast({
											icon:'none',
											title:'设置新密码成功',
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
			}
			
		},
		
	}
</script>

<style lang="scss" scoped>
	$color-primary: #b49950;
	
	@font-face {
		font-family: "kaiti";
		font-weight: normal;
		font-style: normal;
		src: url('../../font/ZiYuYongSongTi-2.ttf');
	}
	@font-face {
		font-family: "Bellamy";
		font-weight: normal;
		font-style: normal;
		src: url('../../font/FrasellPersonalUse-2.otf');
	}
	
	.content{
		padding: 100upx;
		padding-top: 200px;
		min-height: 100vh;
		display: flex;
		flex-direction: column;
		background-image: url('../../static/picture/login.png');
		background-size:cover;
	}
	.logo{
	    text-align: center;
		image{
		    height: 200upx;
		    width: 200upx;
		    margin: 0 0 60upx;
		}
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
				width: 300upx;
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
	.links{
		text-align: center;
		margin-top: 40upx;
		font-size: 26upx;
		color: white;
		view{
			display: inline-block;
			vertical-align: top;
			margin: 0 10upx;
		}
		.link-highlight{
			color: orange;
		}
	}
</style>
