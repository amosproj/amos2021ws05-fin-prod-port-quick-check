import React, { useState, useEffect } from 'react';

import wretch from 'wretch';

import Menubar from '../components/Menubar';
import Card from '../components/card';
import { VStack, List, Button } from '@chakra-ui/react';

const api = wretch()
  .url('http://localhost:8080')
  .headers({ 'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept' });

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

const newProjectMock = {
  projectID: 0,
  creatorID: 0,
  projectName: 'string',
  members: [1, 2],
  productAreas: [1, 2, 3],
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

  // const fetchProjects = () => {
  //   api
  //     .url('/projects')
  //     .get()
  //     .json((json) => setData(json));
  // };

  useEffect(() => {
    api.url('/projects').get((json) => console.log(json));
    // fetchProjects();
  }, []);

  // const postProject = () => {
  //   api('/projects')
  //     .post(newProjectMock)
  //     .res((res) => {
  //       console.log(res);
  //     });
  //   // fetchProjects();
  // };

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
