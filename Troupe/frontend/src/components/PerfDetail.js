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

// 제목, 기간, 시간, 장소, 티켓가격

const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === "dark" ? "#1A2027" : "#FFF",
  ...theme.typography.body2,
  padding: theme.spacing(1),
  textAlign: "center",
  color: theme.palette.text.secondary,
}));

//포스터 슬라이드
function Carousel(props) {
  function CarouselItem(props) {
    const [open, setOpen] = React.useState(false);
    function handleOpen() {
      setOpen(true);
    }
    function handleClose() {
      setOpen(false);
    }
    return (
      <Paper style={{ backgroundColor: "#FFFF" }} elevation={0}>
        <img
          onClick={handleOpen}
          src="https://source.unsplash.com/random"
          alt="random"
          style={{ height: "500px", width: "400px" }}
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
              top: "2%",
              left: "30%",
              height: "700px",
              width: "550px",
            }}
          >
            <img
              onClick={handleOpen}
              src="https://source.unsplash.com/random"
              alt="random"
              style={{ height: "700px", width: "550px" }}
            ></img>
          </Box>
        </Modal>
        <Fragment>
          <Box
            style={{
              fontFamily: "IBM Plex Sans KR",
              background: "pink",
              borderRadius: "10%",
              position: "absolute",
              top: "15px",
              right: "115px",
              zIndex: "3",
            }}
          >
            {props.status}
          </Box>
          <Box
            style={{
              fontFamily: "IBM Plex Sans KR",
              background: "skyblue",
              borderRadius: "10%",
              position: "absolute",
              top: "15px",
              right: "165px",
              zIndex: "3",
            }}
          >
            {props.category}
          </Box>
        </Fragment>
      </Paper>
    );
  }
  var items = [
    {
      name: "Random Name #1",
      description: "Probably the most random thing you have ever seen!",
    },
    {
      name: "Random Name #2",
      description: "Hello World!",
    },
  ];

  return (
    <MUICarousel animation="slide" indicators="" autoPlay={false}>
      {items.map((item, i) => (
        <CarouselItem
          key={i}
          item={item}
          category={props.category}
          status={props.status}
          imageUrl={props.imageUrl}
        />
      ))}
    </MUICarousel>
  );
}

// 목록 수정 삭제 버튼
function ModifyDeleteButton(props) {
  const [user, setUser] = React.useState(true);

  function handleUser() {
    setUser(!user);
  }

  function onRemove() {
    const data = {
      pfNo: props,
    };
    apiClient.perfRemove(data);
  }

  return (
    <div style={{ float: "right", backgroundColor: "#FFF" }}>
      <Button
        variant="outlined"
        href="/perf/list"
        style={{
          margin: "8px",
          backgroundColor: "beige",
          fontFamily: "IBM Plex Sans KR",
        }}
      >
        목록
      </Button>
      {user ? (
        <Fragment>
          <Button
            variant="outlined"
            href="/perf/new"
            style={{
              margin: "8px",
              backgroundColor: "skyblue",
              fontFamily: "IBM Plex Sans KR",
            }}
          >
            수정
          </Button>
          <Button
            variant="outlined"
            style={{
              margin: "8px",
              backgroundColor: "pink",
              fontFamily: "IBM Plex Sans KR",
            }}
            onClick={onRemove}
          >
            삭제
          </Button>
        </Fragment>
      ) : (
        ""
      )}
    </div>
  );
}

function PerfDetail() {
  const { pfNo } = useParams();

  const [category, setCategory] = useState(""); //  필수
  const [createdTime, setCreatedTime] = useState(Date.now()); //  비 필수
  const [description, setDescription] = useState(""); //  필수
  const [detailTime, setDetailTime] = useState(""); //  필수
  const [endDate, setEndDate] = useState(Date.now()); //  필수
  const [imageUrl, setImageUrl] = useState({
    //  필수
    index: 0,
    url: "",
  });
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
      name: "",
      price: 0,
    },
  ]);
  const [isRemoved, setIsRemoved] = useState(false); //  비 필수
  const [runtime, setRuntime] = useState(0); // 필수
  const [startDate, setStartDate] = useState(Date.now()); //  필수
  const [status, setStatus] = useState(""); //  필수
  const [title, setTitle] = useState(""); //  필수
  const [updatedTime, setUpdatedTime] = useState(Date.now()); //  비 필수

  const [performanceNo, setPerformanceNo] = useState(pfNo);
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
    let hour = ("0" + date.getHours()).slice(-2); //시 2자리 (00, 01 ... 23)
    let minute = ("0" + date.getMinutes()).slice(-2); //분 2자리 (00, 01 ... 59)
    let second = ("0" + date.getSeconds()).slice(-2); //초 2자리 (00, 01 ... 59)

    let returnDate =
      year +
      "." +
      month +
      "." +
      day +
      ". " +
      hour +
      ":" +
      minute +
      ":" +
      second;
    return returnDate;
  }

  useEffect(() => {
    setPerformanceNo(pfNo);

    apiClient.getPerfDetail(pfNo).then((data) => {
      console.log(`return data : ${data}`);
      setCategory(data.category);
      setCreatedTime(data.createdTime);
      setDescription(data.description);
      setDetailTime(data.detailTime);
      setEndDate(data.endDate);
      setImageUrl(data.imageUrl);
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

    apiClient.getPerfReviewList(pfNo).then((data) => {
      console.log(data);
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
        });
      });

      setCommentList(json);
    });
  }, []);

  // console.log(price);
  // console.log(`pfNo: ${pfNo}`);
  // console.log(`performanceNo: ${performanceNo}`);
  return (
    <div style={{ background: "#FFFF", fontFamily: "IBM Plex Sans KR" }}>
      <ModifyDeleteButton />
      <div>
        <Grid container spacing={4}>
          <Grid item xs={5}>
            <Item
              elevation={0}
              style={{ position: "relative", direction: "row" }}
            >
              <Carousel
                category={category}
                status={status}
                imageUrl={imageUrl}
              ></Carousel>
            </Item>
          </Grid>
          <Grid item xs={7}>
            <Item elevation={0}>
              <ul
                style={{
                  fontSize: "large",
                  listStyle: "none",
                  paddingLeft: "0px",
                  fontFamily: "IBM Plex Sans KR",
                  textAlign: "left",
                }}
              >
                <li>
                  <a style={{ textDecoration: "none" }} href="/">
                    <img
                      src={memberInfo.profileImg}
                      alt="random"
                      style={{
                        borderRadius: "70%",
                        objectFit: "cover",
                        height: "20px",
                        width: "20px",
                      }}
                    ></img>
                    {memberInfo.nickname}
                  </a>
                </li>
                <li>제목: {title}</li>
                <br></br>
                <li>
                  기간: {convertTime(startDate)} ~ {convertTime(endDate)}
                </li>
                <br></br>
                <li>공연 시간: {runtime}</li>
                <br></br>
                <li>장소: {location}</li>
                <br></br>
                {price.map((item, i) =>
                  i === 0 ? (
                    <li id={i}>
                      가격 : {item.name} {item.price}
                    </li>
                  ) : (
                    <li id={i}>
                      {item.name} {item.price}
                    </li>
                  )
                )}
                {/* <li>가격: 전석 33,000원</li> */}
              </ul>
            </Item>
          </Grid>
          <Grid item xs={12}>
            <Item elevation={0}>{description}</Item>
          </Grid>
        </Grid>
        <div style={{ margin: "12px" }}>
          <Grid container spacing={1}>
            <Grid item xs={12}>
              <Item>
                <CommentList
                  refreshFunction={refreshFunction}
                  commentList={commentList}
                  performanceNo={performanceNo}
                />
              </Item>
            </Grid>
          </Grid>
        </div>
      </div>
    </div>
  );
}

export default PerfDetail;
