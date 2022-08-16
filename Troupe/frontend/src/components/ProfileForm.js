import apiClient from "../apiClient";
import React from "react";
import { useParams } from "react-router-dom";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import Link from "@mui/material/Link";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import { ThemeProvider } from "@mui/material/styles";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import Theme from "./Theme";
import styledButton from "../css/button.module.css";

import Stage from "../img/stage.jpg";

export default function ProfileForm() {
  //memberNo
  const { memberNo } = useParams();

  // //memberInfo 초기화
  // const [memberInfo, setMemberInfo] = React.useState(""
  //   // {
  //   // profileImageUrl: "Stage",
  //   // memberType: "PERFORMER",
  //   // email: "123",
  //   // nickname: "123123",
  //   // description: "123123123",
  //   // memberNo: -1,
  //   // removed: false,
  //   // }
  // );

  //default nickname
  const [defaultNickname, setDefaultNickname] = React.useState("");

  //초기 data 불러오기 판단
  const [defaultValue, setDefaultValue] = React.useState(false);
  //frontend image 초기화
  const [imgUrl, setImgUrl] = React.useState("");
  //email 초기화
  const [email, setEmail] = React.useState("");
  //nickname 초기화
  const [nickname, setNickname] = React.useState("");
  // description 초기화
  const [description, setDescription] = React.useState("");
  //memberType 초기화
  const [memberType, setMemberType] = React.useState("AUDIENCE");

  //nickname 중복확인
  const [nicknameCheck, setNicknameCheck] = React.useState(true);
  //nickname 길이확인
  const [nicknameLength, setNicknameLength] = React.useState(true);
  //currentPassword 일치 확인
  const [pwCurrent, setPwCurrent] = React.useState(true);
  //password 길이확인
  const [pwLength, setPwLength] = React.useState(true);
  //password 일치확인
  const [pwSame, setPwSame] = React.useState(true);
  //memberNo update 시 값 update
  React.useEffect(() => {
    apiClient.getMyinfo().then((data) => {
      // setMemberInfo(data);
      setDefaultNickname(data.nickname);

      setNickname(data.nickname);
      setEmail(data.email);
      setImgUrl(data.profileImageUrl);
      setDescription(data.description);
      setMemberType(data.memberType);

      setDefaultValue(true);
    });
  }, [memberNo]);

  //image 업로드
  const changeImage = (e) => {
    setImgUrl(URL.createObjectURL(e.target.files[0]));
  };

  //nickname Change
  const changeNickname = (e) => {
    setNickname(e.target.value);
    if (defaultNickname === e.target.value) {
      setNicknameCheck(true);
    } else {
      setNicknameCheck(false);
    }
  };

  //email Change
  const changeEmail = (e) => {
    setEmail(e.target.value);
  };

  //중복체크
  const sameCheck = (string) => {
    if (string === "nickname") {
      if (nicknameCheck) {
        alert("nickname 중복확인이 이미 완료되었습니다.");
        return;
      }
      apiClient.existNickname({ nickname: nickname }).then((data) => {
        if (data === false) {
          setNicknameCheck(true);
        } else {
          console.log("nicknameCheck : " + data);
        }
      });
      //닉네임 2~20자
      const nicknameLength = nickname.length;
      const nicknameLengthCheck = nicknameLength >= 2 && nicknameLength <= 20;
      setNicknameLength(nicknameLengthCheck);
    }
  };

  //입력된 값이 올바른지 확인
  const checkValue = (data) => {
    //현재 비밀번호 일치 확인
    return apiClient
      .pwCurrentCheck(data.currentPassword)
      .then((data) => {
        setPwCurrent(data);
        if (!pwCurrent) {
          return false;
        } else {
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
        }
      })
      .catch((error) => {
        console.log(error);
        return false;
      });
  };

  //회원가입버튼 클릭
  const handleSubmit = (event) => {
    event.preventDefault();
    const formData = new FormData(event.currentTarget);
    console.log(event.currentTarget);
    console.log(formData);

    const modifyData = {
      profileImage: formData.get("imgUpload"),
      nickname: formData.get("nickname"),
      email: formData.get("email"),
      currentPassword: formData.get("currentpassword"),
      password: formData.get("password"),
      passwordCheck: formData.get("passwordCheck"),
      profileMessage: formData.get("profileMessage"),
    };
    console.log(modifyData);

    //입력된 값이 올바른지 확인
    checkValue(modifyData).then((data) => {
      console.log(data);
      if (true) {
        apiClient.modifyProfile(formData);
      }
    });
  };

  //취소버튼
  const cancel = () => {
    window.location.href = `/profile/${memberNo}`;
  };
  return (
    <ThemeProvider theme={Theme}>
      <Container maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            fontFamily: "SBAggroB",
            paddingBottom: "100px",
          }}
        >
          <form
            onSubmit={handleSubmit}
            encType="multipart/form-data"
            style={{ textAlign: "center" }}
          >
            <Grid container spacing={2}>
              <Grid item xs={12}>
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
                      border: "3px white solid",
                      boxShadow:
                        "0 10px 35px rgba(0, 0, 0, 0.05), 0 6px 6px rgba(0, 0, 0, 0.3)",
                    }}
                  ></img>
                )}
              </Grid>
              <Grid item xs={12} style={{ position: "relative" }}>
                <Button
                  style={{
                    width: "100px",
                    height: "30px",
                  }}
                  className={styledButton.btn4}
                >
                  찾아보기
                  <input
                    style={{
                      position: "absolute",
                      width: "80px",
                      height: "30px",
                      bottom: "0px",
                      right: "0px",
                      opacity: "0%",
                    }}
                    id="profileImage"
                    className="profileImage"
                    name="profileImage"
                    type="file"
                    accept="image/*"
                    onChange={changeImage}
                  />{" "}
                </Button>
              </Grid>
              <Grid item xs={6}>
                <TextField
                  required
                  fullWidth
                  name="memberType"
                  label="memberType"
                  id="memberType"
                  value={memberType}
                  style={{ backgroundColor: "white" }}
                  disabled
                  sx={{
                    "& .MuiOutlinedInput-root.Mui-focused": {
                      "& > fieldset": {
                        borderColor: "#66cc66",
                      },
                    },
                  }}
                />
              </Grid>

              {memberType === "PERFORMER" ? (
                <Grid item xs={6} style={{ textAlign: "right" }}>
                  <div style={{ fontSize: "12px" }}>
                    본인인증이 완료되었습니다.
                  </div>
                </Grid>
              ) : (
                <Grid item xs={6} style={{ textAlign: "right" }}>
                  <Button
                    style={{
                      width: "80px",
                      height: "30px",
                      bottom: "0px",
                      right: "0px",
                    }}
                    className={styledButton.btn4}
                    // onClick={() => 본인인증()}
                  >
                    본인인증
                  </Button>
                </Grid>
              )}
              <Grid
                item
                xs={12}
                style={{ textAlign: "left", fontSize: "20px" }}
              >
                <TextField
                  required
                  fullWidth
                  name="email"
                  label="email"
                  id="email"
                  value={email}
                  style={{ backgroundColor: "white" }}
                  disabled
                  sx={{
                    "& .MuiOutlinedInput-root.Mui-focused": {
                      "& > fieldset": {
                        borderColor: "#66cc66",
                      },
                    },
                  }}
                />
              </Grid>
              <Grid item xs={9}>
                {defaultValue ? (
                  <TextField
                    autoComplete="given-name"
                    name="nickname"
                    required
                    fullWidth
                    id="nickname"
                    label="닉네임"
                    defaultValue={defaultNickname}
                    onChange={changeNickname}
                    style={{ backgroundColor: "white" }}
                    sx={{
                      "& .MuiOutlinedInput-root.Mui-focused": {
                        "& > fieldset": {
                          borderColor: "#66cc66",
                        },
                      },
                    }}
                  />
                ) : (
                  <div></div>
                )}
              </Grid>
              <Grid item xs={3} style={{ position: "relative" }}>
                <Button
                  style={{
                    position: "absolute",
                    width: "80px",
                    height: "30px",
                    bottom: "13px",
                    right: "0px",
                  }}
                  className={styledButton.btn4}
                  onClick={() => sameCheck("nickname")}
                >
                  중복확인
                </Button>
              </Grid>
              {nicknameCheck ? (
                <Grid item xs={12}>
                  <div
                    style={{
                      color: "green",
                      fontSize: "13px",
                      fontFamily: "Cafe24SsurroundAir",
                      fontWeight: "bold",
                    }}
                  >
                    nickname 중복확인 완료
                  </div>
                </Grid>
              ) : (
                <Grid item xs={12}>
                  <div
                    style={{
                      color: "red",
                      fontSize: "13px",
                      fontFamily: "Cafe24SsurroundAir",
                      fontWeight: "bold",
                    }}
                  >
                    nickname 중복확인이 필요합니다.
                  </div>
                </Grid>
              )}
              {nicknameLength ? (
                <div></div>
              ) : (
                <Grid item xs={12}>
                  <div
                    style={{
                      color: "red",
                      fontSize: "13px",
                      fontFamily: "Cafe24SsurroundAir",
                      fontWeight: "bold",
                    }}
                  >
                    닉네임은 2~20자의 길이로 설정해주세요.
                  </div>
                </Grid>
              )}
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="currentpassword"
                  label="현재 비밀번호"
                  type="password"
                  id="currentpassword"
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
              </Grid>
              {pwCurrent ? (
                <div></div>
              ) : (
                <Grid item xs={12}>
                  <div
                    style={{
                      color: "red",
                      fontSize: "13px",
                      fontFamily: "Cafe24SsurroundAir",
                      fontWeight: "bold",
                    }}
                  >
                    비밀번호가 일치하지 않습니다.
                  </div>
                </Grid>
              )}
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  name="password"
                  label="새 비밀번호"
                  type="password"
                  id="password"
                  autoComplete="new-password"
                  style={{ backgroundColor: "white" }}
                  sx={{
                    "& .MuiOutlinedInput-root.Mui-focused": {
                      "& > fieldset": {
                        borderColor: "#66cc66",
                      },
                    },
                  }}
                />
              </Grid>
              {pwLength ? (
                <div></div>
              ) : (
                <Grid item xs={12}>
                  <div
                    style={{
                      color: "red",
                      fontSize: "13px",
                      fontFamily: "Cafe24SsurroundAir",
                      fontWeight: "bold",
                    }}
                  >
                    비밀번호는 8~20자의 길이로 설정해주세요.
                  </div>
                </Grid>
              )}
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  name="passwordCheck"
                  label="새 비밀번호 확인"
                  type="password"
                  id="passwordCheck"
                  autoComplete="new-password"
                  style={{ backgroundColor: "white" }}
                  sx={{
                    "& .MuiOutlinedInput-root.Mui-focused": {
                      "& > fieldset": {
                        borderColor: "#66cc66",
                      },
                    },
                  }}
                />
              </Grid>
              {pwSame ? (
                <div></div>
              ) : (
                <Grid item xs={12}>
                  <div
                    style={{
                      color: "red",
                      fontSize: "13px",
                      fontFamily: "Cafe24SsurroundAir",
                      fontWeight: "bold",
                    }}
                  >
                    비밀번호가 일치하지 않습니다.
                  </div>
                </Grid>
              )}
              <Grid item xs={12}>
                {defaultValue ? (
                  <TextField
                    fullWidth
                    multiline
                    id="description"
                    label="소개글 입력"
                    defaultValue={description}
                    name="description"
                    style={{ backgroundColor: "white" }}
                    sx={{
                      "& .MuiOutlinedInput-root.Mui-focused": {
                        "& > fieldset": {
                          borderColor: "#66cc66",
                        },
                      },
                    }}
                  />
                ) : (
                  <div></div>
                )}
              </Grid>
            </Grid>
            <Grid container spacing={2}>
              <Grid item xs={6}>
                <Button
                  style={{
                    width: "100%",
                    height: "100%",
                  }}
                  className={styledButton.btn}
                  onClick={cancel}
                >
                  취소
                </Button>
              </Grid>
              <Grid item xs={6}>
                <Button
                  type="submit"
                  style={{
                    width: "100%",
                    height: "100%",
                  }}
                  className={styledButton.btn3}
                >
                  프로필수정
                </Button>
              </Grid>
            </Grid>
          </form>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
