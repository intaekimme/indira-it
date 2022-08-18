import React from "react";
import apiClient from "../apiClient";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import Stage from "../img/stage.jpg";
import Avatar from "./Avatar";
export default function LikeabilityBar(props) {
  //멤버 데이터
  const [memberInfoResponse, setMemberInfoResponse] = React.useState({});
  //호감도 데이터
  const [likeabilityData, setLikeabilityData] = React.useState({});
  //호감도 레벨
  const [level, setLevel] = React.useState(0);
  //호감도 경험치
  const [exp, setExp] = React.useState(0);
  //레벨업 필요경험치
  const [requiredExpNext, setRequiredExpNext] = React.useState(0);
  //현재레벨 최소경험치
  const [requiredExpNow, setRequiredExpNow] = React.useState(0);
  React.useEffect(() => {
    if (props.likeabilityData) {
      setMemberInfoResponse(props.likeabilityData.memberInfoResponse);
      setLikeabilityData(props.likeabilityData);
      setLevel(props.likeabilityData.likabilityResponse.level);
      setExp(props.likeabilityData.likabilityResponse.exp);
      setRequiredExpNext(props.likeabilityData.likabilityResponse.requiredExpNext);
      setRequiredExpNow(props.likeabilityData.likabilityResponse.requiredExpNow);

      console.log(props.likeabilityData);
      console.log(props.likeabilityData.avatarResponse);
      console.log(props.likeabilityData.memberInfoResponse);
      console.log(props.likeabilityData.likabilityResponse.exp);
    }
  }, [props.likeabilityData]);
  // const likeabilityData = props.likeabilityData;
  // // 호감도 경험치
  // const exp = likeabilityData.exp;
  
  // 아바타 url
  // const [avatarUrl, setAvatarUrl] = React.useState([]);
  // React.useEffect(() => {
  // 	apiClient.getavatar(likeabilityData.memberNo).then((data) => {
  // 		setAvatarUrl(data);
  // 	})
  // }, [likeabilityData.memberNo]);
  const avatarUrl = [Stage];
  const imgWidth = 75;
  const imgHeight = 100;
  const barHeight = 30;

  const avatarClick = ()=>{
    window.location.href = `/profile/${memberInfoResponse.memberNo}`;
  }

  return (
    <div>
      <Grid
        container
        spacing={3}
        alignItems="center"
        style={{
          padding: "20px",
        }}
      >
        <Grid item xs={2} style={{ position: "relative" }}>
          {/* {avatarUrl.map((url, index) => (
            <img
              key={`${url}${index}`}
              src={url}
              alt={url}
              style={{
                width: `${imgWidth}px`,
                height: `${imgHeight}px`,
              }}
            ></img>
          ))} */}

          {
            <Avatar
              avatarResponse={likeabilityData.avatarResponse}
              left="-30%"
              top="-50%"
              button={true}
              onClick={avatarClick}
            />
          }
        </Grid>
        <Grid item xs={8} style={{ position: "relative" }}>
          <Box
            style={{
              position: "absolute",
              backgroundColor: "#AAAAAA",
              borderRadius: "50px",
              top: `50%`,
              width: "90%",
              height: `${barHeight}px`,
            }}
          ></Box>
          <Box
            style={{
              position: "absolute",
              backgroundColor: "#FF88DD",
              borderRadius: "50px",
              top: `50%`,
              width: `${90 * ((exp-requiredExpNow)*100/(requiredExpNext-requiredExpNow)) * 0.01}%`,
              height: `${barHeight}px`,
            }}
          ></Box>
          <div
          style={{
            position: "absolute",
            top: `${barHeight*2}px`,
            width: "90%",
            height: `${barHeight}px`,
            }}>
            {memberInfoResponse.nickname} 님
          </div>
        </Grid>
        <Grid item xs={2}>
          <div>
            Lv.{" "}
            <span
              style={{
                color: "#fda085",
              }}
            >
              {parseInt(level)}
            </span>
          </div>
        </Grid>
        <Grid item xs={12}></Grid>
        <Grid item xs={12}></Grid>
      </Grid>
    </div>
  );
}
