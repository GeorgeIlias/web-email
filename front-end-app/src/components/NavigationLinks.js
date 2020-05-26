import React from 'react';
import { BrowserRouter, Route, Switch, Link } from 'react-router-dom';
import RegisterComponent from './RegisterComponent';
import './componentsCSS/Navigation.css';
import App from '../App';
import { getCookieUser } from '../globals/GlobalCookie';



class NavigationLinks extends React.Component {
    render() {
        return (
            <nav className="navigation-bar-100-percent" >
                <ul >
                    <Link to='/RegisterComponent'>
                        <li className="li-no-point-inline">Registration</li>
                    </Link>
                    <Link to='/'>
                        <li className="li-no-point-inline">home</li>
                    </Link>
                </ul>
            </nav>
        )
    };
}

export default NavigationLinks;