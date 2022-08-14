import * as React from "react";
import { useCallback } from "react";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import Link from "@mui/material/Link";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import SearchBar from "./SearchBar";
import PerfFeedToggle from "./MainButton";
import apiClient from "../apiClient";
import { useState, useParams } from "react-router-dom";
import { useQuery } from "react-query";
import FeedListAll from "./FeedListAll";
import stylesTag from "../css/tag.module.css";
import FeedListFollow from "./FeedListFollow";
import FeedListSave from "./FeedListSave";
import FeedListSearch from "./FeedListSearch";
import Theme from "./Theme";
import SearchIcon from "@mui/icons-material/Search";
function Copyright() {
  return (
    <Typography color="text.secondary" align="center" component="span">
      {"Copyright © "}
      <Link color="inherit" href="/">
        Troupe
      </Link>{" "}
      {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}

export default function MainFeed() {
  let { pageNumber } = useParams();
  const [tags, setTagList] = React.useState([]);
  const [tag, setTag] = React.useState("");
  const [showAll, setShowAll] = React.useState(true);
  const [showFollow, setShowFollow] = React.useState(false);
  const [showSave, setShowSave] = React.useState(false);
  const [showSearch, setShowSearch] = React.useState(false);
  const [hide, setHide] = React.useState(true);
  const handleShowAll = () => {
    setShowAll(true);
    setShowSave(false);
    setShowFollow(false);
    setShowSearch(false);
  };

  const handleShowFollow = () => {
    setShowAll(false);
    setShowSave(false);
    setShowFollow(true);
    setShowSearch(false);
  };

  const handleShowSave = () => {
    setShowAll(false);
    setShowSave(true);
    setShowFollow(false);
    setShowSearch(false);
  };

  const handleShowSearch = () => {
    if (tags.length === 0) {
      alert("검색어를 입력해주세요.");
      return;
    }
    setShowAll(false);
    setShowSave(false);
    setShowFollow(false);
    setShowSearch(true);
  };

  const changeTag = (event) => {
    setTag(event.target.value);
  };


  const addTagFunc = useCallback(
    (event) => {
      if (process.browser) {
        // 요소 불러오기
        const HashWrapOuter = document.querySelector(
          `.${stylesTag.HashWrapOuter}`,
        );
        const HashWrapInner = document.createElement("div");
        HashWrapInner.className = stylesTag.HashWrapInner;

        if (event.key === "Enter" && event.target.value.trim() !== "") {
          event.preventDefault();
          // HashWrapInner.innerHTML = "# " + event.target.value;
          // HashWrapOuter.appendChild(HashWrapInner);
           setHide(false);
          if (tags.length >= 5) {
            // alert("최대 5개까지 등록할 수 있습니다");
            const notice = document.querySelector("#notice");
            if (document.querySelector("#notice div") !== null) return;
            const newNotice = document.createElement("div");
            newNotice.innerHTML = "최대 5개까지 등록할 수 있습니다";
            newNotice.className = stylesTag.alert;
            notice.appendChild(newNotice);
            return;
          } else {
            if (document.querySelector("#notice div") !== null) {
              const notice = document.querySelector("#notice");
              const div = document.querySelector("#notice div");
              notice.removeChild(div);
            }
          }
          setTagList((tags) => [...tags, tag]);
          setTag("");
          console.log(tags);
        }
      }
    },
    [tag, tags],
  );

  function deleteTag(tagName) {
    setTagList(tags.filter((tag) => tag !== tagName));
    if(tags.length===0) setHide(true)
  }
  
  return (
    <ThemeProvider theme={Theme}>
      <CssBaseline />
      <Grid container style={{ display: "flex", justifyContent: "center" }}>
        <PerfFeedToggle></PerfFeedToggle>
       <Grid item xs={12} style={{ textAlign: "center" }}>
        <input
          className={stylesTag.HashInput}
          label="태그"
          value={tag}
          onChange={changeTag}
          onKeyUp={addTagFunc}
          placeholder="이곳에 태그입력 후 엔터를 치세요."
          maxLength={20}
          style={{textAlign: "center"}}
        />
       </Grid>
       
        <Grid item xs={4}></Grid>
        <Grid item xs={4} mt={6}>
          <div item className={stylesTag.HashWrap}>
            <div className={stylesTag.HashWrapOuter}>
              {tags ? (
                tags.map((item, id) => (
                  <div
                    className={stylesTag.HashWrapInner}
                    key={id}
                    onClick={() => deleteTag(item)}
                  >
                    # {item}
                  </div>
                ))
              ) : (
                <div></div>
              )}
            </div>
          </div>
        </Grid>
        <Grid item xs={4} mt={6}>
          <Button
            onClick={handleShowSearch}
            variant="contained"
            color="neutral"
            style={{
              fontFamily: "SBAggroB",
              width: "50px",
              height: "30px",
              color: "black",
              marginLeft: "10px",
              boxShadow:
              "0 10px 30px rgba(0, 0, 0, 0.05), 0 6px 6px rgba(0, 0, 0, 0.08)"
            }}
          >
            <SearchIcon color="white"></SearchIcon>
          </Button>
        </Grid>
      </Grid>
      <Grid
          item
          xs={4}
          id="notice"
          mt={2}
          style={{ textAlign: "center", fontFamily: "SBAggroB", marginBottom: "30px"}}
        >
        {!hide ?( <span>태그를 누르면 삭제됩니다</span>):(<span></span>)}
        </Grid>
      <Grid
        container
        style={{
          display: "flex",
          justifyContent: "center",
          textAlign: " center",
        }}
        spacing={2}
      >
        <Grid item xs={2}>
          <Button
            onClick={handleShowAll}
            variant="contained"
            color="neutral"
            style={{
              fontFamily: "Cafe24SsurroundAir",
              fontWeight: "bold",
              width: "150px",
              height: "30px",
            }}
          >
            최신순 피드
          </Button>
        </Grid>
        <Grid item xs={2}>
          <Button
            onClick={handleShowSave}
            variant="contained"
            color="neutral"
            style={{
              fontFamily: "Cafe24SsurroundAir",
              fontWeight: "bold",
              width: "150px",
              height: "30px",
            }}
          >
            북마크 피드
          </Button>
        </Grid>
        <Grid item xs={2}>
          <Button
            onClick={handleShowFollow}
            variant="contained"
            color="neutral"
            style={{
              fontFamily: "Cafe24SsurroundAir",
              fontWeight: "bold",
              width: "150px",
              height: "30px",
            }}
          >
            팔로우 피드
          </Button>
        </Grid>
      </Grid>

      <div>
        <Container sx={{ py: 10 }} maxWidth="md">
          {showFollow ? <FeedListFollow></FeedListFollow> : null}
          {showSave ? <FeedListSave></FeedListSave> : null}
          {showAll ? <FeedListAll></FeedListAll> : null}
          {showSearch ? <FeedListSearch tags={tags}></FeedListSearch> : null}
        </Container>
      </div>
      {/* Footer */}
      <Box sx={{  p: 4 }}>
        <Typography
          variant="subtitle1"
          align="center"
          color="text.secondary"
          component="span"
        >
          인디라IT
        </Typography>
        <Copyright />
      </Box>
      {/* End footer */}
    </ThemeProvider>
  );
}
