import {
  Input,
  IconButton,
  Button,
  CircularProgress,
  Spacer,
  Textarea,
  VStack,
  Box,
  Flex,
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
  const changeProductName = useStoreActions((actions) => actions.productList.changeProductName);
  const changeProductComment = useStoreActions(
    (actions) => actions.productList.changeProductComment
  );

  const removeProduct = () => {
    removeProductState(product);
  };

  const setProduct = (productName) => {
    product.productName = productName;
    changeProductName(product);
  };
  const handleTextInputChange = (comment) => {
    product.comment = comment;
    changeProductComment(product);
  };

  return (
    <div>
      <Card
        layerStyle="card_bordered"
        justifyContent="space-between"
        direction="column"
        // w={(parentID > 0) ? ' 90%' : 'full'}
        _hover={{ boxShadow: '2xl' }}
      >
        <Flex w="full" mb={3}>
          <Input
            variant="bold"
            align="center"
            size="xl"
            isDisabled={!editMode}
            onChange={(e) => {
              setProduct(e.target.value);
            }}
            value={product.productName}
          />
        </Flex>
        <Flex w="full">
          <Spacer />
          <VStack mr={5}>
            <CircularProgress size="40px" value={40} />
            <Link href="/ratings">
              <Button variant="whisper">Economical</Button>
            </Link>
          </VStack>

          <VStack>
            <CircularProgress size="40px" value={40} />
            <Link href="/ratings">
              <Button variant="whisper" href="/ratings">
                Complexity
              </Button>
            </Link>
          </VStack>
          <Spacer />

          <Textarea
            width="50%"
            isDisabled={!editMode}
            value={product.comment !== null ? product.comment : ''}
            onChange={(e) => {
              handleTextInputChange(e.target.value);
            }}
            placeholder="Anmerkung"
          />

          {editMode ? (
            <Box ml={3}>
              <RemoveButton removeProdFct={removeProduct} />
            </Box>
          ) : undefined}
        </Flex>
      </Card>
    </div>
  );
}
