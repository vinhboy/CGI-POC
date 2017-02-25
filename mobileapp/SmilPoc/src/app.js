import React, { Component } from 'react';
import { Router, Actions, Scene } from 'react-native-router-flux';
import { connect, Provider } from 'react-redux';
import { createStore, applyMiddleware, compose } from 'redux';

const ConnectedRouter = connect()(Router);
import reducers from './reducers';
import Login from './login';
import Home from './home';

// create store...
const middleware = [/* ...your middleware (i.e. thunk) */];
const store = compose(
  applyMiddleware(...middleware)
)(createStore)(reducers);




const Scenes = Actions.create(
  <Scene key='root' hideNavBar>
    <Scene key='login' component={Login} initial/>
    <Scene key='home' component={Home}/>

  </Scene>
)

class App extends Component {

  componentDidMount(){

  }

  render () {
    return (
      <Provider store={store}>
        <ConnectedRouter scenes={Scenes} />
      </Provider>
    );
  }
}

export default App;
