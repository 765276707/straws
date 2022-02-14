import { getAccessToken, setAccessToken, removeAccessToken } from '@/server/token'
import { resetRouter } from '@/router'
import auth from '@/api/modules/auth'

const getDefaultState = () => {
  return {
    token: getAccessToken(),
    name: '',
    avatar: '',
    roles: []
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    return new Promise((resolve, reject) => {
      auth.login(userInfo).then(response => {
        
        // 保存令牌
        const { accessToken, tokenType, refreshToken, scope, expiresIn } = response.data;
        commit('SET_TOKEN', accessToken)
        setAccessToken(accessToken)
        resolve()
  
      }).catch(error => {
        console.log("login error")
        reject(error)
      })
    })
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      auth.getInfo().then(response => {
        const { username, avatar, roles } = response.data

        // roles must be a non-empty array       
        if (!roles || roles.length <= 0) {
          reject('getInfo: roles must be a non-null array!')
        }

        commit('SET_ROLES', roles)
        commit('SET_NAME', username)
        commit('SET_AVATAR', avatar)
        
        console.log(state.roles)

        resolve()

      }).catch(error => {
        console.log(error)
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      auth.logout().then(() => {
        removeAccessToken() // must remove  token  first
        resetRouter()
        commit('RESET_STATE')
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeAccessToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

