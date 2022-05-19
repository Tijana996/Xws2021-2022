import React, {useState, useEffect} from "react";

function FollowingRequest(props) {

    function handleAcceptRequest() {
        props.handleAcceptRequest(props.request.userId, props.request.userName, props.request.userLastName);
    }

    function handleDeclineRequest() {
        props.handleDeclineRequest(props.request.userId, props.request.userName, props.request.userLastName);
    }

    return (
        <div className='d-flex justify-content-between p-2 mb-1' style={{background: 'white'}}>
            <h4 style={{alignSelf: 'center'}}>{props.request.userName} {props.request.userLastName}</h4>
            <div>
                <button className='btn btn-success mr-2' onClick={handleAcceptRequest}>Prihvati</button>
                <button className='btn btn-danger' onClick={handleDeclineRequest}>Odbij</button>
            </div>
        </div>
    );
}

export default FollowingRequest;