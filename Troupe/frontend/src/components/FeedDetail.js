import React, { Fragment } from "react";
import CssBaseline from "@mui/material/CssBaseline";
import { createTheme, ThemeProvider, styled } from "@mui/material/styles";
import { Button, Grid, Container } from "@mui/material";
import Box from "@mui/material/Box";
import Modal from "@mui/material/Modal";
import MUICarousel from "react-material-ui-carousel";
import Paper from "@mui/material/Paper";
import apiClient from "../apiClient";
import CardMedia from "@mui/material/CardMedia";
import { useParams } from "react-router-dom";
import stylesTag from "../css/tag.module.css";
import { func } from "prop-types";

const theme = createTheme({
  palette: {
    neutral: {
      main: "#fda085",
      contrastText: "#fff",
    },
  },
});

const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === "dark" ? "#1A2027" : "#FFF",
  ...theme.typography.body2,
  padding: theme.spacing(1),
  textAlign: "center",
  color: theme.palette.text.secondary,
}));

function Carousel(props) {
  function CarouselItem(prop) {
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
          src={prop.item}
          alt="img"
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
              src={prop.item}
              alt="img"
              style={{ height: "700px", width: "550px" }}
            ></img>
          </Box>
        </Modal>
      </Paper>
    );
  }

  return (
    <MUICarousel animation="slide" indicators="">
      {props.children.map((item, id) => (
        <CarouselItem key={id} item={item.props.item} />
      ))}
    </MUICarousel>
  );
}

function ModifyDeleteButton(props) {
  const [user, setUser] = React.useState(false);

  React.useEffect(() => {
    setUser(parseInt(sessionStorage.getItem("loginMember")) === props.memberNo);
    // console.log(sessionStorage.getItem("loginMember") == props.memberNo);
    // console.log(props.memberNo);
  }, [sessionStorage.getItem("loginMember"), props.memberNo]);
  function handleUser() {
    setUser(!user);
  }
  function onRemove() {
    apiClient.feedRemove(props.feedNo);
  }

  return (
    <div style={{ float: "right", backgroundColor: "#FFF" }}>
      <Button
        variant="outlined"
        href="/feed/list"
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
            href="/feed/modify/${feedNo}"
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

export default function FeedDetail() {
  const { feedNo } = useParams();
  const [feedInfo, setFeedInfo] = React.useState({
    content: "",
    createdTime: "",
    memberNo: 0,
    nickname: "",
    profileImageUrl: "",
    tags: [],
  });
  const [like, setLike] = React.useState(0);
  const [img, setImg] = React.useState(new Map());

  React.useEffect(() => {
    apiClient.getFeedDetail(feedNo).then((data) => {
      setFeedInfo(data);
      setImg(data.images);
    });
    apiClient.getFeedTotalLike(feedNo).then((data) => {
      setLike(data);
    });
  }, [feedNo]);
  // console.log(Object.keys(img));
  // console.log(Object.values(img));
  return (
    <ThemeProvider theme={theme}>
      <Container maxWidth="md">
        <CssBaseline />
        <ModifyDeleteButton feedNo={feedNo} memberNo={feedInfo.memberNo} />
        <Grid container spacing={4}>
          {/* <FollowButton memberNo={feedInfo.memberNo}></FollowButton> */}
          <Grid item xs={5}>
            <Item
              elevation={0}
              style={{ position: "relative", direction: "row" }}
            >
              <Carousel>
                {Object.values(img) ? (
                  Object.values(img).map((item, id) => (
                    <Item key={id} item={item} />
                  ))
                ) : (
                  <div></div>
                )}
              </Carousel>
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
                  <a style={{ textDecoration: "none" }} href="#">
                    <img
                      src={feedInfo.profileImageUrl}
                      alt="profile"
                      style={{
                        borderRadius: "70%",
                        objectFit: "cover",
                        height: "50px",
                        width: "50px",
                        marginRight: "10px",
                      }}
                    ></img>
                    <span>{feedInfo.nickname}</span>
                  </a>
                </li>
                <li>
                  <p>{feedInfo.content}</p>
                </li>
                <li>
                  <div className={stylesTag.HashWrapOuter}>
                    {feedInfo.tags ? (
                      feedInfo.tags.map((item, id) => (
                        <div className={stylesTag.HashWrapInner} key={id}>
                          # {item}
                        </div>
                      ))
                    ) : (
                      <div></div>
                    )}
                  </div>
                </li>
              </ul>
            </Item>
            총 좋아요 수 {like}
            <div style={{ margin: "12px" }}>
              <Grid container spacing={1}>
                <Grid item xs={12}>
                  <Item>댓글란</Item>
                </Grid>
              </Grid>
            </div>
          </Grid>
        </Grid>
      </Container>
    </ThemeProvider>
  );
}
