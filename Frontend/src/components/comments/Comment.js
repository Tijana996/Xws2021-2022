import React from "react";

function Comment(props) {
    return (
        <li key={props.comment.id} style={{margin: '0 -15px 0 -15px', padding: '15px', borderTop: '1px solid #ebeef5'}}>
            <span className="cmt-thumb" style={{width: '50px', float: 'left', marginRight: '15px'}}>
            <img src="https://bootdey.com/img/Content/avatar/avatar1.png" alt="" style={{width: '50px'}} /></span>
            <div className="cmt-details" style={{paddingTop: '5px'}}>
            <span style={{fontSize: '14px', fontWeight: 'bold'}}>{props.comment.userName} {props.comment.userSurname}</span>
            <span> {props.comment.timestamp} </span>
            <p>{props.comment.content}</p>
            </div>
        </li>
    )
}

export default Comment;
