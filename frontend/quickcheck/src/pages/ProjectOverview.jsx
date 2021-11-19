import React, { useState, useEffect } from 'react';

import Menubar from '../components/Menubar';
import Card from '../components/card';
import { VStack, List, Button } from '@chakra-ui/react';

const mocks = {
  projects: [
    {
      title: 'Volksbank berlin brandenburg',
      role: 'Consultant',
    },
    {
      title: 'ING',
      role: 'Project Manager',
    },
    {
      title: 'Sparkasse Berlin',
      role: 'Consultant',
    },
  ],
};

function ProjectCard(props) {
  return (
    <Card
      title={props.project.title}
      buttonLabel="open"
      labels={[['Role', props.project.role]]}
    ></Card>
  );
}

export default function ProjectOverview() {
  const [data, setData] = useState({ projects: [] });

  useEffect(() => {
    fetch('https://randomuser.me/api/')
      .then((response) => response.json())
      .then((data) => setData(data));
  }, []);

  return (
    <div>
      <Menubar mb={5} title="Project Overview"></Menubar>
      <VStack justifyContent="center" spacing={10} mt={5}>
        <List spacing={3} maxW={800} mx={2}>
          {mocks.projects.map((project) => (
            <ProjectCard project={project} key={project.title}></ProjectCard>
          ))}
        </List>

        <Button size="lg">Add new</Button>
      </VStack>
    </div>
  );
}
