<template>
	<view class="box">
		<view class="" style="font-size: 18px;margin-left: 50%;transform: translate(-50%,0) ;margin-top: 90px;color: white">
			微信扫描登录
		</view>
		<image :src="qr" mode="heightFix" style="width: 60%;margin-left: 50%;transform:translate(-50%,0);margin-top: 60px;"></image>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				qr:'',
				timer:'',
				tempUserId:''
			}
		},
		onLoad(e) {
			console.log(e.qrUrl)
			this.qr = e.qrUrl
			this.tempUserId = e.id
			let tmp = 0;
			this.timer = setInterval(() => {
				// 轮询判断用户是否已经登录
				tmp++;
				if(tmp==10){
					clearInterval(this.timer)
				}
				uni.request({
					url:"http://1.15.179.24:8001/wxUser/isLogin?tempUserId=" + this.tempUserId,
					method:'GET',
					success: (res2) => {
						console.log(res2)
						if (res2.data.code === 200) {
							// console.log(res2)
							console.log('成功登录')
							this.$store.state.user.isLogin=true;
							this.$store.commit('changeInfo',res2.data.data)
							this.$store.commit('change_oid',res2.data.data.userId)
							console.log("从微信登录",this.$store.state.user.info)
							clearInterval(this.timer)
							uni.redirectTo({
								url:'/pages/newTab/newTab'
							})
						}
						if (res2.data.code === 204) {
							//继续轮询 不退出 这不是完整代码 根据你的自己改
						}	
					}
				})
			}, 3000);
		},
		methods: {
			
		}
	}
</script>

<style lang="scss">
.box{
	padding: 50px;
	min-height: 100vh;
	background-image: url('../../static/login/qr.png');
	background-size: cover;
}
</style>
