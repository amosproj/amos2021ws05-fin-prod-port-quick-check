import React, { useState, useEffect } from 'react';
import { List, Button } from '@chakra-ui/react';
import { useToast } from '@chakra-ui/react';

//utisl
import { api } from '../utils/apiClient';

//components
import Page from '../components/Page';
import ProjectCard from '../components/ProjectCard.jsx';

export const mocks = {
  newProject: {
    creatorID: '2375e026-d348-4fb6-b42b-891a76758d5d',
    projectName: 'Mock Project',
    members: ['2375e026-d348-4fb6-b42b-891a76758d5d', '0fef539d-69be-4013-9380-6a12c3534c67'],
    productAreas: [],
  },
  role: 'Mock Consultant',
};

export default function ProjectOverview() {
  const [projectsData, setProjectsData] = useState([]);
  const toast = useToast();

  // one way of showing an error notification to the user
  const errorNotification = (err) => {
    console.error('internal error:', err.message);
    toast({
      title: 'Error occured!',
      description: 'check dev console',
      status: 'error',
      duration: 3000,
      isClosable: true,
    });
  };

  // get all projects from the API
  const getProjects = () => {
    api
      .url('/projects')
      .get()
      .json((json) => setProjectsData(json));
  };

  // runs when rendering
  useEffect(() => {
    getProjects();
  }, []);

  // FOR DEV ONLY: create new mock project when pressing 'add new' button
  const createProject = () => {
    projectsData.push(mocks.newProject);
    api
      .url('/projects')
      .post(mocks.newProject)
      .internalError((err) => errorNotification(err))
      .res()
      .catch(console.error);
  };

  return (
    <Page title="Your Projects">
      <List spacing={3} mx={2} w="full">
        {projectsData.map((project) => (
          <ProjectCard project={project} key={project.projectID} />
        ))}
      </List>
      <Button size="lg" onClick={createProject}>
        Add new
      </Button>
    </Page>
  );
}
