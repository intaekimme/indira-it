import apiClient from '../apiClient';
import React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';

import SNSLoginFacebook from './SNSLoginFacebook';

const theme = createTheme();

export default function Login() {
  //로그인버튼 클릭
  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const loginInfo = {
      email: data.get('email'),
      password: data.get('password'),
    };
    console.log(loginInfo);
    apiClient.login(loginInfo);
  };

  //페이스북 로그인
  const facebookLogin = () =>{
    alert("facebook 로그인 창");
  }
  //인스타 로그인
  const instagramLogin = () =>{
    alert("instagram 로그인 창");
  }

  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Sign in
          </Typography>
          <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}
          style={{textAlign:"center"}}>
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
            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="Password"
              type="password"
              id="password"
              autoComplete="current-password"
            />
            <FormControlLabel
              control={<Checkbox value="remember" color="primary" />}
              label="로그인 유지"
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 2, mb: 1 }}
              style = {{backgroundColor: "gray"}}
            >
              로그인
            </Button>
            <SNSLoginFacebook />
            {/* <Button
              fullWidth
              variant="contained"
              sx={{ mt: 0, mb: 1 }}
              style = {{backgroundColor: "blue"}}
              onClick = {facebookLogin}
            >
              FaceBook 로그인
            </Button> */}
            <Button
              fullWidth
              variant="contained"
              sx={{ mt: 0, mb: 1 }}
              style = {{backgroundColor: "purple"}}
              onClick = {instagramLogin}
            >
              Instagram 로그인
            </Button>
            <Grid container>
              <Grid item xs>
                <Link href="/resetpw" variant="body2">
                  비밀번호 찾기
                </Link>
              </Grid>
              <Grid item>
                <Link href="/signup" variant="body2">
                  회원가입
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
}