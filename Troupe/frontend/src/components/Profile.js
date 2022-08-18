import React from "react";
import { useParams } from "react-router-dom";
import apiClient from "../apiClient";

import Grid from "@mui/material/Grid";
import Container from "@mui/material/Container";
import Button from "@mui/material/Button";
import { ThemeProvider } from "@mui/material/styles";

import MainPerf from "./MainPerf";
import MainFeed from "./MainFeed";
import PerfList from "./PerfList";
import _MinhoPerfList from "./_MinhoPerfList";
import _MinhoFeedList from "./_MinhoFeedList";
import ProfileMemberInfo from "./ProfileMemberInfo";
import ProfileAnalyze from "./ProfileAnalyze";
import ProfileTabs from "./ProfileTabs";
import ProfileStage from "./ProfileStage";
import GuestBook from "./GuestBook";
import Avatar from "./Avatar";
import Theme from "./Theme";

function Profile(props) {
  //memberNo
  const { memberNo } = useParams();
  //이 member의 아바타
  const [avatar, setAvatar] = React.useState("");

  //화면 width에 따른 화면분할여부
  const [gridItemxs, setGridItemxs] = React.useState(
    window.innerWidth < 1000 ? 12 : 6,
  );

  //memberInfo 초기화
  const [memberInfo, setMemberInfo] = React.useState("");
  //memberInfo 화면분할 update 시 불러오기
  React.useEffect(() => {
    if (memberNo) {
      apiClient.getMemberInfo(memberNo).then((data) => {
        setMemberInfo(data);
        console.log(data);
      });
    }
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

  //member Avatar update
  React.useEffect(() => {
    if (!memberNo) {
      return;
    }
    apiClient.getMemberAvatar(memberNo).then((data) => {
      console.log(data);
      setAvatar(data);
    });
  }, [memberNo]);

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
        <_MinhoPerfList memberInfo={memberInfo} string="myperf"/>,
        <_MinhoFeedList memberInfo={memberInfo} string="register"/>,
        <_MinhoPerfList memberInfo={memberInfo} save={true} string="saveperf"/>,
        <_MinhoFeedList memberInfo={memberInfo} save={true} string="save" />,
      ]
    : [
        <_MinhoPerfList memberInfo={memberInfo} save={true} string="saveperf"/>,
        <_MinhoFeedList memberInfo={memberInfo} save={true} string="save"/>,
      ];
  const tabText = performer
    ? ["공연/전시 목록", "피드 목록", "공연/전시 북마크", "피드 북마크"]
    : ["공연/전시 북마크", "피드 북마크"];

  const modifyAvatar = () => {
    window.location.href = `/profile/${memberNo}/modify-avatar`;
  };
  return (
    <ThemeProvider theme={Theme}>
      <Container component="main" maxWidth="1920px">
        <Grid
          container
          spacing={2}
          style={{ textAlign: "center", fontFamily: "SBAggroB" }}
        >
          <Grid
            item
            xs={gridItemxs}
            container
            justifyContent="flex-start"
            alignItems="flex-end"
            style={{
              backgroundColor: "gray",
              paddingRight: 20,
              paddingLeft: 10,
              paddingBottom: 10,
              position: "relative",
            }}
          >
            {/* Stage start */}
            <ProfileStage style={{width:"100%", height:"100%"}} memberNickname={ memberInfo.nickname } memberNo={memberNo}
            modifyAvatar = {modifyAvatar} avatar={avatar} gridItemxs={gridItemxs}/>
            {/* Stage finish */}
            {/* 방명록 */}
            <GuestBook memberNickname={ memberInfo.nickname } memberNo={memberNo}/>
          </Grid>
          <Grid item xs={gridItemxs} style={{ padding: 0 }}>
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
                  width="100%"
                  nickname={memberInfo.nickname}
                  performer={performer}
                  myPage={myPage}
                />
              </Grid>
            </Grid>
          </Grid>
          {
            //공연/전시, 피드 목록
          }
          <Grid item xs={12} style={{ padding: 0, paddingTop: "30px" }}>
            <ProfileTabs tabContent={tabContent} tabText={tabText} />
          </Grid>
        </Grid>
      </Container>
    </ThemeProvider>
  );
}
export default Profile;
