import axios from "../../api/axios";


function getProfileAndPosts(userId, token) {
    return axios.get('posts/user/' + userId, {headers: {authorization: token}});
}

function saveNewComment(requestBody, token) {
    return axios.post('comments', requestBody, {headers: {authorization: token}});
}

export {
    getProfileAndPosts,
    saveNewComment
};