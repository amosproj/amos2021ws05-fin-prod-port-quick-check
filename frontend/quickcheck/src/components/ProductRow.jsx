import {
  HStack,
  Box,
  Input,
  Heading,
  IconButton,
  Editable,
  EditableInput,
  EditablePreview,
  Button,
  CircularProgress,
  CircularProgressLabel,
  Spacer,
} from '@chakra-ui/react';
import React from 'react';
import { DeleteIcon } from '@chakra-ui/icons';
// import { useEffect } from 'react';

export default function ProductRow({
  product,
  editable,
  removeButton,
  childToParent,
  handleAddProduct,
}) {
  /*const removeProduct = (product) => {
    const newProductsData = productsData.filter((p) => p.productName !== product.productName);
    childToParent(newProductsData);
  };*/

  return (
    <div>
      <Box
        w="full"
        minW="15em"
        p={3}
        mb={6}
        align="center"
        rounded="md"
        boxShadow="md"
        overflow="hidden"
        _hover={{ boxShadow: '2xl' }}
      >
        <HStack spacing={5}>
          <Heading as="h4" size="md">
            {editable ? (
              <Editable defaultValue={product.productName}>
                <EditablePreview />
                <EditableInput />
              </Editable>
            ) : (
              product.productName
            )}
          </Heading>
          <Spacer />
          <Button> Economical Evaluation </Button>
          <CircularProgress value={40} color="green.400">
            <CircularProgressLabel>40%</CircularProgressLabel>
          </CircularProgress>
          <Spacer />
          <Button> Complexity Evaluation </Button>
          <CircularProgress value={40} color="green.400">
            <CircularProgressLabel>40%</CircularProgressLabel>
          </CircularProgress>
          <Spacer />
          <Input width={125} placeholder="Anmerkung" />
          {removeButton}
        </HStack>
      </Box>
    </div>
  );
}
