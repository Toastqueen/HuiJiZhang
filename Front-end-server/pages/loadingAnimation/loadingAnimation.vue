<template>
	<view class="container">
		<view class="list">
			<view class="item" v-for="(item,index) in list">
				{{item}}
			</view>
			<view class="loading">{{loadingText}}</view>
		</view>
		<!--loading-->
		<view class="light-loading" v-if="LoadingModel">
			<view class="box">
				<view class="h2">Loading</view>
				<view class="span"></view>
				<view class="span"></view>
				<view class="span"></view>
				<view class="span"></view>
				<view class="span"></view>
				<view class="span"></view>
			</view>
		</view>
		<!--loading-->
	</view>
</template>
<script>
	var _self, page = 1, timer = null;
	export default {
		data() {
			return {
				list:[1,2,3,4,5,6,7,8,9,10],
				loadingText:"",
				LoadingModel:false,
			}
		},
		onLoad() {
			_self = this;
	
			this.getList();
		},
		onPullDownRefresh:function(){
			this.getList();
		},
		onReachBottom:function(){
			if(timer != null){
				clearTimeout(timer);
			}
			_self.LoadingModel = true
			_self.loadingText = '加载中...';
			
			timer = setTimeout(function(){
				_self.getMore();
			}, 800);
			
		},
		methods: {
			/* more */
			getMore : function(){
				// _self.loadingText = '加载中...';
				uni.showNavigationBarLoading();
				
				setTimeout(function(){
					_self.LoadingModel = false
				},500)
				
				console.log(page)
				// uni.request({
				//     url: '',
				// 	   data:{page:page},
				//     method: 'GET',
				//     success: function(res){
				//      _self.loadingText = '';
				//      if(res.data == null){
				//       uni.hideNavigationBarLoading();
				//       _self.loadingText = '已加载全部';
				//       return false;
				//      }
						page++;
				//      console.log(res);
						var obj = [11,12,13]
						_self.list = _self.list.concat(obj);
				//      _self.loadingText = '加载更多';
				//      uni.hideNavigationBarLoading();
				//     }
				// });
				
				
			},
			/* list */
			getList : function(){
			   page = 1;
			   uni.showNavigationBarLoading();
			   
			   /*  
			   uni.request({
					url: '',
					method: 'GET',
					data:{page:page},
					success: function(res){
						
						uni.hideNavigationBarLoading();
						uni.stopPullDownRefresh();
				*/		
						page++;
						_self.loadingText = '加载更多';
				/* 		
					},
				})
				*/
			},
			/* end */
		}
	}
</script>

<style lang="less">

</style>
