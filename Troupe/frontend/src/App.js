import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Navigate } from "react-router-dom";
import Header from "./Header";
import PerfList from "./components/PerfList";
import FeedList from "./components/FeedList";
import Login from "./components/Login";
import Signup from "./components/Signup";
import EmailSend from "./components/EmailSend";
import ProfileForm from "./components/ProfileForm";
import ResetPw from "./components/ResetPw";
import Profile from "./components/Profile";
import Test from "./components/Test";
import PerfDetail from "./components/PerfDetail";
import PerfNew from "./components/PerfNew";
import FeedRegister from "./components/FeedRegister";
import FeedDetail from "./components/FeedDetail";
import FeedModify from "./components/FeedModify";
import GuestBook from "./components/GuestBook";
import { QueryClient, QueryClientProvider } from "react-query";
import { ReactQueryDevtools } from 'react-query/devtools';

const queryClient = new QueryClient();

function App() {
  const loginCheck = () => {
    return window.sessionStorage.getItem("loginCheck") === "true";
  };
  const [memberData, setMemberData] = React.useState(
    window.sessionStorage.getItem("memberData"),
  );
  React.useEffect(() => {
    setMemberData(window.sessionStorage.getItem("memberData"));
  }, [window.sessionStorage.getItem("memberData")]);
  const myPageCheck = () => {
    return true;
  };
  return (
    <div>
      <div>
        <Header />
      </div>
      <QueryClientProvider client={queryClient}>
        <Router>
          <Routes>
            <Route path="/" element={<PerfList />}></Route>
            <Route path="/perf/list/:startNo" element={<PerfList />}></Route>
            <Route path="/feed/list" element={<FeedList />}></Route>
            <Route path="/login" element={loginCheck() ? <Navigate to="/" /> : <Login />} ></Route>
            <Route path="/signup" element={loginCheck() ? <Navigate to="/" /> : <Signup />} ></Route>
            <Route path="/email" element={<EmailSend />}></Route>
            <Route path="/resetpw" element={loginCheck() ? <ResetPw /> : <Navigate to="/" />} ></Route>
            <Route path="/member/reset-password/:token" element={loginCheck() ? <ResetPw /> : <Navigate to="/" />} ></Route>
            <Route path="/profile/:memberNo" element={<Profile />}></Route>
            <Route path="/profile/:memberNo/:modify" element={true ? <ProfileForm /> : <Navigate to="/" />} ></Route>
            <Route path="/test" element={<Test />}></Route>
            <Route path="/perf/detail" element={<PerfDetail />}></Route>
            <Route path="/perf/new" element={<PerfNew />}></Route>
            <Route path="/feed/register" element={<FeedRegister />}></Route>
            <Route path="/feed/detail/:feedNo" element={<FeedDetail />}></Route>
            <Route path="/feed/modify/:feedNo" element={<FeedModify />}></Route>
            <Route path="/guestbook/test" element={<GuestBook/>}></Route>
          </Routes>
        </Router>
        <ReactQueryDevtools initialIsOpen={true} position="bottom-right" />
      </QueryClientProvider>
    </div>
  );
}

export default App;
