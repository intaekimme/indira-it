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
import Swal from 'sweetalert2'
import withReactContent from 'sweetalert2-react-content'
const MySwal = withReactContent(Swal)

export default function CommentChild(props) {
  console.log(props);
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
    setContent(props.childComment.comment);
    if (sessionStorage.getItem("loginCheck"))
      setUser(parseInt(sessionStorage.getItem("loginMember")));
    
  }, []);

  function onChangeContent(event) {
    setContent(event.target.value);
  }

  //  댓글 수정
  const modifyComment = () => {
    setModify(!modify);
    if (modify) {
      const data = {
        content: content,
      };
      props.modifyCommentFunc(props.somethingNo, props.childComment.comment.reviewNo, data);
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
    Swal.fire({
      title: '삭제하시겠습니까?',
      showDenyButton: true,
      showCancelButton: true,
      confirmButtonText: '삭제',
      denyButtonText: `취소`,
    }).then((result) => {
      /* Read more about isConfirmed, isDenied below */
      if (result.isConfirmed) {
        Swal.fire('댓글이 삭제되었습니다', '', 'success')
        props.deleteCommentFunc(props.somethingNo, props.childComment.comment.reviewNo);
        setContent("삭제된 댓글입니다.");
        setDeleted(true);
      } 
    })
    // if (window.confirm("삭제하시겠습니까?")) {
    // }
  };

  //  댓글 등록 취소
  const cancelRegister = () => {
    setCancel(!cancel);
    setIsChildReviewRegister(!isChildReviewRegister);
  };
  
  return (
    <Card sx={{ maxWidth: "100%", m: 2 }}>
      <CardHeader
        avatar={
          <Avatar
            alt={props.childComment.nickname}
            src={props.childComment.profileImageUrl}
          />
        }
        subheader={props.childComment.nickmane}
        style={{ textAlign: "left" }}
      />
      <CardContent style={{ textAlign: "left" }}>
        <Typography variant="body1" color="textSecondary" component="p">
          {!props.childComment.isRemoved && !deleted ? (
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
            {props.childComment.memberNo === user &&
            !props.childComment.isRemoved &&
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
            {props.childComment.memberNo === user &&
            !props.childComment.isRemoved &&
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
      </Grid>
    </Card>
  );
}
