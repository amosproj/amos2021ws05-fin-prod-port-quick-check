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
import React, { useState, useEffect } from 'react';
import Page from '../components/Page';
import { useParams } from 'react-router-dom';
import { api } from '../utils/apiClient';
import Card from '../components/Card';
import { CloseIcon, EditIcon, CheckIcon } from '@chakra-ui/icons';


export default function Project() {
  const [projectData, setprojectData] = useState({
    projectID: 0,
    projectName: '',
    members: [],
    productAreas: [],
  });

  const getProject = () => {
    api
      .url('/projects/' + projectID)
      .get()
      .json((json) => setprojectData(json))
      .catch(console.error);
  };

  useEffect(() => {
    getProject();
  }, []);

  const { projectID } = useParams();

  const setHeader = (name) => {
    setprojectData({
      projectID: 0,
      projectName: name,
      members: [],
      productAreas: [],
    });
  };


  return (
    <Page title="Project">
      <Card barColor="teal">
        <Heading>Project: </Heading>
        <Heading color="teal.300"> {projectData.projectName} </Heading>
      </Card>

      <Card direction="column">
        <Heading size="lg">Project Members</Heading>
      </Card>

      <Card>
        <Heading size="lg">Product Areas</Heading>
        <Button onClick={() => setHeader('clicked')}>hello</Button>
      </Card>

    </Page>
  );
}
