import React, { useState, useEffect } from 'react';
import { Component } from 'react';

import {
  SimpleGrid,
  Heading,
  CardLabel,
  Link,
  Table,
  Thead,
  Th,
  Tr,
  Td,
  Tfoot,
  Tbody,
  Box,
  Center,
  Text,
  Stack,
  HStack,
} from '@chakra-ui/react';
import Menubar from '../components/Menubar';
import Card from '../components/card';
import { VStack, List, Button } from '@chakra-ui/react';
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
    <Card
      title={props.project.projectName}
      buttonLabel="open"
      labels={[['Role', mocks.role]]}
    ></Card>
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
    <div>
      <Menubar mb={5} title="Project Overview"></Menubar>
      <VStack justifyContent="center" spacing={10} my={10}>
        <List spacing={3} maxW={800} w="100%" mx={2}>
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
