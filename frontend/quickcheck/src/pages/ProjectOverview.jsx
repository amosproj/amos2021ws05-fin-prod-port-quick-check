import React from "react";

import Menubar from "../components/Menubar";
import Card from "../components/card";
import { VStack, List } from "@chakra-ui/react";

const mock = {
  title: "Volksbank berlin brandenburg",
  type: "Open",
  link: "./Login",
  lastEdit: "November 12",
  created: "July 20",
  role: 'Consultant'
};

const mock2 = {
  title: "ING",
  type: "Open",
  link: "./Login",
  lastEdit: "November 12",
  created: "July 20",
  role: 'Consultant'
};

function ProjectOverview(props) {
  return (
    <VStack justifyContent="center" spacing={10}>
      <Menubar title="Project Overview"></Menubar>
        <List spacing={3} maxW={800}>
          <Card project={mock}></Card>
          <Card project={mock2}></Card>
          <Card project={mock2}></Card>
        </List>
    </VStack>
  );
}

export default ProjectOverview;
