/**
 * Api hooks to get all the info and and keep it as a function until i need to call it from a different 
 * @author gIlias
 */
import axios from 'axios';
//constant for the url to be added with parameters as the functions are made
const url = 'http://localhost.com:8080';

/**
 * function to call the api for loging a user out
 * no parameters needed for this call
 * @author gIlias
 * @return responseFromServer
 */
export async function callLogout() {
    var itemToReturn;
    const config = {
        method: 'get',
        url: 'http://localhost:8080/api/user/logoutRequest',
    }
    await axios( config ).then( res => {
        itemToReturn = res;
    } ).catch( function ( error ) {
        console.log( error );
    } );

    return itemToReturn;

}

/**
 * function to call the api for loging a user into the website
 * @param username
 * @param passwordToBeHashed
 * @author gIlias
 * @return responseFromServer
 */
export async function callLogin( userName, passwordToBeHashed ) {
    var itemToReturn;
    const config = {
        method: 'post',
        url: 'http://localhost:8080/api/user/loginRequest',
        data: {
            'userName': String( userName ),
            'attemptedPassword': String( passwordToBeHashed )
        }
    };
    await axios( config ).then( res => {
        itemToReturn = res;
    } ).catch( function ( error ) {
        console.log( error );
    } );
    return itemToReturn;
}
/**
 * Axios call method to register a given user after a form is filled out
 *
 * the param is a hashmap of objects taken from the form itself
 * @param hashMapOfItems
 * @author gIlias
 * @return responseFromServer
 */
export async function callRegister( hashMapOfItems ) {
    var itemToReturn
    const config = {
        method: 'post',
        url: 'http://localhost:8080/api/user/createUser',
        data: hashMapOfItems
    }
    await axios( config ).then( res => {
        itemToReturn = res;
    } ).catch( function ( error ) {
        console.log( error );
    } );
    return itemToReturn;
}



