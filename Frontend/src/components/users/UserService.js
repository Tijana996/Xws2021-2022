import axios from "../../api/axios";

function getUsers(token) {
    return axios.get('/users', {headers: {authorization: token}})
}

function getUserById(userId, token) {
    return axios.get('/users/' + userId , {headers: {authorization: token}})
}

function saveUser(requestBody, token) {
    return axios.post('/users/register', requestBody, {headers: {authorization: token}})
}

function changePassword(userId, requestBody, token) {
    return axios.patch('/auth/change-password/' + userId, requestBody, {headers: {authorization: token}})
}

function fetchUserData(userId, token) {
    return axios.get('/users/' + userId, {headers: {authorization: token}});
}

function saveUserProfile(requestBody, userId, token) {
    return axios.put('/users/' + userId, requestBody, {headers: {authorization: token}});
}

export {
    getUsers,
    getUserById,
    saveUser,
    changePassword,
    fetchUserData, 
    saveUserProfile
}