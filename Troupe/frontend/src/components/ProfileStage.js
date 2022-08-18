import React from "react";
import { useParams } from "react-router-dom";
import apiClient from "../apiClient";

import Grid from "@mui/material/Grid";
import Button from "@mui/material/Button";

import Stage from "../img/stage2.jpg";
import ProfileMemberInfo from "./ProfileMemberInfo";
import ProfileAnalyze from "./ProfileAnalyze";
import ProfileTabs from "./ProfileTabs";
import Avatar from "./Avatar";

export default function ProfileStage(props) {
  //memberNo
  const { memberNo } = useParams();
  //img 가로세로
  const imgWidth = 75;
  const imgHeight = 100;
  //memberAvatar 크기배율
  const ratioCurrent = 6;
  //top3 크기배율
  const ratioTop3 = 3;
  

  //CurrentAvatar top 이동
  const currentAvatarTop = "10%";
  //CurrentAvatar left 이동
  const [currentAvatarLeft, setCurrentAvatarLeft] = React.useState(props.gridItemxs===6 ? (window.innerWidth/4)-(imgWidth*(ratioCurrent+1)/2) : (window.innerWidth/2)-(imgWidth*(ratioCurrent+1)/2));
  //top3Avatar top 이동
  const top3AvatarTop = "50%";
  //top3Avatar left 이동
  const [top3AvatarLeft, setTop3AvatarLeft] = React.useState(props.gridItemxs===6 ? (window.innerWidth/2)-(imgWidth*(ratioTop3+1)) : (window.innerWidth)-(imgWidth*(ratioTop3+1)));
  const handleLeft = () => {
    const size = window.innerWidth;
    if (size < 1000) {
      setCurrentAvatarLeft(size/2-imgWidth*(ratioCurrent+1)/2);
      setTop3AvatarLeft(size-imgWidth*(ratioTop3+1));
      console.log(size/2-imgWidth*(ratioCurrent+1)/2);
    } else {
      setCurrentAvatarLeft(size/4-imgWidth*(ratioCurrent+1)/2);
      setTop3AvatarLeft(size/2-imgWidth*(ratioTop3+1));
      console.log(size/4-imgWidth*(ratioCurrent+1)/2);
    }
  };
  //화면분할 update
  React.useEffect(() => {
    window.addEventListener("resize", handleLeft);
  }, [window.innerWidth]);

  //이 member의 호감도 top100
  const [performerTop100, setPerformerTop100] = React.useState([]);
  //이 member의 호감도 top3
  const [performerTop3, setPerformerTop3] = React.useState([]);
  React.useEffect(() => {
    if (!memberNo) {
      return;
    }
    apiClient.getPerformerTop100({ profileMemberNo: memberNo }).then((data) => {
      let top100Fans = [...data.top100Fans];
      // const top100Fans = [...data.top100Fans];
      top100Fans = top100Fans.splice(0, 98);
      const length = top100Fans.length;
      let top3Fans = [];
      if (length >= 3) {
        top3Fans.push(top100Fans[0]);
        top3Fans.push(top100Fans[1]);
        top3Fans.push(top100Fans[2]);
        top100Fans.splice(0, 3);
        console.log(top3Fans);
        if (length < 99) {
          let tempFans = [];
          for (let i = 0; i < 99 - length;i++){
            tempFans.push(true);
          }
          // tempFans.push(...top100Fans.reverse());
          console.log(tempFans);
          setPerformerTop100(tempFans);
        }
      }
      else{
        top3Fans.push(...top100Fans);
        for (let i = 0; i < 3 - length;i++){
          top3Fans.push(true);
        }
        console.log(top3Fans);
        let tempFans = [];
        for (let i = 0; i < 99; i++) {
          tempFans.push(true);
        }
        console.log(tempFans);
        setPerformerTop100(tempFans);
      }
      setPerformerTop3(top3Fans);
    });
  }, [memberNo]);
  return (
    <Grid
      style={{ width: "100%", height: "100%", padding:20}}
      item
      container
      alignItems="flex-end"
    >
      <img
        src={Stage}
        alt={Stage}
        style={{
          position: "absolute",
          objectFit: "cover",
          top: "0%",
          left: "0%",
          width: `${100}%`,
          height: `${100}%`,
          border: "5px solid white",
          borderRadius: "5px",
          boxShadow:
            "0 10px 35px rgba(0, 0, 0, 0.05), 0 6px 6px rgba(0, 0, 0, 0.6)",
          // zIndex: "1",
        }}
      />
      <Button
        onClick={props.modifyAvatar}
        style={{
          display: "flex",
          left: currentAvatarLeft,
          top: currentAvatarTop,
          zIndex: 5,
        }}
      >
        <Avatar
          avatarResponse={props.avatar}
          imgWidth={imgWidth * ratioCurrent}
          imgHeight={imgHeight * ratioCurrent}
          divWidth={imgWidth * ratioCurrent}
          divHeight="100%"

          // style={{
          //   position: "absolute",
          //   display: "flex",
          //   justifyContent: "center",
          //   alignItems: "center",
          // }}
        />
      </Button>
      {/* <Button
        style={{
          display: "absolute",
          left: top3AvatarLeft,
          top: top3AvatarTop,
          zIndex: 5,
        }}
      >
        <Avatar
          avatarResponse={props.avatar}
          imgWidth={imgWidth * ratioTop3}
          imgHeight={imgHeight * ratioTop3}
          divWidth={imgWidth * ratioTop3}
          divHeight="100%"
        />
      </Button> */}
      <Grid
        alignItems="flex-end"
        container
        spacing={0.5}
        style={{ paddingBottom: 50 }}
      >
        {performerTop100.map((fan, index) => (
          <Grid
            key={`performerTop100_${index}`}
            container
            item
            xs={1}
            style={{ position: "relative", padding: 0 }}
          >
            <Avatar
              avatarResponse={fan.avatarResponse}
              imgWidth={imgWidth}
              imgHeight={imgHeight}
              left={`${parseInt(index / 12) % 2 == 0 ? "-50%" : 0}`}
            />
          </Grid>
        ))}
      </Grid>
      <Grid
        justifyContent="flex-end"
        alignItems="flex-end"
        container
        spacing={0.5}
        style={{ height: `${imgHeight * 2}` }}
      >
        {performerTop3.map((fan, index) => (
          <Grid
            key={`performerTop3_${index}`}
            item
            xs={2}
            style={{ position: "relative", padding: 0, zIndex: `${10-index}`, }}
          >
            <Avatar
              avatarResponse={fan.avatarResponse}
              imgWidth={imgWidth * ratioTop3}
              imgHeight={imgHeight * ratioTop3}
              divWidth={imgWidth * ratioTop3}
              divHeight={imgHeight * ratioTop3}
              left="-30%"
            />
          </Grid>
        ))}
      </Grid>
    </Grid>
  );
}