import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import './App.css';

import LoginPage from './user/LoginPage';
import RegisterPage from './user/RegisterPage';
import AddProduct from './product/AddProduct';
import ProductPage from './product/ProductPage';
import SingleProductPage from './product/SingleProductPage';

function App() {
  return (
    <div className="App">
      <h1>Bid-Bash</h1>
      <Router>
        <Routes>
          <Route path="/login" Component={LoginPage} />
          <Route path='/register' Component={RegisterPage}/>
          <Route path='/add-product' Component={AddProduct}/>
          <Route path='/all-products' Component={ProductPage}/>
          <Route path='/product/:productId' Component={SingleProductPage}/>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
