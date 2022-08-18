import apiClient from "../apiClient";
import React, { Fragment, useEffect } from "react";
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
import { useLocation } from "react-router-dom";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import Theme from "./Theme";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import Swal from 'sweetalert2'
import withReactContent from 'sweetalert2-react-content'
const MySwal = withReactContent(Swal)
export default function PerfModify() {
  const location1 = useLocation();
  const performanceNo = location1.pathname.split("/")[3];
  // console.log("location1", location1.pathname.split("/")[3]);

  // 추가된 이미지 url
  const [imgUrl, setImgUrl] = React.useState([]);
  // 추가된 이미지파일
  const [images, setImages] = React.useState([]);
  // 기존 이미지 번호+url
  const [oldImage, setOldImage] = React.useState(new Map());
  // 삭제된 이미지 번호
  const [imageNo, setImageNo] = React.useState([]);
  // 삭제된 이미지 번호들의 키(filter거르면 key값 변경되기 때문에 원본 서치용)
  const [imgKeys, setImgKeys] = React.useState(new Map());

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

  useEffect(() => {
    apiClient.getPerfDetail(performanceNo).then((data) => {
      console.log(data);
      setCategory(data.category);
      setDescription(data.description);
      setPerfStartDate(data.startDate);
      setPerfEndDate(data.endDate);

      setImgKeys(data.imageUrl);
      setOldImage(data.imageUrl);

      setLocation(data.location);
      setRuntime(data.runtime);
      setPerfName(data.title);
      setSeatPrice(data.price);
    });
  }, []);

  //  공연 수정 버튼 클릭
  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    console.log(event.currentTarget);
    // console.log(data);

    //  날짜 데이터 미수정시 변환
    if (
      perfStartDate == data.get("startDate") &&
      perfEndDate != data.get("endDate")
    ) {
      data.set("startDate", new Date(perfStartDate));
    } else if (
      perfStartDate != data.get("startDate") &&
      perfEndDate == data.get("endDate")
    ) {
      data.set("endDate", new Date(perfEndDate));
    } else if (
      perfStartDate == data.get("startDate") &&
      perfEndDate == data.get("endDate")
    ) {
      data.set("startDate", new Date(perfStartDate));
      data.set("endDate", new Date(perfEndDate));
    }

    let imageList = [...images];
    imageList.forEach((item) => {
      data.append("images", item);
    });
    data.append("seatPrice", JSON.stringify(seatPrice));

    console.log(data.getAll("images"));

    apiClient.perfModify(performanceNo, data);
  };

  //image 업로드
  const changeImage = (event) => {
    const imageLists = event.target.files;
    let imageUrlLists = [...imgUrl];
    let imageList = [...images];
    let size = Object.values(oldImage).length;
    for (let i = 0; i < imageLists.length; i++) {
      const currentImageUrl = URL.createObjectURL(imageLists[i]);
      imageUrlLists.push({ url: currentImageUrl, file: imageLists[i] });
      imageList.push(imageLists[i]);
    }

    if (imageUrlLists.length > 10 - size) {
      imageUrlLists = imageUrlLists.slice(0, 10 - size);
      imageList = imageList.slice(0, 10 - size);
      MySwal.fire({
        icon: 'warning',
        title: '최대 10개 까지 업로드 할 수 있습니다',
        confirmButtonColor: '#66cc66',
        confirmButtonBorder: '#66cc66',
      })
    }
    setImgUrl(imageUrlLists);
    setImages(imageList);
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

  function deleteOldImage(imgUrl) {
    console.log(Object.values(oldImage).length);
    // 보이는거 지우고
    let imgNo = getKeyByValue(imgKeys, imgUrl);
    // 새로 정렬 돼서 문제..
    setOldImage(Object.values(oldImage).filter((url) => url !== imgUrl));

    // 삭제된 번호 넣기
    let list = [...imageNo];
    list.push(imgNo);
    // console.log(imgNo);
    setImageNo(list);
  }
  function getKeyByValue(object, value) {
    // console.log(value);
    return Object.keys(object).find((key) => object[key] === value);
  }
  //  공연 이미지 삭제
  function deleteImage(imgFile) {
    setImgUrl(imgUrl.filter((imgUrl) => imgUrl.file !== imgFile));
    setImages(images.filter((images) => images !== imgFile));
    // console.log(images);
  }

  //  공연 등록 폼 취소
  const cancelForm = () => {
    Swal.fire({
      title: '수정을 취소하시겠습니까?',
      showCancelButton: true,
      confirmButtonText: '예',
      cancelButtonText: '아니오',
      confirmButtonColor: 'red',
    }).then((result)=>{
      if(result.isConfirmed){
        window.location.href = `/perf/detail/${performanceNo}`;
      }
    })
  };

  console.log(imgUrl);
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
              {Object.values(oldImage) ? (
                Object.values(oldImage).map((item, id) => (
                  <span key={id} className={stylesTag.img}>
                    <img
                      key={id}
                      src={item}
                      alt=""
                      style={{
                        height: "300px",
                        width: "200px",
                        border: "3px white solid",
                        boxShadow:
                          "0 10px 35px rgba(0, 0, 0, 0.05), 0 6px 6px rgba(0, 0, 0, 0.2)",
                      }}
                    ></img>
                    <RemoveCircleOutlineIcon
                      color="error"
                      onClick={() => deleteOldImage(item)}
                      className={stylesTag.btn1}
                      style={{ top: "-275px" }}
                    ></RemoveCircleOutlineIcon>
                  </span>
                ))
              ) : (
                <div></div>
              )}
              {imgUrl ? (
                <span>
                  {imgUrl.map((item, id) => (
                    <span key={id} className={stylesTag.img}>
                      <img
                        key={id}
                        src={item.url}
                        alt=""
                        style={{
                          height: "300px",
                          width: "200px",
                          border: "3px white solid",
                          boxShadow:
                            "0 10px 35px rgba(0, 0, 0, 0.05), 0 6px 6px rgba(0, 0, 0, 0.2)",
                        }}
                      ></img>
                      <RemoveCircleOutlineIcon
                        color="error"
                        onClick={() => deleteImage(item.file)}
                        className={stylesTag.btn1}
                        style={{ top: "-275px" }}
                      ></RemoveCircleOutlineIcon>
                    </span>
                  ))}
                </span>
              ) : (
                // <Box style={{ height: "100px", width: "100px" }}></Box>
                <div></div>
              )}
              <Button
                variant="contained"
                component="label"
                color="green"
                style={{
                  fontFamily: "SBAggroB",
                  marginTop: "10px",
                  marginBottom: "40px",
                }}
              >
                + 포스터 추가
                <input type="file" multiple hidden onChange={changeImage} />
              </Button>
            </Grid>
            <Grid item xs={12}>
              <TextField
                name="title"
                variant="outlined"
                fullWidth
                required
                id="title"
                label="공연 제목"
                value={perfName}
                autoFocus
                onChange={changePerfName}
                inputProps={{
                  maxLength: 50,
                }}
                style={{ backgroundColor: "white" }}
                sx={{
                  "& .MuiOutlinedInput-root.Mui-focused": {
                    "& > fieldset": {
                      borderColor: "#66cc66",
                    },
                  },
                }}
              />
            </Grid>
            <Grid item xs={12}>
              {seatPrice.map((x, i) => {
                return (
                  <Grid container>
                    <Grid item xs={2}></Grid>
                    <Grid item xs={8}>
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
                        sx={{
                          "& .MuiOutlinedInput-root.Mui-focused": {
                            "& > fieldset": {
                              borderColor: "#66cc66",
                            },
                          },
                        }}
                      />
                      <TextField
                        id="price"
                        className="ml10"
                        name="price"
                        label="가격"
                        value={x.price}
                        onChange={(e) => handleSeatPrice(e, i)}
                        style={{
                          marginTop: "10px",
                          backgroundColor: "white",
                        }}
                        type="number"
                        sx={{
                          "& .MuiOutlinedInput-root.Mui-focused": {
                            "& > fieldset": {
                              borderColor: "#66cc66",
                            },
                          },
                        }}
                      />
                    </Grid>
                    <Grid item xs={1} marginTop="20px">
                      {seatPrice.length !== 1 && (
                        <RemoveCircleOutlineIcon
                          color="error"
                          onClick={() => deleteSeatPrice(i)}
                        ></RemoveCircleOutlineIcon>
                      )}
                      {seatPrice.length - 1 === i && (
                        <AddCircleIcon
                          color="success"
                          onClick={addSeatPrice}
                        ></AddCircleIcon>
                      )}
                    </Grid>
                    <Grid item xs={1}></Grid>
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
                  renderInput={(params) => (
                    <TextField
                      {...params}
                      sx={{
                        "& .MuiOutlinedInput-root.Mui-focused": {
                          "& > fieldset": {
                            borderColor: "#66cc66",
                          },
                        },
                      }}
                    />
                  )}
                />
              </LocalizationProvider>
            </Grid>

            <Grid item xs={2}>
              <Typography
                style={{ fontSize: "large", fontFamily: "IBM Plex Sans KR" }}
              >
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
                  renderInput={(params) => (
                    <TextField
                      {...params}
                      sx={{
                        "& .MuiOutlinedInput-root.Mui-focused": {
                          "& > fieldset": {
                            borderColor: "#66cc66",
                          },
                        },
                      }}
                    />
                  )}
                />
              </LocalizationProvider>
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
                  sx={{
                    "&.Mui-focused .MuiOutlinedInput-notchedOutline": {
                      borderColor: "#66cc66",
                    },
                  }}
                >
                  <MenuItem value={"연극"} style={{ fontFamily: "SBAggroB" }}>
                    연극
                  </MenuItem>
                  <MenuItem value={"뮤지컬"} style={{ fontFamily: "SBAggroB" }}>
                    뮤지컬
                  </MenuItem>
                  <MenuItem value={"무용"} style={{ fontFamily: "SBAggroB" }}>
                    무용
                  </MenuItem>
                  <MenuItem value={"클래식"} style={{ fontFamily: "SBAggroB" }}>
                    클래식
                  </MenuItem>
                  <MenuItem value={"국악"} style={{ fontFamily: "SBAggroB" }}>
                    국악
                  </MenuItem>
                  <MenuItem value={"가요"} style={{ fontFamily: "SBAggroB" }}>
                    가요
                  </MenuItem>
                  <MenuItem value={"전시"} style={{ fontFamily: "SBAggroB" }}>
                    전시
                  </MenuItem>
                  <MenuItem value={"기타"} style={{ fontFamily: "SBAggroB" }}>
                    기타
                  </MenuItem>
                </Select>
              </FormControl>
            </Grid>

            <Grid item xs={4}>
              <TextField
                required
                fullWidth
                name="runtime"
                label="공연 시간"
                value={runtime}
                id="runtime"
                onChange={changeRuntime}
                style={{ backgroundColor: "white" }}
                sx={{
                  "& .MuiOutlinedInput-root.Mui-focused": {
                    "& > fieldset": {
                      borderColor: "#66cc66",
                    },
                  },
                }}
              />
            </Grid>

            <Grid item xs={4}>
              <TextField
                required
                fullWidth
                name="location"
                label="공연 장소"
                value={location}
                id="location"
                onChange={changeLocation}
                style={{ backgroundColor: "white" }}
                sx={{
                  "& .MuiOutlinedInput-root.Mui-focused": {
                    "& > fieldset": {
                      borderColor: "#66cc66",
                    },
                  },
                }}
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
                value={description}
                onChange={changeDescription}
                inputProps={{
                  maxLength: 2000,
                }}
                style={{ backgroundColor: "white" }}
                sx={{
                  "& .MuiOutlinedInput-root.Mui-focused": {
                    "& > fieldset": {
                      borderColor: "#66cc66",
                    },
                  },
                }}
              />
              <div style={{ textAlign: "right", color: "grey" }}>
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
                    // value={perfStartDate.length < 30 ? new Date(perfStartDate) : perfStartDate}
                    value={perfStartDate}
                    name="startDate"
                  ></input>
                  <input
                    type="hidden"
                    // value={perfEndDate.length < 30 ? new Date(perfEndDate) : perfEndDate}
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
                  <input type="hidden" value={imageNo} name="imageNo"></input>

                  <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                    color="green"
                    sx={{ mt: 3, mb: 2 }}
                    style={{ fontFamily: "SBAggroB" }}
                  >
                    공연 수정
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
