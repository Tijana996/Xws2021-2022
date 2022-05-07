import React from "react";
import Comment from "../comments/Comment";

function Post(props) {
    
    function handleCommentInputChange(event) {
        if (event.key === 'Enter' && event.target.value !== "") {
            props.handleSavingComment(props.post.id, event.target.value);
            event.target.value = "";
        }

    }

    return (
        <div className="panel w-100 mb-2">
            <div className="panel-body" style={{background: '#fff', padding: "15px"}}>
            <div className="fb-user-thumb" style={{float: 'left', width: '70px', marginRight: '15px'}}>
                <img src="https://bootdey.com/img/Content/avatar/avatar1.png" alt="" style={{width: '70px', height: '70px', borderRadius: '50%', WebkitBorderRadius: '50%'}} />
            </div>

            <div className="fb-user-details">
                <h3 style={{margin: '15px 0 0', fontSize: '18px', fontWeight: 300}}><span>{props.post.userName} {props.post.userLastName}</span></h3>
                <p style={{color: '#c3c3c3'}}>{props.post.timestamp}</p>
            </div>

            <div className="clearfix" />
                <p className="fb-user-status" style={{padding: '10px 0', lineHeight: '20px'}}>{props.post.content}</p>
                <div className="fb-status-container fb-border" style={{borderTop: '1px solid #ebeef5', margin: '0 -15px 0 -15px', padding: '0 15px'}}>
                    <div className="fb-time-action" style={{padding: '15px 0'}}>
                    <span title="Like this" style={{marginRight: '5px', color: '#2972a1'}}>Like</span>
                    <span style={{marginRight: '5px', color: '#5a5a5a'}}>-</span>
                    <span title="Dislike this" style={{marginRight: '5px', color: '#2972a1'}}>Dislike</span>
                    <span style={{marginRight: '5px', color: '#5a5a5a'}}>-</span>
                    <span title="Leave a comment" style={{marginRight: '5px', color: '#2972a1'}}>Comments</span>
                </div>
            </div>

            <div className="fb-status-container fb-border fb-gray-bg" style={{borderTop: '1px solid #ebeef5', margin: '0 -15px 0 -15px', padding: '0 15px', background: '#f6f6f6'}}>
                <div className="fb-time-action like-info" style={{padding: '15px 0'}}>
                    <span style={{marginRight: '5px', color: '#2972a1'}}>Jhon Due,</span>
                    <span style={{marginRight: '5px', color: '#2972a1'}}>Danieal Kalion</span>
                    <span style={{marginRight: '5px', color: '#5a5a5a'}}>and</span>
                    <span style={{marginRight: '5px', color: '#2972a1'}}>40 more</span>
                    <span style={{marginRight: '5px', color: '#5a5a5a'}}>like this</span>
                </div>

                <ul className="fb-comments" style={{listStyleType: 'none'}}>
                    {props.post.comments.map(comment => <Comment key={comment.id} comment={comment} />)}
                    <li style={{margin: '0 -15px 0 -15px', padding: '15px', borderTop: '1px solid #ebeef5'}}>
                        <span className="cmt-thumb" style={{width: '50px', float: 'left', marginRight: '15px'}}>
                        <img src="https://bootdey.com/img/Content/avatar/avatar1.png" alt="" style={{width: '50px'}} /></span>

                        <div className="cmt-form" style={{display: 'inline-block', width: '90%'}}>
                            <input type="text" className="form-control" placeholder="Unesite komentar" style={{height: '50px', lineHeight: '35px'}} onKeyUp={handleCommentInputChange} />
                        </div>
                    </li>
                </ul>
                <div className="clearfix" /></div>
            </div>
        </div>
    )
}

export default Post;