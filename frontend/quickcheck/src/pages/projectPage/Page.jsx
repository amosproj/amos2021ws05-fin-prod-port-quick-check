import { Heading, Button, HStack, Input, Spacer } from '@chakra-ui/react';
import { React, useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

import ProductAreaList from './ProjectAreaList';

import MemberTable from './MemberTable';
import Page from '../../components/Page';
import Card from '../../components/Card';

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

  const setProjectName = (name) => {
    updateProject({ projectName: name });
  };

  const { id } = useParams();

  useEffect(() => {
    // fetchProject();
    updateProject({ ...mockProject });
  }, []);

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
          onChange={(e) => handleChange('projectName')(e.target.value)}
          value={projectData.projectName}
        />
        <Spacer />
      </Card>

      <Card direction="column">
        <Heading variant="upper" size="md">
          Members
        </Heading>
        <MemberTable editMode={editMode} />
      </Card>

      <Card direction="column">
        <Heading variant="upper" size="md">
          Product Areas
        </Heading>
        <ProductAreaList editMode={editMode} />
      </Card>

      {editMode ? (
        <HStack>
          <Button variant="whisper" size="md" onClick={() => setEditMode(false)}>
            Cancel
          </Button>
          <Button variant="primary" size="md" onClick={() => setEditMode(false)}>
            Confirm
          </Button>
        </HStack>
      ) : (
        <Button variant="whisper" size="md" onClick={() => setEditMode(true)}>
          Edit
        </Button>
      )}
    </Page>
  );
}
