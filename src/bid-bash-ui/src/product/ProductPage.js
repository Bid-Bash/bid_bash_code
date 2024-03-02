import './ProductPage.css'
import React, { useState, useEffect } from 'react';
import axios from 'axios';

const Product = ({ product }) => {
    return (
        <div className="col-md-4 mb-4">
          <div className="card">
            <img src={product.fileUrls[0]} className="card-img-top"  style={{ height: '200px', width: '100%', objectFit: 'cover' }} alt={product.productTitle} />
            <div className="card-body">
              <h5 className="card-title">{product.productTitle}</h5>
              <p className="card-text">{product.productDesc}</p>
              <p className="card-text">Price: ${product.price}</p>
              {product.isAvailable ? <p className="card-text">Available</p> : <p className="card-text">Not Available</p>}
            </div>
          </div>
        </div>
      );
    };

const ProductPage = () => {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await axios.get('http://localhost:8080/product/all-products');
        const formattedProducts = response.data.map(product => ({
          productId: product[0],
          ownerId: product[1],
          productTitle: product[2],
          productDesc: product[3],
          price: product[4],
          productDeadline: product[5],
          isAvailable: product[6],
          current_bid: product[7],
          fileUrls: [product[8]]
        }));
        setProducts(formattedProducts);
      } catch (error) {
        console.error('Error fetching products: ', error);
      }
    };

    fetchProducts();
  }, []);

  return (
    <div className="product-page">
      <h1>Product Showcase</h1>
      <div className="product-container">
        {products.map((product) => (
          <Product key={product.productId} product={product} />
        ))}
      </div>
    </div>
  );
};

export default ProductPage;
