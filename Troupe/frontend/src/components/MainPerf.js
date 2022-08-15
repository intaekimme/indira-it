import * as React from "react";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import Link from "@mui/material/Link";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import PerfFeedToggle from "./MainButton";
import apiClient from "../apiClient";
import { useState, useParams } from "react-router-dom";
import { useQuery } from "react-query";
import PerfList from "./PerfList";
import PerfSearchList from "./PerfSearchList";
import Theme from "./Theme";
import { FormControl, Select, MenuItem, TextField } from "@mui/material";
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

export default function MainPerf() {
  let { pageNumber } = useParams();

  let [pageCounter, setPageCounter] = React.useState(pageNumber);
  let [searchCategory, setSearchCategory] = React.useState("title");
  let [searchWord, setSearchWord] = React.useState("");
  let [showList, setShowList] = React.useState(true);
  let [showSearch, setShowSearch] = React.useState(false);
  const [howManySearch, setHowManySearch] = React.useState(0);

  const handlePageCounter = () => {
    setPageCounter(++pageCounter);
  };

  const handleSearchCategory = (e) => {
    setSearchCategory(e.target.value);
    console.log(searchCategory);
  };

  const handleSearchWord = (e) => {
    setSearchWord(e.target.value);
    console.log(searchWord);
  };

  const handleShowList = () => {
    setShowList(true);
    setShowSearch(false);
  };

  const handleShowSearch = () => {
    if (searchWord.length === 0) {
      alert("검색어를 입력해주세요.");
      return;
    }
    setShowList(false);
    setShowSearch(true);
    setHowManySearch(howManySearch + 1);
  };

  return (
    <ThemeProvider theme={Theme}>
      <CssBaseline />
      <Grid style={{ display: "flex", justifyContent: "center" }}>
        <PerfFeedToggle></PerfFeedToggle>
      </Grid>
      <Grid style={{ display: "flex", justifyContent: "center" }}>
        <FormControl>
          <Select
            onChange={handleSearchCategory}
            label="category"
            value={searchCategory}
          >
            <MenuItem value={"title"}>제목+내용</MenuItem>
            <MenuItem value={"nickname"}>작성자</MenuItem>
          </Select>
          <TextField onChange={handleSearchWord}></TextField>
          <Button onClick={handleShowSearch}>검색</Button>
        </FormControl>
      </Grid>
      <Grid style={{ display: "flex", justifyContent: "center" }}>
        <Button href="/perf/register" variant="contained">
          공연등록
        </Button>
      </Grid>
      <Grid>
        <Container sx={{ py: 10 }} maxWidth="md">
          {showList? <PerfList></PerfList> : null}
          {showSearch? <PerfSearchList condition={searchCategory} keyword={searchWord} howManySearch={howManySearch} setHowManySearch={setHowManySearch}></PerfSearchList> : null}
        </Container>
      </Grid>
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
