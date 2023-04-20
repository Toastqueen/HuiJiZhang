<template>
	<view class="busin-container">
		<skeleton>
			<template slot="label">
				<p>公积金贷款金额</p>
			</template>
			<template slot="input">
				<view class="input-class">
					<input class="uni-input" v-model="form.grand" type="number" maxlength="10" placeholder="请输入单价" />
					<view class="">
						万元
					</view>
				</view>
			</template>
		</skeleton>
		<skeleton>
			<template slot="label">
				<p>商业贷款金额</p>
			</template>
			<template slot="input">
				<view class="input-class">
					<input class="uni-input" v-model="form.amount" type="number" maxlength="10" placeholder="请输入房屋面积" />
					<view class="">
						万元
					</view>
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
				<picker @change="grandBindPickerChange" :value="rateIndex" :range="rateList" range-key="value">
					<view style="padding: 20rpx;background-color: white;">{{ rateList[rateIndex].value }}</view>
				</picker>
				<view class="ico_down">
				</view>
			</template>
		</skeleton>
		<view class="dengyu">
			= {{form.grandRate}} %
		</view>
		<skeleton>
			<template slot="label">
				<p>商贷利率方式</p>
			</template>
			<template slot="input">
				<radio-group @change="radioRateChange">
					<label style="margin-right: 30rpx;" v-for="(item, index) in rateArr" :key="item.value">
						<radio :value="item.value" :checked="index === current" /><text
							class="radio-class">{{item.name}}</text>
					</label>
				</radio-group>
			</template>
		</skeleton>
		<skeleton v-if="form.rate === '1'">
			<template slot="label">
				<p>商贷利率</p>
			</template>
			<template slot="input">
				<picker @change="loanBindPickerChange" :value="loanIndex" :range="loanList" range-key="value">
					<view style="padding: 20rpx;background-color: white;">{{ loanList[loanIndex].value }}</view>
				</picker>
				<view class="ico_down">
				</view>
			</template>
		</skeleton>
		<view class="content" v-else>
			<view class="left-label">
				商贷利率
			</view>
			<view class="right-form">
				<view class="right-form-rate">
					<view class="right-form-rate-left">
						LPR
					</view>
					<view class="right-form-rate-right">
						<input class="uni-input" @input='LprInput' v-model="form.lpr" type="number" maxlength="10" />
						<view class="">
							%
						</view>
					</view>
				</view>
				<view class="right-form-rate">
					<view class="right-form-rate-left">
						基点
					</view>
					<view class="right-form-rate-right">
						<input class="uni-input" @input='pointInput' v-model="form.point" type="number"
							maxlength="10" />
						<view class="">
							‱
						</view>
					</view>
				</view>
			</view>
		</view>
		<view class="dengyu" v-if="form.rate === '1'">
			= {{form.rateNum}} %
		</view>
		<view class="dengyu" v-else>
			= {{form.rateLpr}} %
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
				loanList: [{
						key: 1,
						value: "基准利率(4.9%)"
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
				loanIndex: 0,
				amount: 33.4,
				form: {
					// 计算方式
					rate: '1',
					// 公积金贷款金额
					grand: '',
					// 商贷金额
					amount: '',
					// 年限
					years: 240,
					// 公积金基准利率 显示参数
					grandRate: 3.25,
					// 公积金基准利率 
					grandRateShow: 0.0325,
					// 商贷基准利率 显示利率
					rateNum: 0.49,
					// 商贷基准利率
					baseRate: 0.049,
					// lpr显示利率
					rateLpr: 4.65,
					// lpr 利率
					lpr: 4.65,
					// 基点
					point: null,
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
						sloanMoney: this.form.amount,
						gloanMoney: this.form.grand,
						smonths: this.form.years,
						gmonths: this.form.years,
						srate: this.form.rate === '1' ? this.form.baseRate : Number(this.form.rateLpr) / 100,
						grate: this.form.grandRateShow
					}
					this.$emit('submit', obj, 1)
				}
			},
			ruls() {
				if (!this.form.grand) {
					uni.showToast({
						title: '请输入公积金贷款金额',
						icon: 'none'
					})
					return false
				}
				if (!this.form.amount) {
					uni.showToast({
						title: '请输入商贷贷款金额',
						icon: 'none'
					})
					return false
				}
				return true
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
			// 计算方式勾选事件
			radioRateChange(val) {
				this.form.rate = val.detail.value
			},
			// 贷款年限切换事件
			yearBindPickerChange(e) {
				this.yearIndex = e.detail.value;
				this.form.years = this.yearArray[e.detail.value].key
			},
			// 公积金基准利率
			grandBindPickerChange(e) {
				this.form.grandRateShow = 0.0325 * this.rateList[e.detail.value].key
				this.form.grandRate = (0.0325 * this.rateList[e.detail.value].key * 100).toFixed(2)
				this.rateIndex = e.detail.value;
			},
			// 商贷基准利率
			loanBindPickerChange(e) {
				this.form.baseRate = 0.049 * this.rateList[e.detail.value].key
				this.form.rateNum = (0.049 * this.rateList[e.detail.value].key * 100).toFixed(2)
				this.loanIndex = e.detail.value;
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
