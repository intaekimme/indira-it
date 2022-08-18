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
        // console.log(props.kind);
        return (
          <Comment
            key={index}
            kind={props.kind}
            performanceNo={comment.pfNo}
            feedNo={props.feedNo}
            memberNo={comment.memberNo}
            reviewNo={comment.reviewNo}
            nickname={comment.nickname}
            profileImageUrl={comment.profileImageUrl}
            comment={comment.comment}
            childComments={comment.childComments}
            isRemoved={comment.isRemoved}
            parentCommentNo={props.parentCommentNo}
            resetFunction={props.resetFunction}
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
          kind={props.kind}
        />
      )}
    </div>
  );
}
