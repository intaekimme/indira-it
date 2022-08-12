import * as React from 'react';
import { useCallback } from 'react';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import Link from '@mui/material/Link';
import { createTheme, ThemeProvider} from '@mui/material/styles';
import SearchBar from './SearchBar'
import PerfFeedToggle from './MainButton';
import apiClient from '../apiClient';
import { useState, useParams } from 'react-router-dom';
import { useQuery } from 'react-query';
import FeedListAll from './FeedListAll';
import stylesTag from "../css/tag.module.css";
import FeedListFollow from './FeedListFollow'
import FeedListSave from './FeedListSave'
import FeedListSearch from './FeedListSearch';


function Copyright() {
  return (
    <Typography color="text.secondary" align="center" component="span">
      {'Copyright © '}
      <Link color="inherit" href="/">
        Troupe
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}


const theme = createTheme();

export default function MainFeed() {
  let {pageNumber} = useParams();
  const [tags, setTagList] = React.useState([]);
  const [tag, setTag] = React.useState('');
  const[showAll, setShowAll] = React.useState(true);
  const[showFollow, setShowFollow] = React.useState(false);
  const[showSave, setShowSave] = React.useState(false);
  const[showSearch, setShowSearch] = React.useState(false);


  const handleShowAll = () => {
    setShowAll(true)
    setShowSave(false)
    setShowFollow(false)
    setShowSearch(false)
  };

  const handleShowFollow = () => {
    setShowAll(false)
    setShowSave(false)
    setShowFollow(true)
    setShowSearch(false)
  };

  const handleShowSave = () => {
    setShowAll(false)
    setShowSave(true)
    setShowFollow(false)
    setShowSearch(false)
  };

  const handleShowSearch = () => {
    setShowAll(false)
    setShowSave(false)
    setShowFollow(false)
    setShowSearch(true)
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
  };
  
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <div style={{display:'flex', justifyContent:'center'}}>
        <PerfFeedToggle></PerfFeedToggle>
      </div>
      <div style={{display:'flex', justifyContent:'center'}}>
        <Grid container>
        <Grid item xs={6}>
              <p id="notice">태그를 누르면 삭제됩니다</p>
              <div className={stylesTag.HashWrap}>
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
              <input
                className={stylesTag.HashInput}
                label="태그"
                value={tag}
                onChange={changeTag}
                onKeyUp={addTagFunc}
                placeholder="태그입력 후 엔터를 치세요"
                maxLength={20}
              />
            </Grid>
            <Grid item xs={4}>
              <Button onClick={handleShowSearch}>검색</Button>
              <Button onClick={handleShowAll}>전체피드</Button>
              <Button onClick={handleShowSave}>저장피드</Button>
              <Button onClick={handleShowFollow}>팔로우 피드</Button>
            </Grid>
        </Grid>
      </div>
      <div>
        <Container sx={{ py: 10 }} maxWidth="md">
          {showFollow? <FeedListFollow></FeedListFollow> : null}
          {showSave? <FeedListSave></FeedListSave> : null}
          {showAll? <FeedListAll></FeedListAll> : null}
          {showSearch? <FeedListSearch tags={tags}></FeedListSearch>: null}
        </Container>
      </div>
      {/* Footer */}
      <Box sx={{ bgcolor: 'background.paper', p: 4 }}>

        <Typography
          variant="subtitle1"
          align="center"
          color="text.secondary"
          component="span"
        >
          Troupe
        </Typography>
        <Copyright />
      </Box>
      {/* End footer */}
    </ThemeProvider>
  );
}
