import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import "./SingleProductPage.css"

const SingleProductPage = (match) => {
    const { productId } = useParams();
    const [product, setProduct] = useState(null);
    const [bidAmount, setBidAmount] = useState(0);
    const [currentBid, setCurrentBid] = useState(0);
    const [stompClient, setStompClient] = useState(null);


    useEffect(() => {

        const fetchProduct = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/product/${productId}`);
                console.log(response)
                setProduct(response.data);
                setCurrentBid(response.data.current_bid)

            } catch (error) {
                console.error('Error fetching product: ', error);
            }
        };

        fetchProduct();

        const connectWebSocket = () => {
            const socket = new SockJS("http://localhost:8080/ws");
            const stompClient = Stomp.over(socket);
    
            stompClient.connect({}, (con) => {
                console.log("Connected", con);
                setStompClient(stompClient);
            });
        };
    
        const subscribeToBidTopic = () => {
            if (stompClient) {
                stompClient.subscribe(`/topic/bid/${productId}`, (response) => {
                    const body = JSON.parse(response.body);
                    console.log('Response Body:', body);
                    setCurrentBid(body.bidAmount);
                });
            }
        };
    
        if (!stompClient) {
            connectWebSocket();
        } else {
            subscribeToBidTopic();
        }
    
        return () => {
            if (stompClient) {
                stompClient.disconnect();
            }
        };

    }, [productId, stompClient]);

    const handleBid = () => {
        const bidRequest = {
            userId: 3,
            productId: productId,
            bidAmount: bidAmount
        }

        axios.post("http://localhost:8080/bid-request/bid", bidRequest).then((response)=>{
            //console.log(response);
        }).catch((error)=>{
            console.log(error);
        })
    };

    // const connect = () =>{
    //     const socket = new SockJS("http://localhost:8080/ws")
    //     const stompClient = Stomp.over(socket);

    //     stompClient.connect({},(con)=>{
    //         //console.log("Connected", con);
    //         stompClient.subscribe((`/topic/bid/${productId}`), (response)=>{
    //             const body = JSON.parse(response.body);
    //             console.log('Response Body:', body);
    //             setCurrentBid(body.bidAmount);
    //         })
    //     })
    // }

    // connect();

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
                                product.fileUrls.map((url, index) => (
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
                            <span className="visually-hidden">Next</span>
                            <span className="carousel-control-next-icon" aria-hidden="true"></span>
                        </button>
                    </div>
                </div>
                <div className="col-md-6">
                    <h1>{product.productTitle}</h1>
                    <p>{product.productDesc}</p>
                    <div className="row">
                        <div className="col-sm-6">
                            <p>Original Price: ${product.price}</p>
                        </div>
                        <div className="col-sm-6">
                            <p>Bidding Price: ${currentBid}</p>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-sm-6">
                            <input type="number" className="form-control" id="bidAmount" value={bidAmount} onChange={(e) => setBidAmount(e.target.value)} />
                        </div>
                        <div className="col-sm-6">
                            <button className="btn btn-primary" onClick={handleBid}>Bid</button>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    );
};

export default SingleProductPage;
