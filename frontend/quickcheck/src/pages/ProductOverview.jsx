import React, { useState, useRef, useEffect} from 'react';
import { useToast } from '@chakra-ui/react';
import Page from '../components/Page';
import {
  //Text,
  //Box,
  Modal,
  ModalOverlay,
  ModalHeader,
  ModalContent,
  ModalBody,
  FormControl,
  FormLabel,
  ModalFooter,
  ModalCloseButton,
  Button,
  useDisclosure,
  //List,
  //Heading,
  HStack,
  //Spacer,
  IconButton,
  VStack,
  Input,
} from '@chakra-ui/react';
import { AddIcon, DeleteIcon } from '@chakra-ui/icons';
import Card from '../components/Card';
import ProductRow from '../components/ProductRow';
import uuid4 from 'uuid';
import { api } from '../utils/apiClient';

// import { useToast } from '@chakra-ui/react';

// import { api } from '../utils/apiClient';
//import { Link } from 'react-router-dom';

/*const prod1mock = {
  newProject: {
    creatorID: 0,
    projectName: 'Karl-Heinz Günther',
    members: [],
    productAreas: [],
  },
  area: 'Loan',
};*/

const products = [
  {
    productName: 'Product1',
    productID: 1,
    projectID: 1,
    productAreaID: 2,
  },
  {
    productName: 'Product2',
    productID: 2,
    projectID: 1,
    productAreaID: 2,
  },
];
const mocks = {
  product: {
    productName: 'ProductAPI',
    productID: 223,
    projectID: 1,
    productAreaID: 2,
  }
}

function AddButton(props) {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [productName, setProductName] = useState('');
  const header = 'Add Product';
  return (
    <>
      <IconButton icon={<AddIcon />} variant="primary" size="lg" {...props} onClick={onOpen} />
      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader color="primary">{header}</ModalHeader>
          <ModalCloseButton />
          <ModalBody px={10}>
            <FormControl>
              <Input mb={6} placeholder="Product Name" onChange={(e) => setProductName(e.target.value)} />
            </FormControl>
          </ModalBody>
          <ModalFooter py={5} px={10}>
            <Button
              variant="primary"
              mx={3}
              onClick={(e) => {
                props.onAddProduct(productName);
                onClose();
              }}
            >
              Save
            </Button>
            <Button onClick={onClose} variant="wisper">
              Cancel
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
}

function RemoveButton({ onRemove, product }) {
  return (<div>
    <IconButton
      icon={<DeleteIcon />}
      onClick={() => {
        //onRemove();
        onRemove(product);
      }}
      colorScheme="teal"
      variant="outline"
      size="md"
      color="white"
      bg="red.700"
      w={10}
    /></div>
  );
}


export default function ProductOverview() {
  const [productsData, setProductsData] = useState(products);
  const [editable, setEditable] = useState(false);
  const toast = useToast();
  //const [input, setInput] = useState("");
  const refInputProd = useRef();


  const handleAddProduct = (productName) => {
    const newProduct = {
      productName: productName,
      productID: uuid4(),
      projectID: 1, // Abfragen
      productAreaID: 2, //Abfragen wo man sich befindet
    };
    setProductsData([...productsData, newProduct]);

  }

  const childToParent = (childdata) => {
    setProductsData(childdata);
  };


  const removeProduct = (product) => {
    const newProductsData = productsData.filter((p) => p.productName !== product.productName);
    setProductsData(newProductsData);
  };
  // get all projects from the API
  const getProducts = () => {
    api
      .url('/products')
      .get()
      .json((json) => setProductsData(json));
  };

  // runs when rendering
  useEffect(() => {
    getProducts();
  }, []);

  const errorNotification = (err) => {
    console.error('internal error:', err.message);
    toast({
      title: 'Error occured!',
      description: 'check dev console',
      status: 'error',
      duration: 3000,
      isClosable: true,
    });
  };
  // FOR DEV ONLY: create new mock project when pressing 'add new' button
  const createProject = () => {
    productsData.push(mocks.product);
    api
      .url('/products')
      .post(mocks.product)
      .internalError((err) => errorNotification(err))
      .res()
      .catch(console.error);
  };

  const EditButtons = () => {
    if (editable) {
      return (

        <HStack>
          {editable ? <AddButton w={16} onAddProduct={handleAddProduct} /> : {}}
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
          <Button size="md" onClick={() => setEditable(true)}>
            Edit
          </Button>
        </div>
      );
    }
  };

  return (
    <div>
      <Page title="Product Overview">
        <Card barColor="cyan">
          <VStack spacing={2}>
            {productsData.map((product) => (
              <ProductRow
                product={product}
                key={uuid4()}
                editable={editable}
                removeButton={editable ? <RemoveButton onRemove={removeProduct} product={product} /> : <div />}
              > </ProductRow>
            ))}
            <Button>Generate Results</Button>
          </VStack>
        </Card>
              {mocks.product.productName}
        <EditButtons />
        <Button onClick={createProject}> API</Button>
      </Page>
    </div>
  );
}
