import React from "react";
import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import CardContent from "@mui/material/CardContent";
import CardActions from "@mui/material/CardActions";
import Avatar from "@mui/material/Avatar";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import { red } from "@mui/material/colors";
import FavoriteIcon from "@mui/icons-material/Favorite";
import ShareIcon from "@mui/icons-material/Share";

export default function Comment(props) {
  console.log(props);
  return (
    <Card sx={{ maxWidth: 345 }}>
      <CardHeader
        avatar={<Avatar alt={props.nickname} src={props.profileImageUrl} />}
      />
      <CardContent>
        <Typography variant="body2" color="textSecondary" component="p">
          {props.comment}
        </Typography>
      </CardContent>   
    </Card>
  );
}
