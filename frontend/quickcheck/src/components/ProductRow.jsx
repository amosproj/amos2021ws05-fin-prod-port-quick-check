import {
  Flex,
  Box,
  Input,
  IconButton,
  Button,
  CircularProgress,
  CircularProgressLabel,
  Spacer,
  Textarea,
} from '@chakra-ui/react';
import React from 'react';
import Card from './Card';
import { AddIcon, DeleteIcon } from '@chakra-ui/icons';
import { useStoreState, useStoreActions } from 'easy-peasy';
// import { useEffect } from 'react';

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

export default function ProductRow({ product, editMode }) {
  const removeProductState = useStoreActions((actions) => actions.productList.removeProduct);

  const removeProduct = () => {
    removeProductState(product);
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
        {editMode ? <RemoveButton removeProdFct={removeProduct} /> : undefined}
      </Card>
    </div>
  );
}
