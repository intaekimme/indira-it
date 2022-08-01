import apiClient from "../apiClient";
import React from "react";
import Grid from "@mui/material/Grid";
import Container from "@mui/material/Container";
import { Card } from "@mui/material";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";

import Stage from "../img/stage.jpg";
import PerfList from "./PerfList";
import FeedList from "./FeedList";
import ProfileUserInfo from "./ProfileUserInfo";
import ProfileAnalyze from "./ProfileAnalyze";
import ProfileTabs from "./ProfileTabs";

const theme = createTheme();

function Profile(userNo) {
  //화면 width
  const [widthSize, setWidthSize] = React.useState(0);
  //화면 width에 따른 화면분할여부
  const [gridItemxs, setGridItemxs] = React.useState(6);
  window.addEventListener("resize", (e) => {
    const size = window.innerWidth;
    setWidthSize(size);
    if (size < 1000) {
      setGridItemxs(12);
    } else {
      setGridItemxs(6);
    }
  });

  const profile = {
    image: (
      <AccountCircleIcon
        fontSize="large"
        sx={{ fontSize: "200px" }}
      ></AccountCircleIcon>
    ),
    nickname: "닉네임",
    content:
      "가나다라마바사 아자차카타파하 에헤 이야이야이야 가나다라마바사 아자차카타파하 에헤 이야이야이야",
    follower: 1234567,
  };

  // const profile = apiClient.getProfile(userNo);

  //공연자/일반 판단
  // const performer = props.member_type==="performer";
  const performer = true;

  const tabContent = performer ? [<PerfList/>, <FeedList/>, <PerfList/>, <FeedList/>] : [<PerfList/>, <FeedList/>];
  const tabText = performer ? ["공연/전시 목록", "피드 목록", "공연/전시 북마크", "피드 북마크"] : ["공연/전시 북마크", "피드 북마크"];
  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="1920px">
        <Grid container spacing={2} style={{ textAlign: "center" }}>
          <Grid
            item
            xs={gridItemxs}
            style={{ backgroundColor: "gray", padding: 0 }}
          >
            {/* Stage start */}
            <img
              src={Stage}
              alt={Stage}
              style={{
                objectFit: "contain",
                width: "100%",
                height: "100%",
              }}
            />
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
                <ProfileUserInfo
                  width="100%"
                  userNo={userNo}
                  profile={profile.image}
                  nickname={profile.nickname}
                  content={profile.content}
                  follower={profile.follower}
                />
              </Grid>
              {
                //개인분석
              }
              <Grid item xs={12}>
                <ProfileAnalyze nickname={profile.nickname} performer={performer}/>
              </Grid>
              {
                //공연/전시, 피드 목록
              }
              <Grid item xs={12}>
                <ProfileTabs tabContent={tabContent} tabText = {tabText}/>
              </Grid>
            </Grid>
          </Grid>
        </Grid>
      </Container>
    </ThemeProvider>
  );
}
export default Profile;
