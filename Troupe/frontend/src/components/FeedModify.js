import React, { useCallback } from "react";
import { useParams } from "react-router-dom";
import CssBaseline from "@mui/material/CssBaseline";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import TextField from "@mui/material/TextField";
import { Button, Grid, Container } from "@mui/material";
import stylesTag from "../css/tag.module.css";
import apiClient from "../apiClient";
import RemoveCircleOutlineIcon from "@mui/icons-material/RemoveCircleOutline";
import Theme from "./Theme";
import HighlightOffIcon from "@mui/icons-material/HighlightOff";
export default function FeedModify() {
  const { feedNo } = useParams();
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
  const [tags, setTagList] = React.useState([]);
  const [tag, setTag] = React.useState("");
  const [content, setContent] = React.useState("");
  const [maxSize, setMaxSize] = React.useState(10);

  React.useEffect(() => {
    apiClient.getFeedDetail(feedNo).then((data) => {
      if (parseInt(sessionStorage.getItem("loginMember")) !== data.memberNo) {
        alert("권한이 없습니다");
        window.location.href = "/feed/list/all/0";
      }
      setImgKeys(data.images);
      setOldImage(data.images);
      setContent(data.content);
      setTagList(data.tags);
    });
  }, []);

  const handleSubmit = (event) => {
    event.preventDefault();
    if (images.length === 0 && oldImage.length === 0) {
      alert("사진을 업로드하세요");
      return;
    }
    const data = new FormData(event.currentTarget);
    let imageList = [...images];
    //file형태로 리스트 뽑기 위해..
    imageList.forEach((item) => {
      data.append("images", item);
    });
    // console.log(data.get("content"));
    // console.log("제출할 태그들 " + data.get("tags"));
    // console.log(data.get("images"));
    // console.log("삭제된 이미지 번호들 " + data.get("imageNo"));
    // console.log("기존 이미지 사이즈: " + Object.values(oldImage).length);

    apiClient.feedModify(data, feedNo);
  };

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
      alert("최대 10개 까지 업로드 할 수 있습니다");
    }
    setImgUrl(imageUrlLists);
    setImages(imageList);
    // console.log(imageUrlLists);
  };
  const changeTag = (event) => {
    setTag(event.target.value);
  };

  const changeContent = (event) => {
    setContent(event.target.value);
  };

  const cancelForm = () => {
    if (window.confirm("수정을 취소하시겠습니까?")) {
      window.location.href = "/feed/list/all/0";
    } else {
      return;
    }
  };
  const addTagFunc = useCallback(
    (event) => {
      if (process.browser) {
        // 요소 불러오기
        const HashWrapOuter = document.querySelector(
          `.${stylesTag.HashWrapOuter}`,
        );
        const HashWrapInner = document.createElement("div");
        HashWrapInner.className = stylesTag.HashWrapInner;

        if (event.key === "Enter" && event.target.value.trim() !== "") {
          event.preventDefault();
          if (tags.length >= 5) {
            // alert("최대 5개까지 등록할 수 있습니다");
            const notice = document.querySelector("#notice");
            if (document.querySelector("#notice div") !== null) return;
            const newNotice = document.createElement("div");
            newNotice.innerHTML = "최대 5개까지 등록할 수 있습니다";
            newNotice.className = stylesTag.alert;
            notice.appendChild(newNotice);
            return;
          } else {
            if (document.querySelector("#notice div") !== null) {
              const notice = document.querySelector("#notice");
              const div = document.querySelector("#notice div");
              notice.removeChild(div);
            }
          }
          setTagList((tags) => [...tags, tag]);
          setTag("");
          console.log(tags);
        }
      }
    },
    [tag, tags],
  );

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
  function deleteImage(imgFile) {
    setImgUrl(imgUrl.filter((imgUrl) => imgUrl.file !== imgFile));
    setImages(images.filter((images) => images !== imgFile));
  }
  function deleteTag(tagName) {
    setTagList(tags.filter((tag) => tag !== tagName));
  }

  return (
    <ThemeProvider theme={Theme}>
      <Container maxWidth="md">
        <CssBaseline />

        <div
          style={{
            textAlign: "center",
            marginTop: "3%",
            fontFamily: "SBAggroB",
            paddingBottom: "400px",
          }}
        >
          <Grid container spacing={12}>
            <Grid item xs={12}>
              {Object.values(oldImage) ? (
                Object.values(oldImage).map((item, id) => (
                  <span key={id} className={stylesTag.img}>
                    <img
                      key={id}
                      src={item}
                      alt=""
                      style={{
                        height: "150px",
                        width: "150px",
                        border: "3px white solid",
                        boxShadow:
                          "0 10px 35px rgba(0, 0, 0, 0.05), 0 6px 6px rgba(0, 0, 0, 0.2)",
                      }}
                    ></img>
                    <RemoveCircleOutlineIcon
                      color="error"
                      onClick={() => deleteOldImage(item)}
                      className={stylesTag.btn1}
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
                        style={{ height: "150px", width: "150px" }}
                      ></img>
                      <RemoveCircleOutlineIcon
                        color="error"
                        onClick={() => deleteImage(item.file)}
                        className={stylesTag.btn1}
                      ></RemoveCircleOutlineIcon>
                    </span>
                  ))}
                </span>
              ) : (
                <div></div>
              )}
              <div>
                <Button variant="contained" component="label" color="green">
                  + 사진/동영상 추가
                  <input type="file" multiple hidden onChange={changeImage} />
                </Button>
              </div>
            </Grid>
          </Grid>
          <Grid container>
            <Grid item xs={12}>
              <input
                className={stylesTag.HashInput}
                label="태그"
                value={tag}
                onChange={changeTag}
                onKeyUp={addTagFunc}
                placeholder="이곳에 태그입력 후 엔터를 치세요"
                maxLength={20}
                style={{ textAlign: "center" }}
              />

              <div className={stylesTag.HashWrap}>
                <div className={stylesTag.HashWrapOuter}>
                  {tags ? (
                    tags.map((item, id) => (
                      <div
                        className={stylesTag.HashWrapInner}
                        key={id}
                        onClick={() => deleteTag(item)}
                      >
                        # {item}
                        <span
                          style={{
                            display: "inline-block",
                            paddingLeft: "10px",
                            paddingTop: "3.7px",
                          }}
                        >
                          <HighlightOffIcon
                            fontSize="small"
                            color="error"
                          ></HighlightOffIcon>
                        </span>
                      </div>
                    ))
                  ) : (
                    <div></div>
                  )}
                </div>
              </div>
              <Grid
                id="notice"
                style={{
                  marginTop: "20px",
                  marginBottom: "10px",
                }}
              ></Grid>
            </Grid>
          </Grid>

          <Grid container>
            <Grid item xs={12}>
              <TextField
                multiline
                rows={8}
                fullWidth
                required
                label="내용"
                autoFocus
                onChange={changeContent}
                placeholder="내용 입력"
                size="medium"
                value={content}
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
            </Grid>
          </Grid>
          <div style={{ textAlign: "right", color: "grey" }}>
            {content.length}/2000
          </div>

          <Grid container spacing={2}>
            <Grid item xs={8}></Grid>
            <Grid item xs={2}>
              <Button
                type="button"
                fullWidth
                variant="contained"
                sx={{ mt: 3, mb: 2 }}
                color="inherit"
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
                <input type="hidden" value={tags} multiple name="tags"></input>
                <input type="hidden" value={content} name="content"></input>
                <input type="hidden" value={imageNo} name="imageNo"></input>
                <Button
                  type="submit"
                  fullWidth
                  variant="contained"
                  sx={{ mt: 3, mb: 2 }}
                  color="green"
                  style={{ fontFamily: "SBAggroB" }}
                >
                  수정 완료
                </Button>
              </form>
            </Grid>
          </Grid>
        </div>
      </Container>
    </ThemeProvider>
  );
}
