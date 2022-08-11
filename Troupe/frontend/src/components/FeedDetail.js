import React from "react";
import { styled } from "@mui/material/styles";
import { Grid } from "@mui/material";
import Paper from "@mui/material/Paper";
import apiClient from "../apiClient";
import { useParams } from "react-router-dom";
import stylesTag from "../css/tag.module.css";
import FollowButton from "./FollowButton";
import ModifyDeleteButton from "./ModifyDeleteButton";
import Carousel from "./Carousel";
import Box from "@mui/material/Box";
import stylesModal from "../css/modal.module.css";
import MUICarousel from "react-material-ui-carousel";
import CarouselItem from "./CarouselItem";
import FeedLikeButton from "./FeedLikeButton";
import FeedSaveButton from "./FeedSaveButton";

const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === "dark" ? "#1A2027" : "#FFF",
  ...theme.typography.body2,
  padding: theme.spacing(1),
  textAlign: "center",
  color: theme.palette.text.secondary,
  margin: "100px 0px",
}));

export default function FeedDetail(props) {
  // const { feedNo } = useParams();
  const [feedInfo, setFeedInfo] = React.useState({
    content: "",
    createdTime: "",
    memberNo: 0,
    nickname: "",
    profileImageUrl: "",
    tags: [],
  });

  const [feedNo, setFeedNo] = React.useState(0);
  const [open, setOpen] = React.useState(false);
  const [like, setLike] = React.useState(0);
  const [img, setImg] = React.useState(new Map());
  const [user, setUser] = React.useState(0);
  function searchTag(tagName) {
    if (tagName !== "") {
      apiClient.getFeedSearchTest(tagName).then((data) => {});
    }
  }

  React.useEffect(() => {
    setFeedNo(props.feedNo);
    setOpen(props.open);

    if (props.feedNo) {
      if (sessionStorage.getItem("loginCheck"))
        setUser(parseInt(sessionStorage.getItem("loginMember")));

      apiClient.getFeedDetail(props.feedNo).then((data) => {
        setFeedInfo(data);
        setImg(data.images);
      });
      apiClient.getFeedTotalLike(props.feedNo).then((data) => {
        setLike(data);
      });
    }
  }, [props.feedNo, props.open]);
  // console.log(Object.keys(img));
  // console.log(Object.values(img));

  return open ? (
    <Box className={stylesModal.inner}>
      <Grid container spacing={4}>
        <Grid item xs={6} className={stylesModal.img}>
          {/* <Carousel>
              {Object.values(img) ? (
                Object.values(img).map((item, id) => (
                  <Item key={id} item={item} />
                ))
              ) : (
                <div></div>
              )}
            </Carousel> */}
          <MUICarousel
            autoPlay={false}
            animation="slide"
            style={{
              backgroudColor: "black",
            }}
          >
            {Object.values(img)
              ? Object.values(img).map((item, id) => (
                  <CarouselItem key={id} item={item} />
                ))
              : null}
          </MUICarousel>
        </Grid>
        <Grid item xs={6}>
          <ModifyDeleteButton feedNo={feedNo} memberNo={feedInfo.memberNo} />
          <Item elevation={0}>
            <div
              style={{
                fontSize: "medium",
                paddingLeft: "0px",
                fontFamily: "IBM Plex Sans KR",
                textAlign: "left",
              }}
            >
              <div>
                <a
                  style={{ textDecoration: "none" }}
                  href={"/profile/" + feedInfo.memberNo}
                >
                  <img
                    src={feedInfo.profileImageUrl}
                    alt="profile"
                    style={{
                      borderRadius: "70%",
                      objectFit: "cover",
                      height: "50px",
                      width: "50px",
                      marginRight: "10px",
                    }}
                  ></img>
                  <span>{feedInfo.nickname}</span>
                </a>
                {user !== feedInfo.memberNo ? (
                  <div>
                    <FollowButton
                      memberNo={feedInfo.memberNo}
                      style={{ textAlign: "top" }}
                    ></FollowButton>
                  </div>
                ) : (
                  <div></div>
                )}
              </div>
              <div>
                <p>{feedInfo.content}</p>
                <div className={stylesTag.HashWrapOuter}>
                  {feedInfo.tags ? (
                    feedInfo.tags.map((item, id) => (
                      <div
                        className={stylesTag.HashWrapInner}
                        key={id}
                        onClick={() => searchTag(item.trim())}
                      >
                        # {item}
                      </div>
                    ))
                  ) : (
                    <div></div>
                  )}
                </div>
              </div>
            </div>
          </Item>
          <FeedLikeButton feedNo={feedNo}></FeedLikeButton>
          <FeedSaveButton feedNo={feedNo}></FeedSaveButton>
          <Grid container margin="10% 10% 10% 10%">
            <Grid item xs={7}>
              <Item>댓글란</Item>
            </Grid>
          </Grid>
        </Grid>
      </Grid>
    </Box>
  ) : (
    <div></div>
  );
}
