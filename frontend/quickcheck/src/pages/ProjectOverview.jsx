import React from "react";

import Menubar from "../components/Menubar";
import Card from "../components/card";
// import {Flex, VStack, Heading, Input, Button, FormControl, FormLabel} from "@chakra-ui/react"


function ProjectOverview() {


    return (
    <div>
        <Menubar title="Project Overview"></Menubar>

        <Card title="Project 1" type="Open" link="./Login"></Card>
        <Card title="Project 2" type="Open"></Card>
        <Card title="Project 3" type="Open"></Card>
        <Card title="Project 4" type="Open"></Card>
        </div>
    )
}

export default ProjectOverview;
