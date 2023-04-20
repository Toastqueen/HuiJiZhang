<template>
	<view class="box">
		<view class="head" style="">
			<view class="" style="" @click="goBack">
				<text class="cuIcon-back"></text>
				<text class="" style="font-size: 16px;">
					返回
				</text> 
			</view>
			<view class="" style="font-weight: 600;font-size: 15px;margin: 0 auto;transform: translate(-50%,0);">
				识别结果
			</view>
		</view>
		
		<view class="" style="width: 90%;height: 150px;background-color: #ECF4F8;margin:0 auto;display: flex;flex-direction: column;margin-top: 4px;border-radius: 10px;padding: 4px;
		box-shadow: 0 0 2px 2px rgba(0, 0, 0, 0.2);">
			<view class="" style="height: 30px;margin: 0 auto;display: flex;align-items: center;">
				<text style="color:#347CAF;font-weight: bolder;margin-right: 6px;">您的图片</text>
				<image src="../../static/identify/1.svg" mode="heightFix" style="height: 20px;"></image>
			</view>
			<view>
				<scroll-view class="scroll-view_H" scroll-x="true" scroll-left="120" >
					<image :src="item" mode="heightFix" style="height: 110px;margin-left: 10px;" v-for="(item,index) in imgArr" @click="clickImage(item)"></image>
				</scroll-view>
			</view>
		</view>
	<uni-calendar
		ref="calendar"
		:insert="false"
		@confirm="confirmDate"
	 />		
	 <!-- <u-picke :show="pickerShow" ref="uPicker" :columns="columns" @confirm="confirmPicker" @change="changePicker"></u-picker> -->
	
	 
	<swiper :indicator-dots="true" :autoplay="false" :interval="3000" @change="change"
	:duration="1000" style="height:calc(100vh - 225px);">
		<swiper-item v-for="(item,index) in objArr" :key="index">		
		<view class="modal">
			<view class="modal_head" style="position: relative;display: flex;margin-bottom: 16px;">
				<image src="../../static/detail/yuanquan.svg" mode="heightFix" style="height: 8px;position: absolute;top: 0;"></image>
				<!-- <image src="../../static/detail/zhangdan.svg" mode="heightFix" style="height: 50px;margin: 0 auto;"></image> -->
				<text style="margin: 0 auto;color: #347CAF;font-weight: bolder;">账本识别结果</text>
				<image src="../../static/detail/yuanquan.svg" mode="heightFix" style="height: 8px;position: absolute;top: 0;right: 0;"></image>
			</view>
			
					
					
					<view class="modal_body"> 
						<picker mode="selector" :range="classArray" @change="bindPickerChange" :value="idx">
							<view class="body_box" style="background-color: #88BCED;">
									<image src="../../static/detail/leixing.svg" mode=""></image>
									<text class="Key">类型</text>
									<view class="Value" style="display: flex;align-items: center;">
											<text>{{myObj.typeName}}</text>
										<!-- <image :src="require(`../../static/items/${typeId<100?typeId:100}.png`)" mode="heightFix" style="height: 25px;background-color: aliceblue;border-radius: 50%;"></image>	 -->
									</view>
								 
							</view>
						</picker>
						<view class="body_box" style="background-color: #9EC56B;">
							<image src="../../static/detail/shijian.svg" mode=""></image>
							<text class="Key">时间</text>
							<text class="Value" @click="showCalendar">
								{{myObj.fullDate}}
							</text>
						</view>
						<view class="body_box" style="background-color: #F9D339;">
							<image src="../../static/detail/zhanghu.svg" mode=""></image>
							<text class="Key">账户</text>
							<text class="Value">
								{{my_account}}
							</text>
						</view>
						<view class="body_box" style="background-color: #DF9199;">
							<image src="../../static/detail/jine.svg" mode=""></image>
							<text class="Key">金额</text>
							<view class="Value" style="width: 50%;display: flex;align-items: center;">
								<input type="text" v-model="myObj.money" style="width:100%;text-align:right">
								<view>元</view>
							</view>
						</view>
						<view class="body_box" style="background-color: #BA8BF8;">
							<image src="../../static/detail/shangjia.svg" mode=""></image>
							<text class="Key">商家</text>
							<view class="Value" style="width: 50%;display: flex;align-items: center;">
								<input type="text" v-model="myObj.seller" style="width:100%;text-align:right">
							</view>
						</view>
						<view class="body_box" style="background-color: #FFC06E;">
							<image src="../../static/detail/beizhu.svg" mode=""></image>
							<text class="Key">备注</text>
							<text style="font-size: 12px;color: gray;margin-left: 10px;">有{{myObj.fileUrls.length}}张图片</text>
							<view class="Value" style="width: 50%;display: flex;align-items: center;">
								<input type="text" v-model="myObj.remark" style="width:100%;text-align:right" placeholder="无">
							</view>
						</view>
						<view class="modal_button" style="display: flex;justify-content: space-around;">
							<view style="width: 30%;height: 34px;border-radius: 14px; line-height: 40px;background-color:#4095E5;display: flex;align-items: center;justify-content: space-evenly;" @click="saveModal(index)">
								<image src="../../static/detail/bianji.svg" mode=""></image>
								<text style="font-size: 16px;color: white;font-weight: 500;">保存</text>
							</view>
							<view style="width: 30%;height: 34px;border-radius: 14px;line-height: 40px;background-color:#B2DDF5;display: flex;align-items: center;justify-content: space-evenly;" @click="deleteModal(index)">
								<image src="../../static/detail/shanchu.svg" mode=""></image>
								<text style="font-size: 16px;color: dodgerblue;font-weight: 500;">清空</text>
							</view>
						</view>	
						
					</view>		
			
		</view>
		</swiper-item>
	</swiper>	
		<view class="" style="background-color: white;padding: 12px;padding-top: 18px; border-radius: 24px 24px 0 0;">
			<view class="" style="display: flex;align-items: center;">
				<text class="" style="background-color:#4095E5;width: 60px;font-size: 14px;color: white;border-radius: 7px;padding: 2px;margin: 6px;">
					识别结果
				</text>	
				<text style="color: #C9C9C9;">
					可选中复制粘贴
				</text>
			</view>
			<text selectable="true" style="font-size: 14px;padding-left: 6px;padding-right: 6px;">
				{{myObj.info}}
			</text>
			
		</view>
	</view>
</template>

<script>
import upload from '../../uni_modules/uview-ui/libs/config/props/upload'
import { data } from '../../uni_modules/uview-ui/libs/mixin/mixin'
	export default {
		data() {
			return {
				show:false,
				idx:0,
				classArray:[],
				typeidArray:[],
				my_account:'默认账户',
				imgArr:[],
				infoArr:[],
				id: '',
				userId: '',
				
				parentId: '',
				parentName: '',
				isPayment: '',
				
				
				fullDate:'',
				
				savedArr:[],
				
				myObj:{
					typeId: '',
					typeName: '',
					money: '',
					remark: "",
					seller: "",
					createTime: "",
					fullDate:'',
					info:'',
					fileUrls: [],
					fileNum:0,
				},
				objArr:[]
			}
		},
		onLoad() {
			
			let arr = []
			let userinfo = this.$store.state.user.info
			uni.request({
				url:'http://1.15.179.24:9520/type/getAllMoneyType?userId='+userinfo.userId,
				method:'GET',
				header:{
					Authorization:userinfo.token
				},
				success: (res) => {
					console.log(res.data.data)
					let datalist =res.data.data
					datalist.forEach((item,i)=>{
						if(item['parent_id']!==0){
							// arr.push({item["typeName"],item['typeId']})
							// arr.push([item["typeName"],item['typeId']])
							arr.push(item["typeName"])
							this.typeidArray.push(item["typeId"])
						}
					})
					console.log('获得金额类型',arr)
					this.classArray = arr
				}
			})
			
			//原来在这里直接上传
			this.uploadFile()
		},
		computed:{
			details(){
				return this.$store.state.account.details
			}
	
		},
		methods: {
			
			async uploadFile(){
				let tmpArr = this.$store.state.account.tmpArr
				uni.showLoading({
					mask:true,
					title:'上传中...'
				})
				// tmpArr = await this.compress();
				// console.log(tmpArr)
				
				
				// console.log(this.$store.state.account.tmpArr)
				
				let month = new Date().getMonth()+1
				let date = new Date().getDate()
				if(month<10){
					month = 0+''+month
				}
				if(date<10){
					date = 0+''+date
				}
				let fulldate = new Date().getFullYear()+'-'+(month)+'-'+date
				
				// let tmpArr = this.$store.state.account.tmpArr
				let token = this.$store.state.user.info.token
				let tot = tmpArr.length
				let userId = this.$store.state.user.info.userId
				let tmpFiles =[]
				for(let i=0;i<tot;i++){
					tmpArr[i] = await this.compress(tmpArr[i]);
					
					console.log('开始上传',i,tmpArr[i])
					uni.uploadFile({
						url:'http://1.15.179.24:9520/user/ImgServer',
						header:{
							"Authorization":token
						},
						// method:"POST",
						filePath:tmpArr[i],
						name:'upFiles',
						success: (res) => {
							let resdata = res.data
							if(JSON.parse(resdata).code==200){
								console.log("上传图床服务器成功");
								console.log(JSON.parse(resdata))
								// this.imgArr.push(JSON.parse(resdata).data[0])//图片url
								this.imgArr.push(JSON.parse(resdata).data[0])
								if(this.imgArr.length == tot){
									uni.hideLoading()
									console.log("图片集合",this.imgArr)//图片集合
									uni.request({
										url:'http://1.15.179.24:9520/record/photoProcessor',
										method:'PUT',
										header:{
											Authorization:token
										},
										data:{
											userId:userId,
											urls:this.imgArr
										},
										success: (res3) => {
											console.log(res3)
											this.infoArr = res3.data.data
											console.log(this.infoArr)
											
											this.infoArr.forEach((item,j)=>{
												let obj={
													typeId: '',
													typeName: '',
													money: '',
													remark: "",
													seller: "",
													createTime: "",
													fullDate:fulldate,
													info:'',
													fileUrls:[]
												}
												if(item.typeId!==null){
													obj.typeId = item.typeId
													obj.typeName =item.typeName
												}
												obj.money = item.money
												obj.info = item.info
												obj.fileUrls = item.fileUrls
												// obj
												if(j==0){
													// this.info = obj.info
													this.myObj = obj
												}
												this.objArr.push(obj)
											})
										}
									})
								return;
								}
							}
						},
						fail: (err) => {
							console.log(err)
						}
					})	
				}
			},
			compress(img){   
				  console.log('开始压缩');
				  let that=this
				  // let img = this.$store.state.account.tmpArr
				  return new Promise((res) => {
						// var localPath = plus.io.convertAbsoluteFileSystem(img);
						plus.io.resolveLocalFileSystemURL(img, (entry) => {      //通过URL参数获取目录对象或文件对象
						entry.file((file) => {       // 可通过entry对象操作图片 
						console.log('压缩前图片信息:' + JSON.stringify(file)); //压缩前图片信息
							  if(file.size > 504800) {   //   如果大于500Kb进行压缩
									plus.zip.compressImage({    // 5+ plus.zip.compressImage 了解一下，有详细的示例
										  src: img,          //src: 压缩原始图片的路径    
										  dst: img.replace('.png', '2222.png').replace('.PNG', '2222.PNG').replace('.jpg','2222.jpg').replace('.JPG','2222.JPG'),
										  width: '40%',      //dst: (String 类型 )压缩转换目标图片的路径，这里先在后面原始名后面加一个2222区分一下
										  height: '40%',     //width,height: (String 类型 )缩放图片的宽度,高度
										  quality: 10,      //quality: (Number 类型 )压缩图片的质量
										  overwrite: true,  //overwrite: (Boolean 类型 )覆盖生成新文件
										  // format:'jpg'   //format: (String 类型 )压缩转换后的图片格式
									}, (event) => {
										  console.log('压缩后图片信息:' + JSON.stringify(event));// 压缩后图片信息
										  // this.imageInfo=event
										  let newImg = event.target;
										  res(newImg);  //返回新的图片地址，在uploadFile之前接收
						 }, function(err) {
						// console.log('Resolve file URL failed: ' + err.message);
						 });
							  }else{//else小于500kb跳过压缩，直接返回现有的src
									res(img);
							  }
						});
						}, (e) => { // 返回错误信息
							  // console.log('Resolve file URL failed: ' + e.message);
						});
				  })
			},
			
			
			goBack(){
				let userinfo = this.$store.state.user.info
				for(let i=0;i<this.objArr.length;i++){
					if(this.savedArr[i]==1){
						console.log(i,this.objArr[i])
						uni.request({
							url:'http://1.15.179.24:9520/record/addMoneyRecord',
							method:'POST',
							header:{
								Authorization:userinfo.token
							},
							data:{
							    "userId":userinfo.userId,
							    "typeId":this.objArr[i].typeId,
							    "money":this.objArr[i].money,
							    "seller":this.objArr[i].seller,
							    "remark":this.objArr[i].remark,
								"createTime":this.objArr[i].fullDate+" 00:00:00",
							    "moneyRecordPicture":{
							        "fileUrls":this.objArr[i].fileUrls
							    }
							},
							success: (res) => {
								console.log("保存当前信息",res)
							}
						})
					}
				}
				uni.navigateBack()
			},
			onBackPress(e){
				console.log("监听到手机返回键。")
				let userinfo = this.$store.state.user.info
				for(let i=0;i<this.objArr.length;i++){
					if(this.savedArr[i]==1){
						console.log(i,this.objArr[i])
						uni.request({
							url:'http://1.15.179.24:9520/record/addMoneyRecord',
							method:'POST',
							header:{
								Authorization:userinfo.token
							},
							data:{
							    "userId":userinfo.userId,
							    "typeId":this.objArr[i].typeId,
							    "money":this.objArr[i].money,
							    "seller":this.objArr[i].seller,
							    "remark":this.objArr[i].remark,
								"createTime":this.objArr[i].fullDate+" 00:00:00",
							    "moneyRecordPicture":{
							        "fileUrls":this.objArr[i].fileUrls
							    }
							},
							success: (res) => {
								console.log("手机返回键，保存当前信息",res)
							}
						})
					}
				}
			},
			
			change(e){
				console.log(e.detail.current)
				// this.info = this.objArr[e.detail.current].info
				this.myObj = this.objArr[e.detail.current]
			},
			 bindPickerChange: function(e) {
				console.log('picker发送选择改变，携带值为', e.detail.value)
				this.idx = e.detail.value
				// let t = this.classArray
				this.myObj.typeName = this.classArray[this.idx]
				this.myObj.typeId = this.typeidArray[this.idx]
				// this.myObj.typeId = t[1][this.idx]
			},
			
			selectClass(){
				// this.pickerShow = true
				
			},
			
			clickImage(url){
				// 从相册选择6张图
				// console.log(this.$store.state.user.info.avatarUrl)
				uni.previewImage({
					urls:[url],
					current: ''
				});
			},
			showCalendar(){
				console.log("弹出日历")
				this.show=true
				this.$refs.calendar.open()
			},
			confirmDate(e){
				console.log('调用日历确认')
				console.log(new Date(e.fulldate))
				console.log(e.fulldate)
				this.myObj.fullDate = e.fulldate
			},
			deleteModal(index){
				this.myObj.money = 0
				this.myObj.seller = ''
			},
			saveModal(index){
				let userinfo = this.$store.state.user.info
				this.objArr[index] = this.myObj
				console.log("保存",index,this.myObj)
				if(this.myObj.money == 0){
					uni.showToast({
						icon:'none',
						title:'请填写金额',
						duration:1000
					})
					return;
				}
				if(this.myObj.typeName == ''){
					uni.showToast({
						icon:'none',
						title:'请选择类型',
						duration:1000
					})
					return;
				}
				//成功保存的存在一个数组中
				this.savedArr[index] = 1;
				console.log("保存记录index",this.objArr[index])
				uni.showToast({
					icon:'none',
					title:'保存成功',
					duration:1000
				})
				
			}
		}
	}
</script>

<style lang="scss">
.box{
	min-height: 100vh;
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
.scroll-view_H {
	white-space: nowrap;
	width: 100%;
}
.scroll-view-item {
	height: 300rpx;
	line-height: 300rpx;
	text-align: center;
	font-size: 36rpx;
}
.scroll-view-item_H {
	display: inline-block;
	width: 30%;
	height: 300rpx;
	line-height: 300rpx;
	text-align: center;
	font-size: 36rpx;
}
.modal{
	
	height: calc(100vh - 280px);
	width: 80%;
	margin: 0 auto;
	margin-top: 16px;
	box-shadow: 0px 1px 2px 2px rgba(0, 0, 0, 0.2);
	background-color: #ECF4F8;
	border-radius: 10px;
	z-index: 5;
	padding: 10px;
	image{
		height: 20px;
		width: 20px;
	}	
	.body_box{
		margin-bottom: 24px;
		height: 40px;
		border-radius: 14px;
		display: flex;
		align-items: center;
		padding-left: 10px;
		padding-right: 6px;
		.Key{
			margin-left: 8px;
			font-weight: 500;
			font-size: 16px;
		}
		.Value{
			margin-left: auto;
			font-weight: 500;
			font-size: 14px;
		}
	}
}

</style>
