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
import AccountCircleIcon from '@mui/icons-material/AccountCircle';

import PerfList from '..//components/PerfList';

const theme = createTheme();

function Profile() {
  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xl">
        <Grid container spacing={2} style={{ textAlign: "center" }}>
          <Grid item xs={6} style={{ backgroundColor: "gray" }}>
            <Grid style={{ height: "100%" }}>
							{/* Stage start */}
              <AccountCircleIcon
                fontSize="large"
                sx={{ fontSize: "100px" }}
              ></AccountCircleIcon>
							{/* Stage finish */}
            </Grid>
          </Grid>
          {
            //프로필
          }
          <Grid item xs={6} style={{ backgroundColor: "blue" }}>
            <Grid container spacing={3}>
              <Grid item xs={12}>
                <PerfList />
              </Grid>
              <Grid item xs={12}>
                <PerfList />
              </Grid>
              <Grid item xs={12}>
                <PerfList />
              </Grid>
            </Grid>
          </Grid>
        </Grid>
      </Container>
    </ThemeProvider>
  );
}
export default Profile;