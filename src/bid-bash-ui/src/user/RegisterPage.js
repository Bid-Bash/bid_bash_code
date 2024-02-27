import axios from 'axios';
import React, { useState } from 'react';

const RegisterPage = () => {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [city, setCity] = useState('');
    const [country, setCountry] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('')

    const handleRegister = () => {
        // Validate input fields
        setError('')

        if (!name || !email || !password || !confirmPassword || !city || !country) {
            setError('All fields are required');
            setSuccess('')
            return;
        }

        // Validate password and confirm password match
        if (password !== confirmPassword) {
            setError('Passwords do not match');
            setSuccess('')
            return;
        }

        const requestBody = {
            "name":name,
            "email":email,
            "password":password,
            "address1":city,
            "address2":country
        }

        axios.post("http://localhost:8080/user/register", requestBody).then(response =>{

            setError('')
            setSuccess(name + " Registered successfully")
            console.log(response)

        }).catch(error =>{
            setSuccess('')
            setError(error.response.data)
            console.log(error)
        })
    };

    return (
        <div className="container mt-5">
            <div className="row justify-content-center">
                <div className="col-md-6">
                    <div className="card">
                        <div className="card-header">
                            <h3 className="text-center">Register</h3>
                        </div>
                        <div className="card-body">
                            <form>
                                <div className="row mb-3">
                                    <div className="col">
                                        <label htmlFor="name" className="form-label">Name:</label>
                                        <input type="text" className="form-control" id="name" value={name} onChange={(e) => setName(e.target.value)} />
                                    </div>
                                    <div className="col">
                                        <label htmlFor="email" className="form-label">Email:</label>
                                        <input type="email" className="form-control" id="email" value={email} onChange={(e) => setEmail(e.target.value)} />
                                    </div>
                                </div>
                                <div className="row mb-3">
                                    <div className="col">
                                        <label htmlFor="password" className="form-label">Password:</label>
                                        <input type="password" className="form-control" id="password" value={password} onChange={(e) => setPassword(e.target.value)} />
                                    </div>
                                    <div className="col">
                                        <label htmlFor="confirmPassword" className="form-label">Confirm Password:</label>
                                        <input type="password" className="form-control" id="confirmPassword" value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} />
                                    </div>
                                </div>
                                <div className="row mb-3">
                                    <div className="col">
                                        <label htmlFor="city" className="form-label">City:</label>
                                        <input type="text" className="form-control" id="city" value={city} onChange={(e) => setCity(e.target.value)} />
                                    </div>
                                    <div className="col">
                                        <label htmlFor="country" className="form-label">Country:</label>
                                        <input type="text" className="form-control" id="country" value={country} onChange={(e) => setCountry(e.target.value)} />
                                    </div>
                                </div>
                                {error && <div className="alert alert-danger">{error}</div>}
                                {success && <div className="alert alert-success">{success}</div>}
                                <button type="button" className="btn btn-primary btn-block" onClick={handleRegister}>Register</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default RegisterPage;
