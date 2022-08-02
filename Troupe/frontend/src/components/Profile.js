import apiClient from "../apiClient";
import React from "react";
import Grid from "@mui/material/Grid";
import Container from "@mui/material/Container";
import { Card } from "@mui/material";
import { createTheme, ThemeProvider } from "@mui/material/styles";

import Stage from "../img/stage.jpg";
import PerfList from "./PerfList";
import FeedList from "./FeedList";
import ProfileMemberInfo from "./ProfileMemberInfo";
import ProfileAnalyze from "./ProfileAnalyze";
import ProfileTabs from "./ProfileTabs";

const theme = createTheme();

export default function Profile(memberNo) {
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

  //회원 기본정보 요청
  // const profile = apiClient.getMemberInfo(memberNo);
  const memberInfo = {
    memberNo: 1,
    email: "abc@naver.com",
    nickname: "이것은닉네임이다",
    description:
      "가나다라마바사 아자차카타파하 에헤 이야이야이야 가나다라마바사 아자차카타파하 에헤 이야이야이야",
    memberType: "performer",
    profileImageUrl: Stage,
    // "https://s3.ap-northeast-2.amazonaws.com/hongjoo.troupe.project/static/94d60d59-bdc3-4322-9042-7be3e184ed2acute.jpg",
  };

  //공연자/일반 판단
  // const performer = memberInfo.memberType==="performer";
  const performer = true;

  //ProfileTabs.js 전달
  const tabContent = performer
    ? [
        <PerfList memberNo={memberInfo.memberNo} />,
        <FeedList memberNo={memberInfo.memberNo} />,
        <PerfList memberNo={memberInfo.memberNo} save={true} />,
        <FeedList memberNo={memberInfo.memberNo} save={true} />,
      ]
    : [
        <PerfList memberNo={memberInfo.memberNo} save={true} />,
        <FeedList memberNo={memberInfo.memberNo} save={true} />,
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
                <ProfileMemberInfo width="100%" memberInfo={memberInfo} />
              </Grid>
              {
                //개인분석
              }
              <Grid item xs={12}>
                <ProfileAnalyze
                  nickname={memberInfo.nickname}
                  performer={performer}
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
