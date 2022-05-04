import axios from "../../api/axios";


function saveNewPost(requestBody, token) {
    return axios.post('posts/', requestBody, {headers: {authorization: token}});
}

export {
    saveNewPost
};