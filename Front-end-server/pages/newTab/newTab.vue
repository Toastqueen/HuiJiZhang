<template>
	<view>
		
		<view class="content">
			<index v-if="currentPage==1"></index>
			<home v-if="currentPage==2"></home>
			<apply v-if="currentPage==4"></apply>
			<my v-if="currentPage==5" ref="mypage"></my>
		</view>
		<view class="cu-bar tabbar" >
			<view class="action text-gray" @click="currentPage=1" :style="{color:(currentPage==1?'#1DA6D6':'gray')}">
				<view class="cuIcon-homefill"></view> 首页
			</view>
			<view class="action text-gray" @click="currentPage=2" :style="{color:(currentPage==2?'#1DA6D6':'gray')}" style="display: flex;flex-direction: column;"> 
				<!-- <view class="cuIcon-similar"></view> 统计 -->
				<image src="../../static/tab/home.svg" style="height: 22px;width: 22px;margin-bottom: 1px;" v-show="currentPage!=2"></image>
				<image src="../../static/tab/home2.svg" style="height: 22px;width: 22px;margin-bottom: 1px;" v-show="currentPage==2"></image>
				统计
			</view>
			<view class="action text-gray add-action" @tap="gotoAccount" :style="{color:(currentPage==3?'#1DA6D6':'gray')}">
				<button class="cu-btn cuIcon-add shadow" style="background-color:#1DA6D6 ;"></button>
				记账
			</view>
			<view class="action text-gray" @click="currentPage=4" :style="{color:(currentPage==4?'#1DA6D6':'gray')}" style="display: flex;flex-direction: column;">
				<image src="../../static/tab/apply.jpg" mode="" style="height: 23px;width: 23px;margin-bottom: 1px;" v-show="currentPage!=4"></image>
				<image src="../../static/tab/apply2.jpg" mode="" style="height: 23px;width: 23px;margin-bottom: 1px;" v-show="currentPage==4"></image>
				应用
			</view>
			<view class="action text-gray" @click="currentPage=5" :style="{color:(currentPage==5?'#1DA6D6':'gray')}">
				<view class="cuIcon-my">
				</view>
				我的
			</view>
		</view>
	</view>
</template>

<script>
	import my from "../my/my.vue"
	import home from "../home/home.vue"
	import index from "../index/index.vue"
	import account from "../account/account.vue"
	import apply from "../apply/apply.vue"
	export default {
		components:{
			my,
			home,
			index,
			account,
			apply
		},
		data() {
			return {
				currentPage: 5,
				paddingBottomHeight: 0,
				tmpCurrent:0
			}
		},
		onReachBottom() {
			uni.$emit('onReachBottom');
	   },
		onShow() {//能够有效刷新子组件的数据
		
			this.tmpCurrent=this.currentPage;
			this.currentPage=0;
			this.$nextTick(()=>{
				this.currentPage = this.tmpCurrent;
			})
		},
		//拿到状态机中的数据
		computed:{
			
		},
		// onShow() {
		// 	this.$refs.mypage.ShowData()
		// },
		methods: {
			gotoAccount(){
				if(this.$store.state.user.isLogin===false){
					uni.showModal({
						content:'请先登录',
						success:function(res){
							if(res.confirm){
								uni.navigateTo({
									url:"/pages/kitty-BasicLogin/login"
								})
							}
						}
					})
				}
				else{
					uni.navigateTo({
						url:"/pages/account/account",
						animationType:"slide-in-bottom",
						animationDuration:300
					});	
				}
			}
		}
	}
</script>

<style lang="scss">
.content {
	width: 100%;
	height: 100%;
	// border: 1px saddlebrown solid;
	padding-bottom: 80rpx;
}
.tabbar {
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		display: flex;
		justify-content: space-around;
		align-items: center;
		width: 100%;
		// height: 25rpx;
		// border: 1px solid crimson;
		background-color: #ffffff;
 
		.tabbar-item {
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;
			width: 56rpx;
			height: 85%;
		}
	}
</style>
