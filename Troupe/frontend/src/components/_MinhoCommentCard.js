import React from 'react';
import apiClient from "../apiClient";

import _MinhoModifyComment from "./_MinhoModifyComment";

import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardMedia from '@mui/material/CardMedia';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import Button from '@mui/material/Card';
import Typography from '@mui/material/Typography';
import TextField from "@mui/material";
import { red } from '@mui/material/colors';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ShareIcon from '@mui/icons-material/Share';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import ModeEditIcon from "@mui/icons-material/ModeEdit";
import DeleteIcon from "@mui/icons-material/Delete";
import ReplyIcon from "@mui/icons-material/Reply";
import SendIcon from "@mui/icons-material/Send";

export default function _MinhoCommentCard(props) {
  const [expanded, setExpanded] = React.useState(false);
  const [modify, setModify] = React.useState(false);
  const [no, setNo] = React.useState(0);
  const [createTime, setCreateTime] = React.useState("");

  React.useEffect(() => {
    if (props.no && props.no != 0) {
      setNo(props.no);
      console.log(props.no);
      console.log(props.isRemoved);
    }
  }, [props.no]);

  React.useEffect(() => {
    console.log(props.createdTime);
    if (props.createdTime) {
      const date = new Date(props.createdTime);
      const string = date.getFullYear() +
        "/" + (date.getMonth() + 1) +
        "/" + date.getDate() +
        " " + date.getHours() +
        ":" + date.getMinutes();
      setCreateTime(string);
    }
  }, [props.createdTime]);

  const handleExpandClick = () => {
    setExpanded(!expanded);
  };
  //수정버튼
  const modifyButton = (no, comment) => {
    console.log(no);
    props.modifyButton(no, comment);
    setModify(false);
  };
  const [memberInfo, setMemberInfo] = React.useState({});
  React.useEffect(() => {
    console.log(props.memberInfo);
    if (props.memberInfo) {
      setMemberInfo(props.memberInfo);
    }
  }, [props.memberInfo]);
  const profileImgClick = () => {
    window.location.href=`/profile/${memberInfo.memberNo}`;
  }
  return (
    <Card>
      <CardHeader
        avatar={
          !memberInfo.profileImageUrl ||
            memberInfo.profileImageUrl === "" ? (
            <Button onClick={ profileImgClick } style={{
              width: "50px",
              height: "50px",
              objectFit: "cover",
              borderRadius: "50%",
              // border: "8px black solid",
              // boxShadow: "3px 3px 5px #000",
            }}>
              <AccountCircleIcon
                fontSize="large"
                sx={{ fontSize: "50px" }}
              ></AccountCircleIcon>
            </Button>
          ) : (
            <Button onClick={ profileImgClick } style={{
              width: "50px",
              height: "50px",
              objectFit: "cover",
              borderRadius: "50%",
              // border: "8px black solid",
              // boxShadow: "3px 3px 5px #000",
            }}>
              <img
                src={memberInfo.profileImageUrl}
                alt={memberInfo.profileImageUrl}
                style={{
                  width: "50px",
                  height: "50px",
                  objectFit: "cover",
                  borderRadius: "50%",
                  // border: "8px black solid",
                  // boxShadow: "3px 3px 5px #000",
                }}
              />
            </Button>
          )
        }
        // action={
        //   <IconButton aria-label="settings">
        //     <MoreVertIcon />
        //   </IconButton>
        // }
        title={memberInfo.nickname}
        subheader={createTime}
      />
      {/* <CardMedia image="/static/images/cards/paella.jpg" title="Paella dish" /> */}
      <CardContent>
        {props.isRemoved === true ? (
          <Typography variant="body2" color="textSecondary" component="span">
            삭제된 글입니다.
          </Typography>
        ) : modify ? (
          <_MinhoModifyComment
            comment={props.comment}
            modifyButton={modifyButton}
            no={no}
          />
        ) : (
          <Typography variant="body2" color="textSecondary" component="span">
            {props.comment}
          </Typography>
        )}
      </CardContent>

      <CardActions disableSpacing>
        <div style={{ textAlign: "right" }}>
          {parseInt(sessionStorage.getItem("loginMember")) ===
          memberInfo.memberNo ? (
            props.isRemoved === true ? (
              <div></div>
            ) : (
              <div>
                <IconButton
                  onClick={() => {
                    setModify(true);
                  }}
                  aria-label="add to favorites"
                >
                  <ModeEditIcon />
                </IconButton>
                <IconButton
                  onClick={() => {
                    props.deleteButton(props.no);
                  }}
                  aria-label="share"
                >
                  <DeleteIcon />
                </IconButton>
              </div>
            )
          ) : (
            <div></div>
          )}
        </div>
      </CardActions>
      {/* <Collapse in={expanded} timeout="auto" unmountOnExit>
        <CardContent>
          <Typography paragraph>Method:</Typography>
          <Typography paragraph>
            Heat 1/2 cup of the broth in a pot until simmering, add saffron and
            set aside for 10 minutes.
          </Typography>
          <Typography paragraph>
            Heat oil in a (14- to 16-inch) paella pan or a large, deep skillet
            over medium-high heat. Add chicken, shrimp and chorizo, and cook,
            stirring occasionally until lightly browned, 6 to 8 minutes.
            Transfer shrimp to a large plate and set aside, leaving chicken and
            chorizo in the pan. Add pimentón, bay leaves, garlic, tomatoes,
            onion, salt and pepper, and cook, stirring often until thickened and
            fragrant, about 10 minutes. Add saffron broth and remaining 4 1/2
            cups chicken broth; bring to a boil.
          </Typography>
          <Typography paragraph>
            Add rice and stir very gently to distribute. Top with artichokes and
            peppers, and cook without stirring, until most of the liquid is
            absorbed, 15 to 18 minutes. Reduce heat to medium-low, add reserved
            shrimp and mussels, tucking them down into the rice, and cook again
            without stirring, until mussels have opened and rice is just tender,
            5 to 7 minutes more. (Discard any mussels that don’t open.)
          </Typography>
          <Typography>
            Set aside off of the heat to let rest for 10 minutes, and then
            serve.
          </Typography>
        </CardContent>
      </Collapse> */}
    </Card>
  );
}