import * as React from "react";
import { useCallback } from "react";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import Link from "@mui/material/Link";
import { ThemeProvider } from "@mui/material/styles";
import PerfFeedToggle from "./MainButton";
import { useParams } from "react-router-dom";
import FeedListAll from "./FeedListAll";
import stylesTag from "../css/tag.module.css";
import stylesButton from "../css/button.module.css";
import FeedListFollow from "./FeedListFollow";
import FeedListSave from "./FeedListSave";
import FeedListSearch from "./FeedListSearch";
import Theme from "./Theme";
import SearchIcon from "@mui/icons-material/Search";
import Select from "@mui/material/Select";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
function Copyright() {
  return (
    <Typography color="text.secondary" align="center" component="span">
      {"Copyright © "}
      <Link color="inherit" href="/">
        Indielight
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
  const [howManySearch, setHowManySearch] = React.useState(0);
  const [nowState, setNowState] = React.useState("");
  const handleChange = (e) => {
    setNowState(e.target.value);
  };

  const handleShowAll = () => {
    setShowAll(true);
    setShowSave(false);
    setShowFollow(false);
    setShowSearch(false);
  };

  const handleShowFollow = () => {
    if (!sessionStorage.getItem("loginCheck")) {
      sessionStorage.setItem("currentHref", window.location.href);
      window.location.href = "/login";
    }
    setShowAll(false);
    setShowSave(false);
    setShowFollow(true);
    setShowSearch(false);
  };

  const handleShowSave = () => {
    if (!sessionStorage.getItem("loginCheck")) {
      sessionStorage.setItem("currentHref", window.location.href);
      window.location.href = "/login";
    }
    setShowAll(false);
    setShowSave(true);
    setShowFollow(false);
    setShowSearch(false);
  };

  const handleShowSearch = () => {
    // if (tags.length === 0) {
    //   alert("검색어를 입력해주세요.");
    //   return;
    // }
    setShowAll(false);
    setShowSave(false);
    setShowFollow(false);
    setShowSearch(true);
    setHowManySearch(howManySearch + 1);
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
          handleShowSearch();
        }
      }
    },
    [tag, tags],
  );

  function deleteTag(tagName) {
    setTagList(tags.filter((tag) => tag !== tagName));
    if (tags.length === 0) setHide(true);
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
            style={{ textAlign: "center" }}
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
          {/* <Button
            onClick={handleShowSearch}
            className={stylesButton.btn4}
            style={{
              width: "50px",
              marginLeft: "10px",
            }}
          >
            <SearchIcon color="neutral"></SearchIcon>
          </Button> */}
        </Grid>
      </Grid>
      <Grid
        item
        xs={4}
        id="notice"
        mt={2}
        style={{
          textAlign: "center",
          fontFamily: "SBAggroB",
          marginBottom: "30px",
        }}
      >
        {!hide ? <span>태그를 누르면 삭제됩니다</span> : <span></span>}
      </Grid>
      <Grid
        container
        style={{
          justifyContent: "center",
          textAlign: " center",
        }}
        spacing={2}
      >
        <Grid item xs={1} style={{ fontFamily: "SBAggroB" }}>
          <FormControl fullWidth>
            <InputLabel id="demo-simple-select-label">정렬</InputLabel>
            <Select
              labelId="demo-simple-select-label"
              id="demo-simple-select"
              label="정렬"
              value={nowState}
              onChange={handleChange}
              sx={{
                "&.Mui-focused .MuiOutlinedInput-notchedOutline": {
                  borderColor: "#66cc66",
                },
              }}
            >
              <MenuItem
                value="최신순 피드"
                onClick={handleShowAll}
                style={{ fontFamily: "SBAggroB" }}
              >
                최신순 피드
              </MenuItem>
              <MenuItem
                value="북마크 피드"
                onClick={handleShowSave}
                style={{ fontFamily: "SBAggroB" }}
              >
                북마크 피드
              </MenuItem>
              <MenuItem
                value="팔로우 피드"
                onClick={handleShowFollow}
                style={{ fontFamily: "SBAggroB" }}
              >
                팔로우 피드
              </MenuItem>
            </Select>
          </FormControl>
        </Grid>
      </Grid>
      <div>
        <Container sx={{ py: 10 }} maxWidth="md">
          {showFollow ? (
            <FeedListFollow
              showSearch={showSearch}
              handleShowSearch={handleShowSearch}
              setTagList={setTagList}
            ></FeedListFollow>
          ) : null}
          {showSave ? (
            <FeedListSave
              showSearch={showSearch}
              handleShowSearch={handleShowSearch}
              setTagList={setTagList}
            ></FeedListSave>
          ) : null}
          {showAll ? (
            <FeedListAll
              showSearch={showSearch}
              handleShowSearch={handleShowSearch}
              setTagList={setTagList}
            ></FeedListAll>
          ) : null}
          {showSearch ? (
            <FeedListSearch
              tags={tags}
              howManySearch={howManySearch}
              setHowManySearch={setHowManySearch}
              showSearch={showSearch}
              handleShowSearch={handleShowSearch}
              setTagList={setTagList}
            ></FeedListSearch>
          ) : null}
        </Container>
      </div>
      {/* Footer */}
      <Box sx={{ p: 4 }}>
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
