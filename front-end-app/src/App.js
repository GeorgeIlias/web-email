import React from 'react';
import './App.css';
import { callLogout } from './api/UserApi';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import RegisterComponent from './components/RegisterComponent';
import Navigation from './components/Navigation';
import { getContextUser } from './globals/GlobalCookie';

import axios from 'axios';

class App extends React.Component {
  constructor( props ) {
    super( props );
    this.state = { entry: null };
  }


  redirectToRegistration() {

  }


  componentDidMount() {
    /**
     * test to see what is returned
     * 
     * @author gIlias
     */
    const result = callLogout();
    if ( result != null && result != undefined ) {

      Promise.resolve( result ).then( ( value ) => {
        this.setState( { entry: value.data } );
      } );

    }
  }




  render() {
    return (
      <div id="Total" className="Total">
        <div id="Header" className="Header">
          <Navigation />
        </div>
        <div id="App" className="App">
        </div>

      </div>
    );
  }
}



export default App;
