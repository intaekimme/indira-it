import React from "react";
import apiClient from "../apiClient";

import Grid from "@mui/material/Grid";
import TextField from "@mui/material/TextField";
import IconButton from "@mui/material/IconButton";
import SendIcon from "@mui/icons-material/Send";

export default function _MinhoModifyComment(props) {
  const [no, setNo] = React.useState(0);

  React.useEffect(() => {
    if (props.no && props.no != 0) {
      setNo(props.no);
      console.log(props.no);
    }
  }, [props.no]);

  const handleSubmit = (event) => {
    event.preventDefault();
    const formData = new FormData(event.currentTarget);
    props.modifyButton(no, formData.get("comment"));
  };
  return (
    <form
      onSubmit={handleSubmit}
      encType="multipart/form-data"
      style={{ textAlign: "center" }}
    >
      <Grid container item>
        <Grid item xs={11} alignItems="flex-end">
          <TextField
            fullWidth
            multiline
            id="comment"
            label="방명록 수정"
            defaultValue={props.comment}
            name="comment"
            style={{ backgroundColor: "white" }}
            sx={{
              "& .MuiOutlinedInput-root.Mui-focused": {
                "& > fieldset": {
                  borderColor: "#66cc66",
                },
              },
            }}
          ></TextField>
        </Grid>
        <Grid item xs={1}>
          <IconButton type="submit" aria-label="show more">
            <SendIcon />
          </IconButton>
        </Grid>
      </Grid>
    </form>
  );
}
