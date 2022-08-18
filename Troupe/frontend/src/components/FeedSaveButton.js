import * as React from "react";
import apiClient from "../apiClient";
import Button from "@mui/material/Button";
import { Grid } from "@mui/material";
import { ThemeProvider } from "@mui/material/styles";
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
      sessionStorage.setItem("currentHref", window.location.href);
      window.location.href = "/login";
    } else {
      const currentSave = save;
      // setSave(!save);
      apiClient
        .feedSave(feedNo)
        .then((data) => {
          setSave(data);
        })
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
            <Grid item xs={11}>
              <TurnedInIcon color="yellow"></TurnedInIcon>
            </Grid>
            <Grid item xs={1} paddingTop="3px"></Grid>
          </Grid>
        ) : (
          <Grid container item xs={12}>
            <Grid item xs={11}>
              <TurnedInNotIcon></TurnedInNotIcon>
            </Grid>
            <Grid item xs={1} paddingTop="3px"></Grid>
          </Grid>
        )}
      </Button>
    </ThemeProvider>
  );
}
