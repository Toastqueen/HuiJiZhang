<template>
	<view class="box" style="display: flex;flex-direction: column;">
		
		<view class="" style="display: flex;margin-top: 40px;font-size: 18px;align-items: center;">
			<view class="cuIcon-back" @tap="goBack">
			</view>
			<view class="" style="font-weight: 700;margin: 0 auto;">
				元宇宙世界
			</view>
		</view>
		
		<view class="" style="font-size: 24px;font-weight: 600;margin: 0 auto;margin-top: 50px;">
			生成你的元宇宙形象
		</view>
		<view class="" style="margin: 0 auto;margin-top: 14px;font-size: 28px;color: dodgerblue;font-weight: 600;">
			{{name[i]}}
		</view>
		<image :src="require(`../../static/apply/universe/${i+1}.png`)" mode="widthFix" width="100%" style="margin: 0 auto;margin-top: -30px;"></image>
		<view class="" style="margin: 0 auto;font-size: 22px;font-weight: 600;white-space: pre-wrap;">
			{{text[i]}}
		</view>
		<Loading26 class="box2" v-show="showLoading"></Loading26>
	</view>
</template>

<script>
	import Loading26 from "@/components/loading26.vue";
	export default {
		components:{
			Loading26
		},
		data() {
			return {
				showLoading:false,
				i:0,
				name:['美食家','休闲行家','购物狂魔','学习达人','身体强壮','月月有余','常鳞凡介'],
				text:['口腹之欲, 美食所归\n精选细作, 美味传神',
				'红尘中人乐不休\n浮生百态收眼底',
				'一直以为清空购物车是全删\n原来是全买',
				'读书不觉已春深\n一寸光阴一寸金',
				'肌肉紧实如山峦\n步履矫健似江湖',
				'理财无管晏\n言利析秋毫',
				'春去秋来岁月长\n只愿平凡不负忙']
			}
		},
		onLoad() {
			this.showLoading = true
			let userinfo = this.$store.state.user.info
			uni.request({
				url:'http://1.15.179.24:9520/record/userFigureTab?userId='+userinfo.userId,
				method:'GET',
				header:{
					Authorization:userinfo.token
				},
				success: (res) => {
					console.log(res)
					this.showLoading = false
					if(res.data.code==200){
						this.i = res.data.data-1
					}
					else{
						this.i=0
					}
				},
				fail: (err) => {
					this.i=0
				}
			})
			
		},
		methods: {
			goBack(){
				uni.navigateBack()
			}
		}
	}
</script >

<style lang="scss">

.box{
	padding:20px;
	min-height: 100vh;
	background-image: url('../../static/apply/universe/bgi.png');
	background-size: cover;
}
.box2{
	position: absolute;
	top: 300px;
	left: 150px;
}

</style>
