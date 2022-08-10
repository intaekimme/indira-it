import axios from "axios";
import React, { useEffect, useState } from "react";
import CommentCount from "./CommentCount";
import CommentForm from "./CommentForm";
import Comment from "./Comment";

export default function CommentList(props) {
  console.log(props);

  const comments = props.commentList;
  console.log(comments);
  return (
    <div>
      <CommentCount listSize={props.commentList.length} />
      {comments.map((comment, index) => {
        return (
          <Comment
            key={index}
            memberNo={comment.memberNo}
            reviewNo={comment.reviewNo}
            nickname={comment.nickname}
            profileImageUrl={comment.profileImageUrl}
            comment={comment.comment}
          />
        );
      })}
      <CommentForm refreshFunction={props.refreshFunction} />
    </div>
  );
}
