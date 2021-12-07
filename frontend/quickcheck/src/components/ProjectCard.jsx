import { Button, Heading, VStack, Text, Spacer, Link } from '@chakra-ui/react';
import Card from '../components/Card';
import { mocks } from '../pages/ProjectOverview.jsx';

export default function ProjectCard({ project }) {
  return (
    <Card layerStyle="card_bordered">
      <Heading color="primary" size="lg" align="center" w="40%" maxW="50%">
        {project.projectName}
      </Heading>
      <Spacer />
      <VStack p={2}>
        <Text fontWeight="bolder" fontSize="md">
          {mocks.role}
        </Text>
        <Text fontSize="sm" color="gray.400">
          Role
        </Text>
      </VStack>
      <Spacer />
      <Link to={'' + project.projectID}>
        <Button variant="wisper" size="lg" colorScheme="blue" align="center" w={24}>
          open
        </Button>
      </Link>
    </Card>
  );
}
