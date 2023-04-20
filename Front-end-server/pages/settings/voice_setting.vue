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
				声音、触感与通知
			</view>
		</view>
		<view class="outerBox">
			<view class="itemLine">
				<view class="itemName">
					开启声音
				</view>
				<view class="" style="margin-left: auto;">
					<u-switch v-model="value1" activeColor="#1E90FF" size="18"></u-switch>
				</view>
				
			</view>
			<view class="itemLine">
				<view class="itemName">
					开启触感
				</view>
				<view class="" style="margin-left: auto;">
					<u-switch v-model="value2" activeColor="#1E90FF" size="18"></u-switch>
				</view>
			</view>
			<view class="itemLine" style="border-bottom: white;">
				<view class="itemName">
					开启消息通知
				</view>
				<view class="" style="margin-left: auto;">
					<u-switch v-model="value3" activeColor="#1E90FF" size="18"></u-switch>
				</view>
			</view>	
		</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				value1:true,
				value2:true,
				value3:true
			}
		},
		methods: {
			goBack(){
				uni.navigateBack({
					animationType:"fade-out",
					animationDuration: 1000
				})
			}
		}
	}
</script>

<style lang="scss"> 
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
.outerBox{
	background-color: white;
	padding-right: 4px;
}

.itemLine{
	width: 97%;
	margin-left: 3%;
	display: flex;
	// border: 1px solid salmon;
	height: 50px;
	align-items: center;
	background-color: white;
	padding-left: 10px;
	padding-right: 10px;
	border-bottom: 1px solid #DCDCDC;
	.itemName{
		margin-left: 5px;
		font-weight: 600;
		// font-family: SimHei;
	}
	
}
</style>
