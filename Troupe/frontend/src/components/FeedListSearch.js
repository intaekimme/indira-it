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
  // console.log(FeedListSearchQuery.data);
  // console.log(FeedListSearchQuery.isLoading);
  const [change, setChange] = React.useState(false);

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
                  style={{
                    boxShadow:
                      "0 10px 35px rgba(0, 0, 0, 0.05), 0 6px 6px rgba(0, 0, 0, 0.5)",
                  }}
                  elevation={0}
                >
                  <Grid container mt={1}>
                    <Grid ml={1}>
                      <a
                        style={{ textDecoration: "none" }}
                        href={"/profile/" + datum.memberNo}
                      >
                        <img
                          src={datum.profileImageUrl}
                          alt=""
                          style={{
                            borderRadius: "70%",
                            objectFit: "cover",
                            height: "30px",
                            width: "30px",
                            boxShadow:
                              "0 10px 35px rgba(0, 0, 0, 0.05), 0 6px 6px rgba(0, 0, 0, 0.1)",
                          }}
                        ></img>
                      </a>
                    </Grid>
                    <Grid ml={1} mb={2}>
                      <Typography
                        style={{
                          fontSize: "13px",
                          fontFamily: "SBAggroB",
                          wordBreak: "break-all",
                          overflow: "hidden",
                        }}
                        component="span"
                      >
                        {datum.nickname}
                      </Typography>
                    </Grid>
                  </Grid>
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
                      margin: "5px",
                      padding: "0px",
                    }}
                  >
                    <Grid>
                      <FeedLikeButton
                        change={change}
                        feedNo={datum.feedNo}
                      ></FeedLikeButton>
                    </Grid>
                    <Grid mr={-3}>
                      <FeedSaveButton
                        change={change}
                        feedNo={datum.feedNo}
                      ></FeedSaveButton>
                    </Grid>
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
