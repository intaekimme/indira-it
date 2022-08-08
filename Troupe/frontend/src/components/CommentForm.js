import React from "react";
import CreateIcon from "@mui/icons-material/Create";
import { IconButton, TextField } from "@mui/material";
import apiClient from "../apiClient";

export default function CommentForm() {
  const [review, setReview] = React.useState();

  const reset = () => setReview();

  const reviewRegister = (event) => {
    apiClient.perfReviewNew();
  };

  const WriteButton = () => (
    <IconButton onClick={reviewRegister}>
      <CreateIcon color="gray" />
    </IconButton>
  );

  return (
    <TextField
      fullWidth
      id="write-review-form"
      placeholder="댓글을 입력하세요."
      value={review}
      type="text"
      maxlength="500"
      InputProps={{ endAdornment: <WriteButton /> }}
    />
  );
}
