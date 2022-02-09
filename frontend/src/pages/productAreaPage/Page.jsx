import React, { useState, useEffect } from 'react';
import Page from '../../components/Page';
import { useStoreState, useStoreActions } from 'easy-peasy';
import { useParams } from 'react-router-dom';
import { List, Button, HStack, Link } from '@chakra-ui/react';

import AddProductButton from './AddProductButton';
import ProductRow from './ProductRow';
/**
 * Displays the product area page. 
 * The product area page contains a list of products for a specific product area. 
 * The products are mapped to one list item each displayed by a ProductRow.
 * Products can contain product variants which are nested in a product row.
 * Product variants are mapped to a ProductVariant.
 * The product area page links to the results page and for each product / product variant either to the
 *    - economic rating page or
 *    - complexity rating page or
 *    - evaluation page
 * The page contains an edit button which disappears and is replaced by confirm and cancel buttons once
 * the user clicks it. Then the edit mode is activated and changes to product names and comments can be 
 * made. Clicking on the then appearing confirm button sends the updates made to the backend and ends the 
 * edit mode. Clicking on cancel aborts the updating and discards the changes made. The edit mode will be 
 * switched off.
 */
export default function ProductOverview() {
  const items = useStoreState((state) => state.productList.items);
  const fetchProducts = useStoreActions((actions) => actions.productList.fetch);

  const getAreaProducts = useStoreState((state) => state.productList.getAreaProducts);
  const updateAllProducts = useStoreActions((actions) => actions.productList.updateAllProducts);
  const [editMode, setEditMode] = useState(false);

  const { projectID, productAreaID } = useParams();

  /** 
   * Fetches products initially from backend.
   * Only executed when rendered.
   */
  useEffect(() => {
    fetchProducts(projectID);
    console.log('rendered');
  }, []);

  /**
   * Posts all updated product to backend and
   * turns edit mode off.
   */
  const updateProducts = () => {
    setEditMode(false);
    updateAllProducts(items);
  };
  /**
   * Displays buttons to edit the products when editMode is true.
   * If editMode is turned off buttons to edit the products are is displayed.
   */
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
      <Page title="Product Overview" backref={`/projects/${projectID}`}>
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
        <HStack>
          <EditButtons />
          <Link href={`/projects/${projectID}/productArea/${productAreaID}/results`}>
            <Button>Generate Results</Button>
          </Link>
        </HStack>
      </Page>
    </div>
  );
}
