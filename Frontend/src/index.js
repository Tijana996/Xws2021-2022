import 'bootstrap/dist/css/bootstrap.min.css';
import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import { BrowserRouter as Router } from 'react-router-dom';
import {Provider} from "react-redux";
import {createStore} from "redux";
import rootReducer from "./reducers/rootReducer";
import axios from "../src/api/axios";

let token = localStorage.getItem('Token');
let userName = localStorage.getItem('userName');
let userLastName = localStorage.getItem('userLastName');
let userId = null;

const authorizationHeader = token === null ? '' : 'Bearer ' + token;
axios.get('/auth/validate-token', {headers: {Authorization: authorizationHeader}}).then(response => {
    if(!response.data.userId) {
        token = null;
        userId = null;
        userName = null;
        userLastName = null;
    } else {
        userId = response.data.userId;
    }
}).catch(error => {
    userId = null;
    userName = null;
    userLastName = null;
    token = null;
}).finally(() => {
    const store = createStore(rootReducer);
    ReactDOM.render(
        <React.StrictMode>
            <Router>
                <Provider store={store}><App token={token} userName={userName} userId={userId} userLastName={userLastName} /></Provider>
            </Router>
        </React.StrictMode>,
        document.getElementById('root')
    );
});


