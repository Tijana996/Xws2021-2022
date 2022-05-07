import React, {useState, useEffect, useRef} from 'react';
import { getProfileAndPosts, saveNewComment } from './ProfileService';
import { connect } from 'react-redux';
import Post from '../post/Post';
import { saveNewPost } from '../post/PostService';

function Profile(props) {

    const [profile, setProfile] = useState({
        name: "",
        lastName: "",
        isPrivateProfile: false,
        posts: []
    });

    const newPostText = useRef();

    useEffect(() => {
        const userId = props.requestUserId ? props.requestUserId : props.userId;
        getProfileAndPosts(userId, props.token).then(response => {
            setProfile({
                name: response.data.name,
                lastName: response.data.lastName,
                isPrivateProfile: response.data.privateProfile,
                posts: response.data.posts
            });
        });
    }, []);

    function savePostHandler() {
        const postText = newPostText.current.value;
        const userId = props.requestUserId ? props.requestUserId : props.userId;
        saveNewPost({
            content: postText,
            userId: userId,
            userName: profile.name,
            userLastName: profile.lastName
        }, props.token).then(response => {
            newPostText.current.value = "";
            getProfileAndPosts(userId, props.token).then(response => {
                setProfile({
                    name: response.data.name,
                    lastName: response.data.lastName,
                    posts: response.data.posts
                });
            });
        });
    }

    function saveComment(postId, comment) {
        saveNewComment({
            postId: postId,
            content: comment,
            userName: profile.name,
            userSurname: profile.lastName,
            userId: props.userId
        }, props.token).then(response => {
            const userId = props.requestUserId ? props.requestUserId : props.userId;
            getProfileAndPosts(userId, props.token).then(response => {
                setProfile({
                    name: response.data.name,
                    lastName: response.data.lastName,
                    posts: response.data.posts
                });
            });
        });
    }
    
    return (
        <div className="body-replica" style={{color: "#797979", background: "#ddd", fontFamily: "'Open Sans', sans-serif", padding: "0px !important", margin: "0px !important", fontSize: "13px", textRendering: "optimizeLegibility", WebkitFontSmoothing: "antialiased", MozFontSmoothing: "antialiased", MarginTop: "20px"}}>
            <div className="container bootstrap snippets bootdey">
                <div className="row">
                <div className="panel" style={{width: "100%", marginBottom: "20px"}}>
                    <div className="cover-photo" style={{position: 'relative'}}>
                        <div className="fb-timeline-img">
                            <img src="https://bootdey.com/img/Content/bg1.jpg" alt="" style={{width: '100%', height: 'auto', maxHeight: '280px', borderRadius: '4px 4px 0 0', WebkitBorderRadius: '4px 4px 0 0'}} />
                        </div>
                        <div className="fb-name" style={{bottom: '5px', left: '175px', position: 'absolute'}}>
                            <h2><span style={{color: '#FFFFFF', textRendering: 'optimizelegibility', textShadow: '0 0 3px rgba(0, 0, 0, 0.8)', fontSize: '25px'}}>{profile.name} {profile.lastName}</span></h2>
                        </div>
                    </div>

                    <div className="panel-body" style={{background: '#fff', padding: "15px"}}>

                        <div className="profile-thumb" style={{float: 'left', position: 'relative'}}>
                            <img src="https://bootdey.com/img/Content/avatar/avatar1.png" alt="" style={{width: '140px', height: '140px', borderRadius: '50%', WebkitBorderRadius: '50%', marginTop: '-90px', border: '3px solid #fff'}} />
                        </div>

                        <div style={{height: 50}}></div>
                    </div>
                </div>

                <div className="panel profile-info" style={{width: "100%", marginBottom: '20px'}} hidden={props.requestUserId}>
                    <form>
                     <textarea ref={newPostText} className="form-control input-lg p-text-area" rows={2} placeholder="O čemu razmišljate?" style={{border: 'none', fontWeight: 300, boxShadow: 'none', color: '#c3c3c3', fontSize: '16px'}} defaultValue={""} />
                    </form>

                    <footer className="panel-footer" style={{"padding":"10px 15px","backgroundColor":"#f5f5f5","borderTop":"1px solid #ddd","borderBottomRightRadius":"3px","borderBottomLeftRadius":"3px"}}>
                        <button type="button" className="btn btn-info pull-right" style={{float: "right !important"}} onClick={savePostHandler}>Post</button>
                    </footer>
                </div>
                
                {props.requestUserId && profile.isPrivateProfile ? 
                    <h5 className="mt-5">Profil je privatan.</h5> :
                    <>
                        {profile.posts.length === 0 && <h5 className="mt-5">Nema dosadašnjih objava.</h5>}
                        {profile.posts.map(post => <Post handleSavingComment={saveComment} key={post.id} post={post} />)}
                    </>
                }
                
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

export default connect(mapStateToProps)(Profile);