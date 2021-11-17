import React from "react";

import Menubar from "../components/Menubar";
import Card from "../components/card";
import { Flex, VStack } from "@chakra-ui/react";
// import {Flex, VStack, Heading, Input, Button, FormControl, FormLabel} from "@chakra-ui/react"

const mockProjects = {
    "hello": "foo" 
}


function ProjectOverview(props) {


    return (
        <VStack justifyContent='center' base={1000}>

        <Menubar title="Project Overview"></Menubar>

            <Card title={mockProjects.hello} type="Open"></Card>
            <Card title="Project 3" type="Open"></Card>
            <Card title="Project 4" type="Open"></Card>
            <Card title="Project 1" type="Open"></Card>


        </VStack>

    )
}

export default ProjectOverview;
