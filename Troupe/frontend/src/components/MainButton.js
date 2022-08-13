import React from "react";
import { Button, ButtonGroup } from "@mui/material";
import Theme from "./Theme";
import { ThemeProvider } from "@mui/material/styles";
export default function PerfFeedToggle() {
  return (
    <ThemeProvider theme={Theme}>
      <ButtonGroup size="large" sx={{ pt: 7, justifyContent: "center" }}>
        <Button
          href="/perf/list/0"
          variant="contained"
          color="yellow"
          style={{ fontFamily: "SBAggroB", width: "220px", height: "40px" }}
        >
          공연
        </Button>
        <Button
          href="/feed/list/all/0"
          variant="contained"
          color="green"
          style={{
            fontFamily: "SBAggroB",
            width: "220px",
            height: "40px",
            color: "black",
          }}
        >
          피드
        </Button>
      </ButtonGroup>
    </ThemeProvider>
  );
}
