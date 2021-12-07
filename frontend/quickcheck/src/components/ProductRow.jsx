import {
  HStack,
  Box,
  Input,
  Heading,
  IconButton,
  Button,
  CircularProgress,
  CircularProgressLabel,
  Spacer,
} from '@chakra-ui/react';
import React from 'react';
import { DeleteIcon } from '@chakra-ui/icons';
// import { useEffect } from 'react';

export default function ProductRow({ product, productsData, childToParent, editable }) {
  const removeProduct = (product) => {
    const newProductsData = productsData.filter((p) => p.productName !== product.productName);
    childToParent(newProductsData);
  };

  return (
    <div>
      <Box
        w="full"
        minW="15em"
        bg="gray.600"
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
            {' '}
            {product.productName}{' '}
          </Heading>
          <Spacer />
          <Button bg={'gray.500'}> Economical Evaluation </Button>
          <CircularProgress value={40} color="green.400">
            <CircularProgressLabel>40%</CircularProgressLabel>
          </CircularProgress>
          <Spacer />
          <Button bg={'gray.500'}> Complexity Evaluation </Button>
          <CircularProgress value={40} color="green.400">
            <CircularProgressLabel>40%</CircularProgressLabel>
          </CircularProgress>
          <Spacer />
          <Input width={125} placeholder="Anmerkung" />
          if (editable){' '}
          {
            <IconButton
              icon={<DeleteIcon />}
              onClick={() => {
                removeProduct(product);
              }}
              colorScheme="teal"
              variant="outline"
              size="md"
              color="white"
              bg="red.700"
              w={10}
            />
          }
        </HStack>
      </Box>
    </div>
  );
}
