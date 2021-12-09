import { Heading, Button, HStack, Input, Spacer, Text } from '@chakra-ui/react';
import { React, useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

import { useStoreActions, useStoreState } from 'easy-peasy';

import Page from '../../components/Page';
import Card from '../../components/Card';

import ProductAreaList from './ProjectAreaList';
import MemberTable from './MemberTable';

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

export default function Project(props) {
  const project = useStoreState((state) => state.project.data);
  const updateProject = useStoreActions((actions) => actions.project.update);

  const [editMode, setEditMode] = useState(false);

  const handleChange = (key) => (value) => {
    updateProject({ [key]: value });
  };

  const setProjectName = (newName) => updateProject({projectName: newName}) 
  const setMembers = (members) => updateProject({members: members}) 
  const setProductAreas = (productAreas) => updateProject({productAreas: productAreas}) 

  const { id } = useParams();

  useEffect(() => {
    // fetchProject();
    updateProject({...mockProject});
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
          align="center"
          variant="heading"
          isDisabled={!editMode}
          onChange={(e) => setProjectName(e.target.value)}
          value={project.projectName}
        />
        <Spacer />
      </Card>

      <Card direction="column">
        <Heading variant="upper" size="md">
          Members
        </Heading>
        <MemberTable editMode={editMode} members={project.members} setMembers={setMembers} />
      </Card>

      <Card direction="column">
        <Heading variant="upper" size="md">
          Product Areas
        </Heading>
        <ProductAreaList
          productAreas={project.productAreas}
          setProductAreas={setProductAreas}
          editMode={editMode}
        />
      </Card>

      <EditButtons />
    </Page>
  );
}
