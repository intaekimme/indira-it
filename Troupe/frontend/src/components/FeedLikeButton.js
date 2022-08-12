import * as React from "react";
import apiClient from "../apiClient";
import Favorite from "@mui/icons-material/Favorite";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";
import Button from "@mui/material/Button";
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
      }}
    >
      {isLike ? (
        <div>
          <Favorite color="error"></Favorite>
          <span style={{ marginBottom: "3px" }}>{likeCount}</span>
        </div>
      ) : (
        <div>
          <FavoriteBorderIcon></FavoriteBorderIcon>
          {likeCount}
        </div>
      )}
    </Button>
  );
}
