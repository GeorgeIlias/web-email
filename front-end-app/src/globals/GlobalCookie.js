import React from 'react';
import Cookies from 'universal-cookie';

export function getCookieUser() {
    const cookieToReceive = new Cookies();
    var itemToCheck = cookieToReceive.get( 'userCookie' );
    return itemToCheck;
}

export function getContextUser() {
    var context = React.createContext();
    context.user = null;
}


