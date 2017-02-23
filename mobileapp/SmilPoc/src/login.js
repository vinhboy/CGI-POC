import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View
} from 'react-native';

import {
  Button, h3,FormLabel, FormInput, FormValidationMessage
} from 'react-native-elements';

export default class Login extends Component {

  state = {
    email: '',
    pwd: '',
    errmessage: ''
  }

  _login(){


    const {email, pwd} = this.state;

    if (email.length < 1  ){
      this.setState({errmessage: 'Please enter valid credentials'});
    }else{
        this.setState({errmessage: ''});
    }
  }


  componentDidMount(){


  }

  render() {

  const {errmessage} = this.state;
    return (

      <View style={styles.container}>


        <FormLabel>Email</FormLabel>
        <FormInput
          onChangeText={(text) => this.setState({email: text})}
          />

        <FormLabel>Password</FormLabel>

        <FormInput
          onChangeText={(text) => this.setState({pwd: text})}
          secureTextEntry
          />

        <FormValidationMessage>
          {errmessage}
        </FormValidationMessage>
        <Button
          raised
          icon={{name: 'cached'}}
          title='Login'
          onPress={this._login.bind(this)}/>

      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },

});
