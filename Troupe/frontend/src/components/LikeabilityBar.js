import React from "react";
import apiClient from "../apiClient";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import Stage from "../img/stage.jpg";
import Avatar from "./Avatar";
export default function LikeabilityBar(props) {
  const likeabilityData = props.likeabilityData;
  // 호감도 경험치
  const exp = likeabilityData.exp;
  // 아바타 url
  // const [avatarUrl, setAvatarUrl] = React.useState([]);
  // React.useEffect(() => {
  // 	apiClient.getavatar(likeabilityData.memberNo).then((data) => {
  // 		setAvatarUrl(data);
  // 	})
  // }, [likeabilityData.memberNo]);
  const avatarUrl = [Stage];
  const imgWidth = 30;
  const imgHeight = 50;
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
        <Grid item xs={2} style={{position: "relative"}}>
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
            <Avatar avatarResponse={likeabilityData.avatarResponse} />
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
        </Grid>
        <Grid item xs={2}>
          <div>Lv.{parseInt(exp / 100)}</div>
        </Grid>
      </Grid>
    </div>
  );
}
