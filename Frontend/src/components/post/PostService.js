import axios from "../../api/axios";


function saveNewPost(requestBody) {
    return axios.post('posts/', requestBody);
}

export {
    saveNewPost
};