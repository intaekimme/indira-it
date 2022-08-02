import * as React from "react";
import { styled } from "@mui/material/styles";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import Grid from "@mui/material/Grid";
import InfoOutlinedIcon from "@mui/icons-material/InfoOutlined";

import apiClient from "../apiClient";
import InterestPolygon from "./InterestPolygon";
import LikeabilityRank from "./LikeabilityRank";
import styledTooltip from "../css/tooltip.module.css";

const ExpandMore = styled((props) => {
  const { expand, ...other } = props;
  return <IconButton {...other} />;
})(({ theme, expand }) => ({
  transform: !expand ? "rotate(0deg)" : "rotate(180deg)",
  marginLeft: "auto",
  transition: theme.transitions.create("transform", {
    duration: theme.transitions.duration.shortest,
  }),
}));

export default function ProfileAnalyze(props) {
  // 자신의 유저페이지인지 판단
  // const [mypage, setMypage] = React.useState(window.sessionStorage.getItem('loginUser').userNo === props.userNo);
  const [mypage, setMypage] = React.useState(false);
  // 이 유저를 팔로우 했는지 판단
  const [followThisUser, setFollowThisUser] = React.useState(true);
  //나와의 호감도보기
  const [likeabilityWithUser, setLikeabilityWithUser] = React.useState(false);
  //개인분석 결과
  const [interest, setInterest] = React.useState([10,20,30,40,50,60,70,80]);
  //호감도 순위
  // const [likeabilityData, setLikeabilityData] = React.useState([apiClient.getLikeabilityData]);
  const [likeabilityData, setLikeabilityData] = React.useState([
    {
      starUserNo: 1,
      nickname: "첫번째",
      exp: 1520
    },
    {
      starUserNo: 2,
      nickname: "두번째",
      exp: 1250
    },
    {
      starUserNo: 1,
      nickname: "세번째",
      exp: 970
    },
  ]);
  // setInterest(apiClient.interest(props.userNo));
  // 나와의 호감도/ 이유저의 호감도 보기 버튼클릭
  const changeLikeability = () => {
    setLikeabilityWithUser((current) => !current);
  };

  React.useEffect(() => {
    if (!props.performer) {
      setLikeabilityWithUser(false);
    }
  }, [props.performer]);

  return (
    <Card
      sx={{ width: props.width }}
      style={{ border: "5px solid #FFCF24", backgroundColor: "#FFFFFF" }}
    >
      <CardContent>
        {likeabilityWithUser ? (
          <Grid container spacing={1} style={{ textAlign: "center" }}>
            <Grid item xs={12}>
              <div style={{ position: "relative", textAlign: "right" }}>
                <div className={styledTooltip.tooltipcontainer}></div>
                <div className={styledTooltip.tooltip}>
                  <InfoOutlinedIcon />
                  <Button
                    style={{ color: "black" }}
                    onClick={changeLikeability}
                  >
                    {props.nickname} 님의 호감도 순위 보기
                  </Button>
                  <div
                    className={`${styledTooltip.tooltiptext} ${styledTooltip.tooltipleft}`}
                  >
                    {props.nickname} 님의 호감도 순위
                  </div>
                </div>
              </div>
            </Grid>
            <Grid item xs={12}>
              <LikeabilityRank
              nickname={props.nickname}
              likeabilityWithUser={likeabilityWithUser}
              likeabilityData = {likeabilityData}
              ></LikeabilityRank>
            </Grid>
          </Grid>
        ) : (
          <Grid container spacing={2} style={{ textAlign: "center" }}>
            <Grid item xs={6}>
              <div style={{ position: "relative", textAlign: "left" }}>
                <div>{props.nickname} 님의 관심사</div>
              </div>
            </Grid>
            <Grid item xs={6}>
              {mypage || !props.performer ? (
                <div></div>
              ) : (
                <div style={{ position: "relative", textAlign: "right" }}>
                  <div className={styledTooltip.tooltipcontainer}>
                    <div className={styledTooltip.tooltip}>
                      <InfoOutlinedIcon />
                      <Button
                        style={{ color: "black" }}
                        onClick={changeLikeability}
                      >
                        나와의 호감도 보기
                      </Button>
                      <div
                        className={`${styledTooltip.tooltiptext} ${styledTooltip.tooltipleft}`}
                      >
                        {props.nickname} 님에 대한 나의 호감도 순위
                      </div>
                    </div>
                    {/* )} */}
                  </div>
                </div>
              )}
            </Grid>
            <Grid item xs={6}>
              <InterestPolygon nickname={props.nickname} interest={interest}></InterestPolygon>
            </Grid>
            <Grid item xs={6}>
              <LikeabilityRank
                nickname={props.nickname}
                likeabilityWithUser={likeabilityWithUser}
                likeabilityData = {likeabilityData}
                width={props.width}
                >
              </LikeabilityRank>
            </Grid>
          </Grid>
        )}
      </CardContent>
    </Card>
  );
}
