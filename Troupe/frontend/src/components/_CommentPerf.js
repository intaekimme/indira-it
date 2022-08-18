import React, { useEffect } from "react";
import apiClient from "../apiClient";
import CommentTest from "./CommentTest";

export default function CommentPerf(props) {
  const modifyCommentFunc = (somethingNo, reviewNo, data) => {
    apiClient.perfReviewModify(somethingNo, reviewNo, data);
  };

  const deleteCommentFunc = (somethingNo, reviewNo) => {
    apiClient.perfReviewDelete(somethingNo, reviewNo);
  };

  return (
    <CommentTest
      data={props}
      modifyCommentFunc={modifyCommentFunc}
      deleteCommentFunc={deleteCommentFunc}
      somethingNo={props.comment.pfNo}
    ></CommentTest>
  );
}
