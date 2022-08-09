import React from "react";
import {useParams} from "react-router-dom";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import apiClient from "../apiClient";

const theme = createTheme();

export default function ResetPw() {
  const {token} = useParams();
  const [resetPossible, setResetPossible] = React.useState(true);

  React.useEffect(()=>{
    if(token){
      setResetPossible(true);
    }
  }, [token]);

  const handleSubmit = (event) => {
    const formData = new FormData(event.currentTarget);
    const email = formData.get("email");
    apiClient.requestPassword(email);
  };
  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Box
            component="form"
            onSubmit={handleSubmit}
            noValidate
            sx={{ mt: 1 }}
          >
            {resetPossible ? (
              <div style={{ width: "100%" }}>
                <TextField
                  margin="normal"
                  required
                  fullWidth
                  name="password"
                  label="새 비밀번호"
                  type="password"
                  id="password"
                  autoComplete="new-password"
                  autoFocus
                />
                <TextField
                  margin="normal"
                  required
                  fullWidth
                  name="passwordCheck"
                  label="비밀번호 확인"
                  type="password"
                  id="passwordCheck"
                  autoComplete="new-password"
                />
              </div>
            ) : (
              <TextField
                margin="normal"
                required
                fullWidth
                id="email"
                label="Email Address"
                name="email"
                autoComplete="email"
                autoFocus
              />
            )}
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 2, mb: 1 }}
              style={{ backgroundColor: "gray" }}
            >
              비밀번호 초기화
            </Button>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
