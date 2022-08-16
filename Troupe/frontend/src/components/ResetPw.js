import React from "react";
import { useParams } from "react-router-dom";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import Theme from "./Theme";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import apiClient from "../apiClient";

export default function ResetPw() {
  const { token } = useParams();
  const [resetPossible, setResetPossible] = React.useState(false);
  //password 길이확인
  const [pwLength, setPwLength] = React.useState(true);
  //password 일치확인
  const [pwSame, setPwSame] = React.useState(true);

  React.useEffect(() => {
    console.log(token);
    if (token) {
      setResetPossible(true);
    } else {
      setResetPossible(false);
    }
  }, [token]);

  const handleSubmit = (event) => {
    event.preventDefault();

    const formData = new FormData(event.currentTarget);
    const email = formData.get("email");
    console.log(email);
    if (email) {
      apiClient.requestPassword(email);
    } else {
      const password = formData.get("password");
      const passwordCheck = formData.get("passwordCheck");
      //password 8~20자
      const pwLength = password.length;
      const pwCheck = pwLength >= 8 && pwLength <= 20;
      setPwLength(pwCheck);
      //paswword 일치확인
      const pwSame = password === passwordCheck;
      setPwSame(pwSame);
      console.log(pwCheck && pwSame);
      if (pwCheck && pwSame) {
        apiClient.resetPassword(token, password);
      }
    }
  };
  return (
    <ThemeProvider theme={Theme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            fontFamily: "SBAggroB",
            paddingBottom: "1000px",
          }}
        >
          <Box
            component="form"
            onSubmit={handleSubmit}
            noValidate
            sx={{ mt: 1 }}
          >
            {resetPossible ? (
              <div style={{ width: "100%" }}>
                <TextField
                  margin="normal"
                  required
                  fullWidth
                  name="password"
                  label="새 비밀번호"
                  type="password"
                  id="password"
                  autoComplete="new-password"
                  autoFocus
                  style={{ backgroudColor: "white" }}
                  sx={{
                    "& .MuiOutlinedInput-root.Mui-focused": {
                      "& > fieldset": {
                        borderColor: "#66cc66",
                      },
                    },
                  }}
                />
                {pwLength ? (
                  <div></div>
                ) : (
                  <Grid>
                    <div style={{ color: "red" }}>
                      비밀번호는 8~20자의 길이로 설정해주세요.
                    </div>
                  </Grid>
                )}
                <TextField
                  margin="normal"
                  required
                  fullWidth
                  name="passwordCheck"
                  label="비밀번호 확인"
                  type="password"
                  id="passwordCheck"
                  autoComplete="new-password"
                  style={{ backgroudColor: "white" }}
                  sx={{
                    "& .MuiOutlinedInput-root.Mui-focused": {
                      "& > fieldset": {
                        borderColor: "#66cc66",
                      },
                    },
                  }}
                />
                {pwSame ? (
                  <div></div>
                ) : (
                  <Grid>
                    <div style={{ color: "red" }}>
                      비밀번호가 일치하지 않습니다.
                    </div>
                  </Grid>
                )}
              </div>
            ) : (
              <TextField
                margin="normal"
                required
                fullWidth
                id="email"
                label="Email Address"
                name="email"
                autoComplete="email"
                autoFocus
                style={{ backgroundColor: "white" }}
                sx={{
                  "& .MuiOutlinedInput-root.Mui-focused": {
                    "& > fieldset": {
                      borderColor: "#66cc66",
                    },
                  },
                }}
              />
            )}
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 2, mb: 1 }}
              color="yellow"
              style={{ fontFamily: "SBAggroB" }}
            >
              비밀번호 초기화
            </Button>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
