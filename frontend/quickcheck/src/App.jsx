import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link, Navigate } from 'react-router-dom';

import Login from './pages/Login';
import ProjectOverview from './pages/ProjectOverview';
import ProductOverview from './pages/ProductOverview';
import Project from './pages/Project';

function App() {
  return (
    <div>
      <Router>
        <Link to="/login"> [Login] </Link>
        <Link to="projects"> [Projects] </Link>
        <Link to="/products"> [Products] </Link>

        <Routes>
          {/* TODO: use proper redirect (see https://gist.github.com/mjackson/b5748add2795ce7448a366ae8f8ae3bb) */}
          <Route path="/" element={<Navigate replace to="/login" />} />
          <Route path="/login" element={<Login />}></Route>
          <Route path="login" element={<Login />} />
          <Route path="projects" element={<ProjectOverview />}/>
          <Route path="projects/:id" element={<Project />}/>
          <Route path="/products" element={<ProductOverview />}/>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
