import { Heading, Button, HStack, VStack, Input, Spacer } from '@chakra-ui/react';
import { React, useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

import { useStoreActions, useStoreState } from 'easy-peasy';

import Page from '../../components/Page';
import Card from '../../components/Card';

import ProductAreaList from './ProjectAreaList';
import MemberTable from './MemberTable';

export default function Project() {
  const project = useStoreState((state) => state.project.data);
  const setName = useStoreActions((actions) => actions.project.setProjectName);
  const initProject = useStoreActions((actions) => actions.project.init);
  const fetchProject = useStoreActions((actions) => actions.project.fetch);
  const sendCreateProject = useStoreActions((actions) => actions.project.sendCreate);
  const sendUpdateProject = useStoreActions((actions) => actions.project.sendUpdate);
  const [editMode, setEditMode] = useState(false);

  const { projectID } = useParams();
  useEffect(() => {
    if (projectID === 'new') {
      initProject();
      setEditMode(true);
    } else {
      fetchProject(projectID);
    }
  }, []);

  const EditButtons = () => {
    const confirm = () => {
      setEditMode(false);

      if (projectID === 'new') {
        sendCreateProject(project);
      } else {
        sendUpdateProject(project);
      }
    };
    const cancel = () => {
      setEditMode(false);
      if (projectID === 'new') {
        window.location.href = '../projects';
      } else {
        fetchProject(projectID);
      }
    };

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
    <Page title="Manage Project" backref="/projects">
      <VStack w="full">
        <Card layerStyle="card_bar" justifyContent="center">
          <Heading variant="upper" size="md" mr={3} align="center">
            Project:
          </Heading>
          <Input
            variant="bold"
            size="3xl"
            w="auto"
            isDisabled={!editMode}
            onChange={(e) => setName(e.target.value)}
            value={project.projectName}
          />
        </Card>
        <Spacer />

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
      </VStack>
    </Page>
  );
}
