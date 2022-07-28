import * as React from 'react';
import Button from '@mui/material/Button';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardMedia from '@mui/material/CardMedia';
import CssBaseline from '@mui/material/CssBaseline';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import Link from '@mui/material/Link';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import TurnedInNotIcon from '@mui/icons-material/TurnedInNot';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
import TurnedInIcon from '@mui/icons-material/TurnedIn';
import { ButtonGroup } from '@mui/material';
import Favorite from '@mui/icons-material/Favorite';


// 무한 스크롤 구현 필요(더보기 버튼을 활용한, axios)

function Copyright() {
  return (
    <Typography variant="body2" color="text.secondary" align="center">
      {'Copyright © '}
      <Link color="inherit" href="/">
        Troupe
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

function TagSearchForm() {
  return (
    console.log('gotcha')
  );
}

function PerfExhToggle(){
  return (
    <ButtonGroup size='large' sx={{pt:7, justifyContent:'center'}}>
      <Button sx={{color:'black',background:'orange', fontFamily:'IBM Plex Sans KR'}}>Performance Feed</Button>
      <Button sx={{color:'black',background:'pink', fontFamily:'IBM Plex Sans KR'}}>Exhibition Feed</Button>
    </ButtonGroup>
  )
}

function range(start, end) {
  let array = [];
  for (let i = start; i < end; ++i) {
    array.push(i);
  }
  console.log(array)
  return array;
}

let pages = 6
// let cards = [1, 2, 3, 4, 5, 6]; //여기로 받아오기

const theme = createTheme();

export default function Album() {

  const [like, setLike] = React.useState(false)
  const [save, setSave] = React.useState(false)
  let [cards, setCard] = React.useState([1,2,3,4,5,6])

  function handleLike() {
    setLike(!like)
  }

  function handleSave() {
    setSave(!save)
  }

  function handleCard() {
    pages=pages + 6
    setCard(range(0, pages))
  }

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <div style={{display:'flex', justifyContent:'center'}}>
        <PerfExhToggle></PerfExhToggle>
      </div>
      <main>
        <Container sx={{ py: 10 }} maxWidth="md">
          <Grid container spacing={4}>
            {cards.map((card) => (
              <Grid item key={card} xs={12} sm={6} md={4}>
                <Card
                  sx={{ height: '100%', display: 'flex', flexDirection: 'column' }}
                  elevation={0}
                >
                  <Typography gutterBottom variant="h6" component="h3">
                    <img src='https://source.unsplash.com/random' style={{borderRadius:'70%', objectFit:'cover', height:'20px', width:'20px'}}></img>
                    SmartToy
                  </Typography>
                  <CardMedia 
                    component="img"
                    sx={{
                      pb: 1,
                      objectFit:'cover',
                      width:'300px',
                      height:'300px'
                    }}
                    image="https://source.unsplash.com/random"
                    alt="random"
                  />
                  <CardActions sx={{justifyContent: 'space-between', margin:'0px', padding:'0px'}}>
                    <Button 
                    size="small" 
                    onClick={handleLike} 
                    style={{color:'black', 
                            justifyContent:'flex-start', 
                            margin:'0px', 
                            padding:'0px'}}>
                              {like ? (<Favorite></Favorite>):(<FavoriteBorderIcon></FavoriteBorderIcon>)}
                              30
                              </Button>
                    <Button 
                    size="small" 
                    onClick={handleSave} 
                    style={{color:'black', 
                            justifyContent:'flex-end', 
                            margin:'0px', 
                            padding:'0px'}}>
                              {save ? (<TurnedInIcon></TurnedInIcon>):(<TurnedInNotIcon></TurnedInNotIcon>)}
                              </Button>
                  </CardActions>
                </Card>
              </Grid>
            ))}
          </Grid>
        </Container>
      </main>
      {/* Footer */}
      <Box sx={{ bgcolor: 'background.paper', p: 4 }} component="footer">
        <div style={{display:'flex', justifyContent:'center', pb:7}}>
          <Button variant='outlined' color='primary' size='large' onClick={handleCard}>더보기</Button> 
          {/* 여기에 axios 사용 */}
        </div>
        <Typography
          variant="subtitle1"
          align="center"
          color="text.secondary"
          component="p"
        >
          Troupe
        </Typography>
        <Copyright />
      </Box>
      {/* End footer */}
    </ThemeProvider>
  );
}

