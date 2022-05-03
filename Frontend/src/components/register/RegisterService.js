import axios from "../../api/axios";


function register(requestBody) {
    return axios.post('auth/register', requestBody);
}

export {
    register
};