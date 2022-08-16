import { useEffect } from "react";

export default function CommentPerf(props) {
  useEffect(() => {
    setContent(props.comment);
    if (sessionStorage.getItem("loginCheck"))
      setUser(parseInt(sessionStorage.getItem("loginMember")));

    apiClient
      .getPerfChildReviewList(props.performanceNo, props.reviewNo)
      .then((data) => {
        const json = [];

        data.forEach((item) => {
          json.push({
            memberNo: item.memberNo,
            reviewNo: item.reviewNo,
            profileImageUrl: item.profileImageUrl,
            comment: item.comment,
            nickname: item.nickname,
            isRemoved: item.removed,
            pfNo: item.pfNo,
          });
        });
        setchildComments(json);
      });
  }, []);

  return <div></div>;
}
