import React, { useEffect, useState } from "react";
import CreateIcon from "@mui/icons-material/Create";
import { IconButton, TextField } from "@mui/material";
import apiClient from "../apiClient";

export default function CommentForm(props) {
  const [review, setReview] = useState("");
  const [isChild, setIsChild] = useState(props.isChild);

  useEffect(() => {
    console.log(props.isChild);
  }, []);

  const reset = () => setReview("");

  //  답글 폼 변화 인식
  const onChange = (event) => {
    setReview(event.target.value);
  };

  const reviewRegister = () => {
    const data = {
      content: review,
    };
    apiClient.perfReviewNew(props.performanceNo, data, props.refreshFunction);
    reset();
  };

  const childReviewRegister = () => {
    const data = {
      content: review,
    };
    apiClient.perfChildReviewNew(
      props.performanceNo,
      props.reviewNo,
      data,
      props.refreshFunction
    );
    reset();
    setIsChild(false);
  };

  const WriteButton = () => (
    <IconButton onClick={isChild ? childReviewRegister : reviewRegister}>
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
