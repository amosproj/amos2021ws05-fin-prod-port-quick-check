import React, { useState, useEffect, useRef } from 'react'
import Page from '../components/Page'
import {
  Text,
  Box,
  Button,
  List,
  Heading,
  HStack,
  Spacer, IconButton,
  VStack, Input
} from "@chakra-ui/react";
import { AddIcon } from '@chakra-ui/icons';
import Card from '../components/Card';
import ProductRow from '../components/ProductRow';
import uuid4 from "uuid";

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

const products = [
  {
    productName: "Product1",
    productID: 1,
    projectID: 1,
    productAreaID: 2
  }, {
    productName: "Product2",
    productID: 2,
    projectID: 1,
    productAreaID: 2
  }]


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

  const [productsData, setProductsData] = useState(products);
  const [editable, setEditable] = useState(false);
  const [input, setInput] = useState("");
  const refInputProd = useRef()
  const EditButtons = () => {
    if (editable) {
      return (
        <HStack>
          <Button size="md" onClick={() => setEditable(false)}>
            Cancel
          </Button>
          <Button size="md" onClick={() => setEditable(false)}>
            Confirm
          </Button>
        </HStack>
      );
    } else {
      return (
        <div>
          <IconButton
            icon={<AddIcon />}
            colorScheme='teal'
            variant='outline'
            size="md"
            w={10}
            onClick={handleClickAddButton}>
          </IconButton>
          <Button size="md" onClick={() => setEditable(true)}>
            Edit
          </Button>
        </div>
      );
    }

  }
 
  const handleClickAddButton = () => {
    const newProduct = {
      productName: refInputProd.current.value,
      productID: uuid4(),
      projectID: 1,
      productAreaID: 2
    }
    
    setProductsData([...productsData, newProduct]);
    refInputProd.current.value = null
  }

  /* function handleProductChange(e){
    const productName = refInputProd.current.value
    newProduct = {
      productName: productName,
      productID: 3,
      projectID: 1,
      productAreaID: 2
    }
    refInputProd.current.value = null
  }*/
  
  return (
    <div>
      <Page title="Product Overview">

        <Card barColor="cyan">
          <VStack>
            {productsData.map((product) => <ProductRow product={product}></ProductRow>)}
          </VStack>
        </Card>
        <Input onInput = {handleClickAddButton} ref={refInputProd} placeholder='Product' />
        <EditButtons />
      </Page>
    </div>
  )
}
