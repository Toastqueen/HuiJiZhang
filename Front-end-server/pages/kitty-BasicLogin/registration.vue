<template>
	<view class="content">
		
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
		
		
		<view class="uni-form-item uni-column" style="margin-top: 50px;">
			<input type="number" v-model="info.tel" class="uni-input" name="" placeholder="请输入手机号" />
		</view>
		<view class="uni-form-item uni-column column-with-btn">
			<input type="number" class="uni-input" name="" placeholder="请输入验证码" v-model="info.code" />
			<button  @tap="sendCode">{{codeBtn.text}}</button>
		</view>
		<view class="uni-form-item uni-column">
			<input type="password" v-model="info.password" class="uni-input" name="" placeholder="请输入密码" />
		</view>
		<button type="primary" @click="checkCode">下一步</button> 
		<view class="links">已有账号？<view class="link-highlight" @tap="gotoLogin">点此登陆</view></view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				month:new Date().getMonth(),
				 mon:['一','二','三','四','五','六','七','八','九','十','十一','十二'],
				 monE:['January','February','March','April','May','June','July','August','September','October','November',
				 'December'],
				captchaImg: '',
				seconds: 60,
				codeBtn: {
					text: '获取验证码',
					waitingCode: false,
					count: this.seconds
				},
				info:{
					name:'',
					tel:'',
					password:"",
					token:"",
					userID:"",
					code:'',
					tempKey:''
				}
			}
		},
		methods: {
			sendCode: function () {
				this.codeBtn.waitingCode = true;
				this.codeBtn.count = this.seconds;
				this.codeBtn.text = this.codeBtn.count + 's';
				
				uni.request({
					url:'http://1.15.179.24:9520/user/sendCode',
					method:'POST',
					data:{
						phoneNumber:this.info.tel
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
			
			goToNext(){
				uni.redirectTo({
					url:'/pages/kitty-BasicLogin/registration2?tel='+this.info.tel+'&pwd='+this.info.password
				})
			},
			
			checkCode(){
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
							this.goToNext()
						}
						else{
							uni.showToast({
								icon:'none',
								title:'验证码失败'
							})
						}
					}
				})
			},
			
			gotoLogin: function () {
				uni.redirectTo({
					url: 'login'
				})
			},
			handleReg:function(){//注册
				
			}
		},
		computed: {
			
		}
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
		    margin: 0 0 40upx;
		}
	}
	.uni-form-item{
		margin-bottom: 40upx;
		padding: 0;
		border-bottom: 1px solid dimgrey;
		.uni-input{
			font-size: 30upx;
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
	.img-captcha{
		width: 150upx;
		height: 60upx;
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
			color: orange
		}
	}
</style>
