import React, { useState, useEffect } from 'react';
import { Box, Input, Heading, Button, Text } from '@chakra-ui/react';
import { useStoreState, useStoreActions } from 'easy-peasy';

const Child = () => {
  const project = useStoreState((state) => state.project);

  return (
    <Box>
      <Text>project name: {project.projectName} (in child component)</Text>
    </Box>
  );
};

export default function TestRange() {
  const project = useStoreState((state) => state.project);
  const updateProject = useStoreActions((actions) => actions.updateProject);
  const fetchProject = useStoreActions((actions) => actions.fetchProject);

  const [name, setName] = useState(project.projectName);

  useEffect(() => {
    fetchProject({ id: 1 });
    console.log('rendered');
  }, []);

  return (
    <Box bg="gray.700" align="center" mt="20vh">
      <Input
        placeholder="project name"
        value={name}
        onChange={(e) => {
          setName(e.target.value);
        }}
      />
      <Button onClick={() => updateProject({ projectName: name })}>update project name</Button>
      <Text>project: {JSON.stringify(project)}</Text>
      <Child />
    </Box>
  );
}
