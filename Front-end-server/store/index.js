import Vue from "vue"
import Vuex from 'vuex'
import persistedState from "vuex-persistedstate"
import account from './account.js'
import user from './user.js'
Vue.use(Vuex)
const store = new Vuex.Store({
	plugins:[
		persistedState({
			storage: {
				getItem: key => uni.getStorageSync(key),
				setItem: (key, value) => uni.setStorageSync(key, value),
				removeItem: key => uni.removeStorageSync(key)
			}
		})
	],
	modules:{
		account,
		user
	}
})
export default store