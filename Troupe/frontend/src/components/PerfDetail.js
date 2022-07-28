import React, { Fragment } from 'react'
import { styled } from '@mui/material/styles';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import ToysIcon from '@mui/icons-material/Toys';
import { Button } from '@mui/material'
import { Chip } from '@mui/material';
import MUICarousel from 'react-material-ui-carousel';

// 제목, 기간, 시간, 장소, 티켓가격

const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#EEE3D0',
  ...theme.typography.body2,
  padding: theme.spacing(1),
  textAlign: 'center',
  color: theme.palette.text.secondary,
}));

function Carousel(props)
{
    var items = [
        {
            name: "Random Name #1",
            description: "Probably the most random thing you have ever seen!"
        },
        {
            name: "Random Name #2",
            description: "Hello World!"
        }
    ]

    return (
        <MUICarousel animation='slide' indicators=''>
            {
                items.map( (item, i) => <CarouselItem key={i} item={item} /> )
            }
        </MUICarousel>
    )
}

function CarouselItem(props)
{
    return (
        <Paper style = {{backgroundColor: '#EEE3D0'}} elevation={0}>
            <img src='https://source.unsplash.com/random'></img>
        </Paper>
    )
}

function ModifyDeleteButton() {
  return (
    <div style = {{float:'right', backgroundColor:'#EEE3D0'}}>
            <Button variant='outlined' href='/perf/list' style={{margin: '8px', backgroundColor:'beige', fontFamily: 'IBM Plex Sans KR'}}>목록</Button>
        <Fragment>
            <Button variant='outlined' href='/feed/list' style={{margin: '8px', backgroundColor:'skyblue', fontFamily: 'IBM Plex Sans KR'}}>수정</Button>
            <Button variant='outlined' style={{margin: '8px', backgroundColor:'pink', fontFamily: 'IBM Plex Sans KR'}}>삭제</Button>
        </Fragment>
    </div>
  );
} 

function perfDetail() {
  return (
    <div style={{height:'100vh', width:'100vw', background:'#EEE3D0', fontFamily:'IBM Plex Sans KR'}}>
      <h1 style={{margin:'0px'}}>
        Performance Detail
      </h1>
        <ModifyDeleteButton/>
      <div>
        <Grid container spacing={4}>
          <Grid item xs={5}>
            <Item elevation={0} style={{position:'relative'}}>
                <Carousel>
                </Carousel>
                <Chip label='상영 중' color='danger'  style={{position:'absolute', top:'0px', right:'0px', zIndex:'3'}}></Chip>
                <Chip label='뮤지컬' color='primary'  style={{position:'absolute', top:'0px', right:'70px', zIndex:'3'}}></Chip>
            </Item>
          </Grid>
          <Grid item xs={7}>
            <Item elevation={0}>
              <ul style={{listStyle:'none', paddingLeft:'0px', fontFamily:'IBM Plex Sans KR', textAlign:'left'}}>
                <li>
                  <a style={{textDecoration:'none'}} href='#'>
                  <ToysIcon fontSize='small'></ToysIcon>짱아
                  </a>
                </li>
                <li>제목: 햄릿</li>
                <li>가격: 전석 33,000원</li>
                <li>시간: 월, 수, 금 8시</li>
                <li>기간: 2022.06.19 ~ 2022.09.13</li>
                <li>장소: 예술의 전당</li>
              </ul>
            </Item>
          </Grid>
        </Grid>
        <div style={{margin:'12px'}}>
        <Grid container spacing = {1}>
          <Grid item xs={12}>
            <Item>댓글란</Item>
          </Grid>
        </Grid>
        </div>
      </div>
    </div>
  );
} 

export default perfDetail;