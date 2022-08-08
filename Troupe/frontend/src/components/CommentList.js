import axios from "axios";
import React, { useEffect, useState } from "react";
import apiClient from "../apiClient";
import CommentCount from "./CommentCount";
import CommentForm from "./CommentForm";

export default function CommentList({ performance }) {
  const [responseData, setResponseData] = useState();
  // const [reviewList, setReviewList] = useState([]);
  // const getReviewList = async (performance) => {
  //   const res = await axios
  //     .get(`/perf/${performance}/review/list`)
  //     .then((response) => {
  //       alert("불러오기 성공");
  //       setResponseData(response);
  //     })
  //     .catch((error) => {
  //       alert("공연 후기 불러오기 실패" + error);
  //       return null;
  //     });

  //   setResponseData(res);
  // };

  useEffect(() => {
    async function fetchData() {
      const res = await axios
        .get(`/perf/${performance}/review/list`)
        .then((response) => {
          alert("불러오기 성공");
          return response;
        })
        .catch((error) => {
          alert("공연 후기 불러오기 실패" + error);
          return null;
        });
      return res;
    }
    const result = fetchData();
    console.log(result);
    setResponseData(result.data);
    console.log(responseData);
  }, []);

  return (
    <div>
      {/* <CommentCount listSize={reviewList.length} /> */}
      <CommentForm />
    </div>
  );
}
