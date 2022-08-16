import { createTheme } from "@mui/material/styles";

const Theme = createTheme({
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
    white: {
      main: "#FFFFFF",
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
  select: {
    "&.Mui-focused .MuiOutlinedInput-notchedOutline": {
      borderColor: "#66cc66",
    },
  },
  textField: {
    "& .MuiOutlinedInput-root.Mui-focused": {
      "& > fieldset": {
        borderColor: "#66cc66",
      },
    },
  },
});

export default Theme;
