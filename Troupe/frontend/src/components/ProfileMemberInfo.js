import React from "react";
import { useParams } from "react-router-dom";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";

import apiClient from "../apiClient";
import LoginPopup from "./LoginPopup";
import NumberFilter from "../function/NumberFilter.js";
import styledButton from "../css/button.module.css";

export default function ProfileMemberInfo(props) {
  //memberNo
  const { memberNo } = useParams();

  //memberInfo
  const [memberInfo, setMemberInfo] = React.useState("");

  //프로필 이미지 초기화
  const [profileImageUrl, setProfileImageUrl] = React.useState(
    props.memberInfo.profileImageUrl
  );
  //프로필 이미지 update
  React.useEffect(() => {
    setMemberInfo(props.memberInfo);
    if (
      props.memberInfo.profileImageUrl &&
      props.memberInfo.profileImageUrl !== "" &&
      props.memberInfo.profileImageUrl !== null
    ) {
      //프로필이미지 세팅
      setProfileImageUrl(
        <img
          src={props.memberInfo.profileImageUrl}
          alt={props.memberInfo.profileImageUrl}
          style={{
            width: "200px",
            height: "200px",
            objectFit: "cover",
            borderRadius: "50%",
          }}
        />
      );
    } else {
      //기본이미지 세팅
      setProfileImageUrl(
        <AccountCircleIcon
          fontSize="large"
          sx={{ fontSize: "200px" }}
        ></AccountCircleIcon>
      );
    }
  }, [props.memberInfo]);

  //이 member의 팔로워 수 초기화
  const [followerCount, setFollowerCount] = React.useState("");
  //이 member의 관심태그 초기화
  const [interestTag, setInterestTag] = React.useState("");
  // 이 유저를 팔로우 했는지 판단초기화
  const [isFollowing, setIsFollowing] = React.useState(false);
  //memberNo update 시
  React.useEffect(() => {
    //이 member의 팔로워 수 update
    apiClient.getFollowerCount(memberNo).then((data) => {
      setFollowerCount(data.fanCount);
    });
    //이 member의 관심태그 update
    // apiClient.getInterestTag(memberNo).then((data) => {
    //   setInterestTag(data);
    // });

    // 이 유저를 팔로우 했는지 판단 update
    if(sessionStorage.getItem("loginCheck")){
      apiClient.isFollowing({
        profileMemberNo: memberNo,
        fanMamberNo: sessionStorage.getItem("loginMember"),
      })
      .then((data) => {
        setIsFollowing(data.isFollowing);
      });
    }
  }, [memberNo]);
  // const followerCount = 123123123;

  // 자신의 유저페이지인지 판단
  const myPage = props.myPage;
  // const [isFollowing, setIsFollowing] = React.useState(false);

  // follow/unfollow 버튼클릭
  const followClick = () => {
    if (!sessionStorage.getItem("loginCheck")) {
      window.location.href = "/login";
    } else {
      const currnetFollow = isFollowing;
      setIsFollowing(!isFollowing);
      apiClient.follow({currentFollow: currnetFollow}).then((data) => {
        if (!data) {
          setIsFollowing(currnetFollow);
        }
      });
    }
  };

  //프로필 수정 버튼 클릭
  const modifyProfile = () => {
    sessionStorage.setItem("memberData", {
      memberInfo: memberInfo,
    });
    window.location.href = `/profile/${memberNo}/modify`;
  }

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
          <div>{profileImageUrl}</div>
          <div style={{ padding: "20px" }}>{memberInfo.nickname}</div>
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
            <Button>{NumberFilter(followerCount)}</Button>
            {myPage ? (
              <Button
                style={{
                  width: "100px",
                  fontSize: "12px",
                  backgroundColor: "#AAAAAA",
                  color: "white",
                  borderRadius: "15px",
                  margin: "10px",
                }}
                onClick={ modifyProfile }
              >
                프로필수정
              </Button>
            ) : isFollowing ? (
              <Button className={styledButton.btn} onClick={followClick}>
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
          {memberInfo.description}
        </Typography>
      </CardContent>
    </Card>
  );
}
