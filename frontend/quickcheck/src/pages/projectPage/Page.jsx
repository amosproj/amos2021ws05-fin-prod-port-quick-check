import { Heading, Button, HStack, Input, Spacer } from '@chakra-ui/react';
import { React, useState, useEffect, Redirect } from 'react';
import { useParams } from 'react-router-dom';

import { useStoreActions, useStoreState, sendCreate } from 'easy-peasy';

import Page from '../../components/Page';
import Card from '../../components/Card';

import ProductAreaList from './ProjectAreaList';
import MemberTable from './MemberTable';

const mockProject = {
  projectID: 1,
  creatorID: '2375e026-d348-4fb6-b42b-891a76758d5d',
  projectName: 'Amos Bank',
  members: [
    { userEmail: 'consultant@amos.de', role: 'Consultant' },
    { userEmail: 'manager@amos.de', role: 'Project Owner' },
  ],
  productAreas: [1],
};

export default function Project(props) {
  const project = useStoreState((state) => state.project.data);
  const setName = useStoreActions((actions) => actions.project.setProjectName);
  const updateProject = useStoreActions((actions) => actions.project.update);
  const fetchProject = useStoreActions((actions) => actions.project.fetch);
  const createProject = useStoreActions((actions) => actions.project.sendCreate);
  const [editMode, setEditMode] = useState(false);

  const { id } = useParams();
  useEffect(() => {
     if (id != "new"){
    fetchProject(id);
}
    else{
        setEditMode(true)
    }
    // updateProject({ ...mockProject });
  }, []);

  const EditButtons = () => {
      const confirm = () => {
        setEditMode(false);
        if (id != "new"){
       updateProject({ project });
        }
        else{
            project.projectID=0;
            createProject({project});
        }

    }
    const cancel = () => {
      setEditMode(false);
      if (id != "new"){
     fetchProject(id);
    }
    else{
        window.location.href = "../projects";
    }
  }

    if (editMode) {
      return (
        <HStack>
          <Button variant="whisper" size="md" onClick={() => cancel()}>
            Cancel
          </Button>
          <Button variant="primary" size="md" onClick={() => confirm()}>
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
          onChange={(e) => setName(e.target.value)}
          value={project.projectName}
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

      <EditButtons />
      <p>{JSON.stringify(project)}</p>

    </Page>
  );
}
