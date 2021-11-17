import React from "react";

import Menubar from "../components/Menubar";
import Card from "../components/card";
import { VStack, Flex, List } from "@chakra-ui/react";

// import {Flex, VStack, Heading, Input, Button, FormControl, FormLabel} from "@chakra-ui/react"

const mock = {
  title: "Mocki",
  lastopened: "10.10.2020",
};

function ProjectOverview(props) {
  return (
    <VStack justifyContent="center" spacing={10}>
      <Menubar title="Project Overview"></Menubar>
      <Flex base={1000} flexDir="column">
        <List spacing={3}>
          <Card title="Project 4" type="Open"></Card>
          <Card title="Project 1" type="Open"></Card>

          <Card
            title="Project 1"
            type="Open"
            link="./Login"
            productowner="Amos"
            lastopened="November 12"
            created="July 20"
          ></Card>
        </List>
      </Flex>
    </VStack>
  );
}

export default ProjectOverview;
