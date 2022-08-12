import { createTheme } from "@mui/material/styles";

const theme = createTheme({
  palette: {
    neutral: {
      main: "#fda085",
      contrastText: "#fff",
    },
    green: {
      main: "#66cc66",
      contrastText: "#fff",
    },
    yellow: {
      main: "#ffd400",
    },
    black: {
      main: "#000000",
    },
  },
  typography: {
    fontFamily: [
      "yang",
      "116angmuburi",
      "116watermelon",
      "SBAggroB",
      "Cafe24SsurroundAir",
      "NEXON Lv1 Gothic OTF",
      "Bareun_hipi",
      "Atomy-Bold",
    ].join(","),
  },
});

export default theme;
