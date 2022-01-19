import React, { useState, useEffect } from 'react';
import Page from '../../components/Page';
import { useStoreState, useStoreActions } from 'easy-peasy';
import { useParams } from 'react-router-dom';
import {
  List,
  Button,
  HStack,
  Link,
} from '@chakra-ui/react';

import AddProductButton from './AddProductButton'
import ProductRow from './ProductRow';



export default function ProductOverview() {
  const allItems = useStoreState((state) => state.productList.items);
  const products = useStoreState((state) => state.productList.products);
  const addProductAction = useStoreActions((actions) => actions.productList.addProduct);
  const fetchProducts = useStoreActions((actions) => actions.productList.fetch);
  const createProduct = useStoreActions((actions) => actions.productList.createProduct);

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
          {editMode ? <AddProductButton w={16} onAddProduct={addProductAPI} /> : undefined}
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
      productName: productName,
      productArea: {},
      projectID: new Date().getSeconds(),
      parentID: 0,
    };
    addProductAction(prod);
  };

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


  return (
    <div>
      <Page title="Product Overview">
        <List spacing={2} w="full">
          {products.map((product) => (
            <ProductRow product={product} key={product.productID} editMode={editMode}></ProductRow>
          ))}
        </List>
        <EditButtons />
        <Link href={`/results/`}>
          <Button>Generate Results</Button>
        </Link>
        <p>{JSON.stringify(allItems)}</p>)
      </Page>
    </div>
  );
}
