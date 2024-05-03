import axios from 'axios';
import React, { useEffect, useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';

const AddProduct = () => {
    const [productTitle, setProductTitle] = useState('');
    const [productDesc, setProductDesc] = useState('');
    const [price, setPrice] = useState(0);
    const [images, setImages] = useState([]);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const fileInputRef = useRef(null)
    const user = JSON.parse(localStorage.getItem("user"));
    const navigate = useNavigate()

    useEffect(()=>{
        if (user == null) {
            navigate("/login");
        }
    })
    

    const handleTitleChange = (e) => {
        setProductTitle(e.target.value);
    };

    const handleDescChange = (e) => {
        setProductDesc(e.target.value);
    };

    const handlePriceChange = (e) => {
        setPrice(e.target.value);
    };

    const handleImageChange = (e) => {
        // const fileList = Array.from(e.target.files);
        const selectedFiles = e.target.files;
        setImages([...selectedFiles]);
    };

    const makeAllStatesEmpty = () => {
        setPrice(0)
        setProductDesc('')
        setImages([])
        setProductTitle('')
        fileInputRef.current.value = ''
    }

    const handleSubmit = (e) => {
        e.preventDefault();

        if(!productTitle || !productDesc || !price){
            setError("Please fill all the fields")
            setSuccess("")
            return ;

        }

        const product = {
            "ownerId": user.userId,
            "productTitle": productTitle,
            "productDesc": productDesc,
            "price": price
        }

        let formData = new FormData();

        formData.append('product', JSON.stringify(product));

        images.forEach((im, index) => {
            formData.append('file', im);
        });

        axios.post("http://localhost:8080/product/new-item", formData)
        .then(response => {
            console.log(response)
            setError('')
            setSuccess('Product Register Sucessfully')
            makeAllStatesEmpty()
        })
        .catch(error => {
            console.log(error)
            //setError(error.response.data)
            //setSuccess('')
        });
    };

    return (
        <div className="container mt-5">
            <h2>Add Product</h2>
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label htmlFor="productTitle" className="form-label">Product Title:</label>
                    <input type="text" className="form-control" id="productTitle" value={productTitle} onChange={handleTitleChange} />
                </div>
                <div className="mb-3">
                    <label htmlFor="productDesc" className="form-label">Product Description:</label>
                    <textarea className="form-control" id="productDesc" value={productDesc} onChange={handleDescChange}></textarea>
                </div>
                <div className="mb-3">
                    <label htmlFor="price" className="form-label">Price:</label>
                    <input type="number" step="0.01" className="form-control" id="price" value={price} onChange={handlePriceChange} />
                </div>
                <div className="mb-3">
                    <label htmlFor="images" className="form-label">Images:</label>
                    <input type="file" className="form-control" id="images" ref={fileInputRef} multiple onChange={handleImageChange} />
                </div>
                {error && <div className="alert alert-danger">{error}</div>}
                {success && <div className="alert alert-success">{success}</div>}
                <button type="submit" className="btn btn-primary">Submit</button>
            </form>
        </div>
    );
};

export default AddProduct;
