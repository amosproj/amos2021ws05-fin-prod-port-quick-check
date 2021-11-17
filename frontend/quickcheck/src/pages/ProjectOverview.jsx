import React, { Component } from 'react';

import Menubar from "../components/Menubar";
import Card from "../components/card";
import { VStack, List } from "@chakra-ui/react";


const mocks = {
  projects: [
    {
      title: "Volksbank berlin brandenburg",
      lastEdit: "November 12",
      role: 'Consultant'
    },
    {
      title: "ING",
      lastEdit: "November 12",
      role: 'Consultant'
    },
    {
      title: "Sparkasse Berlin",
      lastEdit: "10.10.2020",
      role: 'Consultant'
    },
  ]
}

export class ProjectOverview extends Component {

  constructor(props) {
    super(props);

    this.state = {
      data: null,
    };
  }


  componentDidMount() {
    fetch("https://randomuser.me/api/")
      .then(response => response.json())
      .then(data => this.setState(data));
    console.log(this.state.data)
  }



  render() {
    return (
    <VStack justifyContent="center" spacing={10}>
      <Menubar title="Project Overview"></Menubar>
        <List spacing={3} maxW={800}>
          {mocks.projects.map((project) => (
          <Card key={project.title} project={project}></Card>

          ))}
        </List>

    </VStack>
  );
  }
}

export default ProjectOverview
