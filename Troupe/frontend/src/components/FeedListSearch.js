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
import PlusButton from "./PlusButton";
import FeedDetail from "./FeedDetail";

export default function FeedListSearch(props) {
  const [change, setChange] = React.useState(false);
  const [open, setOpen] = React.useState(false);
  const [feedNo, setFeedNo] = React.useState(0);
  const [cards, setCards] = React.useState([]);
  const [pageNumber, setPageNumber] = React.useState(1);

  React.useEffect( () =>{
      apiClient.feedTagSearch(props.tags, 0).then((data)=>{
      setCards(data);
    });
  }, [props.tags])

  async function fetchMore() {
    setPageNumber(pageNumber + 1);
    console.log(pageNumber)
    apiClient.feedTagSearch(props.tags, pageNumber).then((data)=>{
      setCards([...cards, ...data])
    })
    console.log(cards)
  }

  function handleOpen(no) {
    setOpen(true);
    setFeedNo(no);
    setChange(false);
  }
  function handleClose() {
    setOpen(false);
    setChange(true);
  }

  const changeFunction = (check) => {
    setChange(check);
  };

  if (true) {
    return (
      <Fragment>
        <Grid container spacing={4}>
          {cards.map(card => (
              <Grid item key={card.feedNo} xs={12} sm={6} md={4}>
                <Card
                  sx={{
                    position: "relative",
                    height: "100%",
                    display: "flex",
                    flexDirection: "column",
                  }}
                  style={{
                    boxShadow:
                      "0 10px 35px rgba(0, 0, 0, 0.05), 0 6px 6px rgba(0, 0, 0, 0.5)",
                  }}
                  elevation={0}
                >
                  <Grid container mt={1}>
                    <Grid ml={1}>
                      <a
                        style={{ textDecoration: "none" }}
                        href={"/profile/" + card.memberNo}
                      >
                        <img
                          src={card.profileImageUrl}
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
                        {card.nickname}
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
                    }}
                    image={Object.values(card.images)[0]}
                    alt=""
                    onClick={() => handleOpen(card.feedNo)}
                  ></CardMedia>
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
                        feedNo={card.feedNo}
                      ></FeedLikeButton>
                    </Grid>
                    <Grid mr={-3}>
                      <FeedSaveButton
                        change={change}
                        feedNo={card.feedNo}
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
          ))}

        </Grid>
        <PlusButton handleCard={fetchMore}></PlusButton>
      </Fragment>
    );
  } else {
    return <div></div>;
  }
}
