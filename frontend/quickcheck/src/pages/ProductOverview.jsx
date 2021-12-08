import React, { useState, useRef } from 'react';
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

/* function TextF(props) {
  return (
    <p>{props.product}</p>
    //<p>{proj}</p> 
  )
}*/

export default function ProductOverview() {
  const [productsData, setProductsData] = useState(products);
  const [editable, setEditable] = useState(false);
  //const [input, setInput] = useState("");
  const refInputProd = useRef();

  /*const handleClickAddButton = () => {
    const newProduct = {
      productName: refInputProd.current.value,
      productID: uuid4(),
      projectID: 1,
      productAreaID: 2,
    };

    setProductsData([...productsData, newProduct]);
    refInputProd.current.value = null;
  };*/

  const handleAddProduct = (productName) => {
    const newProduct = {
      productName: productName,
      productID: uuid4(),
      projectID: 1, // Abfragen
      productAreaID: 2, //Abfragen wo man sich befindet
    };
    setProductsData([...productsData, newProduct]);

  }

  /*const handleRemoveProduct = () => {
    const newProductsData = productsData.filter((p) => p.productName !== refInputProd.current.value);
    setProductsData(newProductsData);
  };*/

  const childToParent = (childdata) => {
    setProductsData(childdata);
  };
  const varf = "DFGHJ";

  const removeProduct = (product) => {
    const newProductsData = productsData.filter((p) => p.productName !== product.productName);
    setProductsData(newProductsData);
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
                productsData={productsData}
                //childToParent={childToParent}
                removeButton={editable ? <RemoveButton onRemove={removeProduct} product={product} /> : <div />}
              > </ProductRow>
            ))}
            <Button>Generate Results</Button>
          </VStack>
        </Card>

        <EditButtons />
      </Page>
    </div>
  );
}
