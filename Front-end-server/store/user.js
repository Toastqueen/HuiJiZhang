 export default{
	state(){
		return{
			isLogin:false,//登录状态
			oid:'',
			accountName:'',
			info:{
				userId:'',
				userName:"",//电话号码
				nickName:'Ethan',
				gender:null,
				phoneNumber:'',
				avatarUrl:'',
				days_num:0,
				age:'',
				token:""
			}
		}
	},
	mutations:{
		changeInfo(state,obj){//obj是新的条件对象
			state.info = obj
		},
		change_isLogin(state,obj){
			state.isLogin = obj
		},
		change_oid(state,obj){
			state.oid = obj
		},
		change_accountName(state,obj){
			state.accountName = obj
		},
		change_avatarUrl(state,obj){
			state.info.avatarUrl=obj
		},
		change_nickName(state,obj){
			state.info.nickName=obj
		},
		change_gender(state,obj){
			state.info.gender=obj
		},
		change_age(state,obj){
			state.info.age=obj
		},
		change_phoneNumber(state,obj){
			state.info.phoneNumber=obj
		},
		change_userId(state,obj){
			state.info.userId=obj
		}
		/*
		如何调用：
		this.$store.commit('changeInfo',{
			name:'acb'
		})
		*/
	}
}