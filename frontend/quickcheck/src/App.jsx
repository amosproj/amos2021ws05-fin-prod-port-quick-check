import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link, Navigate } from 'react-router-dom';
import { ChakraProvider } from '@chakra-ui/react';
import { StoreProvider } from 'easy-peasy';

import { store } from './store';

import Login from './pages/Login';
import ProjectOverview from './pages/ProjectOverview';
import Project from './pages/Project';
import TestRange from './pages/TestRange';

function App() {
  return (
    <div>
      <ChakraProvider>
        <StoreProvider store={store}>
          <Router>
            <Link to="/login"> [Login] </Link>
            <Link to="projects"> [Projects] </Link>

            <Routes>
              {/* TODO: use proper redirect (see https://gist.github.com/mjackson/b5748add2795ce7448a366ae8f8ae3bb) */}
              <Route path="/" element={<Navigate replace to="/login" />} />
              <Route path="login" element={<Login />} />
              <Route path="test" element={<TestRange />} />
              <Route path="projects" element={<ProjectOverview />}></Route>
              <Route path="projects/:id" element={<Project />}></Route>
            </Routes>
          </Router>
        </StoreProvider>
      </ChakraProvider>
    </div>
  );
}

export default App;
