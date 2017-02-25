import {Actions} from 'react-native-router-flux'

export const handleLogin = (email, pwd)=>{
  //API call here
  
  Actions.home()
  // Redux require you to return an object with type
  return {type: 'LOGINOK', payload: {email: email, pwd: pwd}}
}


export const startMonitoring = () => {
  return {
    type: 'START_MONITOR'
  }
}
