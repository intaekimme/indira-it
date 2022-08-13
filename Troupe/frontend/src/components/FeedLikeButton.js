import * as React from "react";
import apiClient from "../apiClient";
import Favorite from "@mui/icons-material/Favorite";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";
import Button from "@mui/material/Button";
import { Grid } from "@mui/material";
export default function FeedLikeButton(props) {
  const [isLike, setIsLike] = React.useState(false);
  const [feedNo, setFeedNo] = React.useState(0);
  const [likeCount, setLikeCount] = React.useState(0);
  React.useEffect(() => {
    if (isLike.response) return;
    setFeedNo(props.feedNo);

    if (props.feedNo) {
      if (sessionStorage.getItem("loginCheck")) {
        apiClient.feedLikeCheck(props.feedNo).then((data) => {
          // console.log(data);
          setIsLike(data);
        });
      }
      apiClient.getFeedTotalLike(props.feedNo).then((data) => {
        setLikeCount(data);
      });
    }
  }, [props.feedNo, props.change, isLike]);

  const LikeClick = () => {
    if (!sessionStorage.getItem("loginCheck")) {
      alert("로그인이 필요한 서비스입니다.");
      window.location.href = "/login";
    } else {
      const currentLike = isLike;
      setIsLike(!isLike);

      apiClient
        .feedLike(feedNo)
        .then((data) => {
          setLikeCount(data);
        })
        .catch(() => {
          setIsLike(currentLike);
        });
    }
  };

  return (
    <Button
      size="small"
      onClick={LikeClick}
      style={{
        color: "black",
        justifyContent: "flex-start",
        margin: "0px",
        padding: "0px",
        fontFamily: "116watermelon",
      }}
    >
      {isLike ? (
        <Grid container item xs={12}>
          <Grid item xs={1}>
            <Favorite color="error"></Favorite>
          </Grid>
          <Grid item xs={11} paddingTop="3px">
            <div style={{ display: "inline-block", paddingBottom: "10px" }}>
              {likeCount}
            </div>
          </Grid>
        </Grid>
      ) : (
        <Grid container item xs={12}>
          <Grid item xs={1}>
            <FavoriteBorderIcon></FavoriteBorderIcon>
          </Grid>
          <Grid item xs={11} paddingTop="3px">
            <div style={{ display: "inline-block", paddingBottom: "10px" }}>
              {likeCount}
            </div>
          </Grid>
        </Grid>
      )}
    </Button>
  );
}
