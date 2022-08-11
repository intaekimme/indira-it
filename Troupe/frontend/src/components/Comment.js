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

export default function Comment(props) {
  const [user, setUser] = useState(0);
  const [childComments, setchildComments] = useState([]);
  const [isChild, setIsChild] = useState(false);
  const [isChildReviewRegister, setIsChildReviewRegister] = useState(false);

  useEffect(() => {
    // console.log(props);
    if (sessionStorage.getItem("loginCheck"))
      setUser(parseInt(sessionStorage.getItem("loginMember")));

    apiClient
      .getPerfChildReviewList(props.performanceNo, props.reviewNo)
      .then((data) => {
        console.log(data);
        // setchildComment(data);
      });
  }, []);

  //  답글 작성 클릭
  const childCommentRegister = (event) => {
    if (!sessionStorage.getItem("loginCheck")) {
      if (window.confirm("로그인이 필요합니다. 로그인 하시겠습니까?"))
        window.location.href = `/login`;
    } else {
      setIsChildReviewRegister(true);
    }
  };

  // console.log(props);
  // console.log(user);
  console.log(childComments);

  return (
    <Card sx={{ maxWidth: 345 }}>
      <CardHeader
        avatar={<Avatar alt={props.nickname} src={props.profileImageUrl} />}
        subheader={props.nickname}
      />
      <CardContent>
        <Typography variant="body1" color="textSecondary" component="p">
          {props.comment}
        </Typography>
      </CardContent>
      {props.memberNo === user ? (
        <Grid container>
          <Grid item>
            <Button size="small" aria-label="modify">
              수정
            </Button>
            <Button size="small" aria-label="delete">
              삭제
            </Button>
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
      ) : (
        <Grid container>
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
