import React from "react";
import apiClient from "../apiClient";
import _MinhoCommentCard from "./_MinhoCommentCard";

import Modal from '@mui/material/Modal';
import Card from '@mui/material/Card';
import Box from '@mui/material/Box';
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Grid from "@mui/material/Grid";
import CardHeader from '@mui/material/CardHeader';
import CardMedia from '@mui/material/CardMedia';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ShareIcon from '@mui/icons-material/Share';
import MoreVertIcon from '@mui/icons-material/MoreVert';


import MenuBookRoundedIcon from '@mui/icons-material/MenuBookRounded';

import styledButton from "../css/button.module.css";


const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  overflow:'scroll',
};

export default function GuestBook(props) {
  const [existMyGuestBook, setExistMyGuestBook] = React.useState(false);
  const [myGuestBookContent, setMyGuestBookContent] = React.useState({});
  const [guestBookList, setGuestBookList] = React.useState([]);
  React.useEffect(()=>{
    if(!props.memberNo){
      return;
    }
    apiClient.getGuestBookList(props.memberNo).then((data)=>{
      console.log(data);
      setGuestBookList(data);
    });
  }, [props.memberNo]);

  const [open, setOpen] = React.useState(false);
  //방명록 모달 열기
  const handleOpen = () => {
    apiClient.getGuestBookList(props.memberNo).then((data) => {
      console.log(data);
      setGuestBookList(data);
    });
    apiClient.getMyGuestBook(props.memberNo).then((data) => {
      console.log(data);
      if(!data || data==="" || data.length==0){
        setExistMyGuestBook(false);
        setMyGuestBookContent(data);
      }
      else {
        console.log(data);
        setExistMyGuestBook(true);
        setMyGuestBookContent(data);
      }
    });
    setOpen(true);
  }
  //방명록 모달 닫기
  const handleClose = () => setOpen(false);

  //방명록 작성
  const registGuestBook = (event) => {
    event.preventDefault();
    const formData = new FormData(event.currentTarget);
    const content = formData.get("content");
    console.log(content);
    if (!content || content==='') {
      return;
    }
    apiClient.registGuestBook(props.memberNo, {content: content}).then(() => {
      apiClient.getGuestBookList(props.memberNo).then((data) => {
        console.log(data);
        setGuestBookList(data);
      });
      apiClient.getMyGuestBook(props.memberNo).then((data) => {
        console.log(data);
        if(!data || data==="" || data.length==0){
          setExistMyGuestBook(false);
          setMyGuestBookContent(data);
        }
        else {
          console.log(data);
          setExistMyGuestBook(true);
          setMyGuestBookContent(data);
        }
      });
    });
  };
  
  //방명록수정
  const modifyButton = (hostMemberNo, comment) => {
    console.log(hostMemberNo);
    apiClient.modifyGuestBook(hostMemberNo, comment).then(() => {
      apiClient.getGuestBookList(props.memberNo).then((data) => {
        console.log(data);
        setGuestBookList(data);
      });
      apiClient.getMyGuestBook(props.memberNo).then((data) => {
        console.log(data);
        if(!data || data==="" || data.length==0){
          setExistMyGuestBook(false);
          setMyGuestBookContent(data);
        }
        else {
          console.log(data);
          setExistMyGuestBook(true);
          setMyGuestBookContent(data);
        }
      });
    });
  };
  //방명록삭제
  const deleteButton = (hostMemberNo) => {
    console.log(hostMemberNo);
    apiClient.deleteGuestBook(hostMemberNo).then(() => {
      apiClient.getGuestBookList(props.memberNo).then((data) => {
        console.log(data);
        setGuestBookList(data);
      });
      apiClient.getMyGuestBook(props.memberNo).then((data) => {
        console.log(data);
        if(!data || data==="" || data.length==0){
          setExistMyGuestBook(false);
          setMyGuestBookContent(data);
        }
        else {
          console.log(data);
          setExistMyGuestBook(true);
          setMyGuestBookContent(data);
        }
      });
    });
  };
  return (
    <div>
      <Button
        className={styledButton.btn}
        onClick={handleOpen}
        style={{
          textAlign: "center",
          margin: 10,
          marginLeft: 15,
          position: "absolute",
          left: 0,
          bottom: 0,
          width: "100px",
          height: "100px",
          backgroundColor: "#66CC66",
        }}
      >
        <MenuBookRoundedIcon
          fontSize="large"
          sx={{ fontSize: "80px" }}
          style={{ color: "white" }}
        />
      </Button>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
        style={{ width: "100%", height: "100%" }}
      >
        <Box
          sx={style}
          className={style}
          style={{ padding: 20, width: "75%", height: "80%" }}
        >
          <Typography id="modal-modal-title" component="span">
            {sessionStorage.getItem("loginCheck") === "true" ? (
              existMyGuestBook ? (
                <Grid
                  container
                  item
                  xs={12}
                  style={{ padding: 10, marginBottom: 20 }}
                >
                  <Grid item xs={12}>
                    내가 쓴 방명록
                  </Grid>
                  <Grid item xs={12}>
                    <_MinhoCommentCard
                      kind="guestbook"
                      modifyButton={modifyButton}
                      deleteButton={deleteButton}
                      isRemoved={myGuestBookContent.removed}
                      comment={myGuestBookContent.content}
                      memberInfo={myGuestBookContent.visitorMemberInfoResponse}
                      no={myGuestBookContent.hostMemberInfoResponse.memberNo}
                      createdTime={myGuestBookContent.createdTime}
                    />
                  </Grid>
                </Grid>
              ) : (
                <form
                  onSubmit={registGuestBook}
                  encType="multipart/form-data"
                  style={{ textAlign: "center" }}
                >
                  <Grid
                    container
                    item
                    xs={12}
                    style={{ padding: 10, textAlign: "right" }}
                  >
                    <Grid item xs={12}>
                      <TextField
                        id="content"
                        name="content"
                        label="방명록"
                        multiline
                        rows={4}
                        placeholder="방명록을 작성해 주세요"
                        variant="outlined"
                        style={{
                          width: "100%",
                          marginBottom: "10px",
                        }}
                      />
                    </Grid>
                    <Grid item xs={12} style={{ textAlign: "right" }}>
                      <Button
                        type="submit"
                        className={styledButton.btn}
                        style={{
                          width: "100px",
                          height: "100%",
                          backgroundColor: "#66CC66",
                          color: "white",
                          alignContent: "right",
                        }}
                      >
                        방명록 작성
                      </Button>
                    </Grid>
                  </Grid>
                </form>
              )
            ) : (
              <Button
                onClick={() => {
                  sessionStorage.setItem("currentHref", window.location.href);
                  window.location.href = "/login";
                }}
              >
                로그인 후 방명록 작성
              </Button>
            )}
          </Typography>
          <Typography
            component="span"
            id="modal-modal-description"
            sx={{ mt: 2 }}
          >
            {/* <Grid
              container
              item
              xs={12}
              style={{ padding: 10, textAlign: "right" }}
            > */}
            <Grid container item xs={12} style={{ padding: 10 }}>
              <Grid item xs={12}>
                {props.memberNickname} 님의 방명록
              </Grid>
              {guestBookList.map((guestBook, index) => (
                <Grid key={`guestBook${index}`} item xs={12} style={{ margin: 10 }}>
                  <_MinhoCommentCard
                    key={`guestBook${index}`}
                    kind="guestbook"
                    modifyButton={modifyButton}
                    deleteButton ={deleteButton}
                    isRemoved={ guestBook.removed }
                    comment={guestBook.content}
                    memberInfo={guestBook.visitorMemberInfoResponse}
                    no={guestBook.hostMemberInfoResponse.memberNo}
                    createdTime={guestBook.createdTime}
                  />
                </Grid>
              ))}
            </Grid>
            {/* </Grid> */}
            {/* <Card>
              <CardHeader
                title={
                  existMyGuestBook ? (
                    <div>
                      <br />
                      기존에 썼던 방명록 (댓글폼 붙일예정)
                    </div>
                  ) : (
                    <TextField
                      id="outlined-multiline-static"
                      label="Multiline"
                      multiline
                      rows={4}
                      placeholder="방명록을 작성해 주세요"
                      variant="outlined"
                      style={{
                        width: "100%",
                        marginBottom: "10px",
                      }}
                    />
                  )
                }
                subheader={
                  sessionStorage.getItem("loginCheck") === "true" ? (
                    <div></div>
                  ) : existMyGuestBook ? (
                    <div> </div>
                  ) : (
                    <Grid item xs={12} style={{ textAlign: "right" }}>
                      <Button
                        type="submit"
                        className={styledButton.btn}
                        style={{
                          width: "100px",
                          height: "100%",
                          backgroundColor: "#45E3C6",
                          color: "black",
                          alignContent: "right",
                        }}
                      >
                        방명록 작성
                      </Button>
                    </Grid>
                  )
                }
              />
              <CardMedia
                image="/static/images/cards/paella.jpg"
                title="Paella dish"
              />
              <CardContent>
                <Typography
                  variant="body2"
                  color="textSecondary"
                  component="span"
                >
                  방명록 목록들
                </Typography>
              </CardContent>
              <CardActions disableSpacing>
                <IconButton aria-label="add to favorites">
                  <FavoriteIcon />
                </IconButton>
                <IconButton aria-label="share">
                  <ShareIcon />
                </IconButton>
              </CardActions>
            </Card> */}
          </Typography>
        </Box>
      </Modal>
    </div>
  );
}