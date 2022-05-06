import { useEffect, useState} from 'react';
import {fetchUserData, saveUserProfile} from './UserService';
import {createErrorAlert, createSuccessAlert} from "../../alertHelper";
import {connect} from "react-redux";

function ConfigureProfile(props) {

    const [profile, setProfile] = useState({
        id: null,
        name: '',
        surname: '',
        phoneNumber: '',
        address: '',
        email: '',
        birthDate: '',
        education: '',
        workingExperience: '',
        hoby: '',
        privateProfile: false
    });

    const [showSpinner, setShowingSpinner] = useState(false);

    function handleChange(event) {
        setProfile({
            ...profile,
            [event.target.name]: event.target.value
        })
    }

    function handleChangeProfilePrivacy(event) {
        setProfile({
            ...profile,
            privateProfile: event.target.checked
        })
    }
    
    useEffect(() => {
        fetchUserData(props.userId, props.token).then(response => {
            setProfile({
                name: response.data.name || "",
                surname: response.data.surname || "",
                phoneNumber: response.data.phoneNumber || "",
                address: response.data.address || "",
                email: response.data.email || "",
                birthDate: response.data.birthDate || "",
                education: response.data.education || "",
                workingExperience: response.data.workingExperience || "",
                hoby: response.data.hoby || "",
                privateProfile: response.data.privateProfile}, props.token)
        });
    }, []);

    function saveUserData() {
        setShowingSpinner(true);
        const userId = localStorage.getItem("userId");
        saveUserProfile(profile, userId, props.token).then(response => {
            createSuccessAlert("Uspešno sačuvano");
        }).catch(error => {
            createErrorAlert("Greška se dogodila");
        }).finally(_ => {
            setShowingSpinner(false);
        });
    }


    return (
        <div className="container rounded bg-white mt-5 mb-5">
            <div className="row">
                <div className="col-md-3 border-right">
                    <div className="d-flex flex-column align-items-center text-center p-3 py-5">
                        <img className="rounded-circle mt-5" width="150px" src="https://st3.depositphotos.com/15648834/17930/v/600/depositphotos_179308454-stock-illustration-unknown-person-silhouette-glasses-profile.jpg" /><span> </span></div>
                </div>

                <div className="col-md-5 border-right">
                    <div className="p-3 py-5">
                        <div className="d-flex justify-content-between align-items-center mb-3">
                            <h4 className="text-right">Podešavanje naloga</h4>
                        </div>
                        <div className="row mt-2">
                            <div className="col-md-6"><label className="labels">Ime</label><input type="text" name="name" className="form-control" placeholder="ime" value={profile.name} onChange={handleChange} /></div>
                            <div className="col-md-6"><label className="labels">Prezime</label><input type="text" name="surname" className="form-control" value={profile.surname} placeholder="prezime" onChange={handleChange} /></div>
                        </div>
                        <div className="row mt-3">
                            <div className="col-md-12"><label className="labels">Broj telefona</label><input type="text" name="phoneNumber" className="form-control" placeholder="broj telefona" value={profile.phoneNumber} onChange={handleChange} /></div>
                            <div className="col-md-12"><label className="labels">Adresa</label><input type="text" className="form-control" name="address" placeholder="adresa" value={profile.address} onChange={handleChange} /></div>
                            <div className="col-md-12"><label className="labels">Email</label><input type="text" className="form-control" name="email" placeholder="imejl" value={profile.email} onChange={handleChange} /></div>
                            <div className="col-md-12"><label className="labels">Datum rođenja</label><input type="date" className="form-control" name="birthDate" placeholder="datum rođenja" value={profile.birthDate} onChange={handleChange} /></div>
                            <div className="col-md-12">
                                <label className="labels">Privatan profil</label>
                                <div>
                                    <input type="checkbox" name="privateProfile" checked={profile.privateProfile} onChange={handleChangeProfilePrivacy} />
                                </div>
                                </div>
                        </div>
                        <div className="mt-5 text-center"><button className="btn btn-primary profile-button" type="button" onClick={saveUserData}>
                            Save Profile
                            {showSpinner &&
                            <div className="spinner-border spinner-border-sm ml-1" role="status">
                                <span className="sr-only">Loading...</span>
                            </div>
                            }
                        </button></div>
                    </div>
                </div>
                
                <div className="col-md-4">
                    <div className="p-3 py-5">
                        <div className="col-md-12"><label className="labels">Obrazovanje</label><textarea name='education' className="form-control" rows="3" value={profile.education} onChange={handleChange}></textarea></div>
                        <div className="col-md-12"><label className="labels">Radno iskustvo</label><textarea className="form-control" name='workingExperience' rows="3" value={profile.workingExperience} onChange={handleChange}></textarea></div>
                        <div className="col-md-12"><label className="labels">Interesovanja</label><textarea className="form-control" rows="3" name='hoby' value={profile.hoby} onChange={handleChange}></textarea></div>
                    </div>
                </div>
            </div>
        </div>
    )
}

function mapStateToProps(state) {
    return {
        userId: state.userId,
        token: state.token
    }
}

export default connect(mapStateToProps)(ConfigureProfile);