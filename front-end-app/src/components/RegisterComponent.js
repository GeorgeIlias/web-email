import React from 'react';
import ReactDOM from 'react-dom';
import { callRegister } from '../api/UserApi';
import { Redirect } from 'react-router-dom'

import './componentsCSS/Registration.css';

class RegisterComponent extends React.Component {
    constructor( props ) {
        super( props );
        this.state = {
            firstName: '',
            lastName: '',
            password: '',
            username: '',
            reEnterPasword: '',
            dateOfBirth: undefined,
            formSubmitResponse: undefined
        };
        this.props = {
            formSubmitRepsonse: undefined
        }
    }

    /**
     * function to call the render the correct error messages 
     * 
     * @author gIlias
     */
    deliverErrorMessage = ( message, id ) => {
        if ( message != null ) {
            const element =
                <div className="error-div" id={id}>
                    <p>{message}</p>
                </div>
                ;
            return ReactDOM.render( element, document.getElementById( "errorsToThrow" ) );
        } else if ( message == null ) {
            document.getElementById( id ).remove();
        }
    }

    /**
     * function to handle the changes in all the fields of the form 
     * Exception to this function being the passwords that will be handled in a different 
     * function so that i can check them against each other
     * 
     * @param {Event} event 
     */
    handleChange = ( event ) => {
        var stringName = event.target.getAttribute( "id" );
        var value = event.target.value;
        this.setState( { stringName: value } );
    }



    /**
     * method to check the proper validation for the username, will be called on loss of focus
     * @author gIlias
     */
    checkFirstAndLastNames = () => {
        var firstNameItem = new String( this.state.firstName );
        var lastNameItem = new String( this.state.lastName );

        let regexMatcher = new RegExp( '/^[A-Za-z \\s -] + $  ' );

        var checkerFirstName = firstNameItem.match( regexMatcher );
        var checkerLastName = lastNameItem.match( regexMatcher );
        if ( ( checkerFirstName != null && checkerFirstName != undefined && checkerFirstName != false )
            &&
            ( checkerLastName != null && checkerLastName != undefined && checkerLastName != false ) ) {
            if ( firstNameItem.length > 0 && lastNameItem.length > 0 ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }
    /**
     * following function will be used to check for both passwords
     * @author gIlias
     */
    checkPasswords = () => {

    }

    /**
     * Method to handle the password checking, typically this will compare them 
     * and once they are properly checked they will then unlock the submit message
     * 
     * @param {EventParam} event 
     */
    handlePasswordChange( event ) {

        var firstPassword = this.state.password;
        var reEnterPasword = this.state.reEnterPasword;
        var regexForPassword = new RegExp( '[^a-zA-Z0-9]' );
        var regexForNullPasswords = new RegExp( '\\0 \\v \\s' );

        var firstPasswordRegExpTwo = firstPassword.match( regexForPassword );
        var firstPasswordRegExpOne = firstPassword.match( regexForNullPasswords );

        var secondPasswordRegExpTwo = reEnterPasword.match( regexForPassword );
        var secondPasswordRegExpOne = reEnterPasword.match( regexForNullPasswords );

        if ( firstPasswordRegExpOne != null && firstPasswordRegExpTwo != undefined ) {

        } else if ( firstPasswordRegExpOne == null || firstPasswordRegExpTwo == undefined ) {
            this.deliverErrorMessage( null, 'passwordError' );
            this.deliverErrorMessage( 'the first password must be between 8 and 32 and must not contain any non-numeric/non-alphabetic characters  ', 'passwordError' );
        }
        if ( secondPasswordRegExpOne != null && secondPasswordRegExpTwo != undefined ) {

        } else if ( secondPasswordRegExpOne == null || secondPasswordRegExpTwo == undefined ) {
            this.deliverErrorMessage( null, 'reEnterPasswordError' );
            this.deliverErrorMessage( 'the second password must be between 8 and 32 and must not contain any non-numeric/non-alphabetic characters  ', 'reEnterPasswordError' );
        }




    }


    /**
     * function to call the api call to register a user and return a given response
     * 
     *
     * 
     * @author gIlias
     * @param event 
     */
    //TODO add final checking for all the input tags on the form
    submitForm = ( event ) => {
        var booleanCheckerToCallRegister = false;



        var result = callRegister();
        Promise.resolve( result ).then( ( value ) => {
            var formSubmitResponse = value.data;
            if ( value.data.state == 200 ) {
                return <Redirect to="../" formSubmitProps={formSubmitResponse} />
            } else {
                var item = deliverErrorMessage( 'There was an error when submitting the form', 'form' );
                if ( item != undefined ) {
                    return item;
                }
            }
        } );


    }

    render() {
        return (
            <div >
                <form className="form-align" onSubmit={this.submitForm}>
                    <div>
                        <label htmlFor="divFirstLast">Please enter your first and last name</label>
                        <div id="divFirstLast">
                            <span className="span-spacing">
                                <label htmlFor="firstName">First Name : </label>
                                <input value={this.state.firstName} id="firstName" required />
                            </span>
                            <span className="span-spacing">
                                <label htmlFor="lastName">Last Name : </label>
                                <input value={this.state.lastName} id="lastName" required />
                            </span>
                        </div>

                    </div>
                    <div>
                        <span>
                            <label htmlFor="username">Enter your username : </label>
                            <input value={this.state.username} id="username" required />
                        </span>
                    </div>
                    <br />
                    <div>
                        <span>
                            <span className="span-spacing">
                                <label htmlFor="firstPass">Enter your password : </label>
                                <input onChange={this.handlePasswordChange} value={this.state.password} id="firstPass" type="password" required />
                            </span>

                            <span className="span-spacing">
                                <label htmlFor="passReEnter">Re-enter your password : </label>
                                <input onChange={this.handlePasswordChange} value={this.state.reEnterPasword} id="passReEnter" type="password" required />
                            </span>
                        </span>
                    </div>
                    <div className="button-div-right-align">
                        <input className="button-size-spacing" type="button" value="Reset" />
                        <input className="button-size-spacing" type="submit" value="Register" />
                    </div>
                </form>
                <div id="errorsToThrow">
                </div>
            </div >
        );
    }
}



export default RegisterComponent;