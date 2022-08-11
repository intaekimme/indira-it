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

	//이 member의 호감도 data
  const [performerTop100, setPerformerTop100] = React.useState([]);
  React.useEffect(() => {
    apiClient.getPerformerTop100({ profileMemberNo: memberNo }).then((data) => {
      console.log(data);
      setPerformerTop100(data.top100Fans);
    });
  }, [memberNo]);
	return (
		<div style={{position: "relatvie", height:"100%", height:"100%"}}>
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
			{performerTop100.map((fan) => (
				<Avatar avatarResponse={ fan.avatarResponse } />
			))}
    </div>
  );
}