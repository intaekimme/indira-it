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
    fontFamily: ["SBAggroB", "Cafe24SsurroundAir"].join(","),
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
