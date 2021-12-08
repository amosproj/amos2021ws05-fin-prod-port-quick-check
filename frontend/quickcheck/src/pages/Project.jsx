import { Heading, Button, HStack } from '@chakra-ui/react';
import MemberTable from '../components/MemberTable';
import { React, useState, useEffect } from 'react';

import ProductAreaList from '../components/ProjectAreaCard';
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
  productAreas: [1],
};

function CardHeader({ text, ...rest }) {
  return (
    <Heading
      {...rest}
      size="md"
      align="center"
      letterSpacing={1.5}
      fontWeight={800}
      color="gray.500"
      py={2}
    >
      {text}
    </Heading>
  );
}

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
          <Button variant="wisper" size="md" onClick={() => setEditMode(false)}>
            Cancel
          </Button>
          <Button variant="primary" size="md" onClick={() => setEditMode(false)}>
            Confirm
          </Button>
        </HStack>
      );
    } else {
      return (
        <Button variant="wisper" size="md" onClick={() => setEditMode(true)}>
          Edit
        </Button>
      );
    }
  };

  return (
    <Page title="Manage Project">
      <Card layerStyle="card_bar" justifyContent="center">
        <CardHeader text="PROJECT:" mr={8} />

        <Heading size="lg" fontFamily="body">
          <ShowEditable text={projectData.projectName} editable={editMode}></ShowEditable>
        </Heading>
      </Card>

      <Card direction="column">
        <CardHeader text="MEMBERS" />
        <MemberTable editMode={editMode} members={projectData.members} handleChange={setMembers} />
      </Card>

      <Card direction="column">
        <CardHeader text="PRODUCT AREAS" />
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
