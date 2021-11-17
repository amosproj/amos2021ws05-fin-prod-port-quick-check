import React from "react";

import Menubar from "../components/Menubar";
import Card from "../components/card";
import { Flex } from "@chakra-ui/react";
// import {Flex, VStack, Heading, Input, Button, FormControl, FormLabel} from "@chakra-ui/react"


function ProjectOverview() {


    return (
    <div>
        <Menubar title="Project Overview"></Menubar>
        <Flex justifyContent='center' base={1000}>
            <Card title="Project 1" type="Open" link="./Login" productowner="amos" lastopened="November 12"
></Card>
            <Card title="Project 2" type="Open"></Card>
            <Card title="Project 3" type="Open"></Card>
            <Card title="Project 4" type="Open"></Card>

        </Flex>

        </div>
    )
}

export default ProjectOverview;
