import * as React from "react";
import Button from "@mui/material/Button";
import { ThemeProvider } from "@mui/material/styles";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import Theme from "./Theme";
import KeyboardDoubleArrowDownIcon from "@mui/icons-material/KeyboardDoubleArrowDown";
export default function PlusButton(props) {
  return (
    <ThemeProvider theme={Theme}>
      <Container sx={{ py: 4 }} maxWidth="md">
        <div style={{ display: "flex", justifyContent: "center", pb: 7 }}>
          <Button fullWidth size="large" onClick={props.handleCard}>
            <Typography gutterBottom fontFamily="SBAggroB" fontSize="17px">
              <KeyboardDoubleArrowDownIcon color="action"></KeyboardDoubleArrowDownIcon>
            </Typography>
          </Button>
        </div>
      </Container>
    </ThemeProvider>
  );
}
