import axios from "../../api/axios";


function getProfileAndPosts(userId) {
    return axios.get('posts/user/' + userId);
}

export {
    getProfileAndPosts
};