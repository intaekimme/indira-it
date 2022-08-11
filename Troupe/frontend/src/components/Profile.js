import React from "react";
import { useParams } from "react-router-dom";
import apiClient from "../apiClient";

import Grid from "@mui/material/Grid";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";

import PerfList from "./PerfList";
import FeedList from "./FeedList";
import ProfileMemberInfo from "./ProfileMemberInfo";
import ProfileAnalyze from "./ProfileAnalyze";
import ProfileTabs from "./ProfileTabs";
import ProfileStage from "./ProfileStage";

const theme = createTheme();

function Profile(props) {
  //memberNo
  const { memberNo } = useParams();

  //화면 width에 따른 화면분할여부
  const [gridItemxs, setGridItemxs] = React.useState(window.innerWidth<1000 ? 12 : 6);

  //memberInfo 초기화
  const [memberInfo, setMemberInfo] = React.useState("");
  //memberInfo 화면분할 update 시 불러오기
  React.useEffect(() => {
    apiClient.getMemberInfo(memberNo).then((data) => {
      setMemberInfo(data);
    });
  }, [memberNo]);

  //공연자/일반 판단 초기화
  const [performer, setPerformer] = React.useState(false);
  //공연자/일반 판단 memberInfo update 시 update
  React.useEffect(() => {
    setPerformer(memberInfo.memberType === "PERFORMER");
  }, [memberInfo]);

  // 자신의 유저페이지인지 판단 초기화
  const [myPage, setMypage] = React.useState(false);
  // 자신의 유저페이지인지 판단 update
  React.useEffect(() => {
    setMypage(sessionStorage.getItem("loginMember") === memberNo);
  }, [sessionStorage.getItem("loginMember"), memberNo]);

  //1000보다 큰경우 2화면분할
  const handleGrid = () => {
    const size = window.innerWidth;
    if (size < 1000) {
      setGridItemxs(12);
    } else {
      setGridItemxs(6);
    }
  };
  //화면분할 update
  React.useEffect(() => {
    window.addEventListener("resize", handleGrid);
  }, [window.innerWidth]);

  const tabContent = performer
    ? [
        <PerfList memberInfo={memberInfo} />,
        <FeedList memberInfo={memberInfo} />,
        <PerfList memberInfo={memberInfo} save={true} />,
        <FeedList memberInfo={memberInfo} save={true} />,
      ]
    : [
        <PerfList memberInfo={memberInfo} save={true} />,
        <FeedList memberInfo={memberInfo} save={true} />,
      ];
  const tabText = performer
    ? ["공연/전시 목록", "피드 목록", "공연/전시 북마크", "피드 북마크"]
    : ["공연/전시 북마크", "피드 북마크"];
  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="1920px">
        <Grid container spacing={2} style={{ textAlign: "center" }}>
          <Grid
            item
            xs={gridItemxs}
            style={{ backgroundColor: "gray", padding: 0, position:"relative" }}
          >
            {/* Stage start */}
            <ProfileStage/>
            {/* Stage finish */}
          </Grid>
          <Grid
            item
            xs={gridItemxs}
            style={{ backgroundColor: "blue", padding: 0 }}
          >
            <Grid container spacing={3}>
              {
                //프로필
              }
              <Grid item xs={12}>
                <ProfileMemberInfo
                  width="100%"
                  memberInfo={memberInfo}
                  myPage={myPage}
                />
              </Grid>
              {
                //개인분석
              }
              <Grid item xs={12}>
                <ProfileAnalyze
                  nickname={memberInfo.nickname}
                  performer={performer}
                  myPage={myPage}
                />
              </Grid>
              {
                //공연/전시, 피드 목록
              }
              <Grid item xs={12}>
                <ProfileTabs tabContent={tabContent} tabText={tabText} />
              </Grid>
            </Grid>
          </Grid>
        </Grid>
      </Container>
    </ThemeProvider>
  );
}
export default Profile;
