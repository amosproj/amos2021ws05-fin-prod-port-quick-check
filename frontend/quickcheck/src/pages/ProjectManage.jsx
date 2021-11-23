import React, { Component } from 'react';
import {getSubmitButtonProps, getCancelButtonProps, getEditButtonProps, Editable, EditableInput, EditablePreview , Flex, IconButton, ButtonGroup, CheckIcon, CloseIcon, EditIcon, useEditableControls} from "@chakra-ui/react"

import Menubar from '../components/Menubar';
import Card from '../components/card';
import Card_simple from '../components/card_simple';
import MemberCard from '../components/MemberCard';
import { VStack, List, Button, Box } from '@chakra-ui/react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import { Center, Heading, Text, Stack, Avatar, useColorModeValue } from '@chakra-ui/react';

import App from '../App';
import BaseCard from '../components/BaseCard.jsx';
import ProjectAreaCard from '../components/ProjectAreaCard.jsx';
const mocks = {
  project: {
    type: 'Project',
    title: 'Volksbank berlin brandenburg',
    role: 'Consultant',
    description:
      'Project with Volksbank.  Project with Volksbank. Project with Volksbank. Project with Volksbank. Project with Volksbank. Project with Volksbank. Project with Volksbank. Project with Volksbank.',
  },
  productAreas: [{
      type: 'finances',
      percent: "75",
  },
  {
      type: 'stuff',
      percent: "90",
  },
  {
      type: 'Money',
      percent: "1",
  },
],
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
    <BaseCard

    barColor="blue.500"
    >
    <Stack>
      <Text
        color={'green.500'}
        textTransform={'uppercase'}
        fontWeight={800}
        fontSize={'sm'}
        letterSpacing={1.1}
      >
        {props.type}
      </Text>
      <Heading fontSize={'2xl'} fontFamily={'body'}>
      <Editable defaultValue={props.title}>
      <EditablePreview />
      <EditableInput />
      </Editable>

      </Heading>
      <Text color={'gray.500'}>  <Editable defaultValue={props.description}>
        <EditablePreview />
        <EditableInput />
        </Editable></Text>
    </Stack>
    </BaseCard>
  );
}
function Member_Card(props) {
  return <MemberCard Members={props.members}></MemberCard>;
}

function AreaCard(props) {
  return <ProjectAreaCard areas={props.areas}></ProjectAreaCard>;
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
            title={mocks.project.title}
            description={mocks.project.description}
          ></ProjectCard>
          <Member_Card  members={mocks.members} > </Member_Card>

          <AreaCard  areas={mocks.productAreas} > </AreaCard>

          <Link to="/projects">
            <Button size="lg">Edit</Button>
          </Link>


  }
        </VStack>
      </div>
    );
  }
}

export default ManageProject;
