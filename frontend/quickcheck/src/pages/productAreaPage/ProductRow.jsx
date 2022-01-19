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
  Heading,
  List,
  Link,
} from '@chakra-ui/react';
import React from 'react';
import Card from '../../components/Card';
import { DeleteIcon } from '@chakra-ui/icons';
import { useStoreActions, useStoreState } from 'easy-peasy';
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

function ProductVariant({ productVariant, editMode }) {
  return (
    <Card bg="gray.600" w="full" m={2} justifyContent="space-between">
      <Input
        align="center"
        size="lg"
        w="25%"
        isDisabled={!editMode}
        onChange={(e) => {
          console.log(e.target.value);
        }}
        value={productVariant.productName}
      />
      <Spacer />
      <VStack>
        {/* <CircularProgress size="40px" value={40} /> */}
        <Button variant="whisper">Economical</Button>
      </VStack>
      <Spacer />

      <VStack>
        {/* <CircularProgress size="40px" value={40} /> */}
        <Button variant="whisper">Complexity</Button>
      </VStack>
      <Spacer />
      <Textarea width="30%" placeholder="Anmerkung" />
    </Card>
  );
}

export default function ProductRow({ product, editMode }) {
  const removeProductState = useStoreActions((actions) => actions.productList.removeProduct);
  const changeProductName = useStoreActions((actions) => actions.productList.changeProductName);
  const changeProductComment = useStoreActions(
    (actions) => actions.productList.changeProductComment
  );

  const getVariants = useStoreState((state) => state.productList.getVariants);

  const productVariants = getVariants(product);

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
    <Card
      layerStyle="card_bordered"
      justifyContent="space-between"
      direction="column"
      // w={(parentID > 0) ? ' 90%' : 'full'}
      _hover={{ boxShadow: '2xl' }}
    >
      
      
      <Flex direction="row" w='full' justifyContent="space-between">
      <Flex w="25%" mb={3}>
        <Input
          variant="bold"
          align="center"
          size="2xl"
          isDisabled={!editMode}
          onChange={(e) => {
            setProduct(e.target.value);
          }}
          value={product.productName}
        />
      </Flex>
      <Flex w="75%">
        <VStack mr={5}>
        <CircularProgress size="40px" value={product.progressEconomic} />

          <Link href="/ratings">
            <Button variant="whisper">Economical</Button>
          </Link>
        </VStack>

        <VStack>
        <CircularProgress size="40px" value={product.progressComplexity} />
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
      </Flex>
      </Flex>
      <Heading size="md" mt={8} align="left" w="full">
        Variants
      </Heading>
      <Flex w='full' mt={5}>
      <List w="full">
        {productVariants.map((variant) => (
          // <p>{JSON.stringify(variant)}</p>
          <ProductVariant productVariant={variant} editMode={editMode} key={variant.productID} />
        ))}
      </List>
      </Flex>
    </Card>
  );
}
