import React from 'react'
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Header from './Header';
import PerfList from './routes/PerfList';
import FeedList from './routes/FeedList';
import Login from './routes/Login';
import SignUp from './routes/SignUp';
import ResetPw from './routes/ResetPw';
import Profile from './routes/Profile';
import Test from './routes/Test';

function App() {
  return (
    <div>
      <div><Header /></div>
      <Router>
        <Routes>
          <Route path="/" element={<PerfList />}></Route>
          <Route path="/perf/list" element={<PerfList />}></Route>
          <Route path="/feed/list" element={<FeedList />}></Route>
          <Route path="/login" element={ <Login /> }></Route>
          <Route path="/signup" element={ <SignUp /> }></Route>
          <Route path="/resetpw" element={ <ResetPw /> }></Route>
          <Route path="/profile" element={ <Profile /> }></Route>
          <Route path="/test" element={ <Test /> }></Route>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
