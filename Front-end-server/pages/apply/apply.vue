<template>
	<view class="box">
		<view class="head" style="">
			<view class="" style="font-weight: 600;font-size: 15px;margin: 0 auto;transform: translate(-50%,0);">
				应用
			</view>
		</view>
		
		<view class="applyBox" style="background-color: rgba(255, 255, 255, 0.9);" hover-class="toClick" hover-stay-time="100" @click="goToReport">
			<image src="../../static/apply/1.png" mode="heightFix" style="height: 94%;"></image>
			<view class="applyText" >
				<view class="above" style="display: flex;align-items: center;">
					<view class="" style="font-size: 20px;font-weight: 600;line-height: 22px;">
						消费报告<br>智能建议
					</view>
					<image src="../../static/apply/11.png" mode="widthFix" style="margin-left: auto;width: 40px;"></image>
				</view>
				<view class="below" style="margin-top: 6px; font-size: 14px;font-weight: 500;color: dimgrey;line-height: 24px;">
					生成你的专属消费报告和智能建议
				</view>
			</view>
			<image src="../../static/apply/arrow.png" mode="heightFix" style="height: 20px;margin-left: auto;"></image>
		</view>
		
		<view class="applyBox" style="background-color: rgba(255, 255, 255, 0.9);" hover-class="toClick" hover-stay-time="100" @click="goToBudget">
			<image src="../../static/apply/2.png" mode="heightFix" style="height: 94%;"></image>
			<view class="applyText" >
				<view class="above" style="display: flex;align-items: center;">
					<view class="" style="font-size: 20px;font-weight: 600;line-height: 30px;">
						预算
					</view>
					<image src="../../static/apply/22.png" mode="widthFix" style="margin-left: auto;width: 40px;"></image>
				</view>
				<view class="below" style="margin-top: 6px; font-size: 14px;font-weight: 500;color: dimgrey;line-height: 24px;">
					计划并查看每个月的支出情况以便于后续消费
				</view>
			</view>
			<image src="../../static/apply/arrow.png" mode="heightFix" style="height: 20px;margin-left: auto;"></image>
		</view>
		
		<view class="applyBox"  style="background-color: rgba(255, 255, 255, 0.9);" hover-class="toClick" hover-stay-time="100" @click="goToBigdata">
			<image src="../../static/apply/3.png" mode="heightFix" style="height: 94%;"></image>
			<view class="applyText" >
				<view class="above" style="display: flex;align-items: center;">
					<view class="" style="font-size: 20px;font-weight: 600;line-height: 30px;">
						大数据
					</view>
					<image src="../../static/apply/33.png" mode="widthFix" style="margin-left: auto;width: 40px;"></image>
				</view>
				<view class="below" style="margin-top:6px; font-size: 14px;font-weight: 500;color: dimgrey;line-height: 24px;">
					统计查看您在所有用户中的进度
				</view>
			</view>
			<image src="../../static/apply/arrow.png" mode="heightFix" style="height: 20px;margin-left: auto;"></image>
		</view>
		
		<view class="applyBox"  style="background-color:rgba(255, 255, 255, 0.9);"  hover-class="toClick" hover-stay-time="100" @click="goToUniverse">
			<image src="../../static/apply/4.png" mode="heightFix" style="height: 94%;"></image>
			<view class="applyText" >
				<view class="above" style="display: flex;align-items: center;">
					<view class="" style="font-size: 20px;font-weight: 600;line-height: 30px;">
						元宇宙世界
					</view>
					<image src="../../static/apply/44.png" mode="widthFix" style="margin-left: auto;width: 40px;"></image>
				</view>
				<view class="below" style="margin-top:6px; font-size: 14px;font-weight: 500;color: dimgrey;line-height: 24px;">
					生成专属于您的元宇宙形象
				</view>
			</view>
			<image src="../../static/apply/arrow.png" mode="heightFix" style="height: 20px;margin-left: auto;"></image>
		</view>
		
	
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			goToReport(){
				uni.navigateTo({
					url:'/pages/apply/report'
				})
			},
			goToBigdata(){
				uni.navigateTo({
					url:'/pages/apply/bigdata'
				})
			},
			goToBudget(){
				uni.navigateTo({
					url:'/pages/apply/budget'
				})
			},
			goToUniverse(){
				uni.navigateTo({
					url:'/pages/apply/universe'
				})
			}
		}
	}
</script>

<style lang="scss">
.box{
	min-height: calc(100vh - 80rpx);
	padding-bottom: 10px;
	background-color: #F8F8F8;
	background-image: url('../../static/picture/apply3.png');
	background-size:cover;
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-color: rgba(255, 255, 255, 0.3);
}
.head{
	z-index: 8;
	height: 90px;width: 100%;
	background-color: #B6DFF8;
	display: flex;
	padding-top: 60px;
	position: sticky;
	top: 0;
}
.applyBox{
	height: 120px;
	width: 90%;
	margin: 0 auto;
	margin-top: 15px;
	display: flex;
	align-items: center;
	// border: 1px saddlebrown solid;
	border-radius: 18px;
	background-color: white;
	box-shadow: 0 1px 2px 2px rgba(0, 0, 0, 0.1);
	// box-shadow: 0 1px 2px 2px ;
	filter: drop-shadow(0px 0px 0px rgba(0,0,0,0.1));
	padding: 10px;
}
.applyText{
	display: flex;
	flex-direction: column;
	width: 50%;
	height: 80%;
	padding-left: 12px;
	margin-left: 10px;
}
.toClick{
	// transform: scale(0.99);
	opacity: 0.8;
}

</style>
