<template>
	<view class="content">
		<calculator @submit='submit'></calculator>
	</view>
</template>

<script>
	import calculator from '@/components/calculator/index.vue'
	export default {
		components: {
			calculator
		},
		data() {
			return {}
		},
		onLoad() {

		},
		methods: {
			submit(obj, type) {
				uni.navigateTo({
					url: type === 1 ? '../all-pay/index?data=' + encodeURIComponent(JSON.stringify(obj)) : '../pay/index?data=' + encodeURIComponent(JSON.stringify(obj))
				})
			}
		}
	}
</script>

<style>

</style>
