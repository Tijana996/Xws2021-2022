import React, {useEffect, useState} from "react";
import { searchProfiles } from './SearchUsersService';
import SearchedUser from './SearchedUser';
import { connect } from "react-redux";

function SearchUsers(props) {
    const [userInput, setUserInput] = useState("");
    const [searchedProfiles, setSearchedProfiles] = useState([]);

    useEffect(() => {
        if (!userInput) {
            setSearchedProfiles([]);
            return;
        }

        const identifier = setTimeout(() => {
            searchProfiles(null, userInput, props.userId).then(response => {
                setSearchedProfiles(response.data);
            }).catch(error => {
    
            });
        }, 500);
        
        return () => {
            clearTimeout(identifier);
        };
    }, [userInput]);
    
    function handleUserInput(event) {
        setUserInput(event.target.value);
    }

    return (
        <div style={{paddingTop: "20px", background: "#eee", minHeight: "100vh"}}>
        <div className="container">
            <div className="col-md-12">
                <div className="page-people-directory">
                    <div className="row">
                        <div className="col-md-12">
                        <div className="well" style={{ borderRadius: 0, border: "none" }}>
                            <div className="row">
                            <div className="col-md-12">
                                <input type="text" placeholder="Unesite ime korisnika" value={userInput} className="form-control" onChange={handleUserInput} />
                            </div>
                                <div className="col-md-3">
                                    <div className="btn-group" style={{ display: "block" }}>
                                    <ul className="dropdown-menu bullet pull-right animated pulse margin-top-45">
                                        <li>
                                            <input type="radio" id="ex1_1" name="ex1" defaultValue={1} defaultChecked="" />
                                            <label htmlFor="ex1_1">Fullname</label>
                                        </li>

                                        <li>
                                            <input type="radio" id="ex1_2" name="ex1" defaultValue={2} />
                                            <label htmlFor="ex1_2">Company</label>
                                        </li>

                                        <li>
                                        <input
                                            type="radio"
                                            id="ex1_3"
                                            name="ex1"
                                            defaultValue={3}
                                        />
                                        <label htmlFor="ex1_3">Position</label>
                                        </li>
                                    </ul>
                                    </div>
                                </div>
                                </div>
                            </div>
                            <br />
                            <div className="row">
                                <div className="col-md-6">
                                <h3>Rezultati pretrage</h3>
                                </div>
                            </div>

                            <div  className="list-group contact-group" style={{ marginTop: 20 }}>
                                {searchedProfiles.length === 0 && <h5 className="mt-5">0 rezultata pronađeno.</h5>}
                                {searchedProfiles.map(profile => <SearchedUser key={profile.id} profile={profile}></SearchedUser>)}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </div>

    )

}


function mapStateToProps(state) {
    return {
        userId: state.userId
    }
}

export default connect(mapStateToProps)(SearchUsers);
