import React from "react";
import CreateIcon from "@mui/icons-material/Create";
import { IconButton, TextField } from "@mui/material";
import apiClient from "../apiClient";

export default function CommentForm(props) {
  const [review, setReview] = React.useState();

  const reset = () => setReview();
  const onChange = (event) => {
    setReview(event.target.value);
  };

  const reviewRegister = () => {
    const data = {
      content: review,
    };
    apiClient.perfCommentNew(2, data, props.refreshFunction);
    reset();
  };

  const WriteButton = () => (
    <IconButton onClick={reviewRegister}>
      <CreateIcon color="gray" />
    </IconButton>
  );

  return (
    <form>
      <TextField
        fullWidth
        id="write-review-form"
        placeholder="댓글을 입력하세요."
        value={review}
        type="text"
        maxlength="500"
        onChange={onChange}
        InputProps={{ endAdornment: <WriteButton /> }}
      />
    </form>
  );
}
