import React, { useEffect } from 'react';

import Card from '../components/Card';
import Page from '../components/Page';
import { List, Button, Heading, VStack, Text, Spacer, Link } from '@chakra-ui/react';
import { useStoreActions, useStoreState } from 'easy-peasy';

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
      <Link href={`projects/${project.projectID}`}>
        <Button variant="whisper" size="lg" colorScheme="blue" align="center" w={24}>
          open
        </Button>
      </Link>
    </Card>
  );
}

export default function ProjectOverview() {
  const projectList = useStoreState((state) => state.projectList.items);
  const fetchProjects = useStoreActions((actions) => actions.projectList.fetch);

  useEffect(() => {
    fetchProjects();
  }, []);

  return (
    <Page title="Your Projects">
      <List spacing={3} mx={2} w="full">
        {projectList.map((project) => (
          <ProjectCard project={project} key={project.projectID} />
        ))}
      </List>
      <Link href='projects/new'>
        <Button size="lg" variant='primary'>New Project</Button>
      </Link>
    </Page>
  );
}
