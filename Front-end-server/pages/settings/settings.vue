<template>
	<view class="box">
		<view class="head">
			<view class="" style="position: absolute;display: flex;align-items: center;" @click="goBack">
				<view class="cuIcon-back"></view>
				<view class="" style="font-size: 17px;">
					返回
				</view> 
			</view>
			<view class="" style="margin: 0 auto;font-weight: 600;">
				设置
			</view>
		</view>
		<view class="itemLine" @click="gotoPage('/pages/settings/zhanghao_setting')">
			<image src="../../static/my/settings/zhanghao.png" mode=""></image>
			<view class="itemName">
				账号设置
			</view>
			<view class="cuIcon-right"></view>
		</view>
		<view class="headline">
			个性化设置
		</view>
		<view class="itemLine" v-for="(item,index1) in list1" :key="index1+'-id1'" @click="gotoPage(item.url)">
			<image :src="item.img" mode=""></image>
			<view class="itemName">
				{{item.name}}
			</view>
			<view class="cuIcon-right"></view>
		</view>
		<view class="headline">
			功能设置
		</view>
		<view class="itemLine" v-for="(item,index2) in list2" :key="index2+'-id2'" @click="gotoPage(item.url)">
			<image :src="item.img" mode=""></image>
			<view class="itemName">
				{{item.name}}
			</view>
			<view class="cuIcon-right"></view>
		</view>
		<view class="headline">
			安全设置
		</view>
		<view class="itemLine" v-for="(item,index3) in list3" :key="index3+'-id3'" @click="gotoPage(item.url)">
			<image :src="item.img" mode=""></image>
			<view class="itemName">
				{{item.name}}
			</view>
			<view class="cuIcon-right"></view>
		</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				list1:[
					{
						name:'智能识别',
						img:'../../static/my/settings/gexinghua.png',
						url:'/pages/settings/identify_setting'
					},
					{
						name:'声音、触感与消息通知',
						img:'../../static/my/settings/shengyin.png',
						url:'/pages/settings/voice_setting'
					}
				],
				list2:[
					{
						name:'类别设置',
						img:'../../static/my/settings/leibie.png',
						url:'/pages/settings/class_setting'
					},
					
					{
						name:'账户设置',
						img:'../../static/my/settings/zhanghu.png',
						url:'/pages/settings/zhanghu_setting'
					}
				],
				list3:[
					{
						name:'密码保护',
						img:'../../static/my/settings/mima.png',
						url:'/pages/settings/pwd_setting'
					},
					{
						name:'数据导出',
						img:'../../static/my/settings/shuju.png',
						url:'/pages/settings/output_setting'
					}
				]
			}
		},
		methods: {
			gotoPage(u){
				console.log(u),
				uni.navigateTo({
					url:u,
					animationType:"slide-in-right",
					animationDuration:500
				})
			},
			goBack(){
				uni.navigateBack({
					delta:3,
					animationType:"slide-out-right",
					animationDuration:500
				})
			}
		}
	}
</script>

<style lang="scss">
.box{
	min-height: 100vh;
	background-color: #F5F5F5;
}
.head{
	height: 75px;
	background-color: #B6DFF8;
	display: flex;
	align-items: flex-end;
	padding-bottom: 10px;
	.text{
		position:absolute;
		// border: 1px solid saddlebrown;
		text-align: center;
		height: 30px;
		width: 100%;
		margin-top: 45px;
		font-weight: bold;
	}
}
.headline{
	font-size: 15px;
	font-weight: 600;
	margin: 10px;
}
.itemLine{
	display: flex;
	// border: 1px solid salmon;
	height: 50px;
	align-items: center;
	background-color: white;
	padding-left: 10px;
	padding-right: 10px;
	border-bottom: 1px solid lightgray;
	image{
		height: 30px;
		width: 30px;
	}
	.itemName{
		margin-left: 15px;
	}
	.cuIcon-right{
		margin-left: auto;
	}
}
</style>
