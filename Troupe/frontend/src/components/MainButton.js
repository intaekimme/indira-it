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
          style={{
            marginRight: "0.1px",
            fontFamily: "SBAggroB",
            width: "220px",
            height: "40px",
            border: "3px solid white",
            boxShadow:
              "0 10px 30px rgba(0, 0, 0, 0.05), 0 6px 6px rgba(0, 0, 0, 0.3)",
          }}
        >
          공연
        </Button>
        <Button
          href="/feed/list/all/0"
          variant="contained"
          color="green"
          style={{
            marginLeft: "0.1px",
            fontFamily: "SBAggroB",
            width: "220px",
            height: "40px",
            color: "black",
            border: "3px solid white",
            boxShadow:
              "0 10px 30px rgba(0, 0, 0, 0.05), 0 6px 6px rgba(0, 0, 0, 0.3)",
          }}
        >
          피드
        </Button>
      </ButtonGroup>
    </ThemeProvider>
  );
}
