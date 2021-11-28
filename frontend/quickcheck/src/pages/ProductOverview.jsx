import React, { useState, useEffect } from 'react'
import Page from '../components/Page'
import {
  Text,
  Box,
  Button,
  List,
  Heading,
  HStack,
  Spacer
} from "@chakra-ui/react"
import Card from '../components/Card';
// import { useToast } from '@chakra-ui/react';

// import { api } from '../utils/apiClient';
import { Link } from 'react-router-dom';

const prod1mock = {
  newProject: {
    creatorID: 0,
    projectName: 'Karl-Heinz Günther',
    members: [1, 2],
    productAreas: [1, 2, 3],
  },
  area: 'Loan',
};

const project1 = {
  project: {
    projectName: "TestFName1",
    projectID: 0,
  }
}

const project2 = {
  project: {
    projectName: "TestName2",
    projectID: 1,
  }
}
const projectsData = {}
/* const projectsData = {
  project1: {
    projectName: "Name1",
    projectID: 0
  },
  project2:{
    projectName: "Name2",
    projectID: 1
  }
} */





function ProjectCard(props) {
  
  return (
    <Card>
      <Heading size="lg" color="teal.400" align="center" py={{ base: 4, md: 0 }} w="50%">
        {props.project.projectName}
      </Heading>
      <Spacer />
      <HStack p={2} spacing={0}>
        <Text fontWeight="bolder" fontSize="md">
          {prod1mock.area}
        </Text>
        <Text fontSize="sm" color="gray.400">
          Product
        </Text>
      </HStack>
      <Spacer />
      <Button bg="teal.500" align="center" _hover={{ bg: 'teal.400' }} w={24}>
        Edit
      </Button>
    </Card>
  );
}

function TextF(props){
return(
  <p>{props.project}</p> 
  //<p>{proj}</p> 
)
}

export default function ProductOverview() {
  const [projectsData, setProjectsData] = useState([project1])

  let [value, setValue] = React.useState("")

  let handleInputChange = (e) => {
    let inputValue = e.target.value
    setValue(inputValue)
  }
  return (
    <div>
      <Page title="Product Overview">
      <List spacing={3} maxW="900px" mx={2}>
      {projectsData.map((project) => (
          //<ProjectCard project={project} key={project.projectID}></ProjectCard>
          <TextF project={project.project.projectName}/>
          
        ))}
      </List>
      <Box> {projectsData.project}</Box>
      </Page>
    </div>
  )
}
/* 
const project1 = {
  project: {
    projectName: "TestFName1",
    projectID: 0,
  }
}
*/