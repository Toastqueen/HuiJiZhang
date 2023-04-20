<template>
	<view class="container">
		<view class="container-page">
			<view class="" style="padding: 45rpx 30rpx;">
				<view class="header-group">
					<view class="group" :class="getClass(index,active)" @tap='tabsClick(item,index)'
						:style="index === active ? 'background: #FFFFFF; border-radius: 15rpx 15rpx 0 0; border-bottom: 2px solid #FFFFFF' : 'background: #409EFF;color: #FFFFFF;'"
						v-for="(item,index) in list" :key='index'>
						{{item}}
					</view>
				</view>
				<view class="container-body">
					<view class="container-body-head">
						计算结果使用利率:公积金基准利率{{grate * 100}}%商贷基准利率{{srate * 100}}%
					</view>
					<view class="container-body-title">
						{{active ? '首期还款': '每月还款'}}
					</view>
					<view class="repayment-money">
						<text class="text1">{{monthPay}}</text>
						<text class="text2">元</text>
						<text class="text3">共{{gmonths}}期</text>
					</view>
					<view class="amount-tips">
						{{this.lineOne}}
					</view>
					<view class="by-the-month" @tap="open()" v-if="active">
						查看每期还款明细
					</view>
					<view class="tabel">
						<view class="tabel-one">
							首付金额 ----
						</view>
						<view class="tabel-one">
							贷款金额 {{ringList[0].value}}万
						</view>
					</view>
					<view class="tabel">
						<view class="tabel-one">
							支付利息 {{ringList[1].value}}万
						</view>
						<view class="tabel-one">
							贷还款总额 {{houseTotalPrice.value}}万
						</view>
					</view>
					<view class="bottom-tips">
						注：此计算记过仅供参考,实际缴费应以当地为准
					</view>
				</view>
				<view class="">
					<button type="default" class="buttom-class">重新计算</button>
				</view>
			</view>
		</view>
		<uni-popup ref="popup" background-color="#fff" type="bottom">
			<view class="">
				<view class="popup-body-head">
					<view class="popup-body-num">
						期数
					</view>
					<view class="popup-body-amount">
						还款金额
					</view>
				</view>
				<scroll-view class="popup-body" scroll-y>
					<view class="popup-body-day" v-for="(item,index) in perList"
						:style="index%2 === 0 ? 'background-color: #F8F8F8;': ''">
						<view class="popup-body-num">
							第{{item.type}}期
						</view>
						<view class="popup-body-amount">
							{{item.money}}
						</view>
					</view>
				</scroll-view>
			</view>

		</uni-popup>
	</view>
</template>
<script>
	import LoanCaculate from "@/components/calculator/LoanCaculate.js"
	export default {
		components: {},
		data() {
			return {
				selectedItem: 0,
				lineOne: "每月还款额固定，所还总利息较多，适合收入稳定者。",
				ringList: [],
				houseTotalPrice: "",
				sloanMoney: 0,
				gloanMoney: 0,
				payMoney: 0,
				smonths: 0,
				gmonths: 0,
				monthPay: 0,
				srate: 0,
				grate: 0,
				monthDecMoney: 0,
				lessYear: 0,
				moreYear: 0,
				afterPay: 0,
				noEq: false,
				noSame: false,
				lastMonth: 0,
				lastMonthpay: 0,
				lastLess: 0,
				is: true,
				list: [
					'等额本息',
					'等额本金',
				],
				active: 0,
				perList: []
			}
		},
		onLoad(option) {
			const data = JSON.parse(decodeURIComponent(option.data));
			this.sloanMoney = Number(data.sloanMoney) * 10000;
			this.gloanMoney = Number(data.gloanMoney) * 10000;
			this.totalPrice = this.sloanMoney + this.gloanMoney; //贷款总价
			this.smonths = data.smonths;
			this.gmonths = data.gmonths;
			this.srate = data.srate;
			this.grate = data.grate;

			// true 等额本息 false 等额本金
			this.caculateAcpi(this.active)
		},
		mounted() {
			// this.totalPrice= this.$route.query.sloanMoney

		},
		methods: {
			open() {
				// 第一期金额 monthPay  期数 months 递减金额 monthDecMone 存在精度丢失问题 仅参考
				this.perList = []
				let num = 1
				let connt = Number(this.monthPay).toFixed(2)
				this.perList.push({
					'type': num,
					'money': connt
				})
				for (let i = 2; i <= this.gmonths; i++) {
					num = i
					connt = connt - Number(this.monthDecMoney)
					this.perList.push({
						'type': num,
						'money': connt.toFixed(2)
					})
				}

				// 通过组件定义的ref调用uni-popup方法 ,如果传入参数 ，type 属性将失效 ，仅支持 ['top','left','bottom','right','center']
				this.$refs.popup.open()
			},
			tabsClick(item, index) {
				this.active = index
				this.caculateAcpi(this.active)
			},
			getClass(index, active) {
				if (active === 0) {
					if (index === 1) {
						return 'right-bottom'
					}
				}
				if (active === 1) {
					if (index === 0) {
						return 'left-bottom'
					}
				}
			},
			caculateAcpi(isAcpi) {
				let totalMonthpay = 0;
				let smonthPay = 0;
				let totalPay = 0;
				let gmonthPay = 0;
				if (!isAcpi) {
					smonthPay = LoanCaculate.Acpi(this.srate, this.sloanMoney, this.smonths); //商贷月付
					gmonthPay = LoanCaculate.Acpi(this.grate, this.gloanMoney, this.gmonths); //商贷月付
					totalMonthpay = smonthPay + gmonthPay;
					totalPay = smonthPay * this.smonths + gmonthPay * this.gmonths;
					if (this.smonths != this.gmonths) {
						this.noEq = true;
						this.lessYear = (Math.min(this.smonths, this.gmonths));
						this.moreYear = (Math.abs(this.smonths - this.gmonths));
						if (this.smonths > this.gmonths) {
							this.afterPay = smonthPay.toFixed(0);
						} else {
							this.afterPay = gmonthPay.toFixed(0);
						}
					};
					this.lineOne = "每月还款额固定,所还总利息较多"
				} else {
					for (let month = 0; month < this.smonths; month++) {
						smonthPay = LoanCaculate.AverageCapital(this.srate, this.sloanMoney, this.smonths, month) //商业贷款月付
						totalPay += smonthPay; //商贷共付
					}
					for (let month = 0; month < this.gmonths; month++) {
						gmonthPay = LoanCaculate.AverageCapital(this.grate, this.gloanMoney, this.gmonths, month) //公积金贷款月付
						totalPay += gmonthPay; //公积金共付
					}
					if (this.smonths != this.gmonths) {
						this.noSame = true;
						this.lastMonth = Math.min(this.smonths, this.gmonths) + 1;

						if (this.smonths > this.gmonths) {
							this.lastMonthpay = parseInt(LoanCaculate.AverageCapital(this.srate, this.sloanMoney, this
								.smonths, this.lastMonth));
							this.lastLess = (LoanCaculate.AverageCapital(this.srate, this.sloanMoney, this.smonths, 0) -
								LoanCaculate.AverageCapital(this.srate, this.sloanMoney, this.smonths, 1)).toFixed(2);
						} else {
							this.lastMonthpay = parseInt(LoanCaculate.AverageCapital(this.grate, this.gloanMoney, this
								.gmonths, this.lastMonth));
							this.lastLess = (LoanCaculate.AverageCapital(this.grate, this.gloanMoney, this.gmonths, 0) -
								LoanCaculate.AverageCapital(this.grate, this.gloanMoney, this.gmonths, 1)).toFixed(2);

						}
					}
					totalMonthpay = LoanCaculate.AverageCapital(this.srate, this.sloanMoney, this.smonths, 0) +
						LoanCaculate.AverageCapital(this.grate, this.gloanMoney, this.gmonths, 0);
					let nextmonthPay = LoanCaculate.AverageCapital(this.srate, this.sloanMoney, this.smonths, 1) +
						LoanCaculate.AverageCapital(this.grate, this.gloanMoney, this.gmonths, 1);
					this.monthDecMoney = (totalMonthpay - nextmonthPay).toFixed(2);
					this.lineOne = `前期还款额较大,每月还款额递减 ${this.monthDecMoney}`
				}
				//console.log("月供",Math.ceil(monthPay));
				this.monthPay = totalMonthpay.toFixed(2);

				//console.log("总还款",Math.ceil(totalPay));
				let totalInterest = totalPay - this.sloanMoney - this.gloanMoney;
				//console.log("总利息",Math.ceil(totalInterest));
				this.houseTotalPrice = {
					name: "还款总价",
					value: (totalPay / 10000).toFixed(2)
				} //还款总价,cuowu
				this.ringList = [
					// { name: "首付金额", value: (~~this.payMoney).toFixed(2), color: "#6EBFFF" },
					{
						name: "贷款总价",
						value: (this.totalPrice / 10000).toFixed(2),
						unit: "万元",
						color: "#FFDA7C"
					},
					{
						name: "支付利息",
						value: (totalInterest / 10000).toFixed(2),
						unit: "万元",
						color: "#FF70A0"
					}
				]
			}
		}
	}
</script>


<style lang="scss" scoped>
	.container-page {
		height: 100vh;
		overflow-y: hidden;
		background-color: #8CC5FF;
		background-image: linear-gradient(#8CC5FF, #8CC5FF);

		.header-group {
			display: flex;
			background: #409EFF;
			border-top-right-radius: 15rpx;
			border-top-left-radius: 15rpx;

			.group {
				width: 50%;
				height: 100rpx;
				display: flex;
				justify-content: center;
				align-items: center;
			}
		}

		.container-body {
			background-color: #FFFFFF;
			padding-bottom: 30rpx;

			.container-body-head {
				background-color: #F6F6F6;
				font-size: 26rpx;
				color: #979797;
				text-align: center;
				line-height: 60rpx;
			}

			.container-body-title {
				font-size: 28rpx;
				text-align: center;
				padding: 30rpx 0 10rpx 0;
			}

			.repayment-money {
				text-align: center;

				.text1 {
					color: #FA5B33;
					font-size: 100rpx;
				}

				.text2 {
					font-size: 36rpx;
				}

				.text3 {
					font-size: 32rpx;
				}
			}

			.by-the-month {
				text-align: center;
				line-height: 60rpx;
				text-decoration: underline
			}

			.amount-tips {
				text-align: center;
				color: #EB5149;
				font-size: 26rpx;
			}

			.tabel {
				display: flex;
				padding: 20rpx 0;

				.tabel-one {
					width: 50%;
					text-align: center;
				}
			}

			.bottom-tips {
				margin-top: 30rpx;
				text-align: center;
				font-size: 22rpx;
				color: #979797;
			}

		}
	}

	.popup-body {
		max-height: 800rpx;
		overflow-y: auto;

		.popup-body-day {
			display: flex;
			padding: 15rpx 0;

			.popup-body-num {
				text-align: center;
				width: 50%;
			}

			.popup-body-amount {
				width: 50%;
				text-align: center;
			}
		}
	}

	.popup-body-head {
		display: flex;
		padding: 15rpx 0;
		background-color: #EBEBEB;

		.popup-body-num {
			text-align: center;
			width: 50%;
		}

		.popup-body-amount {
			width: 50%;
			text-align: center;
		}
	}

	.buttom-class {
		margin-top: 20rpx;
		border-radius: 30rpx;
		color: #FFFFFF;
		background: #409EFF
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
