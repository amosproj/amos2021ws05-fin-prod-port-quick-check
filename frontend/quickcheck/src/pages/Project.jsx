import { Heading, Button, Text, HStack, Stack } from '@chakra-ui/react';
import MemberCard from '../components/MemberCard';
import { React, useState, useEffect } from 'react';

import ProjectAreaCard from '../components/ProjectAreaCard';
import ShowEditable from '../components/editable.jsx';

import Page from '../components/Page';
import { useParams } from 'react-router-dom';
import { api } from '../utils/apiClient';
import Card from '../components/Card';

const mockProject = {
  projectID: 1,
  creatorID: '2375e026-d348-4fb6-b42b-891a76758d5d',
  projectName: 'Amos Bank',
  members: [
    { email: 'consultant@amos.de', role: 'Consultant' },
    { email: 'manager@amos.de', role: 'Project Owner' },
  ],
  productAreas: [1, 2, 3],
};

export default function Project(prop) {
  const [editable, setEditable] = useState(true);
  const [projectData, setprojectData] = useState({
    projectID: 0,
    projectName: '',
    members: [],
    productAreas: [],
  });

  const setMembers = (newMembers) => {
    // extra func because member card only knows the members
    setprojectData({
      ...projectData,
      members: newMembers,
    });
  };

  const { id } = useParams();
  const fetchProject = () => {
    api
      .url('/projects/' + id)
      .get()
      .json((json) => setprojectData(json))
      .catch(console.error);
  };

  useEffect(() => {
    // fetchProject();
    setprojectData(mockProject);
  }, []);

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
      <Card barColor="blue.500">
        <Text
          color="green.500"
          textTransform="uppercase"
          fontWeight={800}
          fontSize="sm"
          letterSpacing={1.1}
        >
          Project:
        </Text>
        <Heading size="lg" fontFamily="body">
          <ShowEditable text={projectData.projectName} editable={editable}></ShowEditable>
        </Heading>
      </Card>

      <MemberCard editable={editable} members={projectData.members} handleChange={setMembers} />

      <Button onClick={(e) => console.log(projectData.members)}>console log</Button>

      <ProjectAreaCard areas={projectData.productAreas} editable={editable} />
      <EditButtons />
    </Page>
  );
}
