import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link, Navigate } from 'react-router-dom';
import { ChakraProvider } from '@chakra-ui/react';
import { StoreProvider } from 'easy-peasy';

import Login from './pages/Login';
import ProjectOverview from './pages/ProjectOverview';
import ProductOverview from './pages/productAreaPage/Page';
import Project from './pages/projectPage/Page';
import ResultPage from './pages/resultPage/Page';
import Rating from './pages/ratingPage/Rating';
import TestRange from './pages/TestRange';

import store from './store';
import theme from './styles/theme';

function App() {
  return (
    <div>
      <ChakraProvider theme={theme}>
        <StoreProvider store={store}>
          <Router>
            <Routes>
              {/* TODO: use proper redirect (see https://gist.github.com/mjackson/b5748add2795ce7448a366ae8f8ae3bb) */}
              <Route path="test" element={<TestRange />} />
              <Route path="/" element={<Navigate replace to="/login" />} />
              <Route path="login" element={<Login />} />
              <Route path="projects" element={<ProjectOverview />} />
              <Route path="projects/:projectID" element={<Project />} />
              <Route path="results" element={<ResultPage />} />
              <Route
                path="projects/:projectID/productArea/:productAreaID"
                element={<ProductOverview />}
              />
              <Route
                path="projects/:projectID/productArea/:productAreaID/products/:productID/ratings"
                element={<Rating />}
              />
            </Routes>
            <Link to="login"> [Login] </Link>
            <Link to="projects"> [Projects] </Link>
            <Link to="products"> [Products] </Link>
            <Link to="ratings"> [Ratings] </Link>
            <Link to="results"> [Results] </Link>
          </Router>
        </StoreProvider>
      </ChakraProvider>
    </div>
  );
}

export default App;
