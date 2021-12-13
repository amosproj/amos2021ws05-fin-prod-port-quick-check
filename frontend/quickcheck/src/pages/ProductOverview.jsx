import React, { useState, useRef, useEffect } from 'react';
import Page from '../components/Page';
import { useStoreState, useStoreActions } from 'easy-peasy';
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
import { AddIcon, DeleteIcon } from '@chakra-ui/icons';
import Card from '../components/Card';
import ProductRow from '../components/ProductRow';
import uuid4 from 'uuid';
import { api } from '../utils/apiClient';

// import { useToast } from '@chakra-ui/react';

// import { api } from '../utils/apiClient';
//import { Link } from 'react-router-dom';

const mock = {
  product: {
    productID: 151,
    productName: 'Added Prod',
    productArea: {},
    projectID: 1,
    parentID: 0,
  },
};

const products = [
  {
    productID: 111,
    productName: 'Optionen 1',
    productArea: {},
    projectID: 1,
    parentID: 0,
  },
  {
    productID: 112,
    productName: 'Optionen 2',
    productArea: {},
    projectID: 1,
    parentID: 0,
  },
  {
    productID: 113,
    productName: 'Optionen 1 child',
    productArea: {},
    projectID: 1,
    parentID: 111,
  },

  {
    productID: 114,
    productName: 'Optionen 1 child',
    productArea: {},
    projectID: 1,
    parentID: 111,
  },
  {
    productID: 115,
    productName: 'Optionen 2 child',
    productArea: {},
    projectID: 1,
    parentID: 112,
  },
];

const getProducts = (products) => {
  return products.filter((prod) => prod.parentID === 0);
};

const getChildren = (product) => {
  return products.filter((prod) => prod.parentID === product.productID);
};

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
  const addProductAction = useStoreActions((actions) => actions.productList.addProduct);
  const setProducts = useStoreActions((actions) => actions.productList.set);
  //const addProject = useStoreActions((actions) => actions.projectList.add);
  const [productsData, setProductsData] = useState(products);
  const [editMode, setEditMode] = useState(false);

  const set = () => {
    setProducts(products);
  };

  useEffect(() => {
    set();
  }, []);

  const EditButtons = () => {
    if (editMode) {
      return (
        <HStack>
          {editMode ? <AddButton w={16} onAddProduct={addProduct} /> : undefined}
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
  const addProduct = (productName) => {
    const prod = {
      productID: 0,
      productName: productName,
      productArea: {},
      projectID: uuid4(),
      parentID: uuid4,
    };
    addProductAction(prod);
  };

  return (
    <div>
      <Page title="Product Overview">
        <List spacing={2} w="full">
          {productsAction.map((product) => (
            <ProductRow
              parentID={0}
              product={product}
              key={uuid4()}
              editMode={editMode}
            ></ProductRow>
          ))}
        </List>
        <Button>Generate Results</Button>
        <EditButtons />
        <p>{/*JSON.stringify(productsAction)*/}</p>
        <p>{/*JSON.stringify(products)*/}</p>
      </Page>
    </div>
  );
}
