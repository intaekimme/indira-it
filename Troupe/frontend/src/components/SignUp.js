import apiClient from "../apiClient";
import React from "react";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import Link from "@mui/material/Link";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";

const theme = createTheme();

export default function SignUp() {
  //frontend image update
  const [imgUrl, setImgUrl] = React.useState("");
  //nickname update
  const [nickname, setNickname] = React.useState("");
  //email update
  const [email, setEmail] = React.useState("");
  //nickname 길이확인
  const [nicknameLength, setNicknameLength] = React.useState(true);
  //password 길이확인
  const [pwLength, setPwLength] = React.useState(true);
  //password 일치확인
  const [pwSame, setPwSame] = React.useState(true);

  //image 업로드
  const changeImage = (e) => {
    setImgUrl(URL.createObjectURL(e.target.files[0]));
  };

  //nickname Change
  const changeNickname = (e) => {
    setNickname(e.target.value);
  };

  //email Change
  const changeEmail = (e) => {
    setEmail(e.target.value);
  };

  //중복체크
  const sameCheck = (string) => {
    if (string === "email") {
      apiClient.existEmail({ email: email });
    } else if (string === "nickname") {
      apiClient.existNickname({ nickname: nickname });
    }
  };

  //입력된 값이 올바른지 확인
  const checkValue = (data) => {
    //닉네임 2~20자
    const nicknameLength = data.nickname.length;
    const nicknameCheck = nicknameLength >= 2 && nicknameLength <= 20;
    setNicknameLength(nicknameCheck);
    //password 8~20자
    const pwLength = data.password.length;
    const pwCheck = pwLength >= 8 && pwLength <= 20;
    setPwLength(pwCheck);
    //paswword 일치확인
    const pwSame = data.password === data.passwordCheck;
    setPwSame(pwSame);

    return nicknameCheck && pwCheck && pwSame;
  };

  //회원가입버튼 클릭
  const handleSubmit = (event) => {
    event.preventDefault();
    const formData = new FormData(event.currentTarget);
    console.log(event.currentTarget);
    console.log(formData);

    const data = {
      profileImage: formData.get("imgUpload"),
      nickname: formData.get("nickname"),
      email: formData.get("email"),
      password: formData.get("password"),
      passwordCheck: formData.get("passwordCheck"),
      profileMessage: formData.get("profileMessage"),
    };
    console.log(data);

    //입력된 값이 올바른지 확인
    if (!checkValue(data)) {
      return;
    }
    apiClient.signup(data);
  };

  return (
    <ThemeProvider theme={theme}>
      <Container maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <form
            onSubmit={handleSubmit}
            encType="multipart/form-data"
            style={{ textAlign: "center" }}
          >
            <Grid container spacing={2}>
              <Grid item xs={9}>
                {imgUrl === "" ? (
                  <AccountCircleIcon
                    fontSize="large"
                    sx={{ fontSize: "100px" }}
                  ></AccountCircleIcon>
                ) : (
                  <img
                    src={imgUrl}
                    alt=""
                    style={{
                      objectFit: "cover",
                      width: "100px",
                      height: "100px",
                      borderRadius: "40%",
                    }}
                  ></img>
                )}
              </Grid>
              <Grid item xs={3} style={{ position: "relative" }}>
                <Button
                  style={{
                    position: "absolute",
                    width: "80px",
                    height: "30px",
                    bottom: "0px",
                    right: "0px",
                    backgroundColor: "#CCCCCC",
                    color: "black",
                  }}
                >
                  찾아보기
                </Button>
                <input
                  style={{
                    position: "absolute",
                    width: "80px",
                    height: "30px",
                    bottom: "0px",
                    right: "0px",
                    opacity: "0%",
                  }}
                  id="imgUpload"
                  className="imgUpload"
                  name="imgUpload"
                  type="file"
                  accept="image/*"
                  onChange={changeImage}
                />
              </Grid>
              <Grid item xs={9}>
                <TextField
                  autoComplete="given-name"
                  name="nickname"
                  required
                  fullWidth
                  id="nickname"
                  label="닉네임"
                  autoFocus
                  onChange={changeNickname}
                />
              </Grid>
              <Grid item xs={3} style={{ position: "relative" }}>
                <Button
                  style={{
                    position: "absolute",
                    width: "80px",
                    height: "30px",
                    bottom: "0px",
                    right: "0px",
                    backgroundColor: "#CCCCCC",
                    color: "black",
                  }}
                  onClick={() => sameCheck("nickname")}
                >
                  중복확인
                </Button>
              </Grid>
              {nicknameLength ? (
                <div></div>
              ) : (
                <Grid item xs={12}>
                  <div style={{ color: "red" }}>
                    닉네임은 2~20자의 길이로 설정해주세요.
                  </div>
                </Grid>
              )}
              <Grid item xs={9}>
                <TextField
                  required
                  fullWidth
                  id="email"
                  label="Email Address"
                  name="email"
                  autoComplete="email"
                  onChange={changeEmail}
                />
              </Grid>
              <Grid item xs={3} style={{ position: "relative" }}>
                <Button
                  style={{
                    position: "absolute",
                    width: "80px",
                    height: "30px",
                    bottom: "0px",
                    right: "0px",
                    backgroundColor: "#CCCCCC",
                    color: "black",
                  }}
                  onClick={() => sameCheck("email")}
                >
                  중복확인
                </Button>
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="password"
                  label="비밀번호"
                  type="password"
                  id="password"
                  autoComplete="new-password"
                />
              </Grid>
              {pwLength ? (
                <div></div>
              ) : (
                <Grid item xs={12}>
                  <div style={{ color: "red" }}>
                    비밀번호는 8~20자의 길이로 설정해주세요.
                  </div>
                </Grid>
              )}
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="passwordCheck"
                  label="비밀번호 확인"
                  type="password"
                  id="passwordCheck"
                  autoComplete="new-password"
                />
              </Grid>
              {pwSame ? (
                <div></div>
              ) : (
                <Grid item xs={12}>
                  <div style={{ color: "red" }}>
                    비밀번호가 일치하지 않습니다.
                  </div>
                </Grid>
              )}
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  multiline
                  id="profileMessage"
                  label="소개글 입력"
                  name="profileMessage"
                />
              </Grid>
              <Grid item xs={12}>
                <FormControlLabel
                  required
                  control={
                    <Checkbox value="allowExtraEmails" color="primary" />
                  }
                  label="이메일 전송에 동의합니다."
                />
              </Grid>
            </Grid>
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Sign Up
            </Button>
            <Grid container justifyContent="flex-end">
              <Grid item>
                <Link href="/login" variant="body2">
                  로그인
                </Link>
              </Grid>
            </Grid>
          </form>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
