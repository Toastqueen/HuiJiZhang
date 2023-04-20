<template>
	<view class="box">
		<!-- 这是记账组件 -->
		<!-- <cusTab class="bottom-box"></cusTab> -->
		<scroll-view scroll-x class="nav text-center" style="height: 100px ;background-color: lightblue;padding-top: 60px;z-index: 8;">
			<view class="cu-item" style="font-size: 40rpx;"
			:class="index==current?'cur':''" v-for="(item,index) in tablist" :key="index" @tap="tabSelect" :data-id="index">
				{{item.name}}
			</view>
			<view class="setting" style="">
				<image src="../../static/my/shezhi3.svg" mode="widthFix" @click="goToClassSetting"></image>
			</view>
		</scroll-view>
		
		<!-- <view class="container">
			<text class="dialog-text text-lg">今日总收支：{{NumberVal}} 元 </text>
		</view> -->
		
		
		<!-- 模态框 -->
		<uni-calendar 
			ref="calendar"
			:insert="false"
			@confirm="confirmDate"
		 />		
		<view class="modal" v-show="IfshowModal">
			<view class="modal_head">
				<image :src="Item_src" mode="heightFix"></image>
				<view class="">
					{{Item_name}}
				</view>
			</view>
			<view class="modal_body">
				<view class="body_box">
					<view class="box_text">消费时间</view>
					<view class="flex box_line" @click="showCalendar" style="background-color:#89BEF7;">
						<view class="">
							{{my_date}}
						</view>
						<view class="cuIcon-calendar" style="margin-left:auto;margin-right: 10%;"></view>
					</view>

				</view>
				<view class="body_box">
					<view class="box_text">账户</view>
					<view class="flex box_line" style="background-color: #87D169;">
						<view class="">
							{{accountName}}
						</view>
						<!-- <view class="cuIcon-writefill" style="margin-left:auto;margin-right: 10%;"></view> -->
					</view>
				</view>
				<view class="body_box">
					<view class="box_text">金额</view>
					<view class="flex box_line" style="background-color: #F7DB50;">
						<input type="number" placeholder="必填" v-model="money">
					</view>
				</view>
				<view class="body_box">
					<view class="box_text">消费商家</view>
					<view class="flex box_line" style="background-color: #E5BED4;">
						<input type="text" placeholder="选填" v-model="seller">
					</view>
				</view>
				<view class="body_box">
					<view class="box_text">备注</view>
					<view class="flex box_line" style="background-color: #FFBF84;">
						<input type="text" placeholder="选填" v-model="remark">
					</view>
				</view>
			</view>
			<view class="modal_button flex">
				<button type="default" style="width: 30%;height: 40px;line-height: 40px;" @click="cancelModal">取消</button>
				<button type="primary" style="width: 30%;height: 40px;line-height: 40px;background-color:deepskyblue;" @click="saveModal">保存</button>
			</view>
		</view>
		<!-- 遮罩 -->
		<view class="overlay" v-show="IfshowModal">
			
		</view>
		
		<swiper :current="current" :duration="200" @change="change" class="swiper" style="padding-bottom: 14px;">
			<swiper-item >
				<scroll-view scroll-y style="margin-bottom: 10px;" class="scroll">
					<!-- 支出，numStatus设置为-1  -->
						<view class="paid" @tap="numStatus=-1" >
							<!-- 拍照识别部分 -->
							<view class="photo" @click="takeMyPhotos" >
								<view class="pic1">
									<image src="../../static/items/photo/back2.png" mode="widthFix"></image>
								</view>
								<view class="pic2">
									<image src="../../static/items/photo/photo.png"></image>
								</view>
								<view class="text-1">
									拍照识别
								</view>
								<view class="text-2">
									-智能输入-
								</view>
							</view>
							
					
							<view class="" v-for="(payItem,payIndex) in myData.pay" :key="payIndex" style="margin-bottom: 10px;">
								<view class="ibox" >
									<view class="" style="display: flex;justify-content: center;">
										<image :src="require(`../../static/items/${payItem.typeId<100?payItem.typeId:100}.png`)" mode="" style="height: 40rpx;width: 40rpx;padding: 0;background-color: transparent;"></image>
										<text class="title" style="line-height: 40rpx;">{{payItem.typeName}}</text>
									</view>
									<view class="cu-list grid col-5 no-border" style="padding-bottom: 0;">
										<view class="cu-item" v-for="(item,index) in payItem.subList"  @click="showMyModal(item)" style="padding-bottom: 0;">
											<view class="Aicon">
												<image :src="require(`../../static/items/${item.typeId<100?item.typeId:100}.png`)" mode=""></image>
												<!-- <img :src="item.src" alt=""> -->
												<text>{{item.typeName}}</text>
											</view>
										</view>
									</view>	
								</view>	
							</view>
							
						</view>	
				</scroll-view>	
			</swiper-item>
			
			<swiper-item>
				<scroll-view scroll-y="true" >
					<view class="gain" @tap="numStatus=1">
						<view class="photo" @tap="takeMyPhotos">
							<view class="pic1">
								<image src="../../static/items/photo/back2.png" mode="widthFix"></image>
							</view>
							<view class="pic2">
								<image src="../../static/items/photo/photo.png"></image>
							</view>
							<view class="text-1">
								拍照识别
							</view>
							<view class="text-2">
								-智能输入-
							</view>
						</view>
						<view class="ibox" style="margin-bottom: 10px;">
							<!-- <text class="title">日常</text> -->
							<view class="cu-list grid col-3 no-border" style="">
								<view class="cu-item" v-for="(gainItem,gainIndex) in myData.gain" :key="gainIndex"  @click="showMyModal(gainItem)">
									<view class="Aicon">
										<image :src="require(`../../static/items/${gainItem.typeId<100?gainItem.typeId:100}.png`)" mode=""></image>
										<!-- <img :src="item.src" alt=""> -->
										<text>{{gainItem.typeName}}</text>
									</view>
								</view>
							</view>	
						</view>
					</view>	
				</scroll-view>
			</swiper-item>
		</swiper>



	</view>
</template>

<script>
	// import cusTab from '../../components/cusTab.vue';
	export default {
		name:"account",
		components:{
			// cusTab
		},
		data() {
			return {
				swiperheight:1000,
				now:new Date(),
				my_date:new Date().getFullYear()+'-'+(new Date().getMonth()+1)+'-'+new Date().getDate(),
				my_account:'默认账户',
				money:'',
				seller:'',
				remark:'',
				mymy:123,
				myData:{
					pay:[],
					gain:[]
				},
				
				show:false,
				mode:'date',
				IfshowModal:0,
				Item_name:'',
				Item_src:'',
				Item_typeId:'',
				Item_parentId:'',
				Item_type:'',
				
				numStatus:-1,
				value: '',
				NumberVal:0,
				
				TabCur: 0,
				scrollLeft: 0,
				current:0,
				modalName: null,
				tablist:[
					{
						name:"支出"
					},
					{
						name:"收入"
					}
				],
				list11:[
					{
						src:"../../static/items/14.png",
						name:"餐饮"
					},
					{
						src:"../../static/items/15.png",
						name:"零食"
					},
					{
						src:"../../static/items/16.png",
						name:"果蔬"
					}
				]
			}
		},
		computed:{
			userinfo(){
				return this.$store.state.user.info
			},
			accountName(){
				return this.$store.state.user.accountName
			}
		},
		onLoad() {
			uni.getSystemInfo({
				success:(res)=>{
					// 这个减100，看个人需求可以不要，
					//这里我需要和底部隔开一定距离，变相来讲就是让这个swiper少一点高度，
					let height = res.windowHeight - uni.upx2px(300)  
					console.log("记账界面高度:",height);
					this.swiperheight = height;
				}
			})
		},
		onShow() {
			// this.mymy = 2023
			// 首先获取 支出大类，收入大类
			// 获取金额类别信息
			let userinfo = this.$store.state.user.info
			let Pay_Arr = [];
			let Gain_Arr = [];
			uni.request({
				url:'http://1.15.179.24:9520/type/getAllMoneyType?userId='+userinfo.userId,
				method:'GET',
				header:{
					Authorization:userinfo.token//注意token 别过期了
				},
				success: (res) => {
					console.log(res.data.data)//得到 数组
					let datalist = res.data.data
					datalist.forEach((item, i) => {
					  // console.log(item, i + 1);
					  if (item["parent_id"] == 0) {
					    // console.log("大类:" + item["typeName"]);
					    if (item["type"] === "支出") {
					      Pay_Arr.push({ ...item, subList: [] });
					    } else {
					      Gain_Arr.push(item);
					    }
					  } else {
					    // console.log("小类:" + item["typeName"]);
					    // console.log(item["parent_id"]);
					    let idx = item["parent_id"];
					    Pay_Arr.forEach((newitem, j) => {
					      if (newitem["typeId"] === idx) {
					        newitem.subList.push(item);
					      }
					    });
					  }
					});
					// console.log(Pay_Arr);
					// console.log(Gain_Arr);
					// console.log("更新前:",this.myData)
					this.myData.pay = Pay_Arr
					this.myData.gain =Gain_Arr
					// console.log(this.myData.pay)
				}
			})
		},
		methods: {
			clearInfo(){
				this.Item_name = ''
				this.Item_src = ''
				this.money=''
				this.seller=''
				this.remark=''
				let month = new Date().getMonth()+1
				let date = new Date().getDate()
				console.log(month,typeof month)
				if(month<10){
					month = 0+''+month
				}
				if(date<10){
					date = 0+''+date
				}
				this.my_date = new Date().getFullYear()+'-'+(month)+'-'+date
				console.log(this.my_date,typeof this.my_date)
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
				this.my_date = e.fulldate
			},
			takeMyPhotos(){
				console.log("开启智能识别")
				let tmpArr = []
				let imgArr = [] //
				let tot =0
				let token = this.userinfo.token
				let userId = this.userinfo.userId
				uni.chooseImage({
					count:5,
					sizeType:['compressed'],
					success: (res) => {
						console.log(res.tempFilePaths)
						tmpArr = res.tempFilePaths//临时图片路径
						
						this.$store.commit('account/InitTmpArr',tmpArr)
						uni.navigateTo({
							url:'/pages/identify/identify'
						})
					}
				})
			},
			showMyModal(e) {
				this.clearInfo()
				this.IfshowModal=1
				console.log(e)//
				this.Item_name = e.typeName                            //类别名字
				this.Item_src = `../../static/items/${e.typeId}.png`   //类别图片
				this.Item_typeId = e.typeId
				this.Item_parentId = e.parent_id
				this.Item_type = e.type
				
				console.log("showMymodal",this.Item_name,this.Item_src)
				// this.modalName = 'myDialog'
				let userinfo = this.userinfo
				// console.log("userinfo展示",userinfo)
				
			},
			cancelModal(){
				this.IfshowModal = 0
				this.clearInfo()
			},
			saveModal(){
				// console.log("保存信息：",this.my_date,this.money,this.seller,this.remark)   // 时间，金额 ，卖家 ，备注
				if(this.money === ''){
					uni.showToast({
						icon:'error',
						title:'金额不能为空',
						duration:1000
					})
				}
				else if(this.money === '0'){
					uni.showToast({
						icon:'none',
						title:'请输入大于0的金额',
						duration:1000
					})
				}
				else{
					this.IfshowModal = 0
					let edate = this.my_date
					let emoney = this.money
					let eseller =this.seller
					let eremark = this.remark
					let ename = this.Item_name
					let esrc = this.Item_src
					let eid = this.Item_typeId
					let eparentid = this.Item_parentId
					let etype = this.Item_type
					let now  = new Date()
					// console.log(edate,typeof edate)
					console.log("调试日期：",edate)
					// console.log(new Date(edate).getDay())//获取星期几
					// console.log(Date.parse(edate))
					// console.log(Date.parse('2023-03-15'))
					// console.log(Date.parse('2023-3-15'))
					this.$store.dispatch('account/addItem',{
						name:ename,
						typeId:eid,
						parent_id:eparentid,
						type:etype,
						src:esrc,
						money:emoney,
						seller:eseller,
						remark:eremark,
						timeSeconds:Date.parse(edate),//作为时间戳比较
						year:edate.split('-')[0],
						month:edate.split('-')[1],
						date:edate.split('-')[2],
						day:new Date(edate).getDay(),
						fullDate:edate
					})
					let userinfo = this.userinfo
					uni.request({
						url:'http://1.15.179.24:9520/record/addMoneyRecord',
						method:'POST',
						header:{
							Authorization:userinfo.token
						},
						data:{
						    "userId":userinfo.userId,
						    "typeId":eid,
						    "money":emoney,
						    "seller":eseller,
						    "remark":eremark,
							"createTime":edate+" 00:00:00",
						    "moneyRecordPicture":{
						        "fileUrls":[]
						    }
						},
						success: (res) => {
							console.log(res)
						}
					})
					uni.setStorage({
						key:"info",
						data:JSON.stringify(this.$store.state.account.item),
						success() {
							uni.showToast({
								duration:1000,
								title:'成功记账',
								icon:'success'
							})
						}
					})
					this.clearInfo()
					
				}
				
			},
			onBackPress(e){
				console.log("监听到返回。")
				if(this.IfshowModal){
					this.IfshowModal = 0
					this.clearInfo()
					return true
				}
			},
			
			goToClassSetting(){
				uni.navigateTo({
					url:"/pages/settings/class_setting"
				})
			},
			
			
			tabSelect(e) {
				this.current = e.currentTarget.dataset.id;
				// this.scrollLeft = (e.currentTarget.dataset.id - 1) * 60
			},
			change(e){
				this.current = e.detail.current
			},
			inputDialogToggle() {
				this.$refs.inputDialog.open()
			},
			dialogClose() {
				console.log('点击关闭')
			},
			dialogInputConfirm(val) {
				if(isNaN(val)||val===''){
					// uni.showModal({
					// 	title:"请输入数字"
					// })
					this.showMyModal()
				}
				else{
					// uni.showLoading({
					// 	title: '输入成功'
					// })	
					setTimeout(() => {
						uni.hideLoading()
						console.log(val)
						this.value = val
						// 关闭窗口后，恢复默认内容
						this.$refs.inputDialog.close()
					}, 100)	
				}
				
				
			}
		}
	}
</script>

<style lang="scss">


.box{
	// padding-bottom: 100rpx;
	height: 100vh;
	
	.swiper{
		height:calc(100vh - 100px);
		// border: 3px solid sandybrown;
	}
	.scroll{
		height: 100%;
	}
	.setting{
		// position: absolute;
		height: 50px;
		width: 50px;
		// border: 1px solid saddlebrown;
		margin-top: -50px;
		margin-left: auto;
		margin-right: 10px;
		image{
			background-color: transparent;
		}
	}
	.modal{
		position: absolute;
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%);
		height: 60%;
		width: 80%;
		background-color: white;
		border-radius: 20px;
		z-index: 10;
		.modal_head{
			background-color: #D5F4FF;
			border-radius: 20px 20px 0 0;
			height: 16%;
			display: flex;
			flex-direction: column;
			align-items: center;
			font-size: 16px;
			font-family: SimHei;
			image{
				margin: 7px;
				// border: 1px solid sandybrown;
				// width: 0px;
			}
		}
	}	
	.overlay {
	  position: absolute;
	  top: 0;
	  left: 0;
	  width: 100%;
	  height: 100%;
	  background-color: rgba(0, 0, 0, 0.2);
	  // backdrop-filter: blur(1px);
	  z-index: 9;
	}
	padding-bottom: 100rpx;
	margin-bottom: 50px;
	// background-color: #80D1C8;
	// background-image: linear-gradient(to right top, #5e88c2, #3f89ba, #1f88ae, #00869f, #00838f, #1c8b8b, #359385, #4d9a7e, #75ae85, #9cc28d, #c5d599, #eee8a9);
	// background-image: linear-gradient(to right top, #7cb0e0, #a2aae1, #c4a4d6, #dd9fc4, #ea9fad, #eea6a7, #f0aea2, #efb7a0, #f3c3ad, #f7cfba, #fbdac8, #ffe6d6);
	background-color: #f8f8f8;
	.cur{
		color: white;
		// border: px solid red;
		border-radius: 20rpx;
		background: gray;
	}
	.photo{
		// margin-top: 10px;
		position: relative;
		width: 90%;
		left: 5%;
		margin-top: 5px;
		.pic1{
			// width: 92%;
			// height: 140px;
			// overflow: hidden;
			z-index: 0;
			// border-radius: 14px;
			// margin-left: 4%;
			// border:1px solid seagreen;
			image{
				// transform: rotate(90deg);
				width: 100%;
				height: 100%;
				// border:1px solid saddlebrown;
				// margin: -120px 80px;
				background-color: transparent;
				// border:1px solid salmon;
				padding: 0;
				border-radius: 12px;
				// left: 1%;
			}
		}
		.pic2{
			z-index: 5;
			position: absolute;
			// border: 1px solid rebeccapurple;
			top: 10%;
			right: 5%;
			image{
				// top: -140px;
				// left: 160%;
				transform: rotate(15deg);
				width: 140px;
				height: 140px;
				background-color: transparent;
			}
		}
		.text-1{
			position: absolute;
			font-size:  35px;
			color: #f8f8f8;
			font-weight: bold;
			top: 20px;
			left: 40px;
			z-index: 5;
		}
		.text-2{
			position: absolute;
			font-size:  20px;
			color: #f8f8f8;
			// font-weight: bold;
			top: 75px;
			left: 70px;
			z-index: 5;
		}
	}
	.ibox{
		box-shadow: 1px 2px 1px 2px rgba(0, 0, 0, 0.2);
		background-color: white;
		
		// border: 2px solid rebeccapurple;
		border-radius: 13rpx;
		margin: 20rpx auto;
		width: 90%;
		
		padding-bottom: 10rpx;
		padding-top: 20rpx;
		.cu-list{
			padding-bottom: 0;
			.cu-item{
				padding-bottom: 0;
			}
		}
	}
	.title{
		color: #B39958;
		font-size: 30rpx;
		// margin-left: 40rpx;
		// margin-top: 30rpx;
	}
	image{
		// border: 1px solid rebeccapurple;
		background-color: #E6F4F1;
		// background-color: white;
		border-radius: 50%;
		width: 95rpx;
		height: 95rpx;
		padding: 10rpx;
		
	}
	.box_text{
		font-weight: bold;
	}
	.body_box{
		margin-left: 10%;
		margin-bottom: 10px;
	}
	.box_line{
		// border: 1px saddlebrown solid;
		border-radius: 10px;
		margin-top: 9px;
		height: 30px;
		align-items: center;
		padding-left: 10px;
		width: 85%;
		color: gray;
	}
	
}
</style>
