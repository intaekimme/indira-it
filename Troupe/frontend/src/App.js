import React from "react";
import { useParams } from "react-router-dom";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Navigate } from "react-router-dom";
import Header from "./Header";
import PerfList from "./components/PerfList";
import FeedList from "./components/FeedList";
import Login from "./components/Login";
import Signup from "./components/Signup";
import ProfileForm from "./components/ProfileForm";
import ResetPw from "./components/ResetPw";
import Profile from "./components/Profile";
import Test from "./components/Test";
import PerfDetail from "./components/PerfDetail";
import PerfNew from "./components/PerfNew";

function App() {
  const loginCheck = () => {
    return window.sessionStorage.getItem("loginCheck") === "true";
  };
  const [memberData, setMemberData] = React.useState(window.sessionStorage.getItem("memberData"));
  React.useEffect(() => {
    setMemberData(window.sessionStorage.getItem("memberData"));
  }, [window.sessionStorage.getItem("memberData")]);
  const myPageCheck = () => {
    return true;
  }
  return (
    <div>
      <div>
        <Header />
      </div>
      <Router>
        <Routes>
          <Route path="/" element={<PerfList />}></Route>
          <Route path="/perf/list" element={<PerfList />}></Route>
          <Route path="/feed/list" element={<FeedList />}></Route>
          <Route path="/login" element={loginCheck() ? <Navigate to="/" /> : <Login />}></Route>
          <Route path="/signup" element={loginCheck() ? <Navigate to="/" /> : <Signup />}></Route>
          <Route path="/resetpw" element={loginCheck() ? <Navigate to="/" /> : <ResetPw />}></Route>
          <Route path="/profile/:memberNo" element={<Profile />}></Route>
          <Route path="/profile/:memberNo/:modify" element={
            true
             ? <ProfileForm /> : <Navigate to="/" />}></Route>
          <Route path="/test" element={<Test />}></Route>
          <Route path="/perf/detail" element={<PerfDetail />}></Route>
          <Route path="/perf/new" element={<PerfNew />}></Route>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
