export default{
	namespaced:true,
	//开启命名空间后，访问所有属性都要带模块
	state(){
		return{
			item:[],
			details:{
				id:'',
				userId:'',
				typeId:'',
				typeName:'',
				parentId:'',
				parentName:'',
				isPayment:'',
				money:'',
				remark:'',
				seller:'',
				createTime:'',
				info:'',
				fileUrls:[]
			},
			IndexArr:[],
			ImageArr:[],
			tmpArr:[],
			InfoArr:[],
			pageArr:[],
			pageNum:1
		}
	},
	mutations:{
		InitInfo(state,info){
			state.item = JSON.parse(info)
		},
		pushItem(state,obj){
			state.item.unshift(obj) 
		},
		pushPageArr(state,obj){
			state.pageArr.push.apply(state.pageArr,obj) 
		},
		addPageNum(state){
			state.pageNum++
		},
		clearItem(state,obj){
			state.item = []
		},
		InitDetails(state,info){
			state.details = info
		},
		//this.$store.commit('account/InitDetails',e)
		InitIndexArr(state,info){
			// state.IndexArr = info
			state.IndexArr.push.apply(state.IndexArr,info)
		},
		InitImageArr(state,info){
			state.ImageArr = info
		},
		InitTmpArr(state,info){
			state.tmpArr = info
		},
		InitInfoArr(state,info){
			state.InfoArr = info
		}
	},
	actions:{
		addItem(context,data){
			context.commit('pushItem',data)
			// uni.setStorageSync({
			// 	key:"info",
			// 	data:JSON.stringify(this.state.item) 
			// })
		}
	},
	getters:{
		allItems(state){
			return state.item
		}
	}
}