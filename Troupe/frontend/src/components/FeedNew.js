import apiClient from "../apiClient";
import React from "react";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";

const theme = createTheme();

export default function PerfNew() {
  //공연 포스터
  const [imgUrl, setImgUrl] = React.useState([]);
  //공연 제목
  const [perfName, setPerfName] = React.useState("");
  //공연기간
  const [perfDate, setPerfDate] = React.useState("");
  //공연시간
  const [runtime, setRuntime] = React.useState(0);
  //가격
  const [price, setPrice] = React.useState(0);
  //좌석
  const [seat, setSeat] = React.useState("");

  //image 업로드
  const changeImage = (e) => {
    const imageLists = e.target.files;
    let imageUrlLists = [...imgUrl];

    for (let i = 0; i < imageLists.length; i++) {
      const currentImageUrl = URL.createObjectURL(imageLists[i]);
      imageUrlLists.push(currentImageUrl);
    }

    if (imageUrlLists.length > 10) {
      imageUrlLists = imageUrlLists.slice(0, 10);
    }

    setImgUrl(imageUrlLists);
  };

  //공연제목 Change
  const changePerfName = (e) => {
    setPerfName(e.target.value);
  };

  //공연기간 Change
  const changePerfDate = (e) => {
    setPerfDate(e.target.value);
  };

  const addPrice = (e) => {};

  const addSeat = (e) => {};

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
    // if(!checkValue(data)){
    //   return;
    // }
    apiClient.signup(data);
  };

  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="md">
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
            <Grid
              container
              spacing={2}
              sx={{ gridTemplateRows: "repeat(5, 2fr)" }}
            >
              <Grid item xs={11}>
                {imgUrl ? (
                  <div>
                    {imgUrl.map((image, id) => (
                      <img
                        src={image}
                        alt=""
                        style={{ height: "70px", width: "70px" }}
                      ></img>
                    ))}
                  </div>
                ) : (
                  <Box style={{ height: "100px", width: "100px" }}></Box>
                )}
              </Grid>
              <Grid item xs={1} style={{ position: "relative" }}>
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
                  multiple
                  type="file"
                  accept="image/*"
                  onChange={changeImage}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  autoComplete="given-name"
                  name="nickname"
                  required
                  fullWidth
                  id="nickname"
                  label="공연 제목"
                  autoFocus
                  onChange={changePerfName}
                />
              </Grid>

              <Grid item xs={10}>
                <TextField
                  required
                  fullWidth
                  id="email"
                  label="공연 가격"
                  name="email"
                  autoComplete="email"
                  onChange={changePerfName}
                />
              </Grid>

              <Grid item xs={2}>
                <Button>+</Button>
                <Button>-</Button>
              </Grid>

              <Grid item xs={5}>
                <TextField
                  required
                  fullWidth
                  name="password"
                  label="시작기간-달력으로 하기"
                  type="password"
                  id="password"
                  autoComplete="new-password"
                />
              </Grid>

              <Grid item xs={2}>
                <Typography
                  style={{ fontSize: "large", fontFamily: "IBM Plex Sans KR" }}
                >
                  ~
                </Typography>
              </Grid>

              <Grid item xs={5}>
                <TextField
                  required
                  fullWidth
                  name="password"
                  label="종료기간-달력으로 하기"
                  type="password"
                  id="password"
                  autoComplete="new-password"
                />
              </Grid>

              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="passwordCheck"
                  label="공연 시간"
                  type="password"
                  id="passwordCheck"
                  autoComplete="new-password"
                />
              </Grid>

              <Grid item xs={12}>
                <TextField
                  fullWidth
                  multiline
                  id="profileMessage"
                  label="공연 소개"
                  name="profileMessage"
                />
              </Grid>

              <Grid item xs={3}>
                <Button
                  type="submit"
                  fullWidth
                  variant="contained"
                  sx={{ mt: 3, mb: 2 }}
                >
                  등록하기
                </Button>
              </Grid>
              <Grid item xs={3}>
                <Button
                  type="submit"
                  fullWidth
                  variant="contained"
                  color="error"
                  sx={{ mt: 3, mb: 2 }}
                >
                  취소하기
                </Button>
              </Grid>
            </Grid>
          </form>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
