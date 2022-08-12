import * as React from "react";
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
import PerfListCard from "./PerfListCard";

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

const theme = createTheme();

export default function PerfList() {
  let { pageNumber } = useParams();

  let [pageCounter, setPageCounter] = React.useState(pageNumber);

  const handlePageCounter = () => {
    setPageCounter(++pageCounter);
  };

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <div style={{ display: "flex", justifyContent: "center" }}>
        <SearchBar></SearchBar>
      </div>
      <div style={{ display: "flex", justifyContent: "center" }}>
        <PerfFeedToggle></PerfFeedToggle>
      </div>
      <div>
        <Container sx={{ py: 10 }} maxWidth="md">
          <PerfListCard></PerfListCard>
        </Container>
      </div>
      {/* Footer */}
      {/* <Box sx={{ bgcolor: "background.paper", p: 4 }}>
        <Typography
          variant="subtitle1"
          align="center"
          color="text.secondary"
          component="span"
        >
          Troupe
        </Typography>
        <Copyright />
      </Box> */}
      {/* End footer */}
    </ThemeProvider>
  );
}
