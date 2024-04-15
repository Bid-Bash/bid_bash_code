// Dashboard.js
import React, { useState } from 'react';
import Sidebar from './sidebar.js'; // Assuming the correct file name
import MainContent from './maincontent.js'; // Assuming the correct file name
import './dashboard.css'; // Ensure your CSS is appropriately structured for these components

const Dashboard = () => {
  const [selectedSection, setSelectedSection] = useState('My Listings');

  const handleSectionClick = (section) => {
    setSelectedSection(section);
  };

  return (
    <div className="dashboard">
      <Sidebar selectedSection={selectedSection} onSectionClick={handleSectionClick} />
      <MainContent selectedSection={selectedSection} />
    </div>
  );
};

export default Dashboard;
