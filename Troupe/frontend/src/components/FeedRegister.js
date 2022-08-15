import React, { useCallback } from "react";
import CssBaseline from "@mui/material/CssBaseline";
import { ThemeProvider } from "@mui/material/styles";
import TextField from "@mui/material/TextField";
import { Button, Grid, Container } from "@mui/material";
import stylesTag from "../css/tag.module.css";
import apiClient from "../apiClient";
import RemoveCircleOutlineIcon from "@mui/icons-material/RemoveCircleOutline";
import Theme from "./Theme";

export default function FeedRegister() {
  const [imgUrl, setImgUrl] = React.useState([]);
  const [images, setImages] = React.useState([]);
  const [tags, setTagList] = React.useState([]);
  const [tag, setTag] = React.useState("");
  const [content, setContent] = React.useState("");
  const [hide, setHide] = React.useState(true);
  // const [isReady, setIsReady] = React.useState(false);

  // React.useState(() => {
  //   if (images.length !== 0 && content.length !== 0) setIsReady(true);
  // }, [isReady]);

  const handleSubmit = (event) => {
    event.preventDefault();
    if (images.length === 0) {
      alert("사진을 업로드하세요");
      return;
    } else if (content.length === 0) {
      alert("내용을 작성하세요");
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

    apiClient.feedNew(data);
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
      alert("최대 10개 까지 업로드 할 수 있습니다");
    }
    setImgUrl(imageUrlLists);
    setImages(imageList);
    // console.log(imageUrlLists);
    // setIsReady(false);
  };
  const changeTag = (event) => {
    setTag(event.target.value);
  };

  const changeContent = (event) => {
    setContent(event.target.value);
    // setIsReady(false);
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
          // HashWrapInner.innerHTML = "# " + event.target.value;
          // HashWrapOuter.appendChild(HashWrapInner);
          setHide(false);
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

  function deleteImage(imgFile) {
    setImgUrl(imgUrl.filter((imgUrl) => imgUrl.file !== imgFile));
    setImages(images.filter((images) => images !== imgFile));
    // console.log(images);
  }
  function deleteTag(tagName) {
    setTagList(tags.filter((tag) => tag !== tagName));
  }

  const cancelForm = () => {
    if (window.confirm("등록을 취소하시겠습니까?")) {
      window.location.href = "/feed/list/all/0";
    } else {
      return;
    }
  };

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
              {imgUrl ? (
                <div>
                  {imgUrl.map((item, id) => (
                    <span key={id} className={stylesTag.img}>
                      <img
                        key={id}
                        src={item.url}
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
                        onClick={() => deleteImage(item.file)}
                        className={stylesTag.btn1}
                      ></RemoveCircleOutlineIcon>
                    </span>
                  ))}
                </div>
              ) : (
                <div></div>
              )}
              <Button variant="contained" component="label" color="green">
                + 사진/동영상 추가
                <input type="file" multiple hidden onChange={changeImage} />
              </Button>
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
                style={{textAlign: "center"}}
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
                      </div>
                    ))
                  ) : (
                    <div></div>
                  )}
                </div>
              </div>
              <Grid item style={{ marginTop: "10px", marginBottom: "20px" }} id="notice">
              {!hide ?( <span>태그를 누르면 삭제됩니다</span>):(<span></span>)}
              </Grid>
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
                inputProps={{
                  maxLength: 2000,
                }}
                style={{ backgroundColor: "white" }}
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
                <Button
                  type="submit"
                  fullWidth
                  variant="contained"
                  sx={{ mt: 3, mb: 2 }}
                  color="green"
                  style={{ fontFamily: "SBAggroB" }}
                >
                  피드 등록
                </Button>
                {/* {isReady ? (
                
              ) : (
                <Button
                  type="button"
                  disabled
                  fullWidth
                  variant="contained"
                  sx={{ mt: 3, mb: 2 }}
                  color="grey"
                >
                  피드 등록
                </Button>
              )} */}
              </form>
            </Grid>
          </Grid>
        </div>
      </Container>
    </ThemeProvider>
  );
}
