<template>
	<view class="content">
		<!-- {{num}}
		{{userinfo.name}} -->
		
		<view class="" style="font-family: 'Bellamy';font-size:50px;font-weight: 400;margin: 0 auto;letter-spacing: 1px;color: mediumpurple;margin-top: 20px;">
			{{monE[month]}}
		</view>
		
		<view class="" style="font-size: 30px;font-family:'kaiti';font-weight: 400;margin: 0 auto;margin-top: 40px;">
			{{mon[month]}}月你好
		</view>
		
		<view class="" style="font-size: 15px;font-family:'kaiti';font-weight: 500;margin: 0 auto;margin-top: 10px;margin-bottom: 20px;">
			<view class="">
				【宜】停下脚步 欣赏风景
			</view>
			<view class="">
				【忌】负重奔跑 寸步难行
			</view>	
		</view>
		
		
		<!-- <view class="logo"><image src="../../static/kitty-BasicLogin/logo.png" mode=""></image></view> -->
		<view class="uni-form-item uni-column" style="margin-top: 50px;">
			<input type="number" v-model="info.tel" class="uni-input" name="" placeholder="请输入手机号" />
		</view>
		<view class="uni-form-item uni-column">
			<input type="password" v-model="info.password" class="uni-input" name="" placeholder="请输入密码" />
		</view>
		<button type="primary" @tap="handleLogin">登陆</button>
		<view class="links">
			<view @tap="gotoForgetPassword">忘记密码？</view>
			<view>|</view>
			<view class="link-highlight" @tap="gotoRegistration">注册账号</view>
		</view>
		
		<view class="" style="display: flex;flex-direction: column;margin-top: 30px;align-items: center;">
			<view class="" style="color: white;font-size: 12px;">
				其他登录方式
			</view>
			<image src="../../static/login/wx.svg" mode="heightFix" style="height: 24px;margin-top: 10px;" @click="wxLogin">
			</image>
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
					password:'',
					token:'',
					userID:'',
					avatarUrl:'',
					tempUserId:'',
					qr:'',
					timer:''
				}
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
			confirm(e) {
				console.log(e);
			},
			
			wxLogin(){
				// 请求获取二维码信息 http://1.15.179.24:8001/wxUser/wxQr
				console.log("wxLogin:")
				uni.request({
					url:'http://1.15.179.24:8001/wxUser/wxQr',
					method:'GET',
					success: (res) => {
						console.log(res)
						if (res.data.code === 200) {
							this.info.tempUserId = res.data.data.tempUserId;
							this.info.qr = res.data.data.qrUrl;
							
							uni.navigateTo({
								url:'/pages/kitty-BasicLogin/qrUrl?qrUrl='+this.info.qr+'&id='+this.info.tempUserId
							})
							
							
						}
					}
				})
			},
			// loginSuccess(){
			// 	console.log('成功登录')
			// 	clearInterval(this.info.timer)
			// },
			
			gotoRegistration: function () {
				uni.redirectTo({url: 'registration'});
			},
			gotoForgetPassword: function () {
				uni.redirectTo({url: 'forget-password'});
			},
			handleLogin:function(){
				uni.request({
					url:"http://1.15.179.24:9520/user/login",
					method:"POST",
					data:{
						"userName": this.info.tel, 
						"password": this.info.password
					},
					success: (res) => {
						console.log(res)
						console.log(res.data)
						if(res.data.code ===200){
							this.info.token=res.data.data["token"];
							this.info.userID = res.data.data["userId"];
							this.info.avatarUrl = res.data.data["avatarUrl"];
							this.$store.state.user.isLogin=true;
							this.$store.commit('changeInfo',res.data.data)
							this.$store.commit('change_oid',this.info.userID)
							console.log(this.$store.state.user.info)
							console.log(this.$store.state.user.oid)
							uni.showToast({
								title:"登录成功！",
								duration:1000
							})
							setTimeout(()=>{
								uni.redirectTo({
									url:'/pages/newTab/newTab'
								})
							},1000)
						}
						else{
							uni.showToast({
								icon:'error',
								title:"登录失败",
								duration:1500
							})
						}
					}
				})
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
		border-bottom: 1px solid dimgray;
		.uni-input{
			font-size: 30upx;
			padding: 2px 0;
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
