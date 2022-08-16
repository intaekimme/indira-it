import React from "react";
import { styled } from "@mui/material/styles";
import { Grid } from "@mui/material";
import Paper from "@mui/material/Paper";
import apiClient from "../apiClient";
import stylesTag from "../css/tag.module.css";
import FollowButton from "./FollowButton";
import ModifyDeleteButton from "./ModifyDeleteButton";
import Box from "@mui/material/Box";
import stylesModal from "../css/modal.module.css";
import MUICarousel from "react-material-ui-carousel";
import CarouselItem from "./CarouselItem";
import FeedLikeButton from "./FeedLikeButton";
import FeedSaveButton from "./FeedSaveButton";
import CommentList from "./CommentList";
import Theme from "./Theme";
import { ThemeProvider } from "@mui/material/styles";
import Link from "@mui/material/Link";
const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === "dark" ? "#1A2027" : "#FFF",
  ...theme.typography.body2,
  padding: theme.spacing(1),
  textAlign: "center",
  color: theme.palette.text.secondary,
  margin: "30px 0px",
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
  const [commentList, setCommentList] = React.useState([]);

  function searchTag(tagName) {
    if (tagName !== "") {
      props.setTagList([tagName]);
      props.handleShowSearch(!props.showSearch);
    }
  }
  const refreshFunction = (newComment) => {
    setCommentList([...commentList, newComment]);
  };
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
      apiClient.getFeedCommentList(props.feedNo).then((data) => {
        const json = [];

        data.forEach((item) => {
          json.push({
            memberNo: item.memberNo,
            reviewNo: item.commentNo,
            profileImageUrl: item.profileImageUrl,
            comment: item.content,
            nickname: item.nickname,
            isRemoved: item.removed,
          });
        });

        setCommentList(json);
      });
    }
  }, [props.feedNo, props.open]);
  // console.log(Object.keys(img));
  // console.log(Object.values(img));

  return open ? (
    <ThemeProvider theme={Theme}>
      <Box className={stylesModal.inner}>
        <Grid container spacing={4}>
          <Grid item xs={6} className={stylesModal.img}>
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
              <Grid container item xs={12} spacing={2}>
                <Grid container item xs={9}>
                  <Grid>
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
                          boxShadow:
                            "0 10px 35px rgba(0, 0, 0, 0.05), 0 6px 6px rgba(0, 0, 0, 0.1)",
                        }}
                      ></img>
                    </a>
                  </Grid>
                  <Grid ml={1} mt={3}>
                    {feedInfo.nickname}
                  </Grid>
                </Grid>
                {user !== feedInfo.memberNo ? (
                  <Grid item xs={3}>
                    <FollowButton
                      memberNo={feedInfo.memberNo}
                      style={{ textAlign: "top" }}
                    ></FollowButton>
                  </Grid>
                ) : (
                  <Grid item xs={2}></Grid>
                )}
              </Grid>
              <Grid item xs={11}>
                <div
                  style={{
                    paddingTop: "20px",
                    textAlign: "left",
                    fontSize: "medium",
                  }}
                >
                  {feedInfo.content}
                </div>
                <div
                  className={stylesTag.HashWrapOuter}
                  style={{ marginTop: "100px" }}
                >
                  {feedInfo.tags ? (
                    feedInfo.tags.map((item, id) => (
                      // <Link
                      //   to={{
                      //     pathname: `/feed/list/search`,
                      //     state: {
                      //     tags: item
                      //   }}}
                      //   style={{ textDecoration: "none" }}
                      // >
                      //   </Link>
                      <div
                        className={stylesTag.HashWrapInner}
                        key={id}
                        // onClick={() => searchTag(item.trim())}
                        onClick={() => {
                          props.handleClose();
                          searchTag(item.trim());
                        }}
                      >
                        # {item}
                      </div>
                    ))
                  ) : (
                    <div></div>
                  )}
                </div>
              </Grid>
            </Item>
            <Grid container spacing={2} ml={1}>
              <Grid item xs={10}>
                <FeedLikeButton feedNo={feedNo}></FeedLikeButton>
              </Grid>
              <Grid item xs={2}>
                <FeedSaveButton feedNo={feedNo}></FeedSaveButton>
              </Grid>

              <Grid item xs={11}>
                <Item>
                  <CommentList
                    refreshFunction={refreshFunction}
                    commentList={commentList}
                    feedNo={feedNo}
                    kind={"feed"}
                  />
                </Item>
              </Grid>
            </Grid>
          </Grid>
        </Grid>
      </Box>
    </ThemeProvider>
  ) : (
    <Link to="/perf/list"></Link>
  );
}
