import { Heading, Button, HStack, Input, Spacer } from '@chakra-ui/react';
import { React, useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

import Page from '../../components/Page';
import Card from '../../components/Card';

import MemberTable from './MemberTable';
import ProductAreaList from './ProjectAreaList';

const mockProject = {
  projectID: 1,
  creatorID: '2375e026-d348-4fb6-b42b-891a76758d5d',
  projectName: 'Amos Bank',
  members: [
    { email: 'consultant@amos.de', role: 'Consultant' },
    { email: 'manager@amos.de', role: 'Project Owner' },
  ],
  productAreas: [1],
};

export default function Project(prop) {
  const [editMode, setEditMode] = useState(false);
  const [projectData, setprojectData] = useState({
    projectID: 0,
    projectName: '',
    members: [],
    productAreas: [],
  });

  const handleChange = (key) => (value) => {
    setprojectData({
      ...projectData,
      [key]: value,
    });
  };

  const setMembers = handleChange('members');
  const setProductAreas = handleChange('productAreas');

  const { id } = useParams();

  useEffect(() => {
    // fetchProject();
    setprojectData(mockProject);
  }, []);

  const EditButtons = () => {
    if (editMode) {
      return (
        <HStack>
          <Button variant="whisper" size="md" onClick={() => setEditMode(false)}>
            Cancel
          </Button>
          <Button variant="primary" size="md" onClick={() => setEditMode(false)}>
            Confirm
          </Button>
        </HStack>
      );
    } else {
      return (
        <Button variant="whisper" size="md" onClick={() => setEditMode(true)}>
          Edit
        </Button>
      );
    }
  };

  return (
    <Page title="Manage Project">
      <Card layerStyle="card_bar" justifyContent="center">
        <Spacer />
        <Heading variant="upper" size="md" mr={3}>
          Project:
        </Heading>
        <Input
          variant="bold"
          size="3xl"
          w="auto"
          isDisabled={!editMode}
          onChange={(e) => handleChange('projectName')(e.target.value)}
          value={projectData.projectName}
        />
        <Spacer />
      </Card>

      <Card direction="column">
        <Heading variant="upper" size="md">
          Members
        </Heading>
        <MemberTable editMode={editMode} members={projectData.members} handleChange={setMembers} />
      </Card>

      <Card direction="column">
        <Heading variant="upper" size="md">
          Product Areas
        </Heading>
        <ProductAreaList
          areaIDs={projectData.productAreas}
          handleChange={setProductAreas}
          editMode={editMode}
        />
      </Card>

      <EditButtons />
    </Page>
  );
}
