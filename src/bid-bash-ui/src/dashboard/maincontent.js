import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import TransactionTable from './TransactionTable';

const MainContent = ({ selectedSection }) => {
    const user = JSON.parse(localStorage.getItem("user"));
    const [userProduct, setUserProduct] = useState([]);
    const [userBids, setUserBids] = useState([]);
    const [pendingTransaction, setPendingTrasaction] = useState([]);
    const [successTransaction, setSuccessTransaction] = useState([]);

    useEffect(() => {
        axios.post("http://localhost:8080/product/userItem", { "ownerId": user.userId }).then((response) => {
            setUserProduct(response.data)
        }).catch(error => {
            console.log(error)
        })

        axios.post("http://localhost:8080/bid-request/bidByUser",{ "ownerId": user.userId }).then(response=>{
            const bids=response.data.map(obj =>({
                productId: obj[0],
                productTitle:obj[1],
                productDesc: obj[2],
                isAvailable:obj[3],
                highBid:obj[4]
            }))
            setUserBids(bids);
        }).catch(error=>{
            console.error(error);
        })

        axios.post("http://localhost:8080/bid-request/saleOrders",{"userId":user.userId}).then(response =>{
            console.log(response.data)

            const tr = response.data.map(obj =>({
                productName:obj[0],
                price:obj[1],
                productId:obj[2],
                isPaid: obj[3] != null ? true : false,
                saleId: obj[4],
                tra:obj[3]
            }))

            console.log(tr);
            
            setPendingTrasaction(tr.filter(t => !t.isPaid))
            setSuccessTransaction(tr.filter(t => t.isPaid))
            
        }).then(error =>{
            console.log(error)
        })




    }, [user.userId])

    return (
        <div className="main-content">
            {selectedSection !== "My Listings" && userProduct ? (
                
                <div className='product-container-myBid'>
                    {selectedSection === "My Bids" && userBids ? ( 
                        userBids.map((bids)=>(
                            <div key={bids.productId} className="product-container-dashboard">
                                <Link to={`/product/${bids.productId}`} style={{ color: 'black' }}>
                                    <div className="card">
                                        <div className='card-pos'>
                                            {/* <img src={product.fileUrls[0]} className="card-img-top" style={{ height: '200px', width: '100%', objectFit: 'cover' }} alt={product.productTitle} /> */}
                                        </div>
                                        <div className="card-body">
                                            <h5 className="card-title">{bids.productTitle}</h5>
                                            <p className="card-text">{bids.productDesc}</p>
                                            <p className="card-text">Your Highest Bid: ${bids.highBid}</p>
                                            {bids.isAvailable ? <p className="card-text text-info">Available</p> : <p className="card-text text-danger">Sold Out</p>}
                                        </div>
                                    </div>
                                </Link>
                            </div>
                     
                        ))
                    ) :( <div className='transactions'>
                        {pendingTransaction.length > 0 ? <div> <h5>Pending Transactions</h5>
                        <TransactionTable transactions={pendingTransaction} type="pending" /> </div>: null}
                        
                        {successTransaction.length > 0 ?  <div><h5>Success Transactions</h5>
                        <TransactionTable transactions={successTransaction} type="success" /> </div> :null}
                        
                      </div>) 
                    }
                </div>
            ) : ( 
                userProduct.map((product) => (
                    <div key={product.productId} className="product-container-dashboard">
                        <Link to={`/product/${product.productId}`} style={{ color: 'black' }}>
                            <div className="card">
                                <div className='card-pos'>
                                    {/* <img src={product.fileUrls[0]} className="card-img-top" style={{ height: '200px', width: '100%', objectFit: 'cover' }} alt={product.productTitle} /> */}
                                </div>
                                <div className="card-body">
                                    <h5 className="card-title">{product.productTitle}</h5>
                                    <p className="card-text">{product.productDesc}</p>
                                    <p className="card-text">Price: ${product.price}</p>
                                    {product.isAvailable ? <p className="card-text text-info">Available</p> : <p className="card-text text-danger">*Sold</p>}
                                </div>
                            </div>
                        </Link>
                    </div>
                ))
            )}
        </div>
    );
};

export default MainContent;
