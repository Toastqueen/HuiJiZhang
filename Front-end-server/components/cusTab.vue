<template name="cusTab">
		<view class="cu-bar tabbar bg-black">
			<view class="action text-green" @click="goTo('/pages/index/index')">
				<view class="cuIcon-homefill"></view> 首页
			</view>
			<view class="action text-gray" @click="goTo('/pages/home/home')"> 
				<view class="cuIcon-similar"></view> 统计
			</view>
			<view class="action text-gray add-action" @click="goTo('/pages/account/account')">
				<button class="cu-btn cuIcon-add bg-green shadow"></button>
				记账
			</view>
			<view class="action text-gray" @click="goTo('/pages/budget/budget')">
				<view class="cuIcon-cart">
				</view>
				预算
			</view>
			<view class="action text-gray" @click="goTo('/pages/my/my')">
				<view class="cuIcon-my">
				</view>
				我的
			</view>
		</view>
</template>

<script>
	export default {
		name:"cusTab",
		props:{
		},
		data() {
			return {
				
			}
		},
		methods: {
			goTo(url){
				uni.switchTab({
					url:url
				})
			}
		}
	}
</script>

<style lang="scss">
.tabbar{
	
}
</style>
