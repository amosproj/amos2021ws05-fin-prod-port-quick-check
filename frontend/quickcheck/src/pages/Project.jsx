import {
  Heading,
  Table,
  Thead,
  Tr,
  Td,
  Th,
  Tbody,
  Tfoot,
  Button,
  Text,
  VStack,
  Box,
  HStack,
  Editable,
  EditableInput,
  useEditableControls,
  EditablePreview,
  IconButton,
  ButtonGroup,
  Flex,
  Input,
} from '@chakra-ui/react';
import MemberCard from '../components/MemberCard';
import { React, useState , useEffect} from 'react';
import {
  List,
  Stack,
} from '@chakra-ui/react';

import ProjectAreaCard from '../components/ProjectAreaCard';
import ShowEditable from '../components/editable.jsx';

import Page from '../components/Page';
import { useParams } from 'react-router-dom';
import { api } from '../utils/apiClient';
import Card from '../components/Card';
import { CloseIcon, EditIcon, CheckIcon } from '@chakra-ui/icons';


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


function ProjectCard(prop) {
  return (
    <Card barColor="blue.500">
      <Stack>
        <Text
          color={'green.500'}
          textTransform={'uppercase'}
          fontWeight={800}
          fontSize={'sm'}
          letterSpacing={1.1}
        >
          {prop.type}
        </Text>
        <Heading fontSize={'2xl'} fontFamily={'body'}>
         <ShowEditable text={prop.title} editable={prop.editable}></ShowEditable>
        </Heading>
        <Text color={'gray.500'}>
          {' '}
          <ShowEditable text={prop.description} editable={prop.editable}></ShowEditable>
        </Text>
      </Stack>
    </Card>
  );
}



export default function Project(prop) {
  const [projectData, setprojectData] = useState({
    projectID: 0,
    projectName: '',
    members: [],
    productAreas: [],
  });
  const [editable, setEditable] = useState(false);
const { id } = useParams();
  const getProject = () => {

    api
      .url('/projects/' + id)
      .get()
      .json((json) => setprojectData(json))
      .catch(console.error);
  };

  useEffect(() => {
    getProject();
  }, []);



  const setHeader = (name) => {
    setprojectData({
      projectID: 0,
      projectName: name,
      members: [],
      productAreas: [],
    });
  };

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


        <ProjectCard
          project={mocks.project}
          type="Project"
          title={projectData.projectName}
          description={projectData.description}
          editable={editable}
        />
        <MemberCard members={projectData.members} editable={editable} />
        <ProjectAreaCard areas={projectData.productAreas} editable={editable} />
        <EditButtons />

        <Text> {JSON.stringify(id)}</Text>

      </Page>
  );
}
