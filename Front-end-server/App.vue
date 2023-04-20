<script>
	export default {
		onLaunch: function() {
			uni.hideTabBar()
			console.log('App Launch')
			//尝试提取本地存储
			try{
				const value = uni.getStorageSync("info");
				if(value){
					this.$store.commit('account/InitInfo',value)
				}
			}catch(e){
				//TODO handle the exception
				console.log("提取信息失败......")
			}
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style>
	
</style>
