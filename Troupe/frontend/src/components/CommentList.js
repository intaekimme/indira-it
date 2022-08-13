import axios from "axios";
import React, { useEffect, useState } from "react";
import CommentCount from "./CommentCount";
import CommentForm from "./CommentForm";
import Comment from "./Comment";

export default function CommentList(props) {
  // console.log(props);

  const comments = props.commentList;
  // console.log(comments);
  return (
    <div>
      <CommentCount listSize={props.commentList.length} />
      {comments.map((comment, index) => {
        return (
          <Comment
            key={index}
            performanceNo={comment.pfNo}
            feedNo={props.feedNo}
            memberNo={comment.memberNo}
            reviewNo={comment.reviewNo}
            nickname={comment.nickname}
            profileImageUrl={comment.profileImageUrl}
            comment={comment.comment}
            isRemoved={comment.isRemoved}
            parentCommentNo={props.parentCommentNo}
            refreshFunction={props.refreshFunction}
            refreshChildFunction={props.refreshChildFunction}
          />
        );
      })}
      {!props.parentCommentNo && (
        <CommentForm
          refreshFunction={props.refreshFunction}
          feedNo={props.feedNo}
          performanceNo={props.performanceNo}
        />
      )}
    </div>
  );
}
