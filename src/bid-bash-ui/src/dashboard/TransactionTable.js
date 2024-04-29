import { Button } from '@mui/material';
import axios from 'axios';
import React from 'react';
import { Link } from 'react-router-dom';

const TransactionTable = ({ transactions, type }) => {

  const user = JSON.parse(localStorage.getItem("user"));

  const processPayment = (tr) =>{

    const d = {"saleId":tr.saleId, 
               "productName":tr.productName, 
               "price": tr.price,
              "userName": user.name,
              "emailId":user.email}

    console.log(tr)
    axios.post("http://localhost:8080/payment/charge", d)
    .then(response=> response.data)
    .then(data =>{
      window.location.href = data;
    })
  }

  return (
    <div className="table-responsive">
      <table className="table table-striped">
        <thead>
          <tr>
            <th>Product Name</th>
            <th>Price</th>
            <th>Status</th>
            <th>Action/Date</th>
          </tr>
        </thead>
        <tbody>
          {transactions.map(transaction => (
            <tr key={transaction.productId}>
             <Link to={`/product/${transaction.productId}`}><td>{transaction.productName}</td></Link>
              <td>${transaction.price}</td>
              <td>{type === 'pending' ? 'Pending' : 'Success'}</td>
              {type === "pending" ? (<td> <Button onClick={() => processPayment(transaction)} >Pay</Button> </td>) : <td>{`${transaction.time.getMonth() + 1}/${transaction.time.getDate()}/${transaction.time.getFullYear()} ${transaction.time.getHours()}:${transaction.time.getMinutes()}`}</td>  } 
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default TransactionTable;
