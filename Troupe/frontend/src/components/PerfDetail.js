import React, { Fragment } from 'react'
import { styled } from '@mui/material/styles';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import { Button } from '@mui/material'
import MUICarousel from 'react-material-ui-carousel';
import Modal from '@mui/material/Modal'
import Box from '@mui/material/Box';
// import Chip from '@mui/material/Chip'

// 제목, 기간, 시간, 장소, 티켓가격
// MUI칩이 갑자기 오류가 났다,,,,
// 모달로 포스터 크게 하기

const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#FFF',
  ...theme.typography.body2,
  padding: theme.spacing(1),
  textAlign: 'center',
  color: theme.palette.text.secondary,
}));

const modalStyle = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4,
};

function Carousel(props)
{
  function CarouselItem(props)
  {
    const [open, setOpen] = React.useState(false)
    function handleOpen() {
      setOpen(true)
    }
    function handleClose() {
      setOpen(false)
    }
      return (
          <Paper style = {{backgroundColor: '#FFFF'}} elevation={0}>
              <img onClick={handleOpen} src='https://source.unsplash.com/random' alt='random' style={{height:'500px', width:'400px'}}></img>
              <Modal
              open={open}
              onClose={handleClose}
              aria-labelledby="modal-modal-title"
              aria-describedby="modal-modal-description"
            >
              <Box style={{position:'absolute', top:'2%', left:'30%', height:'700px', width:'550px'}}>
                <img onClick={handleOpen} src='https://source.unsplash.com/random' alt='random' style={{height:'700px', width:'550px'}}></img>
              </Box>
              </Modal>
            <Fragment>
                  <Box style={{ fontFamily:'IBM Plex Sans KR', background:'pink', borderRadius:'10%', position:'absolute', top:'15px', right:'115px', zIndex:'3'}}>상영 중</Box>
                  <Box style={{ fontFamily:'IBM Plex Sans KR', background:'skyblue', borderRadius:'10%', position:'absolute', top:'15px', right:'165px', zIndex:'3'}}>뮤지컬</Box>
            </Fragment>
          </Paper>
      )
  }
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


function ModifyDeleteButton() {
  const [user, setUser] = React.useState(true)
  return (
    <div style = {{float:'right', backgroundColor:'#FFF'}}>
            <Button variant='outlined' href='/perf/list' style={{margin: '8px', backgroundColor:'beige', fontFamily: 'IBM Plex Sans KR'}}>목록</Button>
        { user? (
        <Fragment>
            <Button variant='outlined' href='/feed/list' style={{margin: '8px', backgroundColor:'skyblue', fontFamily: 'IBM Plex Sans KR'}}>수정</Button>
            <Button variant='outlined' style={{margin: '8px', backgroundColor:'pink', fontFamily: 'IBM Plex Sans KR'}}>삭제</Button>
        </Fragment>):('')
        }
    </div>
  );
} 


function PerfDetail() {
  return (
    <div style={{height:'100vh', width:'100vw', background:'#FFFF', fontFamily:'IBM Plex Sans KR'}}>
      {/* <h1 style={{margin:'0px'}}>
        Performance Detail
      </h1> */}
        <ModifyDeleteButton/>
      <div>
        <Grid container spacing={4}>
          <Grid item xs={5}>
            <Item elevation={0} style={{position:'relative', direction:'row'}}>
                <Carousel>
                </Carousel>

            </Item>
          </Grid>
          <Grid item xs={7}>
            <Item elevation={0}>
              <ul style={{fontSize:'large', listStyle:'none', paddingLeft:'0px', fontFamily:'IBM Plex Sans KR', textAlign:'left'}}>
                <li>
                  <a style={{textDecoration:'none'}} href='/'>
                  <img src='https://source.unsplash.com/random' alt='random' style={{borderRadius:'70%', objectFit:'cover', height:'20px', width:'20px'}}></img>
                  짱아
                  </a>
                </li>
                <li>제목: 햄릿</li>
                <br></br>
                <li>가격: 전석 33,000원</li>
                <br></br>
                <li>시간: 월, 수, 금 8시</li>
                <br></br>
                <li>기간: 2022.06.19 ~ 2022.09.13</li>
                <br></br>
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

export default PerfDetail;