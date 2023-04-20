<template>
	<view class="container">
		<view class="header-group">
			<view class="group" :class="getClass(index,active)" @tap='tabsClick(item,index)'
				:style="index === active ? 'background: #FFFFFF; border-radius: 15rpx 15rpx 0 0; border-bottom: 2px solid #FFFFFF' : 'background: #409EFF;color: #FFFFFF;'"
				v-for="(item,index) in list" :key='index'>
				{{item}}
			</view>
		</view>
		<view class="">
			<components :is="currentComponent" @submit='submit' ref="Form" />
		</view>
		<view class="btn">
			<button class="reset">重置</button>
			<button class="save" @tap="save">确认</button>
		</view>
	</view>
</template>

<script>
	import business from '@/components/calculator/business.vue'
	import accumulation from '@/components/calculator/accumulation.vue'
	import group from '@/components/calculator/group.vue'
	export default {
		components: {
			business,
			accumulation,
			group
		},
		data() {
			return {
				list: [
					'商业贷款',
					'公积金贷款',
					'组合贷款'
				],
				active: 0,
				currentComponent: business
			}
		},
		onLoad() {

		},
		methods: {
			submit(obj, type) {
				this.$emit('submit', obj,type)
			},
			tabsClick(item, index) {
				this.active = index
				if (index === 0) {
					this.currentComponent = business
					return
				}
				if (index === 1) {
					this.currentComponent = accumulation
					return
				}
				if (index === 2) {
					this.currentComponent = group
					return
				}
			},
			getClass(index, active) {
				if (active === 0) {
					if (index === 2) {
						return 'right-bottom'
					}
				}
				if (active === 1) {
					if (index === 0) {
						return 'center-left-bottom'
					}
					if (index === 2) {
						return 'center-right-bottom'
					}
				}
				if (active === 2) {
					if (index === 0) {
						return 'left-bottom'
					}
				}
			},
			save() {
				this.$refs.Form.submit()
			}
		}
	}
</script>

<style lang="scss" scoped>
	.container {
		height: 100vh;
		
		overflow-y: hidden;
		background-color: #8CC5FF;
		background-image: linear-gradient(#8CC5FF, #8CC5FF);
		padding: 45rpx 30rpx;
		padding-top: 50px;
		.header-group {
			display: flex;
			background: #409EFF;
			border-top-right-radius: 15rpx;
			border-top-left-radius: 15rpx;
			.group {
				width: 34%;
				height: 100rpx;
				display: flex;
				justify-content: center;
				align-items: center;
			}
		}
	}

	.right-bottom {
		border-radius: 0 15rpx 0 0;
	}

	.left-bottom {
		border-radius: 15rpx 0 0 0;
	}

	.center-left-bottom {
		border-radius: 15rpx 15rpx 0 0;
	}

	.center-right-bottom {
		border-radius: 15rpx 15rpx 0 0;
	}

	.btn {
		margin-top: 50rpx;
		display: flex;

		.reset {
			width: 40%;
			border-radius: 30rpx;
		}

		.save {
			width: 40%;
			border-radius: 30rpx;
			background-color: #67C23A;
			color: #FFFFFF;

			&::after {
				border: none !important;
			}
		}
	}
</style>
