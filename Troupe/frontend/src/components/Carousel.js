import React from "react";
import MUICarousel from "react-material-ui-carousel";
import CarouselItem from "./CarouselItem";

export default function Carousel(props) {
  return (
    <MUICarousel
      autoPlay={false}
      animation="slide"
      style={{ backgroudColor: "black" }}
    >
      {props.children.map((item, id) => (
        <CarouselItem key={id} item={item.props.item} />
      ))}
    </MUICarousel>
  );
}
