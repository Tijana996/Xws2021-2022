import React from "react";

function SearchedUser(props) {
    return (    
        <div key={props.profile.id} className="list-group-item" style={{ border: "none", marginTop: 10 }}>
            <div className="media">
                <div className="pull-left" style={{marginRight: "1em"}}>
                <img
                    className="img-circle"
                    src="https://bootdey.com/img/Content/avatar/avatar1.png"
                    alt="..."
                    style={{ width: 80 , borderRadius: "50%"}}
                />
                </div>
                <div className="media-body">
                <h4 className="media-heading" style={{ fontSize: 16, fontWeight: 500 }} >
                    {props.profile.name} {props.profile.surname}
                </h4>
                <div className="media-content" style={{ marginTop: 5 }}>
                    <i className="fa fa-map-marker" /> 
                        {props.profile.address}
                    <ul
                    className="list-unstyled"
                    style={{ marginTop: 15, marginBottom: 0 }}
                    >
                    <li
                        style={{
                        display: "inline-block",
                        minWidth: 200,
                        marginBottom: 5
                        }}
                    >
                        {props.profile.phoneNumber}
                    </li>
                    <li
                        style={{
                        display: "inline-block",
                        minWidth: 200,
                        marginBottom: 5
                        }}
                    >
                        {props.profile.email}
                    </li>
                    </ul>
                </div>
                </div>
            </div>
        </div>
    )
}

export default SearchedUser;