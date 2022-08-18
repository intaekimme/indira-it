import React from "react";
import apiClient from "../apiClient";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import Stage from "../img/stage.jpg";
import Avatar from "./Avatar";
export default function LikeabilityBar(props) {
  //호감도 데이터
  const [likeabilityData, setLikeabilityData] = React.useState({});
  //호감도 경험치
  const [exp, setExp] = React.useState(0);
  //닉네임 경험치
  const [nickname, setNickname] = React.useState("");
  React.useEffect(() => {
    if (props.likeabilityData) {
      setLikeabilityData(props.likeabilityData);
      setExp(props.likeabilityData.likabilityResponse.exp);
      setNickname(props.likeabilityData.memberInfoResponse.nickname);

      
      // console.log(props.likeabilityData);
      // console.log(props.likeabilityData.avatarResponse);
      // console.log(props.likeabilityData.memberInfoResponse);

      // console.log(props.likeabilityData.likabilityResponse.exp);
      // console.log(props.likeabilityData.memberInfoResponse.nickname);
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
              width: `${90 * (exp % 100) * 0.01}%`,
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
            {nickname} 님
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
              {parseInt(exp / 100)}
            </span>
          </div>
        </Grid>
        <Grid item xs={12}></Grid>
        <Grid item xs={12}></Grid>
      </Grid>
    </div>
  );
}
