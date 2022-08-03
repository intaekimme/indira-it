import React from "react";
import { useParams } from "react-router-dom";
import apiClient from "../apiClient";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Button from "@mui/material/Button";
import Grid from "@mui/material/Grid";
import InfoOutlinedIcon from "@mui/icons-material/InfoOutlined";

import InterestPolygon from "./InterestPolygon";
import LikeabilityRank from "./LikeabilityRank";
import styledTooltip from "../css/tooltip.module.css";

export default function ProfileAnalyze(props) {
  //memberNo
  const { memberNo } = useParams();

  //공연자/일반 판단 초기화
  const performer = props.performer;

  // 자신의 유저페이지인지 판단
  const myPage = props.myPage;

  //나와의 호감도보기
  const [likeabilityWithMember, setLikeabilityWithMember] =
    React.useState(false);

  //개인분석 결과
  // //이 member의 관심카테고리
  // const [interestCategory, setInterestCategory] = React.useState("");
  // React.useEffect(() => {
  //   apiClient.getInterestCategory(memberNo).then((data) => {
  //     setInterestCategory(data);
  //   });
  // });
  const [interestCategory, setInterestCategory] = React.useState([
    10, 20, 30, 40, 50, 60, 70, 80,
  ]);

  //이 member의 호감도 data
  // const [performerLikeabilityData, setPerformerLikeabilityData] = React.useState("");
  // React.useEffect(() => {
  //   apiClient.getPerformerLikeabilityData(memberNo).then((data) => {
  //     setPerformerLikeabilityData(data);
  //   });
  // });
  const [performerLikeabilityData, setPerformerLikeabilityData] =
    React.useState([
      {
        memberNo: 1,
        nickname: "첫번째",
        exp: 1520,
      },
      {
        memberNo: 2,
        nickname: "두번째",
        exp: 1250,
      },
      {
        memberNo: 3,
        nickname: "세번째",
        exp: 970,
      },
    ]);
  // 이 member에 대한 나의 호감도 data
  // const [myLikeabilityData, setMyLikeabilityData] = React.useState("");
  // React.useEffect(() => {
  //   apiClient.getMyLikeabilityData(memberNo).then((data) => {
  //     setMyLikeabilityData(data);
  //   });
  // });
  const [myLikeabilityData, setMyLikeabilityData] = React.useState([
    {
      memberNo: sessionStorage.getItem("loginMember"),
      nickname: props.nickname,
      exp: 1222,
      rank: 78,
    },
  ]);
  //이 member에 대한 나의 호감도 exp
  const [myLikeabilityExp, setMyLikeabilityExp] = React.useState(1222);
  //이 member에 대한 나의 호감도 순위
  const [myLikeabilityRank, setMyLikeabilityRank] = React.useState(78);

  // 나와의 호감도/ 이유저의 호감도 보기 노출여부 설정
  React.useEffect(() => {
    if (!performer) {
      setLikeabilityWithMember(false);
    }
  }, [performer]);

  // 나와의 호감도/ 이유저의 호감도 보기 버튼클릭
  const changeLikeability = () => {
    setLikeabilityWithMember((current) => !current);
  };

  return (
    <Card
      sx={{ width: props.width }}
      style={{ border: "5px solid #FFCF24", backgroundColor: "#FFFFFF" }}
    >
      <CardContent>
        {likeabilityWithMember ? (
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
            <Grid item xs={12} style={{ position: "relative" }}>
              <LikeabilityRank
                nickname={props.nickname}
                likeabilityWithMember={likeabilityWithMember}
                likeabilityData={myLikeabilityData}
                style={{ position: "absolute", top: "50%" }}
              ></LikeabilityRank>
            </Grid>
          </Grid>
        ) : (
          <Grid
            container
            spacing={2}
            alignItems="center"
            style={{ textAlign: "center" }}
          >
            <Grid item xs={6}>
              <div style={{ position: "relative", textAlign: "left" }}>
                <div>{props.nickname} 님의 관심사</div>
              </div>
            </Grid>
            <Grid item xs={6}>
              {myPage || !performer ? (
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
              <InterestPolygon
                nickname={props.nickname}
                data={interestCategory}
              ></InterestPolygon>
            </Grid>
            <Grid item xs={6}>
              <LikeabilityRank
                nickname={props.nickname}
                likeabilityWithMember={likeabilityWithMember}
                likeabilityData={performerLikeabilityData}
                width={props.width}
              ></LikeabilityRank>
            </Grid>
            <Grid item xs={6}></Grid>
            <Grid item xs={6}>
              <div style={{ position: "relative", bottom: "0%" }}>
                {props.nickname} 님의 호감도 순위
              </div>
            </Grid>
          </Grid>
        )}
      </CardContent>
    </Card>
  );
}
