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
				导出
			</view>
		</view>
		<view class="outerBox">
			<view class="itemLine">
				<view class="itemName">
					导出本月账单
				</view>
				<image src="../../static/daochu.svg" mode="heightFix" style="margin-left: auto;height: 20px;" @click="output1(true)"></image>
			</view>
			<view class="itemLine">
				<view class="itemName">
					导出本年账单
				</view>
				<image src="../../static/daochu.svg" mode="heightFix" style="margin-left: auto;height: 20px;" @click="output1(false)"></image>
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
			},
			output1(f){
				let year = new Date().getFullYear()
				let mon = new Date().getMonth()+1
				console.log(mon)
				let userinfo = this.$store.state.user.info
				let myUrl = 'http://1.15.179.24:9520/record/excelDownloadByYearFileUrl?userId='+userinfo.userId+'&year='+year
				if(f==true){
					myUrl = 'http://1.15.179.24:9520/record/excelDownloadByMonthFileUrl?userId='+userinfo.userId+'&month='+mon
				}
				
				console.log("导出excel")
				
				uni.request({
					url:myUrl,
					header:{
						"Authorization":userinfo.token
					},
					method:'GET',
					success: (res) => {
						console.log(res)
						console.log(res.data.data)
						console.log(encodeURI(res.data.data))
						uni.downloadFile({
							url:encodeURI(res.data.data),
							success: (res2) => {
								console.log(res2)
								let tmpPath = res2.tempFilePath;
								uni.openDocument({
									filePath:tmpPath,
									// fileType:'xlsx',
									showMenu:true,
									success: (re2) => {
										console.log(re2)
										console.log("打开文档")
									},
									fail: (err) => {
										console.log(err)
									}
								})		
							}
						})
					}
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
