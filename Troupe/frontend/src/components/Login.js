import apiClient from "../apiClient";
import React from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import Link from "@mui/material/Link";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { ThemeProvider } from "@mui/material/styles";
import { styled } from "@mui/material/styles";
import Paper from "@mui/material/Paper";
import SNSLoginFacebook from "./SNSLoginFacebook";
import Theme from "./Theme";

export default function Login() {
  //로그인버튼 클릭
  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const loginInfo = {
      email: data.get("email"),
      password: data.get("password"),
    };
    // console.log(loginInfo);
    apiClient.login(loginInfo);
  };

  //페이스북 로그인
  const facebookLogin = () => {
    alert("facebook 로그인 창");
  };
  //인스타 로그인
  const instagramLogin = () => {
    alert("instagram 로그인 창");
  };

  const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === "dark" ? "#1A2027" : "#FFF",
    ...theme.typography.body2,
    padding: theme.spacing(5),
    textAlign: "center",
    color: theme.palette.text.secondary,
    margin: "100px 0px",
  }));

  return (
    <ThemeProvider theme={Theme}>
      <div id="back" style={{ fontFamily: "SBAggroB" }}>
        <Container component="main" maxWidth="xs">
          <CssBaseline />

          <Box
            sx={{
              marginTop: 2,
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
              paddingBottom: "300px",
            }}
          >
            <Avatar sx={{ m: 4, bgcolor: "primary.main" }}>
              <LockOutlinedIcon />
            </Avatar>
            <Typography component="h1" variant="h5">
              로그인
            </Typography>
            <Box
              component="form"
              onSubmit={handleSubmit}
              noValidate
              sx={{ mt: 4 }}
              style={{ textAlign: "center" }}
            >
              <TextField
                margin="normal"
                required
                fullWidth
                id="email"
                label="이메일"
                name="email"
                autoComplete="email"
                autoFocus
                style={{ backgroundColor: "white" }}
              />
              <TextField
                margin="normal"
                required
                fullWidth
                name="password"
                label="비밀번호"
                type="password"
                id="password"
                autoComplete="current-password"
                style={{ backgroundColor: "white" }}
              />
              <FormControlLabel
                control={<Checkbox value="remember" color="primary" />}
                label="로그인 유지"
              />
              <Button
                type="submit"
                fullWidth
                variant="contained"
                sx={{ mt: 2, mb: 1 }}
                color="yellow"
                style={{ fontFamily: "SBAggroB" }}
              >
                로그인
              </Button>
              <SNSLoginFacebook />
              {/* <Button
              fullWidth
              variant="contained"
              sx={{ mt: 0, mb: 1 }}
              style = {{backgroundColor: "blue"}}
              onClick = {facebookLogin}
            >
              FaceBook 로그인
            </Button> */}
              <Button
                fullWidth
                variant="contained"
                sx={{ mt: 0, mb: 1 }}
                style={{ backgroundColor: "purple", fontFamily: "SBAggroB" }}
                onClick={instagramLogin}
              >
                Instagram 로그인
              </Button>
              <Grid container justifyContent="space-between">
                <Grid>
                  <Link href="/resetpw" variant="body2">
                    비밀번호 찾기
                  </Link>
                </Grid>
                <Grid>
                  <Link href="/signup" variant="body2">
                    회원가입
                  </Link>
                </Grid>
              </Grid>
            </Box>
          </Box>
        </Container>
      </div>
    </ThemeProvider>
  );
}
