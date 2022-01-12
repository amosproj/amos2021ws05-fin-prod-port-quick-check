import React, { useState, useEffect } from 'react';
import Page from '../../components/Page';
import { useStoreState, useStoreActions } from 'easy-peasy';
import { useParams } from 'react-router-dom';
import {
  Modal,
  ModalOverlay,
  ModalHeader,
  List,
  ModalContent,
  ModalBody,
  FormControl,
  ModalFooter,
  ModalCloseButton,
  Button,
  useDisclosure,
  HStack,
  IconButton,
  Input,
} from '@chakra-ui/react';
import { AddIcon } from '@chakra-ui/icons';

import ProductRow from './ProductRow';

/* const products = [
  {
    productID: 111,
    productName: 'Optionen 1',
    productArea: {},
    projectID: 100,
    parentID: 0,
  },
  {
    productID: 112,
    productName: 'Optionen 2',
    productArea: {},
    projectID: 100,
    parentID: 0,
  },
  {
    productID: 113,
    productName: 'Optionen 1 child',
    productArea: {},
    projectID: 100,
    parentID: 111,
  },

  {
    productID: 114,
    productName: 'Optionen 1 child',
    productArea: {},
    projectID: 100,
    parentID: 111,
  },
  {
    productID: 115,
    productName: 'Optionen 2 child',
    productArea: {},
    projectID: 100,
    parentID: 112,
  },
];*/

// const getProducts = (products) => {
//   return products.filter((prod) => prod.parentID === 0);
// };

// const getChildren = (product) => {
//   return products.filter((prod) => prod.parentID === product.productID);
// };

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
              <Input
                mb={6}
                placeholder="Product Name"
                onChange={(e) => setProductName(e.target.value)}
              />
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

export default function ProductOverview() {
  const productsAction = useStoreState((state) => state.productList.products);
  // const addProductAction = useStoreActions((actions) => actions.productList.addProduct);
  const fetchProducts = useStoreActions((actions) => actions.productList.fetch);
  const createProduct = useStoreActions((actions) => actions.productList.createProduct);
  // const setProducts = useStoreActions((actions) => actions.productList.set);
  const [editMode, setEditMode] = useState(false);

  const { projectID, productAreaID } = useParams();



  useEffect(() => {
    //setProducts(products);
    fetchProducts(projectID);
    console.log('rendered');
  }, []);

  const EditButtons = () => {
    if (editMode) {
      return (
        <HStack>
          {editMode ? <AddButton w={16} onAddProduct={addProductAPI} /> : undefined}
          <Button size="md" onClick={() => setEditMode(false)}>
            Cancel
          </Button>
          <Button size="md" onClick={() => setEditMode(false)}>
            Confirm
          </Button>
        </HStack>
      );
    } else {
      return (
        <div>
          <Button size="md" onClick={() => setEditMode(true)}>
            Edit
          </Button>
        </div>
      );
    }
  };
  /*const addProduct = (productName) => {
    const prod = {
      productID: new Date().getMilliseconds(),
      productName: productName,
      productArea: {},
      projectID: new Date().getSeconds(),
      parentID: 0,
    };
    addProductAction(prod);
  };*/

  const addProductAPI = (productName) => {
    const prod = {
      productName: productName,
      productArea: {
        id: '1',
      },
      projectID: projectID,
    };
    createProduct(prod);
  };

  /*const updateProduct = (productName) => {
    const updatedProd = {

      "productName": productName,
      "comment": "string",
      "resources":

        [
          "string"
        ]

    }
    //updateProduct(updatedProduct, productID);
  }*/

  return (
    <div>
      <Page title="Product Overview">
        <List spacing={2} w="full">
          {productsAction.map((product) => (
            <ProductRow
              parentID={0}
              product={product}
              key={product.productID}
              editMode={editMode}
            ></ProductRow>
          ))}
        </List>
        <Button>Generate Results</Button>
        <Button onClick={() => fetchProducts(projectID)}>Refresh</Button>
        <EditButtons />
      </Page>
    </div>
  );
}
