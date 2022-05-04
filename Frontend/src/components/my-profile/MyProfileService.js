import axios from "../../api/axios";


function getProfileAndPosts(userId, token) {
    return axios.get('posts/user/' + userId, {headers: {authorization: token}});
}

export {
    getProfileAndPosts
};