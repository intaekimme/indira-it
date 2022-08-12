import * as React from "react";
import apiClient from "../apiClient";
import Button from "@mui/material/Button";
import { Grid } from "@mui/material";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import TurnedInIcon from "@mui/icons-material/TurnedIn";
import TurnedInNotIcon from "@mui/icons-material/TurnedInNot";
import Theme from "./Theme";
export default function FeedSaveButton(props) {
  const [save, setSave] = React.useState(false);
  const [feedNo, setFeedNo] = React.useState(0);

  React.useEffect(() => {
    if (save.response) return;
    setFeedNo(props.feedNo);
    if (sessionStorage.getItem("loginCheck") && props.feedNo) {
      apiClient.feedSaveCheck(props.feedNo).then((data) => {
        // console.log(data);
        setSave(data);
      });
    }
  }, [props.feedNo, props.change, save]);

  const SaveClick = () => {
    if (!sessionStorage.getItem("loginCheck")) {
      alert("로그인이 필요한 서비스입니다.");
      window.location.href = "/login";
    } else {
      const currentSave = save;
      setSave(!save);
      apiClient
        .feedSave(feedNo)
        .then((data) => {})
        .catch(() => {
          setSave(currentSave);
        });
    }
  };
  return (
    <ThemeProvider theme={Theme}>
      <Button
        size="small"
        onClick={SaveClick}
        style={{
          color: "black",
          justifyContent: "flex-end",
          margin: "0px",
          padding: "0px",
        }}
      >
        {save ? (
          <Grid container item xs={12}>
            <Grid item xs={1}>
              <TurnedInIcon color="yellow"></TurnedInIcon>
              <Grid item xs={11} paddingTop="3px"></Grid>
            </Grid>
          </Grid>
        ) : (
          <Grid container item xs={12}>
            <Grid item xs={1}>
              <TurnedInNotIcon></TurnedInNotIcon>
              <Grid item xs={11} paddingTop="3px"></Grid>
            </Grid>
          </Grid>
        )}
      </Button>
    </ThemeProvider>
  );
}
