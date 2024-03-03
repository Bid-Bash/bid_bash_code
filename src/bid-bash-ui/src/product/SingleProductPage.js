import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

const SingleProductPage = (match) => {
  const {productId} = useParams();
  const [product, setProduct] = useState(null);
  console.log(productId)
  useEffect(() => {
    
    const fetchProduct = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/product/${productId}`);
        console.log(response.data)

        setProduct(response.data);

      } catch (error) {
        console.error('Error fetching product: ', error);
      }
    };

    fetchProduct();
  }, [productId]);

  const handleBid = () => {
    // Logic for handling bidding
  };

  if (!product) {
    return <div>Loading...</div>;
  }

  return (
    <div className="container mt-4">
      <div className="row">
        <div className="col-md-6">
          <div id="carouselExampleIndicators" className="carousel slide" data-ride="carousel">
            <div className="carousel-inner">
              {
              product.fileUrls.map((url,index) => (
                <div key={index} className={`carousel-item ${index === 0 ? 'active' : ''}`}>
                  <img src={url} className="d-block w-100" alt={`Product ${index + 1}`} />
                </div>
              ))}
            </div>
            <button className="carousel-control-prev" type="button" data-target="#carouselExampleIndicators" data-slide="prev">
              <span className="carousel-control-prev-icon" aria-hidden="true"></span>
              <span className="visually-hidden">Previous</span>
            </button>
            <button className="carousel-control-next" type="button" data-target="#carouselExampleIndicators" data-slide="next">
              <span className="carousel-control-next-icon" aria-hidden="true"></span>
              <span className="visually-hidden">Next</span>
            </button>
          </div>
        </div>
        <div className="col-md-6">
          <h1>{product.productTitle}</h1>
          <p>{product.productDesc}</p>
          <p>Original Price: ${product.price}</p>
          <p>Bidding Price: ${product.current_bid}</p>
          <button className="btn btn-primary" onClick={handleBid}>Bid</button>
        </div>
      </div>
    </div>
  );
};

export default SingleProductPage;
