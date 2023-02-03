import axios from 'axios'
import store from "@/store";

const instance = axios.create({
    baseURL: 'http://localhost:8080',
    headers: {'Access-Control-Allow-Origin': '*'}
});

instance.interceptors.request.use(
    function (config) {
        config.headers.accessToken = `Bearer ${store.state.token}`
        return config;
    },
    function (error) {
        return Promise.reject(error);
    }
);

// instance.interceptors.response.use(
//     function (response) {
//         return response;
//     },
//
//     function (error) {
//         return Promise.reject(error);
//     }
// );

export default instance;