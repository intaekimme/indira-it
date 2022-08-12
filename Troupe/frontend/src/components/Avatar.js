import React, { useEffect } from "react";
import Grid from "@mui/material/Grid";

export default function Avatar(props) {
	const avatarResponse = props.avatarResponse;
  const [imgWidth, setImgWidth] = React.useState(75);
  const [imgHeight, setImgHeight] = React.useState(100);
  const [divWidth, setDivWidth] = React.useState(75);
  const [divHeight, setDivHeight] = React.useState(50);
  const [left, setLeft] = React.useState(0);
  useEffect(() => {
    if (props.imgWidth) {
      setImgWidth(props.imgWidth);
    }
    if (props.imgHeight) {
      setImgHeight(props.imgHeight);
    }
    if (props.divWidth) {
      setDivWidth(props.divWidth);
    }
    if (props.divHeight) {
      setDivHeight(props.divHeight);
    }
    if (props.left) {
      setLeft(props.left);
    }
  }, []);
  return avatarResponse ? (
    <Grid style={{ width: divWidth, height: divHeight, paddingRight: "30px" }}>
      <img
        src={avatarResponse.shapeUrl}
        alt={avatarResponse.shapeUrl}
        style={{
          position: "absolute",
          top: "0%",
          left: left,
          width: `${imgWidth}px`,
          height: `${imgHeight}px`,
        }}
      ></img>
      <img
        src={avatarResponse.clothesUrl}
        alt={avatarResponse.clothesUrl}
        style={{
          position: "absolute",
          top: "0%",
          left: left,
          width: `${imgWidth}px`,
          height: `${imgHeight}px`,
        }}
      ></img>
      <img
        src={avatarResponse.eyeUrl}
        alt={avatarResponse.eyeUrl}
        style={{
          position: "absolute",
          top: "0%",
          left: left,
          width: `${imgWidth}px`,
          height: `${imgHeight}px`,
        }}
      ></img>
      <img
        src={avatarResponse.mouthUrl}
        alt={avatarResponse.mouthUrl}
        style={{
          position: "absolute",
          top: "0%",
          left: left,
          width: `${imgWidth}px`,
          height: `${imgHeight}px`,
        }}
      ></img>
      <img
        src={avatarResponse.noseUrl}
        alt={avatarResponse.noseUrl}
        style={{
          position: "absolute",
          top: "0%",
          left: left,
          width: `${imgWidth}px`,
          height: `${imgHeight}px`,
        }}
      ></img>
      <img
        src={avatarResponse.hairUrl}
        alt={avatarResponse.hairUrl}
        style={{
          position: "absolute",
          top: "0%",
          left: left,
          width: `${imgWidth}px`,
          height: `${imgHeight}px`,
        }}
      ></img>
    </Grid>
  ) : (
      <div style={{ width: divWidth, height: divHeight }}></div>
  );
}