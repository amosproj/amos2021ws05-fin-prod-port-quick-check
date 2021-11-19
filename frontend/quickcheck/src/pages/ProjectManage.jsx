import React, { Component } from 'react';

import Menubar from '../components/Menubar';
import Card from '../components/card';
import Card_simple from '../components/card_simple';
import MemberCard from '../components/MemberCard';
import { VStack, List, Button, Box} from '@chakra-ui/react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import App from '../App';
const mocks = {
  project: {
    type: 'Project',
    title: 'Volksbank berlin brandenburg',
    role: 'Consultant',
    description:
      'Project with Volksbank.  Project with Volksbank. Project with Volksbank. Project with Volksbank. Project with Volksbank. Project with Volksbank. Project with Volksbank. Project with Volksbank.',
  },
  productArea: {
    type: 'Product Areas',
    title: 'Volksbank berlin brandenburg',
    role: 'Consultant',
    description:
      'Project with Volksbank.  Project with Volksbank. Project with Volksbank. Project with Volksbank. Project with Volksbank. Project with Volksbank. Project with Volksbank. Project with Volksbank.',
  },
  members: [
    {
      Name: 'Max Musterman',
      role: 'Consultant',
    },
    {
      Name: 'Jane Doe',
      role: 'Product Ownder',
    },
    {
      Name: 'TU Berlin',
      role: 'Client',
    },
    {
      Name: 'FU Berlin',
      role: 'Client',
    },
  ],
};

function ProjectCard(props) {

  return (

    <Card_simple
      title={props.project.title}
      type={props.project.type}
      description={props.description}
      labels={[['Role', props.project.role]]}
    ></Card_simple>
  );
}
function Member_Card(props) {
  return <MemberCard Members={props.members}></MemberCard>;
}

export class ManageProject extends Component {
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

            <ProjectCard
              project={mocks.project}
              type={mocks.project.type}
              key={mocks.project.title}
              description={mocks.project.description}
            ></ProjectCard>
            <Member_Card members={mocks.members}> </Member_Card>
            <ProjectCard
              project={mocks.productArea}
              type={mocks.productArea.type}
              key={mocks.productArea.title}
              description={mocks.productArea.description}
            ></ProjectCard>

          <Link to="/projects">
            <Button size="lg">Edit</Button>
          </Link>
        </VStack>
      </div>
    );
  }
}

export default ManageProject;
