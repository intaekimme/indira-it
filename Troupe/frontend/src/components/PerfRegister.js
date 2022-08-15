import apiClient from "../apiClient";
import React, { Fragment } from "react";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import RemoveCircleOutlineIcon from "@mui/icons-material/RemoveCircleOutline";
import stylesTag from "../css/tag.module.css";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import Theme from "./Theme";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
export default function PerfNew() {
  //공연 포스터
  const [imgUrl, setImgUrl] = React.useState([]);

  const [images, setImages] = React.useState([]);

  //공연 제목
  const [perfName, setPerfName] = React.useState("");
  //가격, 좌석
  const [seatPrice, setSeatPrice] = React.useState([{ seat: "", price: 0 }]);
  //공연시작 일자
  const [perfStartDate, setPerfStartDate] = React.useState(new Date());
  //공연 종료 일자
  const [perfEndDate, setPerfEndDate] = React.useState(new Date());
  //공연 카테고리
  const [category, setCategory] = React.useState("");
  //공연시간
  const [runtime, setRuntime] = React.useState(0);
  //공연 소개
  const [description, setDescription] = React.useState("");
  //공연 장소
  const [location, setLocation] = React.useState("");

  const [value, setValue] = React.useState(null);
  //  공연 등록 버튼 클릭
  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    // console.log(event.currentTarget);
    // console.log(formData);

    let imageList = [...images];
    imageList.forEach((item) => {
      data.append("images", item);
    });
    data.append("seatPrice", JSON.stringify(seatPrice));
    // data.append("startDate", JSON.stringify(perfStartDate));
    // data.append("endDate", JSON.stringify(perfEndDate));
    // const request = {
    //   images: data.getAll("images"),
    //   title: data.get("title"),
    //   startDate: data.get("startDate"),
    //   endDate: data.get("endDate"),
    //   seatPrice: JSON.stringify(seatPrice),
    //   category: data.get("category"),
    //   description: data.get("description"),
    //   runtime: data.get("runtime"),
    //   location: data.get("location"),
    // };

    apiClient.perfNew(data);
  };

  //image 업로드
  const changeImage = (event) => {
    const imageLists = event.target.files;
    let imageUrlLists = [...imgUrl];
    let imageList = [...images];
    for (let i = 0; i < imageLists.length; i++) {
      const currentImageUrl = URL.createObjectURL(imageLists[i]);
      imageUrlLists.push({ url: currentImageUrl, file: imageLists[i] });
      imageList.push(imageLists[i]);
    }

    console.log(imageUrlLists);
    console.log(imageList);
    if (imageUrlLists.length > 10) {
      imageUrlLists = imageUrlLists.slice(0, 10);
      imageList = imageList.slice(0, 10);
      alert("최대 10개 까지 업로드 할 수 있습니다");
    }

    setImgUrl(imageUrlLists);
    // console.log(imgUrl);
    setImages(imageList);
    // console.log(images);
  };

  //공연제목 Change
  const changePerfName = (e) => {
    setPerfName(e.target.value);
  };

  //공연시작일 Change
  const changePerfStartDate = (e) => {
    setPerfStartDate(e.target.value);
    // console.log(e.target.value);
  };

  //공연종료일 Change
  const changePerfEndDate = (e) => {
    setPerfEndDate(e.target.value);
    // console.log(e.target.value);
  };
  //공연 장르 change
  const changeCategory = (e) => {
    setCategory(e.target.value);
    // console.log(e.target.value);
  };

  // 좌석 가격 change
  const handleSeatPrice = (e, index) => {
    const { name, value } = e.target;
    const list = [...seatPrice];
    list[index][name] = value;
    setSeatPrice(list);
  };

  //  공연 시간 change
  const changeRuntime = (e) => {
    setRuntime(e.target.value);
  };
  //  공연 소개 change
  const changeDescription = (e) => {
    setDescription(e.target.value);
  };
  //  공연 장소 change
  const changeLocation = (e) => {
    setLocation(e.target.value);
  };

  // 좌석 가격 field 추가
  const addSeatPrice = (e) => {
    setSeatPrice([...seatPrice, { seat: "", price: 0 }]);
  };

  //좌석 가격 field 삭제
  const deleteSeatPrice = (index) => {
    const list = [...seatPrice];
    list.splice(index, 1);
    setSeatPrice(list);
  };

  // 공연 제목 길이 체크
  function titleLength(e) {
    if (e.target.value.length > 100) {
      alert("글자수 초과!");
      e.target.value = e.target.value.substring(0, 100);
      e.target.focus();
    }
  }

  // // 공연 소개 길이 체크
  // function descriptionLength(e) {
  //   if (e.target.value.length > 1000) {
  //     alert("글자수 초과!");
  //     e.target.value = e.target.value.substring(0, 1000);
  //     e.target.focus();
  //   }
  // }

  //  공연 이미지 삭제
  function deleteImage(imgFile) {
    setImgUrl(imgUrl.filter((imgUrl) => imgUrl.file !== imgFile));
    setImages(images.filter((images) => images !== imgFile));
    // console.log(images);
  }

  //  공연 등록 폼 취소
  const cancelForm = () => {
    if (window.confirm("등록을 취소하시겠습니까?")) {
      window.location.href = "/perf/list/0";
    } else {
      return;
    }
  };

  return (
    <ThemeProvider theme={Theme}>
      <Container maxWidth="md">
        <CssBaseline />
        <Box
          sx={{
            marginTop: "3%",
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            paddingBottom: "400px",
            textAlign: "center",
          }}
        >
          <Grid container spacing={2} style={{ fontFamily: "SBAggroB" }}>
            <Grid item xs={12}>
              {imgUrl ? (
                <div>
                  {imgUrl.map((item, id) => (
                    <span key={id} className={stylesTag.img}>
                      <img
                        key={id}
                        src={item.url}
                        alt=""
                        style={{
                          height: "450px",
                          width: "300px",
                          border: "3px white solid",
                          boxShadow:
                            "0 10px 35px rgba(0, 0, 0, 0.05), 0 6px 6px rgba(0, 0, 0, 0.2)",
                        }}
                      ></img>
                      <RemoveCircleOutlineIcon
                        color="error"
                        onClick={() => deleteImage(item.file)}
                        className={stylesTag.btn1}
                        style={{ top: "-420px" }}
                      ></RemoveCircleOutlineIcon>
                    </span>
                  ))}
                </div>
              ) : (
                <Box style={{ height: "100px", width: "100px" }}></Box>
              )}
              <Button
                variant="contained"
                component="label"
                color="green"
                style={{ fontFamily: "SBAggroB", marginTop: "10px" }}
              >
                + 포스터 추가
                <input type="file" multiple hidden onChange={changeImage} />
              </Button>
            </Grid>
            <Grid item xs={12}>
              <TextField
                name="title"
                fullWidth
                required
                id="title"
                label="공연 제목"
                autoFocus
                onChange={changePerfName}
                inputProps={{
                  maxLength: 50,
                }}
                style={{ backgroundColor: "white" }}
              />
            </Grid>
            <Grid item xs={12}>
              {seatPrice.map((x, i) => {
                return (
                  <Grid>
                    <TextField
                      id="seat"
                      name="seat"
                      label="좌석명"
                      value={x.seat}
                      onChange={(e) => handleSeatPrice(e, i)}
                      style={{
                        margin: "5px",
                        marginTop: "10px",
                        backgroundColor: "white",
                      }}
                      inputProps={{
                        maxLength: 10,
                      }}
                    />
                    <TextField
                      id="price"
                      className="ml10"
                      name="price"
                      label="가격"
                      onChange={(e) => handleSeatPrice(e, i)}
                      style={{
                        margin: "5px",
                        marginTop: "10px",
                        backgroundColor: "white",
                      }}
                      type="number"
                    />

                    {seatPrice.length !== 1 && (
                      <Button
                        className="mr10"
                        onClick={() => deleteSeatPrice(i)}
                        style={{ marginTop: "10px" }}
                      >
                        <RemoveCircleOutlineIcon color="error"></RemoveCircleOutlineIcon>
                      </Button>
                    )}
                    {seatPrice.length - 1 === i && (
                      <Button
                        onClick={addSeatPrice}
                        style={{ marginTop: "10px" }}
                      >
                        <AddCircleIcon color="success"></AddCircleIcon>
                      </Button>
                    )}
                  </Grid>
                );
              })}
            </Grid>

            <Grid item xs={5}>
              <LocalizationProvider dateAdapter={AdapterDateFns}>
                <DatePicker
                  id="start"
                  name="start"
                  label="시작 날짜"
                  min="2020-01-01"
                  max="2025-12-31"
                  value={perfStartDate}
                  onChange={(newValue) => {
                    setPerfStartDate(newValue);
                  }}
                  renderInput={(params) => <TextField {...params} />}
                />
              </LocalizationProvider>
              {/* <input
                required
                type="date"
                id="start"
                name="start"
                label="starting date"
                min="2020-01-01"
                max="2025-12-31"
                onChange={changePerfStartDate}
              ></input> */}
            </Grid>

            <Grid item xs={2}>
              <Typography style={{ fontSize: "large", marginTop: "12px" }}>
                ~
              </Typography>
            </Grid>

            <Grid item xs={5}>
              <LocalizationProvider dateAdapter={AdapterDateFns}>
                <DatePicker
                  id="end"
                  name="end"
                  label="종료 날짜"
                  min="2020-01-01"
                  max="2025-12-31"
                  value={perfEndDate}
                  onChange={(newValue) => {
                    setPerfEndDate(newValue);
                  }}
                  renderInput={(params) => <TextField {...params} />}
                />
              </LocalizationProvider>
              {/* <input
                required
                type="date"
                id="end"
                name="end"
                label="end date"
                min="2020-01-01"
                max="2025-12-31"
                onChange={changePerfEndDate}
              ></input> */}
            </Grid>

            <Grid item xs={4}>
              <FormControl fullWidth>
                <InputLabel id="category">카테고리</InputLabel>
                <Select
                  name="category"
                  labelId="category"
                  id="category"
                  value={category}
                  label="카테고리"
                  onChange={changeCategory}
                  style={{ backgroundColor: "white" }}
                >
                  <MenuItem value={"뮤지컬"}>뮤지컬</MenuItem>
                  <MenuItem value={"연극"}>연극</MenuItem>
                  <MenuItem value={"국악"}>국악</MenuItem>
                </Select>
              </FormControl>
            </Grid>

            <Grid item xs={4}>
              <TextField
                required
                fullWidth
                name="runtime"
                label="공연 시간"
                id="runtime"
                onChange={changeRuntime}
                style={{ backgroundColor: "white" }}
              />
            </Grid>

            <Grid item xs={4}>
              <TextField
                required
                fullWidth
                name="location"
                label="공연 장소"
                id="location"
                onChange={changeLocation}
                style={{ backgroundColor: "white" }}
              />
            </Grid>

            <Grid item xs={12}>
              <TextField
                multiline
                rows={6}
                fullWidth
                id="description"
                label="공연 소개"
                name="description"
                onChange={changeDescription}
                inputProps={{
                  maxLength: 2000,
                }}
                style={{ backgroundColor: "white" }}
              />
              <div
                style={{
                  textAlign: "right",
                  color: "grey",
                }}
              >
                {description.length}/2000
              </div>
            </Grid>
            <Grid container spacing={2}>
              <Grid item xs={8}></Grid>
              <Grid item xs={2}>
                <Button
                  fullWidth
                  variant="contained"
                  color="inherit"
                  sx={{ mt: 3, mb: 2 }}
                  onClick={() => cancelForm()}
                  style={{ fontFamily: "SBAggroB" }}
                >
                  취소
                </Button>
              </Grid>
              <Grid item xs={2}>
                <form
                  onSubmit={handleSubmit}
                  encType="multipart/form-data"
                  style={{ textAlign: "center" }}
                >
                  <input type="hidden" value={perfName} name="title"></input>
                  {/* <input type="hidden" value={seatPrice} multiple name="seatPrice"></input> */}
                  <input
                    type="hidden"
                    value={perfStartDate}
                    name="startDate"
                  ></input>
                  <input
                    type="hidden"
                    value={perfEndDate}
                    name="endDate"
                  ></input>
                  <input type="hidden" value={category} name="category"></input>
                  <input type="hidden" value={runtime} name="runtime"></input>
                  <input
                    type="hidden"
                    value={description}
                    name="description"
                  ></input>
                  <input type="hidden" value={location} name="location"></input>

                  <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                    color="green"
                    sx={{ mt: 3, mb: 2 }}
                    style={{ fontFamily: "SBAggroB" }}
                  >
                    공연 등록
                  </Button>
                </form>
              </Grid>
            </Grid>
          </Grid>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
