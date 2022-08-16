import React, { useEffect, useState } from "react";
import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import CardContent from "@mui/material/CardContent";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import apiClient from "../apiClient";
import CommentForm from "./CommentForm";
import { Grid } from "@mui/material";
import TextField from "@mui/material/TextField";
import CommentList from "./CommentList";
import ModeEditIcon from "@mui/icons-material/ModeEdit";
import DeleteIcon from "@mui/icons-material/Delete";
import ReplyIcon from "@mui/icons-material/Reply";
import FormatListBulletedIcon from "@mui/icons-material/FormatListBulleted";
import CancelOutlinedIcon from "@mui/icons-material/CancelOutlined";
import SendIcon from "@mui/icons-material/Send";
import CommentChild from "./CommentChild";

export default function CommentTest(props) {
  const [user, setUser] = useState(0);
  // 대댓글 목록
  const [childComments, setchildComments] = useState([]);
  // const [isChild, setIsChild] = useState(false);
  // 대댓글 목록 가시 여부
  const [isChildReviewShow, setIsChildReviewShow] = useState(false);
  // 대댓글 등록 여부
  const [isChildReviewRegister, setIsChildReviewRegister] = useState(false);

  const [content, setContent] = useState("");
  const [modify, setModify] = useState(false);
  const [deleted, setDeleted] = useState(false);
  // 대댓글 등록 취소 여부
  const [cancel, setCancel] = useState(false);

  useEffect(() => {
    setContent(props.data.comment.comment);
    setchildComments(props.data.comment.childComments);
    if (sessionStorage.getItem("loginCheck"))
      setUser(parseInt(sessionStorage.getItem("loginMember")));
    
  }, []);

  function onChangeContent(event) {
    setContent(event.target.value);
  }
  //  답글 작성 클릭
  const childCommentRegister = (event) => {
    setCancel(!cancel);
    setIsChildReviewShow(true);
    if (!sessionStorage.getItem("loginCheck")) {
      if (window.confirm("로그인이 필요합니다. 로그인 하시겠습니까?"))
        window.location.href = `/login`;
    } else {
      setIsChildReviewRegister(true);
    }
  };
  // 대댓글 refresh
  const refreshChildFunction = (newComment) => {
    setchildComments([...childComments, newComment]);
  };
  // 대댓글 리스트 가시 여부
  const childCommentList = () => {
    setIsChildReviewShow(!isChildReviewShow);
  };

  //  댓글 수정
  const modifyComment = () => {
    setModify(!modify);
    if (modify) {
      const data = {
        content: content,
      };
      props.modifyCommentFunc(props.somethingNo, props.data.comment.reviewNo, data);
      setModify(!modify);
    }
  };

  //  수정 취소
  const modifyCancel = () => {
    setContent(props.comment);
    setModify(!modify);
  };

  //  댓글 삭제
  const deleteComment = () => {
    if (window.confirm("삭제하시겠습니까?")) {
      props.deleteCommentFunc(props.somethingNo, props.data.comment.reviewNo);
      setContent("삭제된 댓글입니다.");
      setDeleted(true);
    }
  };

  //  댓글 등록 취소
  const cancelRegister = () => {
    setCancel(!cancel);
    setIsChildReviewRegister(!isChildReviewRegister);
  };

  // console.log(props);
  // console.log(`props.memberNo : ${props.memberNo}

  // !props.isRemoved : ${!props.isRemoved}
  // !deleted : ${!deleted}
  // modify : ${modify}
  // childComments : ${childComments}
  // !props.parentCommentNo : ${!props.parentCommentNo}
  // !cancel : ${!cancel}
  // isChildReviewRegister : ${isChildReviewRegister}
  // isChild : ${isChild}
  // `);
  // console.log(content);
  // console.log(childComments);
  
  return (
    <Card sx={{ maxWidth: "100%", m: 2 }}>
      <CardHeader
        avatar={
          <Avatar
            alt={props.isChildComment ?
              props.childComment.data.comment.nickmane : props.data.comment.nickname}
            src={props.isChildComment ?
              props.childComment.data.comment.profileImageUrl : props.data.comment.profileImageUrl}
          />
        }
        subheader={props.isChildComment ?
          props.childComment.data.comment.nickmane : props.data.comment.nickname}
        style={{ textAlign: "left" }}
      />
      <CardContent style={{ textAlign: "left" }}>
        <Typography variant="body1" color="textSecondary" component="p">
          {!props.data.comment.isRemoved && !deleted ? (
            modify ? (
              <TextField
                fullWidth
                id="write-review-form"
                placeholder="댓글을 입력하세요."
                value={content}
                type="text"
                maxLength="500"
                onChange={onChangeContent}
              />
            ) : (
              <div>{content}</div>
            )
          ) : (
            <div style={{ fontSize: "12px" }}>삭제된 댓글입니다.</div>
          )}
        </Typography>
      </CardContent>

      <Grid>
        <Grid container justifyContent="flex-end">
          <Grid item>
            {/* {props.memberNo === user && !props.isRemoved && !deleted ? ( */}
            {props.data.comment.memberNo === user &&
            !props.data.comment.isRemoved &&
            !deleted ? (
              modify ? (
                <div>
                  <Button
                    size="small"
                    aria-label="modify"
                    onClick={() => modifyCancel()}
                  >
                    <CancelOutlinedIcon color="action"></CancelOutlinedIcon>
                  </Button>
                  <Button
                    size="small"
                    aria-label="modify"
                    onClick={() => modifyComment()}
                  >
                    <SendIcon color="action"></SendIcon>
                  </Button>
                </div>
              ) : (
                <Button
                  size="small"
                  aria-label="modify"
                  onClick={() => modifyComment()}
                >
                  <ModeEditIcon color="action"></ModeEditIcon>
                </Button>
              )
            ) : (
              <div></div>
            )}
            {props.data.comment.memberNo === user &&
            !props.data.comment.isRemoved &&
            !deleted ? (
              <Button
                size="small"
                aria-label="delete"
                onClick={() => deleteComment()}
              >
                <DeleteIcon color="action"></DeleteIcon>
              </Button>
            ) : (
              <div></div>
            )}
            {!props.parentCommentNo && (
              <Button
                size="small"
                aria-label="child-comment"
                onClick={childCommentList}
                style={{ color: "black" }}
                // color="black"
                fontFamily="116watermelon"
              >
                <FormatListBulletedIcon color="action"></FormatListBulletedIcon>
                <Grid style={{ marginTop: "3px", marginLeft: "3px" }}>
                  ({childComments.length})
                </Grid>
              </Button>
            )}

            {!props.data.comment.isRemoved &&
            !deleted &&
            !props.data.comment.parentCommentNo &&
            !cancel ? (
              <Button
                size="small"
                arial-lebel="child-comment-register"
                onClick={childCommentRegister}
              >
                <ReplyIcon color="action"></ReplyIcon>
              </Button>
            ) : (
              <span></span>
            )}
            {cancel ? (
              <Button
                size="small"
                arial-lebel="child-comment-register"
                onClick={cancelRegister}
              >
                <CancelOutlinedIcon color="action"></CancelOutlinedIcon>
              </Button>
            ) : (
              <span></span>
            )}
          </Grid>
        </Grid>
        {isChildReviewRegister && (
          <Grid item>
            <CommentForm
              refreshChildFunction={refreshChildFunction}
              isChild={isChildReviewRegister}
              performanceNo={props.data.comment.pfNo}
              feedNo={props.feedNo}
              parentCommentNo={props.data.comment.reviewNo}
            />
          </Grid>
        )}
        {isChildReviewShow && (childComments.map((item, index) => {
          console.log(item);  
          return (
            <CommentChild
              refreshChildFunction={refreshChildFunction}
              childComment={item}
            />)
        }))
        }
      </Grid>
    </Card>
  );
}
