import React, { Fragment, useEffect, useState } from "react";
import { styled } from "@mui/material/styles";
import Paper from "@mui/material/Paper";
import Grid from "@mui/material/Grid";
import { Button } from "@mui/material";
import MUICarousel from "react-material-ui-carousel";
import Modal from "@mui/material/Modal";
import Box from "@mui/material/Box";
import apiClient from "../apiClient";
import CommentList from "./CommentList";
import { useParams } from "react-router-dom";
import CommentForm from "./CommentForm";
import Comment from "./Comment";
import CommentCount from "./CommentCount";
import Theme from "./Theme";
import { ThemeProvider } from "@mui/material/styles";
import TextField from "@mui/material/TextField";
import FormatListBulletedIcon from "@mui/icons-material/FormatListBulleted";
import ModeEditIcon from "@mui/icons-material/ModeEdit";
import DeleteIcon from "@mui/icons-material/Delete";
import { textAlign } from "@mui/system";
import CssBaseline from "@mui/material/CssBaseline";
// 제목, 기간, 시간, 장소, 티켓가격

const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === "dark" ? "#1A2027" : "#EEE3D0",
  ...theme.typography.body2,
  padding: theme.spacing(1),
  textAlign: "left",
  color: theme.palette.text.secondary,
}));

//포스터 슬라이드
function Carousel(props) {
  function CarouselItem(props) {
    const [open, setOpen] = React.useState(false);
    // console.log(props);
    function handleOpen() {
      setOpen(true);
    }
    function handleClose() {
      setOpen(false);
    }
    return (
      // <Paper style={{ backgroundColor: "#FFF" }} elevation={0}>
      <Paper
        style={{
          backgroundColor: "#EEE3D0",
          height: "800px",
          width: "700px",
        }}
        elevation={0}
      >
        <img
          onClick={handleOpen}
          src={props.imgUrl}
          alt="random"
          style={{ height: "100%", width: "100%" }}
        ></img>
        <Modal
          open={open}
          onClose={handleClose}
          aria-labelledby="modal-modal-title"
          aria-describedby="modal-modal-description"
        >
          <Box
            style={{
              position: "absolute",
              margin: "100px 0px",
              left: "50%",
              width: "100%",
              height: "100%",
              maxHeight: "800px",
              maxWidth: "800px",
              transform: "translateX(-50%)",
            }}
          >
            <img
              onClick={handleOpen}
              src={props.imgUrl}
              alt="random"
              style={{ height: "100%", width: "100%" }}
            ></img>
          </Box>
        </Modal>
      </Paper>
    );
  }

  return (
    <MUICarousel animation="slide" indicators="">
      {props.imgUrl.map((image, i) => (
        <CarouselItem
          key={i}
          category={props.category}
          status={props.status}
          imgUrl={image}
        />
      ))}
    </MUICarousel>
  );
}

// 목록 수정 삭제 버튼
function ModifyDeleteButton(props) {
  const [user, setUser] = React.useState(true);

  React.useEffect(() => {
    setUser(parseInt(sessionStorage.getItem("loginMember")) === props.memberNo);
  }, [sessionStorage.getItem("loginMember"), props.memberNo]);

  function handleUser() {
    setUser(!user);
  }

  function onRemove() {
    const data = {
      pfNo: props,
    };
    apiClient.perfRemove(data);
  }

  function moveToModify() {
    window.location.href = `/perf/modify/${props.performanceNo}`;
  }

  return (
    <Grid>
      <Grid style={{ float: "right", marginRight: "280px" }}>
        <Button
          href="/perf/list/0"
          style={{
            margin: "5px",
            backgroundColor: "transparent",
          }}
        >
          <FormatListBulletedIcon color="action"></FormatListBulletedIcon>
        </Button>
        {user ? (
          <Fragment>
            <Button
              style={{
                margin: "5px",
                backgroundColor: "transparent",
              }}
              onClick={moveToModify}
            >
              <ModeEditIcon color="action"></ModeEditIcon>
            </Button>
            <Button
              style={{
                margin: "5px",
                backgroundColor: "transparent",
              }}
              onClick={onRemove}
            >
              <DeleteIcon color="action"></DeleteIcon>
            </Button>
          </Fragment>
        ) : (
          ""
        )}
      </Grid>
    </Grid>
  );
}

function PerfDetail() {
  const { pfNo } = useParams();

  const [category, setCategory] = useState(""); //  필수
  const [createdTime, setCreatedTime] = useState(Date.now()); //  비 필수
  const [description, setDescription] = useState(""); //  필수
  const [detailTime, setDetailTime] = useState(""); //  필수
  const [endDate, setEndDate] = useState(Date.now()); //  필수
  const [imgUrl, setImgUrl] = useState([]); // 필수
  const [location, setLocation] = useState(""); //  필수
  const [memberInfo, setMemberInfo] = useState({
    //  필수
    memberNo: 0,
    nickname: "",
    profileImg: "", //  기본 이미지 정해서 s3에 업로드하고 그 url을 넣어주기
  });
  const [price, setPrice] = useState([
    {
      id: 0,
      seat: "",
      price: 0,
    },
  ]);
  const [isRemoved, setIsRemoved] = useState(false); //  비 필수
  const [runtime, setRuntime] = useState(0); // 필수
  const [startDate, setStartDate] = useState(Date.now()); //  필수
  const [status, setStatus] = useState(""); //  필수
  const [title, setTitle] = useState(""); //  필수
  const [updatedTime, setUpdatedTime] = useState(Date.now()); //  비 필수

  const [performanceNo, setPerformanceNo] = useState(0);
  const [commentList, setCommentList] = React.useState([]);

  const refreshFunction = (newComment) => {
    setCommentList([...commentList, newComment]);
  };

  function convertTime(timestamp) {
    let date = new Date(timestamp); //타임스탬프를 인자로 받아 Date 객체 생성

    /* 생성한 Date 객체에서 년, 월, 일, 시, 분을 각각 문자열 곧바로 추출 */
    let year = date.getFullYear().toString().slice(-2); //년도 뒤에 두자리
    let month = ("0" + (date.getMonth() + 1)).slice(-2); //월 2자리 (01, 02 ... 12)
    let day = ("0" + date.getDate()).slice(-2); //일 2자리 (01, 02 ... 31)
    // let hour = ("0" + date.getHours()).slice(-2); //시 2자리 (00, 01 ... 23)
    // let minute = ("0" + date.getMinutes()).slice(-2); //분 2자리 (00, 01 ... 59)
    // let second = ("0" + date.getSeconds()).slice(-2); //초 2자리 (00, 01 ... 59)

    let returnDate = year + "." + month + "." + day;
    return returnDate;
  }

  useEffect(() => {
    setPerformanceNo(pfNo);

    //  공연 기본 정보 로드
    apiClient.getPerfDetail(pfNo).then((data) => {
      // console.log(data);
      setCategory(data.category);
      setCreatedTime(data.createdTime);
      setDescription(data.description);
      setDetailTime(data.detailTime);
      setEndDate(data.endDate);
      setImgUrl(Object.values(data.imageUrl));
      setLocation(data.location);
      setMemberInfo({
        memberNo: data.memberNo,
        nickname: data.nickname,
        profileImg: data.profileImg,
      });
      setPrice(data.price);
      setIsRemoved(data.removed);
      setRuntime(data.runtime);
      setStartDate(data.startDate);
      setStatus(data.status);
      setTitle(data.title);
      setUpdatedTime(data.updatedTime);
    });
    //  공연 댓글 정보 로드
    apiClient.getPerfReviewList(pfNo).then((data) => {
      // console.log("return data 2 ", data);
      const json = [];

      data.forEach((item) => {
        json.push({
          memberNo: item.memberNo,
          reviewNo: item.reviewNo,
          profileImageUrl: item.profileImageUrl,
          comment: item.comment,
          nickname: item.nickname,
          isRemoved: item.removed,
          pfNo: item.pfNo,
          childComments: item.childComments,
        });
      });

      setCommentList(json);
    });
  }, [pfNo]);

  return (
    <ThemeProvider theme={Theme}>
      <CssBaseline />
      <Grid
        style={{
          background: "#EEE3D0",
          fontFamily: "SBAggroB",
          textAlign: "center",
        }}
      >
        <div>
          <ModifyDeleteButton
            performanceNo={pfNo}
            perfName={title}
            memberNo={memberInfo.memberNo}
          />
          <Grid container>
            <Grid item xs={2}></Grid>
            <Grid item xs={5}>
              <Item elevation={0} style={{ marginTop: "18px" }}>
                <Carousel
                  category={category}
                  status={status}
                  imgUrl={imgUrl}
                ></Carousel>
              </Item>
            </Grid>
            <Grid item xs={3}>
              <Item elevation={0}>
                <ul
                  style={{
                    display: "relative",
                    fontSize: "large",
                    listStyle: "none",
                    textAlign: "left",
                  }}
                >
                  <li style={{ marginBottom: "10px" }}>
                    <span
                      style={{
                        background: "#66cc66",
                        borderRadius: "10%",
                        fontSize: "20px",
                        width: "80px",
                        position: "relative",
                        marginRight: "5px",
                        boxShadow:
                          "0 10px 35px rgba(0, 0, 0, 0.05), 0 6px 6px rgba(0, 0, 0, 0.1)",
                        padding: "3px",
                        border: "2px solid white",
                        color: "white",
                      }}
                    >
                      {status}
                    </span>
                    <span
                      style={{
                        background: "#ffd400",
                        borderRadius: "10%",
                        fontSize: "20px",
                        width: "80px",
                        position: "relative",
                        boxShadow:
                          "0 10px 35px rgba(0, 0, 0, 0.05), 0 6px 6px rgba(0, 0, 0, 0.1)",
                        padding: "3px",
                        border: "2px solid white",
                        color: "white",
                      }}
                    >
                      {category}
                    </span>
                    <Grid style={{ marginTop: "19px" }}>
                      {/* <a
                        style={{
                          textDecoration: "none",
                        }}
                        href={"/profile/" + memberInfo.memberNo}
                      >
                        <img
                          src={memberInfo.profileImg}
                          alt="random"
                          style={{
                            borderRadius: "70%",
                            objectFit: "cover",
                            height: "65px",
                            width: "65px",
                            boxShadow:
                              "0 10px 35px rgba(0, 0, 0, 0.05), 0 6px 6px rgba(0, 0, 0, 0.1)",
                          }}
                        ></img>
                        <span
                          style={{
                            display: "inline-block",
                            paddingBottom: "30px",
                          }}
                        >
                          {" "}
                          {memberInfo.nickname}
                        </span>
                      </a> */}
                      <Grid container item xs={9} mb={6} mt={7}>
                        <Grid>
                          <a
                            style={{ textDecoration: "none" }}
                            href={"/profile/" + memberInfo.memberNo}
                          >
                            <img
                              src={memberInfo.profileImg}
                              alt="profile"
                              style={{
                                borderRadius: "70%",
                                objectFit: "cover",
                                height: "50px",
                                width: "50px",
                                marginRight: "10px",
                                boxShadow:
                                  "0 10px 35px rgba(0, 0, 0, 0.05), 0 6px 6px rgba(0, 0, 0, 0.1)",
                              }}
                            ></img>
                          </a>
                        </Grid>
                        <Grid ml={1} mt={2}>
                          {memberInfo.nickname}
                        </Grid>
                      </Grid>
                    </Grid>
                  </li>
                  <li>공연제목 : {title}</li>
                  <br></br>
                  <li>
                    기간 : {convertTime(startDate)}~{convertTime(endDate)}
                  </li>
                  <br></br>
                  <li>공연 시간 : {runtime}</li>
                  <br></br>
                  <li>장소 : {location}</li>
                  <br></br>
                  {price.map((item, i) =>
                    i === 0 ? (
                      <li id={i}>
                        좌석 : {item.seat + "       "}
                        가격 : {item.price}
                      </li>
                    ) : (
                      <li id={i}>
                        {item.seat} {item.price}
                      </li>
                    ),
                  )}
                  <br />
                  <li>상세 설명 : {description}</li>
                  <br />
                </ul>
              </Item>
            </Grid>
            <Grid item xs={2}></Grid>
          </Grid>
          <div
            style={{
              margin: "12px",
              paddingBottom: "100px",
              paddingTop: "20px",
            }}
          >
            <Grid container spacing={1}>
              <Grid item xs={2}></Grid>
              <Grid item xs={8}>
                <CommentList
                  kind={"performance"}
                  refreshFunction={refreshFunction}
                  commentList={commentList}
                  performanceNo={performanceNo}
                />
                <Grid item xs={2}></Grid>
              </Grid>
            </Grid>
          </div>
        </div>
      </Grid>
    </ThemeProvider>
  );
}

export default PerfDetail;
