import React, { useState, useEffect } from 'react';

import wretch from 'wretch';

import Menubar from '../components/Menubar';
import Card from '../components/card';
import { VStack, List, Button } from '@chakra-ui/react';

const api = wretch()
  .url('http://localhost:8080')
  .headers({ 'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept' });

const mocks = {
  newProject: {
    creatorID: 0,
    projectName: 'Mock Project',
    members: [1, 2],
    productAreas: [1, 2, 3],
  },
  role: 'Mock Consultant',
};

function ProjectCard(props) {
  return (
    <Card
      title={props.project.projectName}
      buttonLabel="open"
      labels={[['Role', mocks.role]]}
    ></Card>
  );
}

export default function ProjectOverview() {
  const [projectsData, setProjectsData] = useState([]);

  const getProjects = () => {
    api
      .url('/projects')
      .get()
      .json((json) => setProjectsData(json));
  };

  useEffect(() => {
    getProjects();
  });

  const createProject = () => {
    api
      .url('/projects')
      .post(mocks.newProject)
      .res((response) => {
        console.log('POST response:', response);
      });
  };

  console.log(projectsData);

  return (
    <div>
      <Menubar mb={5} title="Project Overview"></Menubar>
      <VStack justifyContent="center" spacing={10} mt={5}>
        <List spacing={3} maxW={800} mx={2}>
          {projectsData.map((project) => (
            <ProjectCard project={project} key={project.projectID}></ProjectCard>
          ))}
        </List>

        <Button size="lg" onClick={createProject}>
          Add new
        </Button>
      </VStack>
    </div>
  );
}
