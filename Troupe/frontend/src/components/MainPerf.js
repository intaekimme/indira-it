import * as React from "react";
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
import PerfList from "./PerfList";
import PerfSearchList from "./PerfSearchList";
import Theme from "./Theme";
import { FormControl, Select, MenuItem, TextField } from "@mui/material";
import stylesButton from "../css/button.module.css";
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
      <Grid
        container
        style={{
          display: "flex",
          justifyContent: "center",
          fontFamily: "SBAggroB",
        }}
      >
        <Grid item xs={12} style={{ textAlign: "center" }}>
          <PerfFeedToggle></PerfFeedToggle>
        </Grid>
        <Grid item mt={3}>
          <FormControl>
            <Grid
              item
              xs={12}
              container
              style={{ justifyContent: "center" }}
              spacing={1}
              marginTop="65px"
            >
              <Grid item xs={3}>
                <Select
                  onChange={handleSearchCategory}
                  value={searchCategory}
                  style={{ backgroundColor: "white" }}
                >
                  <MenuItem value={"title"} style={{ fontFamily: "SBAggroB" }}>
                    제목+내용
                  </MenuItem>
                  <MenuItem
                    value={"nickname"}
                    style={{ fontFamily: "SBAggroB" }}
                  >
                    작성자
                  </MenuItem>
                </Select>
              </Grid>
              <Grid item xs={7}>
                <TextField
                  onChange={handleSearchWord}
                  label="키워드 검색"
                  placeholder="검색어를 입력해주세요."
                  style={{ textAlign: "center", backgroundColor: "white" }}
                ></TextField>
              </Grid>
              <Grid item xs={2}>
                <Button
                  variant="contained"
                  color="neutral"
                  onClick={handleShowSearch}
                  className={stylesButton.btn2}
                  style={{ backgroundColor: "#fda085" }}
                >
                  <SearchIcon color="white"></SearchIcon>
                </Button>
              </Grid>
            </Grid>
          </FormControl>
        </Grid>
        {/* <Grid item xs={12} style={{ justifyContent: "right" }}>
          <Button
            href="/perf/register"
            variant="contained"
            className={stylesButton.btn2}
          >
            +
          </Button>
        </Grid> */}
      </Grid>
      <div>
        <Container sx={{ py: 10 }} maxWidth="md">
          {showList ? <PerfList></PerfList> : null}
          {showSearch ? (
            <PerfSearchList
              condition={searchCategory}
              keyword={searchWord}
              howManySearch={howManySearch}
              setHowManySearch={setHowManySearch}
            ></PerfSearchList>
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
