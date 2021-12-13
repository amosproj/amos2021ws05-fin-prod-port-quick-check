import React, { useEffect } from 'react';

import Card from '../components/Card';
import Page from '../components/Page';
import { List, Button, Heading, VStack, Text, Spacer } from '@chakra-ui/react';
import { useStoreActions, useStoreState } from 'easy-peasy';
import { Link } from 'react-router-dom';

const mocks = {
  newProject: {
    creator: 'alex@amose.de',
    projectName: 'Mock Project_' + new Date().getSeconds(),
    productAreas: [1],
    members: [
      {
        userEmail: 'alex@amose.de',
        userName: 'Alex Amos',
        role: 'PROJECT_MANAGER',
      },
    ],
  },
};

function ProjectCard({ project }) {
  return (
    <Card layerStyle="card_bordered">
      <Heading color="primary" size="lg" align="center" w="40%" maxW="50%">
        {project.projectName}
      </Heading>
      <Spacer />
      <VStack p={2}>
        <Text fontWeight="bolder" fontSize="md">
          {'Mock Role'}
        </Text>
        <Text fontSize="sm" color="gray.400">
          Role
        </Text>
      </VStack>
      <Spacer />
      <Link to={'' + project.projectID}>
        <Button variant="whisper" size="lg" colorScheme="blue" align="center" w={24}>
          open
        </Button>
      </Link>
    </Card>
  );
}

export default function ProjectOverview() {
  const projectList = useStoreState((state) => state.projectList.items);
  const addProject = useStoreActions((actions) => actions.projectList.add);
  const fetchProjects = useStoreActions((actions) => actions.projectList.fetch);
  const createProject = useStoreActions((actions) => actions.project.sendCreate);

  // runs when rendering
  useEffect(() => {
    fetchProjects();
    console.log('rendered');
    // getProjects();
  }, []);

  // FOR DEV ONLY: create new mock project when pressing 'add new' button
  const postProject = () => {
    createProject(mocks.newProject);
    addProject(mocks.newProject);
    console.log('updated project list:', { projectList });
  };

  return (
    <Page title="Your Projects">
      <List spacing={3} mx={2} w="full">
        {projectList.map((project) => (
          <ProjectCard project={project} key={project.projectID} />
        ))}
      </List>
      <Button size="lg" onClick={postProject}>
        Add new
      </Button>
    </Page>
  );
}
