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
				帮助
			</view>
		</view>
		
		<view class="" style="padding: 10px;font-size: 14px;border-bottom: 1px solid gray;">
			<view class="" style="font-size: 20px;font-weight: 600;margin-bottom: 10px;">
				记账管理模块>>
			</view>
			<view class="tx1">
				此模块主要用于对账本信息进行手动录入、自动记账和智能识别。
			</view>
			<view class="">
				<view class="tx2">
					手动记账
				</view>
				系统记录用户手动输入单次收入或支出的类别、时间、账户、金额、商家和备注。
			</view>
			<view class="">
				<view class="tx2">
					账单识别
				</view>
				用户选择需要识别的账单图片后，系统对信息进行识别，将账单的类别、时间、账户、金额、商家和备注等基本信息提取出来。
			</view>
		</view>
		
		<view class="" style="padding: 10px;font-size: 14px;border-bottom: 1px solid gray;">
			<view class="" style="font-size: 20px;font-weight: 600;margin-bottom: 10px;">
				智能分析模块>>
			</view>
			<view class="tx1">
				此模块主要用于根据收支统计得出消费报告和智能建议。
			</view>
			<view class="">
				<view class="tx2">
				消费报告
				</view>
				系统根据用户一段时间内的消费数据统计结果进行智能化分析，得出用户消费行为报告。
			</view>
			<view class="">
				<view class="tx2">
				智能建议
				</view>
				系统根据用户消费行为报告和收支统计，通过大量数据训练后神经网络模型提出消费建议。
			</view>
		</view>
		<view class="" style="padding: 10px;font-size: 14px;border-bottom: 1px solid gray;">
			<view class="" style="font-size: 20px;font-weight: 600;margin-bottom: 10px;">
				特殊功能模块>>
			</view>
			<view class="tx1">
				此模块包括了元宇宙世界、亲友帮助、贷款计算器、理财、大数据分析等特色功能。
			</view>
			<view class="">
				<view class="tx2">
				大数据分析	
				</view>
				将用户的收入和消费情况与系统获得的大数据进行比较，得出用户在大数据下的财务情况。
			</view>
			<view class="">
				<view class="tx2">
				贷款计算器
				</view>
				用户输入贷款金额、年限、利率和方式，计算出贷款金额。
			</view>
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
			goBack(){
				uni.navigateBack()
			}
		}
	}
</script>

<style lang="scss">
.box{
	background-color:white;
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
.tx1{
	font-size: 14px;
	color: dimgray;
}
.tx2{
	font-weight: 600;
}
</style>
