import React, { useCallback } from "react";
import apiClient from "../apiClient";
import CssBaseline from "@mui/material/CssBaseline";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import { Button, Grid } from "@mui/material";
import stylesTag from "../css/tag.module.css"

const theme = createTheme();

export default function FeedRegister() {
  const [images, setImgUrl] = React.useState([]);
  const [tagList, setTagList] = React.useState([]);
  const [tag, setTag] = React.useState("");
  const [content, setContent] = React.useState("");

  const handleSubmit = (event) => {
      event.preventDefault();
    console.log(event.currentTarget);
    const data = new FormData(event.currentTarget);
    // console.log(event.currentTarget);
    // console.log(data);

    apiClient.feedNew(data);
  };

  //   const data = { images: null };
  const changeImage = (event) => {
    const imageLists = event.target.files;
    let imageUrlLists = [...images];

    for (let i = 0; i < imageLists.length; i++) {
      const currentImageUrl = URL.createObjectURL(imageLists[i]);
      imageUrlLists.push(currentImageUrl);
    }

    if (imageUrlLists.length > 10) {
      imageUrlLists = imageUrlLists.slice(0, 10);
    }
    setImgUrl(imageUrlLists);
    // console.log(Object.keys);
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
        const HashWrapOuter = document.querySelector(`.${stylesTag.HashWrapOuter}`);
        const HashWrapInner = document.createElement("div");
        HashWrapInner.className =stylesTag.HashWrapInner;

        // 태그 클릭 이벤트 관련 로직
        HashWrapInner.addEventListener("click", () => {
          HashWrapOuter.removeChild(HashWrapInner);
          console.log(HashWrapInner.innerHTML);
          setTagList(tagList.filter((tag) => tag));
        });
        if (event.key === "Enter" && event.target.value.trim() !== "") {
            //   event.preventDefault();
            HashWrapInner.innerHTML = "#" + event.target.value;
            HashWrapOuter.appendChild(HashWrapInner);
            setTagList((tagList) => [...tagList, tag]);
            setTag('');
            console.log(tagList);
        }
      }
    },
    [tag, tagList]
  );

  return (
    <ThemeProvider theme={theme}>
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
          <Grid container spacing={4}>
            <Grid item xs={9}>
              {images ? (
                <div>
                  {images.map((item, id) => (
                    <img
                      key={id}
                      src={item}
                      alt=""
                      style={{ height: "70px", width: "70px" }}
                    ></img>
                  ))}
                </div>
              ) : (
                <Box style={{ height: "100px", width: "100px" }}></Box>
              )}
              <Button variant="contained" component="label">
                +
                <input type="file" multiple hidden onChange={changeImage} />
              </Button>
            </Grid>
          </Grid>
          <Grid container spacing={4}>
            <Grid item xs={9}>
              <div className={stylesTag.HashWrap}>
                <div className={stylesTag.HashWrapOuter}></div>
                <TextField
                  className={stylesTag.HashInput}
                  autoComplete="given-name"
                  name="tag"
                  fullWidth
                  id="tag"
                  label="태그"
                  value={tag}
                  onChange={changeTag}
                  onKeyUp={addTagFunc}
                  placeholder="태그입력 후 엔터를 치세요"
                />
              </div>
            </Grid>
          </Grid>
          <Grid container spacing={4}>
            <Grid item xs={9}>
              <TextField
                autoComplete="given-name"
                name="content"
                required
                fullWidth
                id="content"
                label="내용"
                autoFocus
                onChange={changeContent}
                placeholder="내용 입력"
              />
            </Grid>
          </Grid>
          <Grid item xs={3} style={{ position: "relative" }}>
            <Grid>
              <Button
                type="submit"
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
                등록
              </Button>
            </Grid>
          </Grid>
        </form>
      </Box>
    </ThemeProvider>
  );
}
