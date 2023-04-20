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
				理财小助手
			</view>
		</view>
		
		<view class="paper" style="height: 110px;width: 100%;background-color: white;
		padding: 6px;border-bottom: 1px solid lightgray;
		display: flex;align-items: center;" v-for="(item,index) in paperList" @click="goTo(index)">
			<view class="" style="width: 40%;">
				<image :src="require(`../../static/picture/paper/${index+1}.jpg`)" mode="widthFix" style="width: 100%;"></image>
			</view>
			
			<text class="" style="font-size: 18px;font-weight: 600;width: 50%;margin-left: 10px;">
				{{item.title}}
			</text>
			<text class="cuIcon-right"  style="position: absolute;font-size: 16px;
			right: 0;"></text>
		</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				paperList:[
					{
						title:"理财优则"
					},
					{
						title:"理财小白应该怎么开始?应该在哪里学习?"
					},
					{
						title:"2023年有哪些稳妥的理财方式?"
					},
					{
						title:"有哪些比余额宝更好的理财方式?"
					},
					{
						title:"有50万存款，该如何理财?"
					}
				]
			}
		},
		methods: {
			goBack(){
				uni.navigateBack()
			},
			goTo(index){
				uni.navigateTo({
					url:'/pages/articles/paper?index='+index
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
</style>
