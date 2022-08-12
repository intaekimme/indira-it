import React from "react"
import { Button } from "@mui/material"
import TurnedInNotIcon from '@mui/icons-material/TurnedInNot';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
import TurnedInIcon from '@mui/icons-material/TurnedIn';
import Favorite from '@mui/icons-material/Favorite';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardMedia from '@mui/material/CardMedia';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import Link from '@mui/material/Link';
import { useInfiniteQuery, useQuery } from 'react-query';
import apiClient from '../apiClient';
import { useParams } from "react-router-dom";



export default function PerfListCard(){
  // let {isLoading, data} = useQuery('performanceList', async () => await apiClient.getPerfList(pageNumber));
  let pageNumber = useParams().pageNumber;  
  let performanceListQuery = useInfiniteQuery('performanceList', apiClient.getPerfList, {getNextPageParam: (lastPage, pages) => {return pages.length + 1 }})
  console.log(performanceListQuery.data)
  console.log(performanceListQuery.isLoading)

  const [like, setLike] = React.useState(false)
  const [save, setSave] = React.useState(false)

  function handleLike() {
    setLike(!like)
  }

  function handleSave() {
    setSave(!save)
  }

  if (!performanceListQuery.isLoading) {
    return (
          <Grid container spacing={4}>
            
            {performanceListQuery.data.pages.map((page) => (
              page.map((datum) => (
                <Grid item key={datum.pfNo} xs={12} sm={6} md={4}>
                <Card
                  sx={{ position:'relative', height: '100%', display: 'flex', flexDirection: 'column' }}
                  elevation={0}
                >
                  {/* <Typography gutterBottom style={{fontSize:'20px', fontFamily:'IBM Plex Sans KR'}} component="span">
                    <img src={data.image} alt='' style={{borderRadius:'70%', objectFit:'cover', height:'20px', width:'20px'}}></img>
                  </Typography> */}
                  <Box style={{ fontFamily:'IBM Plex Sans KR', background:'pink', borderRadius:'10%', position:'absolute', top:'45px', right:'5px',  i:'3'}}>상영 중</Box>
                  <Box style={{ fontFamily:'IBM Plex Sans KR', background:'skyblue', borderRadius:'10%', position:'absolute', top:'45px', right:'60px',  i:'3'}}>뮤지컬</Box>
                  <Link href={'/perf/detail'}>
                    <CardMedia 
                      component="img"
                      sx={{
                        pb: 1,
                        objectFit:'cover',
                        width:'300px',
                        height:'300px'
                      }}
                      image = {Object.values(datum.image)[0]}
                      alt=""
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
              ))
            ))}
          <div style={{display:'flex', justifyContent:'center', pb:7}}>
            <Button variant='outlined' color='primary' size='large' onClick={performanceListQuery.fetchNextPage}>더보기</Button> 
          </div>
          </Grid>
    )
  }
  }
