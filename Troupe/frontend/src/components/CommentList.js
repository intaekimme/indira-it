import axios from "axios";
import React, { useEffect, useState } from "react";
import apiClient from "../apiClient";
import CommentCount from "./CommentCount";
import CommentForm from "./CommentForm";
import Comment from "./Comment";

export default function CommentList({ performance }) {
  const [comments, setComments] = useState([]);
  const [isSubmit, setIsSubmit] = useState(false);

  // const isSubmit = (val) => {
  //   console.log(val);
  // };

  useEffect(() => {
    let completed = false;

    async function fetchData() {
      const response = await apiClient.getPerfCommentList(performance);
      console.log(response);
      if (!completed) setComments(response);
      console.log(comments);
    }
    fetchData();

    return () => {
      completed = true;
    };
  }, []);

  return (
    <div>
      <CommentCount listSize={comments.length} />
      <Comment setIsSubmit={setIsSubmit} />;
      {/* {comments.map((comment) => {
        return <Comment isSubmit={isSubmit} />;
      })} */}
      <CommentForm />
    </div>
  );
}
