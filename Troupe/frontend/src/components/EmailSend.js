import React from "react";
import { useParams } from "react-router-dom";
import Button from "@mui/material/Button";
import apiClient from "../apiClient";
import Email from "../img/email.png";
import Email2 from "../img/email2.png";
import Theme from "./Theme";
import { ThemeProvider } from "@mui/material/styles";
import { Grid } from "@mui/material";
export default function EmailSend() {
  const { token } = useParams();
  const login = () => {
    if (token) {
      apiClient.confirmEmail(token);
    }
    window.location.href = "/login";
  };

  React.useEffect(() => {
    if (token) {
      apiClient.confirmEmail(token);
    }
  }, [token]);

  return (
    <ThemeProvider theme={Theme}>
      <Grid
        style={{
          textAlign: "center",
          margin: "100px 0px",
          paddingBottom: "500px",
        }}
      >
        {token ? (
          <img src={Email2} width="800px"></img>
        ) : (
          <img src={Email} width="800px"></img>
        )}
        <Grid mt={-20}>
          <Button
            onClick={login}
            variant="contained"
            color="yellow"
            style={{
              fontSize: "20px",
              width: "50%",
              fontFamily: "SBAggroB",
              width: "300px",
            }}
          >
            로그인하러 가기
          </Button>
        </Grid>
      </Grid>
    </ThemeProvider>
  );
}
