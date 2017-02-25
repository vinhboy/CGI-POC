import { ActionConst } from 'react-native-router-flux';



const DEFAULT_STATE = {
  scene: {},
loggedin: false, 
currentUser: {}
}
export default (state = DEFAULT_STATE, {type, payload})=> {
console.log(" nav reducer  ",state,type, payload)
  switch(type) {
    case 'LOGOUT':
    return {
      ...state
    }
    case ActionConst.FOCUS:
    //do i need to set a scene??
    //scene: payload.scene ,
    return {
      ...state,

    };
    case 'LOGINOK':

    return {
      ...state,
      loggedin: true,
      currentUser: payload
    };


    default:
    return state
  }
}
