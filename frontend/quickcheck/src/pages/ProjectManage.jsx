import { React, useState, useEffect } from 'react';
import { Heading, Text, List, Stack, HStack, Button } from '@chakra-ui/react';

import { api } from '../utils/apiClient';
import MemberCard from '../components/MemberCard';
import Card from '../components/Card.jsx';
import ProjectAreaCard from '../components/ProjectAreaCard.jsx';
import ShowEditable from '../components/Editable.jsx';
import Page from '../components/Page';

const mocks = {
  project: {
    type: 'Project',
    title: 'Volksbank berlin brandenburg',
    role: 'Consultant',
    description:
      'Project with Volksbank.  Project with Volksbank. Project with Volksbank. Project with Volksbank. Project with Volksbank. Project with Volksbank. Project with Volksbank. Project with Volksbank.',
  },
  productAreas: [
    {
      type: 'finances',
      percent: '75',
    },
    {
      type: 'stuff',
      percent: '90',
    },
    {
      type: 'Money',
      percent: '1',
    },
  ],
  members: [
    {
      Name: 'Max Musterman',
      role: 'Consultant',
    },
    {
      Name: 'Jane Doe',
      role: 'Product Ownder',
    },
    {
      Name: 'TU Berlin',
      role: 'Client',
    },
    {
      Name: 'FU Berlin',
      role: 'Client',
    },
  ],
};

function ProjectCard(props) {
  return (
    <Card>
      <Stack>
        <Text
          color="green.500"
          textTransform="uppercase"
          fontWeight={800}
          fontSize="sm"
          letterSpacing={1.1}
        >
          {props.type}
        </Text>
        <Heading fontSize="2xl" fontFamily="body">
          <ShowEditable text={props.title} editable={props.editable}></ShowEditable>
        </Heading>
      </Stack>
    </Card>
  );
}

export default function ManageProject(prop) {
  const [projectData, setProjectData] = useState([]);
  const [editable, setEditable] = useState(false);

  const getProject = (id) => {
    api
      .url('/projects/' + id)
      .get()
      .json((json) => setProjectData(json));
  };

  // runs when rendering
  useEffect(() => {
    getProject();
  });

  const EditButtons = () => {
    if (editable) {
      return (
        <HStack>
          <Button size="md" onClick={() => setEditable(false)}>
            Cancel
          </Button>
          <Button size="md" onClick={() => setEditable(false)}>
            Confirm
          </Button>
        </HStack>
      );
    } else {
      return (
        <Button size="md" onClick={() => setEditable(true)}>
          Edit
        </Button>
      );
    }
  };

  return (
    <Page title="Manage Project">
      <Text>{prop.id}</Text>
      <Card barColor="blue.500">
        <Stack>
          <Text
            color="green.500"
            textTransform="uppercase"
            fontWeight={800}
            fontSize="sm"
            letterSpacing={1.1}
          >
            {mocks.project.type}
          </Text>
          <Heading fontSize="2xl" fontFamily="body">
            <ShowEditable text={mocks.project.title} editable={editable}></ShowEditable>
          </Heading>
        </Stack>
      </Card>

      <MemberCard members={mocks.project.members} editable={editable} />

      <ProjectAreaCard areas={mocks.project.productAreas} editable={editable} />

      <EditButtons />
    </Page>
  );
}
