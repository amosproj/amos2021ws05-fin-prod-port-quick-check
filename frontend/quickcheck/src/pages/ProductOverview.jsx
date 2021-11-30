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
    members: [],
    productAreas: [],
  },
  area: 'Loan',
};

const product1 = {
  productName: "Product1",
  productID: 1,
  projectID: 1,
  productAreaID: 2
}

const product2 = {
  productName: "Product2",
  productID: 2,
  projectID: 1,
  productAreaID: 2
}



function ProjectCard(props) {

  return (
    <Card>
      <HStack p={2} spacing={5} >
        <Heading size="lg" color="white" align="center" py={{ base: 4, md: 0 }} w="50%">
          {props.product.productName}
        </Heading>

        <Text fontWeight="bolder" fontSize="md">
          <p>Product Area {props.product.productAreaID} </p>
        </Text>

        <Button bg="teal.500" align="center" _hover={{ bg: 'teal.400' }} w={24}>
          Edit
        </Button>
      </HStack>
    </Card>
  );
}

/* function TextF(props) {
  return (
    <p>{props.product}</p>
    //<p>{proj}</p> 
  )
}*/

export default function ProductOverview() {
  const [projectsData, setProjectsData] = useState([product1, product2])

  let [value, setValue] = React.useState("")

  let handleInputChange = (e) => {
    let inputValue = e.target.value
    setValue(inputValue)
  }
  return (
    <div>
      <Page title="Product Overview">
        <Card barColor="cyan">
          <List spacing={3} maxW="900px" mx={2}>
            {projectsData.map((product) => (
              <ProjectCard product={product} key={product.projectID}></ProjectCard>
              //<TextF product={product.productName}/>

            ))}
            <Button> Edit </Button>
          </List>
          
        </Card>
      </Page>
    </div>
  )
}
