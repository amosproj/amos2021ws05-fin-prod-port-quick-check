import React, { useState, useEffect } from 'react';

import Card from '../components/Card';
import Page from '../components/Page';
import { List, Button, Heading, VStack, Text, Spacer } from '@chakra-ui/react';
import { useToast } from '@chakra-ui/react';

import { api } from '../utils/apiClient';
import { Link } from 'react-router-dom';

const mocks = {
  newProject: {
    creatorID: 0,
    projectName: "Mock Project",
    members: [
        "2375e026-d348-4fb6-b42b-891a76758d5d",
       "0fef539d-69be-4013-9380-6a12c3534c67"
    ],
    productAreas: []
  },
  role: 'Mock Consultant',
};

function ProjectCard({ project }) {
  return (
    <Card>
      <Heading size="lg" color="teal.400" align="center" py={{ base: 4, md: 0 }} w="50%">
        {project.projectName}
      </Heading>
      <Spacer />
      <VStack p={2} spacing={0}>
        <Text fontWeight="bolder" fontSize="md">
          {mocks.role}
        </Text>
        <Text fontSize="sm" color="gray.400">
          Role
        </Text>
      </VStack>
      <Spacer />
      <Link to={'' + project.projectID}>
        <Button bg="teal.500" align="center" _hover={{ bg: 'teal.400' }} w={24}>
          open
        </Button>
      </Link>
    </Card>
  );
}

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
  });

  // FOR DEV ONLY: create new mock project when pressing 'add new' button
  const createProject = () => {
    api
      .url('/projects')
      .post(mocks.newProject)
      .internalError((err) => errorNotification(err))
      .res()
      .catch(console.error);
  };

  return (
    <Page title="Your Projects">
      <List spacing={3} mx={2} w='80%'>
        {projectsData.map((project) => (
          <ProjectCard project={project} key={project.projectID}/>
        ))}
      </List>
      <Button size="lg" onClick={createProject}>
        Add new
      </Button>
    </Page>
  );
}
