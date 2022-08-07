import { Box } from "@mui/system";
import React from "react";

export default function CommentCount() {
  const [reviewCount, setReviewCount] = React.useState();

  return <Box>{reviewCount}</Box>;
}
