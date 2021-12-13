import React, { useState, useRef, useEffect } from 'react';
import { useToast } from '@chakra-ui/react';
import Page from '../components/Page';
import { useStore, useStoreActions} from 'easy-peasy';
import {
  //Text,
  Flex,
  Box,
  Modal,
  ModalOverlay,
  ModalHeader,
  List,
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
    productID: 111,
    productName: 'Optionen',
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
    productName: 'Optionen child',
    productArea: {},
    projectID: 1,
    parentID: 111,
  },

  {
    productID: 114,
    productName: 'Optionen child',
    productArea: {},
    projectID: 1,
    parentID: 111,
  },
  {
    productID: 115,
    productName: 'Optionen child',
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

function RemoveButton({ onRemove, product }) {
  return (
    <div>
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
      />
    </div>
  );
}

export default function ProductOverview() {
  const products_state = useStore(state => state.productList.products)
  const [productsData, setProductsData] = useState(products);
  const [editMode, setEditMode] = useState(false);
  //const [input, setInput] = useState("");

  const handleAddProduct = (productName) => {
    const newProduct = {
      productName: productName,
      productID: uuid4(),
      projectID: 1, // Abfragen
      productAreaID: 2, //Abfragen wo man sich befindet
    };
    setProductsData([...productsData, newProduct]);
  };

  const removeProduct = (product) => {
    const newProductsData = productsData.filter((p) => p.productName !== product.productName);
    setProductsData(newProductsData);
  };

  // useEffect(() => {}, []);

  const EditButtons = () => {
    if (editMode) {
      return (
        <HStack>
          {editMode ? <AddButton w={16} onAddProduct={handleAddProduct} /> : {}}
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

  return (
    <div>
      <Page title="Product Overview">
        {/* <Card barColor="cyan"> */}
        <List spacing={2} w="full">
          {getProducts(productsData).map((product) => (
            <>
              <ProductRow
                parentID={0}
                product={product}
                key={uuid4()}
                editable={editMode}
                removeButton={
                  editMode ? <RemoveButton onRemove={removeProduct} product={product} /> : undefined
                }
              ></ProductRow>
              <Box w="95%">
                {getChildren(product).map((child) => (
                  <ProductRow
                    parentID={2}
                    product={child}
                    key={uuid4()}
                    editable={editMode}
                    removeButton={
                      editMode ? (
                        <RemoveButton onRemove={removeProduct} product={child} />
                      ) : undefined
                    }
                  ></ProductRow>
                ))}
              </Box>
            </>
          ))}
          <Button>Generate Results</Button>
        </List>
        {/* </Card> */}
        <EditButtons />
        <p>{JSON.stringify(products_state)}</p>
        {/*<p>{JSON.stringify(products)}</p>
        <p>{JSON.stringify(getProducts(products))}</p>
                  <p>{JSON.stringify(getChildren(products[0]))}</p>*/}
      </Page>
    </div>
  );
}
