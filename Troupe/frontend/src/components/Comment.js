import React, { useEffect, useState } from "react";
import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import CardContent from "@mui/material/CardContent";
import CardActions from "@mui/material/CardActions";
import Avatar from "@mui/material/Avatar";
import IconButton from "@mui/material/IconButton";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import apiClient from "../apiClient";
import CommentForm from "./CommentForm";
import { Grid } from "@mui/material";
import TextField from "@mui/material/TextField";
export default function Comment(props) {
  const [user, setUser] = useState(0);
  const [childComments, setchildComments] = useState([]);
  const [isChild, setIsChild] = useState(false);
  const [isChildReviewRegister, setIsChildReviewRegister] = useState(false);

  const [content, setContent] = useState("");
  const [modify, setModify] = useState(false);
  const [deleted, setDeleted] = useState(false);

  useEffect(() => {
    setContent(props.comment);
    if (sessionStorage.getItem("loginCheck"))
      setUser(parseInt(sessionStorage.getItem("loginMember")));
    if (!props.feedNo) {
      apiClient
        .getPerfChildReviewList(props.performanceNo, props.reviewNo)
        .then((data) => {
          console.log(data);
          // setchildComment(data);
        });
    }
  }, []);

  function onChangeContent(event) {
    setContent(event.target.value);
  }
  //  답글 작성 클릭
  const childCommentRegister = (event) => {
    if (!sessionStorage.getItem("loginCheck")) {
      if (window.confirm("로그인이 필요합니다. 로그인 하시겠습니까?"))
        window.location.href = `/login`;
    } else {
      setIsChildReviewRegister(true);
    }
  };

  const modifyComment = () => {
    setModify(!modify);
    if (modify) {
      // axios
      const data = {
        content: content,
      };
      apiClient.feedCommentModify(
        props.feedNo,
        props.reviewNo,
        data,
        props.refreshFunction,
      );
      // .then(() => {})
      // .catch(() => {
      // });
      setModify(!modify);
    }
  };
  const modifyCancel = () => {
    setContent(props.comment);
    setModify(!modify);
  };
  const deleteComment = () => {
    if (window.confirm("삭제하시겠습니까?")) {
      apiClient.feedCommentDelete(props.feedNo, props.reviewNo).then(() => {
        setContent("삭제된 댓글입니다.");
        setDeleted(true);
      });
    }
  };
  // console.log(props);
  // console.log(user);
  // console.log(childComments);

  return (
    <Card sx={{ maxWidth: "100%" }}>
      <CardHeader
        avatar={<Avatar alt={props.nickname} src={props.profileImageUrl} />}
        subheader={props.nickname}
        style={{ textAlign: "left" }}
      />
      <CardContent style={{ textAlign: "left" }}>
        <Typography variant="body1" color="textSecondary" component="p">
          {!props.isRemoved && !deleted ? (
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
            <div>삭제된 댓글입니다</div>
          )}
        </Typography>
      </CardContent>
      {props.memberNo === user ? (
        <Grid container justifyContent="flex-end">
          <Grid item>
            {!props.isRemoved && !deleted ? (
              modify ? (
                <div>
                  <Button
                    size="small"
                    aria-label="modify"
                    onClick={() => modifyCancel()}
                  >
                    수정 취소
                  </Button>
                  <Button
                    size="small"
                    aria-label="modify"
                    onClick={() => modifyComment()}
                  >
                    수정 완료
                  </Button>
                </div>
              ) : (
                <Button
                  size="small"
                  aria-label="modify"
                  onClick={() => modifyComment()}
                >
                  수정
                </Button>
              )
            ) : (
              <div></div>
            )}
            {!props.isRemoved && !deleted ? (
              <Button
                size="small"
                aria-label="delete"
                onClick={() => deleteComment()}
              >
                삭제
              </Button>
            ) : (
              <div></div>
            )}
            <Button size="small" aria-label="child-comment">
              답글({childComments.length})
            </Button>
            {!props.isRemoved && !deleted ? (
              <Button
                size="small"
                arial-lebel="child-comment-register"
                onClick={childCommentRegister}
              >
                답글작성
              </Button>
            ) : (
              <div></div>
            )}
          </Grid>
          {isChildReviewRegister && (
            <Grid item>
              <CommentForm
                refreshFunction={props.refreshFunction}
                isChild={isChildReviewRegister}
                performanceNo={props.performanceNo}
                reviewNo={props.reviewNo}
              />
            </Grid>
          )}
        </Grid>
      ) : (
        <Grid container justifyContent="flex-end">
          <Grid item>
            <Button size="small" aria-label="child-comment">
              답글({childComments.length})
            </Button>
            <Button
              size="small"
              arial-lebel="child-comment-register"
              onClick={childCommentRegister}
            >
              답글작성
            </Button>
          </Grid>
          {isChildReviewRegister && (
            <Grid item>
              <CommentForm
                refreshFunction={props.refreshFunction}
                isChild={isChildReviewRegister}
                performanceNo={props.performanceNo}
                reviewNo={props.reviewNo}
              />
            </Grid>
          )}
        </Grid>
      )}
    </Card>
  );
}
