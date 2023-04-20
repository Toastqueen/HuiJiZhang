<template>
	<view class="busin-container">
		<skeleton>
			<template slot="label">
				<p>计算方式</p>
			</template>
			<template slot="input">
				<radio-group @change="radioPriceChange">
					<label style="margin-right: 30rpx;" v-for="(item, index) in items" :key="item.value">
						<radio :value="item.value" :checked="index === current" /><text
							class="radio-class">{{item.name}}</text>
					</label>
				</radio-group>
			</template>
		</skeleton>
		<skeleton v-if="form.way !== '1'">
			<template slot="label">
				<p>单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价</p>
			</template>
			<template slot="input">
				<view class="input-class">
					<input class="uni-input" v-model="form.unit" @input="unitInput" type="number" maxlength="10"
						placeholder="请输入单价" />
					<view class="">
						元/㎡
					</view>
				</view>
			</template>
		</skeleton>
		<skeleton v-if="form.way !== '1'">
			<template slot="label">
				<p>面&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;积</p>
			</template>
			<template slot="input">
				<view class="input-class">
					<input class="uni-input" v-model="form.area" @input="areaInput" type="number" maxlength="10"
						placeholder="请输入房屋面积" />
					<view class="">
						㎡
					</view>
				</view>
			</template>
		</skeleton>
		<skeleton>
			<template slot="label">
				<p>房屋总价</p>
			</template>
			<template slot="input">
				<view class="input-class">
					<input class="uni-input" @input='totalPriceInput' v-model="form.totalPrice"
						:disabled="form.way !== '1'" type="number" maxlength="10" placeholder="请输入房屋总价" />
					<view class="">
						万元
					</view>
				</view>
			</template>
		</skeleton>
		<skeleton>
			<template slot="label">
				<p>贷款成数</p>
			</template>
			<template slot="input">
				<picker @change="bindPickerChange" :value="index" :range="array" range-key="name">
					<view style="padding: 20rpx;background-color: white;">{{ array[index].name }}</view>
				</picker>
				<view class="ico_down">
				</view>
			</template>
		</skeleton>
		<skeleton>
			<template slot="label">
				<p>贷款金额</p>
			</template>
			<template slot="input">
				<view class="">
					{{form.amount}} 万元
				</view>
			</template>
		</skeleton>
		<skeleton>
			<template slot="label">
				<p>贷款年限</p>
			</template>
			<template slot="input">
				<picker @change="yearBindPickerChange" :value="yearIndex" :range="yearArray" range-key="name">
					<view style="padding: 20rpx;background-color: white;">{{ yearArray[yearIndex].name }}</view>
				</picker>
				<view class="ico_down">
				</view>
			</template>
		</skeleton>
		<skeleton>
			<template slot="label">
				<p>公积金利率</p>
			</template>
			<template slot="input">
				<picker @change="rateBindPickerChange" :value="rateIndex" :range="rateList" range-key="value">
					<view style="padding: 20rpx;background-color: white;">{{ rateList[rateIndex].value }}</view>
				</picker>
				<view class="ico_down">
				</view>
			</template>
		</skeleton>
		<view class="dengyu">
			= {{form.rateNum}} %
		</view>
	</view>
</template>

<script>
	import skeleton from '@/components/calculator/skeleton.vue'
	export default {
		components: {
			skeleton
		},
		data() {
			return {
				items: [{
						value: '1',
						name: '按房屋总价计算'
					},
					{
						value: '2',
						name: '按单价与面积计算'
					}
				],
				current: 0,
				array: [{
						value: 0.2,
						name: '二成'
					}, {
						value: 0.3,
						name: '三成'
					}, {
						value: 0.4,
						name: '四成'
					}, {
						value: 0.5,
						name: '五成'
					},
					{
						value: 0.6,
						name: '六成'
					}, {
						value: 0.7,
						name: '七成'
					}, {
						value: 0.8,
						name: '八成'
					}, {
						value: 0.9,
						name: '九成'
					}
				],
				rateList: [{
						key: 1,
						value: "基准利率(3.25%)"
					},
					{
						key: 0.7,
						value: "基准利率7折"
					},
					{
						key: 0.85,
						value: "基准利率85折"
					},
					{
						key: 0.88,
						value: "基准利率88折"
					},
					{
						key: 0.9,
						value: "基准利率9折"
					},
					{
						key: 0.95,
						value: "基准利率95折"
					},
					{
						key: 1.05,
						value: "基准利率1.05倍"
					},
					{
						key: 1.1,
						value: "基准利率1.1倍"
					},
					{
						key: 1.2,
						value: "基准利率1.2倍"
					},
					{
						key: 1.3,
						value: "基准利率1.3倍"
					}
				],
				yearArray: [],
				yearIndex: 19,
				index: 5,
				rateIndex: 0,
				amount: 33.4,
				form: {
					// 计算方式
					way: '1',
					rate: '1',
					// 房屋总价
					totalPrice: null,
					// 贷款层数
					percentage: 0.7,
					// 贷款金额
					amount: null,
					// 年限 * 12
					years: 240,
					// 显示利率
					rateNum: 3.25,
					// 基准利率
					baseRate: 0.0325,
					// lpr显示利率
					rateLpr: 3.25,
					// lpr 利率
					lpr: 3.25,
					// 基点
					point: null,
					// 单价
					unit: null,
					// 面积
					area: null

				},
				rateArr: [{
						value: '1',
						name: '基准利率'
					},
					{
						value: '2',
						name: 'LRP基础利率'
					}
				],
				current: 0
			}
		},
		watch: {
			form: {
				handler: function(newvalue) {
					console.log(newvalue);
				},
				deep: true //deep置为true表示：对象中任一属性值发生变化，都会触发handler中的方法
			}
		},
		created() {
			for (let i = 1; i <= 30; i++) {
				this.yearArray.push({
					key: i * 12,
					name: `${i}年`
				})
			}
		},
		onLoad() {

		},
		methods: {
			submit() {
				if (this.ruls()) {
					const obj = {
						totalPrice: this.form.totalPrice,
						loanMoney: this.form.amount,
						months: this.form.years,
						rate: this.form.baseRate,
						payMoney: ((1 - this.form.percentage) * this.form.totalPrice).toFixed(2),
						type: this.form.way
					}
					this.$emit('submit', obj)
				}
			},
			ruls() {
				if (this.form.way === '2') {
					if (!this.form.unit) {
						uni.showToast({
							title: '请输入单价',
							icon: 'none'
						})
						return false
					}
					if (!this.form.area) {
						uni.showToast({
							title: '请输入面积',
							icon: 'none'
						})
						return false
					}
					return true
				}
				if (!this.form.totalPrice) {
					uni.showToast({
						title: '请输入总价',
						icon: 'none'
					})
					return false
				}
				if (!this.form.amount) {
					uni.showToast({
						title: '请输入贷款金额',
						icon: 'none'
					})
					return false
				}

				return true
			},
			// 房屋总价输入框事件
			totalPriceInput(val) {
				this.form.amount = (Number(val.detail.value) * this.form.percentage).toFixed(2)
			},
			// lpr
			LprInput(val) {
				console.log(Number(val.detail.value), 'Lpr')
			},
			//  基点
			pointInput(val) {
				if (this.form.lpr) {
					this.form.rateLpr = (this.form.lpr + Number(val.detail.value) / 100).toFixed(2)
				} else {
					this.form.rateLpr = (Number(val.detail.value) / 100).toFixed(2)
				}
			},
			unitInput(val) {
				if (this.form.area) {
					this.form.totalPrice = (this.form.area * Number(val.detail.value)) / 10000
					this.form.amount = (this.form.totalPrice * this.form.percentage).toFixed(2)
				}
			},
			areaInput(val) {
				if (this.form.unit) {
					this.form.totalPrice = (this.form.unit * Number(val.detail.value)) / 10000
					this.form.amount = (this.form.totalPrice * this.form.percentage).toFixed(2)
				}
			},
			// 计算方式勾选事件
			radioPriceChange(val) {
				this.form.way = val.detail.value
			},
			// // 计算方式勾选事件
			// radioRateChange(val) {
			// 	this.form.rate = val.detail.value
			// },
			// 贷款层数切换事件
			bindPickerChange(e) {
				this.index = e.detail.value;
				this.form.percentage = this.array[e.detail.value].value
				this.form.amount = (this.array[e.detail.value].value * this.form.totalPrice).toFixed(2)
			},
			// 贷款年限切换事件
			yearBindPickerChange(e) {
				this.yearIndex = e.detail.value;
				this.form.years = this.yearArray[e.detail.value].key
			},
			// 基准利率
			rateBindPickerChange(e) {
				this.form.baseRate = 0.0325 * this.rateList[e.detail.value].key
				this.form.rateNum = (0.0325 * this.rateList[e.detail.value].key * 100).toFixed(2)
				this.rateIndex = e.detail.value;
			}

		}
	}
</script>

<style lang="scss" scoped>
	.busin-container {
		min-height: 900rpx;
		background-color: #FFFFFF;
	}

	/deep/ .uni-radio-input {
		width: 24rpx;
		height: 24rpx;
	}

	.radio-class {
		font-size: 24rpx;
	}

	.input-class {
		display: flex;
		align-items: center;
		border-radius: 10rpx;
		padding: 0 20rpx;
		height: 60rpx;
		min-width: 400rpx;
		background-color: #F1F1F1;
	}

	.ico_down {
		float: top;
		width: 0;
		height: 0;
		border: 12rpx solid transparent;
		border-top-color: #B0B0B0;
	}

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
			// display: flex;
			// justify-content: flex-end;
			// align-items: center;
			font-size: 28rpx;
			padding-right: 35rpx;
		}
	}

	.right-form-rate {
		display: flex;
		align-items: center;
		justify-content: center;
		height: 80rpx;

		.right-form-rate-left {
			width: 40%;
			text-align: center;
		}

		.right-form-rate-right {
			display: flex;
			justify-content: center;
			align-items: center;
			width: 60%;
			height: 50rpx;
			padding: 0 20rpx;
			background-color: #F1F1F1;
		}
	}

	.dengyu {
		text-align: right;
		padding: 30rpx;
	}
</style>
