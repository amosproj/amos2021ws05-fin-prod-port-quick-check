import { Heading, Button, Text, HStack, Stack } from '@chakra-ui/react';
import MemberCard from '../components/MemberCard';
import { React, useState, useEffect } from 'react';

import ProjectAreaCard from '../components/ProjectAreaCard';
import ShowEditable from '../components/editable.jsx';

import Page from '../components/Page';
import { useParams } from 'react-router-dom';
import { api } from '../utils/apiClient';
import Card from '../components/Card';

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

const mockProject = {
  projectID: 1,
  creatorID: '2375e026-d348-4fb6-b42b-891a76758d5d',
  projectName: 'Amos Bank',
  // members: ['2375e026-d348-4fb6-b42b-891a76758d5d', '0fef539d-69be-4013-9380-6a12c3534c67'],
  members: [
    { email: 'consultant@amos.de', role: 'Consultant' },
    { email: 'manager@amos.de', role: 'Project Managers' },
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

  const updateMembers = (newMembers) => {
    // extra func because member card only knows the members
    setprojectData({
      ...projectData,
      members: newMembers,
    });
  };

  // const { id } = useParams();
  const fetchProject = () => {
    // api
    //   .url('/projects/' + id)
    //   .get()
    //   .json((json) => setprojectData(json))
    //   .catch(console.error);
    setprojectData(mockProject);
    console.log(projectData);
  };

  useEffect(() => {
    fetchProject();
  }, []); // call again when project data changed

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
        project={projectData.project}
        type="Project"
        title={projectData.projectName}
        description={projectData.description}
        editable={editable}
      />
      <MemberCard editable={editable} members={projectData.members} memberUpdater={updateMembers} />

      <Button onClick={(e) => console.log(projectData.members)}>log</Button>

      <ProjectAreaCard areas={projectData.productAreas} editable={editable} />
      <EditButtons />
    </Page>
  );
}
