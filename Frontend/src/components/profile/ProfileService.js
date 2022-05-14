import axios from "../../api/axios";


function getProfileAndPosts(userId, token) {
    return axios.get('posts/user/' + userId, {headers: {authorization: token}});
}

function saveNewComment(requestBody, token) {
    return axios.post('comments', requestBody, {headers: {authorization: token}});
}

function likePost(requestBody, token) {
    return axios.post('posts/like', requestBody, {headers: {authorization: token}});
}

function dislikePost(requestBody, token) {
    return axios.post('posts/dislike', requestBody, {headers: {authorization: token}});
}

export {
    getProfileAndPosts,
    saveNewComment,
    likePost,
    dislikePost
};