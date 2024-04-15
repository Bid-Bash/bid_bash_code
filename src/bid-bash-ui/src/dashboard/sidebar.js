// Sidebar.js
import React from 'react';

const Sidebar = ({ selectedSection, onSectionClick }) => {
  const sections = ['My Listings', 'My Bids', 'Transaction History'];

  return (
    <div className="sidebar">
      {sections.map((section) => (
        <div key={section} onClick={() => onSectionClick(section)} className={selectedSection === section ? "sidebar-item selected" : "sidebar-item"}>
          <span>
            {section}
          </span>
        </div>
      ))}
    </div>
  );
};

export default Sidebar;
