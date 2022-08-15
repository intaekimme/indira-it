import React from "react";
import { useParams } from "react-router-dom";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";

import apiClient from "../apiClient";
import LoginPopup from "./LoginPopup";
import FollowButton from "./FollowButton";

import NumberFilter from "../function/NumberFilter.js";
import styledButton from "../css/button.module.css";

export default function ProfileMemberInfo(props) {
  //memberNo
  const { memberNo } = useParams();

  //memberInfo
  const [memberInfo, setMemberInfo] = React.useState("");

  //프로필 이미지 초기화
  const [profileImageUrl, setProfileImageUrl] = React.useState(
    props.memberInfo.profileImageUrl,
  );
  //프로필 <img> element
  const [profileImage, setProfileImage] = React.useState(<div></div>);
  //프로필 이미지 update
  React.useEffect(() => {
    setMemberInfo(props.memberInfo);
    if (
      props.memberInfo.profileImageUrl &&
      props.memberInfo.profileImageUrl !== "" &&
      props.memberInfo.profileImageUrl !== null
    ) {
      //프로필이미지 세팅
      setProfileImage(
        <img
          src={props.memberInfo.profileImageUrl}
          alt={props.memberInfo.profileImageUrl}
          style={{
            width: "200px",
            height: "200px",
            objectFit: "cover",
            borderRadius: "30%",
            border: "8px #FFCF24 solid",
            boxShadow: "3px 3px 5px #000",
          }}
        />,
      );
    } else {
      //기본이미지 세팅
      setProfileImage(
        <AccountCircleIcon
          fontSize="large"
          sx={{ fontSize: "200px" }}
        ></AccountCircleIcon>,
      );
    }
  }, [props.memberInfo]);

  //이 member의 팔로워 수 초기화
  const [followerCount, setFollowerCount] = React.useState("");
  //이 member의 관심태그 초기화
  const [interestTag, setInterestTag] = React.useState("");
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
  }, [memberNo]);
  // const followerCount = 123123123;

  // 자신의 유저페이지인지 판단
  const myPage = props.myPage;

  //프로필 수정 버튼 클릭
  const modifyProfile = () => {
    sessionStorage.setItem("memberData", {
      memberInfo: memberInfo,
    });
    window.location.href = `/profile/${memberNo}/modify`;
  };

  return (
    <Card
      sx={{ width: props.width }}
      style={{
        border: "5px solid white",
        backgroundColor: "#FFCF24",
        fontFamily: "SBAggroB",
        position: "relative",
        overflow: "visible",
        marginBottom: "10px",
        borderRadius: "5px",
        boxShadow:
          "0 10px 35px rgba(0, 0, 0, 0.05), 0 6px 6px rgba(0, 0, 0, 0.6)",
        // height: "240px",
      }}
    >
      <CardContent
        style={{
          position: "relative",
          left: "10px",
        }}
      >
        <div style={{ position: "relative", margin: "20px" }}>
          {/* <Button
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
          </Button> */}
          <div>{profileImage}</div>
          <Grid
            container
            item
            spacing={0.5}
            justifyContent="center"
            alignItems="flex-end"
          >
            <Grid
              item
              xs={4}
              justifyContent="center"
              alignItems="flex-end"
            ></Grid>
            <Grid
              item
              xs={4}
              justifyContent="center"
              alignItems="flex-end"
            >
              <div
                style={{ padding: "20px", marginLeft: "20px", color: "white" }}
              >
                <span
                  style={{
                    textShadow: "1px 1px 1px #000",
                  }}
                >
                  {memberInfo.nickname}
                </span>
              </div>
            </Grid>
            <Grid
              item
              xs={4}
              justifyContent="center"
              alignItems="flex-end"
            >
              <div
                style={{
                  float: "right",
                  // width: "200px",
                  // position: "absolute",
                  // right: "0px",
                  // bottom: "0px",
                  textAlign: "right",
                }}
              >
                <Button
                  style={{
                    fontFamily: "SBAggroB",
                    color: "black",
                    marginTop: "17px",
                    marginRight: "-7px",
                  }}
                >
                  follower : {NumberFilter(followerCount)}
                </Button>
              </div>
            </Grid>
          </Grid>
        </div>

        <Typography
          variant="body2"
          color="text.secondary"
          style={{
            paddingBottom: "20px",
            margin: "20px",
            wordBreak: "break-all",
          }}
        >
          {memberInfo.description}
        </Typography>
        <div>
          {myPage ? (
            <Button
              style={{
                width: "100px",
                fontSize: "12px",
                borderRadius: "15px",
                margin: "10px",
                marginTop: "20px",
                border: "3px solid white",
              }}
              variant="contained"
              color="green"
              onClick={modifyProfile}
              className={styledButton.btn2}
            >
              프로필수정
            </Button>
          ) : (
            <FollowButton memberNo={memberNo} />
          )}
        </div>
      </CardContent>
    </Card>
  );
}
