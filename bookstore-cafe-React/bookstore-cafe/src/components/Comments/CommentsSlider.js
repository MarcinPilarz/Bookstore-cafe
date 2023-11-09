import React, { useState, useEffect } from "react";
import axios from "axios";

const CommentsSlider = () => {
  const [reviews, setReviews] = useState([]);
  const [page, setPage] = useState(1);
  useEffect(() => {
    axios
      .get("http://localhost:8080/reviews")
      .then((response) => {
        const reviewsData = response.data;
        console.log("Pobrane dane z API:", reviewsData);
        setReviews(reviewsData);
      })
      .catch((error) => {
        console.error("Błąd pobierania danych wydarzeń", error);
      });
  }, []);

  const numberOfComments = 4;
  const numberOfPage = Math.ceil(reviews.length / numberOfComments);

  const indexStart = (page - 1) * numberOfComments;
  const indexEnd = indexStart + numberOfComments;
  console.log("IP", indexStart);
  console.log("iE", indexEnd);
  const currentComments = reviews.slice(indexStart, indexEnd);
  console.log("iC", currentComments.length);
  const handlePageClick = (newPage) => {
    setPage(newPage);
    console.log("Nowa strona", newPage);
    console.log("cośtam", currentComments);
  };
  return (
    <div>
      {Array.from({ length: numberOfPage }, (_, i) => (
        <span key={`page-${i + 1}`} onClick={() => handlePageClick(i + 1)}>
          {i + 1}
        </span>
      ))}
      <div>
        {currentComments.map((review, index) => (
          <div key={index}>
            <h3>{review.rating}/5</h3>
            <p>{review.reviewData}</p>
            <p>{review.reviewContent}</p>
            <p>
              {review.person.firstName} {review.person.lastName}
            </p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default CommentsSlider;
