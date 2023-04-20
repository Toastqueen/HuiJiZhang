<template>
	<view class="box">
		<view class="head">
			<!-- -->
			<view class="" style="position: absolute;display: flex;align-items: center;" @click="goBack">
				<view class="cuIcon-back"></view>
				<view class="" style="font-size: 17px;">
					返回
				</view> 
			</view>
			<view class="" style="margin: 0 auto;font-weight: 600;">
				账号设置
			</view>
		</view>
		<view class="outerBox">
			<view class="itemLine" style="height: 80px;" @click="changeAvatar">
				<view class="itemName">
					头像
				</view>
				<image :src="info.avatarUrl" mode="" class="content"></image>
				<view class="cuIcon-right"></view>
			</view>	
			<view class="itemLine">
				<view class="itemName">
					ID
				</view>
				<view class="content">
					{{userinfo.userId}}
				</view>
				<view class="cuIcon-right"></view>
			</view>		
			<view class="itemLine" >
				<view class="itemName">
					昵称
				</view>
				<view class="content">
					{{userinfo.nickName}}
				</view>
				<view class="cuIcon-right"></view>
			</view>		
			<view class="itemLine" @click="showModal(2)">
				<view class="itemName">
					性别
				</view>
				<view class="content">
					{{userinfo.gender}}
				</view>
				<view class="cuIcon-right"></view>
			</view>
			<view class="itemLine" @click="showModal(3)">
				<view class="itemName">
					年龄
				</view>
				<view class="content">
					{{userinfo.age}}
				</view>
				<view class="cuIcon-right"></view>
			</view>
			<view class="itemLine" style="margin-bottom: 30px;" @click="goToTel">
				<view class="itemName">
					手机号
				</view>
				<view class="content">
					{{userinfo.phoneNumber}}
				</view>
				<view class="cuIcon-right"></view>
			</view>			
		</view>
		<view class="outerBox" style="margin-bottom: 30px;">
			<view class="itemLine" @click="goToZhanghu">
				<view class="itemName">
					查看全部账户
				</view>
				<view class="cuIcon-right"></view>
			</view>
			<view class="itemLine" @click="goToPwd">
				<view class="itemName">
					密码设置
				</view>
				<view class="cuIcon-right"></view>
			</view>			
		</view>
		
		

		<view class="itemLine" style="margin: 0;width: 100%;border-bottom: white;" @click="Logout">
			<view class="itemName" style="margin: 0 auto;">
				退出登录
			</view>
		</view>
		
		<view class="modal" v-show="ifShow==true" style="display: flex;flex-direction: column;padding: 10px;">
			<view class="">
				<view class="" style="font-weight: 600;">
					{{keyName}}
				</view>
				<input type="text" v-model="value" style="border-bottom: 1px solid gray;margin-top: 10px;margin-bottom: 10px;">
			</view>
			<view class="" style="display: flex;margin-top: auto;">
				<button style="width: 40%;" @click="ifShow=false">取消</button>
				<button style="width: 40%;" @click="saveModal">确认</button>
			</view>
		</view>
		
	</view>
</template>

<script>
	import { pathToBase64 } from '../../js_sdk/mmmm-image-tools/index.js';
	export default {
		data() {
			return {
				ifShow:false,
				keyName:'',
				value:'',
				key:'',
				info:{
					avatarUrl:'../../static/my/touxiang.svg',
					
				},
				gender:'',
				age:'',
				tel:''
			}
		},
		computed:{
			userinfo(){
				return this.$store.state.user.info
			}
		},
		onShow(){
			console.log("信息",this.$store.state.user)
			if(this.$store.state.user.isLogin){
				this.info.avatarUrl = this.userinfo.avatarUrl
			}
			this.gender = this.userinfo.gender
			this.age = this.userinfo.age
			this.tel = this.userinfo.phoneNumber
		},
		methods: {
			goToPwd(){
				uni.navigateTo({
					url:'/pages/settings/pwd_setting'
				})
			},
			goToZhanghu(){
				uni.navigateTo({
					url:'/pages/settings/zhanghu_setting'
				})
			},
			goToTel(){
				uni.navigateTo({
					url:'/pages/settings/tel_setting'
				})
			},
			saveModal(){
				this.ifShow= false
				let userinfo = this.userinfo
				let oid = this.$store.state.user.oid
				if(this.key==2){
					this.gender = this.value
					this.$store.commit('change_gender',this.value)
				}
				if(this.key==3){
					this.age = this.value
					this.$store.commit('change_age',this.value)
				}
				if(this.key==4){
					this.tel = this.value
					this.$store.commit('change_phoneNumber',this.value)
				}
				console.log("更改信息后",this.userinfo)
				uni.request({
					url:'http://1.15.179.24:9520/user/updateUser',
					method:'POST',
					header:{
						Authorization:userinfo.token
					},
					data:{
						userId: oid, 
						phoneNumber: this.tel, 
						gender: this.gender, 
						age:this.age 
					},
					success: (res) => {
						console.log(res)
					}
				})
			},
			showModal(key){
				this.key = key
				console.log(key)
			
				if(key==2){
					this.keyName = '性别'
					this.value = this.userinfo.gender
				}
				if(key==3){
					this.keyName = '年龄'
					this.value = this.userinfo.age
				}
				if(key==4){
					this.keyName = '手机号'
					this.value = this.userinfo.phoneNumber
				}
				this.ifShow = true
			},
			
			goBack(){
				uni.navigateBack({
					animationType:"slide-out-right",
					animationDuration:500
				})
			},
			Logout(){
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
				this.$store.commit('change_accountName','')
				this.$store.commit('change_oid','')
				this.$store.commit('account/clearItem',true)
				uni.navigateBack();
			},
			changeAvatar(){
				let that = this;
				
				let oid = this.$store.state.user.oid
				console.log(this.userinfo)
				// this.userinfo.avatarUrl = "blob:http://localhost:8080/a68ad990-d2ce-41af-8cbb-f7b494dd5f3b"
				uni.chooseImage({
					count:1,
					success: (res) => {
						
						console.log(res.tempFilePaths[0])
						that.info.avatarUrl = res.tempFilePaths[0]
						let savadFile = ''
						uni.saveFile({
							tempFilePath:res.tempFilePaths[0],
							success: (saveRes) => {
								console.log("保存文件到本地")
								savadFile = saveRes.savedFilePath
								console.log(saveRes.savedFilePath)
								that.$store.commit('change_avatarUrl',savadFile)
								// console.log(that.userinfo.avatarUrl)
								console.log(that.userinfo)
								// console.log(JSON.stringify(res.tempFilePaths));
								let token = that.userinfo.token
								let avatarUrl = savadFile
								// let userId = that.userinfo.userId
								// console.log(token)
								console.log(avatarUrl)
								console.log(JSON.stringify(avatarUrl))
								// console.log(userId)
								
								uni.uploadFile({
									url:'http://1.15.179.24:9520/user/upLoadPicture',
									header:{
										// "Content-Type":"multipart/form-data",
										//不要上面这一行终于能成功了
										"Authorization":token
									},
									// method:"POST",
									filePath:avatarUrl,
									name:'upFiles',
									formData:{
										// upFiles:JSON.stringify(avatarUrl),
										userId:oid
									},
									success: (res) => {
										console.log("%%%%%%%%%%%%%%%%%")
										let resdata = res.data
										console.log(resdata)
										console.log(JSON.parse(resdata))
										console.log(JSON.parse(resdata).code)//经过转成json数据才能拿到
										if(JSON.parse(resdata).code==200){
											console.log("上传图片成功");
											return;
										}
										else{
											console.log("上传失败")
											return;
										}
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
	image{
		height: 50px;
		width: 50px;
	}
	.content{
		// margin-left: auto;
		// margin-right: 20px;
		position: absolute;
		right: 15%;
		color: gray;
	}
	.itemName{
		margin-left: 5px;
		font-weight: 600;
		// font-family: SimHei;
	}
	.cuIcon-right{
		margin-left: auto;
		margin-right: 10px;
		color: gray;
	}
}
.modal{
		z-index: 10;
		border: 1px solid #F5F5F5;
		background-color: white;
		border-radius: 20px;
		box-shadow: 0 0 1px 1px rgba(0,0,0,0.2);
		position: absolute;
		top:50%;
		left:50%;
		transform: translate(-50%,-50%);
		height: 150px;
		width: 80%;
}
</style>
