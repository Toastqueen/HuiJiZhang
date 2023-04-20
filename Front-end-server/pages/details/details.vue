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
				类别设置
			</view>
		</view>
		<view class="modal">
			
			<view class="modal_head" style="position: relative;display: flex;margin-bottom: 16px;">
				<image src="../../static/detail/yuanquan.svg" mode="heightFix" style="height: 8px;position: absolute;top: 0;"></image>
				<image src="../../static/detail/zhangdan.svg" mode="heightFix" style="height: 50px;margin: 0 auto;"></image>
				<image src="../../static/detail/yuanquan.svg" mode="heightFix" style="height: 8px;position: absolute;top: 0;right: 0;"></image>
			</view>
			
			<uni-calendar
				ref="calendar"
				:insert="false"
				@confirm="confirmDate"
			 />		
			
			<view class="modal_body">
				<view class="body_box" style="background-color: #88BCED;">
					<image src="../../static/detail/leixing.svg" mode=""></image>
					<text class="Key">类型</text>
					<view class="Value" style="display: flex;align-items: center;">
						<text class="">
							{{typeName}}
						</text>
						<image :src="require(`../../static/items/${typeId<100?typeId:100}.png`)" mode="heightFix" style="height: 25px;background-color: aliceblue;border-radius: 50%;"></image>	
					</view>
					
				</view>
				
				<view class="body_box" style="background-color: #9EC56B;">
					<image src="../../static/detail/shijian.svg" mode=""></image>
					<text class="Key">时间</text>
					<text class="Value" @click="showCalendar">
						{{fullDate}}
					</text>
				</view>
				<view class="body_box" style="background-color: #F9D339;">
					<image src="../../static/detail/zhanghu.svg" mode=""></image>
					<text class="Key">账户</text>
					<text class="Value">
						{{accountName}}
					</text>
				</view>
				<view class="body_box" style="background-color: #DF9199;">
					<image src="../../static/detail/jine.svg" mode=""></image>
					<text class="Key">金额</text>
					<view class="Value" style="width: 50%;display: flex;align-items: center;">
						<input type="text" v-model="money" style="width:100%;text-align:right">
						<view>元</view>
					</view>
				</view>
				<view class="body_box" style="background-color: #BA8BF8;">
					<image src="../../static/detail/shangjia.svg" mode=""></image>
					<text class="Key">商家</text>
					<view class="Value" style="width: 50%;display: flex;align-items: center;">
						<input type="text" v-model="seller" style="width:100%;text-align:right">
					</view>
				</view>
				<view class="body_box" style="background-color: #FFC06E;">
					<image src="../../static/detail/beizhu.svg" mode=""></image>
					<text class="Key">备注</text>
					<text style="font-size: 12px;color: gray;margin-left: 10px;" @click="lookPicture">有{{fileNum}}张图片</text>
					<view class="Value" style="width: 50%;display: flex;align-items: center;">
						<input type="text" v-model="remark" style="width:100%;text-align:right" placeholder="无">
					</view>
				</view>
			</view>
			<view class="modal_button" style="display: flex;justify-content: space-around;">
				<view style="width: 30%;height: 34px;border-radius: 14px; line-height: 40px;background-color:#4095E5;display: flex;align-items: center;" @click="saveModal">
					<image src="../../static/detail/bianji.svg" mode=""></image>
					<text style="font-size: 16px;color: white;font-weight: 500;">保存</text>
				</view>
				<view style="width: 30%;height: 34px;border-radius: 14px;line-height: 40px;background-color:#B2DDF5;display: flex;align-items: center;" @click="showDelete">
					<image src="../../static/detail/shanchu.svg" mode=""></image>
					<text style="font-size: 16px;color: dodgerblue;font-weight: 500;">删除</text>
				</view>
			</view>
			<u-modal :show="show" :content="content" :showCancelButton="true" @confirm="DeleteModal" @cancel="show=false"></u-modal>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				show:false,
				content:'确定删除这条记账记录吗',
				
				my_account:'默认账户',
				id: '',
				userId: '',
				typeId: '',
				typeName: '',
				parentId: '',
				parentName: '',
				isPayment: '',
				money: '',
				remark: "",
				seller: "",
				createTime: "",
				info: null,
				fileUrls: [],
				fileNum:'',
				fullDate:''
			}
		},
			
		onShow() {
			console.log(this.$store.state.account.details)
			let detail = this.$store.state.account.details
			this.id = detail.id
			this.userId = detail.userId
			this.typeId = detail.typeId
			this.typeName = detail.typeName
			this.parentId=detail.parentId
			this.parentName=detail.parentName
			this.isPayment=detail.isPayment
			this.money=detail.money
			this.remark=detail.remark
			this.seller=detail.seller
			this.createTime=detail.createTime
			this.info=detail.info
			this.fileUrls = detail.fileUrls
			this.fileNum = this.fileUrls.length
			this.fullDate = this.createTime.split(" ")[0]
			console.log(this.fullDate)
		},
		computed:{
			details(){
				return this.$store.state.account.details
			},
			accountName(){
				return this.$store.state.user.accountName
			}
		},
		methods: {
			lookPicture(){
				uni.previewImage({
					urls:this.fileUrls,
					current: ''
				});
			},
			goBack(){
				uni.navigateBack()
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
				this.fullDate = e.fulldate
			},
			showDelete(){
				this.show= true
			},
			saveModal(){
				let userinfo = this.$store.state.user.info
				let id = this.id
				let typeId = this.typeId
				let money=this.money
				let seller=this.seller
				let remark=this.remark
				let createTime=this.fullDate+' 00:00:00'
				
				uni.request({
					url:'http://1.15.179.24:9520/record/updateMoneyRecord',
					method:'POST',
					header:{
						Authorization:userinfo.token
					},
					data:{
						id:id,
						typeId:typeId,
						money:money,
						seller:seller,
						remark:remark,
						createTime:createTime
					},
					success: (res) => {
						console.log("修改记账记录:")
						console.log(res)
					}
				})
			},
			DeleteModal(){
				let userinfo = this.$store.state.user.info
				let id = this.id
				uni.request({
					url:'http://1.15.179.24:9520/record/deleteMoneyRecord?recordId='+id,
					method:'PUT',
					header:{
						Authorization:userinfo.token
					},
					success: (res) => {
						console.log("删除该记账记录:")
						console.log(res)
					}
				})
				this.show = false
				uni.navigateBack()
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
.modal{
	
	height: calc(100vh - 150px);
	width: 80%;
	margin: 0 auto;
	margin-top: 30px;
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
