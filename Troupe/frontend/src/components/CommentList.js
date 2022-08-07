import React from "react";
import CommentCount from "./CommentCount";
import CommentForm from "./CommentForm";

export default function CommentList(props) {
  return (
    <div>
      <CommentCount />
      <CommentForm />
    </div>
  );
}
