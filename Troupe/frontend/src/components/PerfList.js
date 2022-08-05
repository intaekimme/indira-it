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
import { createTheme, ThemeProvider} from '@mui/material/styles';
import TurnedInNotIcon from '@mui/icons-material/TurnedInNot';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
import TurnedInIcon from '@mui/icons-material/TurnedIn';
import Favorite from '@mui/icons-material/Favorite';
import SearchBar from './SearchBar'
import PerfFeedToggle from './MainButton';
import apiClient from '../apiClient';
import { useParams } from 'react-router-dom';

// 무한 스크롤 구현 필요(더보기 버튼을 활용한, axios)
// 유저 분간을 어떻게 하지

function Copyright() {
  return (
    <Typography color="text.secondary" align="center" component="span">
      {'Copyright © '}
      <Link color="inherit" href="/">
        Troupe
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

function range(start, end) {
  let array = [];
  for (let i = start; i < end; ++i) {
    array.push(i);
  }  return array;
}

const theme = createTheme();

export default function PerfList() {

  const {startNo} = useParams();

  let [perfList, setPerfList] = React.useState([]);
  React.useEffect((startNo) => {
    apiClient.getPerfList(startNo).then((data)=>{setPerfList(data)});
  }, []);
  const [like, setLike] = React.useState(false)
  const [save, setSave] = React.useState(false)
  let [cards, setCard] = React.useState([])

  function handleLike() {
    setLike(!like)
  }

  function handleSave() {
    setSave(!save)
  }

  function handleCard() {
    setCard(range(0, 6))
  }

  async function handlePerfList() {
    const MyData = await apiClient.getPerfList()
    setPerfList(MyData)
  }
  
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <div style={{display:'flex', justifyContent:'center'}}>
        <SearchBar></SearchBar>
      </div>
      <div style={{display:'flex', justifyContent:'center'}}>
        <PerfFeedToggle></PerfFeedToggle>
      </div>
      <div>
        <Container sx={{ py: 10 }} maxWidth="md">
          <Grid container spacing={4}>
            {cards.map((card) => (
              <Grid item key={card} xs={12} sm={6} md={4}>
                <Card
                  sx={{ position:'relative', height: '100%', display: 'flex', flexDirection: 'column' }}
                  elevation={0}
                >
                  <Typography gutterBottom style={{fontSize:'20px', fontFamily:'IBM Plex Sans KR'}} component="span">
                    <img src='https://source.unsplash.com/random' alt='random' style={{borderRadius:'70%', objectFit:'cover', height:'20px', width:'20px'}}></img>
                    Author
                  </Typography>
                  <Box style={{ fontFamily:'IBM Plex Sans KR', background:'pink', borderRadius:'10%', position:'absolute', top:'45px', right:'5px', zIndex:'3'}}>상영 중</Box>
                  <Box style={{ fontFamily:'IBM Plex Sans KR', background:'skyblue', borderRadius:'10%', position:'absolute', top:'45px', right:'60px', zIndex:'3'}}>뮤지컬</Box>
                  <Link href='/perf/detail'>
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
                    >
                    </CardMedia>
                  </Link>
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
      </div>
      {/* Footer */}
      <Box sx={{ bgcolor: 'background.paper', p: 4 }}>
        <div style={{display:'flex', justifyContent:'center', pb:7}}>
          <Button variant='outlined' color='primary' size='large' onClick={handleCard}>더보기</Button> 
        </div>
        <Typography
          variant="subtitle1"
          align="center"
          color="text.secondary"
          component="span"
        >
          Troupe
        </Typography>
        <Copyright />
      </Box>
      {/* End footer */}
    </ThemeProvider>
  );
}

