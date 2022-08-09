import React from "react";
import Modal from "@mui/material/Modal";
import Box from "@mui/material/Box";
import Paper from "@mui/material/Paper";

export default function CarouselItem(prop) {
  const [open, setOpen] = React.useState(false);

  function handleOpen() {
    setOpen(true);
  }
  function handleClose() {
    setOpen(false);
  }
  return (
    <Paper style={{ backgroundColor: "#FFFF" }} elevation={0}>
      {prop.item.slice(-3) !== "mp4" && prop.item.slice(-3) !== "wav" ? (
        <img
          onClick={handleOpen}
          src={prop.item}
          alt="img"
          style={{ height: "100%", width: "100%" }}
        ></img>
      ) : (
        <video
          autoplay
          controls
          loop
          muted
          src={prop.item}
          style={{ height: "100%", width: "100%" }}
        ></video>
      )}
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box
          style={{
            position: "absolute",
            margin: "100px 0px",
            left: "50%",
            width: "100%",
            height: "100%",
            maxHeight: "800px",
            maxWidth: "800px",
            transform: "translateX(-50%)",
          }}
        >
          <img
            onClick={handleOpen}
            src={prop.item}
            alt="img"
            style={{ height: "100%", width: "100%" }}
          ></img>
        </Box>
      </Modal>
    </Paper>
  );
}
