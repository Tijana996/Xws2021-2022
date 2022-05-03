import { useState} from 'react';
import {login} from "./LoginService";
import {useHistory} from "react-router-dom";
import RetryError from "../common/errors/RetryError";
import Loader from "../common/loaders/Loader";
import {connect} from "react-redux";

function Login(props) {
    const [requestBody, setRequestBody] = useState({
        'username': "",
        'password': ""
    });
    const [showError, setShowError] = useState(false);
    const [serverError, setServerError] = useState(false);
    const [isLoaderShown, setLoaderShown] = useState(false);
    const history = useHistory();

    function handleChange(event) {
        setRequestBody({
            ...requestBody,
            [event.target.name]: event.target.value
        })
    }

    function tryAgain() {
        setServerError(false);
    }

    function handleLogin(event) {
        event.preventDefault();
        setLoaderShown(true);
        login(requestBody).then(response => {
            if (response.status === 200) {
                props.setAuthData({
                    token: response.data.token,
                    userName: response.data.name,
                    // role: response.data.content.role,
                    id: response.data.userId,
                });
                localStorage.setItem('Token', response.data.token);
                localStorage.setItem('userName', response.data.name);
                localStorage.setItem('userId', response.data.userId);
                history.push('/');
            }
        }).catch(error => {
            if (error.response.status === 400 || error.response.status === 403) {
                setShowError(true);
            } else {
                setServerError(true);
            }
            setLoaderShown(false);
        })
    }

    return (
        <div className="form-signin-container text-center h-75">
            {serverError?<RetryError isActive={serverError} retry={() => tryAgain()} />:
            <form className="form-signin" onSubmit={event => handleLogin(event)}>
                <h1 className="h3 mb-3 font-weight-normal">Ulogujte se</h1>
                <label htmlFor="text" className="sr-only">Korisničko ime</label>
                <input type="text" id="inputEmail" className="form-control" placeholder="Korisničko ime" required
                       autoFocus name="username" onChange={(event => handleChange(event))}   />
                <label htmlFor="inputPassword" className="sr-only">Lozinka</label>
                <input type="password" id="inputPassword" className="form-control" placeholder="Lozinka"
                       required name="password" onChange={(event => handleChange(event))} />
                {showError?<label className="text-danger">Korisničko ime ili lozinka nisu ispravni</label>:null}
                <Loader isActive={isLoaderShown} />
                <button className="btn btn-lg btn-primary btn-block" type="submit">Ulogujte se</button>
            </form>
            }
        </div>
    )
}

function mapStateToProps(state) {
    return {
        token: state.token
    }
}

function mapDispatchToProps(dispatch) {
    return {
        setAuthData: (user) => {dispatch({type: 'SET_AUTH_FIELDS', token: user.token, userName: user.userName,
            role: user.role, userId: user.id})}
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Login);