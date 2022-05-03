import React from 'react';
import HomePage from "./components/home/Home";
import NotFoundPage from "./NotFoundPage";
import Navbar from "./components/common/navbar/Navbar";
import { Route, Switch } from "react-router-dom";
import Login from "./components/login/Login";
import Register from  "./components/register/Register";
import Users from "./components/users/Users";
import ChangePassword from "./components/users/ChangePassword";
import {connect} from "react-redux";
import AlertsContainer from "./components/alerts/AlertsContainer";
import UserProfile from './components/users/UserProfile';
import SearchUsers from './components/search-users/SearchUsers';
import MyProfile from './components/my-profile/MyProfile';


function App(props) {
    props.setAuthData({
        userId: props.userId,
        role: props.role,
        userName: props.userName,
        token: props.token
    });

    return (
        <>
        <Navbar />
        <Switch>
            <Route path="/change-password/:userId" exact component={ChangePassword} />
            <Route path="/users/" exact component={Users} />
            <Route path="/login" exact component={Login} />
            <Route path="/register" exact component={Register} />
            <Route path="/configure-profile" exact component={UserProfile} />
            <Route path="/my-profile" exact component={MyProfile} />
            <Route path="/search-users" exact component={SearchUsers} />
            <Route path="/" exact component={HomePage} />
            <Route component={NotFoundPage} />
        </Switch>
        <AlertsContainer />
    </>
    )
}

function mapDispatchToProps(dispatch) {
    return {
        setAuthData: (user) => {
            dispatch({
                type: 'SET_AUTH_FIELDS', token: user.token,
                userName: user.userName, role: user.role,
                userId: user.userId
            })
        }
    }
}

export default connect(null, mapDispatchToProps)(App);