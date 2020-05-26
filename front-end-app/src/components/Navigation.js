import React from 'react';
import { BrowserRouter, Route, Switch, Link } from 'react-router-dom';
import RegisterComponent from './RegisterComponent';
import './componentsCSS/Navigation.css';
import App from '../App';
import { getCookieUser } from '../globals/GlobalCookie';
import NavigationLinks from './NavigationLinks';


class Navigation extends React.Component {
    constructor( props ) {
        super( props );
    }

    render() {
        return this.returnForLoggedInUser();
    };


    checkForUser = () => {
    }


    returnForLoggedInUser = () => {
        if ( getCookieUser() == undefined ) {
            return (
                <div >
                    <BrowserRouter>
                        <div>
                            <NavigationLinks />
                        </div>
                        <div >
                            <Switch>
                                <Route path="../" component={App} />
                                <Route path="/RegisterComponent" component={RegisterComponent} />
                            </Switch>
                        </div>

                    </BrowserRouter>
                </div>
            )
        } else {
            return ( <div><p>it seems you are logged in</p></div> );
        }
    }
}

export default Navigation;