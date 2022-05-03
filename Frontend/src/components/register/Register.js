import { useState} from "react";
import { useHistory } from "react-router-dom";
import ModalLoader from "../common/loaders/ModalLoader";
import {createErrorAlert, createSuccessAlert} from "../../alertHelper";
import { register } from "./RegisterService";

function Register(props) {
    const newUserInitialState = {
        name: '',
        surname: '',
        userName: '',
        password: '',
        repeatedPassword: '',
    };
    const [isLoaderActive, setLoaderActive] = useState(false);
    const [newUser, setNewUser] = useState(newUserInitialState);
    const [formError, setFormError] = useState(null);
    const [buttonDisabled, setButtonDisabled] = useState(false);
    const history = useHistory();

    function handleChange(event) {
        setNewUser({
           ...newUser,
            [event.target.name]: event.target.value
        });
    }

    function handleFormSubmit(event) {
        event.preventDefault();
        if (newUser.password !== newUser.repeatedPassword) {
            setFormError("Unete lozinke nisu iste");
            return;
        }
        setLoaderActive(true);
        setButtonDisabled(true);
        setFormError(null);
        register({
            firstName: newUser.name,
            lastName: newUser.surname,
            username: newUser.userName,
            password: newUser.password,
        }, props.token).then(response => {
            createSuccessAlert("Korisnik sačuvan");
            history.push('/login')
        }).catch(error => {
            if (error.response.status === 400) {
                setFormError("Korisničko ime već postoji");
                return;
            }
            createErrorAlert("Korisnik nije sačuvan")
        }).finally(() => {
            setLoaderActive(false);
            setButtonDisabled(false);
        });
    }

    
    return (
        <div className="form-signin-container text-center h-75">
            <ModalLoader isActive={isLoaderActive} />
            <form className="form-signin" onSubmit={event => handleFormSubmit(event)}>
                <h1 className="h3 mb-3 font-weight-normal">Registracija</h1>
                <label htmlFor="text" className="sr-only">Ime</label>
                <input type="text" id="inputName" className="form-control" placeholder="Ime" required
                        autoFocus name="name" value={newUser.name} onChange={event => handleChange(event)}/>
                <label htmlFor="text" className="sr-only">Prezime</label>
                <input type="text" id="inputSurname" className="form-control" placeholder="Prezime" required
                        name="surname" value={newUser.surname} onChange={event => handleChange(event)} />
                <label htmlFor="text" className="sr-only">Korisničko ime</label>
                <input type="text" id="inputUsername" className="form-control" placeholder="Korisničko ime" required
                        name="userName" value={newUser.userName} onChange={event => handleChange(event)} />
                <label htmlFor="inputPassword" className="sr-only">Lozinka</label>
                <input type="password" id="inputPassword" className="form-control m-0" placeholder="Lozinka"
                        required value={newUser.password} name="password" onChange={event => handleChange(event)} />
                <label htmlFor="inputPassword" className="sr-only">Ponovite lozinku</label>
                <input type="password" id="inputRepeatPassword" className="form-control m-0" placeholder="Ponovite lozinku"
                        required name="repeatedPassword" value={newUser.repeatedPassword} onChange={event => handleChange(event)} />
                {formError?<label className="text-danger">{formError}</label>:null}
                <button className="btn btn-lg btn-primary btn-block" type="submit" disabled={buttonDisabled}>Registrujte se</button>
            </form>
        </div>
    );
}

export default Register;