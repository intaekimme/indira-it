import React from "react";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardMedia from "@mui/material/CardMedia";
import Grid from "@mui/material/Grid";
import { useInfiniteQuery } from "react-query";
import apiClient from "../apiClient";
import FeedSaveButton from "./FeedSaveButton";
import FeedLikeButton from "./FeedLikeButton";
import { Typography } from "@mui/material";
import Modal from "@mui/material/Modal";
import stylesModal from "../css/modal.module.css";
import { Fragment } from "react";
import FeedDetail from "./FeedDetail";
import PlusButton from "./PlusButton";
import stylesTag from "../css/tag.module.css";

export default function _MinhoFeedList(props) {
  const [open, setOpen] = React.useState(false);
  const [feedNo, setFeedNo] = React.useState(0);
  const [isHover, setIsHover] = React.useState(-1);

  const [change, setChange] = React.useState(false);

  function handleOpen(no) {
    setOpen(true);
    setFeedNo(no);
    setChange(false);
  }
  function handleClose() {
    setOpen(false);
    setChange(true);
  }

  let FeedListAllQuery = useInfiniteQuery(
		"SaveFeeds",//데이터 식별 쿼리 키
		()=> apiClient.getMemberFeedList({pageParam:0, memberNo:props.memberInfo.memberNo, string: props.string}),//쿼리 함수
    {
      getNextPageParam: (lastPage, pages) => {
        return pages.length + 1;
			},
			getMemberNo: () => {
				return sessionStorage.getItem("loginMember");
			},
		},
  );
  console.log(FeedListAllQuery.data);
  console.log(FeedListAllQuery.isLoading);

  if (!FeedListAllQuery.isLoading && typeof FeedListAllQuery.data.pages[0]) {
    return (
      <Fragment>
        <Grid container spacing={4}>
          {FeedListAllQuery.data.pages.length == 0 || !FeedListAllQuery.data.pages[0] || FeedListAllQuery.data.pages[0].length == 0 ?
            <Grid item xs={12} style={{position:"relative", height:"100px"}}><div style={{position:"relative", top:"50%"}}>리스트가 없습니다</div></Grid>
            : <div></div>
          }
          {FeedListAllQuery.data.pages[0] ? FeedListAllQuery.data.pages.map((page) =>
            page.map((datum) => (
              <Grid item key={datum.feedNo} xs={12} sm={6} md={4}>
                <Card
                  sx={{
                    position: "relative",
                    height: "100%",
                    display: "flex",
                    flexDirection: "column",
                  }}
                  elevation={0}
                  style={{
                    boxShadow:
                      "0 10px 35px rgba(0, 0, 0, 0.05), 0 6px 6px rgba(0, 0, 0, 0.5)",
                  }}
                >
                  <Grid container mt={1}>
                    <Grid ml={1}>
                      <a
                        style={{ textDecoration: "none" }}
                        href={"/profile/" + datum.memberNo}
                      >
                        <img
                          src={datum.profileImageUrl}
                          alt=""
                          style={{
                            borderRadius: "70%",
                            objectFit: "cover",
                            height: "30px",
                            width: "30px",
                            boxShadow:
                              "0 10px 35px rgba(0, 0, 0, 0.05), 0 6px 6px rgba(0, 0, 0, 0.1)",
                          }}
                        ></img>
                      </a>
                    </Grid>
                    <Grid ml={1} mb={2}>
                      <Typography
                        style={{
                          fontSize: "13px",
                          fontFamily: "SBAggroB",
                          wordBreak: "break-all",
                          overflow: "hidden",
                        }}
                        component="span"
                      >
                        {datum.nickname}
                      </Typography>
                    </Grid>
                  </Grid>
                  <CardMedia
                    component="img"
                    sx={{
                      pb: 1,
                      objectFit: "cover",
                      width: "300px",
                      height: "300px",
                      opacity: isHover === datum.feedNo ? 0.5 : null,
                      transform: isHover === datum.feedNo ? "scale(1.1)" : null,
                      transition: "0.5s",
                    }}
                    image={Object.values(datum.images)[0]}
                    alt=""
                    onClick={() => handleOpen(datum.feedNo)}
                    onMouseEnter={() => setIsHover(datum.feedNo)}
                    onMouseLeave={() => setIsHover(-1)}
                  ></CardMedia>
                  {isHover === datum.feedNo ? (
                    <div
                      style={{
                        marginLeft: "0.5em",
                        display: "inline",
                        position: "absolute",
                        height: "300px",
                        width: "265px",
                        color: "black",
                        top: "150px",
                      }}
                      onMouseEnter={() => setIsHover(datum.feedNo)}
                      onMouseLeave={() => setIsHover(-1)}
                      onClick={() => handleOpen(datum.feedNo)}
                    >
                      {" "}
                      <div className={stylesTag.HashWrapOuter}>
                        {datum.tags.map((tag) => (
                          <div className={stylesTag.HashWrapInner}>#{tag}</div>
                        ))}{" "}
                      </div>
                    </div>
                  ) : null}
                  <CardActions
                    sx={{
                      justifyContent: "space-between",
                      margin: "5px",
                      padding: "0px",
                    }}
                  >
                    <Grid>
                      <FeedLikeButton
                        change={change}
                        feedNo={datum.feedNo}
                      ></FeedLikeButton>
                    </Grid>
                    <Grid mr={-3}>
                      <FeedSaveButton
                        change={change}
                        feedNo={datum.feedNo}
                      ></FeedSaveButton>
                    </Grid>
                  </CardActions>
                </Card>
                <Modal
                  open={open}
                  onClose={handleClose}
                  aria-labelledby="simple-modal-title"
                  aria-describedby="simple-modal-description"
                  className={stylesModal.outer}
                >
                  <FeedDetail
                    setChange={setChange}
                    feedNo={feedNo}
                    open={open}
                    handleClose={handleClose}
                    showSearch={props.showSearch}
                    handleShowSearch={props.handleShowSearch}
                    setTagList={props.setTagList}
                  ></FeedDetail>
                </Modal>
              </Grid>
            )),
          ) : <div></div>}
        </Grid>
        <PlusButton handleCard={FeedListAllQuery.fetchNextPage}></PlusButton>
      </Fragment>
    );
  }
  else {
    return(<div></div>)
  }
}