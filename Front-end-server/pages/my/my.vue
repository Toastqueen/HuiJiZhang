<template>
	<view class="box">
		<view class="head">
			<view class="" style="font-weight: 600; font-size:20px;position: absolute;left: 45%;top: 40px;">
				我的
			</view>
		</view>
		<view class="card" @click="gotoLogin()">
			<image :src="info.avatarUrl" mode="" class="img1"></image>
			<view class="" style="margin-left: 15px;">
				<view class="" style="font-size: 20px;">
					{{info.nickName}}
				</view>
				<view class="" style="font-size: 15px;color:gray;margin-top: 10px;">
					<!-- 今天是你记账第1天 -->
					{{info.text}}
				</view>
			</view>
			<image src="../../static/my/Tab_yonghu.svg" mode="aspectFit" style="height: 40px;width: 40px;margin-left: auto;" @click="gotoLogin"></image>
		</view>
		<view class="middle">
			<!-- 使用循环结构，点击跳转，根据参数到不同页面 -->
			<view v-for="(item,index) in List1" :key="index" class="itemBlock" @click="goTo(index)">
					<image :src="item.image" mode="heightFix" style="height: 40px;"></image>
					<view>{{item.name}}</view>
			</view>
		</view>
		<view class="" style="margin: 20px;margin-left:10px;font-size:25px;font-weight: 700;">
			更多
		</view>
		<view class="foot">
			<view v-for="(item,index) in List2" :key="index" class="itemLine" @click="gotoPage(item.url)" :style="index===3?'border-bottom:white':''">
				<image :src="item.image" mode=""></image>
				<view class="" style="margin-left: 20px;">
					{{item.name}}
				</view>
				<text class="cuIcon-right" style="margin-left: auto;color: gray;"></text>
			</view>
		</view>
		
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				info:{
					avatarUrl:'../../static/my/touxiang.svg',
					nickName:'登录享云同步',
					text:'保障数据安全不丢失',
				},
				List1:[
					{
						image:"../../static/my/01.JPG",
						name:"账户"
					},
					{
						image:"../../static/my/02.JPG",
						name:"预算"
					},
					{
						image:"../../static/my/03.JPG",
						name:"智能识别设置"
					},
					{
						image:"../../static/my/04.JPG",
						name:"数据导出"
					}
				],
				List2:[
					{
						image:"../../static/my/shezhi.png",
						name:"设置",
						url:"/pages/settings/settings"
					},
					{
						image:"../../static/my/bangzhu.png",
						name:"帮助",
						url:"/pages/my/help"
					},
					{
						image:"../../static/my/calc.svg",
						name:"房贷计算器",
						url:"/pages/calculator/index/index"
					},
					{
						image:"../../static/my/licai.svg",
						name:"理财小助手",
						url:"/pages/articles/articles"
					},
				]
			}
		},
		computed:{
			userinfo(){
				return this.$store.state.user.info
			},
			userIsLogin(){
				return this.$store.state.user.isLogin
			}
			
		},
		mounted() {
			// console.log("1.userinfo()",this.userinfo)
			// console.log("2.userIsLogin()",this.userIsLogin)
			let userinfo = this.$store.state.user.info
			let oid = this.$store.state.user.oid
			if(this.userIsLogin === true){
				this.info.nickName= this.userinfo.nickName
				uni.request({
					url:'http://1.15.179.24:9520/user/registerDays/'+oid,
					method:'GET',
					header:{
						Authorization:userinfo.token
					},
					success: (res) => {
						// console.log('获取注册时间')
						console.log(res)
						let days = res.data.data
						this.info.text = `已经记账${days}天,坚持下去!`
						if(res.data.code==401){
							this.$store.commit('changeInfo',{
								userId:'',
								userName:"",
								nickName:'',
								gender:null,
								phoneNumber:'',
								avatarUrl:'',
								days_num:0,
								token:""
							})
							this.$store.commit('change_isLogin',false)
							this.$store.commit('account/clearItem',true)
							this.ShowData()
						}
					}
				})
				this.info.avatarUrl = this.userinfo.avatarUrl
			}else{
				this.ShowData()
			}
		},
		updated() {
			// console.log("1.userinfo()",this.userinfo)
			// console.log("2.userIsLogin()",this.userIsLogin)
			let userinfo = this.$store.state.user.info
			let oid = this.$store.state.user.oid
			if(this.userIsLogin === true){
				this.info.nickName= this.userinfo.nickName
				uni.request({
					url:'http://1.15.179.24:9520/user/registerDays/'+oid,
					method:'GET',
					header:{
						Authorization:userinfo.token
					},
					success: (res) => {
						// console.log('获取注册时间')
						// console.log(res)
						let days = res.data.data
						this.info.text = `已经记账${days}天,坚持下去!`
					}
				})
				this.info.avatarUrl = this.userinfo.avatarUrl
			}else{
				this.ShowData()
			}
		},
		methods: {
			
			goTo(index){
				if(index==0){
					uni.navigateTo({
						url:'/pages/settings/zhanghu_setting'
					})
				}
				if(index==1){
					uni.navigateTo({
						url:'/pages/apply/budget'
					})
				}
				if(index==2){
					uni.navigateTo({
						url:'/pages/settings/identify_setting'
					})
				}
				if(index==3){
					// this.showOut()
					uni.navigateTo({
						url:'/pages/settings/output_setting'
					})
				}
			},
			
			Output(){
				let userinfo = this.$store.state.user.info
				// console.log("导出excel")
				
				uni.request({
					url:'http://1.15.179.24:9520/record/excelDownloadByYearFileUrl?userId='+userinfo.userId+'&&year='+2023,
					header:{
						"Authorization":userinfo.token
					},
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
										// console.log(re2)
										// console.log("打开文档")
									},
									fail: (err) => {
										// console.log(err)
									}
								})		
							}
							
						})
						
						
					}
				})
				
				
			},
			checkFile(){
				uni.getSavedFileList({
				  success: function (res) {
					// console.log(res.fileList);
				  }
				});	
			},
			gotoLogin(){
				//登录状态为false时去登录
				if(!this.$store.state.user.isLogin){
					uni.navigateTo({
						url:"/pages/kitty-BasicLogin/login",
						animationType:"slide-in-right",
						animationDuration:500
					});	
				}
				else{//登录状态为true时去账号设置页面
					uni.navigateTo({
						url:"/pages/settings/zhanghao_setting"
					})
				}
				
			},
			gotoPage(u){
				uni.navigateTo({
					url:u,
					animationType:"slide-in-right",
					animationDuration:400
				});
			},
			ShowData(){
				console.log("打开页面")
				if(this.$store.state.user.isLogin){
					console.log("已经登录了")
					console.log(this.userinfo.nickName)
					this.info.nickName= this.userinfo.nickName
					this.info.text = `已经记账${this.userinfo.days_num}天,坚持下去!`	
					this.info.avatarUrl = this.userinfo.avatarUrl
				}
				else{
					this.info.avatarUrl = '../../static/my/touxiang.svg'
					this.info.nickName= '登录享云同步'
					this.text = '保障数据安全不丢失'
				}
			}
		}
	}
</script>

<style lang="scss">
.box{
	background-color:#F5F5F5;
	min-height: calc(100vh - 80rpx);
	// border: 1px solid rebeccapurple;
	padding-bottom: 30px;
}
.head{
	height: 170px;
	// background-color: #B6DFF8;
	background-image: url('../../static/picture/my.png');
	background-size:100% 100%;
	// background-repeat: no-repeat;
	// background-attachment: fixed;
	// border-radius: 0 0 20px 20px;
}
.card{
	height: 120px;
	width: 86%;
	// border: 1px solid rebeccapurple;
	border-radius: 20px;
	// position: absolute;
	margin-left: 50%;
	transform: translate(-50%,-50%);
	background-color:white;
	box-shadow:1px 1px 3px rgba(0, 0, 0, 0.4) ;
	display: flex;
	align-items: center;
	padding: 10px;
	image{
		height: 80px;
		width: 80px;
		// border: 1px solid saddlebrown;
		border-radius: 50%;
	}
}
.middle{
	margin-top: -30px;
	display: flex;
	// border: 1px saddlebrown solid;
	flex-wrap: wrap;
	background-color: white;
	width: 96%;
	// margin:80px auto;
	margin-left: 2%;
	border-radius: 20px;
	box-shadow:1px 1px 2px rgba(0, 0, 0, 0.3) ;
	padding-top: 10px;
}
.itemBlock{
	width: 25%;
	// border: 1px salmon solid;
	image{
		height: 50px;
		width: 50px;
	}
	display: flex;
	flex-direction: column;
	align-items: center;
	margin-bottom: 15px;
	font-weight: 600;
}
.foot{
	background-color: white;
	margin-left: 2%;
	width: 95%;
	padding: 10px;
	padding-bottom: 0px;
}
.itemLine{
	display: flex;
	image{
		height: 30px;
		width: 30px;
	}
	// border: 1px solid saddlebrown;
	border-bottom: 1px solid lightgray;
	margin-bottom: 10px;
	padding-left: 10px;
	height: 50px;
	// line-height: 50px;
	align-items: center;
	font-size: 20px;
	font-weight: 600;
}

</style>
