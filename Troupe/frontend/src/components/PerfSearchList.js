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
import PlusButton from "./PlusButton";
import PerfSaveButton from "./PerfSaveButton";

export default function PerfSearchList(props) {
  let performanceSearchListQuery = useQuery(
    "performanceSearchList",
    async () =>
      await apiClient.perfSearch({
        condition: props.condition,
        keyword: props.keyword,
      }),
  );
  let pageNumber = useParams().pageNumber;

  console.log(performanceSearchListQuery.data);
  console.log(performanceSearchListQuery.isLoading);

  const [change, setChange] = React.useState(false);
  const [isHover, setIsHover] = React.useState(-1);

  if (props.howManySearch > 0) {
    performanceSearchListQuery.refetch();
    props.setHowManySearch(0);
  }

  if (
    !performanceSearchListQuery.isLoading &&
    typeof performanceSearchListQuery.data
  ) {
    return (
      <Grid container spacing={4} paddingBottom="300px">
        {performanceSearchListQuery.data.map((datum) => (
          <Grid item key={datum.pfNo} xs={12} sm={6} md={4}>
            <Card
              sx={{
                position: "relative",
                height: "100%",
                display: "flex",
                flexDirection: "column",
              }}
              elevation={0}
              style={{
                fontSize: "16px",
                fontFamily: "SBAggroB",
                color: "black",
                boxShadow:
                  "0 10px 35px rgba(0, 0, 0, 0.05), 0 6px 6px rgba(0, 0, 0, 0.5)",
              }}
            >
              {/* <Typography gutterBottom style={{fontSize:'20px', fontFamily:'IBM Plex Sans KR'}} component="span">
                    <img src={data.image} alt='' style={{borderRadius:'70%', objectFit:'cover', height:'20px', width:'20px'}}></img>
                  </Typography> */}
              <Box
                style={{
                  background: "#66cc66",
                  borderRadius: "10%",
                  position: "absolute",
                  top: "10px",
                  right: "5px",
                  border: "2px solid white",
                  i: "3",
                  padding: "2px",
                  boxShadow:
                    "0 10px 35px rgba(0, 0, 0, 0.05), 0 6px 6px rgba(0, 0, 0, 0.1)",
                  color: "white",
                }}
              >
                {datum.status}
              </Box>
              <Box
                style={{
                  background: "#ffd400",
                  borderRadius: "10%",
                  position: "absolute",
                  border: "2px solid white",
                  top: "10px",
                  left: "5px",
                  i: "3",
                  padding: "2px",
                  boxShadow:
                    "0 10px 35px rgba(0, 0, 0, 0.05), 0 6px 6px rgba(0, 0, 0, 0.1)",
                  color: "white",
                }}
              >
                {datum.category}
              </Box>
              <Link href={`/perf/detail/${datum.pfNo}`}>
                <CardMedia
                  component="img"
                  style={{
                    pb: 1,
                    objectFit: "cover",
                    width: "340px",
                    height: "300px",
                    opacity: isHover === datum.pfNo ? 0.5 : null,
                    transform: isHover === datum.pfNo ? "scale(1.1)" : null,
                    transition: "0.5s",
                  }}
                  image={Object.values(datum.image)[0]}
                  alt=""
                  onMouseEnter={() => setIsHover(datum.pfNo)}
                  onMouseLeave={() => setIsHover(-1)}
                ></CardMedia>
                {isHover === datum.pfNo ? (
                  <div
                    style={{
                      position: "absolute",
                      height: "300px",
                      width: "280px",
                      color: "black",
                      top: '100px',
                      right:'15px'
                    }}
                    onMouseEnter={() => setIsHover(datum.pfNo)}
                    onMouseLeave={() => setIsHover(-1)}
                  >
                    <ul style={{ listStyleType: "none" }}>
                      <li># 공연제목:{datum.title}</li>
                      <li># 공연장소:{datum.location}</li>
                    </ul>
                  </div>
                ) : null}
              </Link>
              <CardActions
                sx={{
                  justifyContent: "space-between",
                  margin: "5px",
                  padding: "0px",
                }}
              >
                <Grid></Grid>
                <Grid mr={-3}>
                  <PerfSaveButton
                    change={change}
                    pfNo={datum.pfNo}
                  ></PerfSaveButton>
                </Grid>
              </CardActions>
            </Card>
          </Grid>
        ))}
        <PlusButton
          handleCard={performanceSearchListQuery.fetchNextPage}
        ></PlusButton>
      </Grid>
    );
  }
}
