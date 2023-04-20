<template>
	<view class="box" style="display: flex;flex-direction: column;">
		<view class="" style="width: 100%;">
			<image src="../../static/apply/report/back.png" mode="heightFix" style="height: 24px;margin-top: 50px;" @click="goBack"></image>
		</view>
		
		<view class="" style="font-family: 'Bellamy';font-size:40px;font-weight: 300;margin: 0 auto;letter-spacing: 1px;color: #BFAAAA;">
			{{monE[month]}}
		</view>
		
		<view class="" style="height: 75%;width: 90%;margin: 0 auto;background-color: rgba(255, 255, 255, 0.5);">
			<view class="" style="font-size: 22px;font-weight: 600;height: 7%;padding-left: 20px;color: #BFAAAA;">
				消费报告
			</view>
			
			<!-- <w-loading text="加载中.." mask="true" click="false" ref="loading"></w-loading> -->
			<scroll-view scroll-y="true" style="padding: 10px;font-size: 16px;white-space: pre-wrap;height: 93%;">
				<!-- <view class="" style="padding: 10px;font-size: 16px;white-space: pre-wrap;"> -->
					{{text}}
				<!-- </view>	 -->
			</scroll-view>
			
		</view>
		<Loading26 class="box2" v-show="showLoading"></Loading26>
	</view>
	
</template>

<script>
import { methods } from '../../uni_modules/uview-ui/libs/mixin/mixin'
import Loading26 from "@/components/loading26.vue";
	export default {
		components:{
			Loading26
		},
		data() {
			return {
				showLoading:false,
				text:'',
				month:new Date().getMonth(),
				mon:['一','二','三','四','五','六','七','八','九','十','十一','十二'],
				monE:['January','February','March','April','May','June','July','August','September','October','November',
				 'December'],
			}
		},
		// onReady() {
		// 	this.$refs.loading.open()
		// },
		onLoad(){
			let userinfo = this.$store.state.user.info
			// console.log(userinfo)
			this.showLoading = true
			uni.request({
				url:'http://1.15.179.24:9520/record/aiAnalysis?userId='+userinfo.userId,
				method:'GET',
				header:{
					Authorization:userinfo.token
				},
				success: (res) => {
					this.showLoading = false
					console.log(res.data)
					this.text = res.data.data
				},
				fail: (res) => {
					console.log(res)
				}
			})
		},
		methods: {
			
			goBack(){
				uni.navigateBack()
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
	
.box{
	height: 100vh;
	background-image: url('../../static/apply/report/bgi.png');
	background-size: cover;
}
.swiper-item{
	height:100%;
	width: 90%;
	margin: 0 auto;
	background-color: white;
	border-radius: 20px;
}
.itemBox{
	border-bottom: 1px solid deepskyblue;
	height: 33%;
	width: 90%;
	padding: 12px;
	margin: 0 auto;
}
.head{
	display: flex;
}
.box2{
	// border: 4px solid saddlebrown;
	// margin: 0 auto;
	position: absolute;
	top: 300px;
	left: 150px;
	// height: 30px;
	// width: 30px;
	// border: 1px solid saddlebrown;
}
</style>
