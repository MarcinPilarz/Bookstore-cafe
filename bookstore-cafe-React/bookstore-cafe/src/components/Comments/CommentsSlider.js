import React, { useState, useEffect } from "react";
import axios from "axios";
import { useAuth } from "../Login/LoginInfoContext";
import ReactStars from "react-rating-stars-component";
import "./CommentsSlider.css";
import { useNavigate } from "react-router-dom";
const CommentsSlider = () => {
  const [reviews, setReviews] = useState([]);
  const [page, setPage] = useState(1);
  const { authData } = useAuth();
  const [comment, setComment] = React.useState("");
  const [reviewContent, setReviewContent] = useState("");
  const [rating, setRating] = useState(0);
  const navigate = useNavigate();
  const fetchReviews = async () => {
    if (authData?.token && new Date().getTime() < authData?.expirationTime) {
      const config = {
        headers: {
          Authorization: `Bearer ${authData.token}`,
        },
      };

      try {
        const response = await axios.get(
          "http://localhost:8080/reviews",
          config
        );
        setReviews(response.data);
      } catch (error) {
        console.error("Błąd pobierania danych wydarzeń", error);
      }
    }
  };
  useEffect(() => {
    if (authData?.token) {
      fetchReviews();
    }
  }, [authData?.token, authData?.expirationTime]);

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

  const handleSubmit = async (event) => {
    event.preventDefault();

    const reviewData = {
      reviewContent,
      rating,
    };

    const config = {
      headers: { Authorization: `Bearer ${authData?.token}` },
    };

    try {
      const response = await axios.post(
        `http://localhost:8080/newComment?idPerson=${authData?.idPerson}`,
        reviewData,
        config
      );
      console.log("Odpowiedź serwera:", response.data);
      setReviewContent("");
      setRating(0);
      await fetchReviews();
    } catch (error) {
      console.error("Błąd podczas wysyłania recenzji:", error);
    }
  };

  const handleRatingChange = (newRating) => {
    setRating(newRating);
  };

  const formatDate = (dateString) => {
    return new Date(dateString).toISOString().split("T")[0];
  };

  const handleSigninClick = () => {
    navigate("/signin");
  };
  return (
    <>
      <div className="add-comment-container">
        <form onSubmit={handleSubmit}>
          <textarea
            value={reviewContent}
            maxLength={250}
            onChange={(e) => setReviewContent(e.target.value)}
            placeholder="Twoja recenzja..."
          />
          <ReactStars
            classNames={"star-raiting"}
            count={5}
            onChange={handleRatingChange}
            size={24}
            isHalf={false}
            activeColor="#ffd700"
          />
          {authData?.roleType === "Klient" ? (
            <button type="submit">Dodaj recenzję</button>
          ) : (
            <button onClick={handleSigninClick}>Zaloguj się jako klient</button>
          )}
        </form>
      </div>

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
              <p className="data">{formatDate(review.reviewData)}</p>

              <ReactStars value={review.rating} edit={false} />

              <p className="review-content">{review.reviewContent}</p>
              <p className="name-person">
                {review.person.firstName} {review.person.lastName}
              </p>
              <img className="image-person" src="photo.jpg"></img>
            </div>
          ))}
        </div>
      </div>
    </>
  );
};

export default CommentsSlider;
