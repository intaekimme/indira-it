import React from "react";
import { Button } from "@mui/material";
import TurnedInNotIcon from "@mui/icons-material/TurnedInNot";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";
import TurnedInIcon from "@mui/icons-material/TurnedIn";
import Favorite from "@mui/icons-material/Favorite";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardMedia from "@mui/material/CardMedia";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import Link from "@mui/material/Link";
import { useInfiniteQuery, useQuery } from "react-query";
import apiClient from "../apiClient";
import { useParams } from "react-router-dom";
import FeedSaveButton from "./FeedSaveButton";
import FeedLikeButton from "./FeedLikeButton";
import { Typography } from "@mui/material";
import Modal from "@mui/material/Modal";
import stylesModal from "../css/modal.module.css";
import { Fragment } from "react";
import PlusButton from "./PlusButton";

export default function FeedListSearch(props) {
  // [searchTags, setSearchTags] = React.useState(props.tags)
  
  let FeedListSearchQuery = useInfiniteQuery(
    ["tagSearch"],
    () => apiClient.feedTagSearch({ pageParam: 0, tags: props.tags }),
    {
      retry: true,
      getNextPageParam: (lastPage, pages) => {
        return pages.length + 1;
      },
    },
  );
  console.log(FeedListSearchQuery.data);
  console.log(FeedListSearchQuery.isLoading);
  if (props.howManySearch > 1) {
    FeedListSearchQuery.refetch({ refetchPage: (page, index) => index === 0 })
  }

  if (!FeedListSearchQuery.isLoading) {
    console.log(FeedListSearchQuery.data);
    return (
      <Fragment>
        <Grid container spacing={4}>
          {FeedListSearchQuery.data.pages.map((page) =>
            page.map((datum) => (
              <Grid item key={datum.feedNo} xs={12} sm={6} md={4}>
                <Card
                  sx={{
                    position: "relative",
                    height: "100%",
                    display: "flex",
                    flexDirection: "column",
                  }}
                  elevation={0}
                >
                  <Typography
                    gutterBottom
                    style={{ fontSize: "20px" }}
                    component="span"
                  >
                    <img
                      src={datum.profileImageUrl}
                      alt=""
                      style={{
                        borderRadius: "70%",
                        objectFit: "cover",
                        height: "20px",
                        width: "20px",
                      }}
                    ></img>
                    {datum.nickname}
                  </Typography>
                  <Link href={"/feed/test"}>
                    <CardMedia
                      component="img"
                      sx={{
                        pb: 1,
                        objectFit: "cover",
                        width: "300px",
                        height: "300px",
                      }}
                      image={Object.values(datum.images)[0]}
                      alt=""
                    ></CardMedia>
                  </Link>
                  <CardActions
                    sx={{
                      justifyContent: "space-between",
                      margin: "0px",
                      padding: "0px",
                    }}
                  >
                    <FeedLikeButton></FeedLikeButton>
                    <FeedSaveButton></FeedSaveButton>
                  </CardActions>
                </Card>
              </Grid>
            )),
          )}
        </Grid>

        <PlusButton handleCard={() => FeedListSearchQuery.fetchNextPage} disabled={!FeedListSearchQuery.hasNextPage}></PlusButton>
      </Fragment>
    );
  }
}
