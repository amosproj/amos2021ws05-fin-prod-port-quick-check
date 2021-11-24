import React, { useState, useEffect } from 'react';

import Card from '../components/Card';
import Page from '../components/Page';
import { List, Button, Heading, VStack, Text, Spacer } from '@chakra-ui/react';
import { useToast } from '@chakra-ui/react';

import { api } from '../utils/apiClient';

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
    <Card>
      <Heading size="lg" color="teal.400" align="center" py={{ base: 4, md: 0 }} w="50%">
        {props.project.projectName}
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
      <Button bg="teal.500" align="center" _hover={{ bg: 'teal.400' }} w={24}>
        open
      </Button>
    </Card>
  );
}

export default function ProductOverview() {
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
    <Page title="Product Overview">
      <List spacing={3} maxW="900px" mx={2}>
        {projectsData.map((project) => (
          <ProjectCard project={project} key={project.projectID}></ProjectCard>
        ))}
      </List>

      <Button size="lg" onClick={createProject}>
        Add new
      </Button>
    </Page>
  );
}
