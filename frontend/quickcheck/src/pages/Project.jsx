import { Heading, Button, HStack } from '@chakra-ui/react';
import { React, useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { useStoreState, useStoreActions } from 'easy-peasy';

import ProductAreaList from '../components/ProjectAreaCard';
import MemberTable from '../components/MemberTable';
import Page from '../components/Page';
import Card from '../components/Card';
import { ConditionalInput } from '../components/Inputs';

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

function CardHeader({ text }) {
  return (
    <Heading size="md" align="center" letterSpacing={1.5} fontWeight={800} color="green.400" py={2}>
      {text}
    </Heading>
  );
}

export default function Project() {
  const project = useStoreState((state) => state.project);
  const updateProject = useStoreActions((actions) => actions.updateProject);
  const fetchProject = useStoreActions((actions) => actions.fetchProject);

  const [editMode, setEditMode] = useState(true);

  const { id } = useParams();
  useEffect(() => {
    // fetchProject({projectID: id})
    updateProject(mockProject);
  }, [editMode]);

  const EditButtons = () => {
    if (editMode) {
      return (
        <HStack>
          <Button size="md" onClick={() => setEditMode(false)}>
            Cancel
          </Button>
          <Button size="md" onClick={() => setEditMode(false)}>
            Confirm
          </Button>
        </HStack>
      );
    } else {
      return (
        <Button size="md" onClick={() => setEditMode(true)}>
          Edit
        </Button>
      );
    }
  };

  return (
    <Page title="Manage Project">
      <Card barColor="blue.500">
        <CardHeader text="PROJECT:" />

        <ConditionalInput
          fontStyle={{ fontSize: '3xl', fontWeight: '700' }}
          editable={editMode}
          value={project.projectName}
          onChange={(val) => updateProject({ projectName: val })}
        />
      </Card>

      <Card direction="column">
        <CardHeader text="MEMBERS" />
        <MemberTable editMode={editMode} />
      </Card>

      <Card direction="column">
        <CardHeader text="PRODUCT AREAS" />
        <ProductAreaList editMode={editMode} />
      </Card>
      <EditButtons />
      <p>{JSON.stringify(project)}</p>
    </Page>
  );
}
