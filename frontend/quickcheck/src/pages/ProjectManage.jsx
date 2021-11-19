import React, { Component } from 'react';

import Menubar from '../components/Menubar';
import Card from '../components/card';
import Card_simple from '../components/card_simple';
import MemberCard  from '../components/card_member';
import { VStack, List, Button, Box, Link } from '@chakra-ui/react';


const mocks = {
  project:
    {
    type: "Project",
      title: 'Volksbank berlin brandenburg',
      role: 'Consultant',
      description: "Project with Volksbank.  Project with Volksbank. Project with Volksbank. Project with Volksbank. Project with Volksbank. Project with Volksbank. Project with Volksbank. Project with Volksbank.",
  },
  members:
  [{
      Name: "Max Musterman",
      role: "Consultant",
  },
  {
      Name: "Jane Doe",
      role: "Product Ownder",
  },
    ]
};

function ProjectCard(props) {
  return (
    <Card_simple
      title={props.project.title}
      type={props.project.type}
      description={props.description}
      labels={[
        ['Role', props.project.role],
      ]}
    ></Card_simple>
  );
}
function Member_Card(props) {
  return (
    <MemberCard
      Members={props.members}
    ></MemberCard>
  );
}


export class ProjectManage extends Component {
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
        <Menubar mb={5} title="Manage Project"></Menubar>
        <VStack justifyContent="center" spacing={10} mt={5}>
          <List spacing={3}   maxW={800} mx={2}>
              <ProjectCard project={mocks.project} type={mocks.project.type} key={mocks.project.title} description={mocks.project.description} ></ProjectCard>
          </List>
          <List spacing={3}   maxW={800} mx={2}>
          <Member_Card  members={mocks.members}>   </Member_Card>
          </List>

          <Link to="../project_manage" ><Button size="lg">
            Add Project
          </Button></Link>

        </VStack>
      </div>
    );
  }
}

export default ProjectManage;
