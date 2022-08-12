import React from "react";
import { useParams } from "react-router-dom";
import apiClient from "../apiClient";

import Grid from "@mui/material/Grid";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";

import Stage from "../img/stage.jpg";
import PerfList from "./PerfList";
import FeedList from "./FeedList";
import ProfileMemberInfo from "./ProfileMemberInfo";
import ProfileAnalyze from "./ProfileAnalyze";
import ProfileTabs from "./ProfileTabs";
import Avatar from "./Avatar";

export default function ProfileStage() {
	//memberNo
  const { memberNo } = useParams();
  //img 가로세로
  const imgWidth = 75;
  const imgHeight = 100;

	//이 member의 호감도 top100
  const [performerTop100, setPerformerTop100] = React.useState([]);
	//이 member의 호감도 top3
  const [performerTop3, setPerformerTop3] = React.useState([]);
  React.useEffect(() => {
    if (!memberNo) {
      return;
    }
    apiClient.getPerformerTop100({ profileMemberNo: memberNo }).then((data) => {
      let top100Fans = [...data.top100Fans, ...data.top100Fans, ...data.top100Fans,
        ...data.top100Fans, ...data.top100Fans, ...data.top100Fans,
        ...data.top100Fans, ...data.top100Fans, ...data.top100Fans,
        ...data.top100Fans, ...data.top100Fans, ...data.top100Fans,
        ...data.top100Fans, ...data.top100Fans, ...data.top100Fans,
        ...data.top100Fans, ...data.top100Fans, ...data.top100Fans,
        ...data.top100Fans, ...data.top100Fans, ...data.top100Fans,
        ...data.top100Fans, ...data.top100Fans, ...data.top100Fans,
        ...data.top100Fans, ...data.top100Fans, ...data.top100Fans,
        ...data.top100Fans, ...data.top100Fans, ...data.top100Fans,
        ...data.top100Fans, ...data.top100Fans, data.top100Fans[0], data.top100Fans[0], data.top100Fans[0]];
      // const top100Fans = [...data.top100Fans];
      top100Fans = top100Fans.splice(0, 98);
      const length = top100Fans.length;
      let top3Fans = [];
      if (length>3) {
        top3Fans.push(top100Fans[0]);
        top3Fans.push(top100Fans[1]);
        top3Fans.push(top100Fans[2]);
        top100Fans.splice(0, 3);
        if (length < 99) {
          let tempFans = [];
          for (let i = 0; i < 99 - length;i++){
            tempFans.push(true);
          }
          tempFans.push(...top100Fans.reverse());
          setPerformerTop100(tempFans);
        }
      }
      setPerformerTop3(top3Fans);
      
      // setPerformerTop100([true, ...top100Fans, ...top100Fans, ...top100Fans, ...top100Fans]);
    });
  }, [memberNo]);
  return (
    <Grid item container alignItems="flex-end">
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
        }}
      />
      <Grid alignItems="flex-end" container spacing={0.5} style={{ paddingBottom: 50 }}>
        {performerTop100.map((fan, index) => (
          <Grid key={`performerTop100_${index}`}
            container item xs={1} style={{ position: "relative", padding: 0 }}
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
      <Grid justifyContent="flex-end" alignItems="flex-end"
        container spacing={0.5} style={{ height: `${imgHeight * 2}` }}
      >
        {performerTop3.map((fan, index) => (
          <Grid key={`performerTop3_${index}`}
            item xs={2} style={{ position: "relative", padding: 0 }}
          >
            <Avatar
              avatarResponse={fan.avatarResponse}
              imgWidth={imgWidth * 2}
              imgHeight={imgHeight * 2}
              divWidth={imgWidth * 2}
              divHeight={imgHeight * 2}
              left="-30%"
            />
          </Grid>
        ))}
      </Grid>
    </Grid>
  );
}