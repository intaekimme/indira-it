import React from 'react'
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Navigate } from "react-router-dom";
import Header from './Header';
import PerfList from './components/PerfList';
import FeedList from './components/FeedList';
import Login from './components/Login';
import SignUp from './components/SignUp';
import ResetPw from './components/ResetPw';
import Profile from './components/Profile';
import Test from './components/Test';
import PerfDetail from './components/PerfDetail';

function App() {
  const loginCheck = ()=> {
    if(window.sessionStorage.getItem('loginCheck') === "true"){
      window.location.href='/';
    }
  }
  return (
    <div>
      <div><Header /></div>
      <Router>
        <Routes>
          <Route path="/" element={<PerfList />}></Route>
          <Route path="/perf/list" element={<PerfList />}></Route>
          <Route path="/feed/list" element={<FeedList />}></Route>
          <Route path="/login" element={ window.sessionStorage.getItem('loginCheck') === "true" ? <Navigate to="/" /> : <Login /> }></Route>
          <Route path="/signup" element={ window.sessionStorage.getItem('loginCheck') === "true" ? <Navigate to="/" /> : <SignUp /> }></Route>
          <Route path="/resetpw" element={ <ResetPw /> }></Route>
          <Route path="/profile" element={ <Profile /> }></Route>
          <Route path="/test" element={ <Test /> }></Route>
          <Route path="/perf/detail" element={ <PerfDetail /> }></Route>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
