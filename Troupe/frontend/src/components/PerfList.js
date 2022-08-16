import React from "react";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardMedia from "@mui/material/CardMedia";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import Link from "@mui/material/Link";
import { useInfiniteQuery } from "react-query";
import apiClient from "../apiClient";
import { useParams } from "react-router-dom";
import PlusButton from "./PlusButton";
import PerfSaveButton from "./PerfSaveButton";

export default function PerfListCard() {
  // let {isLoading, data} = useQuery('performanceList', async () => await apiClient.getPerfList(pageNumber));
  let pageNumber = useParams().pageNumber;
  let performanceListQuery = useInfiniteQuery(
    "performanceList",
    apiClient.getPerfList,
    {
      getNextPageParam: (lastPage, pages) => {
        return pages.length + 1;
      },
    },
  );
  console.log(performanceListQuery.data);
  console.log(performanceListQuery.isLoading);

  const [change, setChange] = React.useState(false);
  const [isHover, setIsHover] = React.useState(-1);

  const changeFunction = (check) => {
    setChange(check);
  };

  if (!performanceListQuery.isLoading && performanceListQuery.data.pages[0]) {
    return (
      <Grid container spacing={4}>
        {performanceListQuery.data.pages.map((page) =>
          page.map((datum) => (
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
                      width: "300px",
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
                        lineHeight: "300px",
                        position: "absolute",
                        height: "300px",
                        width: "265px",
                        color: "black",
                        top: 0,
                      }}
                      onMouseEnter={() => setIsHover(datum.pfNo)}
                      onMouseLeave={() => setIsHover(-1)}
                    >
                      <ul style={{listStyleType:'none'}}>
                        <li># 공연제목:{datum.title}</li>
                        <li># 공연장소:{datum.location}</li>
                        <li># 공연기간:{datum.detailTime}</li>
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
          )),
        )}
        <PlusButton
          handleCard={performanceListQuery.fetchNextPage}
        ></PlusButton>
      </Grid>
    );
  }
}
