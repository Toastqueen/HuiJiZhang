<template>
	<view class="container">
		<view class="content">
			<view class="left-label">
				<slot name="label"></slot>
			</view>
			<view class="right-form">
				<slot name="input"></slot>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		components: {
		},
		data() {
			return {}
		},
		onLoad() {
	
		},
		methods: {}
	}
</script>

<style lang="scss" scoped>
	.content {
		display: flex;
		min-height: 100rpx;
		position: relative;

		&::before {
			content: '';
			position: absolute;
			background-color: #979797;
			width: 90%;
			height: 1rpx;
			bottom: 0;
			transform: translate(-50%);
			left: 50%;
		}

		.left-label {
			width: 30%;
			display: flex;
			justify-content: center;
			align-items: center;
			font-size: 28rpx;
		}

		.right-form {
			width: 70%;
			display: flex;
			justify-content: flex-end;
			align-items: center;
			font-size: 28rpx;
			padding-right: 35rpx;
		}
	}
</style>
