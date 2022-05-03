import axios from "../../api/axios";

function searchProfiles(token, name, userId) {
    let url = '/users/find/?name=' + name;
    if (userId) {
        url += '&ignoredId=' + userId;
    }
    return axios.get(url, {headers: {authorization: token}})
}

export {
    searchProfiles
}