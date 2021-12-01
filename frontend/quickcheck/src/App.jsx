import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link, Navigate } from 'react-router-dom';

import Login from './pages/Login';
import ProjectOverview from './pages/ProjectOverview';

import Project from './pages/Project'


function App() {
  return (
    <div>
      <Router>
        <Link to="/login"> [Login] </Link>
        <Link to="projects"> [Projects] </Link>

        <Routes>
          {/* TODO: use proper redirect (see https://gist.github.com/mjackson/b5748add2795ce7448a366ae8f8ae3bb) */}
          <Route path="/" element={<Navigate replace to="/login" />} />
          <Route path="login" element={<Login />} />
          <Route path="projects" element={<ProjectOverview />}></Route>
          <Route path="projects/:id" element={<Project />}></Route>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
