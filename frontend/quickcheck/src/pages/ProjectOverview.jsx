import React, { Component } from 'react';

import Menubar from '../components/Menubar';
import Card from '../components/card';
import { VStack, List, Button, Box } from '@chakra-ui/react';

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
      labels={[
        ['Role', props.project.role],
      ]}
    ></Card>
  );
}

export class ProjectOverview extends Component {
  constructor(props) {
    super(props);

    this.state = {
      data: null,
    };
  }

  componentDidMount() {
    fetch('https://randomuser.me/api/')
      .then((response) => response.json())
      .then((data) => this.setState(data));
    console.log(this.state.data);
  }

  render() {
    return (
      <div>
        <Menubar mb={5} title="Project Overview"></Menubar>
        <VStack justifyContent="center" spacing={10} mt={5}>
          <List spacing={3}    maxW={800} mx={2}>
            {mocks.projects.map((project) => (
              <ProjectCard project={project} key={project.title}></ProjectCard>
            ))}
          </List>

          <Button size="lg">Add new</Button>
        </VStack>
      </div>
    );
  }
}

export default ProjectOverview;
