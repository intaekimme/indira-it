import React from "react";
import apiClient from "../apiClient";

import Card from '@mui/material/Card';
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Grid from "@mui/material/Grid";
import CardHeader from '@mui/material/CardHeader';
import CardMedia from '@mui/material/CardMedia';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ShareIcon from '@mui/icons-material/Share';
import MoreVertIcon from '@mui/icons-material/MoreVert';

import styledButton from "../css/button.module.css";

export default function GuestBook() {
  const [existMyGuestBook, setExistMyGuestBook] = React.useState(false);

  return (
    <Card>
      <CardHeader
        title={
          existMyGuestBook ? (
            <div>
              <br />
              기존에 썼던 방명록 (댓글폼 붙일예정)
            </div>
          ) : (
            <TextField
              id="outlined-multiline-static"
              label="Multiline"
              multiline
              rows={4}
              placeholder="방명록을 작성해 주세요"
              variant="outlined"
              style={{
                width: "100%",
                marginBottom: "10px",
              }}
            />
          )
        }
        subheader={
          existMyGuestBook ? (
            <div> </div>
          ) : (
            <Grid item xs={12} style={{ textAlign:"right"}}>
              <Button
                type="submit"
                className={styledButton.btn}
                style={{
                  width: "100px",
                  height: "100%",
                  backgroundColor: "#45E3C6",
                  color: "black",
                  alignContent: "right",
                }}
              >
                방명록 작성
              </Button>
            </Grid>
          )
        }
      />
      <CardMedia image="/static/images/cards/paella.jpg" title="Paella dish" />
      <CardContent>
        <Typography variant="body2" color="textSecondary" component="span">
          방명록 목록들
        </Typography>
      </CardContent>
      <CardActions disableSpacing>
        <IconButton aria-label="add to favorites">
          <FavoriteIcon />
        </IconButton>
        <IconButton aria-label="share">
          <ShareIcon />
        </IconButton>
      </CardActions>
    </Card>
  );
}