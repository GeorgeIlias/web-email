import React from 'react';
import ReactDOM from 'react-dom';
import { callRegister } from '../api/UserApi';
import { Redirect } from 'react-router-dom'
import './componentsCSS/Registration.css';
import { __RouterContext } from 'react-router';


/**
 * TODO: need to add validation per input field
 */
class RegisterComponent extends React.Component {
    constructor( props ) {
        super( props );
        this.state = {
            firstName: '',
            lastName: '',
            password: '',
            username: '',
            reEnterPassword: '',
            dateOfBirth: undefined,
            formSubmitResponse: undefined,
            formErrors: { firstName: '', lastName: '', username: '', password: '', reEnterPassword: '', matchingPasswords: '', formErrorOptions: '' }
        };
    }

    /**
     * function to handle the changes in all the fields of the form 
     * Exception to this function being the passwords that will be handled in a different 
     * function so that i can check them against each other
     * 
     * @param {Event} event 
     */
    handleChange = ( event ) => {
        var stringName = event.target.name;
        var value = event.target.value;
        this.setState( { stringName: value } );
        console.log( this.state );
    }



    /**
     * method to check the proper validation for the first name, will be called on change
     * @param {EventParam}event
     * 
     *  @author gIlias
     */
    checkFirstName = ( event ) => {
        var arrayToSet = this.state.formErrors;
        var booleanForFirst = false;
        var firstNameItem = new String( event.target.value );

        let regexMatcher = /[^\w -]+/;

        var checkerFirstName = firstNameItem.match( regexMatcher );

        if ( event.target.value.length == 0 ) {
            arrayToSet['firstName'] = '';
        } else {
            if ( event.target.value.length < 3 ) {
                arrayToSet['firstName'] = 'The first name entered is too short.  It should be between 3 and 80 characters.';
                booleanForFirst = false;
            } else if ( event.target.value.length >= 3 && event.target.value.length <= 80 ) {
                if ( checkerFirstName === null ) {
                    console.log( 'null has been called' );
                    arrayToSet['firstName'] = '';
                    booleanForFirst = true;
                } else if ( checkerFirstName !== null ) {
                    console.log( 'not null has been called' );
                    arrayToSet['firstName'] = 'The first name entered has an illegal character.';
                    booleanForFirst = false;
                }
            } else if ( event.target.value.length > 80 ) {
                arrayToSet['firstName'] = 'The first name entered is too long.  It should be between 3 and 80 characters.  Hello there';
                booleanForFirst = false;
            }
        }
        this.setState( { formErrors: arrayToSet } );
        return booleanForFirst;
    }
    /**
     * function to check for the last name on change
     * 
     */
    checkLastName = ( event ) => {
        var arrayToSet = this.state.formErrors;
        var booleanForSecond = false;
        var lastNameItem = new String( event.target.value );

        let regexMatcher = / [^\w -]+/;

        var checkerLastName = lastNameItem.match( regexMatcher );

        console.log( checkerLastName );
        if ( event.target.value.length != 0 ) {
            if ( event.target.value.length < 3 ) {
                booleanForSecond = false;
                arrayToSet['lastName'] = 'The last name entered is too short.  It should be between 3 and 80 characters.';
            } else if ( event.target.value.length >= 3 && event.target.value.length <= 80 ) {
                if ( checkerLastName === null ) {
                    arrayToSet['lastName'] = '';
                    booleanForSecond = true;
                } else if ( checkerLastName !== null ) {
                    arrayToSet['lastName'] = 'The last name entered has an illegal character.';
                    booleanForSecond = false;
                }

            } else if ( event.target.value.length > 80 ) {
                arrayToSet['firstName'] = 'The last name entered is too long.  It should be between 3 and 80 characters.';
                booleanForSecond = false;
            }
        } else {
            arrayToSet['lastName'] = '';
            booleanForSecond = false;
        }

        this.setState( { formErrors: arrayToSet } );
        return booleanForSecond;

    }
    /**
     * Method to handle the password checking, typically this will compare them 
     * and once they are properly checked they will then unlock the submit message
     * 
     * @param {EventParam} event 
     */
    handleFirstPasswordChange = ( event ) => {
        var booleanReturn = false;
        var arrayToSet = this.state.formErrors;

        var firstPassword = event.target.value;

        var regexForPassword = /[^\w \d \s]+/;


        var firstPasswordRegExpOne = firstPassword.match( regexForPassword );

        console.log( '-----------------------------------------' );
        console.log( event.target.value );
        console.log( '------------------------------------------' );
        console.log( event.target.value.length );
        console.log( '-------------------------------------------' );
        console.log( firstPasswordRegExpOne );
        console.log( '----------------------------------------' );
        if ( event.target.value.length < 8 || event.target.value.length > 16 ) {
            if ( event.target.value.length < 8 ) {
                arrayToSet['password'] = 'The first password entered is less than 8 characters long.';
            }
            if ( event.target.value.length > 16 ) {
                arrayToSet['password'] = 'The first password entered is more than 16 characters long.';
            }
            booleanReturn = false;
        } else {
            if ( firstPasswordRegExpOne === null ) {
                booleanReturn = true;
                arrayToSet['password'] = '';
            } else {
                booleanReturn = false;
                arrayToSet['password'] = 'The password should be between 8 and 16 characters long and should not contain any non-alphabetical or non-numerical characters.'
            }
        }
        this.setState( { formErrors: arrayToSet } );
        return booleanReturn;

    }
    /**
     * function to compare the re-entered password with the given regexp
     * 
     * @author gIlias
     */
    handleSecondPasswordChange = ( event ) => {
        var booleanReturn = false;

        var reEnterPassword = event.target.value;

        var arrayToSet = this.state.formErrors;

        var regexForPassword = /[^\w \d \s]+/;


        var secondPasswordRegExpOne = reEnterPassword.match( regexForPassword );

        if ( event.target.value.length < 8 || event.target.value.length > 16 ) {
            if ( event.target.value.length < 8 ) {
                arrayToSet['reEnterPassword'] = 'The  re-entered password entered is less than 8 characters long.';
            }
            if ( event.target.value.length > 16 ) {
                arrayToSet['reEnterPassword'] = 'The re-entered password entered is more than 16 characters long.';
            }
            booleanReturn = false;
        } else {
            if ( secondPasswordRegExpOne === null ) {
                booleanReturn = true;
                arrayToSet['reEnterPassword'] = '';
            } else {
                arrayToSet['reEnterPassword'] = 'The re-entered password should be between 8 and 16 characters long and should not contain any non-alphabetical or non-numerical characters.';
                booleanReturn = false;
            }
        }

        this.setState( { formErrors: arrayToSet } );
        return booleanReturn;
    }



    /**
     * function to check for the passwords, specifically to see if they match on submit
     * 
     * @author gIlias
     */
    onSubmitPasswordChecker = ( event ) => {
        var successBoolean = false;
        var arrayToSet = this.state.formErrors;
        if ( this.state.password === this.state.reEnterPassword ) {
            arrayToSet['matchingPasswords'] = 'The passwords are correct and match.';
            successBoolean = true;
        } else if ( this.state.password !== this.state.reEnterPassword ) {
            arrayToSet['matchingPasswords'] = 'The passwords do not match.';
            successBoolean = false;
        }
        this.setState( { formErrors: arrayToSet } );
        return successBoolean;
    }
    /**
     * function to reset the formErrors array onSubmit
     * @author gIlias 
     */
    resetFormErrorsArray = () => {
        var arrayToSet = this.state.formErrors;
        Object.map( arrayToSet = ( fieldName, i ) => {
            arrayToSet[fieldName] = '';
        } );
        this.setState( { formErrors: arrayToSet } );
    }

    /**
     * function to call all the functions that are too check every field then return a true/false boolean
     * if true is returned then there are no errors.  However, if false is returned then the check has failed
     * 
     * @author gIlias
     */
    booleanTrackerForSubmit = ( event ) => {
        var booleanFirstPass = this.handleFirstPasswordChange( event );
        var booleanSecondPass = this.handleSecondPasswordChange( event );
        var first = this.checkFirstName( event );
        var last = this.checkLastName( event );
        var matchingPassword = this.onSubmitPasswordChecker( event );

        if ( booleanFirstPass && booleanSecondPass && first && last && matchingPassword ) {
            return true;
        } else {
            return false;
        }

    }
    /**
     * function to create a hashmap to send to the api for registration
     * @author gIlias
     */
    createHashMap = () => {
        var hashMapToReturn = {
            username: this.state.username,
            password: this.state.password,
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            dateOfBirth: this.state.dateOfBirth
        };
        return hashMapToReturn;

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
        var booleanCheckerToCallRegister = this.booleanTrackerForSubmit( event );

        if ( booleanCheckerToCallRegister == true ) {
            console.log( 'not fucking working, i think' );
            var result = callRegister( this.createHashMap() );
            Promise.resolve( result ).then( ( value ) => {
                var formSubmitResponse = value.data;
                if ( value.data.state == 200 ) {
                    return <Redirect to="../" formSubmitProps={formSubmitResponse} />
                } else {
                    if ( value != undefined ) {
                        return value.data;
                    } else {
                        this.resetFormErrorsArray();
                    }
                }
            } );
        }


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
                                <input value={this.state.first} id="firstName" onChange={( event ) => { this.handleChange( event ); this.checkFirstName( event ) }} name="firstName" required />
                            </span>
                            <span className="span-spacing">
                                <label htmlFor="lastName">Last Name : </label>
                                <input value={this.state.lastName} id="lastName" onChange={( event ) => { this.handleChange( event ); this.checkLastName( event ) }} name="lastName" required />
                            </span>
                        </div>

                    </div>
                    <div>
                        <span>
                            <label htmlFor="username">Enter your username : </label>
                            <input value={this.state.username} id="username" onChange={this.handleChange} name="username" required />
                        </span>
                    </div>
                    <br />
                    <div>
                        <span>
                            <span className="span-spacing">
                                <label htmlFor="firstPass">Enter your password : </label>
                                <input value={this.state.password} onChange={( event ) => { this.handleChange( event ); this.handleFirstPasswordChange( event ); this.onSubmitPasswordChecker( event ) }} id="firstPass" type="password" name="password" required min='4' max='32' />
                            </span>

                            <span className="span-spacing">
                                <label htmlFor="passReEnter">Re-enter your password : </label>
                                <input value={this.state.reEnterPassword} onChange={( event ) => { this.handleChange( event ); this.handleSecondPasswordChange( event ); this.onSubmitPasswordChecker( event ) }} id="passReEnter" type="password" name="reEnterPassword" required min='4' max='32' />
                            </span>
                        </span>
                    </div>
                    <div className="button-div-right-align">
                        <input className="button-size-spacing" type="button" value="Reset" />
                        <input className="button-size-spacing" type="submit" value="Register" />
                    </div>
                </form>
                <div id="errorsToThrow">
                    {Object.keys( this.state.formErrors ).map( ( fieldname, i ) => {
                        if ( this.state.formErrors[fieldname] ) {
                            var className;
                            var fieldNameToUse;
                            if ( fieldname == 'reEnterPassword' ) {
                                fieldNameToUse = 'Re-entered Password';
                            } else if ( fieldname == 'lastName' ) {
                                fieldNameToUse = 'Last Name';
                            } else if ( fieldname == 'firstName' ) {
                                fieldNameToUse = 'First Name';
                            } else if ( fieldname == 'username' ) {
                                fieldNameToUse = 'Username';
                            } else if ( fieldname == 'matchingPasswords' ) {
                                fieldNameToUse = 'Matching Passwords';
                            } else if ( fieldname = 'formErrorOptions' ) {
                                fieldNameToUse = 'Errors on Submission';
                            } else {
                                fieldNameToUse = fieldname;
                            }
                            return ( <div key={i} className='error-div'><p>{fieldNameToUse} : {this.state.formErrors[fieldname]} </p></div> );
                        } else {
                            return '';
                        }
                    } )}
                </div>
            </div >
        );
    }
}



export default RegisterComponent;