import { Box } from "@mui/system";
import React, { useEffect } from "react";

export default function CommentCount({ listSize }) {
  const [reviewCount, setReviewCount] = React.useState();

  useEffect(() => {
    setReviewCount(listSize);
    console.log(reviewCount);
  }, []);

  return <Box>{reviewCount}</Box>;
}
