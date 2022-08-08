import React, { useEffect, useState } from "react";
import apiClient from "../apiClient";
import CommentCount from "./CommentCount";
import CommentForm from "./CommentForm";

export default function CommentList(props) {
  const [reviewList, setReviewList] = useState([]);

  useEffect(() => {
    setReviewList(apiClient.getPerfList());
    console.log(reviewList);
  }, []);

  return (
    <div>
      <CommentCount />
      <CommentForm />
    </div>
  );
}
