<template>
	<view class="content">
		<view class="" style="font-family: 'Bellamy';font-size:50px;font-weight: 400;margin: 0 auto;letter-spacing: 1px;color: mediumpurple;margin-top: 20px;">
			{{monE[month]}}
		</view>
		
		<text class="" style="font-size: 20px;border-bottom: 1px gray solid;font-weight: 600;margin: 0 auto;">
			您的性别
		</text>
		
		<view class="" style="margin: 0 auto;display: flex;margin-top: 20px;width: 80%;justify-content: space-around;">
			<view class="" style="display: flex;flex-direction: column;align-items: center;" @click="male=1" :class="male===1?'active':''">
				<image src="../../static/kitty-BasicLogin/man.svg" mode="heightFix" style="height: 40px;"></image>
				<text style="font-size: 15px;font-weight: 600;margin-top: 10px;">男性</text>
			</view>
			<view class="" style="display: flex;flex-direction: column;align-items: center;" @click="male=0" :class="male===0?'active':''">
				<image src="../../static/kitty-BasicLogin/women.svg" mode="heightFix" style="height: 40px;"></image>
				<text style="font-size: 15px;font-weight: 600;margin-top: 10px;">女性</text>
			</view>	
		</view>
		
		<text class="" style="font-size: 20px;border-bottom: 1px gray solid;font-weight: 600;margin: 0 auto;margin-top: 20px;color: chocolate;">
			您的年龄
		</text>
		<input type="number" v-model="age" placeholder="必填项" style="border-bottom: 1px solid black;text-align: center;width: 50%;margin: 0 auto;margin-top: 20px;">
		
		<text class="" style="font-size: 20px;border-bottom: 1px gray solid;font-weight: 600;margin: 0 auto;margin-top: 20px;color: chocolate;">
			您的昵称
		</text>
		<input type="text" v-model="name" placeholder="必填项" style="border-bottom: 1px solid black;text-align: center;width: 50%;margin: 0 auto;margin-top: 20px;">
		
		
		<button type="primary" @click="goToComplete"
		style="background-color: #b49950;
		border-radius: 0;
		font-size: 34upx;
		width: 100%;
		margin-top: 60upx;">完成注册</button> 
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
				male:'',
				age:'',
				tel:'',
				pwd:'',
				name:'',
				token:'',
				userId:''
			}
		},
		onLoad(e) {
			console.log(e)
			this.tel = e.tel
			this.pwd = e.pwd
		},
		methods: {
			goToComplete(){
				if(this.age == ''){
					uni.showToast({
						icon:'none',
						title:'年龄不能为空'
					})
				}
				if(this.name == ''){
					uni.showToast({
						icon:'none',
						title:'昵称不能为空'
					})
				}
				uni.request({
					url:"http://1.15.179.24:9520/user/register",
					method:"POST",
					data:{
						"phoneNumber": this.tel, 
						"password": this.pwd, 
						"nickName": this.name
					},
					success: (res) => {
						console.log(res);
						if(res.data.code ===200){
							this.token=res.data.data["token"];
							this.userId = res.data.data["userId"];
							// console.log(this.token)
							uni.showModal({
								showCancel:false,
								content:res.data.message,
								success:function(res){
									if(res.confirm){
										uni.redirectTo({
											url:'login'
										})
									}
								}
							})	
						}
						if(res.data.code ===400){
							uni.showModal({
								showCancel:false,
								content:res.data.message
							})	
						}
					}
					
				})
			}
		}
	}
</script>

<style lang="scss">
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
	.active{
		color: chocolate;
	}
</style>
