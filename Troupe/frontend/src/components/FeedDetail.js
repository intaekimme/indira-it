import React, { useCallback } from "react";
import CssBaseline from "@mui/material/CssBaseline";
import { createTheme, ThemeProvider } from "@mui/material/styles";

const theme = createTheme({
  palette: {
    neutral: {
      main: "#fda085",
      contrastText: "#fff",
    },
  },
});

export default function FeedDetail() {
  return <ThemeProvider></ThemeProvider>;
}
