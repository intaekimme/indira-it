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
import { textAlign } from "@mui/system";

export default function _MinhoPerfList(props) {
  //카드이미지 width
  const [cardWidth, setCardWidth] = React.useState(
    window.innerWidth / 4 + window.innerWidth / 20
  );

  //1000보다 큰경우 2화면분할
  const handleCardWidth = () => {
    const size = window.innerWidth / 4 + window.innerWidth / 20;
    setCardWidth(size);
  };
  //화면분할 update
  React.useEffect(() => {
    window.addEventListener("resize", handleCardWidth);
  }, [window.innerWidth]);

  const [cards, setCards] = React.useState([]);
  const [pageNumber, setPageNumber] = React.useState(1);
  const [change, setChange] = React.useState(false);
  const [isHover, setIsHover] = React.useState(-1);

  React.useEffect(() => {
    if (props.memberInfo) {
      console.log(props.memberInfo.memberNo);
      apiClient
        .getMemberPerfList(0, props.memberInfo.memberNo, props.string)
        .then((data) => {
          console.log(data);
          setCards(data);
        });
    }
  }, [props.memberInfo]);

  async function fetchMore() {
    console.log(pageNumber);
    apiClient
      .getMemberPerfList(pageNumber, props.memberInfo.memberNo, props.string)
      .then((data) => {
        setCards([...cards, ...data]);
      });
    setPageNumber(pageNumber + 1);
    console.log(cards);
  }

  const changeFunction = (check) => {
    setChange(check);
  };

  return (
    <Grid container spacing={4}>
      {cards.map((card) => (
        <Grid item key={`${card.pfNo}`} xs={4}>
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
              {card.status}
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
              {card.category}
            </Box>
            <Link href={`/perf/detail/${card.pfNo}`}>
              {card.images ? (
                <CardMedia
                  component="img"
                  style={{
                    pb: 1,
                    objectFit: "cover",
                    width: `${cardWidth}px`,
                    height: `${cardWidth}px`,
                    opacity: isHover === card.pfNo ? 0.5 : null,
                    transform: isHover === card.pfNo ? "scale(1.1)" : null,
                    transition: "0.5s",
                  }}
                  image={Object.values(card.images)[0]}
                  alt=""
                  onMouseEnter={() => setIsHover(card.pfNo)}
                  onMouseLeave={() => setIsHover(-1)}
                ></CardMedia>
              ) : (
                <div></div>
              )}
              {isHover === card.pfNo ? (
                <div
                  style={{
                    lineHeight: "300px",
                    position: "absolute",
                    height: "300px",
                    width: "265px",
                    color: "black",
                    top: 0,
                  }}
                  onMouseEnter={() => setIsHover(card.pfNo)}
                  onMouseLeave={() => setIsHover(-1)}
                >
                  <ul style={{ listStyleType: "none" }}>
                    <li># 공연제목:{card.title}</li>
                    <li># 공연장소:{card.location}</li>
                    <li># 공연기간:{card.detailTime}</li>
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
                  pfNo={card.pfNo}
                ></PerfSaveButton>
              </Grid>
            </CardActions>
          </Card>
        </Grid>
      ))}
      <PlusButton lusButton handleCard={fetchMore}></PlusButton>
    </Grid>
  );
}
