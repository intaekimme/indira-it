import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import axios from "axios";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  // <React.StrictMode>
  <App />,
  // </React.StrictMode>
);
// axios.defaults.baseURL = "http://i7a804.p.ssafy.io:8081";
axios.defaults.baseURL = "http://localhost:8081";
