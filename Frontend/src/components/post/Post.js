import React, {useState, useEffect} from "react";
import Comment from "../comments/Comment";

function Post(props) {

    const [likes, setLikes] = useState([]);
    const [dislikes, setDislikes] = useState([]);

    useEffect(() => {
        setLikes(props.post.likes);
        setDislikes(props.post.dislikes);
    }, []);
    
    function handleCommentInputChange(event) {
        if (event.key === 'Enter' && event.target.value !== "") {
            props.handleSavingComment(props.post.id, event.target.value);
            event.target.value = "";
        }
    }

    function handleLikeClick() {
        props.likePost(props.post.id).then(response => {
            setLikes(response.data.likes);
            setDislikes(response.data.dislikes);
        });
    }

    function handleDislikeClick() {
        props.dislikePost(props.post.id).then(response => {
            setLikes(response.data.likes);
            setDislikes(response.data.dislikes);
        });
    }

    const isLikedByCurrentUser = likes.some(like => like.userId === props.currentUserId);
    const isDislikedByCurrentUser = dislikes.some(dislike => dislike.userId === props.currentUserId);
    const likeButtonClass = isLikedByCurrentUser ? "btn btn-primary" : "btn btn-outline-primary";
    const dislikeButtonClass = isDislikedByCurrentUser ? "btn btn-danger" : "btn btn-outline-danger";

    const likesText = isLikedByCurrentUser ? `Vama i još ${likes.length - 1} se sviđa ovaj post` : `${likes.length} korsinika voli ovaj post.`;
    const dislikesText = isDislikedByCurrentUser ? `Vama i još ${dislikes.length - 1} se ne sviđa ovaj post` : `${dislikes.length} korsinika ne voli ovaj post.`;

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
                    <button className={likeButtonClass} onClick={handleLikeClick}>Like</button>
                    <span style={{marginRight: '10px', color: '#5a5a5a'}}></span>
                    <button className={dislikeButtonClass} onClick={handleDislikeClick}>Dislike</button>
                </div>
            </div>

            <div className="fb-status-container fb-border fb-gray-bg" style={{borderTop: '1px solid #ebeef5', margin: '0 -15px 0 -15px', padding: '0 15px', background: '#f6f6f6'}}>
                <div className="fb-time-action like-info" style={{padding: '15px 0'}}>
                    <span style={{marginRight: '5px', color: '#2972a1'}}>{likesText}</span>
                </div>

                <div className="fb-time-action like-info" style={{padding: '15px 0'}}>
                    <span style={{marginRight: '5px', color: '#2972a1'}}>{dislikesText}</span>
                </div>

                <ul className="fb-comments" style={{listStyleType: 'none'}}>
                    {props.post.comments.map(comment => <Comment key={comment.id} comment={comment} />)}
                    {props.commentingAllowed ?
                    <li style={{margin: '0 -15px 0 -15px', padding: '15px', borderTop: '1px solid #ebeef5'}}>
                        <span className="cmt-thumb" style={{width: '50px', float: 'left', marginRight: '15px'}}>
                        <img src="https://bootdey.com/img/Content/avatar/avatar1.png" alt="" style={{width: '50px'}} /></span>

                        <div className="cmt-form" style={{display: 'inline-block', width: '90%'}}>
                            <input type="text" className="form-control" placeholder="Unesite komentar" style={{height: '50px', lineHeight: '35px'}} onKeyUp={handleCommentInputChange} />
                        </div>
                    </li> : null
                    }
                    
                </ul>
                <div className="clearfix" /></div>
            </div>
        </div>
    )
}

export default Post;