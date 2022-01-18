import {
  Input,
  IconButton,
  Button,
  CircularProgress,
  Spacer,
  Textarea,
  VStack,
  Box,
  Link,
} from '@chakra-ui/react';
import React from 'react';
import Card from '../../components/Card';
import { DeleteIcon } from '@chakra-ui/icons';
import { useStoreActions } from 'easy-peasy';
import { useState } from 'react';

function RemoveButton({ removeProdFct }) {
  return (
    <div>
      <IconButton
        icon={<DeleteIcon />}
        onClick={() => {
          removeProdFct();
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

export default function ProductRow({ product, editMode, projectID }) {
  const removeProductState = useStoreActions((actions) => actions.productList.removeProduct);
  const updateProduct = useStoreActions((actions) => actions.productList.updateProduct);
  const changeProduct = useStoreActions((actions) => actions.productList.changeProduct);
  const fetchProducts = useStoreActions((actions) => actions.productList.fetch);



  const removeProduct = () => {
    removeProductState(product);
  };

  const setProduct = (productName) => {
    product.productName = productName
    //console.log(JSON.stringify(product))
    changeProduct(product);
    // updateProduct(product);
    //fetchProducts(product.projectID)
  };

  return (
    <div>
      <Card
        layerStyle="card_bordered"
        justifyContent="space-between"
        // w={(parentID > 0) ? ' 90%' : 'full'}
        _hover={{ boxShadow: '2xl' }}
      >
        <Input
          align="center"
          size="md"
          w="25%"
          isDisabled={!editMode}
          onChange={(e) => {
            setProduct(e.target.value);
            // console.log(JSON.stringify(product))
          }}
          value={product.productName}
        />
        <Spacer />
        <VStack>
          <CircularProgress size="40px" value={40} />
          <Link href="/ratings">
            <Button variant="whisper">Economical</Button>
          </Link>
        </VStack>
        <Spacer />

        <VStack>
          <CircularProgress size="40px" value={40} />
          <Link href="/ratings">
            <Button variant="whisper" href="/ratings">
              Complexity
            </Button>
          </Link>
        </VStack>
        <Spacer />

        <Textarea bg={'gray.600'} width="30%" placeholder="Anmerkung" />
        {editMode ? (
          <Box ml={3}>
            <RemoveButton removeProdFct={removeProduct} />
          </Box>
        ) : undefined}
      </Card>
    </div>
  );
}
