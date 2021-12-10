import {
  Flex,
  Box,
  Input,
  Heading,
  Editable,
  EditableInput,
  EditablePreview,
  Button,
  CircularProgress,
  CircularProgressLabel,
  Spacer,
  Textarea,
} from '@chakra-ui/react';
import React from 'react';
import Card from './Card';
// import { useEffect } from 'react';

export default function ProductRow({ product, editable, removeButton }) {
  /*const removeProduct = (product) => {
    const newProductsData = productsData.filter((p) => p.productName !== product.productName);
    childToParent(newProductsData);
  };*/

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
          isDisabled={!editable}
          onChange={(e) => {
            console.log(e.target.value);
          }}
          value={product.productName}
        />
        <Spacer />
        <Button> Economical </Button>
        <CircularProgress value={40} color="green.400">
          <CircularProgressLabel>40%</CircularProgressLabel>
        </CircularProgress>
        <Spacer />
        <Button> Complexity </Button>
        <CircularProgress value={40} color="green.400">
          <CircularProgressLabel>40%</CircularProgressLabel>
        </CircularProgress>
        <Spacer />
        <Textarea width="30%" placeholder="Anmerkung" />
        {removeButton}
      </Card>
    </div>
  );
}
