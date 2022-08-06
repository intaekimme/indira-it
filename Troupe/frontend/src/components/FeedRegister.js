import React, { useCallback } from "react";
import CssBaseline from "@mui/material/CssBaseline";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import { Button, Grid, Container } from "@mui/material";
import stylesTag from "../css/tag.module.css";
import apiClient from "../apiClient";

const theme = createTheme({
  palette: {
    neutral: {
      main: "#fda085",
      contrastText: "#fff",
    },
  },
});

export default function FeedRegister() {
  const [imgUrl, setImgUrl] = React.useState([
    {
      url: "https://s3.ap-northeast-2.amazonaws.com/hongjoo.troupe.project/feed/defalut.jpg",
      file: null,
    },
  ]);
  const [images, setImages] = React.useState([]);
  const [tags, setTagList] = React.useState([]);
  const [tag, setTag] = React.useState("");
  const [content, setContent] = React.useState("");

  const handleSubmit = (event) => {
    event.preventDefault();
    if (images === []) {
      alert("사진을 업로드하세요");
      return;
    }
    const data = new FormData(event.currentTarget);
    let imageList = [...images];
    //file형태로 리스트 뽑기 위해..
    imageList.forEach((item) => {
      data.append("images", item);
    });
    console.log(data.get("content"));
    console.log("제출할 태그들 " + data.get("tags"));
    console.log(data.get("images"));

    // apiClient.feedNew(data);
  };

  const changeImage = (event) => {
    const imageLists = event.target.files;
    let imageUrlLists = [...imgUrl];
    let imageList = [...images];
    for (let i = 0; i < imageLists.length; i++) {
      const currentImageUrl = URL.createObjectURL(imageLists[i]);
      imageUrlLists.push({ url: currentImageUrl, file: imageLists[i] });
      imageList.push(imageLists[i]);
    }

    if (imageUrlLists.length > 10) {
      imageUrlLists = imageUrlLists.slice(0, 10);
      imageList = imageList.slice(0, 10);
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
  const addTagFunc = useCallback(
    (event) => {
      if (process.browser) {
        // 요소 불러오기
        const HashWrapOuter = document.querySelector(
          `.${stylesTag.HashWrapOuter}`,
        );
        const HashWrapInner = document.createElement("div");
        HashWrapInner.className = stylesTag.HashWrapInner;

        // 태그 클릭 이벤트 관련 로직
        HashWrapInner.addEventListener("click", () => {
          HashWrapOuter.removeChild(HashWrapInner);
          console.log(HashWrapInner.innerHTML);
          setTagList(tags.filter((tag) => tag));
        });
        if (event.key === "Enter" && event.target.value.trim() !== "") {
          event.preventDefault();
          HashWrapInner.innerHTML = "# " + event.target.value;
          HashWrapOuter.appendChild(HashWrapInner);
          setTagList((tags) => [...tags, tag]);
          setTag("");
          console.log(tags);
        }
      }
    },
    [tag, tags],
  );

  function deleteImage(imgFile) {
    setImgUrl(imgUrl.filter((imgUrl) => imgUrl.file !== imgFile));
    setImages(images.filter((images) => images !== imgFile));
    // console.log(images);
  }
  function deleteTag(tagName) {
    setTagList(tags.filter((tag) => tag !== tagName));
  }

  return (
    <ThemeProvider theme={theme}>
      <Container maxWidth="md">
        <CssBaseline />

        <div style={{ textAlign: "center", marginTop: "3%" }}>
          <Grid container spacing={12}>
            <Grid item xs={12}>
              {imgUrl ? (
                <div>
                  {imgUrl.map((item, id) => (
                    <span key={id} className={stylesTag.img}>
                      <img
                        key={id}
                        src={item.url}
                        alt=""
                        style={{ height: "150px", width: "150px" }}
                      ></img>
                      <button
                        type="button"
                        onClick={() => deleteImage(item.file)}
                        className={stylesTag.btn1}
                      >
                        -
                      </button>
                    </span>
                  ))}
                </div>
              ) : (
                <div></div>
              )}
              <Button variant="contained" component="label" color="neutral">
                + 사진/동영상 추가
                <input type="file" multiple hidden onChange={changeImage} />
              </Button>
            </Grid>
          </Grid>
          <Grid container>
            <Grid item xs={12}>
              <p>태그를 누르면 삭제됩니다</p>
              <div className={stylesTag.HashWrap}>
                <div className={stylesTag.HashWrapOuter}></div>
                <input type="hidden" value={tags} multiple name="tags"></input>
              </div>
              <input
                className={stylesTag.HashInput}
                label="태그"
                value={tag}
                onChange={changeTag}
                onKeyUp={addTagFunc}
                placeholder="태그입력 후 엔터를 치세요"
              />
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
              />
            </Grid>
          </Grid>
        </div>
        <Grid container spacing={2}>
          <Grid item xs={10}></Grid>
          <Grid item xs={2}>
            <form
              onSubmit={handleSubmit}
              encType="multipart/form-data"
              style={{ textAlign: "center" }}
            >
              <input type="hidden" value={tags} multiple name="tags"></input>
              <input type="hidden" value={content} name="content"></input>
              <Button
                type="submit"
                fullWidth
                variant="contained"
                sx={{ mt: 3, mb: 2 }}
                color="neutral"
              >
                피드 등록
              </Button>
            </form>
          </Grid>
        </Grid>
      </Container>
    </ThemeProvider>
  );
}
