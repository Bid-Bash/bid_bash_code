import axios from 'axios';
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const LoginPage = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const navigate = useNavigate()
  const handleLogin = () => {
    const requestBody = {
      "email":username,
      "password":password
    }

    axios.post('http://localhost:8080/user/login',  requestBody).then(response=>{
      console.log("Login Succesful")
      const user = response.data;
      //console.log(user)
      localStorage.setItem("isLoggedIn", true)
      localStorage.setItem("user", JSON.stringify(user));

      setSuccess(user.name + " Logged in successfully")
      setError('')
      navigate('/all-products')
    }).catch(error=>{
      //console.log(error.resp)
      console.log(error)
      if(error.response.status === 404)
        setSuccess('')
        setError(error.response.data)
      //setError(error)
    })
  };

  return (
    <div className="container mt-5">
        <div className="row justify-content-center">
            <div className="col-md-6">
                <div className="card">
                    <div className="card-header">
                        <h3 className="text-center">Login</h3>
                    </div>
                    <div className="card-body">
                        <form>
                            <div className="mb-3">
                                <label htmlFor="username" className="form-label">Username:</label>
                                <input type="text" className="form-control" id="username" value={username} onChange={(e) => setUsername(e.target.value)} />
                            </div>
                            <div className="mb-3">
                                <label htmlFor="password" className="form-label">Password:</label>
                                <input type="password" className="form-control" id="password" value={password} onChange={(e) => setPassword(e.target.value)} />
                            </div>
                            {error && <div className="alert alert-danger">{error}</div>}
                            {success && <div className="alert alert-success">{success}</div>}
                            <button type="button" className="btn btn-primary btn-block" onClick={handleLogin}>Login</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
);
};

export default LoginPage;
