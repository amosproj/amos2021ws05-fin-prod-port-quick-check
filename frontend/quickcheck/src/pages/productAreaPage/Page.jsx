import React, { useState, useEffect } from 'react';
import Page from '../../components/Page';
import { useStoreState, useStoreActions } from 'easy-peasy';
import { useParams } from 'react-router-dom';
import { List, Button, HStack, Link } from '@chakra-ui/react';

import AddProductButton from './AddProductButton';
import ProductRow from './ProductRow';

export default function ProductOverview() {
  const items = useStoreState((state) => state.productList.items);
  //const products = useStoreState((state) => state.productList.products);
  const fetchProducts = useStoreActions((actions) => actions.productList.fetch);

  const getAreaProducts = useStoreState((state) => state.productList.getAreaProducts);
  const updateAllProducts = useStoreActions((actions) => actions.productList.updateAllProducts);
  const [editMode, setEditMode] = useState(false);

  const { projectID, productAreaID } = useParams();

  useEffect(() => {
    fetchProducts(projectID);
    console.log('rendered');
  }, []);

  const updateProducts = () => {
    setEditMode(false);
    updateAllProducts(items);
  };

  const EditButtons = () => {
    if (editMode) {
      return (
        <HStack>
          {editMode ? <AddProductButton w={16} /> : undefined}
          <Button
            size="md"
            onClick={() => {
              setEditMode(false);
              fetchProducts(projectID);
            }}
          >
            Cancel
          </Button>
          <Button size="md" onClick={() => updateProducts()}>
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
        <List spacing={2} w="full">
          {getAreaProducts(parseInt(productAreaID)).map((product) => (
            <ProductRow
              parentID={0}
              product={product}
              key={product.productID}
              editMode={editMode}
            ></ProductRow>
          ))}
        </List>
        <EditButtons />
        <Link href={`${productAreaID}/results`}>
          <Button>Generate Results</Button>
        </Link>
      </Page>
    </div>
  );
}
