import React, { useState, useEffect } from "react";
import axios from "axios";
import "./CommentsSlider.css";
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

  const numberOfComments = 3;
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

  const generatePagination = () => {
    const numberOfPagination = 3;
    const halfNumberOfPagination = Math.floor(numberOfPagination / 2);

    const firstNumberOfPagination = Math.max(1, page - halfNumberOfPagination);
    const lastNumberOfPagination = Math.min(
      numberOfPage,
      page + halfNumberOfPagination
    );

    return Array.from(
      { length: lastNumberOfPagination - firstNumberOfPagination + 1 },
      (_, i) => i + firstNumberOfPagination
    );
  };
  return (
    <div className="comments pagination">
      {generatePagination().map((numer) => (
        <span
          key={numer}
          onClick={() => handlePageClick(numer)}
          className={numer === page ? "active" : ""}
        >
          {numer}
        </span>
      ))}
      <div className="comments-all-window">
        {currentComments.map((review, index) => (
          <div className="comment-window" key={index}>
            <p className="data">{review.reviewData}</p>
            <h3 className="rating">{review.rating}/5</h3>

            <p className="review-content">{review.reviewContent}</p>
            <p className="name-person">
              {review.person.firstName} {review.person.lastName}
            </p>
            <img className="image-person" src="photo.jpg"></img>
          </div>
        ))}
      </div>
    </div>
  );
};

export default CommentsSlider;
