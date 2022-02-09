import React from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import { ChakraProvider } from '@chakra-ui/react';
import { StoreProvider } from 'easy-peasy';

import Login from './pages/Login';
import ProjectOverview from './pages/ProjectOverview';
import ProductOverview from './pages/productAreaPage/Page';
import Project from './pages/projectPage/Page';
import ResultPage from './pages/resultPage/Page';
import Rating from './pages/ratingPage/Rating';

import store from './store';
import theme from './styles/theme';
import Evaluation from './pages/evaluationPage/Evaluation';

function App() {
  return (
    <div>
      <ChakraProvider theme={theme}>
        <StoreProvider store={store}>
          <Router>
            <Routes>
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
                path="projects/:projectID/productArea/:productAreaID/results"
                element={<ResultPage />}
              />
              <Route
                path="projects/:projectID/productArea/:productAreaID/products/:productID/ratings/:ratingArea"
                element={<Rating />}
              />
              <Route
                path="projects/:projectID/productArea/:productAreaID/products/:productID/evaluation"
                element={<Evaluation />}
              />
            </Routes>
          </Router>
        </StoreProvider>
      </ChakraProvider>
    </div>
  );
}

export default App;
