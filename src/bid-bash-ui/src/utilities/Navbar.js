import React, {useState, useEffect} from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css';
import { useNavigate, useLocation } from 'react-router-dom';
import { Avatar, Button } from '@mui/material';

const Navbar = () => {

    const [isLoggedIn, setIsLoggedIn] = useState(localStorage.getItem("isLoggedIn"));
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const location = useLocation();
    const [user, setUser] = useState(null);
    //const user = useContext(userContext)
    const navigate = useNavigate()

    // Function to toggle the dropdown
    const toggleDropdown = () => {
        setIsDropdownOpen(!isDropdownOpen);
    };

    useEffect(() => {
        setIsLoggedIn(localStorage.getItem("isLoggedIn"));
        setUser(JSON.parse(localStorage.getItem("user")));
    }, [location, isLoggedIn]);
    
    const handleLogout = () => {
        localStorage.removeItem('isLoggedIn');
        localStorage.removeItem('user');
        // setIsLoggedIn(false);
        setIsDropdownOpen(false);
        setIsLoggedIn(false)
        setUser(null);
        navigate('/', {replace: true})
        //window.location.reload(true);
    };

    
    //console.log("Navbar",user)
    return (
        <nav className="navbar">
            <div className="navbar-brand">
                <img className='img-logo' src='logo-black.png' alt='logo'/>
                {/* <Button size='large' sx={{color: "black"}} href = "/all-products">Bid - Bash</Button> */}
            </div>
            <div className="navbar-nav m-auto">
                <ul>
                    <li className="nav-item">
                        <Button size='large' sx={{color: "black"}} href = "/all-products">All Products</Button>
                    </li>
                </ul>
            </div>
            <div className="navbar-nav">
                <ul>
                    {isLoggedIn ? (
                        <li className="nav-item">
                            <div className="profile-dropdown">
                                <Avatar size='large' sx={{bgcolor: 'black', width:70, height:70}} onClick = {toggleDropdown}>{user ? user.name[0] :  "S"}</Avatar>
                                {isDropdownOpen && (
                                    <div className="dropdown-menu">
                                        <Link to="/dashboard" className="dropdown-item">Account</Link>
                                        <button className="dropdown-item" onClick={handleLogout}>Logout</button>
                                    </div>
                                )}
                            </div>
                        </li>
                    ) : (
                        <li className="nav-item">
                            <Link to={{ pathname: '/login', state: {setIsLoggedIn} }} className="btn btn-primary">Login</Link>
                        </li>
                    )}
                </ul>
            </div>
        </nav>
    );
};

export default Navbar;
