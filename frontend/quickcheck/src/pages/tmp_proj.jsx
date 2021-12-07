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
import { useParams } from 'react-router-dom';
import { CloseIcon, EditIcon, CheckIcon } from '@chakra-ui/icons';



//components
import { api } from '../utils/apiClient';
import Page from '../components/Page';
import Card from '../components/Card';

function MemberTable({ members }) {
  return (
    <Table w="full" size="md" bg="gray.600" rounded="md">
      <Thead size="2xl">
        <Tr>
          <Th w="40%">ID</Th>
          <Th w="40%">Name</Th>
          <Th w="20%" isNumeric>
            btn
          </Th>
        </Tr>
      </Thead>
      <Tbody>
        {members.map((id) => (
          <Tr>
            <Td>{id}</Td>
            <Td>{id}</Td>
            <Td isNumeric>
              <Button bg="red" size="sm" m={0} />
              <Button bg="red" size="sm" m={0} />
            </Td>
          </Tr>
        ))}
      </Tbody>
      <Tfoot></Tfoot>
    </Table>
  );
}

function CustomEditable({ value, valueSetter, showEditButtons }) {
  const [stagedValue, setStagedValue] = useState('hello');
  const [editMode, setEditMode] = useState(false);

  const contentEdit = () => {
    return (
      <Input
        value={stagedValue}
        onChange={(e) => {
          setStagedValue(e.target.value);
        }}
      ></Input>
    );
  };

  const contentView = () => {
    return <Text>value{stagedValue}</Text>;
  };

  if (editMode) {
    return (
      <>
        <contentEdit />
        <IconButton size="sm" icon={<CheckIcon />} onClick={(e) => setEditMode(false)} />
        <IconButton size="sm" icon={<CloseIcon />} onClick={(e) => setEditMode(false)} />
      </>
    );
  } else {
    return (
      <>
        <contentView />
        <IconButton size="sm" icon={<EditIcon />} onClick={(e) => setEditMode(true)} />
      </>
    );
  }
  return <Button>{value}</Button>;
}

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

  const [editMode, setEditMode] = useState(false);
  const [testText, setTestText] = useState('hello');

  return (
    <Page title="Project">
      <Card barColor="teal">
        <Heading>Project: </Heading>
        <Heading color="teal.300"> {projectData.projectName} </Heading>
      </Card>

      <Card direction="column">
        <Heading size="lg">Project Members</Heading>
        <MemberTable members={projectData.members} />
      </Card>

      <Card>
        <Heading size="lg">Product Areas</Heading>
        <Button onClick={() => setHeader('clicked')}>hello</Button>
      </Card>

      <CustomEditable value={testText} valueSetter={setTestText} showEditButtons={true} />

      <Input value={'hello'}></Input>
    </Page>
  );
}
