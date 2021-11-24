import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link, Navigate } from 'react-router-dom';

import Login from './pages/Login';
import ProjectOverview from './pages/ProjectOverview';
import ManageProject from './pages/ProjectManage';
import Project from './pages/Project';

function App() {
  return (
    <div>
      <Router>
        <Link to="/login"> [Login] </Link>
        <Link to="projects"> [Projects] </Link>
        <Link to="/manageProject"> [Manage Project] </Link>

        <Routes>
          {/* TODO: use proper redirect (see https://gist.github.com/mjackson/b5748add2795ce7448a366ae8f8ae3bb) */}
          <Route path="/" element={<Navigate replace to="/login" />} />
          <Route path="login" element={<Login />} />
          <Route path="manageProject" element={<ManageProject />} />
          <Route path="projects" element={<ProjectOverview />}></Route>
          <Route path="projects/:projectID" element={<Project />} />

          {/* TODO use nested routes */}
          {/* <Route path="project/"  element={<ProjectOverview/>}>
            <Route path=':id' element={<Project/>}  />

          </Route> */}
        </Routes>
      </Router>
    </div>
  );
}

export default App;
