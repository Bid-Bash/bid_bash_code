import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import './App.css';

import LoginPage from './user/LoginPage';
import RegisterPage from './user/RegisterPage';
import AddProduct from './product/AddProduct';
import ProductPage from './product/ProductPage';
import SingleProductPage from './product/SingleProductPage';
import Navbar from './utilities/Navbar';
import React from 'react';
import Dashboard from './dashboard/dashboard';
import Success from './stripePages/success';
import Failure from './stripePages/failure';

function App() {
  return (
    <div className="App">
      <Router>
        <Navbar/>
        <Routes>
          <Route path="/login" Component={LoginPage} />
          <Route path='/register' Component={RegisterPage}/>
          <Route path='/add-product' Component={AddProduct}/>
          <Route path='/all-products' Component={ProductPage}/>
          <Route path='/product/:productId' Component={SingleProductPage}/>
          <Route path='/dashboard' Component={Dashboard}/>
          <Route path="/success" Component={Success}/>
          <Route path="/failure" Component={Failure}/>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
