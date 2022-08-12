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
import FeedSaveButton from './FeedSaveButton'
import FeedLikeButton from './FeedLikeButton'
import {Typography} from "@mui/material";
import Modal from "@mui/material/Modal";
import stylesModal from "../css/modal.module.css";
import { Fragment } from "react";



export default function FeedListFollow(){
  let FeedListFollowQuery = useInfiniteQuery('FollowFeed', apiClient.getFollowFeedList, {getNextPageParam: (lastPage, pages) => {return pages.length + 1}})
  console.log(FeedListFollowQuery.data)
  console.log(FeedListFollowQuery.isLoading)

  if (!FeedListFollowQuery.isLoading) {
    return (
      <Fragment>
          <Grid container spacing={4}>
            {FeedListFollowQuery.data.pages.map((page) => (
              page.map((datum) => (
                <Grid item key={datum.feedNo} xs={12} sm={6} md={4}>
                <Card
                  sx={{ position:'relative', height: '100%', display: 'flex', flexDirection: 'column' }}
                  elevation={0}
                >
                  <Typography gutterBottom style={{fontSize:'20px', fontFamily:'IBM Plex Sans KR'}} component="span">
                    <img src={datum.profileImageUrl} alt='' style={{borderRadius:'70%', objectFit:'cover', height:'20px', width:'20px'}}></img>
                    {datum.nickname}
                  </Typography>
                  <Link href={'/feed/test'}>
                    <CardMedia 
                      component="img"
                      sx={{
                        pb: 1,
                        objectFit:'cover',
                        width:'300px',
                        height:'300px'
                      }}
                      image = {Object.values(datum.images)[0]}
                      alt=""
                    >
                    </CardMedia>
                  </Link>
                  <CardActions sx={{justifyContent: 'space-between', margin:'0px', padding:'0px'}}>
                    <FeedLikeButton></FeedLikeButton>
                    <FeedSaveButton></FeedSaveButton>
                  </CardActions>
                </Card>
              </Grid>
              ))
            ))}
          </Grid>
          <div style={{display:'flex', justifyContent:'center', pb:7}}>
            <Link href=""></Link>
            <Button variant='outlined' color='primary' size='large' onClick={FeedListFollowQuery.fetchNextPage}>더보기</Button> 
          </div>
      </Fragment>
    )
  }
  }