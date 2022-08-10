import axios from "axios";
import React, { useEffect, useState } from "react";
import CommentCount from "./CommentCount";
import CommentForm from "./CommentForm";
import Comment from "./Comment";

export default function CommentList(props) {
  console.log(props);

  const comments = props.commentList;
  return (
    <div>
      <CommentCount listSize={props.commentList.length} />
      {comments.map((comment, index) => {
        return (
          <Comment
            key={index}
            performanceNo={comment.pfNo}
            memberNo={comment.memberNo}
            reviewNo={comment.reviewNo}
            nickname={comment.nickname}
            profileImageUrl={comment.profileImageUrl}
            comment={comment.comment}
            refreshFunction={props.refreshFunction}
          />
        );
      })}
      <CommentForm
        refreshFunction={props.refreshFunction}
        performanceNo={props.performanceNo}
      />
    </div>
  );
}
