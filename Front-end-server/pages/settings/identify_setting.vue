<template>
	<view class="box">
		<view class="head" style="z-index: 8;width: 100%;position: sticky;top: 0;padding-top: 60px;display: flex;">
			<view class="" style="" @click="goBack">
				<image src="../../static/back.svg" mode="" style="height: 22px;width: 22px;"></image>
			</view>
			<view class="head_box_1">
				智能识别
			</view>
		</view>
		
		<view class="" style="display: flex;flex-direction: column;background-color: white;padding: 20px;">
			<view class="" style="display: flex;align-items: center;">
				<view class="" style="font-size: 18px;font-weight: 600;">
					开启极速智能识别
				</view>
				<view class="" style="margin-left: auto;">
					<u-switch v-model="value1" activeColor="#1E90FF" size="18"></u-switch>
				</view>	
			</view>
			
			<view class="" style="color: gray;margin-top: 10px;">
				开启极速识别记账后，您传入的图片识别内容后直接计入账本，
				跳过手动裁剪和记账内容编辑的步骤
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				value1:true
			}
		},
		methods: {
			goBack(){
				uni.navigateBack()
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
	// border-bottom: 1px solid #DCDCDC;
	.itemName{
		margin-left: 5px;
		font-weight: 600;
		// font-family: SimHei;
	}
	
}
</style>
