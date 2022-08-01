import * as React from "react";
import { styled } from "@mui/material/styles";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";

import apiClient from "../apiClient";
import LoginPopup from "./LoginPopup";
import NumberFilter from "../function/NumberFilter.js";
import styledButton from "../css/button.module.css";

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

export default function UserInfoCard(props) {
  //카드펼치기 핸들러
  // const [expanded, setExpanded] = React.useState(false);

  // const handleExpandClick = () => {
  //   setExpanded(!expanded);
  // };

  // 자신의 유저페이지인지 판단
  // const [mypage, setMypage] = React.useState(window.sessionStorage.getItem('loginUser').userNo === props.userNo);
  const [mypage, setMypage] = React.useState(false);
  // 이 유저를 팔로우 했는지 판단
  const [followThisUser, setFollowThisUser] = React.useState(true);
  // follow/unfollow 버튼클릭
  const followClick = () => {
    const currnetFollow = followThisUser;
    setFollowThisUser((current) => !current);
    const data = {
      follow: !currnetFollow,
      userNo: props.userNo,
      currentUser: 1,
    };
    apiClient.follow(data);
  };
  // React.useEffect(() => {
  //   const data = {
  //     follow: followThisUser,
  //     userNo: props.userNo,
  //     currentUser: 1,
  //   };
  //   apiClient.follow(data);
  // });

  return (
    <Card
      sx={{ width: props.width }}
      style={{ border: "3px solid black", backgroundColor: "#FFCF24" }}
    >
      <CardContent>
        <div style={{ position: "relative", margin: "20px" }}>
          <Button
            style={{
              float: "left",
              width: "100px",
              fontSize: "12px",
              position: "absolute",
              left: "0px",
              bottom: "0px",
              color: "red",
            }}
          >
            신고하기
          </Button>
          <div>{props.profile}</div>
          <div style={{ padding: "20px" }}>{props.nickname}</div>
          <div
            style={{
              float: "right",
              width: "200px",
              position: "absolute",
              right: "0px",
              bottom: "0px",
              textAlign: "right",
            }}
          >
            <Button>{NumberFilter(props.follower)}</Button>
            {mypage ? (
              <Button
                style={{
                  width: "100px",
                  fontSize: "12px",
                  backgroundColor: "#AAAAAA",
                  color: "white",
                  borderRadius: "15px",
                  margin: "10px",
                }}
              >
                프로필수정
              </Button>
            ) : followThisUser ? (
              <Button
                className={styledButton.btn}
                // style={{
                //   width: "100px",
                //   fontSize: "12px",
                //   backgroundColor: "#AAAAAA",
                //   color: "white",
                //   borderRadius: "15px",
                //   margin: "10px",
                // }}
                onClick={followClick}
              >
                -UnFollow
              </Button>
            ) : (
              <Button
              className={styledButton.btn}
                style={{
                  backgroundColor: "#45E3C6",
                  color: "black",
                }}
                onClick={followClick}
              >
                +Follow
              </Button>
            )}
          </div>
        </div>
        <Typography variant="body2" color="text.secondary">
          {props.content}
        </Typography>
      </CardContent>
    </Card>
  );
}
