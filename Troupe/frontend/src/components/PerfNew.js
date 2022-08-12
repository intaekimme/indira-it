import apiClient from '../apiClient';
import React, { Fragment } from 'react';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';


const theme = createTheme();

export default function PerfNew() {
  //공연 포스터
  const [imgUrl, setImgUrl] = React.useState([]);
  //공연 제목
  const [perfName, setPerfName] = React.useState("");
  //공연시작 일자
  const [perfStartDate, setPerfStartDate] = React.useState("");
  //공연 종료 일자
  const [perfEndDate, setPerfEndDate] = React.useState('');
  //공연시간
  const [runtime, setRuntime] = React.useState(0);
  //가격, 좌석
  const [seatPrice, setSeatPrice] = React.useState([{seat:'', price:0}]);
  //공연 장르
  const [genre, setGenre] = React.useState('')

  
  //image 업로드
  const changeImage = (e) => {
    const imageLists = e.target.files;
    let imageUrlLists = [...imgUrl];

    for (let i = 0; i < imageLists.length; i++){
        const currentImageUrl = URL.createObjectURL(imageLists[i]);
        imageUrlLists.push(currentImageUrl);
    }

    if (imageUrlLists.length > 10){
        imageUrlLists = imageUrlLists.slice(0,10);
    }

    setImgUrl(imageUrlLists);
  };

  //공연제목 Change
  const changePerfName = (e) =>{
     setPerfName(e.target.value);
  }

  //공연시작일 Change
  const changePerfStartDate = (e) =>{
    setPerfStartDate(e.target.value);
    console.log(e.target.value);
  }

  //공연종료일 Change
  const changePerfEndDate = (e) =>{
    setPerfEndDate(e.target.value);
    console.log(e.target.value)
  }
  //공연 장르 change
  const changeGenre = (e) => {
    setGenre(e.target.value)
    console.log(e.target.value)
  }

  // 좌석 가격 change
  const handleSeatPrice = (e, index) =>{
    const { name, value } = e.target;
    const list = [...seatPrice];
    list[index][name] = value;
    setSeatPrice(list);
  }

  // 좌석 가격 field 추가
  const addSeatPrice = (e) =>{
    setSeatPrice([...seatPrice, { seat: "", price: 0 }]);
  }

  //좌석 가격 field 삭제
  const deleteSeatPrice = (index) => {
    const list = [...seatPrice];
    list.splice(index, 1);
    setSeatPrice(list);
  }

  // 공연 제목 길이 체크
  function titleLength(e){
    if(e.target.value.length > 100){
      alert('글자수 초과!')
      e.target.value = e.target.value.substring(0,100);
      e.target.focus();
    }
  }

  // 공연 소개 길이 체크
  function descriptionLength(e){
    if(e.target.value.length > 1000){
      alert('글자수 초과!')
      e.target.value = e.target.value.substring(0,1000);
      e.target.focus();
    }
  }
  
  //회원가입버튼 클릭
  const handleSubmit = (event) => {
    event.preventDefault();
    const formData = new FormData(event.currentTarget);
    console.log(event.currentTarget)
    console.log(formData);

    const data = {
      perfImage: formData.get('imgUpload'),
      title: formData.get('title'),
      startDate: formData.get('start'),
      endDate: formData.get('end'),
      seatPrice: seatPrice,
      genre: formData.get('genre'),
      description: formData.get('description'),
    };
    console.log(data);

    apiClient.perfNew(data);
  };

  
  

  return (
    <ThemeProvider theme={theme}>
      <Container maxWidth="md">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <form
            onSubmit={handleSubmit}
            encType="multipart/form-data"
            style={{ textAlign: "center" }}
          >
            <Grid container spacing={2}>
              <Grid item xs={11}> 
                {imgUrl ? (
                    <div>
                    { imgUrl.map((image, id) => (
                        <img src={image} alt='' style={{height:'70px', width:'70px'}}></img>
                    ))}
                    </div>
                ): (
                <Box style={{height:'100px', width:'100px'}}></Box>
                )}
              </Grid>
              <Grid item xs={1} style={{ position: "relative" }}>
                <Button
                  style={{
                    position: "absolute",
                    width: "80px",
                    height: "30px",
                    bottom: "0px",
                    right: "0px",
                    backgroundColor: "#CCCCCC",
                    color: "black",
                  }}
                >
                  찾아보기
                </Button>
                <input
                  style={{
                    position: "absolute",
                    width: "80px",
                    height: "30px",
                    bottom: "0px",
                    right: "0px",
                    opacity: "0%",
                  }}
                  id="imgUpload"
                  className="imgUpload"
                  name="imgUpload"
                  multiple
                  type="file"
                  accept="image/*"
                  onChange={changeImage}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  name="title"
                  fullWidth
                  required
                  id="title"
                  label="공연 제목"
                  autoFocus
                  onChange = { changePerfName }
                  onKeyUp = {titleLength}
                />
              </Grid>
              <Grid item xs={12}>
                {seatPrice.map((x, i) => {
          return (
            <div>
              <input
                id='seat'
                name="seat"
                placeholder="좌석명"
                value={x.seat}
                onChange={e => handleSeatPrice(e, i)}
              />
              <input
                id='price'
                className="ml10"
                name="price"
                placeholder="가격"
                onChange={e => handleSeatPrice(e, i)}
              />
                {seatPrice.length !== 1 && <button
                  className="mr10"
                  onClick={() => deleteSeatPrice(i)}>Remove</button>}
                {seatPrice.length - 1 === i && <button onClick={addSeatPrice}>Add</button>}
            </div>
          );
        })}
              </Grid>

              <Grid item xs={5}>
              <input 
                required
                type="date" 
                id="start" 
                name="start"
                label='starting date'
                min="2020-01-01" 
                max="2025-12-31"
                onChange={changePerfStartDate}></input>
              </Grid>
            
              <Grid item xs={2}>
                  <Typography style={{fontSize:'large', fontFamily:'IBM Plex Sans KR'}}>~</Typography>
              </Grid>

              <Grid item xs={5}>
              <input 
                required
                type="date" 
                id="end" 
                name="end"
                label='end date'
                min="2020-01-01" 
                max="2025-12-31"
                onChange={changePerfEndDate}></input>
              </Grid>

              <Grid item xs={6}>
              <FormControl fullWidth>
                <InputLabel id="genre">장르</InputLabel>
                <Select
                  name='genre'
                  labelId="genre"
                  id="genre"
                  value={genre}
                  label="장르"
                  onChange={changeGenre}
                >
                  <MenuItem value={'뮤지컬'}>뮤지컬</MenuItem>
                  <MenuItem value={'연극'}>연극</MenuItem>
                  <MenuItem value={'국악'}>국악</MenuItem>
                </Select>
              </FormControl>
              </Grid>

              <Grid item xs={6}>
                <TextField
                  required
                  fullWidth
                  name="runtime"
                  label="공연 시간"
                  id="runtime"
                />
              </Grid>

              <Grid item xs={12}>
                <TextField
                  fullWidth
                  multiline
                  id="description"
                  label="공연 소개"
                  rows={6}
                  name="description"
                  onKeyUp={descriptionLength}
                />
              </Grid>

              <Grid item xs={3}>
                <Button
                type="submit"
                fullWidth
                variant="contained"
                sx={{ mt: 3, mb: 2 }}
                >
                등록하기
                </Button>
              </Grid>
              <Grid item xs={3}>
                <Button
                fullWidth
                variant="contained"
                color='error'
                sx={{ mt: 3, mb: 2 }}
                href='/perf/list'
                >
                취소하기
                </Button>
              </Grid>
            </Grid>
          </form>
        </Box>
      </Container>
    </ThemeProvider>
  );
}